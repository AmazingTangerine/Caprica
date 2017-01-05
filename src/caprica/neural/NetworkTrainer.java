package caprica.neural;

import caprica.system.Output;
import java.util.HashMap;

public class NetworkTrainer {
    
    private double[][] inputs;
    private double[][] outputs;
    
    private Network[] trainedNetworks;
    
    public NetworkTrainer( double[][] inputs , double[][] outputs ){
        
        this.inputs = inputs;
        this.outputs = outputs;
        
    }
    
    public void train( int generationSize , int layerSize , int runs ){
        
        //Output.print( "Beggining training" );
        
        //Output.print( "Generating intial set" );
        
        Network[] networks = new Network[ generationSize ];
        
        for ( int i = 0 ; i < generationSize ; i++ ){
            
            Network network = new Network( inputs[ 0 ].length , outputs[ 0 ].length , layerSize );
            networks[ i ] = network;
            
        }
        
        //Output.print( "Generation complete" );
        
        for ( int x = 0 ; x < runs ; x++ ){
        
            //Output.print( "Run #" + ( x + 1 ) );
            
            //Output.print( "Beggning cull process" );

            networks = cull( networks );

            //Output.print( "Cull completed" );
            //Output.print( "Replicating generation" );
        
            Network[] copiedNetworks = new Network[ networks.length * 2 ];
        
            for ( int i = 0 ; i < networks.length ; i++ ){
            
                int index = ( 2 * i );
            
                copiedNetworks[ index ] = networks[ i ];
                copiedNetworks[ index + 1 ] = networks[ i ];

            }
        
            networks = copiedNetworks;
        
            //Output.print( "Replication complete" );
            //Output.print( "Mutating generation" );
        
            for ( int i = 0 ; i < networks.length / 2 ; i++ ){
            
                int index = ( 2 * i );
                
                networks[ index ].mutate();
            
            }
        
            //Output.print( "Mutation complete" );
        
        }
        
        //Output.print( "Beggning cull process" );

        trainedNetworks = cull( networks );

        //Output.print( "Cull completed" );
        //Output.print( "Training complete");
        
    }
    
    public void train( int runs ){
        
        double highestAverage = 0;
        
        for ( int x = 100 ; x < 102 ; x = x + 2 ){
            
            for ( int y = 1 ; y < 25 ; y++ ){
                
                NetworkTrainer trainer = new NetworkTrainer( inputs , outputs );
                trainer.train( x , y , runs );
                
                double averageFitness = 0;
                
                for ( Network network : trainer.getTrainedNetworks() ){
                    
                    averageFitness += network.averageFitness( inputs , outputs );
                    
                }
                
                averageFitness = averageFitness / trainer.getTrainedNetworks().length;
                
                if ( averageFitness > highestAverage ){
                    
                    highestAverage = averageFitness;
                    
                    trainedNetworks = trainer.getTrainedNetworks();
                    
                }
                
            }
            
        }
        
    }
    
    public Network[] getTrainedNetworks(){
        
        return trainedNetworks;
        
    }
    
    private Network[] cull( Network[] networks ){
        
        Network[] culled = new Network[ networks.length / 2 ];
        
        HashMap< Network , Double > fitnessMap = new HashMap<>();
        
        for ( Network network : networks ){
            
            fitnessMap.put( network , network.averageFitness( inputs , outputs ) );
            
        }
        
        Network[] sorted = sort( fitnessMap );
        
        for ( int i = 0 ; i < culled.length ; i++ ){
            
            culled[ i ] = sorted[ i ];
            
        }
        
        return culled;
        
    }
    
    private Network[] sort( HashMap< Network , Double > fitnessMap ){
        
        Network[] sorted = new Network[ fitnessMap.size() ];
   
        int size = fitnessMap.size();
        
        int count = 0;
        
        for ( int i = 0 ; i < size ; i++ ){
        
            double highest = 0;
        
            for ( Network network : fitnessMap.keySet() ){
          
                if ( fitnessMap.get( network ) > highest ){
                
                    highest = fitnessMap.get( network );
                
                }
            
            }

            for ( Network network : fitnessMap.keySet() ){
            
                if ( fitnessMap.get( network ) == highest ){
   
                    sorted[ count ] = network;
                    count++;
                    
                    fitnessMap.remove( network );
                
                    break;
                
                }
            
            }
        
        }
        
        return sorted;
        
    }
      
}
