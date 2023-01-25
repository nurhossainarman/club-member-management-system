package sample;

import com.sun.glass.ui.CommonDialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.dataclass.Person;

import java.io.*;
import java.security.cert.Extension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberList {

  @FXML
  private ListView<Person> listview;

  @FXML
  private TextField searchText;

  @FXML
  private Button searchButton;

  @FXML
  private Button viewMember;

  @FXML
  private Button editMember;

  @FXML
  private Label showMessage;

  @FXML
  private Button back;

  @FXML
  private Button deleteMember;

  private ArrayList<Person> memberArrayList= null;
  private ObservableList<Person> observableList= null;
  private long numberOfMembersFromFile;
  private int selectedIndex=-1;


  @FXML
  void handleViewMember(ActionEvent event) {
    this.selectedIndex = this.listview.getSelectionModel().getSelectedIndex();
    Person selectedPerson=null;
    if (this.selectedIndex >= 0){
      showMessage.setText("Select a member");
      selectedPerson = this.listview.getItems().get(selectedIndex);
      showMessage.setText("");
    }


    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/detailed view.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Detailed View");
      stage.setScene(new Scene(root1));
      DetailedView detailedView = fxmlLoader.getController();
      Image imageOfDetailedView = new Image("file://"+selectedPerson.getPhotoPath());
      detailedView.setImageView(imageOfDetailedView);
      detailedView.setFn(selectedPerson.getFullName());
      detailedView.setId(selectedPerson.getStudentId());
      detailedView.setDob(selectedPerson.getDateOfBirth().toString());
      detailedView.setGen(selectedPerson.getGender());
      detailedView.setDept(selectedPerson.getDepartment());
      detailedView.setPhone(selectedPerson.getPhone());
      stage.show();
    }catch(NullPointerException e) {
      showMessage.setText("Please select a member");
    }
//    catch (NullPointerException ne){
//      showMessage.setText("Please select a member");
//    }
    catch (IOException e) {
      e.printStackTrace();
    }


  }

  @FXML
  void handleEditMember(ActionEvent event) throws IOException {
    try {
      this.selectedIndex = this.listview.getSelectionModel().getSelectedIndex();
      Person selectedPerson = this.listview.getItems().get(selectedIndex);
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Update.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Edit Member");
      stage.setScene(new Scene(root1));
      Controller controller = fxmlLoader.getController();
      controller.FillUI(selectedPerson);
      controller.setSelectedIndexOfMember(selectedIndex);
      stage.show();
      Stage thisWindow = (Stage) editMember.getScene().getWindow();
      thisWindow.close();
    }catch(RuntimeException e) {
      showMessage.setText("Please select a member");
    }
  }

  @FXML
  void handleDeleteMember(ActionEvent event) {
    try {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmation");
      alert.setHeaderText("Delete Member");
      alert.setContentText("Are you sure you want to delete?");
      if (alert.showAndWait().get() == ButtonType.OK) {
        this.selectedIndex = this.listview.getSelectionModel().getSelectedIndex();
        Person selectedPerson = this.listview.getSelectionModel().getSelectedItem();
        this.memberArrayList.remove(selectedPerson);
        this.listview.getItems().remove(selectedIndex);
      }
      writeToFile();
    }catch(RuntimeException e) {
      showMessage.setText("Please select a member");
    }
  }

  @FXML
  void handleBackButton(ActionEvent event) throws IOException {
    Stage primaryStage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("fxml/dashBoard.fxml"));
    primaryStage.setTitle("Dash Board");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    primaryStage.sizeToScene();
    Stage stage = (Stage) back.getScene().getWindow();
    stage.close();
  }

  @FXML
  void handleSearchButton(ActionEvent event) {
    String search = searchText.getText();
    for (Person member : memberArrayList) {
      if (member.getStudentId().equals(search)) {
        selectedIndex = memberArrayList.indexOf(member);
        Person person = listview.getItems().get(selectedIndex);
        listview.getSelectionModel().select(selectedIndex);
        showMessage.setText("");
        break;
      }
      else {
        showMessage.setText("");
        showMessage.setText("No member found, please try again!");
        listview.getSelectionModel().clearSelection();
      }
    }
  }
  void writeToFile(){
    try {
      FileWriter fileWriter= new FileWriter("MemberList.txt");
      for(Person person: this.memberArrayList)
        fileWriter.write(person.getFullName()+"\t\t"+
                person.getStudentId()+"\t\t"+
                person.getDateOfBirth()+"\t\t"+
                person.getGender()+"\t\t"+
                person.getDepartment()+"\t\t"+
                person.getPhone()+"\t\t"+
                person.getPhotoPath()+
                "\n");
      fileWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  long numberOfMembersFromFile(){

    long lines = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader("MemberList.txt"))) {
      while (reader.readLine() != null) lines++;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }
  void fileReader(){
    try {
      File f = new File("MemberList.txt");
      Scanner sc = new Scanner(f);
      while(sc.hasNextLine()){
        String line = sc.nextLine();
        String[] details = line.split("\t\t");
        String fullname = details[0];
        String id= details[1];
        LocalDate dob= LocalDate.parse(details[2]);
        String gender= details[3];
        String dep= details[4];
        String phone= details[5];
        String photo= details[6];
        Person person= new Person(fullname,id,dob,gender,dep,phone,
                photo);
        this.memberArrayList.add(person);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  boolean isFileEmpty(){
    File file = new File("MemberList.txt");
    if (file.length() == 0)
      return true;
    else
      return false;
  }
  @FXML
  void initialize(){
    this.memberArrayList= new ArrayList<>();
    if (!isFileEmpty()) {
      this.numberOfMembersFromFile = numberOfMembersFromFile();
      if (this.numberOfMembersFromFile > 0)
        fileReader();
      this.observableList= FXCollections.observableArrayList(this.memberArrayList);
      this.listview.setItems(observableList);
    }



  }

}
