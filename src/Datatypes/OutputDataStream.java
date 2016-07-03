package Datatypes;

import java.io.IOException;
import java.io.OutputStream;

public class OutputDataStream {
    
    OutputStream outputStream = null;

    public OutputDataStream( OutputStream referenceStream ) {
        
        this.outputStream = referenceStream;
        
    }

    public void write( String message ) throws IOException {
        
        for ( int x = 0 ; x < message.length(); ++x ){
            
            this.outputStream.write( message.charAt( x ) );
            
        }
        
        this.outputStream.flush();
        
    }
}

