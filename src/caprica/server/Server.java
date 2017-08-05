package caprica.server;


import caprica.datatypes.Config;
import caprica.encyption.RSA;
import caprica.internet.NetworkInformation;
import caprica.system.Output;
import caprica.system.Subroutine;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server {

    private ArrayList< Connection > connections = new ArrayList<>();

    private Subroutine commandServer;
    
    private int port;
 
    private RSA encypter = null;
    
    private String name;
    
    public Server( String name , int port ) {
        
        this.name = name;
        this.port = port;
 
    }
    
    public void setEncypter( RSA rsa ){
        
        this.encypter = rsa;
        
    }
    
    public void start() throws IOException{
        
        new Output( name ).disp( "Starting server with IP " + NetworkInformation.externalIP() + " on port " + port );
        
        commandServer = new Subroutine( new IncomingClientHandler( new ServerSocket( port ) , this ) );
        commandServer.start();
        
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
    
    public void removeConnection( Connection connection ){
        
        connections.remove( connection );
        
    }
    
}

