package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class AdminScreen {
    @FXML
    private Pane homePane;
    @FXML
    private Pane employeesPane;
    @FXML
    private Pane customersPane;
    @FXML
    private Pane logoutPane;
    @FXML
    private Pane exitPane;

    public void setOnMouseEntered(MouseEvent a) {
        if (a.getSource() == homePane) {
            homePane.setStyle("-fx-background-color:rgba(230, 172, 85, 0.6); -fx-background-radius:6;-fx-background-insets:-3");
        } else if (a.getSource() == employeesPane) {
            employeesPane.setStyle("-fx-background-color:rgba(230, 172, 85, 0.6); -fx-background-radius:6; -fx-background-insets:-3");
        } else if (a.getSource() == customersPane) {
            customersPane.setStyle("-fx-background-color:rgba(230, 172, 85, 0.6); -fx-background-radius:6; -fx-background-insets:-3");
        } else if (a.getSource() == exitPane) {
            exitPane.setStyle("-fx-background-color:rgba(230, 172, 85, 0.6); -fx-background-radius:6;-fx-background-insets:-3");
        } else if (a.getSource() == logoutPane) {
            logoutPane.setStyle("-fx-background-color:rgba(230, 172, 85, 0.6); -fx-background-radius:6;-fx-background-insets:-3");
        }
    }

    public void setOnMouseExit(MouseEvent a) {
        if (a.getSource() == homePane) {
            homePane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.6); -fx-background-radius:6");
        } else if (a.getSource() == employeesPane) {
            employeesPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.6); -fx-background-radius:6");
        } else if (a.getSource() == customersPane) {
            customersPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.6); -fx-background-radius:6");
        } else if (a.getSource() == exitPane) {
            exitPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.6); -fx-background-radius:6");
        } else if (a.getSource() == logoutPane) {
            logoutPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.6); -fx-background-radius:6");
        }
    }

    public void onMouseClicked(MouseEvent e){
        String stageToOpen;
        Stage newStage = new Stage();
        Parent root = null;

        if(e.getSource() == homePane){
            stageToOpen = "homeScreen.fxml";
        }else if(e.getSource() == employeesPane){
            stageToOpen = "employeesScreen.fxml";
        }else if(e.getSource() == customersPane){
            stageToOpen = "customersScreen.fxml";
        }else{
            stageToOpen = "";
        }

        if(!stageToOpen.equals("")) {
            try {
                root = FXMLLoader.load(getClass().getResource(stageToOpen));
            } catch (Exception err) {
                System.err.println("Screen Not Found");
            }
            Stage currentStage = (Stage) homePane.getScene().getWindow();
            if (root != null) {
                newStage.setScene(new Scene(root, 1100, 620));
                newStage.initStyle(StageStyle.TRANSPARENT);
                currentStage.hide();
                newStage.show();
            }
        }
    }

    public void exitApplication(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Quit Application");
        alert.setContentText("You are about to shut down the Application. Are u sure?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    public void logOut(){
        Stage newStage = new Stage();
        Parent root = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Log out current user");
        alert.setContentText("You are about to log out of your account. Are u sure?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            try{
                root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            }catch (Exception err){
                System.err.println("Screen Not Found");
            }
            Stage currentStage = (Stage)homePane.getScene().getWindow();
            if(root != null){
                newStage.setScene(new Scene(root,720,600));
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.show();
                currentStage.hide();
            }
        }
    }
}
