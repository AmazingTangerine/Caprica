package caprica.graphics;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import caprica.system.ThreadRoutine;
import java.awt.Graphics;

public class Button extends Component {

    private ThreadRoutine action = null;
    private Colour textColour = new Colour( 0 , 0 , 0 );
    private Colour accentColour = new Colour( 0 , 0 , 0 );
    private String style = "box";
    private String text = "";
    
    @Override
    public void draw( Graphics plane ){
    
        plane.setColor( super.getColour().getAWTColor() );
        plane.fillRect( getPos().getX().toInt() , getPos().getY().toInt() , getBounds().getX().toInt() , getBounds().getY().toInt() );
        
        if ( style.equals( "box" ) ){
        
            if ( !text.equals( "" ) ){ //No point in drawing a null text
                
                Label buttonLabel = new Label();
                buttonLabel.setColour( getColour() );
                buttonLabel.setTextColour( textColour );
                buttonLabel.setText( text );
                buttonLabel.setOrientation( new Vector( 0 , 0 ) );
                buttonLabel.setBounds( getBounds() );
                buttonLabel.setPos( getPos() );
                buttonLabel.draw( plane );
                
            }
            
        }
        else if ( style.contains( "space" ) ){
            
            if ( style.contains( "1" ) || style.contains( "2" ) ){
            
                Num deltaS = getBounds().getY().mul( 0.25 );
            
                Num arrowDim = getBounds().getY().min( deltaS ).div( 2 );
                Num offset = getBounds().getY().div( 2 ).min( arrowDim.div( 2 ) );
            
                Vector arrowSize = new Vector( arrowDim , arrowDim );
            
                Arrow arrow = new Arrow();
            
                Num[] relativeX = new Num[]{ deltaS.div( 2 ) , arrowSize.getX().add( deltaS.div( 2 ) ) , getBounds().getX().min( arrowSize.getX().mul( 2 ) ).min( deltaS.div( 2 ) ) , getBounds().getX().min( arrowSize.getX() ).min( deltaS.div( 2 ) ) };
                Num[] angles = new Num[]{ new Num( 0 ) , new Num( 0 ) , new Num( 180 ) , new Num( 180 ) };
                
                if ( style.equals( "space2" ) ){
                    
                    angles = new Num[]{ new Num( 180 ) , new Num( 180 ) , new Num( 0 ) , new Num( 0 ) };
                    
                }
            
                for ( int i = 0 ; i < relativeX.length ; i++ ){
            
                    arrow.setBounds( arrowSize );
                    arrow.setPos( getPos().add( new Vector( relativeX[ i ] , offset ) ) );
                    arrow.setColour( accentColour );
                    arrow.setAngle( angles[ i ] );
                    arrow.draw( plane );
            
                }
            
                if ( !text.equals( "" ) ){ //No point in drawing a null text
                
                    Vector textSize = getBounds().add( arrowSize.getX().mul( 4 ).add( deltaS.mul( 1 ) ).negate() , arrowDim.negate() );
                    Vector textPos = getPos().add( deltaS.div( 2 ).add( arrowSize.getX().mul( 2 ) ) , offset.min( deltaS.div( 2 ) ) );
                
                    Label buttonLabel = new Label();
                    buttonLabel.setColour( getColour() );
                    buttonLabel.setTextColour( textColour );
                    buttonLabel.setText( text );
                    buttonLabel.setOrientation( new Vector( 0 , 0 ) );
                    buttonLabel.setBounds( textSize );
                    buttonLabel.setPos( textPos );
                    buttonLabel.draw( plane );
                
                }
            
            }
           
        }
        
    }
    
    public void setButtonAction( ThreadRoutine action ){
        
        this.action = action;
        
    }
    
    public ThreadRoutine getAction(){
        
        return action;
        
    }
    
    public void setTextColour( Colour textColour ){
        
        this.textColour = textColour;
        
    }
    
    public void setAccentColour( Colour accentColour ){
        
        this.accentColour = accentColour;
        
    }
    
    public void setStyle( String style ){
        
        this.style = style;
        
    }
    
    public void setText( String text ){
        
        this.text = text;
        
    }
    
}
