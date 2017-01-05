package caprica.datatypes;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Vector {

    public List< Num > data = new ArrayList< Num >();
    
    /**
     * @param inputs The coordinates to be inputed
     */
    public Vector( Object... inputs ){
     
        for ( Object input : inputs ){

            data.add( new Num( input ) );
            
        }
        
    }
    
    public Vector( ArrayList< Num > inputList ){
        
        for ( Num input : inputList ){
            
            data.add( input );
            
        }
        
    }
    
    public Vector(){};
    
    /**
     * Converts swing dimension to our vector class
     * @param vectorInput 
     */
    public Vector( Dimension vectorInput ){
        
        data.add( new Num( vectorInput.width ) );
        data.add( new Num( vectorInput.height ) );
        
    }

    /**
     * Get X value
     * @return Returns the first number in the vector 
     */
    public Num getX(){
        
        return data.get( 0 );
        
    }
    
    /**
     * Get Y value
     * @return Returns the second number in the vector 
     */
    public Num getY(){
        
        return data.get( 1 );
        
    }
    
    /**
     * Get Z value
     * @return Returns the third number in the vector 
     */
    public Num getZ(){
        
        return data.get( 2 );
        
    }
    
    /**
     * Adds all the entries in the vector to form another vector
     * @param addingVector The vector to be added
     * @return The combined vector
     */
    public Vector add( Vector addingVector ){
        
        Vector formedVector = new Vector();
        
        for ( int i = 0 ; i < data.size() ; i++ ){
            
            if ( i < this.data.size() ){
                
                if ( i < addingVector.data.size() ){
                
                    formedVector.data.add( data.get( i ).add( addingVector.data.get( i ) ) );
                
                }
                else {
                    
                    formedVector.data.add( this.data.get( i ) );
                    
                }
                
            }
            
        }
        
        return formedVector;
        
    }
    
    /**
     * For conversion to swing dimension
     * @return The dimension of the vector
     */
    public Dimension asDimension(){
        
        return new Dimension( data.get( 0 ).toInt() , data.get( 1 ).toInt() );
        
    }
    
    /**
     * The vector expressed in string for debug
     * @return The string form of the vector
     */
    @Override
    public String toString(){
        
        int counter = 0;
        String compile = "";
        
        String[] names = new String[]{ "X" , "Y" , "Z" };
        
        DecimalFormat format = new DecimalFormat( "#.####" );
        format.setRoundingMode( RoundingMode.HALF_EVEN );
 
        for ( Num value : data ){
            
            if ( counter < names.length ){

                compile += names[ counter ] + ":" + value.toString() + " ";
                
            }
            else{
                
                compile += counter + ":" + value.toString() + " ";
                
            }
            
            counter++;
            
        }
        
        return compile;
        
    }
    
    /**
     * Scales vector based on another vector, really it just multiplies the two
     * @param match The vector scaler
     * @return The scaled vector
     */
    public Vector scale( Vector match ){
        
        Vector scaled = new Vector();
        
        int counter = 0;
        
        for ( Num number : match.data ){
            
            if ( counter < data.size() ){
                
                scaled.data.add( number.multiply( data.get( counter ) ) );
                
            }
            else{
                
                break;
                
            }
            
            counter++;
            
        }
        
        return scaled;
        
    }
    
    public Vector scale( Object... scaleFactors ){
        
        Vector scaleVector = new Vector( scaleFactors );
        
        return this.scale( scaleVector );
        
    }
    
    /**
     * How a vector compares to another
     * @param match The vector to be compared to
     * @return The scaled information of the vector
     */
    public Vector scaleFactor( Vector match ){
        
        Vector factor = new Vector();
        
        int counter = 0;
        
        for ( Num number : match.data ){
            
            if ( counter < data.size() ){
                
                factor.data.add( number.div( data.get( counter ) ) );
                
            }
            else{
                
                break;
                
            }
            
            counter++;
            
        }
        
        return factor;
        
    }
    
    public Vector matchFrom( Vector match , Vector reference ){
        
        return scale( reference.scaleFactor( match ) );
        
    }
    
    /**
     * Magnitude 
     * @return Returns the magnitude of vector
     */
    public Num magnitude(){ //POP POP
        
        Num sum = new Num( 0 );
        
        for ( Num datum : data ){
            
            sum = sum.add( datum.pow( 2 ) );
            
        }
        
        return sum.sqrt();
        
    }
    
    /**
     * Gets the dot product of two vectors
     * @param match The other vector to be dotted
     * @return The dot product
     */
    public Num dotProduct( Vector match ){
        
        Num product = new Num( 0 );
        int counter = 0;
        
        for ( Num number : match.data ){
            
            if ( counter < data.size() ){
                
                product = product.add( data.get( counter ).add( number ) );
           
            }
            else{
                
                break;
                
            }
            
            counter++;
            
        }
        
        return product;
        
    }
    
    /**
     * Gets the cross product of two vectors but only works for 3d vectors
     * @param match The vector to be crossed
     * @return The crossed vector
     */
    public Vector crossPorduct( Vector match ){
        
        Num i = new Num( getY().multiply( match.getZ() ).add( getZ().multiply( getY() ).multiply( -1 ) ) );
        Num j = new Num( getX().multiply( match.getZ() ).add( getZ().multiply( getX() ).multiply( -1 ) ) ).multiply( -1 );
        Num k = new Num( getX().multiply( match.getY() ).add( getY().multiply( getX() ).multiply( -1 ) ) );
        
        return new Vector( i , j , k );
        
    }
    
    /**
     * Using the dot product formula finds the angle between two vectors
     * @param match The vector to be measured against
     * @return The angle between the vector in radians
     */
    public Num angle( Vector match ){
        
        Num magnitudeA = magnitude();
        Num magnitudeB = match.magnitude();
        Num dotProduct = dotProduct( match );
        
        Num angle = new Num( Math.acos( dotProduct.div( magnitudeA.multiply( magnitudeB ) ).toDouble() ) );
        
        return angle;
        
    }
    
    public Vector distance( Vector point ){
        
        Vector distanceVector = new Vector();
        
        for ( int i = 0 ; i < data.size() ; i++ ){
        
            Num distance = data.get( i ).min( point.data.get( i ) );
            
            distanceVector.data.add( distance );
        
        }
        
        return distanceVector;
            
    }
    
    public Vector add( Object... addingPoints ){
        
        Vector addingVector = new Vector( addingPoints );
        
        return this.add( addingVector );
        
    }
    
    public Num get( int index ){
        
        return this.data.get( index );
        
    }
    
    public boolean equality( Vector compare ){
        
        if ( this.data.size() != compare.data.size() ){
            
            return false;
            
        }
        else {
            
            int matchCount = 0;
            
            for ( int i = 0 ; i < this.data.size() ; i++ ){
                
                if ( this.get( i ).equalsWhole( compare.get( i ) ) ){
                    
                    matchCount++;
                    
                }
                
            }
            
            return this.data.size() == matchCount;
            
        }
        
    }
    
    public Vector round(){
        
        Vector roundedVector = new Vector();
        
        for ( Num point : this.data ){
            
            roundedVector.data.add( new Num( point.toInt() ) );
            
        }
        
        return roundedVector;
        
    }
    
}
