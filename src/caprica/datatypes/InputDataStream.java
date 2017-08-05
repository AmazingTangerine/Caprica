package caprica.datatypes;
import caprica.datatypes.Num;
import static caprica.system.CharacterConstants.END_OF_LINE;
import caprica.system.Output;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
            
            int count = inputStream.available();
            
            if ( count > 0 ){
            
                Long start = System.currentTimeMillis();
  
                BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

                StringBuilder builder = new StringBuilder();
                
                String line;
                
                while ( ( line = reader.readLine() ) != null ){
                    
                    builder.append( line );
                    builder.append( "\n" );
                    
                }
                
                String compound = builder.toString();
                
                Long difference = System.currentTimeMillis() - start;
                    
                if ( difference == 0 ){
                        
                    difference = 1L; 
                        
                }
                    
                double speed = ( count / difference );

                //Output.print( "Stream read speed was " + speed + "kb/s" );
                //Output.print( "Trimming new string" );
                
                reader.close();
         
                return compound;
      
            }
            
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

