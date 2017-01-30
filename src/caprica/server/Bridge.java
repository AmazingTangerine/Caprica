package caprica.server;

import caprica.datatypes.Config;
import caprica.encyption.RSA;
import caprica.system.Control;
import caprica.system.Output;
import java.io.IOException;
import java.net.Socket;

public class Bridge {

    private String serverIP;
    
    private boolean online = true;
    private boolean connecting = false;

    private Bridge self;
    
    private Connection connection;
    
    private String encyptionKey;
    
    private RSA encypter;
    
    public Bridge( Config config ){
        
        this.self = this;
        this.encyptionKey = config.get( "key" );
 
        online = false;
        
    }
    
    public void setEncypter( RSA rsa ){
        
        this.encypter = rsa;
        
    }
    
    public boolean connect( String inputServerIP  , int port ) throws IOException {
         
        Output.print( "Connecting to " + inputServerIP );
        
        connecting = true;
        
        Socket connectingSocket = new Socket( inputServerIP , port );
        
        connection = new Connection( connectingSocket , inputServerIP , encyptionKey , encypter );
        
        Output.print( "Connection succesful" );
        
        Command response = connection.getLastCommand( 10 );
        
        if ( response != null ){
        
            Output.print( "Server sent response" );
            
            if ( response.get( 0 ).equals( CommunicationConstants.KNOCK_KNOCK.get( 0 ) ) ){
            
                Output.print( "Knock knock accepted" );
                Output.print( "Sending accepted response" );
       
                try {
                
                    connection.sendCommand( new Command( CommunicationConstants.KNOCK_RESPONSE , CommunicationConstants.KNOCK_RESPONSE ) );
            
                    Output.print( "Response sent" );
                    
                    return true;
                    
                }
                catch( IOException e ){
                    
                    Output.print( "Could not send accepted response" , e );
                    
                }
                
            }
            else {
                
                Output.print( "Server knock not accepted" );
                
            }
        
        }
        else {
            
            Output.print( "Server did not send knock knock" );
            
        }
        
        connecting = false;
        
        return false;
        
    }
    
    public Connection getConnection(){
        
        return connection;
        
    }
    
    public boolean isConnecting(){
        
        return connecting;
        
    }
    
    public String getServerIP(){
        
        return serverIP;
        
    }
    
    public boolean isAlive(){
        
        return online;
        
    }

}

