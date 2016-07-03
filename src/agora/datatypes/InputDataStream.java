package agora.datatypes;

import agora.datatypes.GlobalFile;
import agora.datatypes.Num;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class InputDataStream {
    
    InputStream inputStream = null;
    Reader reader = null;

    public InputDataStream( InputStream referenceStream ) {
        
        this.inputStream = referenceStream;
        
    }

    public InputDataStream( Reader referenceStream ) {
        
        this.reader = referenceStream;
        
    }

    @Override
    public String toString() {
        
        try {
            
            if ( this.inputStream != null ) {
                
                Num line;
                String compound = "";
                
                if ( this.inputStream.available() == 0 ) {
                    
                    return "";
                    
                }
                
                while ( ( line = new Num( this.inputStream.read() ) ).toInt() != -1) {
                    
                    compound = compound + "" + ( char ) line.toInt();
                    
                }
                
                return compound;
                
            }
            
            if ( this.reader != null ) {
                
                Num line;
                
                if ( !this.reader.ready() ) {
                    
                    return "";
                    
                }
                
                String compound = "";
                
                while ( ( line = new Num( this.reader.read() ) ).toInt() != -1 ) {
                    
                    compound = compound + "" + ( char ) line.toInt();
                    
                }
                
                return compound;
                
            }
            
        }
        catch (Exception e ){}
        
        return "";
        
    }

    public static void download( String fileAddress ) { 
        
        GlobalFile downloadLocation = new GlobalFile( fileAddress );
        
        if ( downloadLocation.exists() ) {
            
            downloadLocation.delete();
            
        }
        
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
            
            if ( this.inputStream != null ) {
                
                return this.inputStream.available() > 0;
                
            }
            
            if ( this.reader != null ) {
                
                return this.reader.ready();
                
            }
            
        }
      
        catch (Exception e ){}
        
        return false;
        
    }
}

