package caprica.datatypes;

import caprica.system.Output;
import java.util.ArrayList;

public class StringUtilities {

    public static String[] splitSpace( String splitString ){
        
        char[] rawSplit = splitString.toCharArray();
        
        ArrayList< String > cleanSplitList = new ArrayList<>();
        
        String construction = "";
        int index = 0;
        
        for ( char split : rawSplit ){
           
            if ( ( split == ' ' && !construction.equals( "" ) ) || index == rawSplit.length - 2 ){
       
                cleanSplitList.add( construction );
                
                construction = "";
                
            }
            else if ( split != ' ' ) {
                
                construction += split;
                
            }
     
            index++;
            
        }
        
        String[] list = new String[ cleanSplitList.size() ];
        
        for ( int i = 0 ; i < cleanSplitList.size() ; i++ ){
            
            list[ i ] = cleanSplitList.get( i );
            
        }
        
        return list;
        
    }
    
    public static String snipLast( String input ){
        
        if ( input.length() > 0 ){
        
            return input.substring( 0 , input.length() - 1 );
        
        }
        else {
            
            return input;
            
        }
        
    }
    
}
