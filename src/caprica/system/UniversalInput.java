package caprica.system;

import java.util.Scanner;

public class UniversalInput {

    private String mode;
    
    private Scanner scanner;
    
    public UniversalInput( String mode ){
        
        this.mode = mode;
        
        if ( mode.equals( "scanner" ) ){
            
            scanner = new Scanner( System.in );
            
        }
        
    }
    
    public String nextLine(){
        
        if ( mode.equals( "scanner" ) ){
            
            return scanner.nextLine();
            
        }
        
        return "";
        
    }
    
}
