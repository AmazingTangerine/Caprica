package caprica.system;

import caprica.datatypes.Num;
import java.lang.reflect.Constructor;

public class Subroutine {

    ThreadRoutine routine;
    
    boolean alive = false;
    boolean runOnce = false;
    
    Num delayTime = null;
    
    SubroutineThread thread;
    
    public Subroutine( ThreadRoutine routine ){
        
        this.routine = routine;
         
    }
    
    public void setDelayTime( Num input ){
        
        delayTime = input;
        
    }
    
    public void start(){
        
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
            
                routine.run();
              
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
