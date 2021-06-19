package graphs;

import javafx.util.Pair;

import java.util.*;

public class MatrixGraph implements Graph{
    private final ArrayList<Node> nodes;
    private final Vector<Vector<Double>> adj_matrix;
    private int numberOfNodes;
    private boolean notNegativeEdges;

    //constructors
    public MatrixGraph() {
        this.adj_matrix = new Vector<>();
        this.nodes = new ArrayList<>();
        this.numberOfNodes = 0;
        this.notNegativeEdges = true;
    }

    public MatrixGraph(Iterable<Node> in_nodes) {
        this.adj_matrix = new Vector<>();
        this.nodes = new ArrayList<>();
        this.numberOfNodes = 0;
        for (Node adding : in_nodes) {
            add_node(adding);
        }
        this.notNegativeEdges = true;
    }

    public MatrixGraph(Iterable<Node> in_nodes, Iterable<Edge> edges){
        this.adj_matrix = new Vector<>();
        this.nodes = new ArrayList<>();
        this.numberOfNodes = 0;
        this.notNegativeEdges = true;
        for (Node adding : in_nodes) {
            add_node(adding);
        }
        for (Edge edge:edges){
            //possible bugs
            addOrientedEdge(edge.getFirst_node().getLabel(),edge.getSecond_node().getLabel(),edge.getWeight());
            if(edge.getWeight() < 0){
                this.notNegativeEdges = false;
            }
        }
    }

    //general methods
    @Override
    public void add_node(String label) {
        Node new_node = new Node(label, numberOfNodes++);
        if (this.nodes.contains(new_node)){
            return;
        }
        Vector<Double> new_row = new Vector<>();
        for(int i=0;i<this.numberOfNodes; ++i){
            new_row.add(0.0);
        }
        nodes.add(new_node);
        adj_matrix.add(new_row);
        for (int i=0; i<adj_matrix.size()-1; ++i){
            adj_matrix.get(i).add(0.0);
        }
    }

    @Override
    public void add_node(Node node) {
        for (Node u:this.nodes){
            if (u.equals(node)){
                return;
            }
        }
        Vector<Double> new_row = new Vector<>();
        this.numberOfNodes++;
        for(int i=0;i<this.numberOfNodes; ++i){
            new_row.add(0.0);
        }
        nodes.add(node);
        adj_matrix.add(new_row);
        for (int i=0; i<adj_matrix.size()-1; ++i){
            adj_matrix.get(i).add(0.0);
        }
    }

    @Override
    public void add_nodes(Iterable<String> labels) {
        for (String str:labels){
            this.add_node(str);
        }
    }

