public class Edge {
    
    public String id = "";
    public String vertIni = "";
    public String vertFim = "";
    public Double profIni = 0.8; // profundidade em vertIni
    public Double profFim = 0.8; // profundidade em vertFim
    public Double dia = 150.0; // diâmetro em milimetros
    public double vazao = 1.5; // vazão de cada trecho em L/s
    public double flow = 1.0; // direção do fluxo (positivo = ini -> fim, negativo = fim -> ini)

    public void populate(String titleLine, String line) {
        if(titleLine.startsWith("##")) return;
        if(titleLine.startsWith("#")){
            String[] arrProp = titleLine.substring(1).split(";");
            String[] arrVal = line.split(";");
            if(arrProp.length == arrVal.length){
                for(Integer i = 0; i < arrProp.length; i++) {
                    String prop = arrProp[i];
                    String val = arrVal[i];
                    Double valDbl = 0.0;
                    if(prop == "profIni" || prop == "profFim" || prop == "dia" || prop == "vazao" || prop == "flow"){
                        valDbl = Double.parseDouble(val);
                    }
                    switch(prop){
                        case "id":
                            id = val;
                        case "vertIni":
                            vertIni = val;
                        case "vertFim":
                            vertFim = val;
                        case "profIni":
                            profIni = valDbl;
                        case "profFim":
                            profFim = valDbl;
                        case "dia":
                            dia = valDbl;
                        case "vazao":
                            vazao = valDbl;
                        case "flow":
                            flow = valDbl;
                    }
                }
            }
        }
        return;
    }

    public String toString() {
        return String.format("%s;%s;%s;%.3f;%.3f;%.1f;%.3f;%.1f;", id, vertIni, vertFim, profIni, profFim, dia, vazao, flow);
    }
}
