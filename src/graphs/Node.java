package graphs;

public class Node {
    private final String label;
    private final int index;
    private Node predecessor;
    private double key;
    private double distance;

    public Node(String label, int index) {
        this.label = label;
        this.index = index;
        this.predecessor = null;
        this.key = Integer.MAX_VALUE;
        this.distance = Double.MAX_VALUE;
    }

    public String getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public double getKey() {
        return key;
    }

    public void setKey(double key) {
        this.key = key;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        return this.index;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node){
            return ((Node) obj).label.equals(this.label);
        }
        return false;
    }

    public static <T extends Node> int compareKey(T o1, T o2){
        return (int) (o1.getKey() - o2.getKey());
    }

    public static <T extends Node> int compareDistance(T o1, T o2){
        return (int) (o1.getDistance() - o2.getDistance());
    }

    @Override
    public String toString() {
        return "label='" + label + '\'' +
                ", index=" + index;
    }

}
