package caprica.graphics;

import caprica.datatypes.Vector;
import java.awt.Graphics;

public class Polygon extends Component {

    private Vector[] points = null;
    
    @Override
    public void draw( Graphics plane ){
    
        plane.setColor( super.getColour().getAWTColor() );
 
        if ( points != null ){
            
            int[] xPoints = new int[ points.length ];
            int[] yPoints = new int[ points.length ];
            
            for ( int i = 0 ; i < points.length ; i++ ){
                
                xPoints[ i ] = points[ i ].getX().add( getPos().getX() ).toInt();
                yPoints[ i ] = points[ i ].getY().add( getPos().getY() ).toInt();
                
            }
            
            java.awt.Polygon polygon = new java.awt.Polygon( xPoints , yPoints , points.length );
            
            plane.fillPolygon( polygon );
            
        }
    
    }

    public void setPoints( Vector[] points ){
        
        this.points = points;
        
    }
    
}
