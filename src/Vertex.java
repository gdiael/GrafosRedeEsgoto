import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class Vertex {

    public String id = "";

    public Double posX = 0.0;
    public Double posY = 0.0;

    public Double elev = 0.0;
    public String desc = "";

    private ArrayList<Edge> edges;

    public Vertex(){
        edges = new ArrayList<Edge>();
    }

    public void conect(Edge edge) {
        // precisa de mais verificações
        edges.add(edge);
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
                    Double valDbl = 0.0;
                    if(prop.equals("posX") || prop.equals("posY") || prop.equals("elev")){
                        try {
                            NumberFormat format = NumberFormat.getInstance(Locale.US);
                            Number number = format.parse(val);
                            valDbl = number.doubleValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    switch(prop){
                        case "id":
                            id = val;
                        case "posX":
                            posX = valDbl;
                        case "posY":
                            posY = valDbl;
                        case "elev":
                            elev = valDbl;
                        case "desc":
                            desc = val;
                    }
                }
            }
        }
        return;
    }

    // distância entre dois vertices
    public Double distance(Vertex vertTo){
        return Math.sqrt(Math.pow(vertTo.posX - this.posX, 2.0) + Math.pow(vertTo.posY - this.posY,2.0));
    }

    public String toString() {
        return String.format("%s;%.3f;%.3f;%.3f;%s;", id, posX, posY, elev, desc);
    }

}
