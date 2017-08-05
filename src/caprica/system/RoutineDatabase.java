package caprica.system;

import caprica.server.Bridge;
import caprica.server.Command;
import caprica.server.Connection;
import caprica.server.ConstructedCommandRoutine;
import java.util.HashMap;

public class RoutineDatabase {
    
    private HashMap< Integer , ConstructedCommandRoutine > database = new HashMap<>();
    
    public RoutineDatabase(){}
   
    public RoutineDatabase( HashMap< Integer , ConstructedCommandRoutine > database ){
       
       this.database = database;
       
    }
   
    public void add( int address , ConstructedCommandRoutine routine ){
       
       database.put( address , routine );
       
    }
   
    public int getSize(){
        
        return database.size();
        
    }
    
    public void run( int ID , String[] parameters , Command command , Bridge bridge , ProgramMap map ){

        if ( database.containsKey( ID ) ){
       
            Object[] arguments = new Object[ parameters.length + 2 ];
            arguments[ 0 ] = command;
            arguments[ 1 ] = bridge;
        
            for ( int i = 2 ; i < arguments.length ; i++ ){
            
                arguments[ i ] = parameters[ i - 2 ];
            
            }
       
            database.get( ID ).construct( arguments );
            database.get( ID ).setProgramMap( map );
            database.get( ID ).run();

            if ( database.get( ID ).responseCommand != null ){
  
                try {
    
                    bridge.sendCommand( database.get( ID ).responseCommand );
               
                }
                catch( Exception e ){
                    
                    
                }
                
            }
            
        }
        
    }
   
}
