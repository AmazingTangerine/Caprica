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
        
        //Write to logs
        
    }
    
    /**
     * 
     * @param rawInput The raw input, it'll be interpreted into a string
     * @param logLevel If logLevel < 0 then only prNum to screen if debug mode is active, else prNum to screne. If the log level equals one then the output will be saved in a log file
     */
    public static void print( Object rawInput , Num logLevel ){
        
        String refinedInput = interpretObject( rawInput );
   
        if ( logLevel.less( new Num( 0 ) ) ){
            
            if ( Main.Main.debugMode ){
                
                System.out.println( refinedInput );
                
            }
            
        }
        else{
            
            System.out.println( refinedInput );
            
            if ( logLevel.equals( new Num( 0 ) ) ){
                
                log( new Report( refinedInput ) , "debug" );
                
            }
            
        }
 
    }
    
    /**
     * Same as print just assumes log level is 0
     * @param rawInput The object to be converted to string and printed
     */
    public static void print( Object rawInput ){
        
        print( rawInput , new Num( 0 ) );
        
    }
    
}
