package caprica.server;

import static caprica.server.CommunicationConstants.COMMAND_DILEMETER;
import caprica.system.Output;

public class Command {
    
    //To
    //;
    //From
    //;
    //Function #
    //;
    //Parameters
    //;
    //END
    
    private String stringCommand = null;
    private int functionNumber = -1;
    private String[] parameters = null;
    private String to = null;
    private String from = null;
    private static final int offset = 0;
    
    public String getTo(){
        
        return to;
        
    }
    
    public String getToComputer(){
        
        return to.split( "\\$" )[ 0 ];
        
    }
    
    public String getToProgram(){
        
        return to.split( "\\$" )[ 1 ];
        
    }
    
    public String getFrom(){
        
        return from;
        
    }
    
    public String getFromProgram(){
        
        return from.split( "\\$" )[ 1 ];
        
    }
    
    public String getFromComputer(){
        
        return from.split( "\\$" )[ 0 ];
        
    }
    
    public String[] getParameters(){
        
        return parameters;
        
    }
    
    public int getFunctionNumber(){
        
        return functionNumber;
        
    }
    
    public Command( String command ){

        stringCommand = command;

        if ( command.contains( "" + COMMAND_DILEMETER ) ){
            
            if ( command.split( "" + COMMAND_DILEMETER ).length > 2 ){
         
                String[] datum = command.split( "" + COMMAND_DILEMETER );
       
                to = datum[ offset ];
                from = datum[ offset + 1 ];
                functionNumber = Integer.parseInt( datum[ offset + 2 ] );
            
                if ( functionNumber == 13 ){
                    
                    parameters = new String[ 2 ];
                    parameters[ 0 ] = datum[ offset + 3 ];
                    
                    String fileConstruction = "";
                    
                    for ( int i = offset + 3 + 1 ; i < datum.length ; i++ ){
                        
                        fileConstruction += datum[ i ];
                        
                        if ( i != datum.length - 1 ){
                            
                            fileConstruction += COMMAND_DILEMETER;
                            
                        }
                        
                    }
                    
                    parameters[ 1 ] = fileConstruction;
                    
                }
                else {
                
                    if ( datum.length == 4 + offset ){
            
                        parameters = null;
                
                    }
                    else {
                
                        parameters = new String[ datum.length - ( 4 + offset ) ];
  
                        for ( int i = 3 + offset ; i < datum.length - 1 ; i++ ){
    
                            parameters[ i - ( 3 + offset ) ] = datum[ i ];
                
                        }
            
                    }
                
                }
            
            }
            
        }
        
    }
    
    public Command( String to , String from , int functionNumber , String... parameters ){

        this.functionNumber = functionNumber;
        this.parameters = parameters;
        
        this.to = to;
        this.from = from;
        
        stringCommand = to + COMMAND_DILEMETER + from + COMMAND_DILEMETER + functionNumber + COMMAND_DILEMETER;
        
        for ( String parameter : parameters ){
            
            stringCommand += parameter + COMMAND_DILEMETER;
            
        }
        
        stringCommand += "END";
        
    }
    
    @Override
    public String toString(){
        
        if ( stringCommand != null ){
        
            return stringCommand;
            
        }
            
        return "null";
        
    }
    
}
