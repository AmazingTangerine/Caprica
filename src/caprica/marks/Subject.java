package caprica.marks;

import caprica.datatypes.Config;
import caprica.datatypes.SystemFile;
import caprica.system.Output;
import java.util.HashMap;

public class Subject {

    private HashMap< String , SubSubject > subMarks = new HashMap<>();
    private double totalMarkCount = 0;
    private String className;
    
    public Subject( SystemFile configFile ){
        
        Config classConfig = new Config( new SystemFile( configFile.getPath() + "/" + "overview.conf" ) );
        
        className = classConfig.get( "name" );
        
        String[] markCatagories = classConfig.get( "sub_catagories" ).split( "," );

        for ( String catagorie : markCatagories ){

            int count = Integer.parseInt( classConfig.get( "catagorie_" + catagorie + "_count" ) );
            double worth = Double.parseDouble( classConfig.get( "catagorie_" + catagorie + "_worth" ) );
            
            SubSubject subSubject = new SubSubject( this , configFile.getPath() , catagorie , count , worth );
            
            subMarks.put( catagorie , subSubject );
            
            totalMarkCount += count * worth;
            
        }
 
    }
    
    public String getName(){
        
        return className;
        
    }
    
    public double currentMark(){
        
        double markSum = 0;
        
        for ( SubSubject subSubject : subMarks.values() ){
            
            markSum += subSubject.adjustedMark();
            
        }
        
        return markSum;
        
    }
    
    public SubSubject getSubSubject( String name ){
        
        return subMarks.get( name );
        
    }
    
    public double markNeededTo( double desiredMark , String subMark ){
        
        double preMark = 0;
        
        for ( String subjectName : subMarks.keySet() ){
            
            if ( !subjectName.equals( subMark ) ){
            
                preMark += subMarks.get( subjectName ).adjustedMark();
            
            }
            
        }
        
        double requiredMark = ( desiredMark - preMark ) / subMarks.get( subMark ).getTotalWorth();
        
        return requiredMark;
        
    }
    
}
