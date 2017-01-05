package caprica.math;

import caprica.datatypes.Num;
import caprica.system.Output;
import java.util.ArrayList;
import java.util.List;

public class Function {

    public static final int INTEGRATE_LEFT_ENDPOINT = 1;
    public static final int INTEGRATE_RIGHT_ENDPOINT = 2;
    public static final int INTEGRATE_MIDPOINT = 3;
    public static final int INTEGRATE_TRAPAZOID = 4;
    public static final int INTEGRATE_SIMPSON = 5;
    
    Equation equation;
    
    public Function( Equation equation ){
        
        this.equation = equation;
        
    }

    public Num differentiate( Object rawX , Object rawWidth ){
        
        //f'(x)=[f(x+h)-f(x)]/h
        
        Num x = new Num( rawX );
        Num width = new Num( rawWidth );
        
        Num slope = equation.value( x.add( width ) ).min( equation.value( rawX ) ).div( width );
        
        return slope;
        
    }
    
    public Num integrate( Object rawStart , Object rawEnd , Object nRaw , int method ){
        
        Num start = new Num( rawStart );
        Num end = new Num( rawEnd );
        Num n = new Num( nRaw );
        
        Num step = end.min( start ).div( n );
        
        Num sum = new Num( 0 );
        
        if ( method == Function.INTEGRATE_LEFT_ENDPOINT ){
            
            for ( Num i = new Num( start ) ; i.less( end ) ; i = i.add( step ) ){
                
                sum = sum.add( step.mul( equation.value( i ) ) );
                
            }
            
        }
        else if ( method == Function.INTEGRATE_RIGHT_ENDPOINT ){ //x + step
            
            for ( Num i = new Num( start ) ; i.less( end ) ; i = i.add( step ) ){
                
                sum = sum.add( step.mul( equation.value( i.add( step ) ) ) );
                
            }
            
        }
        else if ( method == Function.INTEGRATE_MIDPOINT ){ //x + ( step / 2 )
            
            for ( Num i = new Num( start ) ; i.less( end ) ; i = i.add( step ) ){
                
                sum = sum.add( step.mul( equation.value( i.add( step.half() ) ) ) );
                
            }
            
        }
        else if ( method == Function.INTEGRATE_TRAPAZOID ){
            
            //f(x) = ( step / 2 ) SUM[ f(x0) + 2f(x1) + 2f(x2) ... f(xn) ]
            
            for ( Num i = new Num( start ) ; i.less( end.add( step ) ) ; i = i.add( step ) ){
                
                if ( i.equalsWhole( start ) || i.equalsWhole( end ) ){
                    
                    sum = sum.add( equation.value( i ) );
                    
                }
                else {
                    
                    sum = sum.add( equation.value( i ).mul( 2 ) );
                    
                }
                
            }
            
            sum = sum.mul( step.half() );
            
        }
        else if ( method == Function.INTEGRATE_SIMPSON ){
            
            //f(x) = ( step / 3 ) SUM[ f(x0) + 4f(x1) + 2f(x2) + 4f(x3) + 2f(x4) + 4f(x5) ... + f(xn) ]
            //n must be even
            
            if ( !n.even() ){
                
                n = n.add( 1 );
                
            }
            
            step = end.min( start ).div( n );
            
            int count = 0;
            
            for ( Num i = new Num( start ) ; i.less( end.add( step ) ) ; i = i.add( step ) ){
                
                if ( i.equalsWhole( start ) || i.equalsWhole( end ) ){
                    
                    sum = sum.add( equation.value( i ) );
                    
                }
                else {
                    
                    if ( count % 2 == 0 ){ //Even
                    
                        sum = sum.add( equation.value( i ).mul( 2 ) );
                        
                    }
                    else {
                        
                        sum = sum.add( equation.value( i ).mul( 4 ) );
                        
                    }
   
                }
                
                count++;
                
            }
            
            sum = sum.mul( step.div( 3 ) );
            
        }
        
        return sum;
        
    }
    
    
}
