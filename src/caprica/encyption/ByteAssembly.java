package caprica.encyption;

import caprica.system.Output;

public class ByteAssembly {

    byte key;
    
    public ByteAssembly( byte key ){
        
        this.key = key;
        
    }
    
    public ByteAssembly( int key ){
        
        this.key = ( byte ) key;
        
    }
    
    public String encypt( String message ){
        
        byte tree = key;
        
        String construction = "";
        
        for ( byte value : message.getBytes() ){
            
            byte product = ( byte )( value ^ tree );

            System.out.println( Output.interpretObject( value ) + "^" + Output.interpretObject( tree ) + "=" + Output.interpretObject( product ) );
            
            construction += ( char )( product );
            
            tree = product;
            
        }
        
        return construction;
        
    }
    
}
