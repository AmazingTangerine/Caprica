package caprica.neural;

import caprica.system.Output;
import java.util.ArrayList;
import java.util.Random;

public class Network {
    
    private int inputRange;
    private int outputRange;
    private int layers;
    
    private Neuron[] inputNeurons;
    private Neuron[] outputNeurons;
    private ArrayList< Neuron[] > layerNeurons = new ArrayList<>();
    
    public Network( int inputRange , int outputRange , int layers ){

        Random generator = new Random();
        
        inputNeurons = new Neuron[ inputRange ];
        
        for ( int i = 0 ; i < inputRange ; i++ ){
            
            Neuron neuron = new Neuron();
            inputNeurons[ i ] = neuron;
            
        }
        
        Neuron[] previousSet = inputNeurons;
        
        for ( int i = 0 ; i < layers ; i++ ){
    
            //int n = generator.nextInt( 2 ) + inputRange; //Height of new layer
            int n = inputRange + 1;
            
            Neuron[] neuralSet = new Neuron[ n ];
            
            for ( int x = 0 ; x < n ; x++ ){
                
                Neuron neuron = new Neuron();
                neuralSet[ x ] = neuron;
                
            }
            
            for ( int x = 0 ; x < previousSet.length ; x++ ){
                
                previousSet[ x ].connect( neuralSet );
                
            }
            
            previousSet = neuralSet;
            
            layerNeurons.add( neuralSet );
            
        }
        
        outputNeurons = new Neuron[ outputRange ];
        
        for ( int i = 0 ; i < outputRange ; i++ ){
            
            Neuron neuron = new Neuron();
            outputNeurons[ i ] = neuron;
            
        }
        
        for ( int i = 0 ; i < previousSet.length ; i++ ){
            
            previousSet[ i ].connect( outputNeurons );
            
        }
        
    }
    
    public double[] process( double[] input ){

        if ( input.length == inputNeurons.length ){
            
            for ( int i = 0 ; i < inputNeurons.length ; i++ ){

                inputNeurons[ i ].input( input[ i ] );
                inputNeurons[ i ].output();
                
            }
         
            for ( Neuron[] layer : layerNeurons ){

                for ( Neuron neuron : layer ){
                    
                    neuron.output();
                    
                }
                
            }
            
            double[] data = new double[ outputNeurons.length ];

            for ( int i = 0 ; i < outputNeurons.length ; i++ ){
            
                data[ i ] = outputNeurons[ i ].getData();
                
            }
         
            return data;
            
        }
        
        return null;
        
    }
    
    public double fitness( double[] input , double[] output ){
        
        double totalDistance = 0;
        
        double[] testOutput = process( input );
        ;
        for ( int i = 0 ; i < testOutput.length ; i++ ){
            
            double distance = Math.abs( output[ i ] - testOutput[ i ] );
            
            totalDistance += distance;
            
        }
        
        double averageDistance = totalDistance / output.length;
        
        return 1 - averageDistance;
        
    }
    
    public double averageFitness( double[][] inputs , double[][] outputs ){
        
        double totalFitness = 0;
        
        for ( int i = 0 ; i < inputs.length ; i++ ){
            
            double fitness = fitness( inputs[ i ] , outputs[ i ] );
            
            totalFitness += fitness;
            
        }
    
        return totalFitness / inputs.length;
        
    }
    
    public void mutate(){
        
        for ( Neuron neuron : inputNeurons ){
            
            neuron.mutate();
            
        }
        
        for ( Neuron[] layer : layerNeurons ){

            for ( Neuron neuron : layer ){
                    
                neuron.mutate();
                    
            }
                
        }
        
    }
    
}
