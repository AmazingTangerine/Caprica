package caprica.graphics;

import caprica.datatypes.Vector;
import java.awt.Graphics;

public class Component {
    
    private Colour colour = new Colour( 0 , 0 , 0 );
    private Vector bounds = new Vector( 0 , 0 , 0 );
    private Vector pos = new Vector( 0 , 0 , 0 );
    
    public void setColour( Colour colour ){
        
        this.colour = colour;
        
    }
    
    public void setBounds( Vector bounds ){
        
        this.bounds = bounds;
        
    }
    
    public void setPos( Vector pos ){
        
        this.pos = pos;
        
    }
    
    public Colour getColour(){
        
        return colour;
        
    }
    
    public Vector getBounds(){
        
        return bounds;
        
    }
    
    public Vector getPos(){
        
        return pos;
        
    }
    
    public void draw( Graphics plane ){}
    
}
