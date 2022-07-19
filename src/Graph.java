import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

    private ArrayList<Vertex> vertices;

    private ArrayList<Edge> edges;

    // profundidade mínima, para cada trecho de tubulação
    public static final Double PROF_MIN = 0.8;
    // custo da tubulação por metro
    public static final Double PIPE_PRICE = 120.0;
    // custo da escavação do solo para execução do tubo por m3 de material escavado
    public static final Double EXCAVATION_PRICE = 30.0;
    // largura mínima da vala da tubulação
    public static final Double EXCAVATION_WIDTH_MIN = 1.5;

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
        for (Vertex vert : this.vertices) {
            if(vert.id.equals(id)){
                return vert;
            }
        }
        return null;
    }

    public Edge getEdge(String id) {
        for (Edge edge : this.edges) {
            if(edge.id.equals(id)){
                return edge;
            }
        }
        return null;
    }

    public boolean addEdge(Edge edge) {
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

    private void clearVisited() {
        for (Vertex vert : vertices) {
            vert.wasVisited = false;
        }
        for (Edge edge : edges) {
            edge.wasVisited = false;
        }
    }

    public Double totalWeight() {
        Double weight = 0.0;
        for (Edge edge : edges) {
            weight += edge.baseWeight();
        }
        return weight;
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
                if(!oposit.wasVisited && !stackVertices.contains(oposit)){
                    stackVertices.push(oposit);
                }
            }
        }
    }

    private Vertex  findLowestVert() {
        Vertex vert = this.vertices.get(0);
        for(int i = 1; i < this.vertices.size(); i++) {
            Vertex otherVert = this.vertices.get(i);
            if(otherVert.elev < vert.elev) {
                vert = otherVert;
            }
        }
        return vert;
    }

    // cria um grafo de linha baseado no grafo atual, os pessos das novas arestas serão a soma dos pessos
    // das vertices (arestas no grafo original) que são conectadas por elas
    public Graph toLineGraph() {
        Graph lineGraph = new Graph();
        // dx será incrimentada para cada novo vertices, para prevenir que eles fiquem sobrepostos
        Double dx = 1.0;
        for (Edge edge : edges) {
            // para cada aresta existente inseriremos um novo vertice no grafo de linha,
            // ese vertíce casicamente terá ok id da aresta original
            Vertex vert = new Vertex(edge.id, dx, dx, dx, "edge");
            dx += 1.0;
            lineGraph.addVertex(vert);
        }

        for (Vertex vert : vertices) {
            List<Edge> adj = vert.getAdjacentEdges();
            // vamos interagir por cada lista de arestas adjacentes a cada vertice
            // fazendo os pares destas arestas para criar as novas arestas do grafo de linha
            // caso o vertice esteja conectado a apenas uma aresta, não entra no laço
            for(int i = 0; i < adj.size() - 1; i++){
                for(int j = i + 1; j < adj.size(); j++){
                    Edge edgeA = adj.get(i);
                    Edge edgeB = adj.get(j);
                    String newId = String.format("%s-%s", edgeA.id, edgeB.id);
                    Edge newEdge = new Edge(newId, edgeA.id, edgeB.id);
                    // o peso da nova aresta será a soma dos pesos das arestas antigas
                    newEdge.setWeight(edgeA.baseWeight() + edgeB.baseWeight());
                    lineGraph.addEdge(newEdge);
                }
            }
        }
        return lineGraph;
    }

    public List<Edge> findMinSpamTree() {
        // a propriedade visited será usada para verificar se o vertíce já foi incluido
        clearVisited();
        // uma lista de prioridade que organizará as aresta por peso, da menor para a maior
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(
            (Edge edgeA, Edge edgeB) -> Double.compare(edgeA.getWeight(), edgeB.getWeight()));
        // vamos pegar o primeiro vertice, marcar como visitado e inserir as arestas adjacentes na lista
        // de prioridade
        Vertex vert = vertices.get(0);
        ArrayList<Edge> minSpamTree = new ArrayList<>(edges.size());
        do {
            if(!vert.wasVisited) {
                vert.wasVisited = true;
                for (Edge edge : vert.getAdjacentEdges()) {
                    if(!edge.wasVisited) {
                        edge.wasVisited = true;
                        minHeap.add(edge);
                    }
                }
            }
            
            Edge minEdge = minHeap.poll();
            // como iremos inserir apenas arestas de vertices visitados, pelo menos uma das pontas
            // ainda não foi visitada
            vert = (minEdge.vertexIni.wasVisited ? minEdge.vertexFim : minEdge.vertexIni);
            if(!vert.wasVisited) {
                minSpamTree.add(minEdge);
            }
        } while(!minHeap.isEmpty());
        return minSpamTree;
    }

    public void updateGravityFlow(List<Edge> minSpamTree) {
        // será computada a direção do fluxo (flow) de cada aresta
        // -1.0 para inverter e 1.0 para manter
        // para isso iremos percorer a arvore em uma busca em largura a partir do vertice com
        // a menor elevação, pelos caminhos da minSpamTree do grafo de linha, onde o id dos vertices
        // de cada aresta são os ids das nossas arestas

        clearVisited();
        
        Vertex vert = findLowestVert();
        
        Deque<Vertex> stackVertices = new ArrayDeque<>();
        stackVertices.add(vert);
        while(!stackVertices.isEmpty()) {
            vert = stackVertices.pop();
            vert.wasVisited = true;
            System.out.println(vert);
            
            for (Edge edge : vert.getAdjacentEdges()) {
                if(!edge.wasVisited){
                    edge.wasVisited = true;
                    System.out.println(edge);
                }
                Vertex oposit = edge.getOpositVertex(vert);
                if(!oposit.wasVisited && !stackVertices.contains(oposit)){
                    stackVertices.add(oposit);
                }
            }
        }

        for (Edge edge : minSpamTree) {
            System.out.println(String.format("%s - peso %.3f", edge.id, edge.getWeight()));
        }
    }

    public void calculateEdgeDirection() {
        Double iniWeight = this.totalWeight();
        System.out.println(String.format("Peso Ini: %.3f", iniWeight));
        List<Edge> minSpamTree = this.toLineGraph().findMinSpamTree();
        updateGravityFlow(minSpamTree);
        Double endWeight = this.totalWeight();
        System.out.println(String.format("Peso Fim: %.3f", endWeight));
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

    public void printGraph() {
        for (Vertex vert : vertices) {
            System.out.println(vert);
            for (Edge edge : vert.getAdjacentEdges()) {
                System.out.println(edge);
            }
        }
    }

    public String toString() {
        return String.format("vert: %d, edge: %d", vertices.size(), edges.size());
    }
}
