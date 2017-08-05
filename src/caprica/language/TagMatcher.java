package caprica.language;

import caprica.system.Output;
import java.util.ArrayList;
import java.util.HashMap;

public class TagMatcher {

    private HashMap< String[] , String > database;
    
    public TagMatcher(){
        
        loadDatabase();
        
    }
    
    public String matchTag( ArrayList< String > tags ){
      
        int count;
        double threshold = 0.7;
        String[] bestKeys = null;
        
        for ( String[] keys : database.keySet() ){
            
            count = 0;
            
            for ( int i = 0 ; i < tags.size() ; i++ ){
                
                for ( int x = 0 ; x < keys.length ; x++ ){
                    
                    if ( tags.get( i ).equals(  keys[ x ] ) ){
                        
                        count++;
                        
                        break;
                        
                    }
                    
                }

                
            }
            
            int length = keys.length;

            if ( ( double ) count / length > threshold ){
                
                threshold = ( double ) count / length;
                
                bestKeys = keys;
             
            }
            
        }
        
        if ( bestKeys != null ){
            
            return database.get( bestKeys );
            
        }
        
        return "null";
        
    }
    
    private void loadDatabase(){
        
        database = new HashMap<>();
        database.put( new String[]{ "greeting" } , "greeting" );
        database.put( new String[]{ "what" , "weather" } , "weather" );
        database.put( new String[]{ "what" , "time" } , "time" );
        database.put( new String[]{ "where" , "self" } , "location" );
        database.put( new String[]{ "what" , "address" } , "external_ip" );
        database.put( new String[]{ "what" , "external" , "address" } , "external_ip" );
        database.put( new String[]{ "what" , "internal" , "address" } , "internal_ip" );
        
    }
    
}
