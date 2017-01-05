package caprica.datatypes;

import caprica.system.Output;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OutputDataStream {
    
    OutputStream outputStream = null;

    public OutputDataStream( OutputStream referenceStream ) {
        
        this.outputStream = referenceStream;
        
    }
    
    public OutputDataStream( File streamFile ) throws FileNotFoundException {
        
        this.outputStream = new FileOutputStream( streamFile );
        
    }

    public void write( String message ) throws IOException {
        
        for ( int x = 0 ; x < message.length(); ++x ){
            
            this.outputStream.write( message.charAt( x ) );
            
        }
        
        this.outputStream.flush();

    }
    
    public void write( InputDataStream inputDataStream ) throws IOException {
        
        InputStream inputStream = inputDataStream.inputStream;
        
        int size = inputStream.available();
        int count = 0;
        int line;
        
        while ( count < size ){
        
            if ( ( line = inputStream.read() ) != -1 ){
            
                this.outputStream.write( line );
            
                count++;
                
            }
        
        }
        
    }
    
    public void close() throws IOException {
        
        this.outputStream.close();
        
    }
    
}

