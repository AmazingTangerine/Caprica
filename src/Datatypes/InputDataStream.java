package Datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputDataStream {

    InputStream inputStream = null;
    Reader reader = null;
    
    public InputDataStream( InputStream referenceStream ){
        
        inputStream = referenceStream;
        
    }
    
    public InputDataStream( Reader referenceStream ){
        
        reader = referenceStream;
        
    }
    
    /**
     * Converts the input stream to a string format
     * @return The converted stream
     * @throws java.io.IOException
     */
    @Override
    public String toString() {
        
        try {

            if ( inputStream != null ){

                String compound = "";
            
                Num line;

                if ( inputStream.available() == 0 ){
                
                    return "";
                
                }
                
                while ( ( line = new Num( inputStream.read() ) ).toInt() != -1 ){

                    compound += "" + ( char ) line.toInt();
                
                }

                return compound;
            
            }
            else if ( reader != null ){
            
                if ( !reader.ready() ){
                    
                    return "";
                    
                }
                
                String compound = "";
            
                Num line;

                while ( ( line = new Num( reader.read() ) ).toInt() != -1 ){

                    compound += "" + ( char ) line.toInt();
                
                }

                return compound;
            
            }
            
        }
        catch( Exception E ){}//Nothing we can do since we cannot throw an exception
        
        return "";
        
    }
    
}
