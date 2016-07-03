package agora.windows;

import agora.datatypes.Vector;
import java.awt.Graphics;

public class BaseWindow implements Window {

    Vector size;
    Vector pos;
    
    public BaseWindow( Vector posInput , Vector sizeInput ){
        
        pos = posInput;
        size = sizeInput;
        
    }
    
    @Override
    public Vector getSize() {

        return size;
        
    }

    @Override
    public void setSize( Vector input ) {

        size = input;
        
    }
    
    @Override
    public Vector getPos() {

        return pos;
        
    }

    @Override
    public void setPos( Vector input ) {

        pos = input;
        
    }

}
