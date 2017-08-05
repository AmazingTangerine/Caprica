package caprica.internet;

import caprica.system.Output;
import java.io.IOException;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

public class EmailMessage {
    
    private Message message;
    private Address[] from = null;
    private String subject = null;
    private String contents = null;
    
    public EmailMessage( Message message ){
        
        this.message = message;
        
        try {
        
            this.from =  message.getFrom();
            this.subject = message.getSubject();
       
            MimeMultipart multiPart = ( MimeMultipart ) message.getContent();
                
            this.contents = multiPart.getBodyPart( 0 ).getContent().toString().replace( "\n" , "" );
                
            
        
        }
        catch( IOException | MessagingException e ){
            
            Output.print( "Error: Could not get critical email data" , e );
            
        }
        
    }
    
    public String getFrom(){
        
        String rawAddress = from[ 0 ].toString();
        
        if ( rawAddress.contains( "<" ) ){
            
            rawAddress = rawAddress.split( "<" )[ 1 ];
            
        }
        
        rawAddress = rawAddress.replace( ">" , "" );
        
        return rawAddress;
        
    }
    
    public String getSubject(){
        
        return subject;
        
    }
    
    public String getContents(){
        
        return contents;
        
    }
    
}
