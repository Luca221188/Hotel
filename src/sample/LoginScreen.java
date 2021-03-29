package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sample.datamodel.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

/*
Interesting point: in Pane u set "opacity" to 0.77, and it applied to all of the children!
 */
public class LoginScreen {
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane loginAnchorPane;

    public void initialize(){

        //createUsers();
        //createRooms();

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String userName = userNameField.getText().trim();
                String password = passwordField.getText().trim();
                String userTypeFxml;
                Image image = new Image(getClass().getResourceAsStream("img/delete.png"));

                if(userName.isEmpty() || password.isEmpty()){

                    Notifications notifications = Notifications.create()
                            .title("Error")
                            .text("Username or Password field are empty")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BOTTOM_LEFT)
                            .graphic(new ImageView(image));
                    notifications.darkStyle();
                    notifications.show();
                    return;
                }

                for(User x : UserData.getUserData().getData()){
                    if(x.getUsername().equals(userName) && x.getPassword().equals(password)){

                        userTypeFxml = x.getUserType().equals("admin") ? "adminScreen.fxml" : "homeScreen.fxml";

                        Stage allUsersScreen = new Stage();
                        Parent root = null;

                        try{
                            root = FXMLLoader.load(getClass().getResource(userTypeFxml));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        Stage oldStage = (Stage)userNameField.getScene().getWindow();
                        Scene homeScene = new Scene(root,1100,620);
                        allUsersScreen.setScene(homeScene);
                        allUsersScreen.initStyle(StageStyle.TRANSPARENT);
                        oldStage.hide();
                        allUsersScreen.show();
                        return;
                    }
                }
                Notifications notifications = Notifications.create()
                        .title("Error")
                        .text("User not found")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_LEFT)
                        .graphic(new ImageView(image));
                notifications.darkStyle();
                notifications.show();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quit");
                alert.setContentText("Are u sure u want to quit?");

                Optional<ButtonType>result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK){
                    Platform.exit();
                }
            }
        });
    }




    private void createUsers(){
        User user1 = new User("Luque","Blappa01","Luca Beltrame",
                "Madeira road 18","7564093592", LocalDate.of(2019, Month.NOVEMBER,22),
                2000,"admin");
        User user2 = new User("Dado","Blappa02","Davide Beltrame",
                "Westover road 5","4545454644", LocalDate.of(2019, Month.JUNE,18),
                2000,"admin");
        User user3 = new User("Titti","Blappa03","Judith Ramos",
                "Madeira road 18","567687867", LocalDate.of(2019, Month.FEBRUARY,10),
                2000,"user");
        User user4 = new User("Albany","Blappa04","Alberto Tomba",
                "Via Palazzolo 4","3488628495", LocalDate.of(2019, Month.APRIL,2),
                2000,"user");
        User user5 = new User("Porko","Blappa05","Michele Paron",
                "Via Spessa 33","5645456767", LocalDate.of(2019, Month.DECEMBER,4),
                2000,"user");
        User user6 = new User("a","a","Luca Beltrame","Madeira Road 18",
                "98989898",LocalDate.of(2019,Month.NOVEMBER,22),2000,"admin");

        UserData.getUserData().addUser(user1);
        UserData.getUserData().addUser(user2);
        UserData.getUserData().addUser(user3);
        UserData.getUserData().addUser(user4);
        UserData.getUserData().addUser(user5);
        UserData.getUserData().addUser(user6);
    }

    private void createRooms(){

        Customer customer1 = new Customer("Barosio Manfredi","baro@gmail.com","Via RottoInCulo",
                "3847384347",4,5,"Visa",LocalDate.of(1999,Month.NOVEMBER,22),
                LocalDate.of(2000,Month.AUGUST,23),23,55);
        Room room1 = new Room(10,"A",4,0,"043169273",100,false,customer1);
        Room room2 = new Room(11,"B",3,0,"0431697919",90,true,null);
        Room room3 = new Room(12,"A",2,1,"043169548",70,true,null);
        Room room4 = new Room(13,"B",1,2,"0431697986",80,true,null);
        Room room5 = new Room(14,"B",4,3,"043169023",100,true,null);

        RoomData.getInstance().addRoom(room1);
        RoomData.getInstance().addRoom(room2);
        RoomData.getInstance().addRoom(room3);
        RoomData.getInstance().addRoom(room4);
        RoomData.getInstance().addRoom(room5);

    }

}
