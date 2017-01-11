package caprica.datatypes;

import java.util.HashMap;

public class Config {

    private HashMap< String , String > data = new HashMap<>();
    private SystemFile configFile;
    
    public Config( String configFile ){
        
        this( new SystemFile( configFile ) );
        
    }
    
    public Config( SystemFile configFile ) {
        
        this.configFile = configFile;
        
        String rawData = configFile.toString();
        String[] lines = rawData.split( "\n" );
        
        for ( String line : lines ){
        
            line = line.replace( "" + ( char ) 13 , "" );
            
            if ( line.contains( "=" ) ){
                
                String key = line.split( "=" )[ 0 ];
                String value = line.split( "=" )[ 1 ];

                data.put( key , value );
                
            }
            
        }
        
    }
    
    public boolean hasKey( String keyName ){
        
        return data.containsKey( keyName );
        
    }
    
    public void put( String keyName , String keyValue ){
        
        String fileData = "";

        if ( data.containsKey( keyName ) ){

            for ( String keyNames : data.keySet() ){
                
                if ( keyNames.equals( keyName ) ){
                    
                    fileData += keyName + "=" + keyValue + "\n";
                    
                }
                else {
                    
                    fileData += keyNames + "=" + data.get( keyNames ) + "\n";
                    
                }
                
            }
        
        }
        else {
            
            data.put( keyName , keyValue );
            
            for ( String keyNames : data.keySet() ){
                
                fileData += keyNames + "=" + data.get( keyNames ) + "\n";
    
            }
            
        }
        
        try {
        
            configFile.write( fileData , false );
        
        }
        catch( Exception e ){

            //Output.log( new Report( "Could not update config file" , e ) , "file" );
            
        }
        
    }
    
    public String get( String keyName ){
        
        if ( data.containsKey( keyName ) ){
            
            return data.get( keyName );
            
        }
        
        return "";
        
    }
    
}
