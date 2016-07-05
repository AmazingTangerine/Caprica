package Server;

import Server.Connection;
import System.Control;
import System.Output;
import System.Report;
import System.Subroutine;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    
    Socket auxiliarySocket;
    OutputStream auxiliaryStream;
    String IP;
    ArrayList< Connection > subConnections = new ArrayList();

    public Client( Socket auxiliaryInput , String inputIP ) throws IOException {
        
        this.auxiliarySocket = auxiliaryInput;
        this.auxiliaryStream = this.auxiliarySocket.getOutputStream();
        this.IP = inputIP;
        
        new Subroutine( new CloseManager() );
        
    }

    public void add( Connection connection ) {
        
        this.subConnections.add( connection );
        
    }

    public class CloseManager extends Thread {
        
        @Override
        public void run() {
            
            while ( true ){
                
                try {
                    
                    Client.this.auxiliaryStream.write( 1 );
                    Client.this.auxiliaryStream.flush();
                    
                }
                catch ( Exception e ) {
                    
                    Output.log( new Report( "Lost connection with client " + Client.this.IP ) , "connection" );
                    break;
                    
                }
                
                Control.sleep(1);
                
            }
                
        }
            
    }

}

