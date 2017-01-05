package caprica.server;

import caprica.datatypes.InputDataStream;
import caprica.datatypes.OutputDataStream;
import static caprica.system.CharacterConstants.END_OF_LINE;
import caprica.system.Control;
import caprica.system.SystemInformation;
import caprica.system.Output;
import caprica.system.ThreadRoutine;
import java.io.File;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class PingHandler implements ThreadRoutine {
        
    private ServerSocket server;
        
    /**
     * The ping handler is just a server that accepts connections for pinging purposes
     * @param inputServer ServerSocket that is the ping server
     */
    public PingHandler( ServerSocket inputServer ){
            
        server = inputServer;
            
    }
        
    public void run() {

        try {
                
            Socket inboundSocket = server.accept();

        } 
        catch( Exception e){}
        
    }
    
}
