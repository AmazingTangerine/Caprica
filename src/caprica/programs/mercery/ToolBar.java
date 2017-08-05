package caprica.programs.mercery;

import caprica.programs.diligence.*;
import caprica.system.ThreadRoutine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class ToolBar extends JMenuBar {

    private HashMap< String , HashMap< String , ThreadRoutine > > actionList = new HashMap<>();
    private Main main;
    
    public ToolBar( Main main ){
     
        this.main = main;
        
        HashMap< String , ThreadRoutine > actions = new HashMap<>();

        actionList.put( "File" , actions );
        
        initializeToolBar();
        
    }
    
    private void initializeToolBar(){
        
        String[] menuNames = new String[]{ "File" , "Add" };
        String[][] subMenuNames = new String[][]{ {  } , {} };
        
        for ( int i = 0 ; i < menuNames.length ; i++ ){
            
            JMenu menu = new JMenu( menuNames[ i ] );
            MenuListener listener = new MenuListener( menuNames[ i ] , main );
           
            
            this.add( menu );
            
        }
        
    }
    
    private class MenuListener implements ActionListener {
   
        String parent;
        Main main;
        
        private MenuListener( String parent , Main main ){
            
            this.parent = parent;
            this.main = main;
            
        }
        
        @Override
        public void actionPerformed( ActionEvent event ) {

            String name = event.getActionCommand();
 
                if ( actionList.containsKey( parent ) ){
                
                    actionList.get( parent ).get( name ).run();
                
                }
            
            
            
        }
    
    }
    
}
