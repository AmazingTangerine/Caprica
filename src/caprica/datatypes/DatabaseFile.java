package caprica.datatypes;

import java.util.ArrayList;

public class DatabaseFile {

    private String dataDilemeter = ";";
    
    private SystemFile file;
    private String[] titles = null;
    private ArrayList< String[] > contents = new ArrayList<>();
    
    public void setTitles( String[] titles ){
        
        this.titles = titles;
        
        save();
        
    }
    
    public DatabaseFile( String filePath ){
        
        this( new SystemFile( filePath ) );
        
    }
    
    public DatabaseFile( SystemFile file ){
        
        this.file = file;
        
        if ( file.exists() ){ //Load file contents
            
            String rawContents = file.toString();
            
            if ( rawContents != null ){
                
                String[] lines = rawContents.split( "\n" );
                
                if ( lines.length != 0 ){
                
                    titles = lines[ 0 ].split( dataDilemeter );
                
                    for ( int i = 1 ; i < lines.length ; i++ ){
                    
                        contents.add( lines[ i ].split( dataDilemeter ) );
                    
                    }
                
                }
                
            }
            
        }
        else { //Create new database file
            
            file.create();
            
        }
        
    }
    
    public ArrayList< String[] > get( String where , String equals ){
        
        ArrayList< String[] > instances = new ArrayList<>();
        
        int titleIndex = -1;
        
        for ( int i = 0 ; i < titles.length ; i++ ){
            
            if ( titles[ i ].equals( where ) ){
                
                titleIndex = i;
                
                break;
                
            }
            
        }
        
        if ( titleIndex != -1 ){
            
            for ( String[] datum : contents ){
                
                if ( datum[ titleIndex ].equals( equals ) || equals.equals( "*" ) ){
                    
                    instances.add( datum );
                    
                }
                
            }
            
        }
        
        return instances;
        
    }
    
    public void remove( String where , String equals ){
        
        int titleIndex = -1;
        
        for ( int i = 0 ; i < titles.length ; i++ ){
            
            if ( titles[ i ].equals( where ) ){
                
                titleIndex = i;
                
                break;
                
            }
            
        }
        
        if ( titleIndex != -1 ){
            
            for ( String[] datum : contents ){
                
                if ( datum[ titleIndex ].equals( equals ) ){
                    
                    contents.remove( datum );
                    
                }
                
            }
            
        }
        
        save();
        
    }
    
    public void clear(){
        
        contents.clear();
        save();
        
    }
    
    public void put( String... data ){
        
        if ( data.length == titles.length ){ //Has to be
            
            contents.add( data );
         
            save();
            
        }
        
    }
    
    private void save(){ //Save data to file
        
        String writeData = "";
        
        for ( int i = 0 ; i < titles.length ; i++ ){
            
            writeData += titles[ i ];
            
            if ( i != titles.length - 1 ){
                
                writeData += dataDilemeter;
                
            }
            
        }
        
        writeData += "\n";
        
        for ( int i = 0 ; i < contents.size() ; i++ ){
            
            for ( int x = 0 ; x < contents.get( i ).length ; x++ ){
                
                writeData += contents.get( i )[ x ];
                
                if ( x != contents.get( i ).length - 1 ){
                    
                    writeData += dataDilemeter;
                    
                }
                
            }
            
            if ( i != contents.size() - 1 ){
                
                writeData += "\n";
                
            }
            
        }
        
        file.write( writeData , false );
        
    }
    
}
