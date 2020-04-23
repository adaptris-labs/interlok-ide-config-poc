package com.adaptris.ide;

import com.adaptris.ide.node.ExternalConnection;
import com.adaptris.ide.node.ExternalConnection.ConnectionTechnology;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class Wizard
{
  @FXML
  private ChoiceBox consumers;

  @FXML
  private ChoiceBox producers;

  @FXML
  private TextField consumerDestination;

  @FXML
  private TextField producerDestination;

  @FXML
  private Button okayButton;

  @FXML
  private Button cancelButton;

  @Getter
  @Setter
  private OkayHandler onOkay;

  @FXML
  public void initialize()
  {
    ExternalConnection consumer = new ExternalConnection(ExternalConnection.ConnectionDirection.CONSUMER);
    ExternalConnection producer = new ExternalConnection(ExternalConnection.ConnectionDirection.PRODUCER);

    consumers.setItems(getTechnologies());
    consumers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      ConnectionTechnology tech = (ConnectionTechnology)newValue;
      //ObservableList<String> consumers = getConsumers(tech);
      consumerDestination.setVisible(tech != ConnectionTechnology.NULL);
      consumer.setTechnology(tech);
      // TODO: provide more settings
      consumer.setEndpoint("NULL");
      consumer.setConnectionClassName("TODO");

      checkEnableOkay();
    });
    consumerDestination.setOnKeyTyped((event) ->
    {
      consumer.setConnectionUrl(consumerDestination.getText());
      checkEnableOkay();
    });

    producers.setItems(getTechnologies());
    producers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      ConnectionTechnology tech = (ConnectionTechnology)newValue;
      //ObservableList<String> producers = getProducers(tech);
      producerDestination.setVisible(tech != ConnectionTechnology.NULL);
      producer.setTechnology(tech);
      // TODO: provide more settings
      producer.setEndpoint("NULL");
      producer.setConnectionClassName("TODO");

      checkEnableOkay();
    });

    producerDestination.setOnKeyTyped((event) ->
    {
      producer.setConnectionUrl(producerDestination.getText());
      checkEnableOkay();
    });

    okayButton.setOnMouseClicked((event) ->
    {
      ((Node)(event.getSource())).getScene().getWindow().hide();
      onOkay.onOkay(consumer, producer);
    });

    cancelButton.setOnMouseClicked((event) ->
    {
      ((Node)(event.getSource())).getScene().getWindow().hide();
    });
  }

  private void checkEnableOkay()
  {
    okayButton.setDisable(!(consumerDestination.isVisible() && !StringUtils.isEmpty(consumerDestination.getText()) &&
                            producerDestination.isVisible() && !StringUtils.isEmpty(producerDestination.getText())));
  }

  private ObservableList<ConnectionTechnology> getTechnologies()
  {
    return FXCollections.observableArrayList(ConnectionTechnology.values());
  }

  /*private ObservableList<String> getConsumers(ConnectionTechnology technology)
  {
    ObservableList<String> list = FXCollections.observableArrayList();
    switch (technology)
    {
      case SOLACE:

        break;

      default:
        list.add("TODO");
        break;
    }
    return list;
  }

  private ObservableList<String> getProducers(ConnectionTechnology technology)
  {
    ObservableList<String> list = FXCollections.observableArrayList();
    switch (technology)
    {
      case WMQ:

        break;

      default:
        list.add("TODO");
        break;
    }
    return list;
  }*/

  @FunctionalInterface
  public interface OkayHandler
  {
    void onOkay(ExternalConnection consumer, ExternalConnection producer);
  }
}
