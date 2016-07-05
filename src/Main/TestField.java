package Main;

import Datatypes.GlobalFile;
import Datatypes.InputDataStream;
import Datatypes.Num;
import Datatypes.Vector;
import Internet.Information;
import Internet.Webpage;
import Server.Bridge;
import System.Control;
import System.Output;
import Units.Unit;

public class TestField {

    /**
     * Just a general section of code to test components
     */
    public static void test(){

        try {
        
            Bridge testBridge = new Bridge( "192.168.0.17" );
            
            Control.sleep( 5 );
            
            testBridge.addConnection( "tesdt" );
        
        }
        catch( Exception e ){
            
            e.printStackTrace();
            
        }
        
    }
    
}
