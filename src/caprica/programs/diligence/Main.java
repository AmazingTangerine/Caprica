package caprica.programs.diligence;

import caprica.system.ConstructedRoutine;
import caprica.system.Output;
import caprica.system.ProgramMap;

public class Main implements ConstructedRoutine {

    public String[] arguments;
    public Output output;
    private Window window;
  
    public Window getWindow(){
        
        return window;
        
    }

    @Override
    public String run(){
    
        output = new Output( "Diligince" );
   
        Output.print( "Starting Diligence program" );
        
        window = new Window( this );
    
        return "";
        
    }
    
    @Override
    public void construct( Object[] objectArguments ) {

        this.arguments = new String[ objectArguments.length ];
        
        for ( int i = 0 ; i < this.arguments.length ; i++ ){
            
            arguments[ i ] = ( String ) objectArguments[ i ];
            
        }
        
    }

    @Override
    public void setProgramMap( ProgramMap map ) {}
    
    public void print( String text ){
        
        output.disp( text );
        window.setStatusBarText( text );
        
    }
    
}
