package com.adaptris.ide.node;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
  
  @FXML
  public void initialize() {
    externalNodeImage.setImage(externalConnection.getTechnology().getImage());
    connectionUrl.setText(externalConnection.getConnectionUrl());
    direction.setText(externalConnection.getDirection().toString());
    
    externalNodePane.setOnMouseDragged(event -> {
      externalNodePane.setManaged(false);
      externalNodePane.setTranslateX(event.getX() + externalNodePane.getTranslateX());
      externalNodePane.setTranslateY(event.getY() + externalNodePane.getTranslateY());
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
