package caprica.torrent;

import java.util.ArrayList;

public class TorrentSorter {
    
    public static boolean containsVirus( Torrent torrent ){
        
        boolean hasVirus = false;
        
        for ( String comment : torrent.getComments() ){
            
            if ( comment.contains( "malware" ) || comment.contains( "virus" ) ){
                
                hasVirus = true;
                
                break;
                
            }
            
        }
        
        return hasVirus;
        
    }
    
    public static int scoreTorrent( Torrent torrent ){
        
        String[] positiveRemarks = new String[]{ "thanks" , "good" , "awesome" , "high" , "thx" , "legit" };
        String[] negativeRemarks = new String[]{ "shit" , "low" , "awful" , "wtf" , "missing" , "cam" };
        
        int score = 0;
        
        score = score + ( torrent.getSeeders() / 10 );
        score = score - ( torrent.getLeachers() / 10 );
        
        for ( String comment : torrent.getComments() ){
            
            for ( String positiveRemark : positiveRemarks ){
                
                if ( comment.contains( positiveRemark ) ){
                    
                    score++;
                    
                }
                
            }
            
            for ( String negativeRemark : negativeRemarks ){
                
                if ( comment.contains( negativeRemark ) ){
                    
                    score--;
                    
                }
                
            }
            
        }
        
        return score;
        
    }
    
    public static Torrent bestTorrent( ArrayList< Torrent > torrents , String searchName ){
        
        Torrent bestTorrent = null;
        int bestScore = 0;
        
        for ( Torrent torrent : torrents ){
            
            if ( !containsVirus( torrent ) ){
                
                if ( torrent.getTitle().toLowerCase().contains( searchName.toLowerCase() ) ){
                    
                    int torrentScore = scoreTorrent( torrent );
                    
                    if ( torrentScore > bestScore && torrentScore > 0 ){
                        
                        bestScore = torrentScore;
                        bestTorrent = torrent;
                        
                    }
                    
                }
                
            }
            
        }
        
        return bestTorrent;
        
    }
    
}
