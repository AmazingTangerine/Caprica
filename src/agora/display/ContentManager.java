package agora.display;

import agora.datatypes.Num;
import agora.drawingtypes.Drawable;
import agora.system.Control;
import agora.windows.Window;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ContentManager extends JPanel {

    Screen caller;
    
    public ContentManager( Screen caller ){
        
        this.caller = caller;
        
        this.setPreferredSize( caller.size() );
        
        ( ( Thread ) new Updater() ).start();
        
    }
    
    @Override
    public void paintComponent( Graphics graphics ) {
            
        super.paintComponent( graphics );
        
        for ( Window window : caller.Windows ){
            
            ( ( Drawable ) window ).draw( graphics );
            
        }

    }
    
    //Refreashes the screen
    public class Updater extends Thread {
    
        public void run(){
            
            while ( true ){
            
                Control.sleep( new Num( Variables.FPS ).inverse() );
            
                caller.repaint();
                
            }
            
        }
    
    }
            
}
