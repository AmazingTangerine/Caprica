package System;

import Datatypes.Matrix;
import Datatypes.Num;
import Datatypes.Vector;
import Units.Unit;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Output {
    
    /**
     * 
     * @param input The input data to be interpreted
     * @return the interpreted object
     */
    public static String interpretObject( Object input ){
        
        if ( input instanceof Matrix ){
            
            Matrix matrix = ( Matrix ) input;
            
            String compound = "";
            
            for ( Num y = new Num( 0 ) ; y.less( matrix.getY() ) ; y.increment() ){
            
                for ( Num x = new Num( 0 ) ; x.less( matrix.getX() ) ; x.increment() ){
      
                    compound += matrix.get( x , y ).toString() + " ";
                
                }
                
                if ( y.toInt() != matrix.getX().toInt() - 1 ){
                    
                    compound += "\n";
                    
                }
                
            }
            
            return compound;
            
        }
        
        return "" + input;
        
    }
    
    public static void log( Report report , String reportType ){

        print( report.message );
        
    }

    public static void print( Object rawInput ){
        
        String refinedInput = interpretObject( rawInput );
   
        System.out.print( refinedInput + "\n" );
 
    }
    
    
}
