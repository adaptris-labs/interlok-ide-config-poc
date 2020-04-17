package com.adaptris.ide.node;

import com.adaptris.mgmt.cluster.ClusterInstance;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.HashSet;
import java.util.Set;

public class InterlokNodeController {
  
  private ClusterInstance clusterInstance;

  @FXML
  private AnchorPane interlokNodePane;
  @FXML
  private Label interlokNodeId;
  @FXML
  private ImageView interlokNodeImage;

  private Set<Line> lines = new HashSet<>();

  @FXML
  public void initialize() {
    interlokNodeImage.setImage(new Image(getClass().getResourceAsStream("/image/adaptris-logo.png")));
    interlokNodeId.setText(clusterInstance.getUniqueId());
    
    interlokNodePane.setOnMouseDragged(event -> {
      interlokNodePane.setManaged(false);

      interlokNodePane.setLayoutX(event.getX() + interlokNodePane.getLayoutX());
      interlokNodePane.setLayoutY(event.getY() + interlokNodePane.getLayoutY());

      for (Line line : lines)
      {
        line.setStartX(interlokNodePane.getLayoutX() + interlokNodePane.getPrefWidth() / 8);
        line.setStartY(interlokNodePane.getLayoutY() + interlokNodePane.getPrefHeight() / 2);
      }

      event.consume();
    });
  }

  public ClusterInstance getClusterInstance() {
    return clusterInstance;
  }

  public void setClusterInstance(ClusterInstance clusterInstance) {
    this.clusterInstance = clusterInstance;
  }

  public void addLineToExternalNode(Line line)
  {
    lines.add(line);
  }

  public void cleanLinesToExternalNodes()
  {
    lines.clear();
  }
}
