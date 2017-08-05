package caprica.main;

import caprica.system.ConstructedRoutine;
import caprica.system.SystemInformation;
import caprica.systemtray.Tray;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TraySetup {

    private static String[] names = new String[]{ "Diligence" , "Dennis" , "Mercery" };
    private static ConstructedRoutine[] routines = new ConstructedRoutine[]{ new caprica.programs.diligence.Main() , new caprica.programs.dennis.Main() , new caprica.programs.mercery.Main() };
    
    public static void main(){
        
        Tray tray = new Tray( SystemInformation.getAppData() + "icon.png" );
        tray.addListener( new Listener() );
        
        for ( String name : names ){
        
            tray.add( new MenuItem( name ) );
        
        }
        
    }
    
    private static class Listener implements ActionListener {

        HashMap< String , ConstructedRoutine > programs = new HashMap<>();
        
        private Listener(){

            for ( int i = 0 ; i < names.length ; i++ ){
            
                programs.put( names[ i ] , routines[ i ] );
            
            }
            
        }
        
        @Override
        public void actionPerformed( ActionEvent event ) {

            String name = event.getActionCommand();
            
            if ( programs.containsKey( name ) ){
                
                programs.get( name ).run();
                
            }
            
        }
        
    }
    
}
