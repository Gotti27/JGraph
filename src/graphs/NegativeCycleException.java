package graphs;

public class NegativeCycleException extends Exception{
    public NegativeCycleException() {
        super("There is a negative cycle reachable from the source node");
    }
}
