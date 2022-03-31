import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;

public class Hotel {
    protected String name;
    protected int stars;
    private ArrayList<Room> totalRooms;
    private ArrayList<Reservation> reservations;
    public static double depositPercent = 0.15;
    private int reservationNumber = 1;
    private int lastRoomNumber = 1;
    private ArrayList<Reservation> cancelledReservations;
    private double totalMoneyReceived;
    private LocalTime hotelsCheckInTime;

    private String reservationsFilename;
    private String cancellationFilename;
    private String sevenYearReservationFile;
    private String sevenYearCancellationFile;

    public static String[][] reservationsHeadings = {{"Reservation Number", "Name", "Type", "Check-In Date",
                                                    "Nights", "Total Amount", "Deposit", "Email", "Checked-In",
                                                    "Checked-Out", "Rooms", "Breakfast Included", "Number of People"}};

    public Hotel(String name, int stars){

        totalRooms = new ArrayList<>();
        totalMoneyReceived = 0;
        hotelsCheckInTime = LocalTime.of(18, 00);
        this.name = name;
        this.stars = stars;
        String tmpName = name.replaceAll(" ", "_");

        this.reservationsFilename =  Integer.toString(stars) + "star_" + tmpName + "reservations.csv";
        if(!new File(reservationsFilename).exists()) {
            reservations = new ArrayList<>();
            reservationNumber = 1;
            Writer.write2dArrayToCSVFile(reservationsHeadings, reservationsFilename);
        }

        this.cancellationFilename = Integer.toString(stars) + "star_" + tmpName + "cancellations.csv";
        if(!new File(cancellationFilename).exists()) {
            cancelledReservations = new ArrayList<>();
            Writer.write2dArrayToCSVFile(reservationsHeadings, cancellationFilename);
        }

        this.sevenYearReservationFile ="l4AuditFiles/" + Integer.toString(stars) + "star_" + tmpName + "sevenYearReservations.csv";
        if(!new File(sevenYearReservationFile).exists()) {
            Writer.write2dArrayToCSVFile(reservationsHeadings, sevenYearReservationFile);
        }

        this.sevenYearCancellationFile ="l4AuditFiles/" + Integer.toString(stars) + "star_" + tmpName + "sevenYearCancellations.csv";
        if(!new File(sevenYearCancellationFile).exists()) {
            Writer.write2dArrayToCSVFile(reservationsHeadings, sevenYearCancellationFile);
        }
    }

    /**
     * Creates a new ArrayList and returns one room of each type.
     * @return ArrayList<Room>
     */
    public ArrayList<Room> getRoomTypes() {
        ArrayList<Room> roomTypes = new ArrayList<>();
        boolean existsAlready = false;
        for(Room r : totalRooms) {
            existsAlready = false;
            for(Room rm : roomTypes) {
                if(rm.getType() == r.getType()) {
                    existsAlready = true;
                }
            }

            if(!existsAlready) {
                roomTypes.add(r);
            }
        }
        return roomTypes;
    }

