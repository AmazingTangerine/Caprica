package caprica.graphics;

import java.awt.Graphics;

public class Oval extends Component {

    @Override
    public void draw( Graphics plane ){
    
        plane.setColor( super.getColour().getAWTColor() );
        
        plane.fillOval( getPos().getX().toInt() , getPos().getY().toInt() , getBounds().getX().toInt() , getBounds().getY().toInt() );
        
    }

}
