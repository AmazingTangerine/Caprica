package caprica.programs.mercery;

public class Text {

    private int type;
    private String message;
    private Long date;
    private String address;
    private String person;
    
    public Text( int type , String message , Long date , String address , String person ){
        
        this.type = type;
        this.message = message;
        this.date = date;
        this.address = address;
        this.person = person;
        
    }
    
    public int getType(){
        
        return type;
        
    }
    
    public String getMessage(){
        
        return message;
        
    }
    
    public Long getDate(){
        
        return date;
        
    }
    
    public String getAddress(){
        
        return address;
        
    }
    
    public String getPerson(){
        
        return person;
        
    }
    
}
