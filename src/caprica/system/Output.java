package caprica.system;

import android.util.Log;
import caprica.datatypes.Matrix;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class Output {

    private String programName = null;
    
    public Output( String programName ){
        
        this.programName = programName;
        
    }
    
    public void disp( Object... rawInputs ){
        
        for ( Object input : rawInputs ){
            
            String refinedInput = interpretObject( input );
            
            if ( SystemInformation.getOS().equals( "Android" ) ){
                
                Log.d( "Caprica", "[" + programName + "]" + refinedInput );
                
            }
            else {
            
                System.out.print( "[" + programName + "]" + refinedInput + "\n" );
        
            }
            
        }
        
    }
    
    /**
     * 
     * @param input The input data to be interpreted
     * @return the interpreted object
     */
    public static String interpretObject( Object input ){
        
        if ( input == null ){ return "null"; }
        
        if ( input instanceof Matrix ){
            
            Matrix matrix = ( Matrix ) input;
            
            String compound = "";
            
            for ( int y = 0 ; y < matrix.getY() ; y++ ){
            
                for ( int x = 0 ; x < matrix.getX() ; x++ ){
      
                    compound += matrix.get( x, y ).toString() + " ";
                
                }
                
                if ( y != matrix.getX() - 1 ){
                    
                    compound += "\n";
                    
                }
                
            }
            
            return compound;
            
        }
        else if ( input.getClass().isArray() ){
            
            Object[] inputArray = ( Object[] ) input;
            
            String construction = "[";
            
            int count = 1;
            
            for ( Object inputName : inputArray ){
                
                construction += interpretObject( inputName );
                
                if ( count != inputArray.length ){
                    
                    construction += ",";
                    
                }
                
                count ++;
                
            }
            
            return construction + "]";
            
        }
        else if ( input instanceof HashMap ){
            
            HashMap< Object , Object > data = ( HashMap< Object , Object > ) input;
            
            String construction = "";
            
            for ( Object key : data.keySet() ){

                construction += interpretObject( key ) + ">";
                construction += interpretObject( data.get( key ) ) + "\n";
                
            }
            
            return construction;
            
        }
        else if ( input instanceof Byte ){
            
            byte value = ( Byte ) input;
            
            return String.format( "%8s" , Integer.toBinaryString( value & 0xFF ) ).replace(' ', '0');
            
        }
            
        
        return "" + input;
        
    }

    public static void print( Object... rawInputs ){
        
        for ( Object input : rawInputs ){
            
            String refinedInput = interpretObject( input );
            
            if ( SystemInformation.getOS().equals( "Android" ) ){
            
                Log.d( "Caprica" , refinedInput );
                
            }
            else {
                
            System.out.print( refinedInput + "\n" );
          
            }
            
        }
        
    }

    static String response = "";
    
    public static String question( String message , boolean password ){
        
        return JOptionPane.showInputDialog( message );

    }
    
}
