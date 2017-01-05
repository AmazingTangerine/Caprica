package caprica.drawingtypes;

import caprica.datatypes.Vector;
import java.awt.Graphics;

public class Rectangle {
    
    private Vector pos;
    private Vector size;
    
    private boolean fill = true;
    
    /**
     * Rectangle shape
     * @param pos Starting pos of rectangle
     * @param size Width and height , starting from pos
     */
    public Rectangle( Vector pos , Vector size ){
        
        this.pos = pos;
        this.size = size;
        
    }
    
    public void draw( Graphics graphics ){
        
        if ( fill ){
            
            graphics.fillRect( pos.getX().toInt() , pos.getY().toInt() , size.getX().toInt() , size.getY().toInt() );
            
        }
        
    }
    
}
