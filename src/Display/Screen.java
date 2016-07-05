package Display;

import System.Information;
import System.Output;
import Windows.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Screen extends JFrame {

    List< Window > Windows = new ArrayList< Window >();
    
    public Screen( String name ){
        
        super( name );
        
        setUndecorated( true );
        setSize( Information.screenSize().asDimension() );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        add( new ContentManager( this ) );
        
        setVisible( true );
        
        
    }
    
    public void addWindow( Window window ){
        
        Windows.add( window );
        
    }
    
}
