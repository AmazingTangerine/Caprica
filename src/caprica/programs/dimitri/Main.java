package caprica.programs.dimitri;

import caprica.programs.dennis.*;
import caprica.datatypes.Num;
import caprica.language.Interface;
import caprica.system.ConstructedRoutine;
import caprica.system.Output;
import caprica.system.ProgramMap;
import caprica.system.Subroutine;

public class Main implements ConstructedRoutine {

    public String[] arguments;
    public Output output;
    private Window window;
    
    public Window getWindow(){
        
        return window;
        
    }

    @Override
    public String run(){
    
        output = new Output( "Dimitri" );
   
        output.disp( "Starting Dimitri program" );
        
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
 
}
