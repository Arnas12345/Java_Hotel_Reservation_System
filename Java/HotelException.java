public class HotelException extends RuntimeException{

    
    /** 
     * Handles exeptions in the hotel
     * @param reason
     * 
     */
    public HotelException(String reason) {
        super(reason);
    }
}