package caprica.programs.diligence;


import caprica.datatypes.Vector;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Window extends JFrame {

    private Main main;
    private JLabel statusLabel;

    public Window( Main main ) {

        super( "Denis" );
        
        this.main = main;
        
        Vector size = new Vector( 1600 * 0.5 , 900 * 0.5 );
        
        this.setPreferredSize( size.asDimension() );
        this.setSize( size.asDimension() );
        this.setResizable( false );
        this.addWindowListener( new WindowCloser() );

        initializeStatusBar();

        this.pack();
        this.setVisible( true );
   
    }
    
    public class WindowCloser extends WindowAdapter {
    
        @Override
        public void windowClosing( WindowEvent event ) {
       
            main.getWindow().dispose();
            
        }
    
    }
    

    private void initializeStatusBar() {

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder( new BevelBorder( BevelBorder.LOWERED ) );
        this.add( statusPanel , BorderLayout.SOUTH );

        statusPanel.setPreferredSize( new Dimension( this.getWidth() , 16 ) );
        statusPanel.setLayout( new BoxLayout( statusPanel , BoxLayout.X_AXIS ) );

        statusLabel = new JLabel( "Status" );
        statusLabel.setHorizontalAlignment( SwingConstants.LEFT );

        statusPanel.add( statusLabel );
     
    }
    
    public void setStatusBarText( String text ) {

        statusLabel.setText( text );

    }
    
    
}
