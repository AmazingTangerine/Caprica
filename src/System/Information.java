package System;

import Datatypes.Vector;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Information {

    /**
     * Gets the size of the screen
     * @return The vector of the screen size
     */
    public static Vector screenSize(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        return new Vector( screenSize );
        
    }
    
}
