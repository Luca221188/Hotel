package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.Notifications;
import sample.datamodel.Customer;
import sample.datamodel.Room;
import sample.datamodel.RoomData;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ReservationScreen {
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerMailField;
    @FXML
    private TextField customerDurationField;
    @FXML
    private TextField customerPeopleNumberField;
    @FXML
    private ComboBox<String>paymentBox;
    @FXML
    private Label capacityLabel;
    @FXML
    private ComboBox<Room>roomNumberCombo;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private Label roomPrice;
    @FXML
    private TextField customerServicesField;
    @FXML
    private Label totalField;

    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private Image errorImg = new Image(getClass().getResourceAsStream("img/delete.png"));
    private Image successImg = new Image(getClass().getResourceAsStream("img/mooo.png"));

    private FilteredList<Room>roomsAvailable = new FilteredList<>(RoomData.getInstance().getRooms(),null);


    public void initialize(){

        updateRoomComboBox();

        //I created an instance of Callback instead of assigning an instance of it through setCellFactory and anonymous,
        //since I need to assign it to setButtonCell() too
        Callback<ListView<Room>,ListCell<Room>> cellFactory = new Callback<ListView<Room>, ListCell<Room>>() {
            @Override
            public ListCell<Room> call(ListView<Room> roomListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(Room room, boolean b) {
                        super.updateItem(room, b);
                        if(b){
                            setText(null);
                        }else{
                            setText(String.valueOf(room.getRoomNumber()));
                        }
                    }
                };
            }
        };

        roomNumberCombo.setCellFactory(cellFactory);
        roomNumberCombo.setItems(roomsAvailable);
        roomNumberCombo.setButtonCell(cellFactory.call(null));





        roomNumberCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room room, Room t1) {
                if(t1 != null){
                    double customerServiceDouble = 0;
                    if(!customerServicesField.getText().isEmpty()) {
                        customerServiceDouble = Double.parseDouble(customerServicesField.getText());
                    }
                    roomPrice.setText(numberFormat.format(t1.getRoomPrice()));
                    capacityLabel.setText(String.valueOf(t1.getCapacity()));

                    totalField.setText(numberFormat.format(customerServiceDouble + t1.getRoomPrice()));
                }
            }
        });



        UnaryOperator<TextFormatter.Change>filter = new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                String newText = change.getControlNewText();
                if(newText.matches("-?([1-9][0-9]*)?") && !newText.equals("-")){
                    return change;
                }
                return null;
            }
        };

//        StringConverter<Integer>converter = new IntegerStringConverter(){
//            @Override
//            public Integer fromString(String s) {
//                if(s.isEmpty()){
//                    return 0;
//                }
//                return super.fromString(s);
//            }
//        };

        TextFormatter<Integer>customerServiceFormatter = new TextFormatter<>(new IntegerStringConverter(),null, filter);
        TextFormatter<Integer>customerPeopleNumberFormatter = new TextFormatter<>(new IntegerStringConverter(),null,filter);
        TextFormatter<Integer>customerDurationFormatter = new TextFormatter<>(new IntegerStringConverter(),null,filter);
        TextFormatter<Integer>customerPhoneFormatter = new TextFormatter<>(new IntegerStringConverter(),null,filter);

        customerServicesField.setTextFormatter(customerServiceFormatter);
        customerPeopleNumberField.setTextFormatter(customerPeopleNumberFormatter);
        customerDurationField.setTextFormatter(customerDurationFormatter);
        customerPhoneField.setTextFormatter(customerPhoneFormatter);

        customerServicesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1 != null){
                    String roomPriceString = roomPrice.getText();
                    Number roomPriceNumber = 0;
                    try{
                        roomPriceNumber = numberFormat.parse(roomPriceString);
                    }catch (ParseException e){
                        System.out.println(e.getMessage());
                    }
                    double roomPriceDouble = roomPriceNumber.doubleValue();

                    if(t1.isEmpty()){  //t1 may be empty
                        totalField.setText(numberFormat.format(roomPriceDouble));
                        return;
                    }
                    double customerServiceAmount = Double.parseDouble(t1);

                    totalField.setText(numberFormat.format(roomPriceDouble + customerServiceAmount));
                }
            }
        });

    }

    public void bookRoom(){
        String customerName = customerNameField.getText().trim();
        String phone = customerPhoneField.getText().trim();
        String address = customerAddressField.getText().trim();
        String email = customerMailField.getText().trim();
        String durationString = customerDurationField.getText().trim();
        String nOfPeopleString = customerPeopleNumberField.getText().trim();
        String paymentType = paymentBox.getValue();
        LocalDate startDate = startDateField.getValue();
        LocalDate endDate = endDateField.getValue();
        Room room = roomNumberCombo.getValue();

        if(customerName.isEmpty() || phone.isEmpty() || address.isEmpty() ||
        email.isEmpty() || durationString.isEmpty() || nOfPeopleString.isEmpty() ||
        paymentType == null || room == null || startDate == null || endDate == null){
            showNotification("All fields must be completed!",errorImg);
            return;
        }


        int duration = Integer.parseInt(durationString);
        int nOfPeople = Integer.parseInt(nOfPeopleString);

        if(endDate.isBefore(startDate) || endDate.isEqual(startDate)){
            showNotification("'End Date' comes before than 'Start Date' or they are equal",errorImg);
            return;
        }

        String totalString = totalField.getText();
        Number totalNumber;

        try{
            totalNumber = numberFormat.parse(totalString);
        }catch (ParseException e){
            System.err.println("Wrong format for 'Total' field");
            return;
        }
        double total = totalNumber.doubleValue();

        String customerService = customerServicesField.getText().trim();
        Customer customer = new Customer(customerName,email,address,phone,nOfPeople,duration,
                paymentType,startDate,endDate,Double.parseDouble(customerService.isEmpty() ? "0" : customerService),total);


        if(room.setRoomCustomer(customer)){
            showNotification("Booking was successful",successImg);
            updateRoomComboBox();
            customerServicesField.clear();
            totalField.setText(numberFormat.format(0));

            customerNameField.clear();
            customerPhoneField.clear();
            customerAddressField.clear();
            customerMailField.clear();
            customerDurationField.clear();
            customerPeopleNumberField.clear();
            paymentBox.setValue(null);
            startDateField.setValue(null);
            endDateField.setValue(null);
            roomPrice.setText(numberFormat.format(0));
            capacityLabel.setText("0");
        }
    }

    public void cancel(){
        Stage currentStage = (Stage)capacityLabel.getScene().getWindow();
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

    private void updateRoomComboBox(){
        Predicate<Room>isAvailable = new Predicate<Room>() {
            @Override
            public boolean test(Room room) {
                return room.isAvailability();
            }
        };
        roomsAvailable.setPredicate(isAvailable);
        roomNumberCombo.setValue(null);
    }
}
