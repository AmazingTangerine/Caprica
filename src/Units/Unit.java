package Units;

import Datatypes.Num;
import System.Output;
import java.util.HashMap;

public class Unit {
    
    HashMap< String , String > typeName;
 
    public void init(){

        typeName = new HashMap<  >();
        
        typeName.put( "length" , "m" );
        typeName.put( "angle" , "rad" );
        
    }
    
    public Num mInFt = new Num( 0.3048 );
    public Num mInIn = new Num( 0.0254 ); 
    public Num degInRad = new Num( 180 / Math.PI ); 
    
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
            
            unit = inputUnit;
            metric = true;
            
        }
        else { //Other
            
            unit = inputUnit;
            metric = false;
        
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
    
    public Unit toBase() {

        if ( type.equals( "length" ) ){

            if ( metric ){
            
                if ( !typeName.get( type ).equals( unit ) ){
                
                    int power = Prefix.PREFIXES.get( unit );
       
                    return new Unit( new Num( data.pow( power ) ) , "m" , type );
                
                }
                else{
                    
                    return new Unit( data , "m" , type );
                    
                }
            
            }
            else{
            
                if ( unit.equals( "ft" ) ){
                    
                    return new Unit( new Num( data.multiply( mInFt ) ) , "m" , type );

                }
                else if ( unit.equals( "in" ) ){
                    
                    return new Unit( data.multiply( mInIn ) , unit , type );

                }
            
            }
        
        }
        else if ( type.equals( "angle" ) ){
            
            if ( unit.contains( "deg" ) ){
                
                return new Unit( data.div( degInRad ) , typeName.get( type ) , type );
                
            }
                
        }
        
        return null;
        
    }
    
    public Unit neat(){ //How neat is that

        if ( data.less( 1 ) ){
            
            String rawNumber = data.toString();
            Num digits = new Num( 0 );
            
            for ( Num i = new Num( 2 ) ; i.less( rawNumber.length() ) ; i.increment() ){
                
                if ( rawNumber.charAt( i.toInt() ) == '0' ){
                    
                    digits.increment();
                    
                }
                else {
                    
                    break;
                    
                }
                
            }
            
            Num power = new Num( Math.floor( digits.div( new Num( 3 ) ).toDouble() ) ).multiply( new Num( -3 ) ); //Note: add floor and ceil to Num class
            
            for ( String unitName : Prefix.PREFIXES.keySet() ){
                
                if ( Prefix.PREFIXES.get( unitName ).equals( power.toInt() ) ){
                    
                    unit = unitName;
                    
                    break;
                    
                }
                
            }

            return new Unit( data.div( new Num( 10 ).pow( power.toInt() ) ) , unit + typeName.get( type ) , type );
            
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
            
            return new Unit( data.div( new Num( 10 ).pow( power.toInt() ) ) , unit + typeName.get( type ) , type );
                  
        }

    }
    
    @Override
    public String toString(){
        
        return data.toDouble() + "[" + unit + "]";
        
    }
    
}
