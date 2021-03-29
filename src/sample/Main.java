package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.datamodel.RoomData;
import sample.datamodel.UserData;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        UserData.getUserData().readFromFile();
        RoomData.getInstance().readFromFile();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("splashscreen.fxml"));
        primaryStage.setTitle("Hello World");
        //Look at this!
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(new Scene(root, 645, 463));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        UserData.getUserData().writeToFile();
        RoomData.getInstance().writeToFile();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
