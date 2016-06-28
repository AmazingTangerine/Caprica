package Datatypes;

import Display.Variables;
import System.Output;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Vector {

    public List< Num > data = new ArrayList<>();
    
    /**
     * @param inputs The coordinates to be inputed
     */
    public Vector( Object... inputs ){
        
        for ( Object input : inputs ){
            
            data.add( new Num( input ) );
            
        }
        
    }
    
    public Vector( Dimension vectorInput ){
        
        data.add( new Num( vectorInput.width ) );
        data.add( new Num( vectorInput.height ) );
        
    }

    /**
     * @return Returns the first number in the vector 
     */
    public Num getX(){
        
        return data.get( 0 );
        
    }
    
    /**
     * @return Returns the second number in the vector 
     */
    public Num getY(){
        
        return data.get( 1 );
        
    }
    
    /**
     * @return Returns the third number in the vector 
     */
    public Num getZ(){
        
        return data.get( 2 );
        
    }
    
    public Vector add( Vector addingVector ){
        
        Vector formedVector = new Vector();
        
        for ( Num i = new Num( 0 ) ; i.less( data.size() ) ; i.increment() ){
            
            if ( i.less( addingVector.data.size() ) ){
                
                formedVector.data.add( data.get( i.toInt() ).add( addingVector.data.get( i.toInt() ) ) );
                
            }
            
        }
        
        return formedVector;
        
    }
    
    public Dimension asDimension(){
        
        return new Dimension( data.get( 0 ).toInt() , data.get( 1 ).toInt() );
        
    }
    
    public String asString(){
        
        Num counter = new Num( 0 );
        String compile = "";
        
        String[] names = new String[]{ "X" , "Y" , "Z" };
        
        for ( Num value : data ){
            
            if ( counter.less( names.length ) ){

                compile += names[ counter.toInt() ] + ":" + value.toDouble() + " ";
                
            }
            else{
                
                compile += counter.toInt() + ":" + value.toDouble() + " ";
                
            }
            
            counter.increment();
            
        }
        
        return compile;
        
    }
    
    public Vector scale( Vector match ){
        
        Vector scaled = new Vector();
        
        Num counter = new Num( 0 );
        
        for ( Num number : match.data ){
            
            if ( counter.less( data.size() ) ){
                
                scaled.data.add( number.multiply( data.get( counter.toInt() ) ) );
                
            }
            else{
                
                break;
                
            }
            
            counter.increment();
            
        }
        
        return scaled;
        
    }
    
    public Vector scaleFactor( Vector match ){
        
        Vector factor = new Vector();
        
        Num counter = new Num( 0 );
        
        for ( Num number : match.data ){
            
            if ( counter.less( data.size() ) ){
                
                factor.data.add( number.div( data.get( counter.toInt() ) ) );
                
            }
            else{
                
                break;
                
            }
            
            counter.increment();
            
        }
        
        return factor;
        
    }
    
    public Vector matchFrom( Vector match , Vector reference ){
        
        return scale( reference.scaleFactor( match ) );
        
    }
    
    public Num magnitude(){ //POP POP
        
        Num sum = new Num( 0 );
        
        for ( Num datum : data ){
            
            sum = sum.add( datum.power( 2 ) );
            
        }
        
        return sum.power( 0.5 );
        
    }
    
}
