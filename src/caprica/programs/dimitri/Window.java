package caprica.programs.dimitri;

import caprica.programs.dennis.*;
import caprica.datatypes.Vector;
import caprica.language.Interface;
import caprica.system.Output;
import caprica.system.SystemInformation;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import caprica.server.Command;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Window extends JFrame {

    private Main main;

    public Window( Main main ) {

        super( "Dennis" );
        
        this.main = main;
        
        Vector size = new Vector( 1600 * 0.5 , 900 * 0.5 );
        
        JPanel contentPanel = new JPanel();
        
        this.setPreferredSize( size.asDimension() );
        this.setSize( size.asDimension() );
        this.setResizable( false );
        this.addWindowListener( new WindowCloser() );
        contentPanel.setLayout( new BoxLayout( contentPanel , BoxLayout.Y_AXIS ) );

        JTextArea textArea = new JTextArea( 50 , 20 );
        textArea.setEditable( false );
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JTextField inputField = new JTextField();
        inputField.addKeyListener( new KeyListener(){

            @Override
            public void keyTyped( KeyEvent event ) {

                int code = event.getKeyChar();
                
                if ( code == 10 ){
                    
                    String text = inputField.getText();
                    inputField.setText( "" );
                    
                    String response = caprica.main.Main.languageInterface.process( text );
                    
                    textArea.append( text + "\n>" + response + "\n" );
                    
                }
                
            }

            @Override
            public void keyPressed( KeyEvent e ) {}

            @Override
            public void keyReleased( KeyEvent e ) {}
           
        });
        
        contentPanel.add( scrollPane );
        contentPanel.add( inputField );
         
        this.add( contentPanel );
        
        this.pack();
        this.setVisible( true );
   
    }
    
    public class WindowCloser extends WindowAdapter {
    
        @Override
        public void windowClosing( WindowEvent event ) {
       
            main.getWindow().dispose();
            
        }
    
    }
    
}
