package caprica.server;

import caprica.encyption.RSA;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

    private int port;
    private ServerSocket server;
    
    public HTTPServer( int port , CommandManager[] managers , String encpytionKey , RSA encypter ) throws IOException {
        
        this.port = port;
        
        Output.print( "Starting HTTP server on port " + port );
        
        server = new ServerSocket( port );
        
        Output.print( "Server online" );
        
        while ( true ){
  
            Socket client = server.accept();
            
            Output.print( "Inbound client" );
            
            Connection clientConnection = new Connection( client , encpytionKey , encypter );
            
            for ( CommandManager manager : managers ){
            
                clientConnection.addCommandManager( manager );
            
            }
            
        }
        
    }
    
}
