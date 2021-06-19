package graphs;

public class NodeNotExistingException extends Exception{
    public NodeNotExistingException(String label) {
        super("node " + label + " does not exist");
    }
}
