package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserData {
    private static UserData userData = new UserData();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd - MM - yyyy");
    private ObservableList<User>data;

    private UserData(){
        this.data = FXCollections.observableArrayList();
    }

    public static UserData getUserData() {
        return userData;
    }

    public void addUser(User user){
        this.data.add(user);
    }

    public ObservableList<User> getData(){
        return data;
    }

    public  void deleteUser(User user){
        this.data.remove(user);
    }

    public void removeAllData(){
        data.clear();
    }

    public void writeToFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))){
            for(int x = 0; x < this.data.size(); x ++){
                User userToWrite = this.data.get(x);
                String formattedDate = userToWrite.getStartDate().format(dateTimeFormatter);
                bw.write(userToWrite.getUsername() + "," + userToWrite.getPassword() +
                        "," + userToWrite.getFullName() + "," + userToWrite.getAddress() + "," + userToWrite.getPhone() +
                        "," + formattedDate + "," + userToWrite.getSalary() + "," + userToWrite.getUserType() + "\n");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readFromFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("users.txt"))){
            String input;

            while((input = bufferedReader.readLine()) != null){
                String[]userDetails = input.split(",");
                LocalDate date = LocalDate.parse(userDetails[5],dateTimeFormatter);

                this.data.add(new User(userDetails[0],userDetails[1],userDetails[2],userDetails[3],
                        userDetails[4],date,Double.parseDouble(userDetails[6]),userDetails[7]));
            }
        }catch (IOException e){
            System.err.println("No file found");
        }
    }
}
