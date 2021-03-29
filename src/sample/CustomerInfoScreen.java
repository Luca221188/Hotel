package sample;

import javafx.beans.value.ObservableValue;
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
import java.time.LocalDate;
import java.util.function.Predicate;

public class CustomerInfoScreen {

    @FXML
    private TextField searchRoomTextField;
    @FXML
    private TableView<Room>customersTableView;
    @FXML
    private TableColumn<Room,Integer>roomNumberColumn;
    @FXML
    private TableColumn<Room,String>customerNameColumn;
    @FXML
    private TableColumn<Room,String>customerEmailColumn;
    @FXML
    private TableColumn<Room,String>customerAddressColumn;
    @FXML
    private TableColumn<Room,String>customerPhoneColumn;
    @FXML
    private TableColumn<Room,Integer>nOfPeopleColumn;
    @FXML
    private TableColumn<Room,String>roomTypeColumn;
    @FXML
    private TableColumn<Room,Integer>durationColumn;
    @FXML
    private TableColumn<Room,String>paymentTypeColumn;
    @FXML
    private TableColumn<Room, LocalDate>startDateColumn;
    @FXML
    private TableColumn<Room,LocalDate>endDateColumn;
    @FXML
    private TableColumn<Room,Double>roomPriceColumn;
    @FXML
    private TableColumn<Room,Double>servicesColumn;
    @FXML
    private TableColumn<Room,Double>totalColumn;

    private FilteredList<Room>roomsOccupied = new FilteredList<>(RoomData.getInstance().getRooms(),null);

    private Image errorImg = new Image(getClass().getResourceAsStream("img/delete.png"));
    private Image successImg = new Image(getClass().getResourceAsStream("img/mooo.png"));

    public void initialize(){

        getTableItems();

        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        customerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> roomStringCellDataFeatures) {
                return roomStringCellDataFeatures.getValue().getRoomCustomer().customerNameProperty();
            }
        });
        customerEmailColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> roomStringCellDataFeatures) {
                return roomStringCellDataFeatures.getValue().getRoomCustomer().emailProperty();
            }
        });
        customerAddressColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> roomStringCellDataFeatures) {
                return roomStringCellDataFeatures.getValue().getRoomCustomer().addressProperty();
            }
        });
        customerPhoneColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> roomStringCellDataFeatures) {
                return roomStringCellDataFeatures.getValue().getRoomCustomer().customerPhoneProperty();
            }
        });
        nOfPeopleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Room, Integer> roomIntegerCellDataFeatures) {
                return roomIntegerCellDataFeatures.getValue().getRoomCustomer().nOfPeopleProperty().asObject();
            }
        });
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        durationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Room, Integer> roomIntegerCellDataFeatures) {
                return roomIntegerCellDataFeatures.getValue().getRoomCustomer().bookingTimeProperty().asObject();
            }
        });
        paymentTypeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> roomStringCellDataFeatures) {
                return roomStringCellDataFeatures.getValue().getRoomCustomer().paymentTypeProperty();
            }
        });

        startDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, LocalDate>, ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<Room, LocalDate> roomLocalDateCellDataFeatures) {
                return roomLocalDateCellDataFeatures.getValue().getRoomCustomer().startDateProperty();
            }
        });

        endDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, LocalDate>, ObservableValue<LocalDate>>() {
            @Override
            public ObservableValue<LocalDate> call(TableColumn.CellDataFeatures<Room, LocalDate> roomLocalDateCellDataFeatures) {
                return roomLocalDateCellDataFeatures.getValue().getRoomCustomer().endDateProperty();
            }
        });

        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        servicesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Room, Double> roomDoubleCellDataFeatures) {
                return roomDoubleCellDataFeatures.getValue().getRoomCustomer().serviceProperty().asObject();
            }
        });
        totalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Room, Double> roomDoubleCellDataFeatures) {
                return roomDoubleCellDataFeatures.getValue().getRoomCustomer().totalProperty().asObject();
            }
        });



        roomPriceColumn.setCellFactory(new Callback<TableColumn<Room, Double>, TableCell<Room, Double>>() {
            @Override
            public TableCell<Room, Double> call(TableColumn<Room, Double> roomDoubleTableColumn) {
                return new Cell();
            }
        });

        servicesColumn.setCellFactory(new Callback<TableColumn<Room, Double>, TableCell<Room, Double>>() {
            @Override
            public TableCell<Room, Double> call(TableColumn<Room, Double> roomDoubleTableColumn) {
                return new Cell();
            }
        });

        totalColumn.setCellFactory(new Callback<TableColumn<Room, Double>, TableCell<Room, Double>>() {
            @Override
            public TableCell<Room, Double> call(TableColumn<Room, Double> roomDoubleTableColumn) {
                return new Cell();
            }
        });


        customersTableView.setItems(roomsOccupied);

    }

    public void goToHomeScreen(){
        Stage currentStage = (Stage)searchRoomTextField.getScene().getWindow();
        Parent root = null;

        try{
            root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        }catch (Exception e){
            System.err.println("Screen not found!");
        }
        if(root != null){
            Scene newScene = new Scene(root,1100,620);
            currentStage.setScene(newScene);
            currentStage.show();
        }
    }

    public void searchRoom(){
        String roomToSearch = searchRoomTextField.getText().trim();

        if(roomToSearch.isEmpty()){
            showNotification("Search field is empty",errorImg);
            return;
        }else if(!roomToSearch.matches("\\d+")){
            showNotification("Type a number",errorImg);
            return;
        }

        Predicate<Room>isFound = new Predicate<Room>() {
            @Override
            public boolean test(Room room) {
                return room.getRoomNumber() == Integer.parseInt(roomToSearch) && !room.isAvailability();
            }
        };
        roomsOccupied.setPredicate(isFound);
    }

    public void clearSearch(){
        getTableItems();
        searchRoomTextField.clear();
    }

    private void getTableItems(){
        roomsOccupied.setPredicate(new Predicate<Room>() {
            @Override
            public boolean test(Room room) {
                return !room.isAvailability();
            }
        });
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

    private static class Cell extends TableCell<Room,Double>{
        private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        @Override
        protected void updateItem(Double aDouble, boolean b) {
            super.updateItem(aDouble, b);
            if(b){
                setText(null);
            }else{
                setText(numberFormat.format(aDouble));
            }
        }
    }
}
