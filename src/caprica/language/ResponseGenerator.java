package caprica.language;

import caprica.internet.NetworkInformation;
import caprica.system.SystemInformation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ResponseGenerator {

    HashMap< String , String[] > responses;
    
    public ResponseGenerator(){
        
        loadDatabase();
        
    }
    
    public String response( String tag ){
        
        String[] possibleResponses = new String[]{};
        
        if ( responses.containsKey( tag ) ){
            
            possibleResponses = responses.get( tag );
           
        }
        else {
            
            switch( tag ){
                
                case "weather":
                    
                    
                    
                case "time":
                    
                    String time = SystemInformation.getTime();
                    possibleResponses = new String[]{
                        "the time is " + time,
                        "it is currently " + time,
                        "it is " + time,
                        
                    };
                    
                    break;
                    
                case "location":
                
                    
                case "external_ip":
                    
                    String ip = NetworkInformation.externalIP();
                    possibleResponses = new String[]{
                        "Your ip is " + ip,
                        "It's " + ip,
                        "The external ip is " + ip,   
                    };
                    
                    break;
                    
                case "internal_ip":
                    
                    ip = NetworkInformation.internalIP();
                    possibleResponses = new String[]{
                        "Your ip is " + ip,
                        "It's " + ip,
                        "The internal ip is " + ip,   
                    };
                    
                    break;
                    
            }
            
        }
        
        if ( possibleResponses.length > 0 ){
            
            Random random = new Random();
            return possibleResponses[ random.nextInt( possibleResponses.length ) ];
            
        }
        
        return "I don't know";
        
    }
    
    private void loadDatabase(){
        
        responses = new HashMap<>();
        responses.put( "greeting" , new String[]{ "hey" , "sup" , "what do you want" } );
        
    }
    
}
