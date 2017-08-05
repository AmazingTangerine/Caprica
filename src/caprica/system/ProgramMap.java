package caprica.system;

import java.util.HashMap;

public class ProgramMap {

    private HashMap< String , Object > data = new HashMap<>();
    
    public void setData( String name , Object value ){
        
        data.put( name , value );
        
    }
    
    public Object getData( String storeName ){
        
        if ( data.containsKey( storeName ) ){
            
            return data.get( storeName );
            
        }
        else {
            
            return "";
            
        }
        
    }
    
}
