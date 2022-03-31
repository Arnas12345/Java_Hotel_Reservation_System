import java.time.LocalDate;

public class SupervisorInterface extends ReceiptionInterface{

    
    /** 
     * This constructor creates a interface for a hotel being passed in. Calls on super on receptionInterface
     * @param hotel
     */
    public SupervisorInterface(Hotel hotel) { //constructor calls the super class hotel to give us access to all the methods 
        super(hotel);
    }

    
    /** 
     * This overriden method from the super class changes the interface
     */
    @Override
    public void run() { //overides the run method in the reservation class 
        boolean more = true;

        while(more){
            System.out.println("A)dd Reservation C)ancel Reservations S)how Reservations I) Check-In O) Check-Out D) Apply Discount Y) Hotel Analysis R)Remove From System Q)uit");
            String command = in.next().toUpperCase();

            if(command.equals("A")) {
                addReservationInterface();
            }
                
            else if(command.equals("C")){
                cancelReservationInterface();
            }
            
            else if(command.equals("S")){
                showReservationsInterface();
            }

            else if(command.equals("I")){
                checkInInterface();
            }

            else if(command.equals("O")){
                checkOutInterface();
            }

            else if(command.equals("D")){
                applyDiscountInterface();
            }

            else if(command.equals("Y")){
                hotelAnalysisInterface();
            }

            else if(command.equals("R")){
                removeFromSystem();
            }

            else if(command.equals("Q")){
                more = false;
            }
        }
    }

    /**
     * This method removes reservations from the system
     */
    private void removeFromSystem() {
        System.out.println("Which System would you like to remove?");
        System.out.println("1) Current Files Reservations");
        System.out.println("2) Audit Files Reservations");
        String command = in.next().toUpperCase();

        if(command.equals("1")){
            hotel.removeReservationsFromFileAfterXDays(30, hotel.getReservationFilename());
            hotel.removeReservationsFromFileAfterXDays(30, hotel.getCancelledReservationFilename());
        }

        else if(command.equals("2")){
            hotel.removeReservationsFromFileAfterXDays(2555, hotel.getSevenYearReservation());
            hotel.removeReservationsFromFileAfterXDays(2555, hotel.getSevenYearCancellation());
        }
    }

    /**
     * This method is used to apply a further discount on a reservation price
     */
    public void applyDiscountInterface() {
        try {
            System.out.print("Enter Reservation ID: ");
            int resId = in.nextInt();
            Reservation r = getReservation(resId);
            System.out.print("Enter Discount Percentage: ");
            int Discount = in.nextInt();
            System.out.println("Total Cost before Discount applied: €"+r.getTotalCost());
            r.applyDiscount(Discount);
            System.out.println("Total Cost after Discount applied: €"+r.getTotalCost());
            
        }
        catch (HotelException h) {
            System.out.println(h.getMessage());
        }
        catch(Exception e) {

        }
        finally {
            System.out.println();
        }
    }

    /**
     * This method creates an interface for the hotel anaylsis which is called above
     */
    public void hotelAnalysisInterface() {
        HotelAnalysis hotelAnaylsis = new HotelAnalysis(hotel);
        try {
            System.out.println("1) Hotel Occupancy Analysis");
            System.out.println("2) Room Occupancy Analysis");
            System.out.println("3) Reservation Billing Analysis");
            System.out.println("4) Total Money Received");
            String command = in.next();
            if(command.equals("1")) {
                LocalDate from;
                LocalDate to;
                
                System.out.println("What date would you like to start analysis from (DD/MM/YYYY): ");
                from = localDateInterface();

                System.out.println("What date would you like to finish the analysis (DD/MM/YYYY): ");
                to = localDateInterface();

                int period = displayPeriodsInterface();
                hotelAnaylsis.writeOccupancyAnalysis(from,period,to);
                
            }
            else if(command.equals("2")) {
                LocalDate from;
                LocalDate to;
                
                System.out.println("What date would you like to start analysis from (DD/MM/YYYY): ");
                from = localDateInterface();

                System.out.println("What date would you like to finish the analysis (DD/MM/YYYY): ");
                to = localDateInterface();

                int period = displayPeriodsInterface();
                hotelAnaylsis.writeRoomAnalysis(from,period,to);
            }
            else if(command.equals("3")) {
                LocalDate from;
                LocalDate to;

                System.out.println("What date would you like to start analysis from (DD/MM/YYYY): ");
                from = localDateInterface();

                System.out.println("What date would you like to finish the analysis (DD/MM/YYYY): ");
                to = localDateInterface();

                int period = displayPeriodsInterface();
                hotelAnaylsis.writeBillingAnalysis(from, period, to);
            }
            else if(command.equals("4")) {
                System.out.println("Total Money Received: €" + hotel.getTotalMoneyReceived());
            }
        }
        catch(Exception e) {
        }
}

    /**
     * This method returns how many days to use for the period time for analysis 
     * @return int
     */
    private int displayPeriodsInterface() {
        System.out.println("A) Daily");
        System.out.println("B) Weekly");
        System.out.println("C) Monthly");
        System.out.println("D) Other");

        String ch = in.next().toUpperCase();
        if(ch.equals("D")) {
            System.out.println("Enter number of days:");
            return in.nextInt();
        }

        else if(ch.equals("A")) {
            return 1;
        }

        else if(ch.equals("B")) {
            return 7;
        }

        else if(ch.equals("C")) {
            return 30;
        }

        else {
            return 0;
        }
    }
}