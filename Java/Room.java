import java.time.LocalDate;
import java.util.HashSet;

public class Room {
    private int roomNumber;
    private String type;
    private int maxNumOfAdults;
    private int maxNumOfChilds;
    public HashSet<LocalDate> reservedDates;
    public double[] prices; 

    
    /** 
     * This is the constructor for for the room class
     * @param roomNumber
     * @param type
     * @param prices
     * @param maxNumOfAdults
     * @param maxNumOfChilds
     * @return 
     */
    public Room(int roomNumber, String type, double[] prices, int maxNumOfAdults, int maxNumOfChilds) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.prices = prices;
        this.maxNumOfAdults = maxNumOfAdults;
        this.maxNumOfChilds = maxNumOfChilds;
        reservedDates = new HashSet<LocalDate>();
    }

    /** 
     * Checks if the room types matches one another
     * @param rm
     * @return boolean
     * @throws HotelException
     */
    public boolean roomMatches(Room rm) throws HotelException{ 
        return (this.type == rm.getType());  //if the type of room matches the room entered         
    }
    
    /** 
     * This sets the room number
     * @param roomNumber
     */
    //getters and setters
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    /** 
     * This can set the max adults 
     * @param maxNumOfAdults
     */
    public void setMaxNumOfAdults(int maxNumOfAdults) {
        this.maxNumOfAdults = maxNumOfAdults;
    }

    /** 
     *This can set the max number of children
     * @param maxNumOfChilds
     */
    public void setMaxNumChilds(int maxNumOfChilds) {
        this.maxNumOfChilds = maxNumOfChilds;
    }

    
    /** 
     * Gets the prices
     * @return double[]
     */
    public double[] getPrices() {
        return prices;
    }

    
    /** 
     * Gets the room number
     * @return int
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    
    /** 
     * Gets the type of room
     * @return String
     */
    public String getType() {
        return type;
    }

    
    /** 
     * Gets the max amount of adults 
     * @return int
     */
    public int getMaxNumOfAdults() {
        return maxNumOfAdults;
    }
 
    /** 
     * Gets the max number of children
     * @return int
     */
    public int getMaxNumOfChilds(){
        return maxNumOfChilds;
    }

    /** 
     * Formats the array of prices 
     * @return String
     */
    protected String pricesArrayFormatting() {
        String s = "";
        for(int i = 0; i < prices.length; i++) {
            s += getDay(i) + " €" + prices[i] + "| ";
        }
        return s;
    }
    
    /** 
     * Gets the day of the week 
     * @param i
     * @return String
     */
    protected String getDay(int i) { //this will get the day of the week monday to sunday each day is stored in an index position
        String[] daysOfWeek = {"Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"};
        return daysOfWeek[i];
    }

    /** 
     * to string method to format the room object 
     * @return String
     */
    public String toString(){ //to string method for the room class
        return "Room No." + Integer.toString(roomNumber) + " | Type: " + type + ", Prices: "+ pricesArrayFormatting();
    }

    /** 
     * Adds reserved dates for the room 
     * @param start
     * @param finish
     */
    public void addReservedDates(LocalDate start, LocalDate finish) { //adds the dates our date hashset
        while(start.isBefore(finish)) { //start date must be before the finsih date 
            reservedDates.add(start); //adds the start date to the reserved date 
            start = start.plusDays(1); //we must loop through util we have added all days until the finish date
        }
    }

    /** 
     * Removes reserved dates for the room
     * @param start
     * @param finish
     */
    public void removeReservedDates(LocalDate start, LocalDate finish) { //this does the same as above ^^ just has a remove instead of an add
        while(start.isBefore(finish)) {
            reservedDates.remove(start);
            start = start.plusDays(1);
        }
    }

    /** 
     * Gets the price for that current day
     * @param cin
     * @return double 
     */
    public double getPriceForDay(LocalDate cin) { //gets the price of that current day 
        int day = cin.getDayOfWeek().getValue() - 1; //gets the integer value of the day of the week (Monday = 0...Sunday = 6)
        return getPrices()[day];
    }
}