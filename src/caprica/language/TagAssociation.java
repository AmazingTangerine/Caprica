package caprica.language;

import java.util.ArrayList;
import java.util.HashMap;

public class TagAssociation {

    private HashMap< String , ArrayList< String > > tags;
    
    public TagAssociation(){
        
        loadTagLibrary();
        
    }
   
    public ArrayList< String > checkTags( String sentence ){
        
        ArrayList< String > foundTags = new ArrayList<>();
        
        for ( String key : tags.keySet() ){
            
            for ( String tag : tags.get( key ) ){
                
                if ( sentence.contains( tag ) ){
                    
                    foundTags.add( key );
                    break;
                    
                }
                
            } 
            
        }
        
        return foundTags;
        
    }
    
    private void loadTagLibrary(){
        
        tags = new HashMap<>();

        String[][] rawTags = new String[][]{
            
            new String[]{ "where" , "location" },
            new String[]{ "greeting" , "hallo" , "hi" , "hello" , "hey" , "holla" },
            new String[]{ "when" },
            new String[]{ "what" },
            new String[]{ "why" },
            new String[]{ "time" },
            new String[]{ "day" },
            new String[]{ "year" },
            new String[]{ "week" },
            new String[]{ "weather" },
            new String[]{ "internal" , "local" , "inner" , "private" },
            new String[]{ "address" , "addy" , "ip" },
            new String[]{ "external" , "public" },
            
        };
        
        
        for ( int x = 0 ; x < rawTags.length ; x++ ){
            
            ArrayList< String > tag = new ArrayList<>();
            
            for ( int y = 0 ; y < rawTags[ x ].length ; y++ ){
                
                tag.add(  rawTags[ x ][ y ] );
                
            }
            
            tags.put( rawTags[ x ][ 0 ] , tag );
            
        }
        
    }

    
}
