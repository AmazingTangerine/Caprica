package Units;

import Datatypes.Num;
import System.Output;
import java.util.HashMap;

public class Unit {
    
    HashMap< String , String > typeName;
 
    public void init(){

        typeName = new HashMap<  >();
        
        typeName.put( "length" , "m" );
        
    }
    
    public Num mInFt = new Num( 0.3048 );
    public Num mInIn = new Num( 0.0254 ); 
    
    public Num data;
    public String unit;
    public boolean metric;
    public String type;
    
    /**
     * Initializes the value and assumes unit is 'm'
     * @param input The input data
     */
    public Unit( Num input ){
        
        init();
        
        data = input;
        unit = "m";
        type = "length";
        metric = true;
        
    }
    
    /**
     * Initialzes unit
     * @param input The value
     * @param inputUnit The value units
     * @param inputType The type of this number
     */
    public Unit( Num input , String inputUnit , String inputType ){
        
        init();
        
        data = input;
        type = inputType;
        
        if ( inputUnit.contains( typeName.get( type ) ) ){ //Metric
            
            if ( inputUnit.equals( typeName.get( inputType ) ) ){
                
                unit = inputUnit;
                
            }
            else{
            
                unit = inputUnit.replaceFirst( typeName.get( type )  , "" );
            
            }
            
            metric = true;
            
        }
        else { //Imperial
            
            unit = inputUnit;
            metric = false;
            type = inputType;
        
        }
        
    }
    
    public Unit add( Unit input ){
        
        //Convert to their base unit, ie 'm' for length
        toBase();
        input.toBase();
            
        //Add the bases
        Num combination = this.data.add( input.data );
            
        Unit returnUnit = new Unit( combination , typeName.get( type ) , type );
            
        return returnUnit;
        
    }
    
    public void toBase() {

        if ( type.equals( "length" ) ){
        
            unit = "m";
        
            if ( metric ){
            
                int power = Prefix.PREFIXES.get( unit );
       
                data = new Num( data.pow( power ) );
            
            }
            else{
            
                if ( unit.equals( "ft" ) ){
                
                    data = data.multiply( mInFt );
                
                }
                else if ( unit.equals( "in" ) ){
                
                    data = data.multiply( mInIn );
                
                }
            
            }
        
        }
        
    }
    
    public Unit neat(){ //How neat is that

        if ( data.less( 0 ) ){
            
            
            
        }
        else{
            
            Num digits = new Num( data.toString().split( "/." )[ 0 ].length() ).add( new Num( - 1 ) );

            Num power = new Num( Math.floor( digits.div( new Num( 3 ) ).toDouble() ) ).multiply( new Num( 3 ) ); //Note: add floor and ceil to Num class
            
            for ( String unitName : Prefix.PREFIXES.keySet() ){
                
                if ( Prefix.PREFIXES.get( unitName ).equals( power.toInt() ) ){
                    
                    unit = unitName;
                    
                    break;
                    
                }
                
            }
            
            return new Unit( data.div( new Num( 1 ).pow( power.toInt() ) ) , unit , type );
            
            
        }
        
        return null;
        
    }
    
}
