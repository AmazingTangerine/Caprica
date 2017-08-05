package caprica.server;

import caprica.datatypes.Config;
import caprica.encyption.RSA;
import static caprica.server.CommunicationConstants.KNOCK_OK;
import static caprica.server.CommunicationConstants.KNOCK_SEND;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.SystemInformation;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Bridge {

    private Connection connection;
    
    private String serverIP;
    private String programName;
    
    private boolean alive = false;
    
    private Output output = new Output( "Bridge" );
    
    private RSA encypter = null;
    
    private ArrayList< CommandManager > commandManagers;
    
    public Bridge(){
        
        commandManagers = new ArrayList<>();
        
    }
    
    public void addCommandManager( CommandManager... managers ){
        
        for ( CommandManager manager : managers ){
            
            manager.setBridge( this );
            commandManagers.add( manager );
            
        }
        
    }
    
    public boolean sendCommandSuppressed( Command command ){
        
        return sendSuppressedCommand( command );
        
    }
    
    public boolean sendSuppressedCommand( Command command ){
        
        try {
            
            return sendCommand( command );
            
        }
        catch( Exception e ){}
        
        return false;
        
    }
    
    public boolean sendCommand( Command command ) throws Exception{
  
        if ( command.toString().contains( "$" ) ){
        
            if ( command.getToComputer().equals( SystemInformation.getComputerName() ) ){ //Own computer do not need to send
            
                for ( CommandManager manager : commandManagers ){
                
                    if ( command.getToProgram().equals( manager.getName() ) ){
                    
                        manager.Manage( command );
                    
                        return true;
                    
                    }
                
                }
            
            }
            else {
            
                if ( connection != null ){
  
                    if ( connection.getAlive() ){
             
                        return connection.sendCommand( command );
                    
                    }
                    else {
                    
                        throw new Exception( "Bridge connection is not alive" );
                    
                    }
                
                }
                else {
                
                    throw new Exception( "Bridge connection is null" );
                
                }
            
            }
        
        }
        
        return false;
        
    }
    
    public boolean sendFile( Command command ) throws Exception{
  
        if ( command.toString().contains( "$" ) ){
        
            if ( command.getToComputer().equals( SystemInformation.getComputerName() ) ){ //Own computer do not need to send
            
                for ( CommandManager manager : commandManagers ){
                
                    if ( command.getToProgram().equals( manager.getName() ) ){
                    
                        manager.Manage( command );
                    
                        return true;
                    
                    }
                
                }
            
            }
            else {
            
                if ( connection != null ){
  
                    if ( connection.getAlive() ){
             
                        return connection.sendCommand( command );
                    
                    }
                    else {
                    
                        throw new Exception( "Bridge connection is not alive" );
                    
                    }
                
                }
                else {
                
                    throw new Exception( "Bridge connection is null" );
                
                }
            
            }
        
        }
        
        return false;
        
    }
    
    public boolean connect( String inputServerIP , String ID , int port ) throws IOException {
         
        alive = false;
        
        output.disp( "Connecting to " + inputServerIP + " on port " + port );
      
        Socket connectingSocket = new Socket( inputServerIP , port ); //Server accepts and immeaditly creates Connection object
        
        if ( encypter != null ){
        
            connection = new Connection( connectingSocket , inputServerIP , encypter ); //Consider making a null check in connection construction
            
        }
        else {
            
            connection = new Connection( connectingSocket , inputServerIP ); 
        
        }
        
        connection.setBridge( this );
        
        output.disp( "Connection streams established" ); //If we get here then the output and input streams were successfully established
        output.disp( "Sending knock-knock" );
        
        try {
            
            connection.sendCommand( new Command( "*" , "*" , -1 , KNOCK_SEND ) );
            
            output.disp( "Knock-knock sent" );
            
            Command response = connection.getLastCommand( 15 );
            
            if ( response != null ){
 
                if ( response.getParameters()[ 0 ].equals( KNOCK_OK ) ){ //Potential fuck up
                    
                    output.disp( "Server says knock is ok" );
                    output.disp( "Sending ID" );
                   
                    try {
                    
                        connection.sendCommand( new Command( "*" , "*" ,  -1 , SystemInformation.getComputerName() ) );

                        connection.setConnectionDetails( ID , SystemInformation.getComputerName() );
                    
                        alive = true;
                        return true;
                    
                    }
                    catch( Exception e ){
                        
                        output.disp( "Error: Could not send ID" , e );
                    
                        connection.close();
                        
                    }
                    
                }
                else {
                    
                    output.disp( "Error: Server did not respond properly" );
                    connection.close();
                    
                }
                
            }
            else {
                
                output.disp( "Error: Server did not send response" );
                connection.close();
                
            }
            
        }
        catch( IOException eKnockSend ){
            
            output.disp( "Error: Could not send knock knock" , eKnockSend );
            
        }
        
        alive = false;
        
        return false;
        
    }

    public String getStoreValue( String key ){
        
        if ( alive ){
            
            return connection.getStoreValue( key );
            
        }
        
        return "null";
        
    }
    
    public void setStoreValue( String key , String value ){
        
        if ( alive ){
            
            connection.setStoreValue( key , value );
            
        }
       
    }
    
    public void setEncypter( RSA rsa ){
        
        this.encypter = rsa;
        
    }
    
    public String getFrom(){
        
        return SystemInformation.getComputerName() + "$" + programName;
        
    }
    
    public String getServerIP(){
        
        return serverIP;
        
    }

    public boolean isAlive(){
        
        return alive;
        
    }
    
}

