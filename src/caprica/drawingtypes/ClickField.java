package caprica.drawingtypes;

import caprica.datatypes.Vector;

public interface ClickField {
    
   public Vector getStartPos();
   public Vector getSize();
    
   public void triggerAction();
   
}
