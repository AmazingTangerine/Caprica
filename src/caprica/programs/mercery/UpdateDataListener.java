package caprica.programs.mercery;

import caprica.server.Bridge;
import caprica.server.Command;
import static caprica.server.CommunicationConstants.SMS_INFO_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SPLIT;
import static caprica.server.CommunicationConstants.SMS_SUB_SPLIT;
import caprica.system.Control;
import caprica.system.ThreadRoutine;
import java.util.ArrayList;

public class UpdateDataListener implements ThreadRoutine {

    private Main main;
    
    public UpdateDataListener( Main main ){
        
        this.main = main;
        
    }
    
    @Override
    public void run() {

        Bridge bridge = caprica.main.Main.mainLink;
        
        if ( bridge != null ){
            
            if ( bridge.isAlive() ){
 
                if ( !bridge.getStoreValue( "fullTextList" ).equals( "null" ) ){
                    
                    Long highestDate = 0L;
                    
                    main.print( "Processing full text list" );
                    
                    String fullTextList = bridge.getStoreValue( "fullTextList" );
                    bridge.setStoreValue( "fullTextList" , "null" );
                    
                    main.messages = new ArrayList<>();
                    
                    String address = null;
                    int type = 0;
                    Long date = null;
                    String message = null;
                    String person = null;
                    
                    for ( String textRaw : fullTextList.split( SMS_SPLIT ) ){
                        
                        for ( String subData : textRaw.split( SMS_SUB_SPLIT ) ){
                            
                            String[] datum = subData.split( SMS_INFO_SPLIT );
                      
                            if ( datum[ 0 ].equals( "address" ) ){
                                
                                address = datum[ 1 ];
                                
                            }
                            else if ( datum[ 0 ].equals( "type" ) ){
                                
                                type = Integer.parseInt( datum[ 1 ] ); 
                                
                            }
                            else if ( datum[ 0 ].equals( "person" ) ){
                                
                                person = datum[ 1 ];
                                
                            }
                            else if ( datum[ 0 ].equals( "date" ) ){
                                
                                date = Long.parseLong( datum[ 1 ] );
                                
                                if ( date > highestDate ){
                                    
                                    highestDate = date;
                                    
                                }
                                
                            }
                            else if ( datum[ 0 ].equals( "body" ) ){
                                
                                message = datum[ 1 ];
                                
                            }
                            
                        }
                        
                        Text text = new Text( type , message , date , address , person );
                        
                        boolean hasText = false;
                        
                        for ( Text checkText : main.messages ){
                            
                            if ( checkText.getAddress().equals( text.getAddress() ) && checkText.getDate().equals( text.getDate() ) && checkText.getMessage().equals( text.getMessage() ) && checkText.getType() == text.getType() ){
                                
                                hasText = true;
                                break;
                                
                            }
                            
                        }
                        
                        if ( !hasText ){
                            
                            main.messages.add( text );
                            
                        }
                        
                    }
                    
                    main.getWindow().refreshData();
                    
                    main.lastUpdate = highestDate;
                    
                }
                
            }
            
        }
        
    }

}
