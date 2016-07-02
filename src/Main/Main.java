package Main;

import Display.Screen;
import Internet.Information;
import System.Control;
import System.Output;
import System.Report;
import System.Setup;
import System.Update;
import Windows.Desktop;

public class Main {

    public static boolean debugMode = true;
    
    public static void main( String[] arguments ) {

        boolean testingMode = false;
        boolean guiMode = true;
        boolean serverMode = false;
 
        Output.log( new Report( "Starting Agora" ) , "debug" );
        
        if ( !testingMode ){

            if ( !Setup.setup() ){
                
                Output.log( new Report( "Could not initiate setup" ) , "critical" );
                
                Control.exit( 1 );
                
            }
                 
            if ( serverMode ){
                
                
                
            }
            else{
                
                if ( guiMode ){
            
                    //Start the GUI
                    Screen GUI = new Screen( "Agora" );
        
                    Desktop desktop = new Desktop();
        
                    GUI.addWindow( desktop );
        
                }
                
            }
            

            
        }
        else{
        
            TestField.test();
            
        }
        
    }
    
}
