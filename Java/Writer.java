import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {

    
    /** 
     * This is the constructor for the class
     */
    public Writer() {

    }

    
    /** 
     * This method writes a 2D Array to the specified file
     * @param data
     * @param filename
     */
    public static void write2dArrayToCSVFile (String[][] data, String filename) {
        
        try {
            FileWriter csvWriter = new FileWriter(filename);
            
            int col;

            for(int row = 0; row < data.length; row++) {
                for(col = 0; col < data[row].length; col++) {
                    csvWriter.append(data[row][col]);
                    if(col < data[row].length-1)    
                        csvWriter.append(",");
                }
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    /** 
     * This method writes an arraylist to the specified filename
     * @param al
     * @param filename
     */
    public static void writeArrayListToCSVFile(ArrayList<String> al, String filename) {

        Writer.write2dArrayToCSVFile(Hotel.convertArrayListTo2dArray(al, ","), filename);
        
    }

    
    /** 
     * This method updates specific data for a particular reservation in a file
     * @param filename
     * @param reservationId
     * @param col
     * @param replace
     */
    public static void updateReservation(String filename, int reservationId, int col, String replace) {

        if(reservationId != -1) { //if reservationId is not -1 the it updates information in the csv file  
            String[][] data = Reader.read2dArrayFromCSV(filename);
            int row;
            int resNum;
            for(row = 0; row < data.length; row++) {
                try {
                    resNum = Integer.parseInt(data[row][0]);
                }
                catch (Exception n) {
                    continue;
                }

                if(resNum == reservationId) {
                    data[row][col] = replace;
                    break;
                }
            }
            Writer.write2dArrayToCSVFile(data, filename);
        }
        else { //if the reservation id is -1 it will add a new row in the csv with the information provided in the parameters
            String[][] data = Reader.read2dArrayFromCSV(filename);
            int j;
            
            String[][] data1 = new String[data.length+1][data[0].length];
            for(int i = 0; i < data.length; i++) {
                for(j = 0; j < data[0].length; j++) {
                    data1[i][j] = data[i][j];
                }
            }


            String[] line = replace.split(",");
            for(int i = 0; i < line.length; i++) {
                data1[data1.length-1][i] = line[i];
            }

            Writer.write2dArrayToCSVFile(data1, filename);
        }
        
    }
    
}