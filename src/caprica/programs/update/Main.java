package caprica.programs.update;

import caprica.datatypes.SystemFile;
import caprica.system.ConstructedRoutine;
import caprica.system.Control;
import caprica.system.Output;
import caprica.system.ProgramMap;
import caprica.system.SystemInformation;
import java.util.ArrayList;
import java.util.HashMap;

public class Main implements ConstructedRoutine {

    private String[] arguments;
    private HashMap< String , String > nameLocations;
    
    @Override
    public String run() {

        nameLocations = new HashMap<>();
     
        for ( int i = 0 ; i < arguments.length ; i++ ){
            
            if ( arguments[ i ].equals( "Pictures" ) ){
                
                nameLocations.put( "Pictures" ,  "C:\\Users\\Julia\\Google Drive\\Pictures\\" );
                
            }
            else if ( arguments[ i ].equals( "USB" ) ){
                
                nameLocations.put( "USB" , "H:\\" );
                
            }
            else if ( arguments[ i ].equals( "Drive" ) ){
                
                nameLocations.put( "GoogleDrive" , "C:\\Users\\Julia\\Google Drive\\" );
                
            }
            
        }
        
        update();

        return "";
        
    }

    @Override
    public void construct( Object[] objectArguments ) {

        this.arguments = new String[ objectArguments.length ];
        
        for ( int i = 0 ; i < this.arguments.length ; i++ ){
            
            arguments[ i ] = ( String ) objectArguments[ i ];
            
        }
        
    }

    @Override
    public void setProgramMap( ProgramMap map ) {}

    public void update(){
        
        SystemFile backupDrive = new SystemFile( "G:/" );
        
        Output.print( "Starting update" );
        
        if ( backupDrive.exists() ){
            
            String date = SystemInformation.getDate();
            
            SystemFile updateFolder = new SystemFile( "G:/" + date.replace( "/" , "_" ) + "/" );

            if ( !updateFolder.exists() ){
                
                updateFolder.create();
                
            }
                
            for ( String folderName : nameLocations.keySet() ){
                        
                SystemFile subUpdateFolder = new SystemFile( updateFolder.getPath() + folderName + "/" );
                subUpdateFolder.create();
                        
                if ( subUpdateFolder.exists() ){
                            
                    SystemFile copyFromFolder = new SystemFile( nameLocations.get( folderName ) );
                            
                    if ( copyFromFolder.exists() ){
                                
                        ArrayList< SystemFile > contents = copyFromFolder.listWholeContents();
                                
                        for ( SystemFile copyFile : contents ){
                                    
                            SystemFile newFile = new SystemFile( subUpdateFolder.getFilePath() + copyFile.getPath().replace( nameLocations.get( folderName ).replace( "\\" , "/" ) , "" ) );

                            try {
                                    
                                copyFile.copy( newFile );
                                    
                            }
                            catch( Exception e ){}
                                    
                        }
  
                    }
                            
                }
               
                Output.print( "Update complete" );
                    
            }

            
        }
        else {
            
            Output.print( "Error: Backup drive does not exist" );
            
        }
        
    }
    
}
