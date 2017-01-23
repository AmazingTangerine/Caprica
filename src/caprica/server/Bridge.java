package caprica.server;

import caprica.datatypes.Config;
import caprica.encyption.RSA;
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
    
    public void connect( String inputServerIP  , int port ) throws IOException {
         
        Output.print( "Connecting to " + inputServerIP );
        
        connecting = true;
        
        Socket connectingSocket = new Socket( inputServerIP , port );
        
        connection = new Connection( connectingSocket , inputServerIP , encyptionKey , encypter );
        
        Output.print( "Connection succesful" );
        
        Command response = connection.getLastCommand( 2 );
        
        if ( response != null ){
        
            Output.print( "Server sent response" );
            
            if ( response.get( 0 ).equals( CommunicationConstants.KNOCK_KNOCK.get( 0 ) ) ){
            
                Output.print( "Knock knock accepted" );
                
                connection.sendCommandSurpressed( new Command( CommunicationConstants.KNOCK_RESPONSE , "" ) );
            
            }
            else {
                
                Output.print( "Server knock not accepted" );
                
            }
        
        }
        else {
            
            Output.print( "Server did not respond to knock" );
            
        }
        
        connecting = false;
        
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

