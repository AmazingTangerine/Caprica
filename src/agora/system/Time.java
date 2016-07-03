package agora.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Time {

    public static String getDate(){
        
        DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd" );
        
        Date date = new Date();

        return dateFormat.format( date );
        
    }
    
    public static String getTime(){
        
        DateFormat dateFormat = new SimpleDateFormat( "HH:mm:ss" );
        
        Date date = new Date();

        return dateFormat.format( date );
        
    }
    
    public static String getDateTime(){
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        Date date = new Date();

        return dateFormat.format( date );
        
    }
    
}
