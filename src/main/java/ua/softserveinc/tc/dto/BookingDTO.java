package ua.softserveinc.tc.dto;

/**
 * Created by Петришак on 14.05.2016.
 */
public class BookingDTO  implements BaseDTO {

        String start;
        Long id;


   public BookingDTO(String start){
        this.start=start;

    }
    public BookingDTO(String start, Long id){
        this.start=start;
        this.id=id;
    }

}
