package caprica.encyption;

//Julia's stupid encyption alogrithm

import caprica.system.Output;

public class JSEA {

    public static String encypt( String message , String key ){
        
        int keyLength = key.length();
        int messageLength = message.length();
        
        int floorLength = Math.floorDiv( messageLength , 256 );
        int remainderLength = messageLength % 256;
 
        message = ( ( char ) floorLength ) + "" + ( ( char ) remainderLength ) + "" + message;
        
        int blocksLength = ( int ) Math.ceil( ( double ) messageLength / ( double ) keyLength );
        
        String tempMessage = message;
        
        for ( int i = 0 ; i < message.length() - keyLength * blocksLength ; i++ ){
            
            tempMessage += "0";
            
        }
        
        message = tempMessage;

        String encyptedMessage = "";
        
        for ( int i = 0 ; i < blocksLength + 1 ; i++ ){
            
            for ( int x = 0 ; x < keyLength ; x++ ){
                
                encyptedMessage += addChar( message.charAt( x + ( i *  keyLength ) ) , key.charAt( x ) );
                
            }
            
        }
        
        return encyptedMessage;
        
    }
    
    public static char addChar( char inputChar , int addChar ){
        
        int newChar = inputChar + addChar;
        
        if ( newChar < 0 ){ 
            
            newChar = 255 - newChar;
            
        }
        else if ( newChar > 255 ){
            
            newChar = newChar - 255;
            
        }
        
        return ( char ) newChar;
        
    }
    
    public static String decypt( String message , String key ){
        
        int keyLength = key.length();
        int rawMessageLength = message.length();
        int blocksLength = ( int ) Math.ceil( ( double ) rawMessageLength / ( double ) keyLength );
        
        String decyptedMessageRaw = "";
        
        for ( int i = 0 ; i < blocksLength ; i++ ){
            
            for ( int x = 0 ; x < keyLength ; x++ ){
                
                decyptedMessageRaw += addChar( message.charAt( x + ( i *  keyLength ) ) , ( int ) key.charAt( x ) * -1 );
               
            }
            
        }
        
        int floorLength = decyptedMessageRaw.charAt( 0 );
        int remainderLength = decyptedMessageRaw.charAt( 1 );
        int messageLength = ( floorLength * 256 ) + remainderLength;

        String decyptedMessage = "";
        
        for ( int i = 2 ; i < 2 + messageLength ; i++ ){
            
            decyptedMessage += "" + decyptedMessageRaw.charAt( i );
            
        }
        
        return decyptedMessage;
        
    }
    
}
