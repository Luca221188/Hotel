package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sample.datamodel.User;
import sample.datamodel.UserData;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/*
A couple of words about PropertyValueFactory!
If the referenced class is in a named module, then it must be reflectively accessible to the javafx.base module.
A class is reflectively accessible if the module opens the containing package to at least the javafx.base module.
Otherwise the call(TableColumn.CellDataFeatures) method will log a warning and return null.
For example, if the Person class is in the com.foo package in the foo.app module, the module-info.java might look like this:

module foo.app {
    opens com.foo to javafx.base;
}
 */
public class EmployeesScreen {
    @FXML
    private TableView<User>employeesTableView;
    @FXML
    private TableColumn<User,String>idColumn;
    @FXML
    private TableColumn<User,String>userNameColumn;
    @FXML
    private TableColumn<User,String>passwordColumn;
    @FXML
    private TableColumn<User,String>fullNameColumn;
    @FXML
    private TableColumn<User,String>addressColumn;
    @FXML
    private TableColumn<User,String>phoneColumn;
    @FXML
    private TableColumn<User,String>startDateColumn;
    @FXML
    private TableColumn<User,Double>salaryColumn;
    @FXML
    private TableColumn<User,String>userTypeColumn;

    private FilteredList<User>filteredUsers;
    private Predicate<User>allUsers;

    @FXML
    private TextField searchTextField;



    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField salaryTextField;
    @FXML
    private ComboBox<String>userTypeCombo;

    private Image errorImg = new Image(getClass().getResourceAsStream("img/delete.png"));
    private Image successImg = new Image(getClass().getResourceAsStream("img/mooo.png"));


