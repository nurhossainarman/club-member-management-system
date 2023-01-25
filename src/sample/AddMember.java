
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.dataclass.Person;
import sample.validator.DataValidation;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class AddMember {

  @FXML
  private Label w1FullName;

  @FXML
  private TextField w1FullNameField;

  @FXML
  private Label w1StudentIdLabel;

  @FXML
  private TextField w1IdField;

  @FXML
  private Label w1DateOfBirthLabel;

  @FXML
  private DatePicker w1DateOfBirthPicker;

  @FXML
  private Label w1GenderLabel;

  @FXML
  private ComboBox<String> w1GenderCommonBox;

  @FXML
  private Label w1DepatmentLabel;

  @FXML
  private TextField w1DepartmentField;

  @FXML
  private Label w1PhoneLabel;

  @FXML
  private TextField w1PhoneField;

  @FXML
  private Label w1PhotoLabel;

  @FXML
  private Button w1ChoosePhotoButton;

  @FXML
  private ImageView w1ImageView;

  @FXML
  private Button w1CreateButton1;

  @FXML
  private Button w1SaveButton;

  @FXML
  private Button w1ClearButton;

  @FXML
  private Label eroormessage;

  @FXML
  private Button back;

  private String profilePhotoPath= null;
  private ArrayList<Person> memberArrayList= null;


  @FXML
  private Label memberAddMessage;
  private long numberOfMembersFromFile;
  private ArrayList<String> genderArrayList;
  private ObservableList<String> observableListGender;
  private int selectedIndexOfMember=-1;

  @FXML
  void handleBackButton(ActionEvent event) throws IOException {
    Stage primaryStage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("fxml/dashBoard.fxml"));
    primaryStage.setTitle("Club Member Management System");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    primaryStage.sizeToScene();
    Stage stage = (Stage) back.getScene().getWindow();
    stage.close();
  }

  @FXML
  void handleChoosePhotoButton(ActionEvent event) {
    FileChooser fileChooser= new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg","*.png");
    fileChooser.getExtensionFilters().addAll(extFilter);
    Stage primaryStage = (Stage) this.w1ChoosePhotoButton.getScene().getWindow();
    File selectedFile= fileChooser.showOpenDialog(primaryStage);
    if (selectedFile != null)
      this.profilePhotoPath= selectedFile.toURI().getPath();

    Image chosenImage= new Image("file://"+this.profilePhotoPath);
    this.w1ImageView.setImage(chosenImage);
  }
  @FXML
  void handleSaveButton(ActionEvent event) {
//    this.selectedIndexOfMember= this.w1MemberList.getSelectionModel().getSelectedIndex();
    String fullName= this.w1FullNameField.getText();
    String studentId= this.w1IdField.getText();
    String department= this.w1DepartmentField.getText();
    String gender= this.w1GenderCommonBox.getValue();
    String phone= this.w1PhoneField.getText();
    LocalDate dateOfBirth= this.w1DateOfBirthPicker.getValue();
    String profilePhotoPath= this.profilePhotoPath;

    try {
      boolean successful=
              DataValidation.isValid(fullName,studentId,dateOfBirth,gender,
                      department,phone,profilePhotoPath,selectedIndexOfMember);
      Person person = new Person(fullName, studentId, dateOfBirth, gender,
              department, phone, profilePhotoPath);

      if(selectedIndexOfMember==-1) {
        this.memberArrayList.add(person);
        resetUI();
        writeToFile();
      }
      else {
        this.memberArrayList.set(selectedIndexOfMember, person);
        resetUI();
        writeToFile();
      }
      this.memberAddMessage.setText("Member added succesfully!");
      this.eroormessage.setText("");
    }catch (Exception ex){
      System.out.println(ex.getMessage());
      this.eroormessage.setText(ex.getMessage());
    }

  }
  void resetUI(){
    try {
      this.w1FullNameField.setText("");
      this.w1IdField.setText("");
      this.w1DepartmentField.setText("");
      this.w1GenderCommonBox.setValue("");
      this.w1PhoneField.setText("");
      this.w1DateOfBirthPicker.setValue(null);
      this.profilePhotoPath = null;
      this.w1ImageView.setImage(null);
    } catch (RuntimeException runtimeException){
      System.out.println(runtimeException.getMessage());
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
    }
    this.genderArrayList= new ArrayList<>();
    this.genderArrayList.add("Male");
    this.genderArrayList.add("Female");
    this.genderArrayList.add("Other");
    this.observableListGender= FXCollections.observableArrayList(this.genderArrayList);
    this.w1GenderCommonBox.setItems(observableListGender);

  }
  @FXML
  public void handleClearButton(ActionEvent actionEvent) {
    resetUI();
  }
}
