package Main;

import Datatypes.Matrix;
import Datatypes.Num;
import Datatypes.Vector;
import System.Output;
import Units.Unit;

public class TestField {

    /**
     * Just a general section of code to test components
     */
    public static void test(){
        
        Unit a = new Unit( new Num( 1000 ) , "m" , "length" );
        
        Output.print( a , new Num( 0 ) );
        Output.print( "\n" , new Num( 0 ) );
        Output.print( a.neat() , new Num( 0 ) );
        
    }
    
}
