package com.adaptris.ide.wizard;

import com.adaptris.ide.node.ExternalConnection;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

public class EndpointController {

  @FXML
  private ImageView endpointBackgroundImg;
  
  @FXML
  private TextField endpointDirection;
  
  @FXML
  private TextField endpointDestination;
  
  @FXML
  private TextField endpointUrl;
  
  @FXML
  private ImageView endpointImage;
  
  @FXML
  private ImageView endpointSettingsImage;
  
  @Getter
  @Setter
  private ExternalConnection externalConnection;
  
  public EndpointController() {
    
  }
  
  public EndpointController(ExternalConnection externalConnection) {
    this.externalConnection = externalConnection;
  }
  
  @FXML
  public void initialize() {
    if(this.getExternalConnection() != null) {
      endpointImage.setImage(this.getExternalConnection().getTechnology().getImage());
      
      endpointDirection.setText(this.getExternalConnection().getDirection().toString());
      endpointDestination.setText(this.getExternalConnection().getEndpoint());
      endpointUrl.setText(this.getExternalConnection().getConnectionUrl());
      
      endpointSettingsImage.setImage(new Image(getClass().getResourceAsStream("/image/adaptris-logo-gear.png")));
      
      endpointBackgroundImg.setImage(new Image(getClass().getResourceAsStream("/image/honey.png")));
      endpointBackgroundImg.setOpacity(0.2);
    }
  }
  
}
