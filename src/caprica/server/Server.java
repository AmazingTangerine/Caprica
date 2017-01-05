package caprica.server;


import caprica.datatypes.Config;
import caprica.encyption.RSA;
import caprica.system.Subroutine;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server {

    private ArrayList< Connection > connections = new ArrayList<>();

    private Subroutine commandServer;
    
    private int port;
 
    private String encyptionKey;
    private RSA encypter;
    
    private CommandManager[] managers = null;
    
    public Server( int port , Config config ) {
        
        this.port = port;
        this.encyptionKey = config.get( "key" );
        this.encypter = new RSA( config.get( "rsa_e" ) , config.get( "rsa_d" ) , config.get( "rsa_n" ) );
 
    }
    
    public void addCommandManagers( CommandManager[] managers ){
        
        this.managers = managers;
        
    }
    
    public CommandManager[] getCommandManagers(){
        
        return managers;
        
    }
    
    public void start() throws IOException{
        
        commandServer = new Subroutine( new IncomingClientHandler( new ServerSocket( port ) , this ) );
        commandServer.start();
        
    }
   
    public String getEncyptionKey(){
        
        return encyptionKey;
        
    }
    
    public RSA getEncypter(){
        
        return encypter;
        
    }
    
    public ArrayList< Connection > getConnections(){
        
        return connections;
        
    }
    
    public void addConnection( Connection connection ){
        
        connections.add( connection );
        
    }
    
}

