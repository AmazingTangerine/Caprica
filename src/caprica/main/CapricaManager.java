package caprica.main;

import caprica.datatypes.ListUtilities;
import caprica.datatypes.SystemFile;
import caprica.server.Bridge;
import caprica.server.Command;
import caprica.server.CommandManager;
import caprica.server.Connection;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.RoutineDatabase;
import caprica.system.SystemInformation;
import java.io.IOException;

public class CapricaManager implements CommandManager {

    private Bridge bridge;
    private RoutineDatabase database;
    
    public CapricaManager(){
        
        database = new RoutineDatabase();
        database.add( 1 , new CapricaRoutines.Store() );
        database.add( 2 , new CapricaRoutines.Print() );
        database.add( 3 , new CapricaRoutines.Identify() );
        database.add( 4 , new CapricaRoutines.Shutdown() );
        database.add( 5 , new CapricaRoutines.Reboot() );
        database.add( 6 , new CapricaRoutines.List() );
        database.add( 7 , new CapricaRoutines.Popup() );
        database.add( 8 , new CapricaRoutines.Exec() );
        database.add( 9 , new CapricaRoutines.Lock() );
        database.add( 10 , new CapricaRoutines.Terminate() );
        database.add( 11 , new CapricaRoutines.Restart() );
        database.add( 12 , new CapricaRoutines.NoTimeForCaution() );
        database.add( 13 , new CapricaRoutines.DownloadFile() );

    }
    
    @Override
    public void Manage( Command command ) {

        int functionID = command.getFunctionNumber();
        String[] parameters = command.getParameters();
      
        database.run( functionID , parameters , command , bridge , Main.map );
        
    }

    @Override
    public void setBridge( Bridge bridge ) {

        this.bridge = bridge;
        
    }

    @Override
    public String getName() {

        return "Caprica";
        
    }

}
