package sample.datamodel;

import javafx.beans.property.*;


public class Room{
    private static int roomsId;

    private IntegerProperty roomId;
    private IntegerProperty roomNumber;
    private StringProperty roomType;
    private IntegerProperty capacity;
    private IntegerProperty floorNumber;
    private StringProperty phoneNumber;
    private DoubleProperty roomPrice;
    private BooleanProperty availability;

    private Customer roomCustomer;

    public Room(int roomNumber,String roomType,int capacity,int floorNumber,String phoneNumber,
                double roomPrice,boolean availability,Customer customer){
        this.roomId = new SimpleIntegerProperty(++roomsId);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.floorNumber = new SimpleIntegerProperty(floorNumber);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.roomPrice = new SimpleDoubleProperty(roomPrice);

        //this.availability = new SimpleStringProperty(availability ? "available" : "busy");
        this.availability = new SimpleBooleanProperty(availability);
        this.roomCustomer = customer;
    }


    public int getRoomId() {
        return roomId.get();
    }

    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId.set(roomId);
    }

    public int getRoomNumber() {
        return roomNumber.get();
    }

    public IntegerProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public String getRoomType() {
        return roomType.get();
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty CapacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public int getFloorNumber() {
        return floorNumber.get();
    }

    public IntegerProperty floorNumberProperty() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber.set(floorNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public double getRoomPrice() {
        return roomPrice.get();
    }

    public DoubleProperty roomPriceProperty() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice.set(roomPrice);
    }

    public boolean isAvailability() {
        return availability.get();
    }

    public BooleanProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }

    public boolean setRoomCustomer(Customer customer){
        if(availability.get()){
            this.roomCustomer = customer;
            setAvailability(false);
            return true;
        }else{
            return false;
        }
    }

    public boolean unsetRoomCustomer(){
        if(!availability.get()){
            this.roomCustomer = null;
            setAvailability(true);
            return true;
        }else{
            return false;
        }
    }

    public Customer getRoomCustomer(){
        return this.roomCustomer;
    }

    static {
        roomsId = 0;
    }

}
