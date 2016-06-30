package Main;

import Datatypes.Num;
import Datatypes.Vector;
import System.Output;
import Units.Unit;

public class TestField {

    /**
     * Just a general section of code to test components
     */
    public static void test(){

        int[] possibleAngles = new int[]{ 40 , 100 , 340 , 270 }; //Possible vector angles
        int[] realAngles = new int[]{ 40 , 100 , -20 , 90 };        

        Num shortSpeed = new Num( 2200 );
        Num longSpeed = new Num( 1.4 * 1000000 );
        
        String[] names = new String[]{ "short" , "long" };
        Num[] speeds = new Num[]{ shortSpeed , longSpeed };
        int[][] counts = new int[][]{ { 4 , 3 , 3 , 2 } , { 2 , 2 , 2 , 3 } };
        
        Vector totalCurrent = new Vector( new Num( 0 ) , new Num( 0 ) );
        Num totalFlux = new Num( 0 );
        
        String message = "";
        
        for ( int z = 0 ; z < names.length ; z++ ){ //For short then long arrows
         
            for ( int i = 0 ; i < possibleAngles.length ; i++ ){ //For the different angle groups
            
                Vector groupCurrent = new Vector( new Num( 0 ) , new Num( 0 ) );
                Num groupFlux = new Num( 0 );
                
                Vector specificCurrent = new Vector( new Unit( speeds[ z ] ) , new Unit( new Num( possibleAngles[ i ] ) , "deg" , "angle" ) );

                for ( int x = 0 ; x < counts[ z ][ i ] ; x++ ){
                
                    groupCurrent = groupCurrent.add( specificCurrent );
                    groupFlux = groupFlux.add( speeds[ z ] );
                
                }
                
                totalCurrent = totalCurrent.add( groupCurrent );
                totalFlux = totalFlux.add( groupFlux );
            
                Output.print( "The group flux for the " + names[ z ] + " arrows of " + realAngles[ i ] + " degrees is " + groupFlux.toString() + "[n/m^2/s]" , new Num( 0 ) );
                message += "The group current for the " + names[ z ] + " arrows of " + realAngles[ i ] + " degrees is " + groupCurrent.asString() + "[n/m^2/s]" + "\n";
            
            }
            
            message += "\n";
            Output.print( "" , new Num( 0 ) );
        
        }
        
        Output.print( "The total flux is " + totalFlux.toString() + "[n/m^2/s]" , new Num( 0 ) );
        
        Output.print( "" , new Num( 0 ) );
        
        Output.print( message , new Num( 0 ) );
        
        Output.print( "The total current is " + totalCurrent.asString() + "[n/m^2/s]" , new Num( 0 ) );
        
        Output.print( "" , new Num( 0 ) );
        
        double radAngle = Math.tan( totalCurrent.getY().div( totalCurrent.getX() ).toDouble() );
        
        Output.print( "The angle for the total current is " + Math.toDegrees( radAngle ) + " degrees", new Num( 0 ) );
        
    }
    
}
