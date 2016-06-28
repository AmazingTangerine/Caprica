package Main;

import Display.Screen;
import Windows.Desktop;

public class Main {

    public static void main( String[] arguments ) {

        boolean testingMode = true;
        
        if ( !testingMode ){
        
            //Start the GUI
            Screen GUI = new Screen( "Agora" );
        
            Desktop desktop = new Desktop();
        
            GUI.addWindow( desktop );
        
        }
        else{
            
            TestField.test();
            
        }
        
    }
    
}
