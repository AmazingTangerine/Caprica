package caprica.server;

import caprica.datatypes.Config;
import caprica.datatypes.SystemFile;
import caprica.datatypes.InputDataStream;
import caprica.datatypes.OutputDataStream;
import caprica.encyption.BlockAssembly;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.SystemInformation;
import caprica.system.ThreadRoutine;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class IncomingClientHandler implements ThreadRoutine {
        
    ServerSocket serverSocket;
    Server server;
    
    public IncomingClientHandler( ServerSocket inputSocket , Server inputServer ){
            
        serverSocket = inputSocket;
        server = inputServer;
        
    }
        
    @Override
    public void run() {

        Socket inboundSocket;
        
        try {
                
            inboundSocket = serverSocket.accept();
     
            String IP = inboundSocket.getInetAddress().getHostAddress();
                        
            Output.print( "Inbound connection from " + IP );
            
            Connection clientConnection = new Connection( inboundSocket , IP , server.getEncyptionKey() , server.getEncypter() );
            
            if ( server.getCommandManagers() != null ){
                
                for ( CommandManager manager : server.getCommandManagers() ){
                    
                    manager.setConnection( clientConnection );
                    clientConnection.addCommandManager( manager );
                    
                }
                
            }
            
            clientConnection.addCommandManager( new ServerManager( server ) );
            
            server.addConnection( clientConnection );
            
        }
        catch( IOException expection ){
            
            Output.print( "Socket error" , expection );
            
        }

    }
    
}
