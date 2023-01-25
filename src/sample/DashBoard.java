package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoard {

  @FXML
  private Button memberList;

  @FXML
  private Button addMember;

  @FXML
  private Button back;


  @FXML
  void handleBackButton(ActionEvent event) throws IOException {
    Stage primaryStage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("fxml/loginWindow.fxml"));
    primaryStage.setTitle("Dash Board");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    primaryStage.sizeToScene();
    Stage stage = (Stage) back.getScene().getWindow();
    stage.close();
  }

  @FXML
  void handleAddMember(ActionEvent event) throws IOException {
    Stage primaryStage= new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("fxml/addMember.fxml"));
    primaryStage.setTitle("Manage Members");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    primaryStage.sizeToScene();

    Stage stage = (Stage) addMember.getScene().getWindow();
    stage.close();

  }

  @FXML
  void handleMemberList(ActionEvent event) throws IOException {
    Stage primaryStage= new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("fxml/MemberList.fxml"));
    primaryStage.setTitle("Member List");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    primaryStage.sizeToScene();

    Stage stage = (Stage) addMember.getScene().getWindow();
    stage.close();

  }

}
