package caprica.main;

import caprica.assimilation.StartupMigration;
import caprica.assimilation.UpdateFiles;
import caprica.datatypes.Config;
import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.language.Interface;
import caprica.programs.dennis.MailChecker;
import caprica.server.Bridge;
import caprica.server.CommandManager;
import caprica.server.Server;
import caprica.system.ConstructedRoutine;
import caprica.system.Output;
import caprica.system.ProgramMap;
import caprica.system.Subroutine;
import caprica.system.SystemInformation;
import caprica.system.Control;
import caprica.systemtray.Tray;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static int connectionPort;
    public static Config config;
    private static Server server;
    public static Bridge mainLink = null;
    public static ProgramMap map = new ProgramMap();
    public static Output output;
    public static Interface languageInterface;
    
    public static void main( String[] arguments ) throws IOException {

        output = new Output( "Caprica" );
        
        if ( arguments.length == 2 ){
            
            if ( arguments[ 0 ].equals( "time" ) ){
                
                int minutes = Integer.parseInt( arguments[ 1 ] );
                
                Control.sleep( minutes * 60 );
                
            }
            
        }
        
        output.disp( "Starting Caprica" );

        //Load config files
        config = new Config( SystemInformation.getAppData() + "info.conf" );
        
        String[] defaultNames = new String[]{ "port" , "mainName" , "mainIP" , "mainLocalIP" };
        String[] defaultValues = new String[]{ "25560" , "VIKI" , "173.32.244.2" , "192.168.0.10" };
   
        for ( int i = 0 ; i < defaultNames.length ; i++ ){
            
            if ( !config.hasKey( defaultNames[ i ] ) ){
                
                config.put( defaultNames[ i ] , defaultValues[ i ] );
                
            }
         
        }
        
        UpdateFiles.updateFiles();
        //StartupMigration.migrateStartup();

        connectionPort = Integer.parseInt( config.get( "port" ) );
     
        output.disp( "Attempting to create server" );
        
        server = new Server( "Caprica" , Integer.parseInt( config.get( "port" ) ) );
  
        try {
        
            server.start();
            
            output.disp( "Server online" );
            
        }
        catch( IOException e ){
            
            output.disp( "Error: Could not create server" , e );
            
        }
           
        languageInterface = new Interface();
        
        Subroutine mailChecker = new Subroutine( new MailChecker() );
        mailChecker.setDelayTime( new Num( 5 ) );
        mailChecker.start();
        
        Control.sleep( 3 );
        
        Subroutine mainLinkCheck = new Subroutine( new MainLinkCheck() );
        mainLinkCheck.setDelayTime( new Num( 60 ) );
        mainLinkCheck.start();
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
        
            TraySetup.main();
            
        }
        
        Control.sleep( 10 );
        
        startupPrograms();
       
    }
    
    private static void startupPrograms(){
        
        HashMap< String , ConstructedRoutine > programList = new HashMap<>();
        programList.put( "update" , new caprica.programs.update.Main() ); 
        
        SystemFile startupSequenceFile = new SystemFile( SystemInformation.getAppData() + "startup.txt" );
        
        if ( startupSequenceFile.exists() ){

            String[] startupSequence = startupSequenceFile.toString().split( "\n" );
            
            for ( String sequence : startupSequence ){
                
                String[] data = sequence.split( " " );
                
                String programName = data[ 0 ];
 
                if ( programList.containsKey( programName ) ){
                    
                    String[] parameters = new String[ data.length - 1 ];
                    
                    for ( int i = 1 ; i < data.length ; i++ ){
                        
                        parameters[ i - 1 ] = data[ i ];
                        
                    }
                    
                    ConstructedRoutine program = programList.get( programName );
                    program.construct( parameters );
                    
                    Subroutine routine = new Subroutine( program );
                    routine.runOnce();
                    
                    map.setData( programName , program );
                    
                }
                
            }
            
        }
        
    }
    
}
