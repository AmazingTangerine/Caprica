package caprica.neural;

import caprica.datatypes.Num;
import caprica.system.Output;
import java.util.Random;

public class Neuron {
    
    private double sum = 0;
    private Synapse[] outputConnections = null;
    private double bias;
    
    public Neuron(){
        
        bias = new Random().nextDouble();
        
    }
    
    public void reset(){
        
        sum = 0;
        
    }
 
    public void mutate(){
        
        Random random = new Random();
        
        double randomBiasDifference = random.nextDouble() / 5; //Max devation of 0.2
        
        if ( random.nextBoolean() ){
                    
            randomBiasDifference = randomBiasDifference * -1;
                    
        }
        
        bias += randomBiasDifference;
        
        if ( bias > 1 ){ bias = 1; }
        if ( bias < 0 ){ bias = 0; } //Essiently a dead neuron
        
        if ( outputConnections != null ){
            
            for ( Synapse synapse : outputConnections ){
                
                double currentWeight = synapse.getWeight();
                
                double randomWeightDifference = random.nextDouble() / 5; //Max devation of 0.2
                
                if ( new Random().nextBoolean() ){
                    
                    randomWeightDifference = randomWeightDifference * -1;
                    
                }
                
                double newWeight = currentWeight + randomWeightDifference;
                
                if ( newWeight > 1 ){
                    
                    newWeight = 1;
                    
                }
                else if ( newWeight < 0 ){
                    
                    newWeight = 0;
                    
                }
                
                synapse.setWeight( newWeight );
                
            }
            
        }
        
    }
    
    public double getData(){
        
        double data =  1 / ( 1 + Math.pow( Math.E , -( sum * bias ) ) );
        
        reset();
        
        return data;
        
    }
    
    public void input( double input ){
        
        sum += input;
        
    }
    
    public void output(){
        
        double output = 1 / ( 1 + Math.pow( Math.E , -( sum * bias ) ) ); //Sigmoud 
        
        if ( outputConnections != null ){
            
            for ( Synapse synapse : outputConnections ){
                
                synapse.transmit( output );
                
            }
            
        }
       
        reset();
        
    }
    
    public void connect( Neuron[] nextRow ){
        
        Random generator = new Random();
        
        outputConnections = new Synapse[ nextRow.length ];
        
        for ( int i = 0 ; i < nextRow.length ; i++ ){
            
            double weight = generator.nextDouble();
            
            Synapse synapse = new Synapse();
            synapse.setWeight( weight );
            synapse.setOutput( nextRow[ i ] );
            
            outputConnections[ i ] = synapse;
            
        }
        
    }
    
}
