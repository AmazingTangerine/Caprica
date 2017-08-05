package caprica.programs.dennis;

import caprica.internet.EmailMessage;
import caprica.internet.Inbox;
import caprica.system.Output;
import caprica.system.ThreadRoutine;
import java.util.ArrayList;

public class MailChecker implements ThreadRoutine {

    Inbox inbox;
    
    public MailChecker(){
        
        inbox = new Inbox( "dennisreynolds400@gmail.com" , "awel-260" );
        
        
    }
    
    @Override
    public void run() {
 
        ArrayList< EmailMessage > messages = inbox.readInbox( true );
        
        if ( messages != null ){
        
            for ( EmailMessage email : messages ){
            
                String message = email.getContents();
    
                if ( message != null & caprica.main.Main.languageInterface != null ){
            
                    String response = caprica.main.Main.languageInterface.process( message );
            
                    inbox.sendEmail( email.getFrom() , "Dennis" , response );
            
                }
            
            }
        
        }
        
    }

}
