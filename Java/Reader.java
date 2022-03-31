import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Reader {
    private ArrayList<Integer> rowsWithStar;
    private int numOfHotels;
    private HashSet<Hotel> hotels;

    
    /** 
     * Getter method for hotels
     * @return HashSet<Hotel>
     */
    public HashSet<Hotel> getHotels() { 
        return hotels;
    }

    
    /** 
     * Constructor to make the hotels from given csv file
     * @param csvFileLocation
     */
    public Reader(String csvFileLocation) {
        String[][] l4Hotels = Reader.read2dArrayFromCSV(csvFileLocation); //makes a string array of all the info in the cvs file
        rowsWithStar = rowsThatContainTheWord(l4Hotels, "star"); //uses our method to get row contains to get the stars and puts it in the arraylist
        numOfHotels = rowsWithStar.size()-1; //gets the number of hotels
        hotels = new HashSet<>();

        int i = 0;
        while(i < numOfHotels) { //while loop to add the hotels to the hotels obj
            Hotel hotel = makeAHotel(l4Hotels, rowsWithStar.get(i), rowsWithStar.get(i+1));
            String rFilename = hotel.getReservationFilename();
            String cFilename = hotel.getCancelledReservationFilename();
            hotel.setReservations(hotel.getReservationsFromCSV(rFilename));
            hotel.setCancelledReservations(hotel.getReservationsFromCSV(cFilename));
            hotel.setLastReservationNum();            
            hotels.add(hotel);
            i++;
        }
    }



    
    /** 
     * This method reads data from csv file as a 2D Array
     * @param filename 
     * @return String[][]
     */
    protected static String[][] read2dArrayFromCSV(String filename) { //makes the information in the csv into a 2d array 
        
        return Hotel.convertArrayListTo2dArray(readArrayListFromCSV(filename), ",");

    }

    
    /** 
     * This method reads from a csv file as an arraylist of strings
     * @param filename
     * @return ArrayList<String>
     */
    protected static ArrayList<String> readArrayListFromCSV(String filename) { //reads each row from csv stores in arraylist
        ArrayList<String> al = new ArrayList<>();
        String line = ""; //this is a string to hold the row information 

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) { //reads line into the string line aslomg as file is not null
                al.add(line); //adds each line into the arraylist 
            }
            
        } 
        catch (IOException e) {
            e.getMessage();
        }
    
        return al; //returns the arraylist
    }

    
    /** 
     * This method makes a hotel object given a 2D Array list containing hotel information(i.e. rooms info)
     * and given the row that the hotel info begins at and ends at in that 2D Array
     * @param csvFile
     * @param startAtRow
     * @param endAtRow
     * @return Hotel
     */
    private Hotel makeAHotel(String[][] csvFile, int startAtRow, int endAtRow){

        int stars = Character.getNumericValue(csvFile[startAtRow][0].charAt(0)); //changes character to an integer exp. 4-star hotel => takes out 4
        Hotel ht = new Hotel("", stars); //makes a new hotel with the stars from the csv

        for(int row = startAtRow; row < endAtRow; row++) {
            
            double[] prices = new double[7];

            // System.out.println(Character.getNumericValue(csvFile[row][4].charAt(0)));
            int maxAdults = Character.getNumericValue(csvFile[row][4].charAt(0)); //switches the string value in the csv for number of adults to a integer value
            int maxChilds = Character.getNumericValue(csvFile[row][4].charAt(2)); //^^ same as above nut for the children


            for(int p = 5; p < csvFile[row].length; p++) {
                prices[p-5] = Integer.parseInt(csvFile[row][p]);
            } //gets all the prices of each room

            for(int i = 0; i < Integer.parseInt(csvFile[row][2]); i++) {
                ht.addRoom(ht.getLastRoomNum(), csvFile[row][1], prices, maxAdults, maxChilds);
            } //adds all rooms to the hotel
            
        }

        return ht;
    
    }

    
    /** 
     * This method goes through a 2D Array and returns the rows that contain the specified word
     * <p>
     * Note: It also will add the last row of the 2D Array to the arraylist being returned.
     * This is so that the arraylist will have the last row so we know at what row the hotel information ends.
     * </p> 
     * @param c
     * @param word
     * @return ArrayList<Integer>
     */
    private ArrayList<Integer> rowsThatContainTheWord(String[][] c, String word) { //searches for the word in the the given array list 
        int j;
        ArrayList<Integer> rows = new ArrayList<>();

        for(int i = 0; i < c.length; i++) { 
            for(j = 0; j < c[i].length; j++) { //simple loop through the array
                if(c[i][j] != null) { //if the postion is not null
                    if(c[i][j].contains(word)) { //and if the postion contains the string word 
                        // System.out.println(c[i][j]);
                        rows.add(i); //add the the word to the arraylist 
                    }
                }
            }
        }
        rows.add(c.length); //this adds the last row of the 2d array so it knows where to stop searching 

        return rows; //then returns the arraylist of the rows that contain the word
    }


}