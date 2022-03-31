public class ReceiptionInterface extends CustomerInterface {
    
    /** 
     * Calls super (customer inteface) 
     * @param hotel
     * 
     */
    public ReceiptionInterface(Hotel hotel) { //calls super for access to all methods in hotel 
        super(hotel);
    }

     /** 
     * Contains the selection Add Cancel show check-in check-out quit
     * Overides the super class 
     * 
     */
    @Override
    public void run() {
        boolean more = true;

        while(more){
            System.out.println("A)dd Reservation C)ancel Reservation S)how Reservations I) Check-In O) Check-Out Q)uit");
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

            else if(command.equals("Q")){
                more = false;
            }
        }
    }

    /** 
     * Shows the reservations in the system 
     * 
     */
    public void showReservationsInterface() {
        for(Reservation r : hotel.getReservations()){
            System.out.println(r);
        }
        System.out.println();
    }

    /** 
     * Interface to check-in a customer 
     * 
     */
    public void checkInInterface() {
        try {
            System.out.print("Enter Reservation ID: ");
            int resId = in.nextInt();
            Reservation r = getReservation(resId);
                r.CheckIn();
                System.out.println("Checked-In | Reservation No. " + resId);
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
     * Interface to check-out a customer 
     * 
     */
    public void checkOutInterface() {
        try {
            System.out.print("Enter Reservation ID: ");
            int resId = in.nextInt();
            Reservation r = getReservation(resId);
                r.CheckOut();
                System.out.println("Checked-Out | Reservation No. " + resId);
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
}





