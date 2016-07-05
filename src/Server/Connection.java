package Server;

import Datatypes.InputDataStream;
import Datatypes.OutputDataStream;
import System.Output;
import System.Report;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    
    Socket socket;
    InputDataStream input;
    OutputDataStream output;

    public Connection( Socket inputSocket ) throws IOException {
        
        this.socket = inputSocket;
        this.input = new InputDataStream( this.socket.getInputStream() );
        this.output = new OutputDataStream( this.socket.getOutputStream() );
        
    }

    public void send( String message ) throws IOException {
        
        output.write( message );
        
    }

    public Socket getSocket() {
        
        return socket;
        
    }

    public class Listener extends Thread {
        
        @Override
        public void run() {
            
            while ( true ){
            
                if ( input.available() ){
                  
                    
                    
                }

            }
            
        }
        
    }

}

