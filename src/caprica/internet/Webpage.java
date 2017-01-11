package caprica.internet;

import caprica.datatypes.InputDataStream;
import caprica.datatypes.SystemFile;
import caprica.system.Output;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Webpage {
    
    public static InputDataStream download( String urlAddress ) throws Exception {
        
        URL page = new URL( urlAddress );
        
        URLConnection connection = page.openConnection();

        connection.addRequestProperty( "User-Agent" , "Mozilla/4.76" ); 
        
        connection.connect();
  
        return new InputDataStream( connection.getInputStream() );  
        
    }
    
    public static void downloadFile( String urlAddress , String fileAddress ) throws IOException {
        
        URL page = new URL( urlAddress );
        
        HttpURLConnection connection = ( HttpURLConnection ) page.openConnection();

        connection.addRequestProperty("User-Agent", "Mozilla/4.76"); 
        connection.setRequestMethod( "GET" );
        
        SystemFile downloadFile = new SystemFile( fileAddress );
        
        if ( downloadFile.exists() ){ downloadFile.delete(); }
        
        downloadFile.create();
        
        InputStream inputStream = page.openStream();
        FileOutputStream outputStream = new FileOutputStream( fileAddress );
        
        byte[] data = new byte[ 1024 ];
        
        int datum = -1;
        
        while ( ( datum = inputStream.read( data ) ) != -1 ){
            
            outputStream.write( data , 0 , datum );
            
        }
        
        inputStream.close();
        outputStream.close();
        
    }
    
}
