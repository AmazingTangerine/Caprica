package caprica.datatypes;

import caprica.system.Output;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class SystemFile {
    
    private String fileAddress;
    private File file;
    
    public SystemFile( String filePath ){
        
        fileAddress = filePath.replace( "\\" , "/" );;
        file = new File( filePath );
        
    }
    
    public SystemFile( File inputFile ){
        
        this( inputFile.getPath() );
        
    }

    public String getFilePath(){
        
        return fileAddress;
        
    }
    
    @Override
    public String toString(){
        
        try {
            
            InputDataStream dataStream = new InputDataStream( getStream() );
            
            String data = dataStream.toString();

            dataStream.close();

            return data;
            
        } 
        catch ( Exception exception ) {}
        
        return "";
        
    }
    
    public OutputStream getOutputStream() throws FileNotFoundException {
        
        return new FileOutputStream( file );
        
    }
    
    public InputStream getStream() throws FileNotFoundException {
        
        return new FileInputStream( file );

    }
    
    public String getFileType(){

        return getPath().split( "\\." )[ getPath().split( "\\." ).length - 1 ];
        
    }
    
    public String getPath(){
        
        return fileAddress;
        
    }
    
    public boolean delete(){
        
        return file.delete();
        
    }
    
    public void rename( String newPath ){
        
        getFile().renameTo( new File( newPath ) );
        
        fileAddress = newPath.replace( "\\" , "/" );
        
    }
    
    public boolean exists(){

        return file.exists();
        
    }
    
    public String getFolder(){
        
        String folderName = "";
        
        for ( int i = 0 ; i < fileAddress.split( "/" ).length - 1 ; i++ ){
            
            folderName += fileAddress.split( "/" )[ i ] + "/";
            
        }
        
        return folderName;
        
    }
    
    public boolean create() {
        
        File folder = new File( getFolder() );

        if ( folder.isDirectory() ){
            
            try {
                    
                return file.createNewFile();
                    
            }
            catch( Exception ePrime ){}
            
        }
        else {
        
            if ( folder.mkdirs() ){
                
                try {
                    
                    return file.createNewFile();
                    
                }
                catch( Exception ePrime ){}
                
            }
        
        }
            
        return false;
        
    }
    
    public void write( String data , boolean append ) throws IOException {
        
        if ( exists() && !append ){
            
            delete();
            
        }
        else if ( exists() && append ){
            
            data = new InputDataStream( new File( fileAddress ) ).toString() + data;
            
        }

        create();

        OutputDataStream outputStream = new OutputDataStream( new File( fileAddress ) );
        
        outputStream.write( data );
        
        outputStream.close();
        
    }

    public void writeStream( InputDataStream stream , boolean append ) throws IOException {
        
        write( stream.toString() , append );
 
    }
    
    public int getSize() {
        
        try {
        
            int size = 0;
        
            try {
            
                if ( exists() ){
            
                    size = getStream().available();
            
                }
                else {
                
                    return -1;
                
                }
            
            }
            catch ( FileNotFoundException e ){}

            return size;
        
        }
        catch( Exception e ){
            
            return -1;
            
        }
        
    }
    
    public File getFile(){
        
        return new File( fileAddress );
        
    }
    
    public ArrayList< SystemFile > listWholeContents(){
        
        ArrayList< SystemFile > files = listContents();
        ArrayList< SystemFile > newFiles = new ArrayList<>();
        
        for ( SystemFile file : files ){
            
            if ( file.isFolder() ){
                
                ArrayList< SystemFile > subFiles = file.listWholeContents();
                
                newFiles.addAll( subFiles );
                
            }
            
        }
        
        files.addAll( newFiles );
        
        return files;
        
    }
    
    public ArrayList< SystemFile > listContents(){
        
        File[] files = getFile().listFiles();
        ArrayList< SystemFile > globalFiles = new ArrayList<>();
   
        for ( File file : files ){
            
            SystemFile globalFile = new SystemFile( file );
            
            if ( !globalFile.isSymbolicLink() ){
                
                globalFiles.add( globalFile );
                
            }
 
        }
        
        return globalFiles;
        
    }
    
    public boolean isFolder(){
        
        return getFile().isDirectory();
        
    }
    
    public boolean isSymbolicLink(){
        
        File file = getFile();
        File start;
        
        if ( file.getParent() == null ){
 
            start = file;
            
        }
        else {
    
            try {
            
                File startDirectory = file.getParentFile().getCanonicalFile();
                start = new File( startDirectory , file.getName() );
    
            }
            catch( Exception e ){
            
                return false;
            
            }
     
        }
        
        try {
            
            return !start.getCanonicalFile().equals( start.getAbsoluteFile() );
            
        } 
        catch ( IOException ex ) {
            
            return false;
            
        }
        
    }
    
    public void copy( SystemFile copyFile ) throws IOException{
        
        if ( !copyFile.exists() ){ copyFile.create(); }
        
        Files.copy( this.getFile().toPath() , copyFile.getFile().toPath() , StandardCopyOption.REPLACE_EXISTING );
        
    }
    
}
