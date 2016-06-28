package System;

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
    
}
