package com.adaptris.ide.node;

import com.adaptris.mgmt.cluster.ClusterInstance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InterlokNodeController {
  
  private ClusterInstance clusterInstance;

  @FXML
  private AnchorPane interlokNodePane;
  @FXML
  private Label interlokNodeId;
  @FXML
  private ImageView interlokNodeImage;
  
  @FXML
  public void initialize() {
    interlokNodeImage.setImage(new Image(getClass().getResourceAsStream("/image/adaptris-logo.png")));
    interlokNodeId.setText(clusterInstance.getUniqueId());
    
    interlokNodePane.setOnMouseDragged(event -> {
      interlokNodePane.setManaged(false);
      interlokNodePane.setTranslateX(event.getX() + interlokNodePane.getTranslateX());
      interlokNodePane.setTranslateY(event.getY() + interlokNodePane.getTranslateY());
      event.consume();
    });
  }

  public ClusterInstance getClusterInstance() {
    return clusterInstance;
  }

  public void setClusterInstance(ClusterInstance clusterInstance) {
    this.clusterInstance = clusterInstance;
  }
}
