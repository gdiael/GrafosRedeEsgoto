public class Edge {
    
    public String id = "";
    public String vertIdIni = "";
    public String vertIdFim = "";
    public Double profIni = 0.8; // profundidade em vertIni
    public Double profFim = 0.8; // profundidade em vertFim
    public Double dia = 150.0; // diâmetro em milimetros
    public Double vazao = 1.5; // vazão de cada trecho em L/s
    public Double flow = 0.0; // direção do fluxo (positivo = ini -> fim, negativo = fim -> ini, zero = não calculado)

    public Vertex vertexIni;
    public Vertex vertexFim;

    public boolean wasVisited = false;

    private Double weight = 0.0;

    private static String[] doublePropName = {"profIni", "profFim", "dia", "vazao", "flow"};

    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double val){
        weight = val;
    }

    public Edge(){}

    public Edge(String id, String vertIni, String vertFim){
        this.id = id;
        this.vertIdIni = vertIni;
        this.vertIdFim = vertFim;
    }

    // a elevação do tubo (altura do terreno - profundidade)
    // no caso de uma referência nula, vai usar como base a elevação padrão da classe (0.0)
    public Double getElevIni() {
        return MyUtil.coalesce(vertexIni, new Vertex()).elev - this.profIni;
    }

    public Double getElevFim() {
        return MyUtil.coalesce(vertexFim, new Vertex()).elev - this.profFim;
    }

    public Vertex getOpositVertex(Vertex vert) {
        if(vert == vertexIni) return vertexFim;
        if(vert == vertexFim) return vertexIni;
        return null;
    }

    private static boolean isDblPropertyName(String prop) {
        for (String dblProp : doublePropName) {
            if(prop.equals(dblProp)) return true;
        }
        return false;
    }

    public Double lenght() {
        return vertexIni.distance(vertexFim);
    }

    // Aqui o peso de cada aresta, como as arestas representam tubulação, quanto maior o comprimento
    // da tubulação, mas caro a execução daquele trecho. Além disso arestas com a elevação média maior,
    // também são mais caras, pois potêncialmente tem mais escavação necessária para atingir a profundidade
    // ideial do tubo. Logo o peso (ou curto) de cada aresta vai ser dado por C*ElevMed, onde C é o comprimento
    public Double baseWeight() {
        Double averageElev = (this.getElevIni() + this.getElevFim()) / 2.0;
        return this.lenght() * averageElev;
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
                            id = val; break;
                        case "vertIni":
                            vertIdIni = val; break;
                        case "vertFim":
                            vertIdFim = val; break;
                        case "profIni":
                            profIni = valDbl; break;
                        case "profFim":
                            profFim = valDbl; break;
                        case "dia":
                            dia = valDbl; break;
                        case "vazao":
                            vazao = valDbl; break;
                        case "flow":
                            flow = valDbl; break;
                        default:
                        break;
                    }
                }
            }
        }
    }

    public String toString() {
        return String.format("%s;%s;%s;%.3f;%.3f;%.1f;%.3f;%.1f;", id, vertIdIni, vertIdFim, profIni, profFim, dia, vazao, this.baseWeight());
    }
}
