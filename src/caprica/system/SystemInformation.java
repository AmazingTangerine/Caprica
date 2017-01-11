package caprica.system;

import caprica.datatypes.ConsoleTable;
import caprica.datatypes.SystemFile;
import caprica.datatypes.Vector;
import java.awt.Toolkit;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import javax.naming.Context;

public class SystemInformation {

    public static boolean SSH = true;
    public static boolean networkScan = true;
    private static Runtime runtime = null;
    
    /**
     * Gets the size of the screen
     * @return The vector of the screen size
     */
    public static Vector screenSize(){

        return new Vector( Toolkit.getDefaultToolkit().getScreenSize() );

    }
    
    public static String getTime(){

        Calendar calendar = Calendar.getInstance();
            
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm:ss" );
        
        return simpleDateFormat.format( calendar.getTime() );
        
        
    }
    
    public static String getDate(){
        
        Calendar calendar = Calendar.getInstance();
            
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy/MM/dd" );
        
        return simpleDateFormat.format( calendar.getTime() );
        
    }
    
    public static Runtime getRuntime(){
        
        if ( runtime == null ){
            
            runtime = Runtime.getRuntime();
            
        }
        
        return runtime;
        
    }
    
    public static String getEnglishDate( String date ){
        
        int month = Integer.parseInt( date.split( "/" )[ 1 ] );
        int day = Integer.parseInt( date.split( "/" )[ 2 ] );
        int year = Integer.parseInt( date.split( "/" )[ 0 ] );
        
        String[] months = new String[]{ "January" , "Febuary" , "March" , "April" , "May" , "June" , "July" , "August" , "September" , "November" , "December" };
       
        String numberSuffix;
        
        switch( day ){
            
            case 1:
                
                numberSuffix = "st";
                break;
                
            case 2:
                
                numberSuffix = "nd";
                break;
                
            case 3:
                
                numberSuffix = "rd";
                break;
                
            default:
                
                numberSuffix = "th";
                break;
                        
                
        }
        
        return months[ month ] + " the " + day + numberSuffix + " ";
        
    }
    
    public static String getOS(){
        
        if ( System.getProperty( "os.name" ).contains( "Windows" ) ){
            
            return "Windows";
            
        }
        
        return "Linux";
        
    }
 
    public static String getAppData(){

        String appData = "/Caprica/";
   
        if ( getOS().equals( "Windows" ) ){
            
            appData = System.getenv( "APPDATA" ).replace( "\\" , "/" ) + "/Caprica/";
            
        } 
   
        SystemFile appFolder = new SystemFile( appData );
        
        if ( !appFolder.exists() ){
            
            appFolder.create();
            
        }
        
        return appData;
        
    }
    
    public static String getComputerName(){
        
  
            Map<String, String> env = System.getenv();
        
            if ( env.containsKey( "COMPUTERNAME" ) ){
        
                return env.get("COMPUTERNAME");
        
            }
            else if ( env.containsKey( "HOSTNAME" ) ){
        
                return env.get("HOSTNAME");
        
            }
        
            else {
            
                return "Unknown";
        
            }
        
        
    
    }
    
    public static double getRuntimeUsage(){
 
        return ( ( double )( getRuntime().totalMemory() - getRuntime().freeMemory() ) / getRuntime().totalMemory() ) * 100;
        
    }
        
    public static double getRAMUsage(){
        
        if ( getOS().equals( "Linux" ) ){

            ConsoleTable ramTable = new ConsoleTable( "free -m" );
 
            int total = Integer.parseInt( ramTable.get( "Mem:" , "total" ) );
            int used = Integer.parseInt( ramTable.get( "Mem:" , "used" ) );
            
            return ( used / ( double ) total ) * 100;
            
        }
        
        return 0;
        
    }
    
    public static double getCPUUsage(){
        
        if ( getOS().equals( "Linux" ) ){
            
            ConsoleTable cpuTable = new ConsoleTable( "top -bn1" , 6 );

            double totalPercent = 0;
            
            for ( String rowName : cpuTable.getRowNames() ){
                
                String cpuStringPercent = cpuTable.get( rowName , "%CPU" );
                double cpuDoublePercent = Double.parseDouble( cpuStringPercent );
                        
                totalPercent += cpuDoublePercent;
                
            }

            return totalPercent;
            
        }
        
        return 0;
        
    }
  
    
    public static ArrayList< SystemProcess > getSystemProcesses(){
        
        ArrayList< SystemProcess > processes = new ArrayList<>();
        
        if ( getOS().equals( "Windows" ) ){
            
            String commandLine = System.getenv("windir") + "\\system32\\" + "tasklist.exe /fo csv /nh";
            
            String executeData = Control.exec( commandLine , false );
            
            for ( String dataLine : executeData.split( "\n" ) ){
            
                dataLine = dataLine.replace( "\"" , "" );
             
                String[] rawData = dataLine.split( "," );
                
                String name = rawData[ 0 ];
                String PID = rawData[ 1 ];
                String sessionName = rawData[ 2 ];
                String sessionNumber = rawData[ 3 ];
                String memoryUsage = rawData[ 4 ];
                
                if ( rawData.length == 6 ){
                    
                    memoryUsage += rawData[ 5 ];
                    
                }
                
                SystemProcess process = new SystemProcess( name , PID );
                process.setMemoryUsage( memoryUsage );
               
                processes.add( process );
                
            }
            
        }
        else {
            
            ConsoleTable data = new ConsoleTable( "ps aux" );
  
            for ( String rowName : data.getRowNames() ){
                
                String name = data.get( rowName , "COMMAND" );
                String PID = data.get( rowName , "PID" );
                String memoryUsage = data.get( rowName , "%MEM" );
                
                SystemProcess process = new SystemProcess( name , PID );
                process.setMemoryUsage( memoryUsage );
               
                processes.add( process );
                
            }
            
        }

        return processes;
        
    }
    
}
