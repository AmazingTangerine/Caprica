package caprica.datatypes;

import caprica.system.Output;
import java.io.BufferedOutputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SystemFile {
    
    private String fileAddress;
    private File file;
    
    public SystemFile( String filePath ){
        
        fileAddress = filePath.replace( "\\" , "/" );
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
        
        InputDataStream dataStream = null;
        
        try {
            
            dataStream = new InputDataStream( getStream() );
            
            String data = dataStream.toString();

            return data;
            
        } 
        catch ( Exception exception ) {}
        
        try {
            
            if ( dataStream != null ){
            
                dataStream.close(); //Real bad if this fails
            
            }
            
        }
        catch( IOException e ){
            
            Output.print( "Could not close file stream(bad)" , e );
            
        }
        
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
        
        return new File( this.getFilePath() ).delete();
        
    }
    
    public void rename( String newPath ){
        
        getFile().renameTo( new File( newPath ) );
        
        file = new File( newPath );
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
       
        if ( getFilePath().contains( "." ) ){
            
            try {
                    
                return file.createNewFile();
                    
            }
            catch( Exception ePrime ){}
            
        }
        else {
        
            if ( file.mkdirs() ){
                
                try {
                    
                    return file.createNewFile();
                    
                }
                catch( Exception ePrime ){}
                
            }
        
        }
            
        return false;
        
    }
    
    public boolean safeWrite( String data , boolean append ){
        
        SystemFile tempFile = new SystemFile( this.getFilePath() + ".temp" );
        
        if ( tempFile.exists() ){ tempFile.delete(); }
        
        try {
            
            tempFile.create();

            OutputDataStream outputStream = new OutputDataStream( new File( tempFile.getFilePath() ) );
        
            outputStream.write( data );
        
            outputStream.close();
            
            String oldPath = this.getFilePath();
            
            this.delete();
            
            tempFile.rename( oldPath );
            
        }
        catch( Exception e ){
            
            tempFile.delete();
            
            return false;
            
        }
        
        return true;
        
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
    
    public boolean unZip() throws FileNotFoundException, IOException {
        
        SystemFile zipFolder = new SystemFile( this.getFilePath().replace( ".zip" , "/" ) );
     
        if ( zipFolder.exists() ){
            
            zipFolder.delete();
            
        }
        
        if ( !zipFolder.exists() ){
            
            zipFolder.create();
            
            if ( zipFolder.exists() ){
                
                ZipInputStream zipStream = new ZipInputStream( new FileInputStream( this.getFilePath() ) );
                
                ZipEntry entry = zipStream.getNextEntry();
                
                while ( entry != null ){
                    
                    String newPath = this.getFilePath().replace( ".zip" , "/" ) + entry.getName();

                    if ( !entry.isDirectory() ){
              
                        new SystemFile( newPath ).create();
                        
                        BufferedOutputStream outputStream = new BufferedOutputStream( new FileOutputStream( newPath ) );
        
                        byte[] buffer = new byte[ 4096 ];
        
                        int read;
        
                        while ( ( read = zipStream.read( buffer ) ) != -1 ){
                        
                            outputStream.write( buffer , 0 , read );
                        
                        }
                        
                        outputStream.close();
                        
                    }
                    else {
                        
                        new SystemFile( newPath + "/" ).create();
                        
                    }
                    
                    entry = zipStream.getNextEntry();
                    
                }
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
}
