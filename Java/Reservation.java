import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reservation {

    private int id;
    private String name;
    private String type;
    private LocalDate checkInDate;
    private int numOfNights;
    public ArrayList<Room> rooms;
    private double totalCost;
    private double deposit;
    private String email;
    private boolean breakfastIncluded;
    private final int breakfastCost = 10;
    private int numOfPeople;

    private LocalDateTime checkin ;
    private LocalDateTime checkout;

    private String filename;
    
    /** 
     * This constructor creates a reservation with these data fields
     * @param id
     * @param name
     * @param type
     * @param checkInDate
     * @param numOfNights
     * @param rooms
     * @param email
     * @param filename
     * @param breakfastIncluded
     * @param numOfPeople
     * @return 
     */
    public Reservation(int id, String name, String type, LocalDate checkInDate, int numOfNights, 
                        ArrayList<Room> rooms, String email, String filename, boolean breakfastIncluded, int numOfPeople) {
        this.id = id;
        this.name = name;
        this.type = type.toUpperCase();
        this.checkInDate = checkInDate;
        this.numOfNights = numOfNights;
        this.rooms = rooms;
        this.email = email;
        this.filename = filename;
        this.breakfastIncluded = breakfastIncluded;
        this.numOfPeople = numOfPeople;

        calculateTotalCost();  //call on method to initialize and calculate total cost for the reservation
        calculateDeposit();
        addReservedDatesToRooms();
    }

    
    /** 
     * This method adds reserved dates to rooms to make them unavailable
     */
    public void addReservedDatesToRooms() { //This method adds reserved dates to rooms
        for(Room r : rooms) { //for each loop that loops through the array making r a room
            r.addReservedDates(checkInDate, checkInDate.plusDays(numOfNights)); //adds from the check-in date plus the time people are staying
        }
    }

    
    /** 
     * This method checks in a reservation
     */
    public void CheckIn() { // when this method is called on FOR THIS RESERVATION, the checkin time
            checkin = LocalDateTime.now();        //and date is stored in the private data field above  
            Writer.updateReservation(filename, id, 8, ldtToString(checkin)); //makes a new row in Reservation files accourding to the parameters
    }

    /** 
     * This method checks out a reservation
     */
    public void CheckOut() {
            checkout = LocalDateTime.now();
            Writer.updateReservation(filename, id, 9, ldtToString(checkout)); 
    }

    
    /** 
     * This getter returns if breakfast is included
     * @return boolean
     */
    public boolean getBreakfast(){
        return breakfastIncluded;
    }

    /**
     * This method formats a LocalDateTime taking out the T inbetween Date and Time 
     * @param ldt
     * @return String
     */
    private String ldtToString(LocalDateTime ldt) {
        return ldt.toString().replace("T", " ");
    }
    
    /**
     * This getter returns the reservation email 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * This getter returns total number of people for a reservation 
     * @return int
     */
    public int getNumOfPeople() {
        return numOfPeople;
    }

    /** 
     * This setter sets the email for this reservation
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * This getter returns the resrvation id 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * This getter gets total cost of reservation 
     * @return double
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * This getter returns the number of the nights for the reservation 
     * @return int
     */
    public int getNumOfNights() {
        return numOfNights;
    }

    /**
     * This setter changes the number of nights for a reservation 
     * @param numOfNights
     */
    public void setNumOfNights(int numOfNights) {
        this.numOfNights = numOfNights;
    }

    /**
     * This getter returns the checkin date of the reservation 
     * @return LocalDate
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * This getter returns the reservation type (S/AP) 
     * @return String
     */
    public String getType() {
        return type;
    }
    
    /**
     * This overriden method formats the reservation into a string with all of the data fields
     * @return String
     */
    @Override
    public String toString(){
        return "No. " + id + " | Name: " + name + " | Type(S/AP): " + type + " | Check-in Date: " + checkInDate.toString() + 
                " | Nights: " + numOfNights + " | Total amount: €" + String.format("%.2f", totalCost) + 
                " | Deposit amount: €" + String.format("%.2f", deposit) +" | Email: " + email + " | Breakfast Included: " + breakfastIncluded;
    } 

    /**
     * This method is used to return a string that is used for the csv files 
     * @return String
     */
    public String format() {
        return id + "," + name+ "," +type+ "," +checkInDate.toString()+ "," +numOfNights+ "," 
                +String.format("%.2f", totalCost)+ "," +String.format("%.2f", deposit)+
               ","+email+ ","+ null +","+ null + "," + roomsNumbers() + "," + breakfastIncluded + "," + numOfPeople; 
    }

    /** 
     * This method applies a discount to the total cost
     * @param percentage
     */
    protected void applyDiscount(double percentage) { //allows us to apply a discount of according percentage to the total price
        if(percentage >= 1) { 
            percentage /= 100;
        }//this if statement allows the whole number percentage to be changed to .2 decimal places eg.50 => 0.50

        double reduction = totalCost * percentage;
        totalCost -= reduction;

        Writer.updateReservation(filename, id, 5, String.format("%.2f", totalCost));
        calculateDeposit();
        Writer.updateReservation(filename, id, 6, String.format("%.2f", deposit));
    }

    /**
     * This method returns the room numbers that you have eg(1-2-3) 
     * @return String
     */
    private String roomsNumbers() {
        String s = "";
        for(int i = 0; i < rooms.size(); i++) {
            
            s += Integer.toString(rooms.get(i).getRoomNumber());

            if(i < rooms.size()-1) {
                s += "-";
            }
        }
        return s;
    }

    /**
     * This method returns the total cost using how many rooms reserved and for how many nights and the cost of the rooms and if breakfast included
     */
    private void calculateTotalCost()  { //this method will give us the total cost of the customers stay
        totalCost = 0;
        LocalDate cin = checkInDate;
        LocalDate cout = cin.plusDays(numOfNights);

        while(cin.isBefore(cout)) { //while check-in date is before check out date
            for(Room r : rooms) {
                totalCost += r.getPriceForDay(cin);
                if(breakfastIncluded) totalCost += breakfastCost;
            }
            cin = cin.plusDays(1);  //then move onto the next day
        }
        
        if(type == "AP") applyDiscount(0.05); //if the type is set to AP apply the discount
    }

    /**
     * Calculates the deposit cost of the reservation
     */
    private void calculateDeposit() {
        deposit = totalCost * Hotel.depositPercent;
    }

    
    
    /** 
     * @param days
     * @return boolean
     */
    public boolean afterDateRange(int days){
        return checkInDate.plusDays(days).isAfter(LocalDate.now());
    }
}
