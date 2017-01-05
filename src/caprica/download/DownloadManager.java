package caprica.download;

import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.server.Command;
import caprica.server.Connection;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DownloadManager {

    private static final int PORT = 25561;
    
    Command downloadCommand;
    SystemFile uploadFile;
    
    Connection connection;
    String downloadFileName;
    String storeFileName;
    
    Socket socket;
    
    private int fileCount = 0;
    private int fileSize = 0;
    
    private ServerSocket fileSocket;
    
    private InputStream fileReadStream;
    private ObjectOutputStream fileWriteStream;
    
    Long startTime;
    
    public DownloadManager( Connection connection , String downloadFileName , String storeFileName ){
        
        this.connection = connection;
        this.downloadFileName = downloadFileName;
        this.storeFileName = storeFileName;
        
        downloadCommand = new Command( "file" , "download" , downloadFileName , storeFileName );
        uploadFile = new SystemFile( storeFileName );
      
        download();
        
    }

    private boolean sendCommand(){
        
        try {
         
            connection.sendCommand( downloadCommand );
            
            return true;
        
        }
        catch( Exception e ){ //Could not send command
            
            Output.print( "Could not send file download command" , e );
            
            return false;
            
        }
        
    }
    
    private boolean openSocket() {

        try {
        
            socket = new Socket( connection.getIP() , PORT );
            
            return true;
        
        }
        catch( Exception e ){
            
            Output.print( "Could not open socket to file server socket" , e );
            
            return false;
            
        }
        
    }
    
    public boolean download(){
        
        Output.print( "Starting dowload process" );
        
        if ( sendCommand() ){ 
            
            //Successfully sent command
            
            Control.sleep( 0.5 ); //Wait 0.5 seconds for the server socket to be set up
            
            if ( openSocket() ){
                
                //Successfully opened socket
                
                new Subroutine( new SocketReceiver() ).runOnce();
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    public Num uploadProgess(){ //Gets the upload %
        
        return new Num( fileSize ).div( fileCount );
        
    }
    
    public boolean complete(){
        
        return fileCount == fileSize;
        
    }

    private class SocketReceiver implements ThreadRoutine {

        @Override
        public void run() {

            try {
            
                Output.print( "Openining object stream" );
                
                ObjectInputStream serverStream = new ObjectInputStream( socket.getInputStream() );
                
                try {
                    
                    uploadFile.create();
                    
                    OutputStream fileStream = uploadFile.getOutputStream();
  
                    int fileData;
        
                    Output.print( "Starting file recieve" );
        
                    startTime = System.currentTimeMillis();
                    
                    while ( ( fileData = serverStream.readInt() ) != -1 ){
            
                        int encyptedInt = connection.getRSA().decypt( fileData );
            
                        fileStream.write( encyptedInt );
                        
                        fileCount++;
            
                    }
 
                    Output.print( "File download" );
                    
                    Output.print( "Total file send time: " + ( System.currentTimeMillis() - startTime ) / 1000 + "[s]" );
                    Output.print( "Average file send speed:" + speed() + "[bytes/s]" );
                    
                    try {
                        
                        fileStream.flush();
                        fileStream.close();
  
                        serverStream.close();
        
                        Output.print( "File socket closed" );
                        
                    }
                    catch( Exception e ){ //Could not cleanly close connection
         
                        Output.print( "File socket closed, not nicely" , e );
                        
                    }
                    
                }
                catch( Exception e ){ //Could write data to file
                    
                    Output.print( "Could not write to file" , e );
                    
                    uploadFile.delete();
                    
                    fileCount = -1;
                    
                }
            
            }
            catch( Exception e ){ //Could not input stream
                
                Output.print( "Could not open object input stream" , e );
                
                fileCount = -1;
                
            }
            
        }
        
    }
    
    public DownloadManager( Connection connection , String downloadFileName ){
        
        this.connection = connection;
        this.downloadFileName = downloadFileName;
        uploadFile = new SystemFile( downloadFileName );
        
        Output.print( "Uploading file to client" );
   
        this.fileSize = uploadFile.getSize();

        try {
            
            openServerSocket();
            
            try {
                
                acceptClient();
                
                try {
                    
                    openStream();
                    
                    try {
                        
                        openObjectOutputStream();
                        
                        Output.print( "Writing file data to client socket" );
                    
                        int fileData;
                    
                        while ( ( fileData = fileReadStream.read() ) != -1 ){
            
                            int encyptedInt = connection.getRSA().encypt( fileData );
            
                            fileWriteStream.writeInt( encyptedInt );
            
                        }
                    
                        fileWriteStream.writeInt( -1 );
                        fileWriteStream.flush();
                        
                        Output.print( "Done writing file to client socket" );
                        
                        closeOutputStream();
                        closeInputStream();
                        closeSocket();
                        closeServer();
                        
                    }
                    catch( Exception e ){
                        
                        Output.print( "Could not open object output stream" , e );
                        
                        closeInputStream();
                        closeSocket();
                        closeServer();
                        
                    }
                    
                }
                catch( Exception e ){
                    
                    Output.print( "Could not open file stream" , e );
    
                    closeSocket();
                    closeServer();
                    
                }
                
            }
            catch( Exception e ){
                
                Output.print( "Could not accept client connection" , e );
                
                closeServer();
                
            }
            
        }
        catch( Exception e ){
            
            Output.print( "Could not open server socket" , e );
            
        }
        
    }
    
    private void openServerSocket() throws IOException{
        
        Output.print( "Opening server socket" );
        
        fileSocket = new ServerSocket( PORT );
        
        Output.print( "Server socket opened" );
        
    }
    
    private void acceptClient() throws IOException {
        
        Output.print( "Awaiting client connection" );
        
        socket = fileSocket.accept();
        
        Output.print( "Client connection accepted" );
        
    }
    
    private void openStream() throws FileNotFoundException {
        
        Output.print( "Opening file stream" );
        
        fileReadStream = uploadFile.getStream();
        
        Output.print( "Stream opened" );
        
    }
    
    private void openObjectOutputStream() throws IOException {
        
        Output.print( "Opening object output stream" );
        
        fileWriteStream = new ObjectOutputStream( socket.getOutputStream() );
        
        Output.print( "Finished opening object output stream" );
        
    }
    
    private void closeSocket(){
        
        Output.print( "Closing socket" );
        
        try {
            
            socket.close();
            
            Output.print( "Successfully closed socket" );
            
        }
        catch( Exception e ){
            
            Output.print( "Could not close socket" , e );
            
        }
        
    }
    
    private void closeServer(){
        
        Output.print( "Closing server socket" );
        
        try {
            
            fileSocket.close();
            
            Output.print( "Successfully closed server socket" );
            
        }
        catch( Exception e ){
            
            Output.print( "Could not close server socket" , e );
            
        }
        
    }
    
    private void closeInputStream(){
        
        Output.print( "Closing file input stream" );
        
        try {
            
            fileReadStream.close();
            
            Output.print( "Successfully closed file input stream" );
            
        }
        catch( Exception e ){
            
            Output.print( "Could not close file output stream" , e );
            
        }
        
    }
    
    private void closeOutputStream(){
        
        Output.print( "Closing socket output stream" );
        
        try {
            
            fileReadStream.close();
            
            Output.print( "Successfully closed socket output stream" );
            
        }
        catch( Exception e ){
            
            Output.print( "Could not close socket output stream" , e );
            
        }
        
    }
    
    public Num progess(){ //Gets the upload %
        
        return new Num( fileSize ).div( fileCount );
        
    }
    
    public int speed(){ //Bytes per second
        
        Long elapsedTimeLong = System.currentTimeMillis() - startTime;
        Long elpasedSeconds = ( elapsedTimeLong / 1000L );
        
        return fileCount / elpasedSeconds.intValue();
        
    }
    
}
