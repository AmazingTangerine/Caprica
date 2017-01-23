package caprica.server;

import caprica.encyption.BlockAssembly;
import caprica.encyption.RSA;
import caprica.system.CharacterConstants;
import caprica.system.Control;
import caprica.system.Output;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class InformationBlock {

    public static int blockSize;
    
    String block;
    String message;
    
    private RSA encypter;
    
    public InformationBlock( String message , BlockAssembly assembly , RSA encypter ){
        
        this.message = message;
        this.encypter = encypter;
        
        //Construct block
        block = message;

        if ( block.length() % 8 != 0 ){
            
            int lowerCount = ( int ) Math.floor( ( double ) block.length() / ( double ) 8 ); 
            int difference = ( 8 * ( lowerCount + 1 ) ) - block.length();

            for ( int i = 0 ; i < difference ; i++ ){
                
                block += " ";
                
            }
            
            blockSize = ( lowerCount + 1 ) * 8;
            
        }
        else {
            
            blockSize = message.length();
            
        }
     
        block = assembly.encypt( block );
        
    }
    
    public InformationBlock( ObjectInputStream stream , BlockAssembly assembly , RSA encypter ) throws IOException {
        
        block = "";

        boolean started = false;
        
        Long lastRead = System.currentTimeMillis();

        //Read stream
        while ( true ){
        
            try {
      
                int rawStream = stream.readInt();
                
                if ( rawStream == -1 ){
                    
                    break;
                    
                }
                else {
                
                    int value = encypter.decypt( rawStream );

                    block += ( char ) value;
                    
                    lastRead = System.currentTimeMillis();
                
                }
                    
            }
            catch( Exception e ){}
            
            if ( System.currentTimeMillis() - lastRead > 200 ){
                    
                break;
                    
            }

        }

        blockSize = block.length();
        
        block = assembly.decypt( block );

        //Construct message
        int endConstruct = 0;
        int length = block.length();
        
        for ( int i = length - 1 ; i > 0 ; i-- ){

            if ( block.charAt( i ) != ' ' ){
                
                endConstruct = i;
                
                break;
                
            }
            
        }

        message = "";

        for ( int i = 0 ; i < endConstruct + 1 ; i++ ){

            message += block.charAt( i );
            
        }
 
    }
    
    public String getMessage(){
        
        return message;
        
    }
    
    public void write( ObjectOutputStream stream ) throws IOException {

        for ( int i = 0 ; i < blockSize ; i++ ){

            int encyptedValue = encypter.encypt( ( int ) block.charAt( i ) );

            stream.writeInt( encyptedValue );
            
        }
    
        stream.writeInt( -1 );
        stream.flush();

    }
    
}
