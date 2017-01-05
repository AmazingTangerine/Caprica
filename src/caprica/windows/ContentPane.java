package caprica.windows;

import caprica.datatypes.Vector;
import caprica.drawingtypes.ClickField;
import java.awt.Graphics;
import java.util.ArrayList;

public interface ContentPane {
   
    public void draw( Graphics graphics );
    public void setSize( Vector size );
    public ArrayList< ClickField > getClickFields();
    
}
