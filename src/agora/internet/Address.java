package agora.internet;


public class Address {

    String internalIP;
    String externalIP;
    String MAC;
    
    public Address(){
        
        internalIP = Information.internalIP();
        externalIP = Information.externalIP();
        MAC = Information.getMAC();
        
    }
    
}
