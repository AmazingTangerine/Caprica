package caprica.internet;

import caprica.system.Control;
import caprica.system.SystemInformation;
import caprica.datatypes.Config;
import caprica.system.Output;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetworkInformation {
    
    public static String externalIP(){
      
        try {
        
            URL whatismyip = new URL( "http://checkip.amazonaws.com" );
            BufferedReader in = null;
        
            try {
            
                in = new BufferedReader( new InputStreamReader( whatismyip.openStream() ) );
            
                String ip = in.readLine();
            
                return ip;
            
            } 
            finally {
           
                if ( in != null ) {
               
                    try {
                    
                        in.close();
                
                    } 
                    catch ( IOException e ) {}
            
                }
        
            }
        
        }
        catch( IOException exception ){
            
            //Output.print( "Error: Could not get external ip" , exception );
            
            return null;
            
        }
        
    }
    
    public static String internalIP(){
        
        try {
        
            InetAddress IP = InetAddress.getLocalHost();
            return IP.getHostAddress();
        
        }
        catch( UnknownHostException e ){}
        
        return "Unknown";
            
    }
    
    public static String getMAC(){
        
        String MAC = "02:00:00:00:00:00";   
        
            try {

                byte[] mac = new byte[ 0 ];
            
                Enumeration< NetworkInterface > enumerator = NetworkInterface.getNetworkInterfaces();
            
                while ( enumerator.hasMoreElements() ){
                
                    NetworkInterface network = enumerator.nextElement();
                
                    if ( network.isUp() ){
                    
                        mac = network.getHardwareAddress();
                    
                        break;
                    
                    }
                
                }

                StringBuilder builder = new StringBuilder();
        
                for ( int i = 0 ; i < mac.length ; i++ ) {
            
                    builder.append( String.format( "%02X%s" , mac[ i ] , ( i < mac.length - 1 ) ? ":" : "" ) );    
            
                }
        
                MAC = builder.toString();
        
            }
            catch( Exception e ){}
        
        
        
        
        return "MAC";
        
    }
    
    public static boolean ping( String URL ){
        
        if ( SystemInformation.getOS().equals( "Windows" ) ){
            
            String linuxResponse = Control.exec( "ping " + URL + " -n 1" , true );
            
            return !( linuxResponse.contains( "unreachable" ) || linuxResponse.contains( "could not find host" ) );
            
        }
        else {
            
            String linuxResponse = Control.exec( "ping " + URL + " -c 1" , true );
            
            return !( linuxResponse.contains( "Unreachable" ) || linuxResponse.contains( "unknownhost" ) );
            
        }
        
    }
    
}
