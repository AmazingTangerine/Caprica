package agora.windows;

import agora.datatypes.Num;
import agora.datatypes.Vector;
import agora.drawingtypes.Drawable;
import agora.display.Variables;
import agora.drawingtypes.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class Desktop extends BaseWindow implements Drawable {

    public Desktop(){
        
        super( new Vector( 0 , 0 ) , Variables.BASE_SIZE );
        
    }
    
    @Override
    public void draw( Graphics canvas ) {

        Rectangle background = new Rectangle( super.getPos() , super.getSize() );
        
        canvas.setColor( Color.BLACK );
        
        background.draw( canvas );
        
    }

}
