package caprica.systemtray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

public class Tray {

    private PopupMenu popup;
    private SystemTray tray;
    private TrayIcon icon;
    
    public Tray( String imagePath ){
        
        popup = new PopupMenu();
        tray = SystemTray.getSystemTray();
        
        Image image = Toolkit.getDefaultToolkit().getImage( imagePath );
        
        icon = new TrayIcon( image , "Caprica" , popup );
        icon.setImageAutoSize( true );
        
        try {
        
            tray.add( icon );
        
        }
        catch( Exception e ){e.printStackTrace();}
        
    }
    
    public void add( MenuItem item ){
        
        popup.add( item );
        
    }
    
    public void addSepreator(){
        
        popup.addSeparator();
        
    }
    
    public void addListener( ActionListener listener ){
        
        popup.addActionListener( listener );
        
    }
    
}