    public void initialize(){

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        filteredUsers = new FilteredList<>(UserData.getUserData().getData(),allUsers);

        //employeesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));

        salaryColumn.setCellFactory(new Callback<TableColumn<User, Double>, TableCell<User, Double>>() {
            @Override
            public TableCell<User, Double> call(TableColumn<User, Double> userDoubleTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Double aDouble, boolean b) {
                        super.updateItem(aDouble, b);
                        if(b){
                            setText(null);
                        }else{
                            setText(numberFormat.format(aDouble));
                        }
                    }
                };
            }
        });


        employeesTableView.setItems(filteredUsers);


        allUsers = new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return true;
            }
        };

        employeesTableView.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> userTableView) {
                TableRow<User> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //if(mouseEvent.getClickCount() == 2){
                        User user = employeesTableView.getSelectionModel().getSelectedItem();
                        if(user != null){
                            usernameTextField.setText(user.getUsername());
                            passwordTextField.setText(user.getPassword());
                            fullNameTextField.setText(user.getFullName());
                            addressTextField.setText(user.getAddress());
                            phoneTextField.setText(user.getPhone());
                            datePicker.setValue(user.getStartDate());
                            salaryTextField.setText(String.valueOf(user.getSalary()));
                            userTypeCombo.setValue(user.getUserType());
                        }
                        //}
                    }
                });
                return row;
            }
        });
    }

    public void searchUser(){

        if(searchTextField.getText().trim().isEmpty()){
            showNotification("Id search field is empty",errorImg);
            return;
        }

        Predicate<User>userFound = new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getUserId().equals(searchTextField.getText().trim());
            }
        };

        //String userId = searchTextField.getText().trim();

        filteredUsers.setPredicate(userFound);

        if(filteredUsers.isEmpty()) {
            showNotification("User id not found",errorImg);
        }
    }

    public void updateUser(){
        User userToUpdate = employeesTableView.getSelectionModel().getSelectedItem();

        if(userToUpdate == null){
            showNotification("Select which user to update",errorImg);
            return;
        }

//        String username = usernameTextField.getText().trim();
//        String password = passwordTextField.getText().trim();
//        String fullName = fullNameTextField.getText().trim();
//        String address = addressTextField.getText().trim();
//        String phone = phoneTextField.getText().trim();
//        LocalDate date = datePicker.getValue();   //cannot be null
//        String salaryString = salaryTextField.getText().trim();
//        String userType = userTypeCombo.getValue();  //cannot be null
//
//
//        if(username.isEmpty() || password.isEmpty() || fullName.isEmpty() ||
//        address.isEmpty() || phone.isEmpty() || salaryString.isEmpty()) {
//            showNotification("One or more fields are empty", errorImg);
//            return;
//        }
//

//        if(!salaryString.matches("-?\\d+(.\\d+)?") && !phone.matches("\\d+")){
//            showNotification("Salary and Phone must contain only digits",errorImg);
//            salaryTextField.clear();
//            phoneTextField.clear();
//            return;
//        }else if(!salaryString.matches("-?\\d+(.\\d+)?")){
//            showNotification("Salary must contain only digits",errorImg);
//            salaryTextField.clear();
//            return;
//        }else if(!phone.matches("\\d+")){
//            showNotification("Phone must contain digits only",errorImg);
//            phoneTextField.clear();
//            return;
//        }


        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String fullName = fullNameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        LocalDate date = datePicker.getValue();   //cannot be null
        String salaryString = salaryTextField.getText().trim();
        String userType = userTypeCombo.getValue();   //cannot be null

        if(checkFields(username,password,fullName,address,date,userType,salaryString,phone)){
            return;
        }

        boolean sameName = false;
        boolean samePwd = false;

        for(User x : UserData.getUserData().getData()){
            if(!x.getUserId().equals(userToUpdate.getUserId())){
                if (x.getUsername().equals(username) && x.getPassword().equals(password)) {
                    showNotification("UserName and Password already existing", errorImg);
                    usernameTextField.clear();
                    passwordTextField.clear();
                    return;
                }else if(x.getUsername().equals(username)){
                    sameName = true;
                }else if(x.getPassword().equals(password)){
                    samePwd = true;
                }
            }
            if (sameName && samePwd) {
                showNotification("UserName and Password already existing", errorImg);
                usernameTextField.clear();
                passwordTextField.clear();
                return;
            }
        }

        if(sameName){
            showNotification("UserName already existing",errorImg);
            usernameTextField.clear();
            return;
        }
        if(samePwd){
            showNotification("Password already existing",errorImg);
            passwordTextField.clear();
            return;
        }

        userToUpdate.setUsername(username);
        userToUpdate.setPassword(password);
        userToUpdate.setFullName(fullName);
        userToUpdate.setAddress(address);
        userToUpdate.setPhone(phone);
        userToUpdate.setStartDate(date);
        userToUpdate.setSalary(Double.parseDouble(salaryString));
        userToUpdate.setUserType(userType);


        showNotification("Update successful",successImg);

        clearFields();

    }

    public void addNewUser(){

        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String fullName = fullNameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        String phone = phoneTextField.getText().trim();
        LocalDate date = datePicker.getValue();             //can be null
        String salaryString = salaryTextField.getText().trim();
        String userType = userTypeCombo.getValue();         //can be null

        if(checkFields(username,password,fullName,address,date,userType,salaryString,phone)){
            return;
        }

        boolean sameName = false;
        boolean samePwd = false;
        for(User x : UserData.getUserData().getData()){
            if (x.getUsername().equals(username) && x.getPassword().equals(password)) {
                showNotification("UserName and Password already existing", errorImg);
                usernameTextField.clear();
                passwordTextField.clear();
                return;
            }else if(x.getUsername().equals(username)){
                sameName = true;
            }else if(x.getPassword().equals(password)){
                samePwd = true;
            }
            if (sameName && samePwd) {
                showNotification("UserName and Password already existing", errorImg);
                usernameTextField.clear();
                passwordTextField.clear();
                return;
            }
        }

        if(sameName){
        showNotification("UserName already existing",errorImg);
        usernameTextField.clear();
        return;
        }
        if(samePwd){
        showNotification("Password already existing",errorImg);
        passwordTextField.clear();
        return;
        }

        UserData.getUserData().addUser(new User(username,password,fullName,address,phone,date,Double.parseDouble(salaryString),userType));
        showNotification("New User added successfully",successImg);

        clearFields();
    }


    public void deleteUser(){
        User userToDelete = employeesTableView.getSelectionModel().getSelectedItem();

        if(userToDelete == null){
            showNotification("No user selected from the table",errorImg);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete user");
        alert.setContentText("You are about to delete " + userToDelete.getUsername() + ".\nDo you want to proceed?");

        Optional<ButtonType>result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            UserData.getUserData().deleteUser(userToDelete);
            showNotification("User deleted successfully",successImg);
            clearFields();
        }
    }

    public void clearFields(){
        usernameTextField.clear();
        passwordTextField.clear();
        fullNameTextField.clear();
        addressTextField.clear();
        phoneTextField.clear();
        datePicker.setValue(null);
        salaryTextField.clear();
        userTypeCombo.setValue(null);

        employeesTableView.getSelectionModel().select(null);


        filteredUsers.setPredicate(allUsers);
        searchTextField.clear();
    }

    public void clearTable(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Clear Table");
        alert.setContentText("You are about to destroy all users. Do you want to proceed?");

        Optional<ButtonType>result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            UserData.getUserData().removeAllData();
        }
    }

    public void goBackToHomeScreen(){
        Stage currentStage = (Stage) addressTextField.getScene().getWindow();
        Parent root = null;
        //String sceneToOpen;
        //String userType = LogIn.getInstance("-","-").getUserType();
        //sceneToOpen = userType.equals("admin") ? "adminScreen.fxml" : "homeScreen.fxml";
        try{
            root = FXMLLoader.load(getClass().getResource("adminScreen.fxml"));
        }catch (Exception e){
            System.err.println("Screen not found");
        }
        if(root != null){
            Scene newScene = new Scene(root,1100,620);
            currentStage.setScene(newScene);
        }
    }

    private boolean checkFields(String userName,String pwd,String fullName,String address,LocalDate date,String userType,String salary,String phone){

        if(userName.isEmpty() || pwd.isEmpty() || fullName.isEmpty() ||
                address.isEmpty() || phone.isEmpty() || date == null || salary.isEmpty() ||
                userType == null){
            showNotification("One or more fields are empty",errorImg);
            return true;
        }

        if((!salary.matches("-?\\d+(.\\d+)?") || salary.contains("-")) && !phone.matches("\\d+")){
            showNotification("Salary and Phone must contain only digits",errorImg);
            salaryTextField.clear();
            phoneTextField.clear();
            return true;
        }else if(!salary.matches("-?\\d+(.\\d+)?") || salary.contains("-")){
            showNotification("Salary must contain only digits",errorImg);
            salaryTextField.clear();
            return true;
        }else if(!phone.matches("\\d+")){
            showNotification("Phone must contain digits only",errorImg);
            phoneTextField.clear();
            return true;
        }

        return false;
    }


    private void showNotification(String message, Image image){
        String title = image == successImg ? "Success" : "Error";

        Notifications notifications = Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_LEFT)
                .graphic(new ImageView(image));
        notifications.darkStyle();
        notifications.show();
    }
}

