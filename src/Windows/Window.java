package Windows;

import Datatypes.Vector;
import java.awt.Graphics;

public interface Window {

    public Vector getSize();
    public void setSize( Vector input );
    
    public void setPos( Vector input );
    public Vector getPos();
    
    
}
