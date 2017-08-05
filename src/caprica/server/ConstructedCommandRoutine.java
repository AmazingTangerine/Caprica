package caprica.server;

import caprica.system.ConstructedRoutine;
import caprica.system.ProgramMap;

public class ConstructedCommandRoutine implements ConstructedRoutine {

    public Command command;
    public Bridge bridge;
    public String[] parameters;
    public ProgramMap map;
    public Command responseCommand = null;
    
    @Override
    public String run() {
 
        return "";
            
    }

    @Override
    public void construct( Object[] arguments ) {

        command = ( Command ) arguments[ 0 ];
        bridge = ( Bridge ) arguments [ 1 ];
            
        parameters = new String[ arguments.length - 2 ];
            
        for ( int i = 2 ; i < arguments.length ; i++ ){
                
            parameters[ i - 2 ] = ( String ) arguments[ i ];
                
        }
            
    }

    @Override
    public void setProgramMap( ProgramMap map ) {
        
        this.map = map;
        
    }

}
