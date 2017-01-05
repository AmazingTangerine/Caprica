package caprica.system;

import caprica.datatypes.InputDataStream;
import caprica.datatypes.Num;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

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
    
    public static void setClipboard( String setValue ){
           
            StringSelection selection = new StringSelection( setValue );
            
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents( selection , selection );
        
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
    
    public void lock(){ //No linux command yet
        
        String command = "";
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            command = System.getenv("windir") + "\\system32\\" + "rundll32.exe user32.dll,LockWorkStation";
         
            exec( command );
            
        }
        
    }
    
    public void shutdown(){
        
        String command = "shutdown";
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            command = "shutdown -s -t 0";
            
        }
        
        exec( command );
        
    }
    
    public void reboot(){
        
        String command = "reboot";
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            command = "shutdown -r -t 0";
            
        }
        
        exec( command );
        
    }
    
}
