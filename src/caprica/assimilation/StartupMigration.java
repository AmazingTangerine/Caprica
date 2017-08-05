package caprica.assimilation;

import caprica.datatypes.SystemFile;
import caprica.system.Output;
import caprica.system.SystemInformation;

public class StartupMigration {

    //Windows startup location:: C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Startup
    //Linux startup file:: /etc/rc.local
    
    public static void migrateStartup(){ //Ensures that the Caprica program is in startup sequence
        
        Output output = new Output( "StartupMigration" );
        
        output.disp( "Checking to see if Caprica program is in startup" );
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){ //Create bat file and place in startup folder

            SystemFile startupFile = new SystemFile( "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Startup/Caprica.bat" );
            
            if ( !startupFile.exists() ){
                
                output.disp( "Startup bat file was not found, creating one" );
                String batCommand = "java -jar " + SystemInformation.getAppData() + "Caprica.jar";
                startupFile.create();
                
                if ( startupFile.exists() ){
                    
                    output.disp( "Writing to bat file" );
                    boolean writeSuccess = startupFile.write( batCommand , false );
                    
                    if ( writeSuccess ){
                        
                        output.disp( "Command wrote to bat file" );
                        
                    }
                    else {
                        
                        output.disp( "Error: Could not write command to bat file" );
                        output.disp( "Deleting bat file" );
                        
                        startupFile.delete();
                        
                    }
                    
                }
                else {
                 
                    output.disp( "Error: Could not create startup bat file" );
                
                }
                    
            }
            else {
                
                output.disp( "Startup bat file found, no need to create new one" );
                
            }
            
        } 
        else { //Write to startup file
            
            SystemFile startupFile = new SystemFile( "/etc/rc.local" );
            String startupCommand = "pkill java && java -jar " + SystemInformation.getAppData() + "Caprica.jar &";
            
            if ( startupFile.exists() ){
                
                output.disp( "Startup file exists, checking to see if it contains startup command" );
                
                String startupContents = startupFile.toString();
                
                if ( startupContents.contains( startupCommand ) ){
                    
                    output.disp( "Startup file contains startup command" );
                    
                }
                else {
                    
                    output.disp( "Startup command not in startup file" );
                    output.disp( "Adding startup command to startup file" );
                    
                    startupContents = startupContents.replace( "exit 0" , "" );
                    startupContents += startupCommand + "\nexit 0";
                    
                    boolean writeSuccess = startupFile.write( startupContents  , false );
                    
                    if ( writeSuccess ){
                        
                        output.disp( "Command wrote to startup file" );
                        
                    }
                    else {
                        
                        output.disp( "Error: Could not write command to startup file" );
                        
                    }
                    
                }
                
            }
            else {
                
                output.disp( "Startup file does not exists" );
                startupFile.create();
                
                if ( startupFile.exists() ){
                    
                    output.disp( "Writing to bat file" );
                    boolean writeSuccess = startupFile.write( startupCommand + "\nexit 0"  , false );
                    
                    if ( writeSuccess ){
                        
                        output.disp( "Command wrote to bat file" );
                        
                    }
                    else {
                        
                        output.disp( "Error: Could not write command to startup file" );
                        
                    }
                    
                }
                else {
                    
                    output.disp( "Error: Could not create startup file" );
                    
                }
                
            }
            
        }
        
    }
    
}
