import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerInterface {
    protected Scanner in;
    protected Hotel hotel;
    

    
    /** 
     * Constructor for the customer interface
     * @param hotel
     * @return 
     */
    public CustomerInterface(Hotel hotel) {
        in = new Scanner(System.in);
        this.hotel = hotel;
    }
    
    /** 
     * Contains the selection Add Cancel and Quit for the Interface(runs the interface)
     * @throws IOException
     */
    public void run() throws IOException {
        boolean more = true;

        while(more){
            System.out.println("A)dd Reservation C)ancel Reservation P)ay Q)uit");
            String command = in.next().toUpperCase();

            if(command.equals("A")) {
                addReservationInterface();
            }
                
            else if(command.equals("C")){
                cancelReservationInterface();
            }

            else if(command.equals("P")){
                payReservationInterface();
            }
            
            else if(command.equals("Q")){
                more = false;
            }
        }
    }

    /**
     * Simulation of paying for a reservation;
     */
    private void payReservationInterface() {
        try{
            System.out.print("Enter Reservation ID: ");
            int resId = in.nextInt();
            Reservation r = getReservation(resId);

            System.out.println("Total Cost: €" + r.getTotalCost());
            System.out.println("Would you like to pay?(Y/N)");
            String decision = in.next().toUpperCase();
            if(decision.equals("Y")){
                System.out.println("Paid: €" + r.getTotalCost());
            }
        }
        catch(HotelException h) {
            System.out.println(h.getMessage());
        }
        catch(Exception e) {
            
        }
        finally {
            System.out.println();
        }
    }

    /**
     * Cancel the given reservation interface
     */
    public void cancelReservationInterface() {
        try {
            System.out.print("Enter Reservation ID: ");
            int resId = in.nextInt();
            Reservation r = getReservation(resId);
            if(!r.equals(null)) {
                hotel.cancelReservation(r);
                System.out.println("Cancelled Reservation: " + r.getId());
            }
        }
        catch(HotelException h) {
            System.out.println(h.getMessage());
        }
        catch(Exception e) {
            
        }
        finally {
            System.out.println();
        }
    }

    /** 
     * Gets the reservation in the system by the id number
     * @param id
     * @return Reservation
     * @throws HotelException
     */
    protected Reservation getReservation(int id) throws HotelException {
        for(Reservation r : hotel.getReservations()){
            if(r.getId() == id){
                return r;
            }
        }
        throw new HotelException("Reservation does not exist. ");
    }

    /** 
     * Interface to add a reservation
     */
    public void addReservationInterface() {
        ArrayList<Room> roomsWanted = new ArrayList<>();
        LocalDate l;

        try {
            System.out.println("What check in date (DD/MM/YYYY): ");
            l = localDateInterface();      

            System.out.println("How many nights? ");
            int numNights = in.nextInt();

            System.out.println("How many Adults? ");
            int numAdults = in.nextInt();

            System.out.println("How many Children? ");
            int numChilds = in.nextInt();

            int numOfPeople = numAdults + numChilds;

            System.out.println("What rooms would you like: ");
            while(!(numAdults <= 0 && numChilds <= 0)) {  //the customer will keep add rooms until each person has a bed
                System.out.println("number of adults: " + numAdults + " number of children: " + numChilds);
                Room r1 = (Room)getChoice(hotel.getRoomTypes().toArray());

                for(Room r : hotel.getRoomsOfType(r1.getType())){
                    if(hotel.checkRoomForAvailability(r.getRoomNumber(), l, l.plusDays(numNights))) {
                        roomsWanted.add(r);
                        numAdults -= r1.getMaxNumOfAdults();
                        numChilds -= r1.getMaxNumOfChilds();
                        r1.addReservedDates(l, l.plusDays(numNights)); 
                        break;
                    }
                }  
            }
            for(Room rm : roomsWanted) {
                System.out.println(rm);
            }

            System.out.print("Firstname: ");
            String name = in.next();

            System.out.print("Lastname: ");
            name = name + " " + in.next();
        
            System.out.println(name);
            
            System.out.print("Email Address: ");
            String email = in.next();

            System.out.println(("Would you like breakfast? (Y/N)"));
            String breakfast= in.next().toUpperCase();
            boolean breakfastType = false;
            if(breakfast.equals("Y")){
                breakfastType = true;
            }
            System.out.println();

            Reservation rv = hotel.addReservation(name, roomsWanted, numOfPeople , l, numNights, email, breakfastType);

            System.out.println(rv);
        }

        catch (Exception e) {
            System.out.println("Incorrect information entered. ");
        }
        finally {
            System.out.println();
        }
    }
    
    /** 
     * This method takes an array of the things a user will be able to select from
     * Returns an object that the user selected 
     * @param choices
     * @return Object
     */
    private Object getChoice(Object[] choices) {
        while (true) {
            char c = 'A';
            for (Object choice : choices) {
                if(choice instanceof Room) {
                    Room r = ((Room)choice);
                    System.out.println(c + ") " + r.getType() + " Max Adults: " +
                    r.getMaxNumOfAdults() + " Max Children: " + r.getMaxNumOfChilds());
                }
                c++;
            }
            String input = in.next();
            int n = input.toUpperCase().charAt(0) - 'A';

            if (0 <= n && n < choices.length) {
              return choices[n];
            }
        }
    }   

    /** 
     * Format for the input of the date the user must enter
     * @return LocalDate
     */
    public LocalDate localDateInterface() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        String f = in.next();
        return LocalDate.parse(f, format);
    }
}   



