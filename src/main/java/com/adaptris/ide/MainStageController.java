package com.adaptris.ide;

import com.adaptris.ide.jmx.InterlokJmxHelper;
import com.adaptris.ide.node.ExternalConnection;
import com.adaptris.ide.node.ExternalNodeController;
import com.adaptris.ide.node.InterlokNodeController;
import com.adaptris.mgmt.cluster.ClusterInstance;
import com.adaptris.mgmt.cluster.jgroups.ClusterInstanceEventListener;
import com.adaptris.mgmt.cluster.jgroups.JGroupsListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainStageController implements ClusterInstanceEventListener {
    
  private Map<String, ClusterInstance> clusterInstances;
  
  // Key is combination of host and endpoint.
  private Set<ExternalConnection> externalConnections;
  
  private JGroupsListener clusterManager = new JGroupsListener();
  
  @FXML
  private TextField clusterName;
  
  @FXML
  private AnchorPane networkPane;
  
  @FXML
  private Button clusterSearchButton;
  
  @FXML
  public void initialize() {
    clusterInstances = new HashMap<>();
    externalConnections = new HashSet<>();
    
    clusterSearchButton.setOnMouseClicked((event) -> {
      handleSearchCluster();
    });

  }

  private void drawNewClusterInstance(ClusterInstance instance) {
    try {
      InterlokNodeController controller = new InterlokNodeController();
      controller.setClusterInstance(instance);
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("node/InterlokNode.fxml"));
      loader.setController(controller);

      AnchorPane interlokInstancePane = loader.load();
      interlokInstancePane.getStylesheets().add("/main.css");
      interlokInstancePane.setUserData(controller);

      networkPane.getChildren().add(interlokInstancePane);
      interlokInstancePane.setLayoutX(networkPane.getWidth() / 2 - interlokInstancePane.getPrefHeight());
      interlokInstancePane.setLayoutY(networkPane.getHeight() / 2 - interlokInstancePane.getPrefHeight());

      try {
        Set<ExternalConnection> instanceExternalConnections = new InterlokJmxHelper().withMBeanServer(instance.getJmxAddress()).getInterlokConfig().getExternalConnections();
        instanceExternalConnections.forEach(externalConnection -> {
          AnchorPane external = null;
          if (!externalConnection.getTechnology().canBeShared() || !externalConnections.contains(externalConnection))
          {
            externalConnections.add(externalConnection);
            external = drawNewExternalConnection(externalConnection);
            connectClusterToExternal(interlokInstancePane, external);
          }
        });

      } catch (Exception e) {
        e.printStackTrace();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  private AnchorPane drawNewExternalConnection(ExternalConnection externalConnection) {
    try {
      ExternalNodeController controller = new ExternalNodeController();
      controller.setExternalConnection(externalConnection);
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("node/ExternalNode.fxml"));
      loader.setController(controller);
  
      AnchorPane externalInstancePane = loader.load();
      externalInstancePane.getStylesheets().add("/main.css");
      externalInstancePane.setUserData(controller);

      int y = (int)(externalInstancePane.getPrefHeight() / 2);
      for (Node child : networkPane.getChildren()) {
        Object node = child.getUserData();
        if (node instanceof ExternalNodeController)
        {
          ExternalNodeController nodeController = (ExternalNodeController)node;
          if (nodeController != null && nodeController.getExternalConnection().getDirection() == externalConnection.getDirection())
          {
            y += externalInstancePane.getPrefHeight() * 3 / 2;
          }
        }
      }

      networkPane.getChildren().add(externalInstancePane);
      externalInstancePane.setLayoutX((externalConnection.getDirection() == ExternalConnection.ConnectionDirection.CONSUME ? 1 : 7) * (networkPane.getWidth() - externalInstancePane.getPrefWidth()) / 8);
      externalInstancePane.setLayoutY(y);
      return externalInstancePane;
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  private void connectClusterToExternal(AnchorPane cluster, AnchorPane endpoint)
  {
    if (cluster == null || endpoint == null)
    {
      return;
    }
    double clusterX = cluster.getLayoutX() + cluster.getPrefWidth() / 8;
    double clusterY = cluster.getLayoutY() + cluster.getPrefHeight() / 2;
    double externalX = endpoint.getLayoutX() + endpoint.getPrefWidth() / 8;
    double externalY = endpoint.getLayoutY() + endpoint.getPrefHeight() / 2;

    Line line = new Line(clusterX, clusterY, externalX, externalY);
    line.setStroke(Color.rgb(255, 255, 255));

    InterlokNodeController interlok = (InterlokNodeController)cluster.getUserData();
    ExternalNodeController external = (ExternalNodeController)endpoint.getUserData();
    interlok.addLineToExternalNode(line);
    external.setLineToInterlokNode(line);

    networkPane.getChildren().add(line);
  }

  private void handleSearchCluster() {
    // Stop and restart the cluster manager component.
    clusterInstances.clear();
    try {
      clusterManager.stop();
      clusterManager.setJGroupsClusterName(clusterName.getText());
      
      clusterManager.registerListener(this);
      clusterManager.start();
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void clusterInstancePinged(ClusterInstance clusterInstance) {
    Platform.runLater(() -> {
      if (!clusterInstances.containsKey(clusterInstance.getClusterUuid().toString())) {
        clusterInstances.put(clusterInstance.getClusterUuid().toString(), clusterInstance);
        drawNewClusterInstance(clusterInstance);
      }
    });
  }

}
