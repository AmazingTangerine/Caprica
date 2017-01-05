package caprica.datatypes;
import caprica.datatypes.Num;
import static caprica.system.CharacterConstants.END_OF_LINE;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class InputDataStream {
    
    InputStream inputStream = null;

    public InputDataStream( InputStream referenceStream ) {
        
        this.inputStream = referenceStream;
        
    }
    
    public InputDataStream( File streamFile ) throws FileNotFoundException {
        
        this.inputStream = new FileInputStream( streamFile );
        
    }

    public InputStream getStream(){
        
        return inputStream;
        
    }
    
    public void clear(){
        
        try {
        
            if ( this.inputStream != null ) {
            
                inputStream.reset();
            
            }
        
        }
        catch( Exception e ){}
        
    }
    
    public void close() throws IOException {
        
        this.inputStream.close();
        
    }

    @Override
    public String toString() {

        try {
            
            int line;
            String compound = "";
   
            while ( ( line = inputStream.read() ) != -1 ) {

                if ( line == END_OF_LINE ){
                    
                    break;
                    
                }
 
                compound = compound + "" + ( char ) line;
                     
            }
            
            return compound.trim();
            
        }
        catch ( Exception e ){}
        
        return "";
        
    }


    
    public boolean write( OutputStream stream ) {
        
        try {
            
            int line;
            
            while ( ( line = this.inputStream.read() ) != -1 ){
                
                stream.write( line );
            
            }
            
            stream.flush();
            
            stream.close();
            
            return true;
            
        }
        catch ( Exception line ) {
            
            return false;
        }
        
    }

    public boolean available() {
        
        try {

            return this.inputStream.available() > 0;

        }
      
        catch ( Exception e ){}
        
        return false;
        
    }
    
}

