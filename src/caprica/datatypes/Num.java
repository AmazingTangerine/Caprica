package caprica.datatypes;

import caprica.system.Output;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 *
 * @author Julia
 */
public class Num {
    
    public static final int MODE_DOUBLE = 0; //Fastest
    public static final int MODE_MIRANDA = 1; //10x slower than fastest
    public static final int MODE_BIG_DECIMAL = 2; //Slightly slower than miranda
    
    public static int MODE = MODE_DOUBLE;
    
    //Double mode
    private Double value = null;
    
    //Miranda mode
    private double power;
    private int base;
    private int[] miranda = new int[ 0 ];
    private boolean negative = false;
    
    //BigDecimal mode
    private BigDecimal bigDecimal = null;
    private static int precission = 1;

    public Num( double power , int base , int[] miranda , boolean negative ){
        
        this.power = power;
        this.base = base;
        this.miranda = miranda;
        this.negative = negative;
        
    }
    
    public Num( Object value ) {
 
        if ( MODE == MODE_BIG_DECIMAL ){

            if ( value instanceof Num ){
        
                this.bigDecimal = ( ( Num ) value ).bigDecimal;
        
            }
            else if ( value instanceof String ){
            
                this.bigDecimal = new BigDecimal( ( String ) value );
            
            }   
            else if ( value instanceof Integer ) {
            
                this.bigDecimal = new BigDecimal( ( Integer ) value );
            
            }
            else if ( value instanceof Long ) {
            
                this.bigDecimal = new BigDecimal( ( Long ) value );
            
            }
            else if ( value instanceof Double ){
                
                this.bigDecimal = new BigDecimal( ( Double ) value );
                
            }
            else if ( value instanceof Float ){
                
                this.bigDecimal = new BigDecimal( ( Float ) value );
                
            }
            else if ( value instanceof BigDecimal ){
                
                this.bigDecimal = ( BigDecimal ) value;
                
            }
            
            if ( this.bigDecimal == null ){
                
                Output.print( value.getClass() );
                
            }
        
        }
        else if ( MODE == MODE_MIRANDA ){
        
            if ( value instanceof Num ){
                
                this.power = ( ( Num ) value ).getPower();
                this.base = ( ( Num ) value ).getBase();
                this.miranda = ( ( Num ) value ).getMiranda();
                this.negative = ( ( Num ) value ).getNegative();
                
            }
            else if ( ( value instanceof Double || value instanceof Integer || value instanceof Long ) && !( ( "" + value ).contains( "E" ) ) ){
                
                double doubleValue;
                
                if ( value instanceof Integer ){
                    
                    doubleValue = 0.0 + ( Integer ) value;
                    
                }
                else if ( value instanceof Long ){
                    
                    doubleValue = 0.0 + ( Long ) value;
                    
                }
                else {
                    
                    doubleValue = ( double ) value;
                    
                }
  
                if ( doubleValue < 0 ){
                    
                    negative = true;
                    doubleValue = doubleValue * -1;
                    
                }
                
                if ( doubleValue == 0 ){
                    
                    this.base = 0;
                    this.power = 0;
                    this.miranda = new int[]{ 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 };
                    
                }
                else {

                    this.power = ( int ) Math.ceil( Math.log10( doubleValue ) ) - 1;

                    if ( doubleValue % 10 == 0 ){
                    
                        this.power++;
                    
                    }
                
                    if ( doubleValue == 1.0 ){
                    
                        this.power = 0;
                    
                    }
     
                    this.base = Integer.parseInt( "" + ( "" + ( doubleValue / Math.pow( 10 , power ) ) ).charAt( 0 ) );
              
                    String rawDecimal = ( "" + ( doubleValue / Math.pow( 10 , power ) ) ).replace( this.base + "." , "" );
  
                    if ( rawDecimal.length() < 8 ){
                    
                        int length = rawDecimal.length();
                    
                        if ( rawDecimal.contains( "." ) ){
                   
                            length = rawDecimal.split( "\\." )[ 1 ].length();
                        
                        }
                    
                        for ( int i = 0 ; i < 8 - length ; i++ ){
                        
                            rawDecimal += "0";
                        
                        }
                    
                    }
  
                    miranda = new int[ 8 ];
           
                    for ( int i = 0 ; i < 8 ; i++ ){
                    
                        miranda[ i ] = Integer.parseInt( "" + rawDecimal.charAt( i ) );
    
                    }
                
                }
                
            }
            else if ( value instanceof String || ( "" + value ).contains( "E" ) ){
                
                String rawDecimal = "" + value;
        
                double pow = 1;
                
                if ( rawDecimal.contains( "E" ) ){
                 
                    pow = Double.parseDouble( rawDecimal.split( "E" )[ 1 ] );
                    
                    rawDecimal = rawDecimal.split( "E" )[ 0 ];
                    
                }
                
                double doubleValue = Double.parseDouble( rawDecimal );
                
                if ( doubleValue == 0 ){
                    
                    this.base = 0;
                    this.power = 0;
                    this.miranda = new int[]{ 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 };
                    
                }
                else {
                
                    if ( doubleValue < 0 ){
                    
                        negative = true;
                        doubleValue = doubleValue * -1;
                    
                    }
                
                    this.power = ( int ) Math.ceil( Math.log10( doubleValue ) ) + pow - 1;
       
                    this.base = Integer.parseInt( "" + ( "" + ( doubleValue / Math.pow( 10 , power ) ) ).charAt( 0 ) );

                    if ( rawDecimal.contains( "." ) ){
                    
                        rawDecimal = rawDecimal.split( "\\." )[ 1 ];
                    
                    }
          
                    if ( rawDecimal.length() < 8 ){
                    
                        int length = rawDecimal.length();
                    
                        for ( int i = 0 ; i < 8 - length ; i++ ){
                        
                            rawDecimal += "0";
                        
                        }
                    
                    }
  
                    miranda = new int[ 8 ];
                
                    for ( int i = 0 ; i < 8 ; i++ ){
                    
                        miranda[ i ] = Integer.parseInt( "" + rawDecimal.charAt( i ) );
    
                    }
                
                }

            }
            
        }
        else if ( MODE == MODE_DOUBLE ){
        
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
        
    }
    
    public Num(){}
    
    public int accusator(){
        
        if ( negative ){ return -1; }
        
        return 1;
        
    }
    
    public double getDouble(){
        
        if ( MODE == MODE_DOUBLE ){
        
            return value;
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return bigDecimal.doubleValue();
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            String compile = "";
            
            for ( int i = 0 ; i < miranda.length ; i++ ){
                
                compile += miranda[ i ];
                
            }
            
            return Math.pow( 10 , power ) * Double.parseDouble( base + "." + compile ) * accusator();
            
        }
        
        return 0;
        
    }
    
    public boolean isEmpty(){
        
        if ( MODE == MODE_DOUBLE ){
        
            return value == null;
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return bigDecimal == null;
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return miranda.length == 0;
            
        }
        
        return true;
        
    }
    
    @Override
    public String toString(){
        
        if ( MODE == MODE_DOUBLE ){
        
            return "" + value;
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return bigDecimal.toString();
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            String compile = "";
            
            if ( negative ){ compile = "-"; }
            
            for ( int i = 0 ; i < miranda.length ; i++ ){
                
                compile += miranda[ i ];
                
            }
            
            return base + "." + compile + "E" + power;
            
        }
        
        return "";
        
    }
    
    public String toNiceString(){
        
        if ( MODE == MODE_BIG_DECIMAL ){
            
            return bigDecimal.toEngineeringString();
            
        }
        
        DecimalFormat format = new DecimalFormat( "#.00" );
        
        return format.format( getDouble() );
        
    }
    
    public int toInt(){

        return ( int )( Math.round( toDouble() ) );
        
    }
    
    public double toDouble(){
 
        return getDouble();
        
    }
    
    public float toFloat(){
   
        return ( float )( toDouble() );
        
    }
    
    public double powerLess(){
        
        String compile = "";
            
        for ( int i = 0 ; i < miranda.length ; i++ ){
                
            compile += miranda[ i ];
                
        }
        
        return Double.parseDouble( base + "." + compile ) * accusator();
        
    }
    
    public Num add( Object addingNumRaw ){
        
        Num addingNum = new Num( addingNumRaw );

        if ( MODE == MODE_DOUBLE ){
        
            double addedDouble = this.toDouble() + addingNum.toDouble();

            return new Num( addedDouble );
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return new Num( this.bigDecimal.add( addingNum.bigDecimal ) );
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            if ( power == addingNum.getPower() ){
          
                return new Num( ( this.powerLess() + addingNum.powerLess() ) + "E" + power );
                
            }
            else if ( power < addingNum.getPower() ){ //Our number is smaller than adding number
                
                String rawShift = "";
                
                for ( int i = 0 ; i < addingNum.getPower() - power - 1 ; i++ ){
                    
                    rawShift += "0";
                    
                }
                
                rawShift += ( "" + powerLess() ).replace( "." , "" );
                
                int negCheck = 1;
                
                if ( rawShift.contains( "-" ) ){
                    
                    rawShift = rawShift.replace( "-" , "" );
                    negCheck = -1;
                    
                }

                return new Num( ( new Num( "0." + rawShift ).powerLess() * negCheck + addingNum.powerLess() ) + "E" + addingNum.getPower() );
                
            }
            else if ( power > addingNum.getPower() ){ //Our number is bigger than adding number
                
                String rawShift = "";
                
                for ( int i = 0 ; i < power - addingNum.getPower() - 1 ; i++ ){
                   
                    rawShift += "0";
                    
                }
                
                rawShift += ( "" + addingNum.powerLess() ).replace( "." , "" );
             
                int negCheck = 1;
                
                if ( rawShift.contains( "-" ) ){
                    
                    rawShift = rawShift.replace( "-" , "" );
                    negCheck = -1;
                    
                }

                return new Num( ( this.powerLess() + ( new Num( "0." + rawShift ).powerLess() * negCheck ) ) + "E" + power );
                
            }
            
        }
        
        return new Num();
        
    }
    
    public Num pow( Object powerRaw ){
        
        if ( MODE == MODE_DOUBLE ){
        
            Num powers = new Num( powerRaw );
        
            return new Num( Math.pow( this.toDouble() , powers.toDouble() ) );
        
        }
         else if ( MODE == MODE_BIG_DECIMAL ){
            
            return new Num( Math.pow( this.getDouble() , new Num( powerRaw ).getDouble() ) );
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return new Num( power + new Num( powerRaw ).getDouble() , base , miranda , negative );
            
        }
        
        return new Num();
        
    }
    
    public Num mul( Object multiplyRaw ){
        
        Num multiplyNum = new Num( multiplyRaw );

        if ( MODE == MODE_DOUBLE ){

            return new Num( multiplyNum.getDouble() * this.getDouble() );
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return new Num( this.bigDecimal.multiply( multiplyNum.bigDecimal ) );
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return new Num( "" + ( this.powerLess() * multiplyNum.powerLess() ) + "E" + ( this.getPower() + multiplyNum.getPower() ) );
            
        }
        
        return new Num();
        
    }
    
    public Num multiply( Object multiplyRaw ){
        
        return mul( multiplyRaw );
        
    }
    
    public Num divide( Object divRaw ){
        
        return div( divRaw );
        
    }
    
    public Num div( Object divRaw ){
        
        Num divNum = new Num( divRaw );
        
        if ( MODE == MODE_DOUBLE ){

            return new Num( this.getDouble() / divNum.getDouble() );
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
    
            return new Num( this.bigDecimal.divide( divNum.bigDecimal , precission ) );
            
        }
        else if ( MODE == MODE_MIRANDA ){
  
            return new Num( "" + ( this.powerLess() / divNum.powerLess() ) + "E" + ( this.getPower() - divNum.getPower() ) );
            
        }
        
        return new Num();
        
    }
    
    public Num min( Object subtractNumRaw ){
        
        return this.add( new Num( subtractNumRaw ).negate() );
        
    }
    
    public Num scientific( int tenthPower ){
        
        if ( MODE == MODE_DOUBLE ){
        
            return new Num( Math.pow( 10 , tenthPower ) * this.getDouble() );
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return new Num( this.bigDecimal.multiply( new BigDecimal( 10 ).pow( tenthPower ) ) );
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return this;
            
        }
        
        return new Num();
        
    }
    
    public boolean equalsWhole( Object checkNumRaw ){
        
        Num checkNum = new Num( checkNumRaw );
        
        if ( MODE == MODE_DOUBLE ){
        
            return this.getDouble() == checkNum.getDouble();
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            int compare = this.bigDecimal.compareTo( checkNum.bigDecimal );
            
            return compare == 0;
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            for ( int i = 0 ; i < miranda.length ; i++ ){
                
                if ( miranda[ i ] != checkNum.getMiranda()[ i ] ){
                    
                    return false;
        
                }
                
            }
            
            return this.getPower() == checkNum.getPower() && this.getBase() == checkNum.getBase() && this.getNegative() == checkNum.getNegative();
            
        }
        
        return false;

    }
    
    public boolean greater( Object checkNumRaw ){
        
        return !less( checkNumRaw );
        
    }
    
    public boolean less( Object checkNumRaw ){
        
        Num checkNum = new Num( checkNumRaw );

        if ( MODE == MODE_DOUBLE ){
        
            return this.toDouble() < checkNum.toDouble();
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            int compare = this.bigDecimal.compareTo( checkNum.bigDecimal );
            
            return compare == -1;
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            if ( this.getPower() == checkNum.getPower() ){
                
                return this.powerLess() < checkNum.powerLess();
                
            }
            else {
                
                if ( this.getNegative() && checkNum.getNegative() ){
                
                    return this.getPower() < checkNum.getPower();
                
                }
                else if ( this.getNegative() && !checkNum.getNegative() ){
                    
                    return true;
                    
                }
                else {
                    
                    return false;
                    
                }
                
            }
            
        }
        
        return false;

    }
    
    public Num abs(){
        
        if ( this.less( new Num( 0 ) ) ){
            
            return this.negate();
            
        }
        
        return this;
        
    }
    
    public Num half(){
        
        return this.mul( 0.5 );
        
    }
    
    public Num negate(){
        
        if ( MODE == MODE_DOUBLE ){
        
            return new Num( this.getDouble() * -1 );
            
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return new Num( this.bigDecimal.negate() );
        
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return new Num( power , base , miranda , !negative );
            
        }
        
        return new Num(){};
        
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
        
        if ( MODE == MODE_DOUBLE ){
        
            return ( this.toDouble() % 2 ) == 0;
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
        
            return this.bigDecimal.divideAndRemainder( new BigDecimal( 2 ) )[ 1 ].intValue() == 0;
            
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return ( this.powerLess() % 2 ) == 0;
            
        }
        
        return false;
        
    }
    
    public boolean divisibleBy( Num devisor ){
        
        Num calc = this.div( devisor );
        
        return calc.round().equalsWhole( calc );
        
    }
    
    public Num round(){
        
        if ( MODE == MODE_DOUBLE ){
        
            return new Num( Math.round( this.getDouble() ) );
        
        }
        else if ( MODE == MODE_BIG_DECIMAL ){
            
            return new Num( this.bigDecimal.round( MathContext.DECIMAL32 ) );
  
        }
        else if ( MODE == MODE_MIRANDA ){
            
            return new Num( power , base , new int[]{ 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 } , negative );
            
        }
        
        return new Num();
    
    }
    public Num mod( int modulus ){
        
        if ( this.less( modulus ) ){

            return new Num( 0 );
            
        } 
        
        Num low = this.div( modulus ).round();
        Num divTimes = new Num( modulus ).mul( low );
        
        return this.min( divTimes );
        
    }
    
    private double getPower(){ //ONLY FOR CONSTRUCTOR
        
        return power;
        
    }
    
    private int getBase(){ //ONLY FOR CONSTRUCTOR
        
        return base;
        
    }
    
    private int[] getMiranda(){ //ONLY FOR CONSTRUCTOR
        
        return miranda;
        
    }
    
    private boolean getNegative(){ //ONLY FOR CONSTRUCTOR
        
        return negative;
        
    }
    
}
