package caprica.server;

import caprica.system.Output;


public class ServerManager implements CommandManager {
    
    Connection connection;
    Server server;
    
    public ServerManager( Server server ){
        
        this.server = server;
        
    }
    
    public void setConnection( Connection connection ){
        
        this.connection = connection;
        
    }
    
    @Override
    public void Manage( Command command ) {

        
        Command responseCommand = null;
        
        if ( command.get( 0 ).equals( "session" ) ){
            
            Output.print( "Inbound connection arriving with session ID" );
            
            String sessionID = command.get( 1 );
            
            connection.setID( sessionID );
                
            Output.print( "Checking to see if session ID matches previously logged in account" );
                
            for ( Connection matchConnection : server.getConnections() ){
                    
                if ( matchConnection.getIP().equals( sessionID ) ){
                    
                    Output.print( "Match found" );
                    
                    connection.setUsername( matchConnection.getUsername() ); 
                    //Add authority system
                    
                    break;
                    
                }
                
            }
            
        }
        
        if ( responseCommand != null ){
        
            try {
                        
                connection.sendCommand( responseCommand );
                        
            }
            catch( Exception e ){
                            
                Output.print( "Could not send response" );
                            
            }
                                
        }
        
    }
    
}
