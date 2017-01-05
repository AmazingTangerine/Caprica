package caprica.datatypes;

import java.text.DecimalFormat;

/**
 *
 * @author Julia
 */
public class Num {
    
    private Double value = null;
    
    public Num( Object value ) {
 
        if ( value instanceof Num ){
        
            this.value =( ( Num ) value ).getDouble();
        
        }
        else if ( value instanceof String ){
            
            this.value = Double.parseDouble( ( String ) value );
            
        }
        else if ( value instanceof Integer ) {
            
            this.value = ( ( Integer ) value ).doubleValue();
            
        }
        else {
            
            this.value = ( double ) value;
            
        }
        
    }
    
    public Num(){}
    
    public double getDouble(){
        
        return value;
        
    }
    
    public boolean isEmpty(){
        
        return value == null;
        
    }
    
    @Override
    public String toString(){
        
        return "" + value;
        
    }
    
    public String toNiceString(){
        
        DecimalFormat format = new DecimalFormat( "#.00" );
        
        return format.format( value );
        
    }
    
    public int toInt(){

        return ( int )( Math.round( toDouble() ) );
        
    }
    
    public double toDouble(){
 
        return value;
        
    }
    
    public float toFloat(){
   
        return ( float )( toDouble() );
        
    }
    
    public Num add( Object addingNumRaw ){
        
        Num addingNum = new Num( addingNumRaw );

        double addedDouble = this.toDouble() + addingNum.toDouble();

        return new Num( addedDouble );
        
    }
    
    public Num pow( Object powerRaw ){
        
        Num powers = new Num( powerRaw );
        
        return new Num( Math.pow( this.toDouble() , powers.toDouble() ) );
        
    }
    
    public Num mul( Object multiplyRaw ){
        
        Num multiplyNum = new Num( multiplyRaw );

        return new Num( multiplyNum.getDouble() * this.getDouble() );
        
    }
    
    public Num multiply( Object multiplyRaw ){
        
        return mul( multiplyRaw );
        
    }
    
    public Num div( Object divRaw ){
        
        Num divNum = new Num( divRaw );
        
        return new Num( this.getDouble() / divNum.getDouble() );
        
    }
    
    public Num min( Object subtractNumRaw ){
        
        return this.add(new Num( subtractNumRaw ).negate() );
        
    }
    
    public Num scientific( int tenthPower ){
        
        return new Num( Math.pow( 10 , tenthPower ) * this.getDouble() );
        
    }
    
    public boolean equalsWhole( Object checkNumRaw ){
        
        Num checkNum = new Num( checkNumRaw );
    
        return this.getDouble() == checkNum.getDouble();
        
    }
    
    public boolean less( Object checkNumRaw ){
        
        Num checkNum = new Num( checkNumRaw );

        return this.toDouble() < checkNum.toDouble();

    }
    
    public Num abs(){
        
        if ( this.getDouble() < 0 ){
            
            return this.negate();
            
        }
        
        return this;
        
    }
    
    public Num half(){
        
        return this.mul( 0.5 );
        
    }
    
    public Num negate(){
        
        return this.mul( -1 );
        
    }
    
    public Num inverse(){
        
        return new Num( 1 ).div( this );
        
    }
    
    public Num sqrt(){
        
        return this.pow( 0.5 );
        
    }
    
    public Num sin(){
        
        return new Num( Math.sin( Math.toRadians( this.toDouble() ) ) );
        
    }
    
    public Num cos(){
        
        return new Num( Math.cos( Math.toRadians( this.toDouble() ) ) );
        
    }
        
    public Num tan(){
        
        return new Num( Math.tan( Math.toRadians( this.toDouble() ) ) );
        
    }
    
    public Num e(){
        
        return new Num( Math.exp( this.toDouble() ) );
        
    }
    
    public boolean even(){
        
        return ( this.toDouble() % 2 ) == 0;
        
    }
    
    public boolean divisibleBy( Num devisor ){
        
        Num calc = this.div( devisor );
        
        return Math.round( calc.getDouble() ) == calc.getDouble();
        
    }
    
    public Num round(){
        
        return new Num( Math.round( this.getDouble() ) );
        
    }
    
    public Num mod( int modulus ){
        
        if ( this.less( modulus ) ){

            return new Num( 0 );
            
        } 
        
        Num low = this.div( modulus ).round();
        Num divTimes = new Num( modulus ).mul( low );
        
        return this.min( divTimes );
        
    }
    
}
