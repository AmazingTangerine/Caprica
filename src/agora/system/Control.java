package agora.system;

import agora.datatypes.InputDataStream;
import agora.datatypes.Num;

public class Control {

    /**
     * Puts caller thread to sleep
     * @param seconds The time in seconds for the thread to sleep
     */
    public static void sleep( Object rawSeconds ){
        
        Num seconds = new Num( rawSeconds );
        
        try {
            
            Thread.sleep( Math.round( seconds.toDouble() * 1000 ) );
            
        }
        catch( Exception E ){}
        
    }
    
    public static String exec( String command , boolean wait ){
        
        try {
        
            Process process = Runtime.getRuntime().exec( command );

            if ( wait ){
            
                process.waitFor();
            
            }
            
            return new InputDataStream( process.getInputStream() ).toString() + new InputDataStream( process.getErrorStream() ).toString();
            
        }
        catch( Exception e ){ e.printStackTrace(); }
            
        return null;
        
    }
    
    public static String exec( String command ){
        
        return exec( command , true );
        
    }
    
    public static void exit( int level ){
        
        System.exit( level );
        
    }
    
}
