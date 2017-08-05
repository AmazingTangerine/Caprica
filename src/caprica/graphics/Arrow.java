package caprica.graphics;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import caprica.system.Output;
import java.awt.Graphics;

public class Arrow extends Component {

    private Num angle = new Num( 0 );
    
    @Override
    public void draw( Graphics plane ){
    
        plane.setColor( super.getColour().getAWTColor() );

        Num deltaX = getBounds().getY().div( 2 );
        
        Num[] xPoints = new Num[]{ new Num( 0 ) , getBounds().getX().min( deltaX ) , new Num( 0 ) , deltaX , getBounds().getX() , deltaX };
        Num[] yPoints = new Num[]{ new Num( 0 ) , getBounds().getY().div( 2 ) , getBounds().getY() , getBounds().getY() , getBounds().getY().div( 2 ) , new Num( 0 ) };
    
        Vector[] points = new Vector[ xPoints.length ];
        
        for ( int i = 0 ; i < points.length ; i++ ){
            
            Vector point = new Vector( xPoints[ i ] , yPoints[ i ] );
            Vector centre = getBounds().scale( 0.5 , 0.5 );
            
            Num xTransform = centre.getX().add( point.getX().min( centre.getX() ).mul( angle.cos() ).min( point.getY().min( centre.getY() ).mul( angle.sin() ) ) );
            Num yTransform = centre.getY().add( point.getX().min( centre.getX() ).mul( angle.sin() ).add( point.getY().min( centre.getY() ).mul( angle.cos() ) ) );
  
            points[ i ] = new Vector( xTransform , yTransform );
            
        }
        
        Polygon arrowPolygon = new Polygon();
        arrowPolygon.setColour( getColour() );
        arrowPolygon.setPos( getPos() );
        arrowPolygon.setPoints( points );
        arrowPolygon.draw( plane );
        
    }
    
    public void setAngle( Num angle ){
        
        this.angle = angle;
        
    }
    
}
