package Server;

import Server.Client;
import Server.Connection;
import System.Output;
import System.Report;
import System.Subroutine;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    
    ServerSocket serverSocket;
    Subroutine listener;
    String name;
    int port;
    HashMap< String , Client > Clients = new HashMap();

    public Server(String inputName, int inputPort) throws IOException {
        
        this.name = inputName;
        this.port = inputPort;
        this.serverSocket = new ServerSocket( inputPort );
        
        this.listener = new Subroutine( new Listener() );
        
    }

    public class Listener extends Thread {
        
        @Override
        public void run() {

            while ( true ){
            
                try {
                
                    Socket inboundSocket = Server.this.serverSocket.accept();
                    
                    String IP = inboundSocket.getInetAddress().getHostAddress();
                        
                    Output.log( new Report( "Inbound connection from " + IP ) , "connection" );
                        
                    if ( Clients.containsKey(IP) ) {
                        
                        Output.log( new Report( "Client is allready in list adding new connection" ) , "connection" );
                        Clients.get( IP ).add( new Connection( inboundSocket ) );

                    }
                    else {
                        
                        Output.log( new Report( "New client adding to list" ) , "connection" );
                        Clients.put( IP , new Client( inboundSocket , IP ) );
                        
                    } 
                    
                }
                catch ( Exception e ) {
                    
                    Output.log( new Report( "Could not accept connection with a client" , e ) , "connection" );

                }

            } 
        }
    }

}

