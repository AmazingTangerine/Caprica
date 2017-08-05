package caprica.programs.diligence;

import caprica.datatypes.SystemFile;
import caprica.system.ConstructedRoutine;
import caprica.system.SystemInformation;

public class Reminder {

    private String name;
    private String message;
    private String date;
    private String routineName;
    
    public Reminder( String name , String message , String date , String routineName ){
        
        this.name = name;
        this.message = message;
        this.date = date;
        this.routineName = routineName;
        
    }
    
    public Reminder( String rawData ){
        
        String[] data = rawData.split( "|" );
        this.name = data[ 0 ];
        this.message = data[ 1 ];
        this.date = data[ 2 ];
        this.routineName = data[ 3 ];
        
    }
    
    public Reminder( SystemFile file ){
        
        this( file.toString() );
        
    }
    
    public void save(){
        
        String filePath = SystemInformation.getAppData() + "reminders/" + name + ".txt";
        SystemFile saveFile = new SystemFile( filePath );
        
        String compiled = name + "|" + message + "|" + date + "|" + routineName;
        
        saveFile.write( compiled , false );
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public String getMessage(){
        
        return message;
        
    }
    
    public String getDate(){
        
        return date;
        
    }
    
    public String getRoutineName(){
        
        return routineName;
        
    }
}
