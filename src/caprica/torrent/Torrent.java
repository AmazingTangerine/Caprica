package caprica.torrent;

import caprica.system.Output;
import java.util.ArrayList;

public class Torrent {
    
    private String pageTitle;
    private int seeders;
    private int leachers;  
    private ArrayList< String > comments = new ArrayList<>();
    private String magnetLink;
    
    public Torrent( String html ){
        
        pageTitle = html.split( "<div id=\"title\">" )[ 1 ].split( "</div>" )[ 0 ].trim();
        seeders = Integer.parseInt( html.split( "<dt>Seeders:</dt>" )[ 1 ].split( "</dd>" )[ 0 ].replace( "<dd>" , "" ).trim() );
        leachers = Integer.parseInt( html.split( "<dt>Leechers:</dt>" )[ 1 ].split( "</dd>" )[ 0 ].replace( "<dd>" , "" ).trim() );
        magnetLink = html.split( "icon-magnet.gif'\\);\" href=\"" )[ 1 ].split( "\" title=" )[ 0 ];
     
        for ( String halfComment : html.split( "<div class=\"comment\">" ) ){
            
            if ( halfComment.contains( "</div>" ) && !halfComment.contains( "<html>" ) ){ //Makes it so we dont index before the tag
                
                String comment = halfComment.split( "</div>" )[ 0 ].toLowerCase().trim();
                
                comments.add( comment );
                
            }
            
        }
        
    }
    
    public String getTitle(){
        
        return pageTitle;
        
    }
    
    public int getSeeders(){
        
        return seeders;
        
    }
    
    public int getLeachers(){
        
        return leachers;
        
    }
    
    public ArrayList< String > getComments(){
        
        return comments;
        
    }
    
    public String getMagnetLink(){
        
        return magnetLink;
        
    }
    
}
