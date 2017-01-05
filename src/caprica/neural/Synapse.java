package caprica.neural;

public class Synapse {
    
    double weight = 0;
    Neuron neuron = null;
    
    public void setWeight( double weight ){
        
        this.weight = weight;
        
    }
    
    public double getWeight(){
        
        return weight;
        
    }
    
    public void setOutput( Neuron neuron ){
        
        this.neuron = neuron;
        
    }
    
    public void transmit( double transmitValue ){
        
        if ( this.neuron != null ){
            
            this.neuron.input( transmitValue * weight );
            
        }
        
    }
    
}
