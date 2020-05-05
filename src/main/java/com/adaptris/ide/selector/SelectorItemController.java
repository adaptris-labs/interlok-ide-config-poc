package com.adaptris.ide.selector;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class SelectorItemController {
  
  @FXML
  private TextField selectorText;
  
  @FXML
  private Circle selectorCircle;
  
  private SelectorItemListener listener;

  private SelectorModel myModel;
  
  public SelectorItemController(SelectorItemListener listener, SelectorModel myModel) {
    this.listener = listener;
    this.myModel = myModel;
  }
  
  @FXML
  public void initialize() {
    selectorText.setText(myModel.getDisplayText());
    
    selectorCircle.setOnMouseClicked( event -> {
      listener.itemSelected(myModel);
    });
  }
  
}
