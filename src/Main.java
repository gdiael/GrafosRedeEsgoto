import java.util.logging.Level;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        String fileName = "demo";

        try (Scanner scanner = new Scanner(System.in)) {

            MyUtil.printMsg("\nIniciando Grafo para Redes de Esgoto.", Level.INFO, false);
            MyUtil.printMsg("\nDigite o nome de um dos arquivos de grafo disponiveis na pasta 'data' [ou Enter para demo]:", Level.INFO, false);
            
            String input = scanner.nextLine();
            if(!input.isEmpty()) fileName = input;
        }

        String prefix = ".\\data\\";
        String suffix = ".txt";

        if(!fileName.startsWith(prefix)) fileName = prefix + fileName;

        if(!fileName.endsWith(suffix)) fileName += suffix;

        Path filePath = Path.of(fileName);

        Graph graph = null;

        try {
            String content = Files.readString(filePath, java.nio.charset.StandardCharsets.UTF_8);
            graph = Graph.parseGraph(content);
        } catch (Exception e) {
            MyUtil.printMsg(String.format("Erro ao ler o arquivo: %s%n%s", fileName, e.toString()), Level.SEVERE, true);
        }
        if(graph != null) {
            graph.calculateEdgeDirection();
        } 
    }
}