    @Override
    public void addOrientedEdge(String first_label, String second_label, double weight) {
        try {
            int first_index = getNodeIndex(first_label);
            int second_index = getNodeIndex(second_label);
            this.adj_matrix.get(first_index).set(second_index, weight);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEdge(String first_label, String second_label, double weight) {
        try {
            int first_index = getNodeIndex(first_label);
            int second_index = getNodeIndex(second_label);
            this.adj_matrix.get(first_index).set(second_index, weight);
            this.adj_matrix.get(second_index).set(first_index, weight);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Node getNode(String label){
        try {
            return this.nodes.get(getNodeIndex(label));
        } catch (NodeNotExistingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Node getNode(int index){
        return this.nodes.get(index);
    }

    public int getDeg(Node u){
        int index = u.getIndex();
        int deg = 0;
        for (double edge:this.adj_matrix.get(index)){
            if (edge != 0){
                deg++;
            }
        }
        return deg;
    }

    private int getNodeIndex(String to_search) throws NodeNotExistingException{
        for(Node u:this.nodes){
            if(u.getLabel().equals(to_search)){
                return nodes.indexOf(u);
            }
        }
        throw new NodeNotExistingException(to_search);
    }

    public Edge getEdge(Node u, Node v){
        double edge = this.adj_matrix.get(u.getIndex()).get(v.getIndex());
        return new Edge(u,v,edge);
    }

    public double getEdgeWeight(Node u, Node v) {
        try {
            double distance = this.adj_matrix.get(u.getIndex()).get(v.getIndex());
            if (distance == 0) {
                return Integer.MAX_VALUE;
            } else {
                return distance;
            }
        } catch (NullPointerException e) {
            return Integer.MAX_VALUE;
        }
    }

    public double getEdgeWeight(String first_label, String second_label){
        try {
            int a = getNodeIndex(first_label);
            int b = getNodeIndex(second_label);
            return this.adj_matrix.get(a).get(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Vector<Node> adj(Node u){
        Vector<Node> adj_nodes = new Vector<>();
        try {
            int index = getNodeIndex(u.getLabel());
            for (int internal=0; internal< this.adj_matrix.get(index).size(); ++internal){
                if(adj_matrix.get(index).get(internal) != 0.0){
                    adj_nodes.add(nodes.get(internal));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adj_nodes;
    }

    //algorithms

    private List<Edge> mstPrim(Node root){
        //Node root = getNode(label_root);
        PriorityQueue<Node> Q = new PriorityQueue<>(1, Node::compareKey);
        List<Edge> A = new ArrayList<>();

        for (Node u:this.nodes){
            u.setKey(Integer.MAX_VALUE);
            u.setPredecessor(null);
            Q.add(u);
        }
        root.setKey(0);

        while(!Q.isEmpty()){
            Node u = Q.poll();
            for (Node v:adj(u)){
                if (Q.contains(v) && getEdgeWeight(u,v) != 0 && v.getKey() != 0 && getEdgeWeight(u,v) < v.getKey()){
                    Q.remove(v);
                    v.setPredecessor(u);
                    v.setKey(getEdgeWeight(u,v));
                    Q.add(v);
                }
            }
        }
        for (Node u:nodes){
            if (!u.equals(root)){
                Edge new_edge = new Edge(u,u.getPredecessor(), getEdgeWeight(u,u.getPredecessor()));
                A.add(new_edge);
            }
        }
        return A;
    }

    //dijkstra

    private void init_ss(Node u){
        for (Node v:this.nodes){
            v.setDistance(Double.MAX_VALUE);
            v.setPredecessor(null);
        }
        u.setDistance(0);
    }

    private void relax(Node u, Node v){
        double weight = getEdgeWeight(u,v);
        if (weight != 0 && v.getDistance() > u.getDistance() + weight){
            v.setDistance(u.getDistance() + weight);
            v.setPredecessor(u);
        }
    }

    public void Dijkstra(Node root){
        init_ss(root);
        PriorityQueue<Node> Q = new PriorityQueue<>(Node::compareDistance);
        Q.addAll(this.nodes);
        while (!Q.isEmpty()){
            Node u = Q.poll();
            for (Node v:adj(u)){
                if (Q.contains(v)){
                    Q.remove(v);
                    relax(u,v);
                    Q.add(v);
                }
            }
        }
    }

    //Bellman-Ford da ricontrollare

    private void bellmanFord(Node root) throws NegativeCycleException{
        init_ss(root);
        for (int iteration=1; iteration<this.nodes.size(); ++iteration){
            for (int i=0; i<this.adj_matrix.size(); ++i){
                for (int j=0; j<this.adj_matrix.get(i).size(); ++j){
                    relax(getNode(i),getNode(j));
                }
            }
        }
        for (int i=0; i<this.adj_matrix.size(); ++i){
            for (int j=0; j<this.adj_matrix.get(i).size(); ++j){
                if (getNode(i).getDistance() > getNode(j).getDistance() + getEdgeWeight(getNode(i), getNode(j))){ // da ricontrollare e testare
                    throw new NegativeCycleException();
                }
            }
        }
    }


    @Override
    public List<Edge> minimumSpanningTree(String root_label) {
        return mstPrim(getNode(root_label));
    }

    @Override
    public Pair<List<Edge>, Double> minimumPath(String source_label, String destination_label) throws NegativeCycleException {
        if (this.notNegativeEdges) {
            Dijkstra(getNode(source_label));
        }
        else {
            bellmanFord(getNode(source_label));
        }
        List<Edge> path = new ArrayList<>();
        Node attuale = getNode(destination_label);
        Node predecessor = attuale.getPredecessor();
        while (predecessor != null) { //da ricontrollare
            Edge nuovo = new Edge(predecessor, attuale, getEdgeWeight(predecessor, attuale));
            path.add(nuovo);
            attuale = predecessor;
            predecessor = attuale.getPredecessor();
        }
        Collections.reverse(path);
        return new Pair<>(path, getNode(destination_label).getDistance());
    }

    @Override
    public String toString(){
        StringBuilder graphString = new StringBuilder();
        graphString.append("-------Start Graph Print-------\n");
        graphString.append("Number of nodes = ").append(this.numberOfNodes).append("\n");
        for (Node u:this.nodes){
            graphString.append(u.toString()).append("\n");
        }
        graphString.append("-------Matrix-------\n");
        for (Vector<Double> adjMatrix : adj_matrix) {
            for (Double matrix : adjMatrix) {
                graphString.append(matrix).append("|");
            }
            graphString.append("\n");
        }
        graphString.append("-------End Graph Print-------\n");
        return graphString.toString();
    }
}
