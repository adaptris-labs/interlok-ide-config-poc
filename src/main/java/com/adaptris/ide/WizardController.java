package com.adaptris.ide;

import com.adaptris.ide.node.ExternalConnection;
import com.adaptris.ide.node.ExternalConnection.ConnectionTechnology;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class WizardController
{
  @Getter
  @FXML
  private Label endPointLabel;

  @FXML
  private ChoiceBox endPoint;

  @FXML
  private Label label1;

  @FXML
  private TextField textField1;

  @FXML
  private Label label2;

  @FXML
  private TextField textField2;

  @FXML
  private Label label3;

  @FXML
  private TextField textField3;

  @FXML
  private Button cancelButton;

  @FXML
  private Button nextButton;

  @Getter
  @Setter
  private WizardNextButtonHandler onNext;

  @Getter
  private ExternalConnection connection;

  public WizardController(ExternalConnection.ConnectionDirection direction)
  {
    connection = new ExternalConnection(direction);
  }

  @FXML
  public void initialize()
  {
    endPointLabel.setText(connection.getDirection().toString());

    endPoint.setItems(getTechnologies());
    endPoint.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      ConnectionTechnology tech = (ConnectionTechnology)newValue;
      connection.setTechnology(tech);
      updateLabels();
      checkEnableOkay();
    });

    textFieldHandler(textField1);
    textFieldHandler(textField2);
    textFieldHandler(textField3);

    nextButton.setOnMouseClicked((event) ->
    {
      // TODO get the values from TextField's and stick it somewhere...
      updateConnectionDetails();
      ((Node)(event.getSource())).getScene().getWindow().hide();
      if (onNext != null)
      {
        onNext.onNext(connection);
      }
    });

    cancelButton.setOnMouseClicked((event) ->
    {
      ((Node)(event.getSource())).getScene().getWindow().hide();
    });
  }

  private void textFieldHandler(TextField textField)
  {
    textField.setOnKeyTyped((event) ->
    {
      checkEnableOkay();
    });
  }

  private void updateLabels()
  {
    switch (connection.getTechnology())
    {
      case WMQ:
      case SOLACE:
        label1.setText("Topic / Queue");
        label2.setText("Username");
        label3.setText("Password");
        textField1.setVisible(true);
        textField2.setVisible(true);
        textField3.setVisible(true);
        break;

      case FILESYSTEM:
        label1.setText("Path");
        label2.setText("");
        label3.setText("");
        textField1.setVisible(true);
        textField2.setVisible(false);
        textField3.setVisible(false);
        break;

      case HTTP:
      case JDBC:
        label1.setText("URL");
        label2.setText("Username");
        label3.setText("Password");
        textField1.setVisible(true);
        textField2.setVisible(true);
        textField3.setVisible(true);
        break;

      default:
        label1.setText("");
        label2.setText("");
        label3.setText("");
        textField1.setVisible(false);
        textField2.setVisible(false);
        textField3.setVisible(false);
        break;
    }
  }

  private void checkEnableOkay()
  {
    boolean disabled = true;
    switch (connection.getTechnology())
    {
      case WMQ:
      case SOLACE:
      case JDBC:
        disabled = StringUtils.isEmpty(textField1.getText()) || StringUtils.isEmpty(textField2.getText()) || StringUtils.isEmpty(textField3.getText());
        break;

      case FILESYSTEM:
      case HTTP:
        disabled = StringUtils.isEmpty(textField1.getText());
        break;

      case NULL:
        disabled = false;
        break;

      default:
        break;
    }
    nextButton.setDisable(disabled);
  }

  private void updateConnectionDetails()
  {
    switch (connection.getTechnology())
    {
      case NULL:
        connection.setConnectionUrl("NULL");
        break;

      default:
        connection.setConnectionUrl(textField1.getText());
        // TODO: provide more settings for each consumer/producer type
        connection.setEndpoint("NULL");
        connection.setConnectionClassName("TODO");
        break;
    }
  }

  private static ObservableList<ConnectionTechnology> getTechnologies()
  {
    return FXCollections.observableArrayList(ConnectionTechnology.values());
  }

}
