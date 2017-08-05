package caprica.system;

public interface ConstructedRoutine {
    
    public String run();
    
    public void construct( Object[] arguments );
    public void setProgramMap( ProgramMap map );
    
}
