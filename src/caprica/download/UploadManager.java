package caprica.download;

import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.server.Command;
import caprica.server.Connection;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.Subroutine;
import caprica.system.SystemInformation;
import caprica.system.ThreadRoutine;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UploadManager {

    private static final int PORT = 25561;
    
    Command transferCommand;
    SystemFile sendingFile;
    
    Connection connection;
    
    private String sendingFileName;
    private String retrieveFileName;

    private Socket socket;
    private ServerSocket fileSocket;
    
    private ObjectInputStream inputStream;
    private OutputStream outputStream;
    
    private int fileCount = 0;
    private int fileSize = 0;
    
    private Long startTime;
    
    /**
     * For client initiation to send file to server
     * @param connection Server connection
     * @param sendingFileName Name of the file to be sent
     * @param retrieveFileName Save name for the file on the server side
     */
    public UploadManager( Connection connection , String sendingFileName , String retrieveFileName ){
        
        this.connection = connection;
        this.sendingFileName = sendingFileName;
        this.retrieveFileName = retrieveFileName;
        
        sendingFile = new SystemFile( sendingFileName );
        
        transferCommand = new Command( "file" , "send" , retrieveFileName , "" + sendingFile.getSize() );

        send();
        
    }

    private boolean sendCommand(){
        
        try {
         
            connection.sendCommand( transferCommand );
            
            return true;
        
        }
        catch( Exception e ){ //Could not send command
            
            Output.print( "Could not send file transfer command" , e );
            
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
    
    public boolean send(){
        
        Output.print( "Starting file send process" );
        
        if ( sendCommand() ){ 
            
            //Successfully sent command
            
            Control.sleep( 0.5 ); //Wait 0.5 seconds for the server socket to be set up
            
            if ( openSocket() ){
                
                //Successfully opened socket
                
                startTime = System.currentTimeMillis();
                
                new Subroutine( new SocketSender() ).runOnce();
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    public Num progess(){ //Gets the upload %
        
        return new Num( fileSize ).div( fileCount );
        
    }
    
    public int speed(){ //Bytes per second
        
        Long elapsedTimeLong = System.currentTimeMillis() - startTime;
        Long elpasedSeconds = ( elapsedTimeLong / 1000L );
        
        return fileCount / elpasedSeconds.intValue();
        
    }
    
    public boolean complete(){
        
        return fileCount == fileSize;
        
    }

    private class SocketSender implements ThreadRoutine {

        @Override
        public void run() {

            try {
            
                Output.print( "Openining object stream" );
                
                ObjectOutputStream serverStream = new ObjectOutputStream( socket.getOutputStream() );
                serverStream.flush();
                
                try {
                    
                    InputStream fileStream = sendingFile.getStream();
                    fileSize = sendingFile.getSize();
        
                    int fileData;
        
                    Output.print( "Starting file send" );
        
                    while ( ( fileData = fileStream.read() ) != -1 ){
            
                        int encyptedInt = connection.getRSA().encypt( fileData );
            
                        serverStream.writeInt( encyptedInt );
                        
                        fileCount++;
            
                    }
                    
                    serverStream.writeInt( -1 );
                    
                    Output.print( "File send complete" );
                    
                    try {
                        
                        serverStream.flush();
                        serverStream.close();
        
                        fileStream.close();
                        
                        Output.print( "File socket closed" );
                        
                        Output.print( "Total file send time: " + ( System.currentTimeMillis() - startTime ) / 1000 + "[s]" );
                        Output.print( "Average file send speed:" + speed() + "[bytes/s]" );
                        
                    }
                    catch( Exception e ){ //Could not cleanly close connection
         
                        Output.print( "File socket closed, not nicely" , e );
                        
                    }
                    
                }
                catch( Exception e ){ //Could not send data
                    
                    Output.print( "Could not write to socket" , e );
                    
                    fileCount = -1;
                    
                }
            
            }
            catch( Exception e ){ //Could not output stream
                
                Output.print( "Could not open object output stream" , e );
                
                fileCount = -1;
                
            }
            
        }
        
    }
    
    /**
     * Reaction to upload request, stores sent data
     * @param connection Client connection 
     * @param storeName Name of file to be stored
     * @param fileSize Size of incoming file
     */
    public UploadManager( Connection connection , String storeName , int fileSize ){
        
        Output.print( "Upload file to server subroutine starting" );

        this.connection = connection;
        this.retrieveFileName = storeName;
        this.fileSize = fileSize;
        
        if ( createFile() ){
        
            Output.print( "File created" );
            Output.print( "Opening server socket" );
        
            try {
            
                openServerSocket();
            
                try {
                
                    acceptClient();
                
                    Output.print( "Connection from client accepted" );
                    
                    startTime = System.currentTimeMillis();
                
                    try {
                        
                        openObjectStream();
                        
                        try {
                            
                            writeFile();
                            
                            try {
                                
                                endProcess();
                                
                            }
                            catch( Exception e ){
                                
                                Output.print( "Could not close proccess nicely" , e );
                                
                            }
                            
                        }
                        catch( Exception e ){
                            
                            Output.print( "Could not write to file" , e );
                            
                            sendingFile.delete();
                            
                        }
                        
                    }
                    catch( Exception e ){
                        
                        Output.print( "Could not open object input stream" , e );
                        
                        sendingFile.delete();
                        
                    }
                    
                }
                catch( Exception e ){
                
                    Output.print( "Could not accept client connection" , e );
                    
                    sendingFile.delete();
                
                }
            
            }
            catch( Exception e ){
            
                Output.print( "Could not open server socket" , e );
                //Send command back to client cancelling there side
            
                sendingFile.delete();
                
            }   
        
        }
        else {
            
            Output.print( "Could not create file" );
            
        }
        
    }
    
    private void openServerSocket() throws IOException{
        
        fileSocket = new ServerSocket( PORT );
        
    }
    
    private void acceptClient() throws IOException {
        
        socket = fileSocket.accept();
        
    }
    
    private boolean createFile(){
        
        Output.print( "Creating file" );
        
        sendingFile = new SystemFile( retrieveFileName );
        
        return sendingFile.create();
        
    }
    
    private void openObjectStream() throws IOException {
    
        Output.print( "Opening object input stream" );
   
        inputStream = new ObjectInputStream( socket.getInputStream() );
        
        Output.print( "Object input stream opened" );
        
    }
    
    private void writeFile() throws IOException{
        
        Output.print( "Writing to file" );
        
        outputStream = sendingFile.getOutputStream();
        
        int data;
        
        while ( ( data = inputStream.readInt() ) != -1 ){
            
            int decrypted = connection.getRSA().decypt( data );
            
            outputStream.write( decrypted );
            
            fileCount++;
            
        }
        
        outputStream.flush();
        
        Output.print( "Done writing to file" );
        
    }
    
    private void endProcess() throws IOException {
        
        Output.print( "Closing streams" );
        
        outputStream.close();
        inputStream.close();
        
        socket.close();
        fileSocket.close();
        
        Output.print( "Done closing streams" );
        
    }
    
}
