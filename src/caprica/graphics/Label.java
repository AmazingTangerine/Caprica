package caprica.graphics;

import caprica.datatypes.Num;
import caprica.datatypes.Vector;
import caprica.system.Output;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class Label extends Component {

    private String text = "";
    private Colour textColour = new Colour( 0 , 0 , 0 );
    private Vector centering = new Vector( -1 , 0 );
    
    public void setText( String text ){
        
        this.text = text;
        
    }
    
    public void setTextColour( Colour textColour ){
        
        this.textColour = textColour;
        
    }
    
    public void setOrientation( Vector centering ){
        
        this.centering = centering;
        
    }
    
    @Override
    public void draw( Graphics plane ){
    
        plane.setColor( getColour().getAWTColor() );
        plane.fillRect( getPos().getX().toInt() , getPos().getY().toInt() , getBounds().getX().toInt() , getBounds().getY().toInt() );
    
        Font font;
        String compilation = "";
        String relativeLine;
        int count;
        int fontDim = 0;
        
        for ( int i = 200 ; i >= 0 ; i-- ){
            
            font = new Font( "TimesRoman" , Font.PLAIN , i );
            compilation = "";
            relativeLine = "";
            count = 0;
            
            for ( char character : text.toCharArray() ){ //Assembles into paragraphs
                
                compilation += character;
                
                if ( character == '\n' ){
                
                    relativeLine = "";
                
                }
                else {
                    
                    relativeLine += character;
                    
                    int fontWidth = getFontSize( font , plane , relativeLine ).getX().toInt();
                    
                    if ( fontWidth > getBounds().getX().toInt() ){ //Out of bounds
                        
                        String newLine = "";
                   
                        for ( int x = compilation.length() - 1 ; x > -1 ; x-- ){
                            
                            char back = compilation.toCharArray()[ x ];
                            
                            if ( x == 0 || back == ' ' ){
                                
                                if ( compilation.charAt( x ) != ' ' ){
                                    
                                    newLine += compilation.charAt( x );
                                    
                                }
                                
                                newLine += compilation.substring( x + 1 , compilation.toCharArray().length );
                                compilation = compilation.substring( 0 , x );
                                
                                break;
                                
                            }
                            
                        }

                        compilation += "\n" + newLine;
                        
                        relativeLine = "" + character;
                        
                    }
                    else {
                        
                        //We still good
                        if ( count + 1 == text.toCharArray().length ){ //End of line
                            
                            //compilation += relativeLine;
                            
                        }
                        
                    }
                    
                }
                
                count++;
                
            }
            
            boolean isBig = false;
            int yCount = 0;
                
            for ( String line : compilation.split( "\n" ) ){
                    
                Vector lineSize = getFontSize( font , plane , line );
                
                if ( !lineSize.getX().less( getBounds().getX() ) ){
                        
                    isBig = true;
                    break;
                        
                }
                
                yCount += lineSize.getY().toInt();
                
            }
            
            if ( yCount > getBounds().getY().toInt() ){
                    
                isBig = true;
                    
            }
                
            if ( isBig ){
                    
                //Font is too big
                    
            }
            else {
                    
                //Font is right size
                fontDim = i;
                break;
                    
            }
         
        }
        
        font = new Font( "TimesRoman" , Font.PLAIN , fontDim );
     
        plane.setFont( font );
        plane.setColor( textColour.getAWTColor() );
        
        for ( int i = 0 ; i < compilation.split( "\n" ).length ; i++ ){
            
            String line = compilation.split( "\n" )[ i ];
            Vector currFontSize = getFontSize( font , plane , line );
            
            Num centerXBox = getPos().getX().add( getBounds().getX().div( 2 ) );
            Num centerXText = getPos().getX().add( currFontSize.getX().div( 2 ) );

            Num deltaX = centerXBox.min( centerXText );
   
            int xPos = getPos().getX().add( deltaX ).add( centering.getX().mul( deltaX ) ).toInt();
            int yPos = getPos().getY().add( getBounds().getY().div( 2 ) ).add( currFontSize.getY().div( 2 ) ).add( centering.getY().mul( currFontSize.getY().div( 2 ) ) ).toInt(); 
            
            plane.drawString( line , xPos , yPos );
            
        }
       
    }
    
    public static Vector getFontSize( Font font , Graphics plane , String text ){
        
        ( ( Graphics2D ) plane ).setFont( font );
        FontRenderContext renderContext = ( ( Graphics2D ) plane ).getFontRenderContext();
        GlyphVector glyph = ( ( Graphics2D ) plane ).getFont().createGlyphVector( renderContext , text );
        
        Rectangle rect = glyph.getPixelBounds( null , 0 , 0 );
        
        FontMetrics metrics = plane.getFontMetrics( font );

        int width = metrics.stringWidth( text );
        int height = metrics.getHeight() / 2;
        
        return new Vector( width , height );

    }
    
}
