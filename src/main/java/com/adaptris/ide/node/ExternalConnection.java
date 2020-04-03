package com.adaptris.ide.node;

import javafx.scene.image.Image;

public class ExternalConnection {
  
  public static enum ConnectionTechnology {
    SOLACE {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-solace.png"));
      }

      @Override
      public boolean canBeShared() {
        return true;
      }
    },
    
    WMQ {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-webspheremq.png"));
      }

      @Override
      public boolean canBeShared() {
        return true;
      }
    },
    
    FILESYSTEM {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-oftp.png"));
      }

      @Override
      public boolean canBeShared() {
        return false;
      }
    },
    
    HTTP {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-apache-http.png"));
      }

      @Override
      public boolean canBeShared() {
        return false;
      }
    };
    
    public abstract Image getImage();
    
    // Do we only want one node on the network map?
    public abstract boolean canBeShared();
  }

  private enum ConnectionDirection {
    CONSUME,
    
    PRODUCE;
  }
  
  private String connectionUrl;
  
  private String endpoint;
  
  private String connectionClassName;
  
  private ConnectionDirection direction;
  
  private ConnectionTechnology technology;
  
  public ExternalConnection() {
    
  }

  public String getConnectionUrl() {
    return connectionUrl;
  }

  public void setConnectionUrl(String connectionUrl) {
    this.connectionUrl = connectionUrl;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getConnectionClassName() {
    return connectionClassName;
  }

  public void setConnectionClassName(String connectionClassName) {
    this.connectionClassName = connectionClassName;
  }

  public ConnectionDirection getDirection() {
    return direction;
  }

  public void setDirection(ConnectionDirection direction) {
    this.direction = direction;
  }

  public ConnectionTechnology getTechnology() {
    return technology;
  }

  public void setTechnology(ConnectionTechnology technology) {
    this.technology = technology;
  }
  
}
