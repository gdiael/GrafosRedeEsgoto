public class Vertex {

    public String id = "";

    public Double posX = 0.0;
    public Double posY = 0.0;

    public Double elev = 0.0;
    public String desc = "";

    public Vertex(){
        // não implementado
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
                    if(prop == "posX" || prop == "posY" || prop == "elev"){
                        valDbl = Double.parseDouble(val);
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

    public String toString() {
        return String.format("%s;%.3f;%.3f;%.3f;%s;", id, posX, posY, elev, desc);
    }

}
