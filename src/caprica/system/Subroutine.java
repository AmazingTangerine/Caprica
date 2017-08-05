package caprica.system;

import caprica.datatypes.Num;
import java.lang.reflect.Constructor;

public class Subroutine {

    private ThreadRoutine threadRoutine = null;
    private ConstructedRoutine constructedRoutine = null;
    
    private boolean alive = false;
    boolean runOnce = false;
    
    private Num delayTime = null;
    
    SubroutineThread thread;
    
    private static int threadCount = 0;
    
    public Subroutine( ThreadRoutine routine ){
        
        this.threadRoutine = routine;
         
    }
    
    public Subroutine( ConstructedRoutine routine ){
        
        this.constructedRoutine = routine;
        
    }
    
    public void setDelayTime( Num input ){
        
        delayTime = input;
        
    }
    
    public void start(){
        
        threadCount++;
        
        new Output( "Subroutine" ).disp( "Thread count: " + threadCount );
        
        alive = true;
        
        thread = new SubroutineThread();
        
        ( ( Thread ) thread ).start();

    }
    
    public void runOnce(){
        
        runOnce = true;
        
        restart();
        
    }
    
    public void restart(){
        
        if ( alive ){
            
            stop();
            
        }
                    
        start();
        
    }
    
    public void stop(){
        
        alive = false;
        
        thread.halt();
     
    }
    
    public boolean alive(){
        
        return alive;
        
    }
    
    class SubroutineThread extends Thread {
        
        boolean active = true;
        
        public void halt(){
            
            active = false;
            
        }
        
        @Override
        public void run(){
            
            while ( active ){
            
                if ( threadRoutine == null ){
                    
                    constructedRoutine.run();
                    
                }
                else {
                    
                    threadRoutine.run();
                    
                }
              
                if ( runOnce ){
                    
                    active = false;
                    
                }

                if ( delayTime != null ){
                    
                    Control.sleep( delayTime );
                    
                }
                
            }
            
        }
        
    }

}
