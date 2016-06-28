package Main;

import Datatypes.Matrix;
import Datatypes.Num;
import Datatypes.Vector;
import System.Output;

public class TestField {

    /**
     * Just a general section of code to test components
     */
    public static void test(){
        
        Matrix A = new Matrix( new Vector( 1 , 1 ) , new Vector( 2 , 3 ) );
        Matrix B = new Matrix( new Vector( 1 , 1 ) , new Vector( 2 , 3 ) );
            
        Output.print( A , new Num( 0 ) );
        Output.print( "\n" , new Num( 0 ) );
        Output.print( A.multily( B ) , new Num( 0 ) );
        
    }
    
}
