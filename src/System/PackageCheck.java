package System;

public class PackageCheck {
    
    static String[] packageList = new String[]{ "ssh" , "arp-scan" };
    
    public static int check(){
        
        int installed = 0;
        
        for ( String program : packageList) {
        
           String dpkgOutput = Control.exec( "dpkg-query -W -f='${Status}' " + program );

           if ( !dpkgOutput.equals( "'install ok installed'" ) ){
               
               Control.exec( "apt-get install -y " + program );
               
               installed++;
               
           }
           
        }
        
        return installed;
        
    }
    
}
