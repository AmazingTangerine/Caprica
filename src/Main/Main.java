package Main;

import Display.Screen;
import System.Update;
import Windows.Desktop;

public class Main {

    public static void main( String[] arguments ) {

        boolean testingMode = false;
        boolean guiMode = false;
        
        if ( !testingMode ){
        
            try {
            
                Update.start();
            
            }
            catch( Exception e ){}
            
            if ( !guiMode ){
            
                //Start the GUI
                Screen GUI = new Screen( "Agora" );
        
                Desktop desktop = new Desktop();
        
                GUI.addWindow( desktop );
        
            }
            
        }
        else{
            
            Update.download();
            
        }
        
    }
    
}
