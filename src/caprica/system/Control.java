package caprica.system;

import caprica.datatypes.InputDataStream;
import caprica.datatypes.Num;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
    
    public static String execParams( String[] parameters , boolean wait ){
        
        try {
        
            Process process = new ProcessBuilder( parameters ).start();

            if ( wait ){
            
                process.waitFor();
            
            }
            
            String response = "";
            
            if ( process.getInputStream().available() > 0 ){
                
                response += new InputDataStream( process.getInputStream() ).toString();
                
            }
            
            if ( process.getErrorStream().available() > 0 ){
                
                response += new InputDataStream( process.getErrorStream() ).toString();
                
            }
            
            return response;
            
        }
        catch( Exception e ){ e.printStackTrace(); }
            
        return null;
        
    }
    
    public static String exec( String command , boolean wait ){
        
        try {
        
            Process process = Runtime.getRuntime().exec( command );

            if ( wait ){
            
                process.waitFor();
            
            }
            
            String response = "";
            
            if ( process.getInputStream().available() > 0 ){
                
                response += new InputDataStream( process.getInputStream() ).toString();
                
            }
            
            if ( process.getErrorStream().available() > 0 ){
                
                response += new InputDataStream( process.getErrorStream() ).toString();
                
            }
            
            return response;
            
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
    
    public static void lock(){ //No linux command yet
        
        String command;
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            Output.print( "Locking computer" );
            
            command = System.getenv("windir") + "\\system32\\" + "rundll32.exe user32.dll,LockWorkStation";
         
            exec( command );
            
        }
        
    }
    
    public static void shutdown(){
        
        String command = "shutdown";
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            command = "shutdown -s -t 0";
            
        }
        
        exec( command );
        
    }
    
    public static void reboot(){
        
        String command = "reboot";
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            command = "shutdown -r -t 0";
            
        }
        
        exec( command );
        
    }
    
    public static void popup( String message ){
      
        Output.print( "Creating popup: " + message );
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            JFrame frame = new JFrame();
            frame.requestFocus();
            
            JOptionPane.showMessageDialog( frame , message );
            
        }
        
    }
    
    public static String selectionQuestion( String[] options , String message , String header ){
    
        String choice = ( String ) JOptionPane.showInputDialog( new JFrame() , message , header , JOptionPane.PLAIN_MESSAGE , null , options , options[ 0 ] );
              
        return choice;
        
    }
    
}
