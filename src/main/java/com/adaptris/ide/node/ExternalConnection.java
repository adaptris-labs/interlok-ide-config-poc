package com.adaptris.ide.node;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class ExternalConnection {
  
  public enum ConnectionTechnology {
    NULL {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-null.png"));
      }

      @Override
      public boolean canBeShared() {
        return true;
      }

      @Override
      public boolean isConnectionTechnology(String className) {
        return StringUtils.containsIgnoreCase(className, "null");
      }
    },

    JDBC {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-db.png"));
      }

      @Override
      public boolean canBeShared() {
        return true;
      }

      @Override
      public boolean isConnectionTechnology(String className) {
        return StringUtils.containsIgnoreCase(className, "jdbc");
      }
    },

    SOLACE {
      @Override
      public Image getImage() {
        return new Image(getClass().getResourceAsStream("/image/interlok-solace.png"));
      }

      @Override
      public boolean canBeShared() {
        return true;
      }

      @Override
      public boolean isConnectionTechnology(String className) {
        return StringUtils.containsIgnoreCase(className, "jms");
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

      @Override
      public boolean isConnectionTechnology(String className) {
        return false; // className.equals("");
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

      @Override
      public boolean isConnectionTechnology(String className) {
        return StringUtils.containsIgnoreCase(className, "ftp") ||
                StringUtils.containsIgnoreCase(className, "fs");
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

      @Override
      public boolean isConnectionTechnology(String className) {
        return StringUtils.containsIgnoreCase(className, "jetty") ||
                StringUtils.containsIgnoreCase(className, "http");
      }
    };
    
    public abstract Image getImage();
    
    // Do we only want one node on the network map?
    public abstract boolean canBeShared();

    public abstract boolean isConnectionTechnology(String className);
  }

  public enum ConnectionDirection {
    CONSUMER,
    PRODUCER;

    @Override
    public  String toString()
    {
      return StringUtils.capitalize(name().toLowerCase());
    }
  }

  @Getter
  @Setter
  private String connectionUrl;

  @Getter
  @Setter
  private String endpoint;

  @Getter
  @Setter
  private String connectionClassName;

  @Getter
  @Setter
  private ConnectionDirection direction;

  @Getter
  @Setter
  private ConnectionTechnology technology;

  public ExternalConnection()
  {
    // default/empty constructor
  }

  public ExternalConnection(ConnectionDirection direction)
  {
    this.direction = direction;
  }

  @Override
  public int hashCode()
  {
    int result = 0;
    result = 31 * result + connectionUrl.hashCode();
    result = 31 * result + endpoint.hashCode();
    result = 31 * result + connectionClassName.hashCode();
    result = 31 * result + direction.hashCode();
    result = 31 * result + technology.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object other)
  {
    if (!(other instanceof ExternalConnection)) {
      return false;
    }
    ExternalConnection x = (ExternalConnection)other;
    if (!connectionUrl.equals(x.connectionUrl)) {
      return false;
    }
    if (!endpoint.equals(x.endpoint)) {
      return false;
    }
    if (!connectionClassName.equals(x.connectionClassName)) {
      return false;
    }
    if (!direction.equals(x.direction)) {
      return false;
    }
    if (!technology.equals(x.technology)) {
      return false;
    }
    return true;
  }
}
