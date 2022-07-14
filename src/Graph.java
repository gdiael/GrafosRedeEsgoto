import java.util.ArrayList;

public class Graph {

    private ArrayList<Vertex> vertices;

    private ArrayList<Edge> edges;

    public Graph() {
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
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
                if(element.startsWith(typeVertexStr)) type = typeVertexStr;
                if(element.startsWith(typeEdgeStr)) type = typeEdgeStr;
                continue;
            }
            if(element.startsWith("#")) {
                titleLine = element;
                continue;
            }
            if(!titleLine.isEmpty()) {
                if(type == typeVertexStr) {
                    Vertex vert = new Vertex();
                    vert.populate(titleLine, element);
                    if(!vert.id.isEmpty()) graph.vertices.add(vert);
                }
                if(type == typeEdgeStr) {
                    Edge edge = new Edge();
                    edge.populate(titleLine, element);
                    if(!edge.id.isEmpty()) graph.edges.add(edge);
                }
            }
        }
        return graph;
    }

    public String toString() {
        return String.format("vert: %d, edge: %d", vertices.size(), edges.size());
    }
}
