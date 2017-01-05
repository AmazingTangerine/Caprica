package caprica.datatypes;

import caprica.system.Control;
import caprica.system.Output;
import java.util.ArrayList;
import java.util.HashMap;

public class ConsoleTable {

    private HashMap< String , HashMap< String , String > > table;
    
    private ArrayList< String > collumNames;
    private ArrayList< String > rowNames;
    
    public ConsoleTable( String consoleCommand , int offset ){
        
        table = new HashMap<>();
        rowNames = new ArrayList< String >();
        
        String rawData = Control.exec( consoleCommand , true );
        String[] lines = rawData.split( "\n" );
        
        collumNames = removeSpace( lines[ offset ] );

        for ( int i = 1 + offset ; i < lines.length ; i++ ){
            
            ArrayList< String > rowValues = removeSpace( lines[ i ] );
            
            HashMap< String , String > subMap = new HashMap<>();
            
            String rowName;
            int rowOffset;
            
            if ( rowValues.size() > collumNames.size() ){
                
                rowName = rowValues.get( 0 );
                rowOffset = 1;
                
            }
            else {
                
                rowName = "" + ( i - offset );
                rowOffset = 0;
                
            }
            
            rowNames.add( rowName );
            
            for ( int x = rowOffset ; x < rowValues.size() ; x++ ){
              
                if ( x - rowOffset < collumNames.size() ){
        
                    subMap.put( collumNames.get( x - rowOffset ) , rowValues.get( x ) );
                
                }
                
            }
            
            table.put( rowName , subMap );
            
        }
        
    }
    
    public ConsoleTable( String command ){
        
        this( command , 0 );
        
    }

    public ArrayList< String > getCollumNames(){
        
        return collumNames;
        
    }
    
    public ArrayList< String > getRowNames(){
        
        return rowNames;
        
    }
    
    public String get( String row , String collum ){
        
        return table.get( row ).get( collum );
        
    }
    
    public ArrayList< String > removeSpace( String line ){
        
        ArrayList< String > data = new ArrayList<>();
        
        String construction = "";
        
        int count = 0;
        
        for ( char character : line.toCharArray() ){

            if ( count == line.toCharArray().length - 1 ){ //Last character
                
                construction += character;
                data.add( construction );
                
            }
            else {
            
                if ( character == ' ' ){
                
                    if ( !construction.contains( " " ) && !construction.equals( "" ) ){ 

                        data.add( construction );
                
                    }
                
                    construction = "";
                
                } 
                else {
                
                    construction += character;
                
                }
            
            }
            
            count++;
            
        }
        
        return data;
        
    }
    
}
