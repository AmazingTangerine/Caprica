package caprica.compression;

import caprica.system.Output;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RunLengthEncoder { //130 bytes/s , ~20% file size reduction

    private InputStream inputStream;
    private List< Byte > data = new ArrayList<>();
    private String rawEncoded;
    
    public RunLengthEncoder( InputStream inputStream ) throws IOException {
        
        this.inputStream = inputStream;
        
        int read;
        
        while ( ( read = inputStream.read() ) != -1 ){
            
            data.add( ( byte ) read );
            
        }

    }
    
    private String byteToString( byte datum ){
        
        return String.format( "%8s" , Integer.toBinaryString( datum & 0xFF ) ).replace( ' ' , '0' );
        
    }
    
    public void convertData(){
        
        rawEncoded = "";
        
        int count = 1;
        
        char currentSet = byteToString( data.get( 0 ) ).charAt( 0 );
        
        int i = 0;
        
        for ( byte datum : data ){
            
            String packet = byteToString( datum );
            
            for ( char bit : packet.toCharArray() ){
                
                if ( bit == currentSet ){
                    
                    count++;
                    
                }
                else {

                    rawEncoded += count + "" + bit;
                            
                    currentSet = bit;
                    count = 1;
                    
                }
                
            }
            
        }
        
        rawEncoded += count + "" + currentSet;
        
    }
    
    public List< Byte > encodeData(){
        
        List< Byte > encoded = new ArrayList<>();
        
        for ( char bit : rawEncoded.toCharArray() ){
            
            encoded.add( ( byte ) bit );
            
        }
        
        return encoded;
        
    }
    
}
