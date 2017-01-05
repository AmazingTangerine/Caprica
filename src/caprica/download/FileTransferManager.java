package caprica.download;

import caprica.server.Command;
import caprica.server.CommandManager;
import caprica.server.Connection;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.SystemInformation;

public class FileTransferManager implements CommandManager {

    Connection connection;
    
    public void setConnection( Connection connection ){
        
        this.connection = connection;
        
    }
    
    @Override
    public void Manage( Command command ) {

        Command responseCommand = null;
        
        if ( command.get( 0 ).equals( "file" ) ){

            if ( command.get( 1 ).equals( "download" ) ){ //Server sending file to client
                
                String whatWeWant = command.get( 2 );
    
                new DownloadManager( connection , whatWeWant );
                
            }
            else if ( command.get( 1 ).equals( "send" ) ){ //Client uploading to server
                
                String storeName = command.get( 2 );
                String fileSize = command.get( 3 );
                
                new UploadManager( connection , storeName , Integer.parseInt( fileSize ) );
                
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
