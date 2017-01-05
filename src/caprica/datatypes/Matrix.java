package caprica.datatypes;

import caprica.system.Output;
import java.util.ArrayList;
import java.util.List;

public class Matrix {

    Num[][] data;

    public Matrix( int sizeX , int sizeY ){
        
        data = new Num[ sizeX ][ sizeY ];
        
        clear( null );
        
    }
    
    public Matrix( Vector... vectors ){ //Hey Victor I heard your dad died
        
        data = new Num[ vectors[ 0 ].data.size() ][ vectors.length ];
        
        int counter = 0;
        
        for ( Vector vector : vectors ){
            
            for ( int x = 0 ; x < vector.data.size() ; x++ ){
                
                data[ x ][ counter ] = vector.data.get( x );
                
            }
     
            counter++;
            
        }
        
    }
    
    public Matrix( Object[][] dataInput ){
        
        data = new Num[ dataInput.length ][ dataInput[ 0 ].length ];
        
        for ( int x = 0 ; x < dataInput.length ; x++ ){
            
            for ( int y = 0 ; y < dataInput[ 0 ].length ; y++ ){
                
                data[ x ][ y ] = new Num( dataInput[ x ][ y ] );
                
            }
            
        }
        
    }
    
    public Vector solve( Vector variables , Vector solutionVector ){
        
        int unknownCount = 0;

        Matrix matrix = copy();
        
        for ( int x = 0 ; x < variables.data.size() ; x++ ){

            if ( variables.get( x ).isEmpty() ){

                unknownCount++;
                
            }
            
        }

        if ( unknownCount > matrix.getY() ){ //Cant solve matrix
            
            return null;
            
        }
        
        if ( matrix.getX() != matrix.getY() ){ //Gausian elimination with pivot

            for ( int x = 0 ; x < matrix.getY() ; x++ ){ //Scan matrix from left to right
                
                int largestRow = x;
                Num largestValue = new Num( matrix.get( x , x ).abs() ); //Upper most value
                
                for ( int y = 1 + x; y < matrix.getY() ; y++ ){ //Finding pivot
                    
                    Num absValue = new Num( matrix.get( x , y ).abs() );
                    
                    if ( largestValue.less( absValue ) ){
                        
                        largestRow = y;
                        largestValue = absValue;
                        
                    }
                    
                }
                
                if ( largestRow != x ){
                    
                    matrix = matrix.swapRow( x , largestRow );
                    
                    Num previousSolution = solutionVector.data.get( largestRow );
                    
                    solutionVector.data.set( largestRow , solutionVector.data.get( x ) );
                    solutionVector.data.set( x , previousSolution );
                    
                }
                
                for ( int y = 1 + x ; y < matrix.getY() ; y++ ){ //Elimination
                
                    Num eliminationValue = matrix.get( x , y ).div( largestValue );
                    
                    matrix.set( x , y , new Num( 0 ) );
                    
                    for ( int z = 1 + x ; z < matrix.getX() ; z++ ){

                        Num currentValue = matrix.get( z , y );
                        Num newValue = currentValue.min( matrix.get( z , x ).multiply( eliminationValue ) );

                        matrix.set( z , y , newValue );
                    
                    }
                    
                    solutionVector.data.set( y , solutionVector.data.get( y ).min( solutionVector.data.get( x ).multiply( eliminationValue ) ) );
                    
                }

            }
            
            //Back substitution

            int count = 0;

            for ( int y = matrix.getY() - 1 ; y > -1 ; y-- ){

                Num minusSum = new Num( 0 );
                
                int startSum =  y + 1;
                int endSum = variables.data.size() - 1 - startSum;
                
                for ( int i = variables.data.size() - 1 ; i > -1 ; i-- ){
                    
                    if ( variables.data.get( i ).isEmpty() ){
                        
                        endSum = i - 1;
                        break;
                        
                    }
                    
                }
                    
                int lookingVariable = endSum + 1;
       
                int startAfterSum = lookingVariable + 1;
                int endAfterSum = variables.data.size();

                for ( int x = startSum - 1 ; x < endSum + 1 ; x++ ){

                    minusSum = minusSum.add( matrix.get( x , y ).multiply( variables.data.get( x ) ) );
        
                }
                
                for ( int x = startAfterSum ; x < endAfterSum ; x++ ){
 
                    minusSum = minusSum.add( matrix.get( x , y ).multiply( variables.data.get( x ) ) );
                    
                }

                Num variable = solutionVector.data.get( y ).min( minusSum ).div( matrix.get( lookingVariable , y ) );
                variables.data.set( lookingVariable , variable );
       
            }

        }
        else { //Cramers rule
        
            Num orignalDeterminate = matrix.determinate();

            int count = 0;
            
            for ( int x = 0 ; x < variables.data.size() ; x++ ){
                
                Matrix subMatrix = matrix.copy();

                if ( variables.data.get( x ).isEmpty() ){
                    
                    for ( int y = 0 ; y < matrix.getY() ; y++ ){

                        subMatrix.set( x - count , y , solutionVector.data.get( y ) );
                    
                    }
 
                    variables.data.set( x , subMatrix.determinate().div( orignalDeterminate ) );

                }
                else {

                    count = count + 1;
                    
                }
   
            }
            
        }
            
        return variables;
        
    }
    
