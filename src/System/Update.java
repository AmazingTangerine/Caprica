package System;

import Datatypes.GlobalFile;
import Datatypes.InputDataStream;
import Internet.Webpage;
import java.io.FileNotFoundException;

public class Update {

    public static boolean download(){
                
        try {
        
            InputDataStream agoraGit = Webpage.download( "https://github.com/AmazingTangerine/Agora/archive/master.zip" );

            GlobalFile collection = new GlobalFile( "/Agora/update.zip" );
            collection.writeStream( agoraGit , false );
            
            return true;
            
        }
        catch( Exception E ){
            
            E.printStackTrace();
            
        }
        
        return false;
        
    }

    public static boolean check() throws FileNotFoundException{
        
        download();
        
        if ( new GlobalFile( "/Agora/update.zip" ).unPack() ){
            
            String ourVersion = new InputDataStream( new GlobalFile( "version" ).getStream() ).toString();
            String theirVersion = new InputDataStream( new GlobalFile( "/Agora/Agora-master/version" ).getStream() ).toString();
            
            return ourVersion.equals( theirVersion );

        }
        
        return false;
        
    }
 
    public static boolean start() throws FileNotFoundException {
        
        boolean isCurrentVersion = check();
        
        if ( !isCurrentVersion ){ //Must shut down and reload jar
            
            Control.exec( "./Agora/run.sh &" );
            
            System.exit( 0 );
            
        }
        
        new GlobalFile( "/Agora/update.zip" ).delete();
        new GlobalFile( "/Agora/Agora-master" ).delete();
        
        return false;
        
    }
    
}
