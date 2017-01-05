package caprica.encyption;

import caprica.datatypes.SystemFile;
import caprica.system.CharacterConstants;
import caprica.system.Output;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class BlockAssembly {

    private BlockPad pad;
    
    public BlockAssembly( String key ){
        
        int[] keySet = new int[ key.length() ];
        
        for ( int i = 0 ; i < key.length() ; i++ ){
            
            keySet[ i ] = ( int ) key.charAt( i );
            
        }
        
        pad = new BlockPad( keySet );
        
    }
    
    public BlockAssembly( int[] key ){
        
        pad = new BlockPad( key );
        
    }
   
    public String encypt( String message ){
        
        String encyptedMessage = "";
 
        int size = message.length();
        int totalBlocks = ( int ) Math.ceil( ( double ) size / ( double ) pad.getBlockLength() );
        int count = 0;
        char[] segment = message.toCharArray();

        for ( int i = 0 ; i < totalBlocks ; i++ ){
            
            int[] rawBlock = new int[ pad.getBlockLength() ];
            
            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
            
                rawBlock[ x ] = ( int ) segment[ ( i * pad.getBlockLength() ) + x ];
                
            }
            
            int[] encyptedBlock = pad.encyptBlock( rawBlock );

            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                int value = encyptedBlock[ x ];
                
                if ( value != -1 ){
                    
                    encyptedMessage += ( char ) value;
                    
                }
                
            }
            
        }
        
        return encyptedMessage;
        
    }
    
    public String decypt( String message ){
        
        String decypted = "";
        char[] splitMessage = message.toCharArray();
        
        int blocks = message.length() / pad.getBlockLength();

        for ( int i = 0 ; i < blocks ; i++ ){

            int[] rawBlock = new int[ pad.getBlockLength() ];
            
            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                rawBlock[ x ] = ( int ) splitMessage[ x + ( i * pad.getBlockLength() ) ];
                
            }
            
            int[] decyptedBlock = pad.decyptBlock( rawBlock );
            
            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                int decyptedCode = decyptedBlock[ x ];

                String decyptedBlockMessage = "" + ( char ) decyptedCode;
                
                if ( !decyptedBlockMessage.equals( CharacterConstants.IGNORE ) ){
                    
                    decypted += decyptedBlockMessage;
  
                }
                
            }

        }
         
        return decypted;
        
    }
    
    public void encypt( SystemFile from , SystemFile to ) throws FileNotFoundException , IOException {
        
        String originalName = from.getFilePath();
        boolean sameFile = false;
        
        if ( from.getFilePath().equals( to.getFilePath() ) ){
        
            sameFile = true;
            
            to = new SystemFile( to.getFilePath() + ".temp" );
            
        }
        else {
        
            to.create();
            
        }
        
        InputStream fromStream = from.getStream();
        OutputStream outStream = to.getOutputStream();
 
        int size = fromStream.available();
        int totalBlocks = ( int ) Math.ceil( ( double ) size / ( double ) pad.getBlockLength() );
        int count = 0;
        
        for ( int i = 0 ; i < totalBlocks ; i++ ){
            
            int[] rawBlock = new int[ pad.getBlockLength() ];
            
            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                rawBlock[ x ] = fromStream.read();
                
            }
            
            int[] encyptedBlock = pad.encyptBlock( rawBlock );

            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                int value = encyptedBlock[ x ];
                
                if ( value != -1 ){
                    
                    outStream.write( value );
                    
                }
                
            }
            
        }
        
        outStream.flush();
        outStream.close();
        
        fromStream.close();
        
        if ( sameFile ){
            
            from.delete();
            to.rename( originalName );
            
        }
  
    }
    
    public void decypt( SystemFile from , SystemFile to ) throws FileNotFoundException , IOException {
        
        to.create();
        
        InputStream fromStream = from.getStream();
        OutputStream outStream = to.getOutputStream();
        
        int size = fromStream.available();
        int totalBlocks = ( int ) Math.ceil( ( double ) size / ( double ) pad.getBlockLength() );
        int count = 0;
        
        for ( int i = 0 ; i < totalBlocks ; i++ ){
            
            int[] rawBlock = new int[ pad.getBlockLength() ];
            
            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                rawBlock[ x ] = fromStream.read();
                
            }
            
            int[] decyptedBlock = pad.decyptBlock( rawBlock );

            for ( int x = 0 ; x < pad.getBlockLength() ; x++ ){
                
                int value = decyptedBlock[ x ];
                
                if ( value != -1 ){
                    
                    outStream.write( value );
                    
                }
                
            }
            
        }
        
        fromStream.close();
        
        outStream.flush();
        outStream.close();
        
    }
    
}
