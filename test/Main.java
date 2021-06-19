import graphs.Edge;
import graphs.MatrixGraph;
import graphs.NegativeCycleException;
import javafx.util.Pair;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MatrixGraph myGgraph = new MatrixGraph();
        MatrixGraph spanning_tree = new MatrixGraph();

        myGgraph.add_node("s");
        myGgraph.add_node("a");
        myGgraph.add_node("b");
        myGgraph.add_node("c");
        myGgraph.add_node("d");
        myGgraph.addOrientedEdge("s", "a", 5);
        myGgraph.addOrientedEdge("s", "c", 1);
        myGgraph.addOrientedEdge("a", "b", 4);
        myGgraph.addOrientedEdge("a", "c", 3);
        myGgraph.addOrientedEdge("c", "a", 2);
        myGgraph.addOrientedEdge("b", "d", 1);
        myGgraph.addOrientedEdge("c", "d", 15);
        myGgraph.addOrientedEdge("d", "c", 9);
        try {
            Pair<List<Edge>, Double> path = myGgraph.minimumPath("s", "d");

            System.out.println(path.getValue());
            for (Edge e:path.getKey()){
                System.out.println(e);

            }
        } catch (NegativeCycleException e) {
            e.printStackTrace();
        }
    }
}
