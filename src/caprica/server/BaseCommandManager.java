package caprica.server;

import caprica.datatypes.Config;
import caprica.datatypes.SystemFile;
import caprica.encyption.BlockAssembly;
import caprica.system.Output;
import caprica.system.SystemInformation;

public class BaseCommandManager implements CommandManager {

    Connection connection;

    public void setConnection( Connection connection ){
        
        this.connection = connection;
         
   }
    
    @Override
    public void Manage( Command command ) {

        Command responseCommand = null;
        
        switch( command.get( 0 ) ){
            
            case "print":
 
                Output.print( command.get( 1 ) );
            
            case "data":
                
                String key = command.get( 1 );
                String value = command.get( 2 );
                
                connection.getDataPool().put( key , value );
                
                Output.print( connection.getIP() + " set datapool key " + key + " to " + value );
                
            case "system":
            
                switch( command.get( 1 ) ){
                    
                    case "kill": //End Java program
                    
                        Output.print( "Remote kill command activated" );
                        
                        responseCommand = new Command( "print" , "Killing program" );
                        
                        try {
                        
                            connection.sendCommand( responseCommand );
                            
                            System.exit( 0 );
                        
                        }
                        catch( Exception e ){
                            
                            Output.print( "Could not respond to server, not killing" );
                            
                        }
   
                }
  
            case "user":

                String username = command.get( 2 );
                String password = command.get( 3 );
                    
                Config userList = new Config( new SystemFile( SystemInformation.getAppData() + "users.conf" ) );
                
                switch( command.get( 1 ) ){
                    
                    case "create":

                        if ( userList.hasKey( username ) ){
                            
                            responseCommand = new Command( "print" , "User already exists" );
                            
                        }
                        else {
                            
                            responseCommand = new Command( "print" , "Account created" );
                            
                            //Hashing password
                            BlockAssembly passwordAssembly = new BlockAssembly( connection.getEncyptionKey() );
                            String encyptedPassword = passwordAssembly.encypt( password );
                            
                            userList.put( username , encyptedPassword );
       
                        }
                        
                        break;
       
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
