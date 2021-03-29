package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RoomData {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd - MM - yyyy");
    private static RoomData roomData = new RoomData();
    private ObservableList<Room>rooms;

    private RoomData(){
        rooms = FXCollections.observableArrayList();
    }

    public static RoomData getInstance(){
        return roomData;
    }
    public ObservableList<Room> getRooms(){
        return rooms;
    }
    public void addRoom(Room room){
        rooms.add(room);
    }

    public void writeToFile(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("rooms.txt"))){
            for(Room x : rooms){
                Customer roomCustomer = x.getRoomCustomer();

                bufferedWriter.write(x.getRoomNumber() + "," + x.getRoomType() + "," +
                        x.getCapacity() + "," + x.getFloorNumber() + "," + x.getPhoneNumber() + "," +
                        x.getRoomPrice() + "," + x.isAvailability() + ",");

                if(roomCustomer == null){
                    bufferedWriter.write(null + "\n");
                }else{
                    String formattedStartDate = roomCustomer.getStartDate().format(dateTimeFormatter);
                    String formattedEndDate = roomCustomer.getEndDate().format(dateTimeFormatter);

                    bufferedWriter.write(roomCustomer.getCustomerName() + "," + roomCustomer.getEmail() + "," +
                            roomCustomer.getAddress() + "," + roomCustomer.getCustomerPhone() + "," + roomCustomer.getnOfPeople() + "," +
                            roomCustomer.getBookingTime() + "," + roomCustomer.getPaymentType() + "," + formattedStartDate + "," +
                            formattedEndDate + "," + roomCustomer.getService() + "," + roomCustomer.getTotal() + "\n");
                }
            }
        }catch (IOException e){
            System.err.println("'rooms.txt' not found!");
        }
    }

    public  void readFromFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("rooms.txt"))){
            String input;

            while((input = bufferedReader.readLine()) != null){

                String[]roomDetails = input.split(",");
                int roomNumber = Integer.parseInt(roomDetails[0]);
                String roomType = roomDetails[1];
                int nOfPeople = Integer.parseInt(roomDetails[2]);
                int floorNumber = Integer.parseInt(roomDetails[3]);
                String phone = roomDetails[4];
                double roomPrice = Double.parseDouble(roomDetails[5]);
                boolean availability = Boolean.parseBoolean(roomDetails[6]);

                Customer customer;

                if(roomDetails[7].equals("null")){
                    customer = null;
                }else{
                    LocalDate startDate = LocalDate.parse(roomDetails[14],dateTimeFormatter);
                    LocalDate endDate = LocalDate.parse(roomDetails[15],dateTimeFormatter);

                    customer = new Customer(roomDetails[7],roomDetails[8],roomDetails[9],roomDetails[10],Integer.parseInt(roomDetails[11]),
                            Integer.parseInt(roomDetails[12]),roomDetails[13],startDate,endDate,Double.parseDouble(roomDetails[16]),Double.parseDouble(roomDetails[17]));
                }

                rooms.add(new Room(roomNumber,roomType,nOfPeople,floorNumber,phone,roomPrice,availability, customer));
            }
        }catch (IOException e){
            System.err.println("'rooms.txt' not found!");
        }
    }

//    public void writeToFile(){
//        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("rooms.dat")))){
//            for(Room room : rooms){
//                objectOutputStream.writeObject(room);
//            }
//        }catch (IOException e){
//            System.err.println("'rooms.dat' not found");
//        }
//    }
//
//    public void readFromFile(){
//        try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("rooms.dat")))){
//            boolean endOfFile = false;
//
//            while (!endOfFile){
//                try{
//                    Room room = (Room)objectInputStream.readObject();
//                    System.out.println("Room number: " + room.getRoomNumber());
//                    System.out.println("Customer: " + room.getRoomCustomer());
//
//                    rooms.add(room);
//                }catch (EOFException e){
//                    endOfFile = true;
//                }
//            }
//        }catch (InvalidClassException e){
//            System.err.println("Invalid Class exception " + e.getMessage());
//        }catch (IOException e){
//            e.printStackTrace();
//        }catch (ClassNotFoundException e){
//            System.err.println("Class not Found " + e.getMessage());
//        }
//    }
}
