package caprica.windows;

import caprica.datatypes.Vector;
import caprica.drawingtypes.ClickField;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ClickManager implements MouseListener {

    Window window;
    
    public ClickManager( Window window ){
        
        this.window = window;
        
    }
    
    @Override
    public void mouseClicked( MouseEvent event ) {

        Point point = event.getPoint();
        
        double x = point.getX();
        double y = point.getY();
        
        ArrayList< ClickField > clickFields = window.getPanel().getContentPane().getClickFields();
        
        for ( ClickField clickField : clickFields ){
            
            Vector pos = clickField.getStartPos();
            Vector size = clickField.getSize();
            
            double leftBound = pos.getX().getDouble();
            double rightBound = pos.add( size ).getX().toDouble();
            
            double topBound = pos.getY().toDouble();
            double bottomBound = pos.add( size ).getY().toDouble();
            
            if ( x >= leftBound && x <= rightBound && y >= topBound && y <= bottomBound ){
                
                clickField.triggerAction();
                
            }
            
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
