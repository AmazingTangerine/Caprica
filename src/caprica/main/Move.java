package caprica.main;

import caprica.datatypes.SystemFile;
import java.io.IOException;

public class Move {

    public static void main( String[] arguments ) throws IOException {
        
        String movePath = "C:\\Users\\Julia\\Documents\\Nebula\\app\\libs\\";
        String[] distPaths = { "C:/Users/Julia/Documents/Projects/Caprica/dist/" , "C:\\wamp64\\www\\" };
        
        for ( String distPath : distPaths ){
        
            SystemFile distFolder = new SystemFile( distPath );
        
            for ( SystemFile subFile : distFolder.listWholeContents() ){
            
                SystemFile moveFile = new SystemFile( subFile.getFilePath().replace( distPath , movePath ) );
            
                if ( moveFile.exists() ){
                
                    moveFile.delete();
                
                }
            
                subFile.copy( moveFile );
            
            }
        
        }
        
    }
    
}
