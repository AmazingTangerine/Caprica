package caprica.server;

import caprica.datatypes.Config;
import caprica.encyption.RSA;
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
        this.encypter = new RSA( config.get( "rsa_e" ) , config.get( "rsa_d" ) , config.get( "rsa_n" ) );
        
        online = false;
        
    }
    
    public void connect( String inputServerIP  , int port ) throws IOException {
         
        connecting = true;
        
        Socket connectingSocket = new Socket( inputServerIP , port );
        
        connection = new Connection( connectingSocket , inputServerIP , encyptionKey , encypter );
        
        BaseCommandManager manager = new BaseCommandManager();
        
        connection.addCommandManager( manager );

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

