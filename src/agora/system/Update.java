package agora.system;

import agora.datatypes.GlobalFile;
import agora.datatypes.InputDataStream;
import agora.internet.Webpage;
import java.io.FileNotFoundException;

public class Update {

    public static void download() throws Exception {

        InputDataStream agoraGit = Webpage.download( "https://github.com/AmazingTangerine/Agora/archive/master.zip" );

        GlobalFile collection = new GlobalFile( "/Agora/update.zip" );
        collection.writeStream( agoraGit , false );

        
    }

    public static boolean check() throws Exception {
        
        download();
        
        if ( new GlobalFile( "/Agora/update.zip" ).unPack() ){
            
            String ourVersion = new InputDataStream( new GlobalFile( "version" ).getStream() ).toString();
            String theirVersion = new InputDataStream( new GlobalFile( "/Agora/Agora-master/version" ).getStream() ).toString();
            
            return ourVersion.equals( theirVersion );

        }
        
        return false;
        
    }
 
    public static boolean start() throws Exception {
        
        boolean isCurrentVersion = check();
        
        if ( !isCurrentVersion ){ //Must shut down and reload jar
            
            Control.exec( "./run.sh | at now + 5 seconds" );
            
            System.exit( 0 );
            
        }
        
        new GlobalFile( "/Agora/update.zip" ).delete();
        new GlobalFile( "/Agora/Agora-master" ).delete();
        
        return false;
        
    }
    
}
