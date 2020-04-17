package com.adaptris.ide.node;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

public class ExternalNodeController {

  private ExternalConnection externalConnection;

  @FXML
  private AnchorPane externalNodePane;
  @FXML
  private Label connectionUrl;
  @FXML
  private Label direction;
  @FXML
  private ImageView externalNodeImage;

  @Getter
  @Setter
  private Line lineToInterlokNode;

  @FXML
  public void initialize() {
    externalNodeImage.setImage(externalConnection.getTechnology().getImage());
    connectionUrl.setText(externalConnection.getConnectionUrl());
    direction.setText(externalConnection.getDirection().toString());
    
    externalNodePane.setOnMouseDragged(event -> {
      externalNodePane.setManaged(false);

      externalNodePane.setLayoutX(event.getX() + externalNodePane.getLayoutX());
      externalNodePane.setLayoutY(event.getY() + externalNodePane.getLayoutY());

      lineToInterlokNode.setEndX(externalNodePane.getLayoutX() + externalNodePane.getPrefWidth() / 8);
      lineToInterlokNode.setEndY(externalNodePane.getLayoutY() + externalNodePane.getPrefHeight() / 2);

      event.consume();
    });
  }

  public ExternalConnection getExternalConnection() {
    return externalConnection;
  }

  public void setExternalConnection(ExternalConnection externalConnection) {
    this.externalConnection = externalConnection;
  }

}
