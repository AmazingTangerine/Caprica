package caprica.system;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    public static void playFile( final String filePath ){
        
            new Thread( new Runnable() {

                public void run() {
     
                    try {
                    
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream( new File( filePath ) );
         
                        Clip clip = AudioSystem.getClip();
                        clip.open(inputStream);
                        clip.start(); 
                        
                    } 
                    catch ( Exception e ) {}
    
                }
                
            }).start();
            
        
        
    }
    
}
