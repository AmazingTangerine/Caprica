package caprica.language;

import caprica.system.Output;
import java.util.ArrayList;

public class Interface {

    TagMatcher tagMatcher;
    TagAssociation tagAssociater;
    ResponseGenerator responseGenerator;
    
    public Interface(){
        
        tagMatcher = new TagMatcher();
        tagAssociater = new TagAssociation();
        responseGenerator = new ResponseGenerator();
        
    }
    
    public String process( String sentence ){
        
        //Spell check
        sentence = Spellchecker.spellCheck( sentence );
        
        //Tag association
        ArrayList< String > forwardTags = tagAssociater.checkTags( sentence );
    
        //Tag match
        String responseTag = tagMatcher.matchTag( forwardTags );
        
        //reverse tag association
        String response = responseGenerator.response( responseTag );
        
        return response;
        
    }
    
}
