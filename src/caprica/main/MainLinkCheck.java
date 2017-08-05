package caprica.main;

import caprica.internet.NetworkInformation;
import caprica.server.Bridge;
import caprica.system.Output;
import caprica.system.ThreadRoutine;
import java.io.IOException;

public class MainLinkCheck implements ThreadRoutine {

    Output output = new Output( "MainLinkCheck" );
    
    @Override
    public void run() {

        //output.disp( "Checking connection to main server" ); 
        
        if ( Main.mainLink == null ){
            
            output.disp( "No connection found" );
            
            Main.mainLink = new Bridge();
            connect();
            
        }
        else {
            
            if ( !Main.mainLink.isAlive() ){
                
                //output.disp( "Connection was terminated" );
                
                connect();
                
            }
            else {
                
                //output.disp( "Connected to server" );
                
            }
            
        }
        
    }
    
    public void connect(){
        
        output.disp( "Attempting to connect to main server" );
        
        try {
            
            String ip = Main.config.get( "mainIP" );
            
            if ( NetworkInformation.externalIP() != null ){
            
                if ( NetworkInformation.externalIP().equals( ip ) ){
                
                    output.disp( "Since on same network as server, using local ip" );
                
                    ip = Main.config.get( "mainLocalIP" );
                
                }
            
            }
            
            Main.mainLink.connect( ip , Main.config.get( "mainName" ) , Integer.parseInt( Main.config.get( "port" ) ) );
            Main.mainLink.addCommandManager( new CapricaManager() );
            
            output.disp( "Connecion successful" );
            
        }
        catch( IOException | NumberFormatException e ){
            
            output.disp( "Error: Could not connect to main server" , e );
            
        }
        
    }

}
