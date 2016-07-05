package Internet;

import System.Control;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class Information {
    
    /**
     * Determines weather or not the system has an external internet connection based on ping
     * @return true if the system has an external connection
     */
    public static boolean haveInternet(){
        
        String pingOutput = Control.exec( "ping www.google.com" , false );

        return !pingOutput.equals( "ping: unknown host www.google.com\n" );
        
    }
    
    public static String externalIP(){
        
        if ( haveInternet() ){
        
            String externalResult = Control.exec( "wget http://ipinfo.io/ip -qO -" );
        
            if ( externalResult.length() > 1 ){
        
                return externalResult;
            
            }
        
        }
        
        return "Unknown";
        
    }
    
    public static String internalIP(){
        
        try {
        
            return InetAddress.getLocalHost().getHostAddress();
        
        }
        catch( Exception e ){}
        
        return "Unknown";
            
    }
    
    public static String getMAC(){
        
        try {
        
            byte[] mac = NetworkInterface.getByInetAddress( InetAddress.getLocalHost() ).getHardwareAddress();
        
            StringBuilder builder = new StringBuilder();
        
            for ( int i = 0 ; i < mac.length ; i++ ) {
            
                builder.append( String.format( "%02X%s" , mac[ i ] , ( i < mac.length - 1 ) ? ":" : "" ) );    
            
            }
        
            return builder.toString();
        
        }
        catch( Exception e ){}
        
        return "Unknown";
        
    }
    
}