    public Matrix swapRow( int firstRow , int secondRow ){
        
        Matrix swappedMatrix = copy();
        List< Num > oldRow = new ArrayList<>();
        
        for ( int x = 0 ; x < swappedMatrix.getX() ; x++ ){
            
            oldRow.add( swappedMatrix.get( x , firstRow ) );
            
            swappedMatrix.set( x , firstRow  , swappedMatrix.get( x , secondRow ) );
            
        }
        
        for ( int x = 0 ; x < oldRow.size() ; x++ ){
            
            swappedMatrix.set( x , secondRow , oldRow.get( x ) );
            
        }
        
        return swappedMatrix;
        
    }
    
    public Matrix preDeleteCollum( int collum ){
        
        Matrix matrix = copy();
        
        matrix.set( collum , 0 , new Num() );
        
        return matrix;
        
    }
    
    public Matrix deleteCollum(){
        
        Matrix matrix = copy();

        List< Vector > okVectors = new ArrayList<>();
        
        for ( int x = 0 ; x < matrix.getX() ; x++ ){

            if ( !matrix.get( x , 0 ).isEmpty() ){
        
                Vector vector = new Vector();
                
                for ( int y = 0 ; y < matrix.getY() ; y++ ){
                    
                    vector.data.add( matrix.get( x , y ) );
                    
                }
                
                okVectors.add( vector );
                
            }

        }
        
        Matrix cutMatrix = new Matrix( okVectors.size() , matrix.getY() );
        
        for ( int x = 0 ; x < okVectors.size() ; x++ ){
            
            for ( int y = 0 ; y < matrix.getY() ; y++ ){
                
                cutMatrix.set( x , y , okVectors.get( x ).data.get( y ) );
                
            }
            
        }
        
        return cutMatrix;
        
    }
    
    /**
     * Gets an object in the matrix
     * @param x The width coordinate in the matrix
     * @param y The height coordinate in the matrix
     * @return The object in the matrix
     */
    public Num get( int x , int y ){
        
        return data[ x ][ y ];
        
    }
    
    /**
     * Sets a value in the matrix
     * @param x The width cursor to be set
     * @param y The height cursor to be set
     * @param datum The value to be set in the matrix
     */
    public void set( int x , int y , Num datum ){
        
        data[ x ][ y ] = datum;
        
    }
    
    /**
     * Gets the width of the matrix
     * @return The width of the matrix
     */
    public int getX(){
        
        return data.length;
        
    }
    
    /**
     * Gets the height of the matrix
     * @return The height of the matrix
     */
    public int getY(){
        
        return data[ 0 ].length;
        
    }
    
    /**
     * Creates a copy of the matrix
     * @return Copied matrix
     */
    public Matrix copy(){
        
        Matrix photoCopy = new Matrix( getX() , getY() );
        
        for ( int x = 0 ; x < getX() ; x++ ){
            
            for ( int y = 0 ; y < getY() ; y++ ){
                
                photoCopy.set( x , y , get( x , y ) );
                
            }
            
        }
        
        return photoCopy;
        
    }
    
    public void clear( Num clearer ){
        
        for ( int x = 0 ; x < getX() ; x++ ){
            
            for ( int y = 0 ; y < getY() ; y++ ){
                
                set( x , y , clearer );
                
            }
            
        }
        
    }
    
