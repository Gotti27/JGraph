package graphs;

import javax.annotation.PostConstruct;

public class Edge {
    private final Node first_node;
    private final Node second_node;
    private double weight;

    public Edge(Node first_node, Node second_node, double weight) {
        this.first_node = first_node;
        this.second_node = second_node;
        this.weight = weight;
    }

    public Edge(Node first_node, Node second_node) {
        this.first_node = first_node;
        this.second_node = second_node;
        this.weight = 1;
    }

    public Node getFirst_node() {
        return first_node;
    }

    public Node getSecond_node() {
        return second_node;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString(){
        String result = "";
        if (this.first_node == null){
            result += "null";
        }
        else{
            result += this.first_node.getLabel();
        }
        result += "--(" + this.weight + ")--";
        if (this.second_node == null){
            result += "null";
        }
        else{
            result += this.second_node.getLabel();
        }
        return result;
    }
}
