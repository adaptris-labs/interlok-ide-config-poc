package com.adaptris.ide.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectorModel {

  private String iconName;
  
  private String displayText;
  
  private Object representedObject;
  
  private List<SelectorModel> children;
  
  private int depth;
  
  public SelectorModel() {
    this.setChildren(new ArrayList<>());
  }

  public String getIconName() {
    return iconName;
  }

  public void setIconName(String iconName) {
    this.iconName = iconName;
  }

  public String getDisplayText() {
    return displayText;
  }

  public void setDisplayText(String displayText) {
    this.displayText = displayText;
  }

  public List<SelectorModel> getChildren() {
    return children;
  }

  public void setChildren(List<SelectorModel> children) {
    this.children = children;
  }
  
  public void addChild(SelectorModel child) {
    this.getChildren().add(child);
  }
  
  public void addAll(SelectorModel... children ) {
    Arrays.asList(children).forEach( (child) -> {
      addChild(child);
    });
  }

  public int getDepth() {
    return depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public Object getRepresentedObject() {
    return representedObject;
  }

  public void setRepresentedObject(Object representedObject) {
    this.representedObject = representedObject;
  }
}
