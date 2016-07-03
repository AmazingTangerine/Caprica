package agora.main;

import agora.display.Screen;
import agora.internet.Information;
import agora.system.Control;
import agora.system.Output;
import agora.system.Report;
import agora.system.Setup;
import agora.system.Update;
import agora.windows.Desktop;

public class Main {

    public static boolean debugMode = true;
    
    public static void main( String[] arguments ) {

        boolean testingMode = false;
        boolean guiMode = false;
        boolean serverMode = false;
 
        Output.log( new Report( "Starting Agora" ) , "debug" );
        
        if ( arguments.length > 0 ){
            
            Output.log( new Report( "Argument entry mode" ) , "debug" );
            
            testingMode = Boolean.parseBoolean( arguments[ 0 ] );
            guiMode = Boolean.parseBoolean( arguments[ 1 ] );
            serverMode = Boolean.parseBoolean( arguments[ 2 ] );
            
        }
        
        if ( !testingMode ){

            if ( !Setup.setup() ){
                
                Output.log( new Report( "Could not initiate setup" ) , "critical" );
                
                Control.exit( 1 );
                
            }
                 
            if ( serverMode ){
                
                ServerStart.start();
                
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
