package caprica.graphics;

import caprica.datatypes.Vector;

public class Colour {

    Vector colorVector;
    
    public Colour( int r , int g , int b ){
        
        colorVector = new Vector( r , g , b );
        
    }
    
    public java.awt.Color getAWTColor(){
        
        return new java.awt.Color( colorVector.getX().div( 255 ).toFloat() , colorVector.getY().div( 255 ).toFloat() , colorVector.getZ().div( 255 ).toFloat() );
        
    }
    
}
