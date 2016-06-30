package System;

import Datatypes.GlobalFile;
import Datatypes.InputDataStream;
import Internet.Webpage;

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

    public static boolean check(){
        
        download();
        
        if ( new GlobalFile( "/Agora/update.zip" ).unPack() ){
            
            
            
        }
        
        return false;
        
    }
 
    public static boolean start(){
        
        check();
        
        return false;
        
    }
    
}
