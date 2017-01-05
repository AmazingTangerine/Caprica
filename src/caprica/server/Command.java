package caprica.server;

import caprica.system.CharacterConstants;
import static caprica.system.CharacterConstants.COMMAND_INFO_SPLIT;
import static caprica.system.CharacterConstants.COMMAND_REGEX;
import static caprica.system.CharacterConstants.COMMAND_SPLIT;
import caprica.system.Output;

public class Command {
    
    private String[] subCommands = null;
    private String query = "0";
    private boolean hold = false;

    /**
     * Acceptable range is from 39-126
     * @param subCommands Either entry from constructed command or listed entry
     */
    public Command( String... subCommands ){

        if ( subCommands.length == 1 ){ //From sent command
        
            String fromString = subCommands[ 0 ];

            if ( fromString.contains( COMMAND_SPLIT ) && fromString.contains( COMMAND_INFO_SPLIT ) && fromString.contains( COMMAND_REGEX )  ){ //Makes sure it has the command structure
            
                for ( String commandInfo : fromString.split( COMMAND_INFO_SPLIT ) ){
               
                    String key = commandInfo.split( COMMAND_REGEX )[ 0 ];
                    String value = commandInfo.split( COMMAND_REGEX )[ 1 ];
                
                    if ( key.equals( "command" ) ){
                    
                        this.subCommands = value.split( COMMAND_SPLIT );
                    
                    }
                    else if ( key.equals( "query" ) ){
                    
                        query = value;
                    
                    }
                
                }
            
            }
            
        }
        else{
            
            this.subCommands = subCommands;
        
        }
            
    }
    
    public String get( int index ){
        
        if ( subCommands != null ){
        
            if ( index < subCommands.length ){
            
                return subCommands[ index ];
            
            }
        
        }
        
        return "";
        
    }

    public String getQuery(){
        
        return query;
        
    }
    
    public void setQuery( String referenceName ){
        
        query = referenceName;
        
    }
    
    public void hold(){
        
        hold = true;
        
    }
    
    public boolean held(){
        
        return hold;
             
    }
    
    public String laymen(){
        
        String info = "";
        
        if ( subCommands != null ){

            String base = "command" + COMMAND_REGEX;
        
            for ( String command : subCommands ){
            
                base += command + COMMAND_SPLIT;
            
            }
        
            base = base.substring( 0 , base.length() ); //Removes last command split
            
            info = "Command: " + base;
            
            if ( !query.equals( "null" ) ){
                
                info += "\n" + "Query: " + query;
            }
            
            return info;
        
        }

        return "null";
        
    }
    
    @Override
    public String toString(){
        
        if ( subCommands != null ){

            String base = "command" + COMMAND_REGEX;
        
            for ( String command : subCommands ){
            
                base += command + COMMAND_SPLIT;
            
            }
        
            base = base.substring( 0 , base.length() - 1 ); //Removes last command split
        
            base += COMMAND_INFO_SPLIT + "query" + COMMAND_REGEX + query;
        
            return base;
        
        }

        return "null";
        
    }
    
}
