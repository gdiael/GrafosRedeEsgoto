import java.util.logging.Logger;
import java.util.logging.Level;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static Logger logger = Logger.getLogger("teste");

    public static void main(String[] args) {
        
        try (Scanner scanner = new Scanner(System.in)) {

            logger.log(Level.INFO, "\nIniciando Grafo para Redes de Esgoto.");
            logger.log(Level.INFO, "\nDigite o nome de um dos arquivos de grafo disponiveis na pasta 'data' [ou Enter para demo]:");
            
            String fileName = "demo";
            
            String input = scanner.nextLine();
            if(!input.isEmpty()) fileName = input;

            String prefix = ".\\data\\";
            String suffix = ".txt";

            if(!fileName.startsWith(prefix)) fileName = prefix + fileName;

            if(!fileName.endsWith(suffix)) fileName += suffix;

            Path filePath = Path.of(fileName);
            try {
                String content = Files.readString(filePath, java.nio.charset.StandardCharsets.UTF_8);
                Graph graph = Graph.parseGraph(content);
                graph.depthSearch();
            } catch (Exception e) {
                logger.log(Level.SEVERE, String.format("Erro ao ler o arquivo: %s", fileName), e);
            }
        }
    }
}