package com.adaptris.ide.wizard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class WizardController {

  @FXML
  private AnchorPane wizardConfigPane;

  
  public WizardController() {
    
  }
  
  @FXML
  public void initialize() {
    try {
      ConsumerConfigController controller = new ConsumerConfigController();
  
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsumerConfig.fxml"));
      loader.setController(controller);
  
      AnchorPane consumerConfig = loader.load();
      
      wizardConfigPane.getChildren().clear();
      wizardConfigPane.getChildren().add(consumerConfig);
      
      consumerConfig.setLayoutX(0);
      consumerConfig.setLayoutY(0);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  
  
}
