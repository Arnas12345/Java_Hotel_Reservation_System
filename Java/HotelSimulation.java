import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class HotelSimulation {
    HashSet<Hotel> hotels; 
    Hotel hotel;
    private Scanner scan;

    
    /** 
     * This constructor makes the hotels and lets the user select which hotel they want
     * and which interface they want. e.g. supervisor or receptionist or customer
     */
    public HotelSimulation() { 
        scan = new Scanner(System.in);

        Reader r = new Reader("l4Hotels.csv"); //this reads in the hotels information
        hotels = r.getHotels(); //gets the hotels that were read in by reader

        System.out.println("Which hotel? ");
        hotel = (Hotel)getChoice(hotels.toArray());  //shows the hotels to choose from

        try {
            whichInterface();
        } 
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /** 
     * This method displays the interfaces that the user can select from and loads the selected one
     * The user will have to input a username and password also for some of the interfaces
     * @throws IOException
     */
    public void whichInterface() throws IOException{
        boolean more = true;
        while(more){
            System.out.println("Which interface would you like?   Q)uit");
            System.out.println("1) Customer Interface");
            System.out.println("2) Receiptionist Interface");
            System.out.println("3) Supervisor Interface");
            String command = scan.next().toUpperCase(); //always sets whatever the user enters to uppercase

            if(command.equals("1")){ 
                CustomerInterface b = new CustomerInterface(hotel); //loads the customer interface 
                try {
                    b.run(); //trys to run the interface
                } catch (IOException e) {
                    //do nothing
                }
            }

            else if(command.equals("2")){ 
                HashMap<String, String> userpass; //this is a hashmap that stored our user passwords 
                userpass = new HashMap<>(); 

                            //username //password
                userpass.put("reception","apples"); //this a password for the receiption to access the interface

                System.out.print("Username: ");
                String username = scan.next(); //enter the user name
                System.out.print("Password: ");
                String password = scan.next(); //enter the password
                
                if(userpass.containsKey(username)){ //if the hashmap contains the password information allow access
                    if(userpass.get(username).equals(password)) {
                        System.out.println("Log-in sucessful. "); 
                        ReceiptionInterface b = new ReceiptionInterface(hotel); //we can now load the recieption interface 

                        try {
                            b.run(); //trys to run the interface
                        }
                        catch(Exception e) {
                            //do nothing
                        }
                    }
                }
                else System.out.println("Incorrect username/password. ");

            }

            else if (command.equals("3")){ //will do the same as the above ^^ but instead load the supervisor interface
                HashMap<String, String> userpass;
                userpass = new HashMap<>(); 

                userpass.put("supervisor","pears");

                System.out.print("Username: ");
                String username = scan.next();
                System.out.print("Password: ");
                String password = scan.next();
                if(userpass.containsKey(username)){
                    if(userpass.get(username).equals(password)) {
                        System.out.println("Log-in sucessful. ");
                        SupervisorInterface c = new SupervisorInterface(hotel);

                        try {
                            c.run();
                        }
                        catch(Exception e) {
                            //do nothing
                        }
                    }
                }
                else System.out.println("Incorrect username/password. ");
            }

            else if(command.equals("Q")){
                System.out.println("Quitting...");
                more = false;
            }
        }
    }

    
    /** 
     * This method takes an array of object and if the objects are hotels it will list them out 
     * so that a user can select the hotel they want. The method will return the selected hotel object
     * @param choices
     * @return Object
     */
    private Object getChoice(Object[] choices) { //lets us make a selections of a room or hotel in the interface method 
        while (true) {
            char c = 'A';
            for (Object choice : choices) {
                if(choice instanceof Hotel) { //if choice is an instance of hotel class 
                    Hotel h = ((Hotel)choice); //casts choice to Hotel object
                    System.out.println(c + ") "+ h.getName() + " " + h.getStars() + "-star"); //gets and prints information about the hotel
                }
                c++;
            }
            String input = scan.next();
            int n = input.toUpperCase().charAt(0) - 'A';

            if (0 <= n && n < choices.length) {
              return choices[n];
            }
        }
    }


    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        HotelSimulation hs = new HotelSimulation();
    }
}