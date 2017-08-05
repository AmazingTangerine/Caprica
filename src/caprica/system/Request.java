package caprica.system;

public class Request {
    
    //Request format
    //function #
    //function arguments
    
    private int functionNumber;
    private String[] arguments;
    
    public int getFunctionNumber(){
       
        return functionNumber;
        
    }
    
    public String[] getArguments(){
        
        return arguments;
        
    }
    
    public Request( int functionNumber , String[] arguments ){
        
        this.functionNumber = functionNumber;
        this.arguments = arguments;
        
    }
    
    public Request( int functionNumber ){
        
        this.functionNumber = functionNumber;
        this.arguments = new String[ 0 ]; //No arguments function
        
    }
    
    public Request( String requestString ){
        
        if ( requestString.contains( ";" ) ){
            
            String[] data = requestString.split( ";" );
            
            this.functionNumber = Integer.parseInt( data[ 0 ] );
            this.arguments = new String[ data.length - 1 ];
            
            for ( int i = 0 ; i < arguments.length ; i++ ){
                
                arguments[ i ] = data[ i + 1 ];
                
            }
            
        }
        else {
            
            this.functionNumber = -1;
            
            try {
                
                this.functionNumber = Integer.parseInt( requestString );
                
            }
            catch( Exception e ){}
            
            this.arguments = new String[ 0 ];
            
        }
        
    }
    
}
