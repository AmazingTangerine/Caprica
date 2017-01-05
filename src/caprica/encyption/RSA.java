package caprica.encyption;

import caprica.datatypes.Num;
import caprica.system.Output;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Random;

public class RSA {

    int p, q; //Our starting primes
    int n;
    int phi;
    int e, d; //Our keys
    
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
    
    public RSA( String e , String d , String n ){

        this.e = Integer.parseInt( e );
        this.d = Integer.parseInt( d );
        this.n = Integer.parseInt( n );
        
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
