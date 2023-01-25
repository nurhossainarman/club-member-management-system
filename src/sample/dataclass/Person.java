package sample.dataclass;
import java.time.LocalDate;
public class Person{
  private String fullName;
  private String studentId;
  private LocalDate dateOfBirth;
  private String gender;
  private String department;
  private String phone;
  private String photoPath;
  public Person(String fullName, String studentId, LocalDate dateOfBirth,
                String gender, String department, String phone, String photoPath) {
    this.fullName = fullName;
    this.studentId = studentId;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.department = department;
    this.phone = phone;
    this.photoPath= photoPath;
  }

  public String getFullName() {
    return fullName;
  }



  public String getStudentId() {
    return studentId;
  }



  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }



  public String getGender() {
    return gender;
  }


  public String getDepartment() {
    return department;
  }


  public String getPhone() {
    return phone;
  }


  public String getPhotoPath() {
    return photoPath;
  }


  @Override
  public String toString() {
    return
            fullName+"_"+studentId;
  }

}