    /**
     * This method returns an array list of rooms that are of type "type"
     * @param type
     * @return ArrayList<Room>
     */
    public ArrayList<Room> getRoomsOfType(String type) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (Room r : totalRooms) {
            if(r.getType() == type) rooms.add(r);            
        }
        return rooms;
    }

    /**
     * Getter method to get the cancelled reservations.
     * @return ArrayList<Reservation>
     */
    public ArrayList<Reservation> getCancelledReservations() {
        return cancelledReservations;
    }

    /**
     * Getter method to get last room number.
     * @return int.
     */
    public int getLastRoomNum() {
        return lastRoomNumber;
    }

    /**
     * Setter method to set the cancelled reservation of the hotel
     */
    public void setCancelledReservations(ArrayList<Reservation> cancellations) {
        this.cancelledReservations = cancellations;
    }
    
    /**
     * Getter method to get the cancelled reservations filename
     */
    public String getCancelledReservationFilename() {
        return cancellationFilename;
    }

    /**
     * Getter method for the seven year reservations file filename
     */
    public String getSevenYearReservation(){
        return sevenYearReservationFile;
    }

    /**
     * Getter method for the seven year reservations file filename
     */
    public String getSevenYearCancellation(){
        return sevenYearCancellationFile;
    }

    /**
     * Getter method to get the rooms in the hotel
     * @return ArrayList<Room>
     */
    public ArrayList<Room> getTotalRooms() {
        return totalRooms;
    }

    /**
     * Method to increment the last room number
     */
    public void incrementLastRoomNum() {
        lastRoomNumber++;
    }

    /**
     * Getter method to get the name of the Hotel.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to get the number of stars for the Hotel
     * @return stars.
     */
    public int getStars() {
        return stars;
    }

    /**
     * Getter method to get all reservations for specefied Hotel object.
     * @return ArrayList<Reservation>
     */
    public ArrayList<Reservation> getReservations(){
        return reservations;
    }
    
    /**
     * Setter method to set reservations arraylist in the hotel
     * @return ArrayList<Reservation>
     */
    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Getter method to get the reservation number.
     * @return reservationNumber.
     */
    public int getResNumber() {
        return reservationNumber;
    }

    /**
     * Setter method to set the reservation number.
     * @param number
     */
    public void setResNumber(int number) {
        reservationNumber = number;
    }

    /**
     * Getter method to get reservations file filename.
     * @return ArrayList<Reservation>
     */
    public String getReservationFilename() {
        return reservationsFilename;
    }

    /**
     * Getter method to get the total amount of money recieved.
     * @return totalMoneyReceived.
     */
    public double getTotalMoneyReceived() {
        return totalMoneyReceived;
    }

    /**
     * Method get all reservations on specified date and adds them to an ArrayList.
     * @param date
     * @return ArrayList<Reservation>
     */
    public ArrayList<Reservation> getReservationsForDate(LocalDate date) {
        ArrayList<Reservation> res = new ArrayList<>();

        for (Reservation rv : reservations) {

                LocalDate cin = rv.getCheckInDate();
                LocalDate cout = cin.plusDays(rv.getNumOfNights());

                while(cin.isBefore(cout)) {
                    if(cin.equals(date)) {
                        res.add(rv);
                    }
                    cin = cin.plusDays(1);
                }
            
        }

        return res;
    }

    /**
     * Method get all cancelled reservations on specified date and adds them to an ArrayList.
     * @param date
     * @return ArrayList<Reservation>
     */
    public ArrayList<Reservation> getCancellationsForDate(LocalDate date) {
        ArrayList<Reservation> cres = new ArrayList<>();

        for (Reservation crv : cancelledReservations) {
        
            LocalDate cin = crv.getCheckInDate();
            LocalDate cout = cin.plusDays(crv.getNumOfNights());

            while(cin.isBefore(cout)) {
                if(cin.equals(date)) {
                    cres.add(crv);
                }
                cin = cin.plusDays(1);
            }
        
        }

        return cres;
    }

    /**
     * Method to add a new room to hotels (ArrayList) totalRooms with specified information e.g. prices, max people etc.
     * @param roomNumber
     * @param type
     * @param prices
     * @param maxNumOfAdult
     * @param maxNumOfChilds
     */
    public void addRoom(int roomNumber, String type, double[] prices, int maxNumOfAdult, int maxNumOfChilds){
        try {
            indexOf(roomNumber, totalRooms);
            System.out.println("Room already exists.");
        }
        catch(HotelException e) {
            if(e.getMessage() == "Room does not exist.") {
                Room r = new Room(roomNumber, type, prices, maxNumOfAdult, maxNumOfChilds);
                totalRooms.add(r);
                incrementLastRoomNum();
            }
            
        }
    }

    /**
     * Method to get a room object stored in an arraylist of rooms using the room number it has
     * @param roomNumber
     * @param rooms
     * @return Room
     * @throws HotelException
     */
    private Room indexOf(int roomNumber, ArrayList<Room> rooms) throws HotelException {
        for(Room r : rooms) {
            if(roomNumber == r.getRoomNumber()) {
                return r;
            }
        }
        throw new HotelException("Room does not exist.");
    }

    /**
     * Method checks to see if chosen room is available during specefied dates.
     * @param roomNumber
     * @param checkIn
     * @param checkOut
     * @return boolean
     */
    boolean checkRoomForAvailability(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        HashSet<LocalDate> al = indexOf(roomNumber, totalRooms).reservedDates;
        
        if(!checkIn.isBefore(checkOut)) {
            LocalDate temp = checkIn;
            checkIn = checkOut;
            checkOut = temp;
        }

        while(checkIn.isBefore(checkOut)) {
            if(al.contains(checkIn)) return false;
            checkIn = checkIn.plusDays(1);
        }
        return true;
    }

    /**
     * Method to get a room in the hotel by specifying the room number
     * @param roomNumber
     * @return Room
     */
    public Room getRoom(int roomNumber) {
        return indexOf(roomNumber, totalRooms);
    }

    /**
     * This method takes a string of room numbers (i.e. in the form "2-91-92-93")
     * And separates them using the "-" and then returns these rooms as an array list of rooms
     * @param roomNums
     * @return ArrayList<Room>
     */
    public ArrayList<Room> getRoomsFromString(String roomNums) {

        ArrayList<Room> rms = new ArrayList<>();

        if(roomNums.contains("-")) {
            String[] rooms = roomNums.split("-");

            for(String s : rooms) {
                Room r = getRoom(Integer.parseInt(s));
                rms.add(r);
            }
        }
        else {
            rms.add(getRoom(Integer.parseInt(roomNums)));
        }
        return rms;
    }

    /**
     * Method that gets the reservations from a specified file as an array list of reservations
     * @param filename
     * @return ArrayList<Reservation>
     */
    protected ArrayList<Reservation> getReservationsFromCSV(String filename) {
        ArrayList<String> resStrings = Reader.readArrayListFromCSV(filename);

        resStrings.remove(0);

        ArrayList<Reservation> reservations = new ArrayList<>();

        for(String s : resStrings) {
            reservations.add(makeReservationUsingCSVLine(s));
            
        }
        return reservations;
    }

    /**
     * This method takes a string and separates it into parts to make a reservation
     * @param line
     * @return Reservation
     */
    private Reservation makeReservationUsingCSVLine(String line) {
        String[] st = line.split(",");
        int id = Integer.parseInt(st[0]);
        String name = st[1];
        String type = st[2];
        LocalDate checkInDate = LocalDate.parse(st[3]);
        int numOfNights = Integer.parseInt(st[4]);
        String email = st[7];
        ArrayList<Room> rms = getRoomsFromString(st[10]);
        boolean breakfast = Boolean.parseBoolean(st[11]);
        int numOfPeople = Integer.parseInt(st[12]);
        return new Reservation(id, name, type, checkInDate, numOfNights, rms, email, reservationsFilename, breakfast, numOfPeople);
    }

    /**
     * Adds reservation to the reservations.
     * <p>
     * The rooms passed in are only suggestions as such... the method finds similar rooms to the rooms
     * passed in and checks if they are availible.
     * The method also updates the reservation files with the new reservation 
     * </p>
     * @param name
     * @param rooms
     * @param numOfPeople
     * @param checkInDate
     * @param numOfNights
     * @param email
     * @param breakfast
     * @return Reservation
     */
    public Reservation addReservation(String name, ArrayList<Room> rooms, int numOfPeople, 
                                      LocalDate checkInDate, int numOfNights, String email, boolean breakfast) {
        String type = "AP";
        LocalDate today = LocalDate.now();

        if(checkInDate.minusDays(14).isBefore(today)){
            type = "S";
        }
 
        ArrayList<Room> rms = new ArrayList<>();
        int i = 0;
        for(Room r : totalRooms) {
            if(i < rooms.size()) {
                if(r.roomMatches(rooms.get(i))) ;
                else continue;
                if(checkRoomForAvailability(r.getRoomNumber(), checkInDate, checkInDate.plusDays(numOfNights)));
                else continue;
                rms.add(rooms.get(i));
                i++;
            }
            else break;
        }
        
        Reservation tmp = new Reservation(reservationNumber, name, type, checkInDate, numOfNights, rms, email, reservationsFilename, breakfast, numOfPeople);
        reservations.add(tmp);
        setResNumber(getResNumber()+1);
        totalMoneyReceived += tmp.getTotalCost();

        Writer.updateReservation(reservationsFilename, -1, 0, tmp.format());
        Writer.updateReservation(sevenYearReservationFile, -1, 0, tmp.format());
        return tmp;
    }

    
    
    /** 
     * Cancels the reservation passed in
     * <p>
     * Checks if the money is refundable for the reservation, given the date and time the cancellation is attempted
     * Removes the reservation from the reservation file and adds it to the cancelled reservations file
     * </p>
     * @param rv
     * @return Reservation
     */
    public Reservation cancelReservation(Reservation rv) {
        LocalDate cin = rv.getCheckInDate();
        LocalDate cout = cin.plusDays(rv.getNumOfNights());
        for(Room r : rv.rooms) {
            r.removeReservedDates(cin, cout);
        }

        LocalDateTime refundableCutOffPoint = cin.atTime(hotelsCheckInTime).minusDays(1);

        moveReservationFromTo(rv, reservationsFilename, cancellationFilename);
        moveReservationFromTo(rv, sevenYearReservationFile, sevenYearCancellationFile);
        cancelledReservations.add(rv);
        reservations.remove(rv);

        if(rv.getType().equals("AP")) {
            System.out.println("Advanced Purchase(AP) Reservations are non-refundable. ");
        }
        else if (refundableCutOffPoint.isBefore(LocalDateTime.now())) {
            System.out.println("Your reservation is non-refundable as it is too close to check-in. ");
        }
        else {
            System.out.println("Your refund is being processed... ");
            System.out.printf("Refunded: €%.2f\n", rv.getTotalCost());
            totalMoneyReceived -= rv.getTotalCost();

        }

        return rv;
    }


    
    /** 
     * Moves a reservation from one file to the other.
     * Deletes from the from file and adds to the to file
     * @param rv
     * @param fromFile
     * @param toFile
     */
    public void moveReservationFromTo(Reservation rv, String fromFile, String toFile) {
        
        ArrayList<String> temp = new ArrayList<String>();
        temp = Reader.readArrayListFromCSV(fromFile);
        String format = rv.format();
        temp.remove(format);
        Writer.writeArrayListToCSVFile(temp, fromFile);
        if(new File(toFile).exists()) {
            Writer.updateReservation(toFile, -1, 0, rv.format());
        }
    
    }

    
    /** 
     * Converts ANY arraylist to a 2D Array using a delimeter (e.g. ",")
     * @param al
     * @param delimeter
     * @return String[][]
     */
    protected static String[][] convertArrayListTo2dArray(ArrayList<String> al, String delimeter) {
        
        int rows = al.size();
        int cols = 0;
        for(String s : al) {
            int len = s.split(delimeter).length;
            if(len > cols) {
                cols = len;
            }
        }
        int j;

        String[][] arr = new String[rows][cols];
        for(int i = 0; i < al.size(); i++) {

            String s = al.get(i);
        
            String[] line = s.split(delimeter);
            for(j = 0; j < line.length; j++) {
                arr[i][j] = line[j];
            }
        }
        return arr;
    }

    /** 
     * Sets the last reservation number using the list of reservations and list of cancelled reservations
     */
    protected void setLastReservationNum() {
        
        int max = 0;

        for(Reservation rv : reservations){
            int resNum = rv.getId();
            
            if(resNum > max) {
                max = resNum;
            }
        }
        for(Reservation c : cancelledReservations) {
            int resNum = c.getId();

            if(resNum > max) {
                max = resNum;
            }
        }
        
        reservationNumber = max + 1;
    }

    
    /** 
     * Removes all reservations from a file if the reservations check-in date is past by a specified amount of days
     * @param days
     * @param filename
     */
    public void removeReservationsFromFileAfterXDays(int days, String filename) {
        ArrayList<Reservation> res = getReservationsFromCSV(filename);
        for(Reservation rv : res) {
            if(!rv.afterDateRange(days)) {
                moveReservationFromTo(rv, filename, "nothing"); //moves the reservation from the file to nowhere
                                                                // i.e deletes it
            }
        }
    }

    /** 
     * formats a local date into a nice date format as a string
     * @param a
     * @return String
     */
    public static String ldtFormat(LocalDate a){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        return a.format(format);
    }
}