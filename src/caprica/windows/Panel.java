package caprica.windows;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Panel extends JPanel {
    
    private ContentPane contentPane = null;
    private Subroutine updater;
    
    public Panel(){
        
        updater = new Subroutine( new Updater() );
        updater.setDelayTime( new Num( 1 ).div( 30 ) );
        updater.start();
        
    }
    
    public ContentPane getContentPane(){
        
        return contentPane;
        
    }
    
    public void setContentPane( ContentPane contentPane ){
        
        this.contentPane = contentPane;
        
    }
    
    @Override
    public void paintComponent( Graphics graphics ) {
            
        super.paintComponent( graphics );
        
        if ( contentPane != null ){

            contentPane.setSize( new Vector( this.getSize() ) );
            contentPane.draw( graphics );
        
        }
        
    }
    
    private class Updater implements ThreadRoutine {

        @Override
        public void run() {
            
           Panel.this.repaint();
            
        }
        
        
    }
    
}
