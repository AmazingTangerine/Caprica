package System;

import Datatypes.InputDataStream;
import Datatypes.Num;

public class Control {

    /**
     * Puts caller thread to sleep
     * @param seconds The time in seconds for the thread to sleep
     */
    public static void sleep( Num seconds ){
        
        try {
            
            Thread.sleep( seconds.toLong() * 1000 );
            
        }
        catch( Exception E ){}
        
    }
    
    public static String exec( String command ){
        
        try {
        
            Process process = Runtime.getRuntime().exec( command );
        
            return new InputDataStream( process.getInputStream() ).toString() + new InputDataStream( process.getErrorStream() ).toString();
            
        }
        catch( Exception e ){}
            
        return null;
        
    }
    
}