    public void pushUp(){
        
        Matrix oldMatrix = copy();

        for ( int x = 0 ; x < getX() ; x++ ){
            
            for ( int y = 0 ; y < getY() - 1 ; y++ ){
                
                set( x , y , oldMatrix.get( x , y + 1 ) );
                
            }
            
        }
        
    }
    
    public Matrix add( Matrix inputMatrix ){
        
        if ( getX() == inputMatrix.getX() && getY() == inputMatrix.getY() ){ //Matricies must be same size
            
            Matrix formed = new Matrix( getX() , getY() );
            
            for ( int x = 0 ; x < getX() ; x++ ){
            
                for ( int y = 0 ; y < getY() ; y++ ){
                
                    formed.set( x , y , get( x , y ).add( inputMatrix.get( x , y ) ) );
                
                }
            
            }   
            
            return formed;
            
        }
        
        return null;
        
    }
    
    public Matrix scale( Object scalingValue ){
        
        Matrix scaled = new Matrix( getX() , getY() );
        
        for ( int x = 0 ; x < getX() ; x++ ){
            
            for ( int y = 0 ; y < getY() ; y++ ){
                
                scaled.set( x , y , get( x , y ).multiply( scalingValue ) );
               
            }
            
        }  
        
        return scaled;
        
    }
    
    public Matrix transpose(){
        
        Matrix transposistion = new Matrix( getY() , getX() );
        
        for ( int x = 0 ; x < getX() ; x++ ){
            
            for ( int y = 0 ; y < getY() ; y++ ){
                
               transposistion.set( y , x , get( x , y ) );
                
            }
            
        }  
        
        return transposistion;
        
    }
    
    public Num trace(){
        
        if ( getX() == getY() ){ //Has to be square
        
            Num sum = new Num( 0 );
            
            for ( int i = 0 ; i < getX() ; i++ ){
                
                sum = sum.add( get( i , i ) );
                
            }
            
            return sum;
            
        }
        
        return null;
        
    }
    
    public Matrix multily( Matrix matrix ){
        
        if ( getY() == matrix.getX() ){ //Colums in matrix 'A' must equal rows in matrix 'B'
            
            Matrix formed = new Matrix( getX() , matrix.getY() );
            
            formed.clear( new Num( 0 ) );
            
            for ( int i = 0 ; i < getX() ; i++ ) { 
      
                for ( int j = 0 ; j < matrix.getY() ; j++ ) { 
                    
                    for ( int k = 0 ; k < getY() ; k++ ) { 
                    
                        Num multipliedValue = get( i , k ).multiply( matrix.get( k , j ) );
                        
                        formed.set( i , j , formed.get( i , j ).add( multipliedValue ) );
                    
                    }
            
                }
                
            }
 
            return formed;
            
            
        }
        
        return null;
        
    }
    
    public Vector size(){
        
        return new Vector( getX() , getY() );
        
    }
    
    public Num determinate(){
        
        if ( getX() == 2 ){ //Solve 2x2
            
            return get( 0 , 0 ).multiply( get( 1 , 1 ) ).min( get( 0 , 1 ).multiply( get( 1 , 0 ) ) );
            
        }

        else { //Major minor method
            
            Num sum = new Num( 0 );
            
            int newMatrixSize = getX() - 1;
            
            for ( int y = 0 ; y < getY() ; y++ ){
                
                Vector[] vectors = new Vector[ newMatrixSize ];

                int count = 0;
                
                for ( int firstScan = 0 ; firstScan < y ; firstScan++ ){
                    
                    Vector vector = new Vector();
                    
                    for ( int x = 1 ; x < getX() ; x++ ){
                        
                        vector.data.add( new Num( get( x , firstScan ) ) );
                        
                    }
                    
                    vectors[ count ] = vector;
                    
                    count++;
                    
                }
   
                for ( int secondScan = y + 1 ; secondScan < getY() ; secondScan++ ){

                    Vector vector = new Vector();
                    
                    for ( int x = 1 ; x < getX() ; x++ ){
                        
                        vector.data.add( new Num( get( x , secondScan ) ) );
                        
                    }
                    
                    vectors[ count ] = vector;
                    
                    count++;
   
                }

                Matrix subMatrix = new Matrix( vectors );

                Num subDeterminate = subMatrix.determinate();
                
                Num minor = subDeterminate.multiply( get( 0 , y ).multiply( new Num( -1 ).pow( y ) ) );

                sum = sum.add( minor );
    
            }
            
            return sum;
            
        }
        
    }
    
}
