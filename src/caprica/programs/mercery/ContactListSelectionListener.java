package caprica.programs.mercery;

import caprica.server.Command;
import caprica.system.Output;
import java.util.ArrayList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ContactListSelectionListener implements ListSelectionListener {

    Main main;
    
    public ContactListSelectionListener( Main main ){
        
        this.main = main;
        
    }
    
    @Override
    public void valueChanged( ListSelectionEvent event ) {

        if ( event.getValueIsAdjusting() == false ) {
            
            int pos = event.getFirstIndex();
            
            if ( pos != -1 && main.getWindow().getContacts() != null ){
          
                main.print( "Refreashing message area" );
                
                String contact = main.getWindow().getContacts()[ pos ];
      
                main.getWindow().getMessageContentArea().setText( "" );
                
                for ( Text text : main.messages ){
                
                    if ( text.getAddress().equals( contact ) ){
                    
                        String who = "Them";
                    
                        if ( text.getType() == 2 ){
                        
                            who = "Me";
                        
                        }
                    
                        main.getWindow().getMessageContentArea().append( who + " :" + text.getMessage() + "\n" );
                    
                    }
                
                }
        
            }
            
        }
        
    }

}
