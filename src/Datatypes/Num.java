package Datatypes;

public class Num {

    double data;
    
    //Initializing from primative types
    
    public Num( Num input ){
        
        data = input.toDouble();
        
    }
    
    public Num( Object input ){
        
        if ( input instanceof Long ){
            
            data = ( long ) input;
            
        }
        else if ( input instanceof Integer ){

            data = ( int ) input;

        }
        else if ( input instanceof Double ){
            
            data = ( Double ) input;
            
        }
        else if ( input instanceof Num ){
            
            data = ( ( Num ) input ).data;
  
        }
        
    }
    
    //Conversion back to primative types
    
    public double toDouble(){

        return data;
        
    }
    
    public long toLong(){
        
        return ( long ) data;
        
    }
    
    public int toInt(){
        
        return ( int ) data;
        
    }
    
    @Override
    public String toString(){
        
        return "" + data;
        
    }
    
    //Operations
    
    public Num inverse(){
        
        data = 1 / data;
        
        return this;
        
    }
    
    public boolean less( Object inputNumber ){
        
        return toDouble() < new Num( inputNumber ).toDouble();
        
    }
    
    public void increment(){
        
        data = data + 1;
        
    }
    
    public Num add( Object addingValue ){
        
        return new Num( data + new Num( addingValue ).toDouble() );
        
    }
    
    public Num div( Object divValue ){

        return new Num( data / new Num( divValue ).toDouble() );
        
    }
    
    public Num multiply( Object multiplyValue ){
        
        return new Num( data * new Num( multiplyValue ).toDouble() );
        
    }
    
    public Num power( Object base ){
        
        return new Num( Math.pow( data , ( new Num( base ) ).toDouble() ) );
        
    }
    
    public boolean equals( Num compare ){
        
        return toDouble() == compare.toDouble();
        
    }
    
}
