import java.util.logging.Logger;
import java.util.logging.Level;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Logger logger = Logger.getLogger("teste");

    public static void main(String[] args) {
        
        String fileName = "demo";

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("\nIniciando Grafo para Redes de Esgoto.");
            System.out.println("\nDigite o nome de um dos arquivos de grafo disponiveis na pasta 'data' [ou Enter para demo]:");
            
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
            logger.log(Level.SEVERE, String.format("Erro ao ler o arquivo: %s%n%s", fileName, e.toString()), e);
        }
        if(graph != null) {
            List<Vertex> minSpamTree = graph.toLineGraph().findMinSpamTree();
            for (Vertex vert : minSpamTree) {
                Edge edge = graph.getEdge(vert.id);
                System.out.println(String.format("%s - peso %.3f", edge.id, edge.baseWeight()));
            }
        } 
    }
}