package caprica.programs.mercery;

import caprica.datatypes.Num;
import caprica.programs.diligence.*;
import caprica.server.Command;
import caprica.system.ConstructedRoutine;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.ProgramMap;
import caprica.system.Subroutine;
import java.util.ArrayList;

public class Main implements ConstructedRoutine {

    public String[] arguments;
    public Output output;
    private Window window;
    public ArrayList< Text > messages = new ArrayList<>();
    public Long lastUpdate = 0L;
  
    public Window getWindow(){
        
        return window;
        
    }

    @Override
    public String run(){
    
        output = new Output( "Mercery" );
   
        Output.print( "Starting Mercery program" );
        
        window = new Window( this );
     
        Subroutine updateDataListener = new Subroutine( new UpdateDataListener( this ) );
        updateDataListener.setDelayTime( new Num( 5 ) );
        updateDataListener.start();
        
        Subroutine updateDataRequester = new Subroutine( new UpdateDataRequester( this ) );
        updateDataRequester.setDelayTime( new Num( 5 ) );
        updateDataRequester.start();
 
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
