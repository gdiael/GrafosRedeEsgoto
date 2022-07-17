import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Graph {

    private ArrayList<Vertex> vertices;

    private ArrayList<Edge> edges;

    public Graph() {
        // inicia a lista de vertices
        vertices = new ArrayList<>();
        // inicia a lista de arestas
        edges = new ArrayList<>();
    }

    public boolean addVertex(Vertex newVert) {
        if(newVert.id.isEmpty()) return false;
        for (Vertex vert : vertices) {
            // se a distância entre os vertices for menos que 1mm, consideramos sobrepostos
            if(newVert.distance(vert) < 0.001){
                // não adiciona
                return false;
            }
        }
        vertices.add(newVert);
        return true;
    }

    // retorna o vertice com identificador id, caso não exista retorna null
    public Vertex getVertex(String id) {
        for (Vertex vert : vertices) {
            if(vert.id.equals(id)){
                return vert;
            }
        }
        return null;
    }

    public boolean addEdge(Edge edge){
        if(edge.id.isEmpty()) return false;
        Integer counter = 0;
        for (Vertex vert : vertices) {
            if(vert.conect(edge)){
                counter++;
            }
        }
        if(counter == 2){
            this.edges.add(edge);
            return true;
        }
        return false;
    }

    private void clearVisited(){
        for (Vertex vert : vertices) {
            vert.wasVisited = false;
        }
        for (Edge edge : edges) {
            edge.wasVisited = false;
        }
    }

    public void depthSearch() {
        clearVisited();
        Deque<Vertex> stackVertices = new ArrayDeque<>();
        stackVertices.push(vertices.get(0));
        while(!stackVertices.isEmpty()) {
            Vertex vert = stackVertices.pop();
            vert.wasVisited = true;
            System.out.println(vert);
            for (Edge edge : vert.getAdjacentEdges()) {
                if(!edge.wasVisited){
                    edge.wasVisited = true;
                    System.out.println(edge);
                }
                Vertex oposit = edge.getOpositVertex(vert);
                if(!oposit.wasVisited){
                    stackVertices.push(oposit);
                }
            }
        }
    }

    public static Graph parseGraph(String text) {
        String[] textAr = text.split("\n");
        String typeVertexStr = "##VERTEX";
        String typeEdgeStr = "##EDGE";
        String type = "";
        String titleLine = "";
        Graph graph = new Graph();
        for (String element : textAr) {
            if(element.startsWith ("##")) {
                if(element.startsWith(typeVertexStr)) {
                    type = typeVertexStr;
                } else if(element.startsWith(typeEdgeStr)) {
                    type = typeEdgeStr;
                }
            } else if(element.startsWith("#")) {
                titleLine = element;
            } else if(!titleLine.isEmpty()) {
                if(type.equals(typeVertexStr)) {
                    Vertex vert = new Vertex();
                    vert.populate(titleLine, element);
                    graph.addVertex(vert);
                } else if(type.equals(typeEdgeStr)) {
                    Edge edge = new Edge();
                    edge.populate(titleLine, element);
                    graph.addEdge(edge);
                }
            }
        }
        return graph;
    }

    public String toString() {
        return String.format("vert: %d, edge: %d", vertices.size(), edges.size());
    }
}
