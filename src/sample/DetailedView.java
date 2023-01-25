/**
 * Sample Skeleton for 'detailed view.fxml' Controller Class
 */

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class DetailedView {


  @FXML
  private Label title;

  @FXML // fx:id="imageView"
  private ImageView imageView; // Value injected by FXMLLoader

  @FXML // fx:id="fn"
  private Label fn; // Value injected by FXMLLoader

  @FXML // fx:id="id"
  private Label id; // Value injected by FXMLLoader

  @FXML // fx:id="dob"
  private Label dob; // Value injected by FXMLLoader

  @FXML // fx:id="gen"
  private Label gen; // Value injected by FXMLLoader

  @FXML // fx:id="dept"
  private Label dept; // Value injected by FXMLLoader

  @FXML // fx:id="phone"
  private Label phone; // Value injected by FXMLLoader

  @FXML // fx:id="closeButton"
  private Button closeButton; // Value injected by FXMLLoader

  public ImageView getImageView() {
    return imageView;
  }

  public void setImageView(Image image) {
    this.imageView.setImage(image);
  }

  public Label getFn() {
    return fn;
  }

  public void setFn(String string) {
    this.fn.setText(string);
  }

  public Label getId() {
    return id;
  }

  public void setId(String string) {
    this.id.setText(string);
  }

  public Label getDob() {
    return dob;
  }

  public void setDob(String string) {
    this.dob.setText(string);
  }

  public Label getGen() {
    return gen;
  }

  public void setGen(String string) {
    this.gen.setText(string);
  }
  public Label getDept() {
    return dept;
  }

  public void setDept(String string) {
    this.dept.setText(string);
  }

  public Label getPhone() {
    return phone;
  }

  public void setPhone(String string) {
    this.phone.setText(string);
  }

  @FXML
  void handleCloseButton(ActionEvent event) {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
  }

  @Override
  public String toString() {
    return "DetailedView{" +
            "imageView=" + imageView +
            ", fn=" + fn +
            ", id=" + id +
            ", dob=" + dob +
            ", gen=" + gen +
            ", dept=" + dept +
            ", phone=" + phone +
            '}';
  }
}
