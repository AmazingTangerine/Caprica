package agora.datatypes;

import agora.system.Output;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Num {

    public BigDecimal data;
    
    //Initializing from primative types
    
    public Num( Num input ){
        
        data = new BigDecimal( input.toDouble() );
        
    }
    
    public Num( Object input ){
        
        if ( input instanceof Long ){
            
            data = new BigDecimal( ( long ) input );
            
        }
        else if ( input instanceof Integer ){

            data = new BigDecimal( ( int ) input );

        }
        else if ( input instanceof Double ){
            
            data = new BigDecimal( ( Double ) input );
            
        }
        else if ( input instanceof Num ){
            
            data = ( ( Num ) input ).data;
  
        }
        else if ( input instanceof BigDecimal ){
            
            data = ( BigDecimal ) input;
            
        }
        
    }
    
    //Conversion back to primative types
    
    public double toDouble(){

        return data.doubleValue();
        
    }
    
    public long toLong(){
        
        return data.longValue();
        
    }
    
    public int toInt(){
        
        return data.intValue();
        
    }
    
    @Override
    public String toString(){
        
        DecimalFormat format = new DecimalFormat( "#.####" );
        format.setRoundingMode( RoundingMode.HALF_EVEN );
        
        return "" + format.format( toDouble() );
        
    }
    
    //Operations
    
    public Num inverse(){
        
        data = new BigDecimal( 1 ).divide( data , 30 , RoundingMode.HALF_EVEN );
        
        return this;
        
    }
    
    public boolean less( Object inputNumber ){
        
        return toDouble() < new Num( inputNumber ).toDouble();
        
    }
    
    public void increment(){
        
        data = data.add( new BigDecimal( 1 ) );
        
    }
    
    public Num add( Object addingValue ){
        
        return new Num( data.add( new Num( addingValue ).data ) );
        
    }
    
    public Num div( Object divValue ){

        Num dividant =  new Num( divValue );
  
        return new Num( data.divide( dividant.data , 5 , RoundingMode.HALF_EVEN ) );
        
    }
    
    public Num multiply( Object multiplyValue ){
        
        return new Num( data.multiply( new Num( multiplyValue ).data ) );
        
    }
    
    public Num power( Object base ){
        
        return new Num( Math.pow( data.intValue() , ( new Num( base ) ).toDouble() ) );
        
    }
    
    public Num pow( int base ){
        
        if ( base < 0 ){
        
            return new Num( data.pow( base * -1 ) ).inverse();
        
        }
        else {
            
            return new Num( data.pow( base ) );
            
        }
        
    }
    
    public boolean equals( Num compare ){ //Close enough
        
        return toDouble() == compare.toDouble();
        
    }
    
}
