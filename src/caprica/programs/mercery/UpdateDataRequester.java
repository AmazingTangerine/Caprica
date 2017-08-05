package caprica.programs.mercery;

import caprica.server.Bridge;
import caprica.server.Command;
import static caprica.server.CommunicationConstants.SMS_INFO_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SUB_SPLIT;
import caprica.system.Control;
import caprica.system.ThreadRoutine;
import java.util.ArrayList;

public class UpdateDataRequester implements ThreadRoutine {

    private Main main;
    
    public UpdateDataRequester( Main main ){
        
        this.main = main;
        
    }
    
    @Override
    public void run() {

        Bridge bridge = caprica.main.Main.mainLink;
        
        if ( bridge != null ){
            
            if ( bridge.isAlive() ){
                
                String lastUpdate = caprica.main.Main.config.get( "lastUpdate" );
                    
                Command updateCommand = new Command( "SM-G930W8$Mercery" , "VIKI$Caprica" , 505 , "fullTextList" , lastUpdate );
                
                bridge.sendCommandSuppressed( updateCommand );
                
            }
            
        }
        
    }

}
