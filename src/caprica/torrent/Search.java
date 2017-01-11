package caprica.torrent;

import caprica.internet.Webpage;
import caprica.system.Output;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    
    public static Torrent getBestTorrent( String search ){
        
        Torrent torrent = TorrentSorter.bestTorrent( search( search ) , search );
 
        return torrent;
        
    }
    
    public static ArrayList< Torrent > search( String search ){

        ArrayList< Torrent > torrents = new ArrayList<>();
  
        String searchAddress = "https://thepiratebay.org/search/" + search + "/0/10/0";

        try {
        
            String pageHTML = Webpage.download( searchAddress ).toString();
            
            String pageLinksRegex = "<a[^>]+href=[\"']?([^'\"> ]+)[\"']?[^>]*>";
            
            Matcher macher = Pattern.compile( pageLinksRegex ).matcher( pageHTML );
         
            while ( macher.find() ){
                
                String link = macher.group( 1 );
             
                if ( link.contains( "/torrent/" ) ){
                    
                    String torrentHTML = Webpage.download( "https://thepiratebay.org" + link ).toString();
                    
                    torrents.add( new Torrent( torrentHTML ) );
                    
                }
                
            }
            
        }
        catch( Exception e ){ e.printStackTrace(); }
        
        return torrents;
        
    }
    
    
}
