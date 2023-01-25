package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow {

  @FXML
  private TextField username;

  @FXML
  private PasswordField password;

  @FXML
  private Label errormessage;

  @FXML
  private Button signIn;

  void close(){
    Stage stage = (Stage) signIn.getScene().getWindow();
    stage.close();
  }

  @FXML
  void signInMethod(ActionEvent event) throws IOException {
    if (username.getText().equals("admin") && password.getText().equals("alpha")) {
      Stage primaryStage = new Stage();
      Parent root = FXMLLoader.load(getClass().getResource("fxml/dashBoard.fxml"));
      primaryStage.setTitle("Club Member Management System");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
      primaryStage.sizeToScene();
      close();
    } else
      errormessage.setText("Incorrect username or password");

  }
}

