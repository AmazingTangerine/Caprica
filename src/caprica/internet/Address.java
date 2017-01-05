package caprica.internet;

public class Address {

    public String internalIP = "Unknown";
    public String externalIP = "Unknown";
    public String MAC = "Unknown";
    
    public Address(){
 
        NetworkInformation networkInformation = new NetworkInformation();
        
        internalIP = networkInformation.internalIP();
        externalIP = networkInformation.externalIP();
        MAC = networkInformation.getMAC();
 
    }
    
    public Address( String externalIP , String internalIP , String MAC ){
        
        this.internalIP = internalIP;
        this.externalIP = externalIP;
        this.MAC = MAC;
        
    }
    
    public static Address fromString( String fromString ){
      
        return new Address( fromString.split( ";" )[ 0 ] , fromString.split( ";" )[ 1 ] , fromString.split( ";" )[ 2 ] );
    
    }
    
    public String toString(){
        
        return  externalIP + ";" + internalIP + ";" + MAC;
        
    }
    
}
