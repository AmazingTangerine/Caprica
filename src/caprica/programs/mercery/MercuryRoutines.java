package caprica.programs.mercery;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import caprica.main.*;
import caprica.datatypes.ListUtilities;
import caprica.datatypes.StringUtilities;
import caprica.datatypes.SystemFile;
import caprica.server.Command;
import caprica.server.Connection;
import caprica.server.ConstructedCommandRoutine;
import caprica.system.ConstructedRoutine;
import caprica.system.Control;
import caprica.system.Output;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import static caprica.server.CommunicationConstants.SMS_INFO_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SUB_SPLIT;
import caprica.system.SystemInformation;

public class MercuryRoutines {

    public static class GetContacts extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            Output.print( "User has requested contacts" );
            
            String storeName = parameters[ 0 ];
        
            String address = "";
     
            Uri uri = Uri.parse( "content://sms/" );
            
            ArrayList< String > numbers = new ArrayList<>();
            
            try ( Cursor cursor = PhoneStart.context.getContentResolver().query( uri , null , null , null , null ) ) {

                if( cursor.moveToFirst() ) {
                    
                    for( int i = 0 ; i < cursor.getCount(); i++) {
                        
                        address = cursor.getString( cursor.getColumnIndexOrThrow( "address" ) ).replace( "+1" , "" );
                        
                        if ( !numbers.contains( address ) ){
                            
                            numbers.add( address );
                            
                        }
                        
                        cursor.moveToNext();
                        
                    }
                    
                }
            }
            catch( Exception e ){}
       
            String smsData = ListUtilities.listToString( numbers , "#" );
       
            this.responseCommand = new Command( command.getFromComputer() + "$Caprica" , SystemInformation.getComputerName() + "$Mercery" , 1 , storeName , smsData );
            
            return "";
            
        }

    }
    
    public static class SendText extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            String address = parameters[ 0 ];
            String message = parameters[ 1 ];
      
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage( address , null , message , null , null );
            
            Output.print( "Sent message to " + address );
            
            return "";
            
        }

    }
    
    public static class ContactText extends ConstructedCommandRoutine {
        
        @Override
        public String run(){
            
            Output.print( "User has requested contact text messages" );
     
            String address = parameters[ 0 ];
            String storeName = parameters[ 1 ];
            String rawTime = parameters[ 2 ];
            
            Long withinTime = Long.parseLong( rawTime ) / 1000;
            
            String smsData = "";
     
            Uri uri = Uri.parse( "content://sms/" );
            
            try ( Cursor cursor = PhoneStart.context.getContentResolver().query( uri , null , null , null , null ) ) {

                if( cursor.moveToFirst() ) {
                    
                    for( int i = 0 ; i < cursor.getCount(); i++) {
                    
                        if ( cursor.getString( cursor.getColumnIndexOrThrow( "address" ) ).replace( "+1" , "" ).equals( address ) ){
                        
                            if ( ( System.currentTimeMillis() / 1000 ) - Long.parseLong( cursor.getString( cursor.getColumnIndexOrThrow( "date" ) ) ) < withinTime ){
                            
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
                    
                        }
                        
                        cursor.moveToNext();
                        
                    }
                    
                }
            }
            catch( Exception e ){}
   
            this.responseCommand = new Command( command.getFromComputer() + "$Caprica" , SystemInformation.getComputerName() + "$Mercery" , 1 , storeName , smsData );
        
            return "";
            
        }
        
    }

    public static class FullTexts extends ConstructedCommandRoutine {

        @Override
        public String run(){
            
            Output.print( "User has requested text messages" );
     
            String storeName = parameters[ 0 ];
            
            String smsData = "";
     
            Uri uri = Uri.parse( "content://sms/" );
            
            try ( Cursor cursor = PhoneStart.context.getContentResolver().query( uri , null , null , null , null ) ) {

                if( cursor.moveToFirst() ) {
                    
                    for( int i = 0 ; i < cursor.getCount(); i++) {
                    
                        for ( String indexName : new String[]{ "type" , "address", "person", "date", "body" } ){
                            
                            String value = cursor.getString( cursor.getColumnIndexOrThrow( indexName ) );
                                    
                            if ( indexName.equals( "address" ) ){
                                        
                                value = value.replace( "+1" , "" );
                                        
                                if ( value.startsWith( "1" ) ){
                                            
                                    value = value.substring( 1 );
                                            
                               }
                                        
                                }
                                    
                                smsData += indexName + SMS_INFO_SPLIT + value + SMS_SUB_SPLIT;
                            
                            }

                            smsData = StringUtilities.snipLast( smsData );
                            smsData += SMS_SPLIT;
                       
                        cursor.moveToNext();
                        
                    }
                    
                }
            }
            catch( Exception e ){}
   
            this.responseCommand = new Command( command.getFromComputer() + "$Caprica" , SystemInformation.getComputerName() + "$Mercery" , 1 , storeName , smsData );
        
            return "";
            
        }

    }
    
    public static class TimeText extends ConstructedCommandRoutine {
        
        @Override
        public String run(){
            
            Output.print( "User has requested timed text messages" );
     
            String storeName = parameters[ 0 ];
            String rawTime = parameters[ 1 ];
            
            Long withinTime = Long.parseLong( rawTime );
            
            String smsData = "";

            for ( String sms : PhoneStart.textContents.split( SMS_SPLIT ) ){
                
                for ( String smsSub : sms.split( SMS_SUB_SPLIT ) ){
                    
                    String[] smsInfo = smsSub.split( SMS_INFO_SPLIT );
                           
                    if ( smsInfo[ 0 ].equals( "date" ) ){
                        
                        Long date = Long.parseLong( smsInfo[ 1 ] );
            
                        if ( date >= withinTime ){
                            
                            smsData += sms;
                            break;
                            
                        }
                        
                    }
                    
                }
                
            }

            this.responseCommand = new Command( command.getFromComputer() + "$Caprica" , SystemInformation.getComputerName() + "$Mercery" , 1 , storeName , smsData );
        
            return "";
            
        }
        
    }
   
}
