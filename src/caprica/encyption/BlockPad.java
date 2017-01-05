package caprica.encyption;

import caprica.datatypes.SystemFile;
import caprica.system.Output;
import caprica.system.SystemInformation;
import java.util.Random;

public class BlockPad {

    private int blockLength = 8;
    private int[] blocks;
    private int topValue = 256;

    public BlockPad( int[] blocks ){
        
        this.blocks = blocks;
        
    }
    
    public void setKey( int[] key ){
        
        blocks = key;
        
    }
    
    public int[] encyptBlock( int[] dataBlock ){
        
        int[] encyption = new int[ blockLength ];
        
        for ( int i = 0 ; i < blockLength ; i++ ){
            
            int dataBit;
            
            if ( dataBlock[ i ] == -1 ){
            
                dataBit = -1;
                
            }
            else {
                
                dataBit = dataBlock[ i ] + blocks[ i ];
            
                if ( dataBit > topValue ){
                
                    dataBit -= topValue;
                
                }
            
            }
            
            encyption[ i ] = dataBit;
            
        }
        
        return encyption;
        
    }
    
    public int[] decyptBlock( int[] dataBlock ){
        
        int[] decyption = new int[ blockLength ];
        
        for ( int i = 0 ; i < blockLength ; i++ ){
            
            int dataBit;
            
            if ( dataBlock[ i ] == -1 ){
            
                dataBit = -1;
                
            }
            else {
                
                dataBit = dataBlock[ i ] - blocks[ i ];
            
                if ( dataBit < 0 ){
                
                    dataBit += topValue;
                
                }
            
            }
            
            decyption[ i ] = dataBit;
            
        }
        
        return decyption;
        
    }
    
    public int getBlockLength(){
        
        return blockLength;
        
    }
    
}
