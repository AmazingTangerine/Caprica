package caprica.datatypes;

import java.util.ArrayList;
import java.util.List;

public class ListUtilities {

    public static String listToString( ArrayList< ? > list , String dilemeter ){
        
        String construction = "";
        
        for ( int i = 0 ; i < list.size() ; i++ ){
            
            construction += list.get( i );
            
            if ( i != list.size() - 1 ){
                
                construction += dilemeter;
                
            }
            
        }
        
        return construction;
        
    }
    
}
