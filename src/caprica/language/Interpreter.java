package caprica.language;

import caprica.datatypes.SystemFile;
import caprica.system.Output;
import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
    
    public static final int UPPER_SEARCH_LIMIT = 9;
    
    private SystemFile languageFolder;
    private HashMap< Integer , String[] > languageData;
    
    public Interpreter( SystemFile languageFolder ){
        
        this.languageFolder = languageFolder;
        
        ArrayList< SystemFile > languageFiles = languageFolder.listWholeContents();
        languageData = new HashMap<>();
        
        if ( languageFiles.size() > 0 ){
            
            for ( SystemFile languageFile : languageFiles ){
                
                int functionID = Integer.parseInt( languageFile.getName() );
                String contents = languageFile.toString();
                
                languageData.put( functionID , contents.split( "\n" ) );
                
            }
            
        }
        
    }
    
    public Object[] getFunctionInfo( String sentence ){
        
        String[] spaces = sentence.split( " " );
        
        String[] arguments = new String[ UPPER_SEARCH_LIMIT ];
        
        for ( int functionID : languageData.keySet() ){
            
            for ( String line : languageData.get( functionID ) ){
                
                line = line.trim();
                
                int count = 0;
                arguments = new String[ UPPER_SEARCH_LIMIT ];
                
                String[] lineSpaces = line.split( " " ); 

                for ( int i = 0 ; i < lineSpaces.length ; i++ ){
                    
                    if ( i < spaces.length ){
                        
                        if ( lineSpaces[ i ].equals( spaces[ i ] ) ){
                            
                            count++;
                     
                        }
                        else {
                            
                            for ( int x = 0 ; x < UPPER_SEARCH_LIMIT ; x++ ){
                               
                                if ( lineSpaces[ i ].equals( "<<" + x + ">>" ) ){
                                    
                                    arguments[ x ] = spaces[ i ];
                                    
                                    count++;
                         
                                }
                                
                            }
                            
                        }
                        
                    }
                    
                }
                
                if ( count >= lineSpaces.length ){ //Bueno, its a match made in heaven
                    
                    return new Object[]{ functionID , arguments };
                    
                }
                
            }
            
        }
        
        return null;
        
    }
    
}
