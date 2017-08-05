package caprica.programs.mercery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import caprica.datatypes.Config;
import caprica.datatypes.Num;
import caprica.datatypes.StringUtilities;
import caprica.main.CapricaManager;
import caprica.server.Bridge;
import static caprica.server.CommunicationConstants.SMS_INFO_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SUB_SPLIT;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.SystemInformation;
import caprica.system.ThreadRoutine;
import java.io.IOException;

public class PhoneStart {

    public static Context context;
    public static Config config;
    public static Bridge bridge;
    public static String textContents = "";
    
    
    public static class BridgeChecker implements ThreadRoutine {

        @Override
        public void run() {
            
            if ( isWifiConnected() ){

                    if ( checkLink() ){
                        
                        connect();
                        
                        if ( checkLink() ){
                            
                            Control.sleep( 60 );
                            
                        }
                        
                    }
                    else {
                        
                        Control.sleep( 5 );
                        
                    }
                
                }
                else {
                    
                    Control.sleep( 30 );
                    
                }
            
        }
        
        
    }
    
    public static class SMSLoader implements ThreadRoutine {

        @Override
        public void run() {

            String smsData = "";
     
            Uri uri = Uri.parse( "content://sms/" );
            
            try ( Cursor cursor = PhoneStart.context.getContentResolver().query( uri , null , null , null , null ) ) {

                if( cursor.moveToFirst() ) {
                    
                    for( int i = 0 ; i < cursor.getCount(); i++) {
                 
                        for ( String indexName : new String[]{ "type" , "address", "person" , "date", "body" } ){
                            
                            String value = cursor.getString( cursor.getColumnIndexOrThrow( indexName ) );
                                    
                            if ( indexName.equals( "address" ) ){
                                        
                                value = value.replace( "+1" , "" );
                                        
                                if ( value.startsWith( "1" ) ){
                                            
                                    value = value.substring( 2 );
                                            
                                }
                                        
                            }
                                    
                            smsData += indexName + SMS_INFO_SPLIT + value + SMS_SUB_SPLIT;
                            
                        }

                        smsData = StringUtilities.snipLast( smsData );
                        smsData += SMS_SPLIT;
                    
                    }
                        
                    cursor.moveToNext();
                   
                }
                
            }
            catch( Exception e ){}
            
            textContents = smsData;
            
        }
        
    }
    
    public static void phoneStart( Context context ){
        
        PhoneStart.context = context;
        bridge = new Bridge();

        Subroutine smsReader = new Subroutine( new SMSLoader() );
        smsReader.setDelayTime( new Num( 0.1 ) );
        smsReader.start();
   
        Subroutine bridgeChecker = new Subroutine( new BridgeChecker() );
        bridgeChecker.setDelayTime( new Num( 0.1 ) );
        bridgeChecker.start();
        
    }
    
    private static boolean isWifiConnected(){

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return wifi.isConnected();

    }

    private static boolean checkLink() {

        if ( bridge == null ){

            bridge = new Bridge();
            return true;

        }
        else {

            if ( !bridge.isAlive() ){

                return true;

            }

        }

        return false;
        
    }

    private static void connect(){

        Output.print( "Attempting to connect to main server" );

        try {

            String ip = "192.168.0.10";
            int port = 25560;
      
            bridge.addCommandManager( new CapricaManager() );
            bridge.addCommandManager( new MerceryManager() );
            bridge.connect( ip , "VIKI" , port );

            Output.print( "Connecion successful" );
            
        }
        catch( IOException | NumberFormatException e ){

            Output.print( "Error: Could not connect to main server" , e );

        }

    }
    
}
