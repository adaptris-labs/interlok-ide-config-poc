package com.adaptris.ide.wizard;

import com.adaptris.ide.node.ExternalConnection;

import javafx.scene.Parent;

public enum WizardPageLoader {

  CONSUMER_TECH_SELECT {
    @Override
    public Parent loadPage(ExternalConnection connection) {
      // TODO Auto-generated method stub
      return null;
    }
  },
  
  CONSUMER_TECH_SELECT_EXTENDED {
    @Override
    public Parent loadPage(ExternalConnection connection) {
      // TODO Auto-generated method stub
      return null;
    }
  },
  
  CONSUMER_CONNECTION_DETAILS {
    @Override
    public Parent loadPage(ExternalConnection connection) {
      // TODO Auto-generated method stub
      return null;
    }
  },
  
  CONSUMER_FINAL {
    @Override
    public Parent loadPage(ExternalConnection connection) {
      // TODO Auto-generated method stub
      return null;
    }
  };
  
  private static final String CONSUMER_TECH_SELECT_FXML = "";
  
  private static final String CONSUMER_TECH_SELECT_EXTENDED_FXML = "";
  
  private static final String CONSUMER_CONNECTION_DETAILS_FXML = "";
  
  private static final String CONSUMER_FINAL_FXML = "";
  
  public abstract Parent loadPage(ExternalConnection connection);
  
}
