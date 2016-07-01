package System;

import Datatypes.GlobalFile;
import static Main.Main.debugMode;

public class Setup {
    
    public static boolean setup(){
        
        int installed = PackageCheck.check();
        
        if ( installed != 0 ){
            
            installed = PackageCheck.check();
            
            if ( installed != 0 ){
                
                Output.log( new Report( "Could not install packages" ) , "critical" );
                
                return false;
                
            }
            
        }
        
        if ( !( new GlobalFile( "/Agora/Agora.jar" ).exists() ) ){
        
            if ( !System.getProperty( "user.dir" ).equals( "/Agora" ) && !debugMode ){ //Instalation of jar
            
                Control.exec( "mkdir /Agora" );
                Control.exec( "mv Agora.jar /Agora/Agora.jar" );
                Control.exec( "mv version /Agora/version" );
                Control.exec( "mv run.sh /Agora/run.sh" );
                
                Control.exec( "chmod +x /Agora/run.sh" );
                
                Control.exec( "sh /Agora/run.sh" );
                
            }
        
        }
        
        if ( Internet.Information.hasInternet() ){
        
            try { 
            
                if ( !System.getProperty( "user.dir" ).equals( "/Java/Agora" ) && !debugMode ){
                
                     Update.start();
                
                }
                        
            }   
            catch( Exception e ){
            
                Output.print( "Error: could not update" );
                
                Output.log( new Report( "Could not update Agora" , e ) , "error" );
                
                //Add a reminder to check update
                
            }
            
            
        }
        
        return true;
 
    }
    
}
