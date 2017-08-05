package caprica.server;

import static caprica.server.CommunicationConstants.KNOCK_OK;
import static caprica.server.CommunicationConstants.KNOCK_SEND;
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
    
    public Output output;
    
    public IncomingClientHandler( ServerSocket inputSocket , Server inputServer ){
            
        serverSocket = inputSocket;
        server = inputServer;
        output = new Output( "Server");
        
    }
    
    public void cancel( Connection clientConnection ){
        
        clientConnection.close();
        
    }
        
    @Override
    public void run() {

        Socket inboundSocket;
        
        try {
                
            output.disp( "Waiting for socket acception" );
            
            inboundSocket = serverSocket.accept();
            
            output.disp( "Socket accepted" );
     
            String IP = inboundSocket.getInetAddress().getHostAddress();
                        
            output.disp( "Inbound connection from " + IP );
       
            Connection clientConnection = new Connection( inboundSocket , IP , server.getEncypter() ); //Sets up connection class, encytion ect
            clientConnection.setServer( server );
            
            Command response = clientConnection.getLastCommand( 10 );
            
            if ( response != null ){
 
                if ( response.getParameters()[ 0 ].equals( KNOCK_SEND ) ){ //Potential fuck up
                    
                    output.disp( "Client sent knock-knock" );
                    output.disp( "Sending ok response" );
                    
                    try {
                
                        clientConnection.sendCommand( new Command( "*" , "*" , -1 , KNOCK_OK ) );
                        
                        Control.sleep( 3 );
             
                        Command lastCommand = clientConnection.getLastCommand( 10 );
                        
                        if ( lastCommand != null ){
                        
                            String incomingComputerName = lastCommand.getParameters()[ 0 ];
      
                            clientConnection.setConnectionDetails( incomingComputerName , SystemInformation.getComputerName() );
                         
                            output.disp( "Adding " + incomingComputerName + " to list" );
                        
                            server.addConnection( clientConnection );
                        
                        }
                        else {
                            
                            output.disp( "Error: Client did not respond to ID request" );
                            
                            cancel( clientConnection );
                            
                        }
                    
                    }
                    catch( IOException e ){
                    
                        output.disp( "Error: Could not send knock ok response" , e );
                    
                        cancel( clientConnection );
                        
                    }
                    
                }
                else {
                    
                    output.disp( "Error: Client sent wrong knock response" );
                    
                    cancel( clientConnection );
                    
                }
           
            }
            else {
                
                output.disp( "Error: Client did not send knock-knock" );
                
                cancel( clientConnection );
                
            }
 
        }
        catch( IOException expection ){
            
            output.disp( "Error: Socket error" , expection );
            
        }

    }
    
}
