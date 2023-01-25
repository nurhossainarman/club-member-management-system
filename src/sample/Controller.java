/**
 * Sample Skeleton for 'Update.fxml' Controller Class
 */
package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class Controller {

  @FXML // fx:id="w1FullNameField"
  private TextField w1FullNameField; // Value injected by FXMLLoader

  @FXML // fx:id="w1IdField"
  private TextField w1IdField; // Value injected by FXMLLoader

  @FXML // fx:id="w1DateOfBirthPicker"
  private DatePicker w1DateOfBirthPicker; // Value injected by FXMLLoader

  @FXML // fx:id="w1GenderCommonBox"
  private ComboBox<String> w1GenderCommonBox; // Value injected by FXMLLoader

  @FXML // fx:id="w1DepartmentField"
  private TextField w1DepartmentField; // Value injected by FXMLLoader

  @FXML // fx:id="w1PhoneField"
  private TextField w1PhoneField; // Value injected by FXMLLoader

  @FXML // fx:id="w1ChoosePhotoButton"
  private Button w1ChoosePhotoButton; // Value injected by FXMLLoader

  @FXML // fx:id="w1ImageView"
  private ImageView w1ImageView; // Value injected by FXMLLoader


  @FXML
  private Label eroormessage;

  private String profilePhotoPath= null;
  private ArrayList<Person> memberArrayList= null;
  private ArrayList<String> genderArrayList= null;
  private ObservableList<String> observableListGender= null;
  private int selectedIndexOfMember = -1;
  private long numberOfMembersFromFile=-1;
  @FXML
  private Button back;

  @FXML
  private Label updateMessage;
  private String w1selectedGender;

  public void setSelectedIndexOfMember(int selectedIndexOfMember) {
    this.selectedIndexOfMember = selectedIndexOfMember;
  }

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
    Stage primaryStage = (Stage) this.w1ChoosePhotoButton.getScene().getWindow();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg","*.png");
    fileChooser.getExtensionFilters().addAll(extFilter);
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

//      if(selectedIndexOfMember==-1) {
//        this.memberArrayList.add(person);
//
//        resetUI();
//        writeToFile();
//      }
//      else {
        this.memberArrayList.set(selectedIndexOfMember, person);
        writeToFile();
        this.eroormessage.setText("");
        Alert alert= new Alert(Alert.AlertType.NONE);
        alert.setTitle("Successfull");
        ButtonType goBack= new ButtonType("Go back");
        ButtonType editAgain= new ButtonType("Edit again");
        alert.getButtonTypes().addAll(goBack,editAgain);
        alert.setHeaderText("You successfully updated the member!");
        alert.setContentText("Do you want to go back to dashboard?");
        if(alert.showAndWait().get()==goBack){
          Stage primaryStage= new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("fxml/MemberList.fxml"));
          primaryStage.setTitle("Member List");
          primaryStage.setScene(new Scene(root));
          primaryStage.show();
          primaryStage.sizeToScene();
          Stage thisWindow= (Stage) back.getScene().getWindow();
          thisWindow.close();
        }



//      }
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
  void FillUI(Person person){

      this.w1FullNameField.setText(person.getFullName());
      this.w1IdField.setText(person.getStudentId());
      this.w1DepartmentField.setText(person.getDepartment());
      this.w1GenderCommonBox.setValue(person.getGender());
      this.w1PhoneField.setText(person.getPhone());
      this.w1DateOfBirthPicker.setValue(person.getDateOfBirth());
      this.profilePhotoPath= person.getPhotoPath();
      Image chosenImage= new Image("file://"+this.profilePhotoPath);
      this.w1ImageView.setImage(chosenImage);
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
  void handleFullName(ActionEvent event) {
  }
  @FXML
  void handleGender(ActionEvent event) {
    try {
      this.w1selectedGender = w1GenderCommonBox.getSelectionModel().getSelectedItem().toString();
    }
    catch (NullPointerException ne){
      System.out.println(ne.getMessage());
    }
  }
  @FXML
  void handleId(ActionEvent event) {
  }
  @FXML
  void handleDOB(ActionEvent event) {
  }
  @FXML
  void handleDepartment(ActionEvent event) {
  }
  @FXML
  void handlePhone(ActionEvent event) {

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

}
