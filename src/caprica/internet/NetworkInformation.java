package caprica.internet;

import caprica.system.Control;
import caprica.system.SystemInformation;
import caprica.datatypes.Config;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetworkInformation {
    
    /**
     * Determines weather or not the system has an external internet connection based on ping
     * @return true if the system has an external connection
     */
    public static boolean hasInternet(){
        
        if ( SystemInformation.getOS().equals( "Linux" ) ){
        
            try {                                                                                                                                                                                                                                 
        
                URL url = new URL( "http://www.google.com" );  
                
                URLConnection connection = url.openConnection();                                                                                                                                                                                  
        
                connection.connect(); 
 
                return true;     
        
            }
            catch( Exception e ){ //Will only throw error if there is not internet
                
                return false;
                
            }
            
        }
        
        return false;
        
    }
   
    public static String externalIP(){
      
        if ( hasInternet() ){
        
            String externalResult = Control.exec( "wget http://ipinfo.io/ip -qO -" );
        
            if ( externalResult.length() > 1 ){
        
                return externalResult.replace( "\n" , "" );
            
            }
        
        }

        return "Unknown";
        
    }
    
    public static String internalIP(){
        
            try {
            
                Enumeration< NetworkInterface > enumerator = NetworkInterface.getNetworkInterfaces();
            
                while ( enumerator.hasMoreElements() ){
                
                    NetworkInterface network = enumerator.nextElement();
                
                    if ( network.isUp() ){
                    
                        Enumeration< InetAddress > addresses = network.getInetAddresses();
                    
                        while ( addresses.hasMoreElements() ){
                        
                            String address = addresses.nextElement().getHostAddress();
                        
                            if ( !address.contains( "eth0" ) && !address.contains( "wlan" ) ){
                            
                                return address;
                            
                            }
                        
                        }
                    
                    }
                
                }
        
                return InetAddress.getLocalHost().getHostAddress();
        
            }
            catch( Exception e ){}
        
        
        
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
