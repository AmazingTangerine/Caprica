package caprica.graphics;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import java.awt.Graphics;
import java.util.ArrayList;

public class VerticalHolder extends Component {

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

        Num componentHeight = getBounds().getY().div( localComponents.size() );
            
        Vector componentSize = new Vector( getBounds().getX() , componentHeight );
            
        Num count = new Num( 0 );
        
        for ( int i = 0 ; i < localComponents.size() ; i++ ){
                
            Vector pos = getPos().add( new Vector( 0 , componentHeight.mul( i ) ) );
            Vector size = componentSize;
            
            if ( weights != null ){
                
                if ( weights.length == localComponents.size() ){
                    
                    Num height= getBounds().getY().mul( weights[ i ] );
                    
                    pos = getPos().add( new Vector( 0 , count ) );
                    size = new Vector( getBounds().getY() , height );
                    
                    count = count.add( height );
                    
                }
                
            }
            
            Component component = localComponents.get( i );
            component.setBounds( size );
            component.setPos( pos );
            component.draw( plane );
                
        }
        
    }

}
