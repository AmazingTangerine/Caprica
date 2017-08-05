package caprica.encyption;

import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.system.Output;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class RSA {

    private static final String KNOWN_PRIMES = "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571";
    
    int p, q; //Our starting primes
    int n;
    int phi;
    int e, d; //Our keys
    
    public String decrypt( String message ){
        
        String decrypted = "";
        
        for ( char bit : message.toCharArray() ){
            
            decrypted += this.decypt( ( int ) bit );
            
        }
        
        return decrypted;
        
    }
    
    public String encrypt( String message ){
        
        String encrypted = "";
        
        for ( char bit : message.toCharArray() ){
            
            encrypted += this.encypt( ( int ) bit );
            
        }
        
        return encrypted;
        
    }
    
    public RSA(){ //Random selection of prime numbers
        
        Output.print( "Generating RSA key from scratch" );
        
        Random generator = new Random();
        
        Output.print( "Selecting primes" );
        
        String[] knownPrimes = KNOWN_PRIMES.replace( " " , "" ).split( "," );
        
        int firstPrimeLocation = generator.nextInt( knownPrimes.length );
        int secondPrimeLocation = generator.nextInt( knownPrimes.length );
       
        while ( secondPrimeLocation == firstPrimeLocation ){
            
            secondPrimeLocation = generator.nextInt( knownPrimes.length );
            
        }
        
        p = Integer.parseInt( knownPrimes[ firstPrimeLocation ] );
        q = Integer.parseInt( knownPrimes[ secondPrimeLocation ] );
        
        n = p * q;
        phi = ( p - 1 ) * ( q - 1 );
        
        Output.print( "Finding e and d" );
 
        e = generateE();
        d = generateD();
        
        Output.print( "Finished creating RSA key" );
        
    }
    
    public RSA( int p , int q ){
        
        n = p * q;
        phi = ( p - 1 ) * ( q - 1 );
 
        e = generateE();
        d = generateD();

    }
    
    public RSA( int e , int d , int n ){
        
        this.e = e;
        this.d = d;
        this.n = n;

    }
    
    public RSA( String filePath ){
        
        String rawData = new SystemFile( filePath ).toString();
        String[] data = rawData.split( "," );
        
        this.e = Integer.parseInt( data[ 0 ] );
        this.d = Integer.parseInt( data[ 1 ] );
        this.n = Integer.parseInt( data[ 2 ] );
        
    }
    
    public RSA( String e , String d , String n ){

        this.e = Integer.parseInt( e );
        this.d = Integer.parseInt( d );
        this.n = Integer.parseInt( n );
        
    }
    
    public boolean generateKeyFile( String filePath ){
        
        String data = e + "," + d + "," + n;
        
        SystemFile file = new SystemFile( filePath );
        file.create();
        
        try {
        
            file.write( data , false );
        
            return true;
            
        }
        catch( Exception e ){
        
            return false;
        
        }
        
    }
    
    private int generateE(){
       
        int foundCount = 0;
        
        for ( int i = 1 ; i < phi ; i++ ){
            
            if ( coPrime( i , phi ) && i != 1 ){
                
                if ( foundCount == 2 ){
                
                    return i;
                    
                }
                else {
                    
                    foundCount++;
                    
                }
                
            }
            
        }
        
        return 0;
        
    }
    
    private int generateD(){ //e*d % phi = 1
        
        int genD = 0;
        
        while ( true ){
            
            if ( ( genD * e ) % phi == 1 && genD != e ){
                
                return genD;
                
            }
            
            genD++;
            
        }
           
    }
    
    public int encypt( int value ){

        int encypted = longHandle( value , e );
        
        return encypted;
        
    }

    public int decypt( int value ){

        int decypted = longHandle( value , d );

        return decypted;
        
    }

    private int longHandle( int value , int c ){
        
        BigInteger mathed = BigInteger.valueOf( value ).modPow( BigInteger.valueOf( c ) , BigInteger.valueOf( n ) );

        return mathed.intValue();
        
    }
    
    private int gcd( int a , int b ) {
    
        int hold;
            
        while( b != 0 ){
            
            hold = a;
            a = b;
            b = hold % b;
            
        }
    
        return a;
    
    }
    
    private boolean coPrime( int a , int b ) {
    
        return gcd( a , b ) == 1;
    
    }
    
    public String publicCode(){
        
        return "E:" + e + " D:" + d + " N:" + n;
        
    }
    
}
