package sample;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sample.datamodel.Room;
import sample.datamodel.RoomData;

import java.text.NumberFormat;
import java.util.Optional;
import java.util.function.Predicate;

public class RoomScreen {
    @FXML
    private TableView<Room>roomTableView;

    @FXML
    private TableColumn<Room,Integer>roomIdColumn;
    @FXML
    private TableColumn<Room,String>roomTypeColumn;
    @FXML
    private TableColumn<Room,Integer>roomNumberColumn;
    @FXML
    private TableColumn<Room,Integer>capacityColumn;
    @FXML
    private TableColumn<Room,Integer>floorNumberColumn;
    @FXML
    private TableColumn<Room,String>roomPhoneColumn;
    @FXML
    private TableColumn<Room,Double>roomPriceColumn;
    @FXML
    private TableColumn<Room,Boolean>availabilityColumn;

    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    @FXML
    private CheckBox busyCheckBox;
    @FXML
    private CheckBox availableCheckBox;

    private FilteredList<Room>filteredRooms = new FilteredList<>(RoomData.getInstance().getRooms(),null);
    private Predicate<Room>availableRooms;
    private Predicate<Room>busyRooms;

    @FXML
    private TextField searchTextField;

    private Image errorImg = new Image(getClass().getResourceAsStream("img/delete.png"));
    private Image successImg = new Image(getClass().getResourceAsStream("img/mooo.png"));

    //private Predicate<Room>foundRoom;

    public void initialize(){

//        busyRooms = new Predicate<Room>() {
//            @Override
//            public boolean test(Room room) {
//                return !room.isAvailability();
//            }
//        };

//        availableRooms = new Predicate<Room>() {
//            @Override
//            public boolean test(Room room) {
//                return room.isAvailability();
//            }
//        };
        busyRooms = x -> !x.isAvailability();
        availableRooms = Room::isAvailability;



        //    foundRoom = new Predicate<Room>() {
//            @Override
//            public boolean test(Room room) {
//                return room.getRoomNumber() == Integer.parseInt(searchTextField.getText());
//            }
//        };


        roomTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        floorNumberColumn.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
        roomPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("RoomPrice"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        roomPriceColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Room, Double> call(TableColumn<Room, Double> roomDoubleTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Double aDouble, boolean b) {
                        super.updateItem(aDouble, b);
                        if (b) {
                            setText(null);
                        } else {
                            setText(numberFormat.format(aDouble));
                        }
                    }
                };
            }
        });

//        availabilityColumn.setCellFactory(new Callback<TableColumn<Room, Boolean>, TableCell<Room, Boolean>>() {
//            @Override
//            public TableCell<Room, Boolean> call(TableColumn<Room, Boolean> roomBooleanTableColumn) {
//                return new TableCell<>(){
//                    @Override
//                    protected void updateItem(Boolean aBoolean, boolean b){
//                        super.updateItem(aBoolean, b);
//                        if(b){
//                            setText(null);
//                        }else{
//                            setText(aBoolean ? "Available" : "Busy");
//                        }
//                    }
//                };
//            }
//        });

        availabilityColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Room, Boolean> call(TableColumn<Room, Boolean> roomBooleanTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Boolean aBoolean, boolean b) {
                        super.updateItem(aBoolean, b);
                        if (b) {
                            setText(null);
                        } else {
                            setText(aBoolean ? "Available" : "Busy");
                        }
                    }
                };
            }
        });

        roomTableView.setItems(filteredRooms);
    }

    private void showBusyRooms(){
        filteredRooms.setPredicate(busyRooms);
    }
    private void showAvailableRooms(){
        filteredRooms.setPredicate(availableRooms);
    }
    private void showAllRooms(){
        filteredRooms.setPredicate(null);
    }
    public void showRooms(){
        if(busyCheckBox.isSelected() && availableCheckBox.isSelected() ||
        !busyCheckBox.isSelected() && !availableCheckBox.isSelected()){
            showAllRooms();
        }else if(busyCheckBox.isSelected()){
            showBusyRooms();
        }else if(availableCheckBox.isSelected()){
            showAvailableRooms();
        }
    }

    public void findRoom(){
        String roomNumberToSearch = searchTextField.getText().trim();

        if(roomNumberToSearch.isEmpty()){
            showNotification("Search Field is empty",errorImg);
            return;
        }else if(!roomNumberToSearch.matches("\\d+")){
            showNotification("Type a number",errorImg);
            searchTextField.clear();
            return;
        }
        //------------------------------------------------------------------------
        //  Do not get confounded by the difference between placing the Predicate
        //  within findRoom() and within the intialize(). In the first case, the
        //  Predicate is updated every time u click the Button 'Search', but in
        //  the second case, the Predicate is called only ONCE (initialize() is
        //   called only once!). The difference does not matter in a situation
        //  like Tim's example, but here it is different
        //------------------------------------------------------------------------
//        Predicate<Room>foundRoom = new Predicate<Room>() {
//            @Override
//            public boolean test(Room room) {
//                return room.getRoomNumber() == Integer.parseInt(roomNumberToSearch);
//            }
//        };
        Predicate<Room>foundRoom = x -> x.getRoomNumber() == Integer.parseInt(roomNumberToSearch);

        filteredRooms.setPredicate(foundRoom);
        searchTextField.clear();

        if(filteredRooms.isEmpty()){
            showNotification("No room found",errorImg);
        }else{
            roomTableView.getSelectionModel().selectFirst();
        }
    }

    public void makeRoomAvailable(){
        Room room = roomTableView.getSelectionModel().getSelectedItem();

        if(room == null){
            showNotification("No room selected",errorImg);
            return;
        }
        if(room.isAvailability()){
            showNotification(room.getRoomNumber() + " is already available",errorImg);
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Make Available" );
            alert.setContentText("Are u sure to make room " + room.getRoomNumber() + " available?");

            Optional<ButtonType>result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                room.unsetRoomCustomer();
                showNotification("Room " + room.getRoomNumber() + " is now available",successImg);

                filteredRooms.setPredicate(null);
            }
        }
    }

    public void goBackToHomeScreen() {
        Stage currentStage = (Stage) searchTextField.getScene().getWindow();
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        } catch (Exception e) {
            System.err.println("Screen not found");
        }
        if(root != null){
            Scene newScene = new Scene(root, 1100, 620);
            currentStage.setScene(newScene);
        }

    }

    public void close(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("Are u sure u want to quit?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
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

