package caprica.programs.mercery;


import caprica.programs.diligence.*;
import caprica.datatypes.Vector;
import caprica.system.Control;
import caprica.system.Output;

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
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class Window extends JFrame {

    private Main main;
    private JLabel statusLabel;
    private JList contactsContentList;
    private JTextArea messageContentArea;
    private DefaultListModel listModel;
    private JTextField textField;
    private String[] contacts;
   
    public Window( Main main ) {

        super( "Mercery" );
        
        this.main = main;
        
        Vector size = new Vector( 1600 * 0.5 , 900 * 0.5 );
        
        this.setPreferredSize( size.asDimension() );
        this.setSize( size.asDimension() );
        this.setResizable( false );
        this.addWindowListener( new WindowCloser() );

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout( new BorderLayout() );
        
        listModel = new DefaultListModel();
        
        contactsContentList = new JList();
        contactsContentList.addListSelectionListener( new ContactListSelectionListener( main ) );
        contactsContentList.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );
        contactsContentList.setModel( listModel );
        
        JPanel sideRightPanel = new JPanel();
        sideRightPanel.setPreferredSize( size.scale( 0.74 , 1 ).asDimension() );
        
        messageContentArea = new JTextArea();
        messageContentArea.setEditable( false );
        
        JScrollPane scrollMessagePane = new JScrollPane( messageContentArea );
        scrollMessagePane.setPreferredSize( size.scale( 0.74 , 0.8 ).asDimension() );
        
        textField = new JTextField();
        textField.addKeyListener( new SendListener( main ) );
        textField.setPreferredSize( size.scale( 0.74 , 0.05 ).asDimension() );
        
        sideRightPanel.add( scrollMessagePane );
        sideRightPanel.add( textField );
        
        JPanel sideLeftPanel = new JPanel();
        sideLeftPanel.setPreferredSize( size.scale( 0.25 , 1 ).asDimension() );
        
        JScrollPane scrollContactsPane = new JScrollPane( contactsContentList );
        scrollContactsPane.setPreferredSize( size.scale( 0.25 , 0.95 ).asDimension() );
        
               
        sideLeftPanel.add( scrollContactsPane );
        
        contentPanel.add( sideLeftPanel , BorderLayout.LINE_START );
        contentPanel.add( sideRightPanel , BorderLayout.LINE_END );
        this.add( contentPanel );
        
        initializeStatusBar();

        this.pack();
        this.setVisible( true );

    }
    
    public JTextField getSendField(){
        
        return textField;
        
    }
    
    public JTextArea getMessageContentArea(){
        
        return messageContentArea;
        
    }
    
    public DefaultListModel getContactsListModel(){
        
        return listModel;
        
    }
    
    public JList getContactsContentList(){
        
        return contactsContentList;
        
    }
    
    public void setContactList( String[] contacts ){
        
        listModel.clear();

        for ( String element : contacts ){
            
            listModel.addElement( element );
            
        }

        
        contactsContentList.updateUI();
        contactsContentList.setListData( contacts );
        
        this.contacts = contacts;
 
    }
    
    public String[] getContacts(){
        
        return this.contacts;
        
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
    
    public void refreshData(){
        
        main.print( "Refreshing contact list" );
        
        ArrayList< String > contacts = new ArrayList<>();
        
        for ( Text text : main.messages ){
            
            if ( !contacts.contains( text.getAddress() ) ){
                
                contacts.add( text.getAddress() );
                
            }
            
        }
 
        String[] contactsA = new String[ contacts.size() ];
        
        int i = 0;
        
        for ( String contact : contacts ){
            
            contactsA[ i ] = contact;
            
            i++;
            
        }
        
        setContactList( contactsA );
        
    }
    
}
