import java.time.LocalDate;
import java.util.ArrayList;

public class HotelAnalysis {

    private Hotel hotel;
    private String dir;

    
    /** 
     * Constructs HotelAnalysis object using a specified hotel
     * sets the dir for this object using hotel stars
     * @param hotel
     */
    public HotelAnalysis(Hotel hotel) {
        this.hotel = hotel;
        dir = hotel.getStars() + "Star_Analysis/";
    }

    /**
     * This method prints out all the reservations in the hotel system
     */
    public void printAllReservations() {

        System.out.println("Here are all the reservations for this hotel: ");

        for(Reservation rv : hotel.getReservations()) {
            System.out.println(rv.toString());
        }
    }

    /**
     * This method prints out all the cancelled reservations in the hotel system
     */
    public void printAllCancelledRes() {

        System.out.println("Here are all the cancelled reservations for this hotel: ");

        for(Reservation rv : hotel.getCancelledReservations()) {
            System.out.println(rv.toString());
        }
    }

    /**
     * This method prints out all the rooms in the hotel system
     */
    public void printAllRooms() {
        for(Room rm : hotel.getTotalRooms()) {
            System.out.println(rm.toString());
        }
    }

    
    /** 
     * This method returns occupancy in the hotel for a specified day
     * @param day
     * @return int
     */
    private int getOccupancyForHotelForDay(LocalDate day) {
        int total = 0;

        for(Reservation rv : hotel.getReservationsForDate(day)) {
            total += rv.getNumOfPeople();
        }
        return total;
    }

    
    /** 
     * This method returns the total cost from all reservations for a specified day
     * @param day
     * @return double
     */
    private double getTotalCostFromReservationsForDay(LocalDate day) {
        double total = 0;
        for(Reservation rv : hotel.getReservationsForDate(day)) {
            for(Room rm : rv.rooms) {
                total += rm.getPriceForDay(day);
            }   
        }
        return total;
    }

    
    /** 
     * This method returns the occupancy for a type of room for a specified day
     * @param day
     * @param type
     * @return int
     */
    private int getOccupancyForRoomTypeForDay(LocalDate day, String type) {
        
        int occupancy = 0;

        for(Room r : hotel.getRoomsOfType(type)) {
            if(r.reservedDates.contains(day)) {
                occupancy += r.getMaxNumOfAdults() + r.getMaxNumOfChilds();
            }
        }

        return occupancy;
        
    }

    
    /** 
     * This method writes the occupancy figures for the entire hotel for period of days from FROM date to TO date
     * to a csv file
     * @param from
     * @param period
     * @param to
     */
    public void writeOccupancyAnalysis(LocalDate from, int period, LocalDate to) {
        if(period == 0) {
            period = 1;
        }

        ArrayList<String> lines = new ArrayList<>();
        lines.add("Date Period, Hotel Occupancy");

        String filename = this.dir + hotel.getStars() + "_occupancyForPeriod_" + period + "days_FROMDATE_" + from + "_TODATE_" + to + ".csv";
        
        if(from.isAfter(to)) {
            LocalDate tmp = from;
            from = to;
            to = tmp;
        }

        while(from.isBefore(to)) {
            int occupancy = 0;
            for(int i = 0; i < period; i++) {
                occupancy += getOccupancyForHotelForDay(from.plusDays(i));
            }
            lines.add(Hotel.ldtFormat(from) + "-" + Hotel.ldtFormat(from.plusDays(period)) + ", Occupancy = " + Integer.toString(occupancy));
            from = from.plusDays(period);
        }

        Writer.writeArrayListToCSVFile(lines, filename);
    }

    
    /** 
     * This method writes the occupancy figures for each room type for period of days from FROM date to TO date
     * to a csv file
     * @param from
     * @param period
     * @param to
     */
    public void writeRoomAnalysis(LocalDate from, int period, LocalDate to) {
        if(period == 0) {
            period = 1;
        }

        ArrayList<String> lines = new ArrayList<>();
        lines.add("Date Period, Room Occupancy");

        String filename = this.dir + hotel.getStars() + "_occupancyForRoomTypesForPeriod_" + period + "days_FROMDATE_" + from + "_TODATE_" + to + ".csv";
        
        if(from.isAfter(to)) {
            LocalDate tmp = from;
            from = to;
            to = tmp;
        }

        ArrayList<Room> rooms = hotel.getRoomTypes();
        ArrayList<String> roomTypes = new ArrayList<>();
        for(Room r : rooms) {
            roomTypes.add(r.getType());
        }

        while(from.isBefore(to)) {
            String line = "";
            line = Hotel.ldtFormat(from) + "-" + Hotel.ldtFormat(from.plusDays(period)) + ", ";
            for(String s : roomTypes) {
                int occupancy = 0;
                for(int i = 0; i < period; i++) {
                    occupancy += getOccupancyForRoomTypeForDay(from.plusDays(i), s); 
                }
                line += s + " Occupancy = " + occupancy + ", ";
            }
            
            
            lines.add(line);
            from = from.plusDays(period);
        }

        Writer.writeArrayListToCSVFile(lines, filename);
    }

    
    /** 
     * This method writes the billing figures for the hotel for period of days from FROM date to TO date
     * to a csv file
     * and it also displays the total over the range (from-to)
     * 
     * @param from
     * @param period
     * @param to
     */
    public void writeBillingAnalysis(LocalDate from, int period, LocalDate to) {
        if(period == 0) {
            period = 1;
        }
        double totalMoneyReceived = 0;

        ArrayList<String> lines = new ArrayList<>();
        lines.add("Date Period, Total Money Received For Period");

        String filename = this.dir + hotel.getStars() + "_MoneyReceivedForPeriod_" + period + "days_FROMDATE_" + from + "_TODATE_" + to + ".csv";
        
        if(from.isAfter(to)) {
            LocalDate tmp = from;
            from = to;
            to = tmp;
        }

        while(from.isBefore(to)) {
            double total = 0;
            for(int i = 0; i < period; i++) {
                total += getTotalCostFromReservationsForDay(from.plusDays(i));
            }            
            totalMoneyReceived += total;
            lines.add(Hotel.ldtFormat(from) + "-" + Hotel.ldtFormat(from.plusDays(period)) + ", Total = €" + Double.toString(total));
            from = from.plusDays(period);
        }

        lines.add("TOTAL, €" + totalMoneyReceived);

        Writer.writeArrayListToCSVFile(lines, filename);
    }
}