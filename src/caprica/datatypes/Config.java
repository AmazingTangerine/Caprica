package caprica.datatypes;

import caprica.system.Output;
import java.util.HashMap;

public class Config {

    private HashMap< String , String > data = new HashMap<>();
    private SystemFile configFile;
    private String newLine = "\n";
    
    public Config( String configFile ){
        
        this( new SystemFile( configFile ) );
        
    }
    
    public Config( SystemFile configFile ) {
        
        this.configFile = configFile;
        
        if ( configFile.exists() ){
        
            String rawData = configFile.toString();
       
            String[] lines = rawData.split( newLine );
        
            for ( String line : lines ){
                
                if ( line.contains( "=" ) ){
                    
                    String key = line.split( "=" )[ 0 ];
                    String value = line.split( "=" )[ 1 ];

                    data.put( key , value );
                
                }
            
            }

        }
        
    }
    
    public boolean hasKey( String keyName ){
        
        return data.containsKey( keyName );
        
    }
    
    public void put( String keyName , String keyValue ){

        data.put( keyName , keyValue );
        
        save();
  
    }
    
    public String get( String keyName ){
        
        if ( data.containsKey( keyName ) ){
            
            return data.get( keyName );
            
        }
        
        return "";
        
    }
    
    private void save(){ //Saves the file
        
        String fileData = "";

        for ( String keyName : data.keySet() ){
 
            fileData += keyName + "=" + data.get( keyName ) + newLine;
            
        }
        
        fileData = StringUtilities.snipLast( fileData );

        configFile.write( fileData , false );
        
    }
    
}
