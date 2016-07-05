package Main;

import Server.Constants;
import Server.Server;
import System.Output;
import System.Report;

public class ServerStart {

    public static void start(){
        
        Output.log( new Report( "Starting server" ) , "info" );
        
        try {
        
            Server server = new Server( "Main Computer" );
        
        }
        catch ( Exception e ){
            
            Output.log( new Report( "Could not start Main Computer server " , e ) , "server" );
            
        }
        
    }
    
}
