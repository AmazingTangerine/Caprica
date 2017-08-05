package caprica.assimilation;

import caprica.datatypes.InputDataStream;
import caprica.datatypes.SystemFile;
import caprica.internet.NetworkInformation;
import caprica.internet.Webpage;
import caprica.system.Output;
import caprica.system.SystemInformation;
import static caprica.main.Main.config;
import java.io.IOException;

public class UpdateFiles {

    public static void updateFiles() {

        Output output = new Output( "FileUpdate" );
        
        output.disp( "Starting update file process" );

        String mainURL = "http://192.168.0.10/";
        String[] subItems = new String[]{ "Caprica.jar" , "lib/javax.mail.jar" };

        output.disp( "Checking to see if project folder exists" );

        output.disp( "Creating temp folder" );

        SystemFile tempFolder = new SystemFile( SystemInformation.getAppData() + "temp/" );

        if ( tempFolder.exists() ) {

            tempFolder.delete();

        }

        if ( tempFolder.create() ) {

            output.disp( "Moving network copy of jar files to temp folder" );

            try {

                for ( String subItem : subItems ) {

                    InputDataStream stream = Webpage.download( mainURL + subItem );
                    SystemFile tempFile = new SystemFile( SystemInformation.getAppData() + "temp/" + subItem );
                    tempFile.create();
                    stream.write( tempFile.getOutputStream() );

                }

            }
            catch ( Exception e ) {

                output.disp( "Error: Could not download file" , e );

            }

            output.disp( "Delete old lib and Caprica files" );
            String[] deletionPaths = new String[]{ "Caprica.jar" , "lib" };

            for ( String deletionPath : deletionPaths ) {

                SystemFile deleteFile = new SystemFile( SystemInformation.getAppData() + deletionPath );

                deleteFile.delete();

            }

            output.disp( "Copying temp files to current directory" );

            for ( SystemFile tempFile : new SystemFile( SystemInformation.getAppData() + "temp/" ).listContents() ) {

                try {

                    tempFile.copy( new SystemFile( tempFile.getFilePath().replace( "temp/" , "" ) ) );

                }
                catch ( IOException ePrime ) {

                    output.disp( "Error: Could not move files from temp, this is really bad" , ePrime );

                }

            }

        }
        else {

            output.disp( "Error: Could not create temp folder" );

        }

    }

}


