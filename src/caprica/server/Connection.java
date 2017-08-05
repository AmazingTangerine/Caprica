package caprica.server;

import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.system.Control;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import caprica.encyption.RSA;
import caprica.system.Output;
import caprica.system.SystemInformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Connection {

    private static final boolean debugMode = true;
    private static final boolean ping = true;

    private static double readWaitTime = 350; //In milliseconds
    private static double commandQueueTime = 500; //Time between commands being sent, from the list
    private static double commandCheckTime = 280; //Time between each list check of a command to see if it was sent
    private static final double pingTime = 2; //How often we send a ping command
    private static double commandQueueCheckTime = 200; //Time between each copy-send cycle of the stored commands
    private final static boolean queueOveride = false;
    
    private Socket socket;

    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectoutputStream = null;

    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    private Subroutine listener;
    private Subroutine connectionCheck;
    private Subroutine commandSender;

    private ArrayList< String > commandQueue;
    private HashMap< String , Boolean> sentCommands = new HashMap<>();
    private HashMap< String , IOException> commandErrors = new HashMap<>();
    private ArrayList< String> receivedCommandQueue = new ArrayList<>();

    private Command lastCommand = null;

    private boolean newMessage = false;

    private RSA encypter = null;
    private String ip;
    private String programName; //Braedon has hacked the mainframe. I'm in.'
    private String to;
    private String from;
    private Server server;
    private Bridge bridge;
    
    private HashMap< String , String> dataStore = new HashMap<>();
    private boolean alive = false;
    private Output output;

    public void setServer( Server server ) {

        this.server = server;

    }
    
    public void setBridge( Bridge bridge ){
        
        this.bridge = bridge;
        
    }
    
    public Bridge getBridge(){
        
        return bridge;
        
    }

    public Connection( Socket inputSocket , String ip , RSA encrypter ) throws IOException { //Encrypted connection

        this.commandQueue = new ArrayList<>();

        alive = false;

        this.output = new Output( "Server" );

        this.ip = ip;
        this.encypter = encrypter;

        this.socket = inputSocket;

        if ( encrypter == null ) {

            this.outputStream = this.socket.getOutputStream();
            outputStream.flush();

            this.inputStream = this.socket.getInputStream();

        }
        else {

            this.objectoutputStream = new ObjectOutputStream( this.socket.getOutputStream() );
            outputStream.flush();

            this.objectInputStream = new ObjectInputStream( this.socket.getInputStream() );

        }

        listener = new Subroutine( new Listener( this ) );
        listener.start();

        if ( !queueOveride ){
        
            commandSender = new Subroutine( new CommandSender( this ) );
            commandSender.setDelayTime( new Num( commandQueueCheckTime / ( double ) 1000 ) );
            commandSender.start();

        }
        
        alive = true;

    }

    public Connection( Socket inputSocket , String ip ) throws IOException { //No encyption connection

        this( inputSocket , ip , null );
        this.commandQueue = new ArrayList<>();

    }

    public boolean getAlive() {

        return alive;

    }

    public void flush() throws IOException {

        if ( outputStream != null ) {

            outputStream.flush();

        }

        if ( objectoutputStream != null ) {

            objectoutputStream.flush();

        }

    }

    public String getStoreValue( String name ) {

        if ( dataStore.containsKey( name ) ) {

            return dataStore.get( name );

        }

        return "null";

    }

    public void setStoreValue( String name , String value ) {

        if ( debugMode ) {

            output.disp( "Store value " + name + " was set to " + value );

        }

        dataStore.put( name , value );

    }

    public void setProgramName( String programName ) {

        this.programName = programName;

    }

    public String getProgramName() {

        return programName;

    }
    
    /**
     * Converts command to string and sends it along the stream
     *
     * @param command
     */
    private void sendStringStream( String rawCommand ) throws IOException {

        if ( outputStream != null ) {

            outputStream.flush();

        }
        else {

            objectoutputStream.flush();

        }

        if ( encypter != null ) {

            rawCommand = encypter.encrypt( rawCommand );

        }

        for ( char bit : rawCommand.toCharArray() ) {

            int transmit = ( int ) bit;

            if ( outputStream != null ) {

                outputStream.write( transmit );

            }
            else {

                objectoutputStream.writeInt( transmit );

            }

        }

        if ( outputStream != null ) {

            outputStream.flush();

        }
        else {

            objectoutputStream.writeInt( -1 );
            objectoutputStream.flush();

        }

    }

    protected boolean sendFile( SystemFile file , String localFilePath ) throws IOException {
        
        String rawCommand = "FILE;" + localFilePath + ";" + file.getSize() + ";" + file.toString();
        
        if ( queueOveride ){
        
            sendStringStream( rawCommand );
            return true;
            
        }
        else {
       
            commandQueue.add( rawCommand );
         
            for ( int i = 0 ; i < 10 ; i++ ){
            
                if ( sentCommands.containsKey( rawCommand ) ) {

                    if ( sentCommands.get( rawCommand ) ) {

                        return true;
            
                    }
                    else {

                        IOException error = commandErrors.get( rawCommand );

                        throw error;

                    }

                }

                Control.sleep( commandCheckTime / 1000 );

            }   
        
        }
            
        return false;
        
    }
    
    protected boolean sendCommand( Command command ) throws IOException {
      
        command = new Command( command.toString() );
        
        if ( queueOveride ){
        
            sendStringStream( command.toString() );
            return true;
            
        }
        else {
       
            commandQueue.add( command.toString() );
         
            for ( int i = 0 ; i < 10 ; i++ ){
            
                if ( sentCommands.containsKey( command.toString() ) ) {

                    if ( sentCommands.get( command.toString() ) ) {

                        return true;
            
                    }
                    else {

                        IOException error = commandErrors.get( command.toString() );

                        throw error;

                    }

                }

                Control.sleep( commandCheckTime / 1000 );

            }   
        
        }
            
        return false;
        
    }

    public String getIP() {

        return ip;

    }

    public RSA getRSA() {

        return encypter;

    }

    public Socket getSocket() {

        return socket;

    }

    public void close() {

        listener.stop();

        if ( inputStream != null ) {

            try {

                outputStream.close();
                inputStream.close();

            }
            catch ( IOException e ) {

                output.disp( "Error: Could not close streams" , e );

            }

        }
        else {

            try {

                objectoutputStream.close();
                objectInputStream.close();

            }
            catch ( IOException e ) {

                output.disp( "Error: Could not close streams" , e );

            }

        }

        try {

            socket.close();

        }
        catch ( IOException e ) {

            output.disp( "Error: Could not close socket" , e );

        }

        if ( server != null ) {

            server.removeConnection( this );

        }

        alive = false;

    }

    public Command getLastCommand( int waitIntervals ) {

        for ( int i = 0; i < waitIntervals; i ++ ) {

            if ( newMessage ) {

                newMessage = false;

                return lastCommand;

            }

            Control.sleep( 0.5 );

        }

        return null;

    }

    public Command getLastMessage() {

        while ( !newMessage ) {

            Control.sleep( 0.5 );

        }

        newMessage = false;

        return lastCommand;

    }

    public void setConnectionDetails( String to , String from ) {

        this.to = to;
        this.from = from;

        if ( ping ) {

            connectionCheck = new Subroutine( new ConnectionCheck( this ) );
            connectionCheck.runOnce();

        }

    }

    private class CommandSender implements ThreadRoutine { //Checks to see if connection is still active

        Connection parent;

        public CommandSender( Connection parent ) {

            this.parent = parent;

        }

        @Override
        public void run() {
            
            checkCommandQueue();
            
            processCommands();

        }

    }

    private void checkCommandQueue() {

        ArrayList< String > sendList = ( ArrayList< String > ) commandQueue.clone(); //Copy the command queue
        commandQueue.clear(); //Clear old command queue
        
        for ( String rawCommand : sendList ) {
            
            try {

                sendStringStream( rawCommand );

                if ( debugMode && !rawCommand.contains( "0;ping;END" ) ) {

                    output.disp( "Sent: " + rawCommand );

                }

                sentCommands.put( rawCommand , true );

            }
            catch ( IOException exception ) {

                sentCommands.put( rawCommand , false );

                if ( debugMode &&  ! rawCommand.contains( "0;ping;END" ) ) {

                    output.disp( "Error sending: " + rawCommand );

                }

                commandErrors.put( rawCommand , exception );

            }

            Control.sleep( commandQueueTime / ( double ) 1000 );

        }
        
    }

    private class ConnectionCheck implements ThreadRoutine { //Checks to see if connection is still active

        Connection parent;

        public ConnectionCheck( Connection parent ) {

            this.parent = parent;

        }

        @Override
        public void run() {

            while ( true ) {

                Control.sleep( pingTime );

                try {

                    parent.sendCommand( new Command( to , from , 0 , "ping" ) );

                }
                catch ( IOException e ) {

                    output.disp( "Error: Lost connection to " + to );

                    if ( server != null ) {

                        server.getConnections().remove( parent );

                    }

                    close();

                    break;

                }

            }

        }

    }

    private void processCommands() {

        ArrayList< String > messages = ( ArrayList< String> ) receivedCommandQueue.clone(); //Copy the command queue
        receivedCommandQueue.clear(); //Clear old command queue

        for ( String rawMessage : messages ) {

            Command command = new Command( rawMessage );
            boolean send = server == null;
            
            lastCommand = command;
            newMessage = true;
        
            if ( server != null ) { //Server connection

                boolean hasSent = false;

                for ( Connection connection : server.getConnections() ) {
                     
                    if ( connection.to.equals( command.getToComputer() ) ) {

                        try {

                            hasSent = true;

                            connection.sendCommand( command );
                            
                            if ( debugMode && !rawMessage.contains( "0;ping;END" ) ) {

                                output.disp( "Relayed " + rawMessage + " from " + SystemInformation.getComputerName() );

                            }

                        }
                        catch ( IOException e ) {

                            if ( debugMode &&  ! rawMessage.contains( "0;ping;END" ) ) {

                                output.disp( "Error: Could not relay command" , e );

                            }

                        }

                        break;

                    }
                  
                }

                if ( !hasSent && debugMode && !rawMessage.contains( "0;ping;END" ) ) {

                    output.disp( "Error: Command to " + command.getTo() + " was not processed since it is not in connection list" );

                }

            }
            else { //Bridge connection
                
                if ( debugMode && !rawMessage.contains( "0;ping;END" ) ) {
                    
                    output.disp( "Received command " + command + " going through internal processing" );
                    
                }
                
                bridge.sendCommandSuppressed( command );
                
            }

        }

    }

    private class Listener implements ThreadRoutine { //Cannot be dethreaded

        Connection parent;
        
        public Listener( Connection parent ) {

            this.parent = parent;

        }

        @Override
        public void run() {

            try {

                String rawMessage = "";
                Long lastRead = System.currentTimeMillis();

                Long startTime = -1L;
                
                //Read stream
                while ( true ) {

                    int rawStream = -2;

                    if ( inputStream == null ) {

                        rawStream = objectInputStream.readInt();
                        
                        if ( startTime < 0 ){
                                
                            startTime = System.currentTimeMillis();
                                
                        }
                        
                    }
                    else {

                        if ( inputStream.available() > 0 ) {

                            rawStream = inputStream.read();

                            if ( startTime < 0 ){
                                
                                startTime = System.currentTimeMillis();
                                
                            }
                            
                        }

                    }

                    if ( rawStream == -1 ) {

                        break;

                    }
                    else if ( rawStream != -2 ) {

                        rawMessage += ( char ) rawStream;

                        lastRead = System.currentTimeMillis();

                    }
                    else if ( System.currentTimeMillis() - lastRead > readWaitTime ) {

                        if ( rawMessage.length() > 0 ) {

                            break;

                        }

                    }

                }

                Long time = System.currentTimeMillis() - startTime;
                
                String speed;
                
                if ( time == 0 ){
                    
                    speed = "inf[kb/s]";
                    
                }
                else {
                    
                    speed = ( ( double ) rawMessage.length() / time ) + "[kb/s]";
                    
                }
                
                //Output.print( "Read speed: " + speed );
                
                if ( encypter != null ) {

                    rawMessage = encypter.decrypt( rawMessage );

                }
                
                if ( rawMessage.length() > 2 ){
          
                    if ( debugMode && !rawMessage.contains( "0;ping;END" ) ) {

                        output.disp( "Received: " + rawMessage );

                    }

                    if ( !rawMessage.contains( "0;ping;END" ) ) {
                           
                        receivedCommandQueue.add( rawMessage );

                    }
                
                }
   
            }
            catch ( IOException e ) {
                
                close();
            
            }
          
        }

    }

}
