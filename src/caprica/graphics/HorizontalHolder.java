package caprica.graphics;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import java.awt.Graphics;
import java.util.ArrayList;

public class HorizontalHolder extends Component {

    private ArrayList< Component > localComponents = new ArrayList<>();
    private double[] weights = null;
    
    public void addComponent( Component component ){
        
        localComponents.add( component );
        
    }
    
    public void clearComponents(){
        
        localComponents.clear();
        
    }
    
    public void setWeights( double[] weights ){
        
        this.weights = weights;
        
    }
    
    public ArrayList< Component > getComponents(){
        
        return localComponents;
        
    }
    
    @Override
    public void draw( Graphics plane ){

        Num componentWidth = getBounds().getX().div( localComponents.size() );
            
        Vector componentSize = new Vector( componentWidth , getBounds().getY() );
            
        Num count = new Num( 0 );
        
        for ( int i = 0 ; i < localComponents.size() ; i++ ){
                
            Vector pos = getPos().add( new Vector( componentWidth.mul( i ) , 0 ) );
            Vector size = componentSize;
            
            if ( weights != null ){
                
                if ( weights.length == localComponents.size() ){
                    
                    Num width = getBounds().getX().mul( weights[ i ] );
                    
                    pos = getPos().add( new Vector( count , 0 ) );
                    size = new Vector( width , getBounds().getY() );
                    
                    count = count.add( width );
                    
                }
                
            }
            
            Component component = localComponents.get( i );
            component.setBounds( size );
            component.setPos( pos );
            component.draw( plane );
                
        }
        
    }

}
