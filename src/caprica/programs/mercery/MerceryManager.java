package caprica.programs.mercery;

import caprica.main.*;
import caprica.server.Bridge;
import caprica.server.Command;
import caprica.server.CommandManager;
import caprica.system.Output;
import caprica.system.RoutineDatabase;


public class MerceryManager implements CommandManager {

    private Bridge bridge;
    private RoutineDatabase database;
    
    public MerceryManager(){
        
        database = new RoutineDatabase();
        database.add( 501 , new MercuryRoutines.GetContacts() );
        database.add( 502 , new MercuryRoutines.FullTexts() );
        database.add( 503 , new MercuryRoutines.SendText() );
        database.add( 504 , new MercuryRoutines.ContactText() );
        database.add( 505 , new MercuryRoutines.TimeText() );
        
    }
    
    @Override
    public void Manage( Command command ) {

        int functionID = command.getFunctionNumber();
        String[] parameters = command.getParameters();

        database.run( functionID , parameters , command , bridge , caprica.main.Main.map );
        
    }

    @Override
    public void setBridge( Bridge bridge ) {

        this.bridge = bridge;
        
    }

    @Override
    public String getName() {

        return "Mercery";
        
    }

}
