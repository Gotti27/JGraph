package graphs;

import javafx.util.Pair;

import java.util.List;
import java.util.Set;

public interface Graph {
    void add_node(String label);

    void add_node(Node node);

    void add_nodes(Iterable<String> labels);

    void addOrientedEdge(String first_label, String second_label, double weight);

    void addEdge(String first_label, String second_label, double weight);

    List<Edge> minimumSpanningTree(String root_label);

    Pair<List<Edge>, Double> minimumPath(String source_label, String destination_label) throws NegativeCycleException;
}
