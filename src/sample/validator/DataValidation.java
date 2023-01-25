package sample.validator;

import sample.dataclass.Person;
import sample.exceptions.InvalidDateException;
import sample.exceptions.InvalidIdException;
import sample.exceptions.InvalidNameException;
import sample.exceptions.InvalidPhoneNumberException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataValidation {
  public static ArrayList<String> studentIdList= new ArrayList<>();
  public static int selectedIdIndex;
  public static void getAllID(){
    try {
      File f = new File("MemberList.txt");
      Scanner sc = new Scanner(f);
      while(sc.hasNextLine()){
        String line = sc.nextLine();
        String[] details = line.split("\t\t");
        String id= details[1];
        studentIdList.add(id);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  public static boolean invalidID(String id){
    getAllID();
    if(studentIdList.contains(id)){
      selectedIdIndex= studentIdList.indexOf(id);
      studentIdList.clear();
      return true;
    }
    else {
      studentIdList.clear();
      return false;
    }
  }



  public static boolean isValid(String fullName, String studentId,
                                LocalDate dateOfBirth, String gender,
                                String department, String phone, String photoPath, int selectedIndex) throws Exception {
    if (fullName.equals(""))
      throw new NullPointerException("Full Name field is empty!");
    if (studentId.equals(""))
      throw new NullPointerException("Student Id field is empty!");
    if (invalidID(studentId) && selectedIdIndex!=selectedIndex)
      throw new Exception("Student id already exists!");
    if (isDateNull(dateOfBirth))
      throw new NullPointerException("Date of birth field is empty!");
    if (isDateInvalid(dateOfBirth))
      throw new InvalidDateException("Date of birth is invalid!");
    if (gender==null)
      throw new NullPointerException("Gender field is empty!");
    if (department.equals(""))
      throw new NullPointerException("Department field is empty!");
    if (phone.equals(""))
      throw new NullPointerException("Phone field is empty!");
    if (isNull(photoPath))
      throw new NullPointerException("Please insert a photo!");
    if (!isName(fullName))
      throw new InvalidNameException
              ("Invalid Name" +
              "\n*name should only contain letters" +
              "\n*must have at least 3 letters");
    if (!isID(studentId))
      throw new InvalidIdException
              ("Invalid ID" +
              "\n*student id should only contain digits" +
              "\n*must have 7 digits");
    if (!isOnlyLetters(department))
    throw new Exception("*Department should only contain letters");
    if (!isOnlyDigits(phone))
      throw new InvalidPhoneNumberException
              ("Invalid phone number" +
                      "\n*phone number should only contain digits");
    return true;
  }
  public static boolean isDateNull(LocalDate ld){
    if (ld==null)
      return true;
    else
      return false;
  }
  public static boolean isDateInvalid(LocalDate ld){
    LocalDate today= LocalDate.now();
    return ld.equals(today) || ld.isAfter(today);
  }
  public static boolean isNull(String str){
    return str == null;
  }
  public static boolean isName(String str){
    if (isOnlyLetters(str)
            && str.length()>=3)
      return true;
    else
      return false;
  }
  public static boolean isID(String str){
    if (isOnlyDigits(str) && str.length()==7)
      return true;
    else
      return false;
  }
  public static boolean isOnlyDigits(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i)))
        return false;
    }
    return true;
  }
  public static boolean isOnlyLetters(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter(str.charAt(i))|| Character.isSpaceChar(str.charAt(i)))
        return true;
    }
    return false;
  }
}
