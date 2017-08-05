package caprica.programs.mercery;

import caprica.server.Command;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SendListener implements KeyListener {

    Main main;
    
    public SendListener( Main main ){
        
        this.main = main;
        
    }

    @Override
    public void keyTyped( KeyEvent event ) {

        int code = event.getKeyChar();
        
        if ( code == 10 ){
            
            String address = "+1" + main.getWindow().getContactsContentList().getSelectedValue();
            
            String message = main.getWindow().getSendField().getText();
            main.getWindow().getSendField().setText( "" );
            
            //main.getWindow().getMessageContentArea().append( "Me: " + message + "\n" );
            
            caprica.main.Main.mainLink.sendCommandSuppressed( new Command( "SM-G930W8$Mercery" , "VIKI$Caprica" , 503 , address , message ) );
            
        }
        
    }

    @Override
    public void keyPressed( KeyEvent e ) {}

    @Override
    public void keyReleased( KeyEvent e ) {}
    
}
