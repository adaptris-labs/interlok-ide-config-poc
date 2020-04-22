package com.adaptris.ide;

import com.adaptris.ide.node.ExternalConnection.ConnectionTechnology;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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

  @FXML
  public void initialize() {

    consumers.setItems(getTechnologies());
    consumers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      ConnectionTechnology tech = (ConnectionTechnology)newValue;
      //ObservableList<String> consumers = getConsumers(tech);
      consumerDestination.setVisible(tech != ConnectionTechnology.NULL);
      // TODO: provide more settings
      checkEnableOkay();
    });
    consumerDestination.setOnKeyTyped((event) ->
    {
      checkEnableOkay();
    });

    producers.setItems(getTechnologies());
    producers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      ConnectionTechnology tech = (ConnectionTechnology)newValue;
      //ObservableList<String> producers = getProducers(tech);
      producerDestination.setVisible(tech != ConnectionTechnology.NULL);
      // TODO: provide more settings
      checkEnableOkay();
    });
    producerDestination.setOnKeyTyped((event) ->
    {
      checkEnableOkay();
    });

    okayButton.setOnMouseClicked((event) ->
    {
      // TODO: save the new workflow
      ((Node)(event.getSource())).getScene().getWindow().hide();
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

}
