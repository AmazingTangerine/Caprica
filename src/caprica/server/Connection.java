package caprica.server;

import caprica.system.Control;
import caprica.system.Subroutine;
import caprica.system.ThreadRoutine;
import caprica.encyption.BlockAssembly;
import caprica.encyption.RSA;
import caprica.system.Output;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Connection {
    
    private Socket socket;
    
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    
    private InputStreamReader httpInputStream;
    private BufferedWriter httpOutputStream;    
    
    private List< CommandManager > commandManagers = new ArrayList<>();
    private Subroutine listener; 
    
    private Command lastCommand;
    private boolean newMessage = false;
    
    private BlockAssembly assembly;
    private String encyptionKey;
    private RSA encypter;
    
    private String username = null;
    private String ip;
    private HashMap< String , String > dataPool = new HashMap<>();
    
    private String id;
    
    boolean httpMode = false;
    
    public Connection( Socket inputSocket , String ip , String encyptionKey , RSA encypter ) throws IOException {
        
        this.ip = ip;
        this.encypter = encypter;
        
        this.socket = inputSocket;
        
        this.outputStream = new ObjectOutputStream( this.socket.getOutputStream() );
        outputStream.flush();
        
        this.inputStream = new ObjectInputStream( this.socket.getInputStream() );
     
        this.encyptionKey = encyptionKey;
        
        assembly = new BlockAssembly( encyptionKey );
        
        listener = new Subroutine( new Listener( this ) );
        listener.start();

    }
    
    public void setID( String id ){
        
        this.id = id;
        
    }
    
    public String getID(){
        
        return id;
        
    }
    
    public void sendCommand( Command command ) throws IOException {

        if ( httpMode ){
            
            httpOutputStream.write( command.toString() , 0 , command.toString().length() );
            httpOutputStream.newLine();
            httpOutputStream.flush();
            
        }
        else {
        
            InformationBlock messageBlock = new InformationBlock( command.toString() , assembly , encypter );
            messageBlock.write( outputStream );
        
        }

    }
    
    public void sendCommandSurpressed( Command command ){ //Does not throw a warning if message could not be sent
        
        try {
  
            InformationBlock messageBlock = new InformationBlock( command.toString() , assembly , encypter );
            messageBlock.write( outputStream );
   
        }
        catch( Exception e ){}
        
    }
    
    public void addCommandManager( CommandManager manager ){

        manager.setConnection( this );
        commandManagers.add( manager );
        
    }
    
    public HashMap< String , String > getDataPool(){
        
        return dataPool;
        
    }
    
    public void setUsername( String username ){
        
        this.username = username;
        
    }
    
    public String getUsername(){
        
        return username;
        
    }
    
    public String getIP(){
        
        return ip;
        
    }
    
    public String getEncyptionKey(){
        
        return encyptionKey;
        
    }
    
    public RSA getRSA(){
        
        return encypter;
        
    }
    
    public Socket getSocket() {
        
        return socket;
        
    }
    
    public void close() throws IOException {
        
        if ( httpMode ){
            
            
        }
        else {
        
            listener.stop();

            outputStream.close();
            inputStream.close();
        
            socket.close();
        
        }
           
    }
    
    public Command getLastCommand( int waitIntervals ){
        
        for ( int i = 0 ; i < waitIntervals ; i++ ){
            
            if ( newMessage ){
                
                newMessage = false;
                
                return lastCommand;
                
            }
            
            Control.sleep( 0.5 );
            
        }

        return null;
        
    }

    public Command getLastMessage(){
        
        while ( !newMessage ){
            
            Control.sleep( 0.5 );
            
        }
                
        newMessage = false;
        
        return lastCommand;
        
    }
   
    
    private class Listener implements ThreadRoutine {
     
        Connection parent;
        
        public Listener( Connection parent ){
            
            this.parent = parent;
            
        }
        
        @Override
        public void run() {
            
            try {
            
                InformationBlock blockMessage = new InformationBlock( inputStream , assembly , encypter );
               
                String decyptedMessage = blockMessage.getMessage();
         
                lastCommand = new Command( decyptedMessage );
               
                newMessage = true;
                
                for ( CommandManager manager : commandManagers ){
                    
                    manager.Manage( lastCommand );
                    
                }

            }
            catch( Exception e ){ listener.stop(); }
            
        }
        
    }

}

