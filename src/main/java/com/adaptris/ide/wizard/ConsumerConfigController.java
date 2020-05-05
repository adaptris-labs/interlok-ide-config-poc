package com.adaptris.ide.wizard;

import java.awt.MouseInfo;
import java.awt.Point;

import com.adaptris.ide.node.ExternalConnection;
import com.adaptris.ide.node.ExternalNodeController;
import com.adaptris.ide.node.InterlokNodeController;
import com.adaptris.ide.node.ExternalConnection.ConnectionDirection;
import com.adaptris.ide.selector.AdaptrisEndpointStaticModelBuilder;
import com.adaptris.ide.selector.SelectorController;
import com.adaptris.ide.selector.SelectorItemChosenListener;
import com.adaptris.ide.selector.SelectorModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConsumerConfigController {

  @FXML
  private ImageView connectionImage;
  
  @FXML
  private AnchorPane consumerActualPane;
  
  @FXML
  private RadioButton copyHeadersButton;
  
  @FXML
  private RadioButton multiPayloadButton;
  
  @FXML
  private ImageView acceptImage;
  
  @FXML
  private ImageView grapheneImage;
  
  public ConsumerConfigController() {
    
  }
  
  @FXML
  public void initialize() {
    connectionImage.setImage(new Image(getClass().getResourceAsStream("/image/connection.png")));
    acceptImage.setImage(new Image(getClass().getResourceAsStream("/image/accept.png")));
    grapheneImage.setImage(new Image(getClass().getResourceAsStream("/image/graphene.png")));
    
    connectionImage.setOnMouseClicked( event -> {
      launchConnectionSelector();
    });
    
    acceptImage.setOnMouseClicked( event -> {
      doSaveConsumer();
    });
  }

  private void launchConnectionSelector() {
    Parent root;
    try {
      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);

      SelectorModel rootNode = new SelectorModel();
      rootNode.setDisplayText("Consume Connection");

      SelectorController controller = new SelectorController(stage, new AdaptrisEndpointStaticModelBuilder().build(rootNode), itemSelected -> {
        doConnectionSelected(itemSelected, stage);
      });

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/adaptris/ide/selector/SelectorPane.fxml"));
      loader.setController(controller);

      root = loader.load();
      Scene scene = new Scene(root);
      scene.getStylesheets().add("/test.css");
      stage.setScene(scene);
      
      Point location = MouseInfo.getPointerInfo().getLocation();
      
      stage.initStyle(StageStyle.TRANSPARENT);
      scene.setFill(Color.TRANSPARENT);
      
      stage.show();
      stage.setX(location.getX());
      stage.setY(location.getY() - (scene.getHeight() / 2));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void doConnectionSelected(SelectorModel itemSelected, Stage stage) {
    try {
      ExternalConnection connection = (ExternalConnection) itemSelected.getRepresentedObject();
      connection.setDirection(ConnectionDirection.CONSUMER);
      connection.setEndpoint("/my/rest/*");
      connection.setConnectionUrl("http://localhost:8080/");
      
      EndpointController controller = new EndpointController(connection);
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Endpoint.fxml"));
      loader.setController(controller);
      
      consumerActualPane.getChildren().add(loader.load());
      
      connectionImage.setVisible(false);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void doSaveConsumer() {
    // TODO Auto-generated method stub
    
  }
  
}
