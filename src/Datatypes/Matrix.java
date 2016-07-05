package Datatypes;

public class Matrix {

    Object[][] data;

    public Matrix( Num sizeX , Num sizeY ){
        
        data = new Object[ sizeX.toInt() ][ sizeY.toInt() ];
        
        clear( null );
        
    }
    
    public Matrix( Vector... vectors ){ //Hey Victor I heard your dad died
        
        data = new Object[ vectors[ 0 ].data.size() ][ vectors.length ];
        
        Num counter = new Num( 0 );
        
        for ( Vector vector : vectors ){
            
            for ( Num x = new Num( 0 ) ; x.less( vector.data.size() ) ; x.increment() ){
                
                data[ x.toInt() ][ counter.toInt() ] = vector.data.get( x.toInt() );
                
            }
     
            counter.increment();
            
        }
        
    }
    
    public Matrix( Object[][] dataInput ){
        
        data = new Object[ dataInput.length ][ dataInput[ 0 ].length ];
        
        for ( Num x = new Num( 0 ) ; x.less( dataInput.length ) ; x.increment() ){
            
            for ( Num y = new Num( 0 ) ; y.less( dataInput[ 0 ].length ) ; y.increment() ){
                
                data[ x.toInt() ][ y.toInt() ] = dataInput[ x.toInt() ][ y.toInt() ];
                
            }
            
        }
        
    }
    
    /**
     * Gets an object in the matrix
     * @param x The width coordinate in the matrix
     * @param y The height coordinate in the matrix
     * @return The object in the matrix
     */
    public Object get( Num x , Num y ){
        
        return data[ x.toInt() ][ y.toInt() ];
        
    }
    
    /**
     * Sets a value in the matrix
     * @param x The width cursor to be set
     * @param y The height cursor to be set
     * @param datum The value to be set in the matrix
     */
    public void set( Num x , Num y , Object datum ){
        
        data[ x.toInt() ][ y.toInt() ] = datum;
        
    }
    
    /**
     * Gets the width of the matrix
     * @return The width of the matrix
     */
    public Num getX(){
        
        return new Num( data.length );
        
    }
    
    /**
     * Gets the height of the matrix
     * @return The height of the matrix
     */
    public Num getY(){
        
        return new Num( data[ 0 ].length );
        
    }
    
    /**
     * Creates a copy of the matrix
     * @return Copied matrix
     */
    public Matrix copy(){
        
        Matrix photoCopy = new Matrix( getX() , getY() );
        
        for ( Num x = new Num( 0 ) ; x.less( getX() ) ; x.increment() ){
            
            for ( Num y = new Num( 0 ) ; y.less( getY() ) ; y.increment() ){
                
                photoCopy.set( x , y , get( x , y ) );
                
            }
            
        }
        
        return photoCopy;
        
    }
    
    public void clear( Object clearer ){
        
        for ( Num x = new Num( 0 ) ; x.less( getX() ) ; x.increment() ){
            
            for ( Num y = new Num( 0 ) ; y.less( getY() ) ; y.increment() ){
                
                set( x , y , clearer );
                
            }
            
        }
        
    }
    
    public void pushUp(){
        
        Matrix oldMatrix = copy();

        for ( Num x = new Num( 0 ) ; x.less( getX() ) ; x.increment() ){
            
            for ( Num y = new Num( 0 ) ; y.less( getY().add( -1 ) ) ; y.increment() ){
                
                set( x , y , oldMatrix.get( x , y.add( 1 ) ) );
                
            }
            
        }
        
    }
    
    public Matrix add( Matrix inputMatrix ){
        
        if ( getX().equals( inputMatrix.getX() ) && getY().equals( inputMatrix.getY() ) ){ //Matricies must be same size
            
            Matrix formed = new Matrix( getX() , getY() );
            
            for ( Num x = new Num( 0 ) ; x.less( getX() ) ; x.increment() ){
            
                for ( Num y = new Num( 0 ) ; y.less( getY() ) ; y.increment() ){
                
                    formed.set( x , y , ( ( Num ) get( x , y ) ).add( ( Num ) inputMatrix.get( x , y ) ) );
                
                }
            
            }   
            
            return formed;
            
        }
        
        return null;
        
    }
    
    public Matrix scale( Object scalingValue ){
        
        Matrix scaled = new Matrix( getX() , getY() );
        
        for ( Num x = new Num( 0 ) ; x.less( getX() ) ; x.increment() ){
            
            for ( Num y = new Num( 0 ) ; y.less( getY() ) ; y.increment() ){
                
                scaled.set( x , y , ( ( Num ) get( x , y ) ).multiply( scalingValue ) );
                
            }
            
        }  
        
        return scaled;
        
    }
    
    public Matrix transpose(){
        
        Matrix transposistion = new Matrix( getY() , getX() );
        
        for ( Num x = new Num( 0 ) ; x.less( getX() ) ; x.increment() ){
            
            for ( Num y = new Num( 0 ) ; y.less( getY() ) ; y.increment() ){
                
               transposistion.set( y , x , get( x , y ) );
                
            }
            
        }  
        
        return transposistion;
        
    }
    
    public Num trace(){
        
        if ( getX().equals( getY() ) ){ //Has to be square
        
            Num sum = new Num( 0 );
            
            for ( Num i = new Num( 0 ) ; i.less( getX() ) ; i.increment() ){
                
                sum = sum.add( get( i , i ) );
                
            }
            
            return sum;
            
        }
        
        return null;
        
    }
    
    public Matrix multily( Matrix matrix ){
        
        if ( getY().equals( matrix.getX() ) ){ //Colums in matrix 'A' must equal rows in matrix 'B'
            
            Matrix formed = new Matrix( getX() , matrix.getY() );
            
            formed.clear( 0 );
            
            for ( Num i = new Num( 0 ) ; i.less( getX() ) ; i.increment() ) { 
      
                for ( Num j = new Num( 0 ) ; j.less( matrix.getY() ) ; j.increment()) { 
                    
                    for ( Num k = new Num( 0 ) ; k.less( getY() ) ; k.increment()) { 
                    
                        Num multipliedValue = ( ( Num ) get( i , k ) ).multiply( matrix.get( k , j ) );
                        
                        formed.set( i , j , ( new Num( formed.get( i , j ) ) ).add( multipliedValue ) );
                    
                    }
            
                }
                
            }
 
            return formed;
            
            
        }
        
        return null;
        
    }
    
}
