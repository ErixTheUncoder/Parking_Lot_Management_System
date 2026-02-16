public abstract class Gate{
      /**
     * Unique identifier for this gate
     */
    protected final int gateID;
    
    /**
     * Reference to the building structure
     */
    protected final Building building;

    public Gate(int gID , Building build){
        this.building=build;
        this.gateID=gID;
    }
}