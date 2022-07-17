import java.util.ArrayList;
import java.util.List;

public class Vertex {

    // identificador unico de cada vertice
    public String id = "";

    // coordenada X do vertice
    public Double posX = 0.0;
    // coordenada Y do vertice
    public Double posY = 0.0;
    // coordenada Z do vertice, também chamada de elevação ou cota
    public Double elev = 0.0;
    // descrição do vertice
    public String desc = "";

    // lista de arestas que se conectam no vertice
    private ArrayList<Edge> edges;

    public boolean wasVisited = false;

    private static String[] doublePropName = {"posX", "posY", "elev"};

    public Vertex(){
        edges = new ArrayList<>();
    }

    public List<Edge> getAdjacentEdges(){
        return edges;
    }

    private static boolean isDblPropertyName(String prop) {
        for (String dblProp : doublePropName) {
            if(prop.equals(dblProp)) return true;
        }
        return false;
    }

    public boolean conect(Edge edge) {
        if(this.id.equals(edge.vertIdIni)){
            edges.add(edge);
            edge.vertexIni = this;
            return true;
        }
        if(this.id.equals(edge.vertIdFim)){
            edges.add(edge);
            edge.vertexFim = this;
            return true;
        }
        return false;
    }


    public void populate(String titleLine, String line) {
        if(titleLine.startsWith("##")) return;
        if(titleLine.startsWith("#")){
            String[] arrProp = titleLine.substring(1).split(";");
            String[] arrVal = line.split(";");
            if(arrProp.length == arrVal.length){
                for(Integer i = 0; i < arrProp.length; i++) {

                    String prop = arrProp[i];
                    String val = arrVal[i];
                    Double valDbl = (isDblPropertyName(prop) ? MyUtil.parseDbl(val) : 0.0);
                    
                    switch(prop){
                        case "id":
                            id = val;
                            break;
                        case "posX":
                            posX = valDbl;
                            break;
                        case "posY":
                            posY = valDbl;
                            break;
                        case "elev":
                            elev = valDbl;
                            break;
                        case "desc":
                            desc = val;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    // distância entre dois vertices
    public Double distance(Vertex vertTo){
        return Math.sqrt(Math.pow(vertTo.posX - this.posX, 2.0) + Math.pow(vertTo.posY - this.posY,2.0));
    }

    public String toString() {
        return String.format("%s;%.3f;%.3f;%.3f;%s;", id, posX, posY, elev, desc);
    }

}
