 package caprica.marks;

import caprica.datatypes.InputDataStream;
import caprica.datatypes.Num;
import caprica.datatypes.SystemFile;
import caprica.system.Output;
import java.io.File;

public class SubSubject {

    private double totalMark;
    private double adjustedMark;
    private int count;
    private double worth;
    private SystemFile markFile;
    
    private Subject subject;
    private String markName;
    
    public SubSubject( Subject subject , String classPath , String markName , int count , double worth ){
        
        this.count = count;
        this.worth = worth;
        this.markName = markName;
        this.subject = subject;
        
        markFile = new SystemFile( classPath + "/" + markName );

        calculateSubMark();
        
    }
    
    public void calculateSubMark(){
        
        double[] marks = new double[ count ];
        
        int index = 0;
        
        if ( markFile.exists() ){
            
            try {
            
                String rawData = markFile.toString();
                String[] markLines = rawData.split( "\n" );

                for ( String rawMark : markLines ){
                    
                    try {
                    
                        Double mark = Double.parseDouble( rawMark );
                        
                        marks[ index ] = mark;
                        
                        index++;
                        
                    }
                    catch( Exception e ){}
                        
                }
                
            
            }
            catch( Exception e ){}
            
        }
        
        if ( index == 0 ){ //No mark entry, assume 100%
            
            if ( markName.equals( "final" ) ){ 
            
                SubSubject midtermSubject = subject.getSubSubject( "midterm" );
                
                totalMark = midtermSubject.totalMark();
                
            }
            else {
                
                totalMark = 1;
                
            }
            
        }
        else if ( index == 1 ){ //Mark is first entry
            
            totalMark = marks[ 0 ];
            
        }
        else {
            
            double sum = 0;
            
            for ( int i = 0 ; i < index ; i++ ){
                
                sum += marks[ i ];
                
            }
            
            double average = sum / index;
            
            totalMark = average;
            
        }
    
        adjustedMark = totalMark * worth * count;
        
    }
    
    public boolean addMark( double value ){
        
        try {
        
            if ( markFile.exists() ){
            
                markFile.write( "" + value + "\n" , true );
            
            }
            else {
            
                markFile.create();
                markFile.write( "" + value + "\n" , false );
            
            }
        
            calculateSubMark();
            
            return true;
        
        }
        catch( Exception e ){
            
            return false;
            
        }
        
    }
    
    public double totalMark(){
        
        return totalMark;
        
    }
    
    public double adjustedMark(){
        
        return adjustedMark;
        
    }
    
    public double getTotalWorth(){
        
        return count * worth;
        
    }
    
}
