package caprica.main;

import caprica.datatypes.ListUtilities;
import caprica.datatypes.StringUtilities;
import caprica.datatypes.SystemFile;
import caprica.server.Command;
import caprica.server.Connection;
import caprica.server.ConstructedCommandRoutine;
import caprica.system.ConstructedRoutine;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.SystemInformation;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CapricaRoutines {

    public static class Store extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String storeName = parameters[ 0 ];
            String storeValue = parameters[ 1 ];
          
            bridge.setStoreValue( storeName , storeValue );
     
            return "";
            
        }

    }
    
    public static class Print extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String printValue = parameters[ 0 ];
     
            Output.print( printValue );
            
            return "";
            
        }

    }
        
    public static class Identify extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String respondStoreName = parameters[ 0 ];

            this.responseCommand = new Command( command.getFrom() , SystemInformation.getComputerName() + "$Caprica" , 1 , respondStoreName , SystemInformation.getComputerName() );
            
            return "";
            
        }

    }
            
    public static class Shutdown extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            Control.shutdown();
            
            return "";
            
        }

    }
                
    public static class Reboot extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            Control.reboot();
            
            return "";
            
        }

    }
                    
    public static class List extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String path = parameters[ 0 ];
            String storeName = parameters[ 1 ];
            
            SystemFile folder = new SystemFile( path );
            String contents = ListUtilities.listToString( folder.listContents() , "#" );
            
            this.responseCommand = new Command( command.getFrom() , SystemInformation.getComputerName() + "$Caprica" , 1 , storeName , contents );
            
            return "";
            
        }

    }
                        
    public static class Popup extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String message = parameters[ 0 ];
            
            Control.popup( message );
            
            return "";
            
        }

    }
                            
    public static class Exec extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String executeCommand = parameters[ 0 ];
     
            Control.exec( executeCommand );
            
            return "";
            
        }

    }
    
    public static class Lock extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            Control.lock();
            
            return "";
            
        }

    }
    
    public static class Terminate extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            System.exit( 0 );
            
            return "";
            
        }

    }
    
    public static class Restart extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            Output output = new Output( "Caprica" );
            output.disp( "Restarting Caprica" );
            
            int minutes = Integer.parseInt( parameters[ 0 ] );
            
            if ( SystemInformation.getOS().equals( "Windows" ) ){
            
                //Create the batch file
                SystemFile restart_file = new SystemFile( SystemInformation.getAppData() + "run.bat" );
                
                if ( !restart_file.exists() ){
                    
                    restart_file.write( "java -jar " + SystemInformation.getAppData() + "Caprica.jar time " + minutes , false );
                    
                }
                
                //Add job to at
                Control.exec( SystemInformation.getAppData() + "run.bat" , false );
         
                System.exit( 0 );
                
            }
            else if ( SystemInformation.getOS().equals( "Linux" ) ){
                
                //Create the sh file
                SystemFile restart_file = new SystemFile( SystemInformation.getAppData() + "run.sh" );
                
                if ( !restart_file.exists() ){
                    
                    restart_file.write( "java -jar /Caprica/Caprica.jar" , false );
                    
                }
                
                //Get time in one minute
                String rawTime = SystemInformation.getTime();
                
                int minute = Integer.parseInt( rawTime.split( ":" )[ 1 ] );
                int hour = Integer.parseInt( rawTime.split( ":" )[ 0 ] );
                
                minute += minutes;
                
                if ( minute > 60 ){ minute = 60 - ( minute % 60 ) ; hour += minute % 60; }
                if ( hour > 24 ){ hour -= 24; }
                
                String time = hour + ":" + minute;
                
                //Add job to at
                Control.exec( "at -f /Caprica/run.sh " + time , true );
                
                Control.sleep( 2 );
                System.exit( 0 );
                
            }
            
            return "";
            
        }

    }
    
    public static class NoTimeForCaution extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            try {
            
                java.awt.Desktop.getDesktop().browse( new URI( "https://youtu.be/a3lcGnMhvsA?t=1m" ) );
            
            }
            catch( IOException | URISyntaxException e ){}
            
            return "";
            
        }
        
    }
   
    public static class DownloadFile extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String fileName = parameters[ 0 ];
            String fileData = parameters[ 1 ];
           
            for ( int i = 0 ; i < 4 ; i++ ){
                
                fileData = StringUtilities.snipLast( fileData );
                
            }
            
            SystemFile downloadFile = new SystemFile( SystemInformation.getAppData() + fileName );
            downloadFile.write( fileData , false );
            
            return "";
            
        }

    }
    
}
