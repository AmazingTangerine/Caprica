package DrawingTypes;

import Datatypes.Num;
import Datatypes.Vector;
import Display.Variables;
import System.Information;
import System.Output;
import java.awt.Graphics;

public class Rectangle implements Drawable {

    Vector size;
    Vector pos;
    
    public Rectangle( Vector pos , Vector size ){
        
        this.pos = pos;
        this.size = size;
        
    }
    
    @Override
    public void draw( Graphics canvas ) {

        //Scale the size
        size = size.matchFrom( Information.screenSize() , Variables.BASE_SIZE );
        
        canvas.fillRect( pos.getX().toInt() , pos.getY().toInt() , size.getX().toInt() , size.getY().toInt() );
        
    }

}
