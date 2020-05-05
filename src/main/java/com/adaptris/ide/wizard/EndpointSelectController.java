package com.adaptris.ide.wizard;

import com.adaptris.ide.node.ExternalConnection.ConnectionTechnology;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

public class EndpointSelectController {
  
  @FXML
  private Button buttonHttp;
  
  @FXML
  private Button buttonFs;
  
  @FXML
  private Button buttonMessaging;
  
  @FXML
  private Button buttonFtp;
  
  @FXML
  private Button buttonJdbc;
  
  @FXML
  private Button buttonAws;
  
  @Getter
  @Setter
  private ConnectionTechnology chosenTechnology;
  
  
  @FXML
  public void initialize() {
    buttonHttp.setOnMouseClicked( (event) -> {
      setChosenTechnology(ConnectionTechnology.HTTP);
    });
    
    buttonFs.setOnMouseClicked( (event) -> {
      setChosenTechnology(ConnectionTechnology.FILESYSTEM);
    });
   
  }

}
