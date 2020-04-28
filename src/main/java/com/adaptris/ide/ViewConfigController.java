package com.adaptris.ide;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ViewConfigController
{
  @FXML
  private TextArea configText;

  @FXML
  private Button closeButton;

  private String config;

  public ViewConfigController(String config)
  {
    this.config = config;
  }

  @FXML
  public void initialize()
  {
    configText.setText(config);
    closeButton.setOnMouseClicked((event) ->
    {
      ((Node)(event.getSource())).getScene().getWindow().hide();
    });
  }
}
