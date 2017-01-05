package caprica.windows;

import caprica.datatypes.Vector;
import caprica.drawingtypes.ClickField;
import caprica.system.Output;
import caprica.system.SystemInformation;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Window extends JFrame {
   
    private Vector size; //Size of window
    private Panel panel;

    public Window( Vector size ){
        
        this.size = size;
        
        scale();
        
        panel = new Panel();
        
        this.add( panel );
        this.addMouseListener( new ClickManager( this ) );
        this.pack();
        
        setSize();
  
    }
    
    public Panel getPanel(){
        
        return panel;
        
    }
    
    public void setContentPane( ContentPane contentPane ){
        
        panel.setContentPane( contentPane );
        
    }
    
    /**
     * Scales the window if its larger than the actual screen
     */
    private void scale(){
        
        Vector screenSize = SystemInformation.screenSize();
        
        if ( size.getX().toDouble() > screenSize.getX().toDouble() || size.getY().toDouble() > screenSize.getY().toDouble() ){ //Window larger than actual window
            
            double scaleFactor = 0.01;
            double totalScale = 1;
            
            while ( size.getX().toDouble() > screenSize.getX().toDouble() || size.getY().toDouble() > screenSize.getY().toDouble() ){
                
                totalScale = totalScale - scaleFactor;
                
                size = size.scale( totalScale , totalScale ).round();
                
            }
            
        }
        
    }
    
    /**
     * Sets window size to defined size
     */
    private void setSize(){
        
        Vector actualSize = new Vector( this.getSize() );
        
        while ( !actualSize.equality( size ) ){
            
            this.setSize( size.asDimension() );
            
            actualSize = new Vector( this.getSize() );
           
        }
        
    }
    
}
