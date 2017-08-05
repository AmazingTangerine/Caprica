package caprica.server;

public interface CommandManager {
    
    public void Manage( Command command );
    
    public void setBridge( Bridge bridge );
    
    public String getName();
    
}
