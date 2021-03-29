package sample;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.datamodel.User;
import sample.datamodel.UserData;

import java.time.LocalDate;
import java.time.Month;

public class SplashScreen {
    @FXML
    private ImageView image;

    public void initialize(){

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000),image);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Just consider that u do not need to create a new stage! U can just recycle
                //the previous one and just add a new scene to it, by adding to current Stage:
                //current.setScene(scene);

                Stage loginScreen = new Stage();
                Parent root = null;

                try{
                    root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
                }catch (Exception e){
                    System.err.println("Error found");
                }

                //getScene() is a method that ANY Node can carry out (ImageView, Label..)
                //To get the window, u could have used any Node object of the scene graph
                //populating the Scene. (Ex with ProgressBarIndicator)
                Stage current = (Stage)image.getScene().getWindow();
                Scene scene = new Scene(root,720,600);
                loginScreen.setScene(scene);
                loginScreen.initStyle(StageStyle.TRANSPARENT);
                current.hide();

                loginScreen.show();

            }
        });
    }
}
