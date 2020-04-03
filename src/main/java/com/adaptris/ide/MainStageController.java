package com.adaptris.ide;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainStageController implements ClusterInstanceEventListener {
    
  private Map<String, ClusterInstance> clusterInstances;
  
  // Key is combination of host and endpoint.
  private Map<String, ExternalConnection> externalConnections;
  
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
    externalConnections = new HashMap<>();
    
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
      
      networkPane.getChildren().add(interlokInstancePane);
      interlokInstancePane.setLayoutX(300);
      interlokInstancePane.setLayoutY(300);
      
      try {
        List<ExternalConnection> instanceExternalConnections = new InterlokJmxHelper().withMBeanServer(instance.getJmxAddress()).getInterlokConfig().getExternalConnections();
        instanceExternalConnections.forEach(externalConnection -> {
          if(externalConnection.getTechnology().canBeShared()) {
            if(!externalConnections.containsKey(externalConnection.getConnectionUrl())) {
              externalConnections.put(externalConnection.getConnectionUrl(), externalConnection);
              drawNewExternalConnection(externalConnection);
            }
          } else {
            externalConnections.put(externalConnection.getConnectionUrl(), externalConnection);
            drawNewExternalConnection(externalConnection);
          }
        });
        
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  private void drawNewExternalConnection(ExternalConnection externalConnection) {
    try {
      ExternalNodeController controller = new ExternalNodeController();
      controller.setExternalConnection(externalConnection);
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("node/ExternalNode.fxml"));
      loader.setController(controller);
  
      AnchorPane externalInstancePane = loader.load();
      externalInstancePane.getStylesheets().add("/main.css");
      
      networkPane.getChildren().add(externalInstancePane);
      externalInstancePane.setLayoutX(new Random().nextInt((500 - 50) + 1) + 50);
      externalInstancePane.setLayoutY(new Random().nextInt((500 - 50) + 1) + 50);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
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
