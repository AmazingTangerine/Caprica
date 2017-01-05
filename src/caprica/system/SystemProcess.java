package caprica.system;

public class SystemProcess {

    private String name = "";
    private String PID = "";
    private String memoryUsage = "";
    
    public SystemProcess( String name , String PID ){
        
        this.name = name;
        this.PID = PID;
        
    }
    
    public void setMemoryUsage( String memoryUsage ){
        
        if ( memoryUsage.contains( " K" ) ){
        
            memoryUsage = memoryUsage.replace( " K" , "" );
   
            memoryUsage = "" + Double.parseDouble( memoryUsage ) / ( 8 * 1000000 );
            
            if ( memoryUsage.contains( "E" ) ){
                
                memoryUsage = "0.0";
                
            }
            
            this.memoryUsage = memoryUsage;
            
        }
        
        this.memoryUsage = memoryUsage;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public String getPID(){
        
        return PID;
        
    }
    
    public String getMemoryUsage(){
        
        return memoryUsage;
        
    }
    
    public void kill(){
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            Control.exec( System.getenv("windir") + "\\system32\\" + "taskkill.exe /pid " + getPID() );
            
        }
        else {
            
            Control.exec( "kill " + getPID() );
            
        }
        
    }
    
}
