package caprica.internet;

import caprica.system.Output;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Inbox {
    
    private String emailAddress;
    private String secondaryPassword;
    private String password;
    private String host = null;
    
    public Inbox( String emailAddress , String password , String secondaryPassword ){
        
        this.emailAddress = emailAddress;
        this.secondaryPassword = secondaryPassword;
        this.password = password;
        
        if ( emailAddress.contains( "@gmail.com" ) ){
            
            host = "imap.gmail.com";
            
        }
        else if ( emailAddress.contains( "@uoit.net" ) ){
            
            host = "imap.gmail.com";
            
        }
        
    }
   
    public Inbox( String emailAddress , String password ){
        
        this( emailAddress , password , password );
        
    }
    
    public void sendEmail( String email , String subject , String contents ){
        
        //Output.print( "Opening email session" );
        
        Session emailSession = initilizeSecurity( "smtp" );
        
        MimeMessage message = new MimeMessage( emailSession );

        try {
        
            //Output.print( "Creating email" );
            
            message.setFrom( new InternetAddress( emailAddress ) );
            message.addRecipient( Message.RecipientType.TO , new InternetAddress( email ) );
            message.setSubject( subject );
            message.setText( contents );
            
            //Output.print( "Sending email to " + email + " from " + emailAddress );
            
            try {
                
                Transport.send( message );
                
                //Output.print( "Email successfully sent" );
                
            }
            catch( MessagingException e2 ){
                
                //Output.print( "Error: Could not send email" , e2 );
                
            }

        }
        catch( MessagingException e ){
            
            //Output.print( "Error: Email compilation failed" , e );
            
        }

    }
    
    public Session initilizeSecurity( String protocal ){
        
        Properties properties = new Properties();

        properties.setProperty( "mail.store.protocol" , protocal );
        
        if ( protocal.equals( "imaps" ) ){
        
            properties.setProperty( "mail.imap.ssl.enable" , "true");
            properties.put( "mail.imaps.ssl.trust" , "*" );
              
        }
        else {
            
            properties.put( "mail.smtp.auth" , "true" );
            properties.put( "mail.smtp.starttls.enable" , "true" );
            properties.put( "mail.smtp.host" , "smtp.gmail.com" );
            properties.put( "mail.smtp.port" , "587" );
            properties.put( "mail.smtp.ssl.trust" , "smtp.gmail.com" );
            
        }
        
        Session emailSession = Session.getInstance( properties ,
                    
            new javax.mail.Authenticator() {
                    
                protected PasswordAuthentication getPasswordAuthentication() {
				
                    return new PasswordAuthentication( emailAddress , secondaryPassword );
		
                }
                    
            }
            
        );
            
        return emailSession;
        
    }
   
    public ArrayList< EmailMessage > readInbox( boolean delete ){
        
        if ( host != null ){
        
            try {
        
                Store store = initilizeSecurity( "imaps" ).getStore( "imaps" );
        
                try {

                    store.connect( host , emailAddress , password );
  
                    //Output.print( "Connected to inbox" );
                    //Output.print( "Reading inbox" );
                
                    Folder emailFolder = store.getFolder( "INBOX" );
                    emailFolder.open( Folder.READ_WRITE );

                    // retrieve the messages from the folder in an array and print it
                    Message[] messages = emailFolder.getMessages();

                    if ( messages != null ){
                    
                        ArrayList< EmailMessage > emailMessages = new ArrayList<>();
                    
                        for ( Message message : messages ){
                    
                            emailMessages.add( new EmailMessage( message ) );
                            
                            if ( delete ){
                                
                                message.setFlag( Flags.Flag.DELETED , true );
                                
                            }

                        }
                    
                        return emailMessages;
                    
                    }
                    
                    emailFolder.close( false );
                    store.close();
                
                }
                catch( MessagingException e2 ){
          
                    //Output.print( "Error: Could not connect to inbox" , e2 );
                
                }

            }
            catch( NoSuchProviderException e ){
            
                //Output.print( "Error: Invalid provider for email" , e );
            
            }
            
        }
        
        return null;
        
    }
    
}
