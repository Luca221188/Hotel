package sample.datamodel;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Customer {
    private static int customersId;

    private IntegerProperty customerId;
    private StringProperty customerName;
    private StringProperty email;
    private StringProperty address;
    private StringProperty customerPhone;
    private IntegerProperty nOfPeople;
    private IntegerProperty bookingTime;
    private StringProperty paymentType;
    //private LocalDate startDate;
    private ObjectProperty<LocalDate>startDate;
    //private LocalDate endDate;
    private ObjectProperty<LocalDate>endDate;
    private DoubleProperty service;
    private DoubleProperty total;


    public Customer(String name,String mail,String address,String phone,int people,int time,
                    String payment,LocalDate start,LocalDate end,double service, double total){
        this.customerId = new SimpleIntegerProperty(++customersId);
        this.customerName = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(mail);
        this.address = new SimpleStringProperty(address);
        this.customerPhone = new SimpleStringProperty(phone);
        this.nOfPeople = new SimpleIntegerProperty(people);
        this.bookingTime = new SimpleIntegerProperty(time);
        this.paymentType = new SimpleStringProperty(payment);
        //this.startDate = start;
        this.startDate = new SimpleObjectProperty<>(start);
        //this.endDate = end;
        this.endDate = new SimpleObjectProperty<>(end);
        this.service = new SimpleDoubleProperty(service);
        this.total = new SimpleDoubleProperty(total);
    }

    static {
        customersId = 0;
    }



    public static int getCustomersId() {
        return customersId;
    }

    public static void setCustomersId(int customersId) {
        Customer.customersId = customersId;
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public StringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }

    public int getnOfPeople() {
        return nOfPeople.get();
    }

    public IntegerProperty nOfPeopleProperty() {
        return nOfPeople;
    }

    public void setnOfPeople(int nOfPeople) {
        this.nOfPeople.set(nOfPeople);
    }

    public int getBookingTime() {
        return bookingTime.get();
    }

    public IntegerProperty bookingTimeProperty() {
        return bookingTime;
    }

    public void setBookingTime(int bookingTime) {
        this.bookingTime.set(bookingTime);
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public StringProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    //    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }

//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }

    public double getService() {
        return service.get();
    }

    public DoubleProperty serviceProperty() {
        return service;
    }

    public void setService(double service) {
        this.service.set(service);
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }
}
