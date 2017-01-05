package caprica.drawingtypes;

import caprica.datatypes.Vector;
import java.awt.Graphics;

public class Color {
  
    private Vector colorVector;
    
    public Color( Vector colorVector ){
        
        this.colorVector = colorVector;
        
    }
    
    public void draw( Graphics graphics ){
        
        java.awt.Color color = new java.awt.Color( colorVector.getX().toInt() , colorVector.getY().toInt() , colorVector.getZ().toInt() );
        
        graphics.setColor( color );
        
    }
    
}
