package com.adaptris.ide.selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SelectorController implements SelectorItemListener {
    
  private static final int STAGE_HEIGHT = 450;
  
  private static final int ITEM_HEIGHT = 75;

  @FXML
  private AnchorPane selectorPane;
  
  private SelectorModel model;
  
  private Stage stage;
  
  private List<SelectorItemAndAnchorPaneWrapper> trackOnScreenModelItems;
  
  private SelectorItemChosenListener listener;
  
  public SelectorController(Stage myStage, SelectorModel model, SelectorItemChosenListener listener) {
    this.model = model;
    this.listener = listener;
    this.stage = myStage;
    trackOnScreenModelItems = new ArrayList<SelectorItemAndAnchorPaneWrapper>();
  }
  
  @FXML
  public void initialize() {
    try {
//      selectorPane.setOnMouseClicked( (eventHandler) -> {
//        stage.close();
//        listener.modelItemSelected(null);
//      });
//      selectPane.focusedProperty().addListener((obs, oldVal, newVal) -> {
//        stage.close();
//        listener.modelItemSelected(null);
//      });
      
      AnchorPane rootItem = this.loadItem(model);
      SelectorItemAndAnchorPaneWrapper selectorItemAndAnchorPaneWrapper = new SelectorItemAndAnchorPaneWrapper(model, rootItem);
      
      trackOnScreenModelItems.add(selectorItemAndAnchorPaneWrapper);
      
      selectorPane.getChildren().add(rootItem);
      rootItem.setLayoutX(0);
      rootItem.setLayoutY(((STAGE_HEIGHT / 2) - (rootItem.getPrefHeight() / 2))); 
      
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  @Override
  public void itemSelected(SelectorModel modelItem) {
    if (modelItem.getChildren().size() > 0) {
      drawNewColumnOfItems(modelItem.getChildren());
    } else {
      listener.modelItemSelected(modelItem);
      stage.close();
    }
  }
  
  private void drawNewColumnOfItems(List<SelectorModel> children) {
    //no need to expand the AnchorPane because it's a POC, so I can just make it big to start :-)
    
    this.removeAllOnScreenItems();
    
    int center = ((STAGE_HEIGHT / 2) - (ITEM_HEIGHT / 2));
    int furthestBottom = center;
    int furthestTop = center;
    
    for(int counter = 1; counter <= children.size(); counter ++) {
      try {
        AnchorPane childItem = this.loadItem(children.get(counter - 1));
        trackOnScreenModelItems.add(new SelectorItemAndAnchorPaneWrapper(model, childItem));
        
        selectorPane.getChildren().add(childItem);
        
        childItem.setLayoutX(0);
        if(counter == 1) {
          childItem.setLayoutY(center);
        } else if (counter % 2 == 0) { // even number
          furthestBottom = (furthestBottom + ITEM_HEIGHT);
          childItem.setLayoutY(furthestBottom);
        } else { // odd number
          furthestTop = (furthestTop - ITEM_HEIGHT);
          childItem.setLayoutY(furthestTop);
        }
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    
  }

  private void removeAllOnScreenItems() {
    trackOnScreenModelItems.forEach( item -> {
      selectorPane.getChildren().remove(item.anchorPane);
    });
    trackOnScreenModelItems.clear();
  }

  private AnchorPane loadItem(SelectorModel modelItem) throws IOException {
    SelectorItemController controller = new SelectorItemController(this, modelItem);
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectorItem.fxml"));
    loader.setController(controller);
    
    return loader.load();
  }
  
  class SelectorItemAndAnchorPaneWrapper {
    SelectorModel item;
    AnchorPane anchorPane;
    
    public SelectorItemAndAnchorPaneWrapper(SelectorModel item, AnchorPane anchorPane) {
      this.item = item;
      this.anchorPane = anchorPane;
    }
  }
}
