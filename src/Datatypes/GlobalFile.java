package Datatypes;

import System.Control;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GlobalFile {
    
    String fileAddress;
    
    public GlobalFile( String address ){
        
        fileAddress = address;
        
    }
    
    public InputStream getStream() throws FileNotFoundException {
        
        return new FileInputStream( new File( fileAddress ) );
        
    }
    
    public boolean delete(){
        
        return new File( fileAddress ).delete();
        
    }
    
    public boolean exists(){
        
        return new File( fileAddress ).exists();
        
    }
    
    public boolean create() {
        
        try {
        
            return new File( fileAddress ).createNewFile();
        
        }
        catch( Exception e ){ //Add some correcting methods
         
            File file = new File( fileAddress );
            
            if ( file.mkdirs() ){
                
                try {
                    
                    file.createNewFile();
                    
                }
                catch( Exception ePrime ){
                    
                    //Add more corrections?
                    
                }
                
            }
            
        }
            
        return false;
        
    }
    
    public boolean writeStream( InputDataStream stream , boolean Append ){
        
        if ( exists() && !Append ){
            
            if ( !delete() ){
                
                return false;
                
            }
            
        }
        
        if ( !create() ){
            
            return false;
            
        }
        
        try {
        
            FileOutputStream outputStream = new FileOutputStream( new File( fileAddress ) );
        
            return stream.write( outputStream );
            
        }
        catch ( Exception E ){ //Add correction
        
            return false;
            
        }
 
    }
    
    public boolean unPack(){
        
        if ( fileAddress.contains( ".zip" ) ){
            
            String response = Control.exec( "unzip -o " + fileAddress + " -d /Agora/");

            return true;
            
        }
        
        return false;
        
    }
    
}
