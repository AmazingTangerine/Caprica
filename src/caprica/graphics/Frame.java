package caprica.graphics;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

    private static final double FPS = 30;
    private Vector size;
    private ContentCanvas canvas;
    private Subroutine updater;
    private ArrayList< Component > components = new ArrayList<>();
    
    public Frame( String name , Vector size ){
        
        super( name );
        
        this.size = size;
        
        canvas = new ContentCanvas( this );
        
        this.add( canvas );
        this.setPreferredSize( size.asDimension() );
        this.pack();
        
        this.setVisible( true );
        
        updater = new Subroutine( new Updater() );
        updater.setDelayTime( new Num( 1 / FPS ) );
        updater.start();
        
        this.addMouseListener( new ClickListener() );
        
    }
    
    public void addComponent( Component component ){
        
        components.add( component );
        
    }
    
    public void clearComponentList(){
        
        components.clear();
        
    }
    
    private class Updater implements ThreadRoutine {

        @Override
        public void run() {

            canvas.repaint();
          
        }
   
    }
    
    private class ContentCanvas extends JPanel {
        
        Frame frame;
        
        public ContentCanvas( Frame frame ){
            
            this.frame = frame;
            
        }
        
        @Override
        public void paintComponent( Graphics plane ){
    
            Vector frameSize = new Vector( frame.getContentPane().getSize() );
            Num componentHeight = frameSize.getY().div( components.size() );
            
            Vector componentSize = new Vector( frameSize.getX() , componentHeight );
            
            for ( int i = 0 ; i < components.size() ; i++ ){
                
                Component component = components.get( i );
                component.setBounds( componentSize );
                component.setPos( new Vector( 0 , componentHeight.mul( i ) ) );
                component.draw( plane );
                
            }
            
        }
        
    }
    
    public void buttonCheck( Vector clickPos , ArrayList< Component > components ){
        
        for ( int i = 0 ; i < components.size() ; i++ ){
            
            Component component = components.get( i );
            
            if ( component instanceof HorizontalHolder ){
                
                buttonCheck( clickPos , ( ( HorizontalHolder ) component ).getComponents() );
                
            }
            else if ( component instanceof VerticalHolder ){
                
                buttonCheck( clickPos , ( ( VerticalHolder ) component ).getComponents() );
                
            }
            else if ( component instanceof Button ){
      
                Vector buttonPos = component.getPos();
                Vector buttonSize = component.getBounds();
                
                Vector minBounds = new Vector( buttonPos.getX() , buttonPos.getY() );
                Vector maxBounds = new Vector( buttonPos.getX().add( buttonSize.getX() ) , buttonPos.getY().add( buttonSize.getY() ) );
      
                if ( clickPos.getX().greater( minBounds.getX() ) && clickPos.getX().less( maxBounds.getX() ) ){
                    
                    if ( clickPos.getY().greater( minBounds.getY() ) && clickPos.getY().less( maxBounds.getY() ) ){
                        
                        Button button = ( Button ) component;
                        
                        if ( button.getAction() != null ){
                            
                            new Subroutine( button.getAction() ).runOnce();
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    private class ClickListener implements MouseListener {

        @Override
        public void mouseClicked( MouseEvent event ) {

            buttonCheck( new Vector( event.getX() , event.getY() ) , components );
            
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    
    
    }
    
}
