import java.util.ArrayList;

public class Graph {

    private ArrayList<Vertex> vertices;

    private ArrayList<Edge> edges;

    public Graph() {
        // inicia a lista de vertices
        vertices = new ArrayList<Vertex>();
        // inicia a lista de arestas
        edges = new ArrayList<Edge>();
    }

    public Boolean addVertex(Vertex newVert) {
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
            if(vert.id == id){
                return vert;
            }
        }
        return null;
    }

    public Boolean addEdge(Edge edge){
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
                    if(!vert.id.isEmpty()) graph.addVertex(vert);
                }
                if(type == typeEdgeStr) {
                    Edge edge = new Edge();
                    edge.populate(titleLine, element);
                    if(!edge.id.isEmpty()) graph.addEdge(edge);
                }
            }
        }
        return graph;
    }

    public String toString() {
        return String.format("vert: %d, edge: %d", vertices.size(), edges.size());
    }
}
