import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("");
            System.out.println("Iniciando Grafo para Redes de Esgoto.");
            System.out.println("");
            System.out.println("Digite o nome de um dos arquivos de grafo disponiveis na pasta 'data' [ou Enter para demo]:");
            
            String fileName = "demo";
            
            String input = scanner.nextLine();
            if(!input.isEmpty()) fileName = input;

            String prefix = ".\\data\\";
            String suffix = ".txt";

            if(!fileName.startsWith(prefix)) fileName = prefix + fileName;

            if(!fileName.endsWith(suffix)) fileName += suffix;

            Path filePath = Path.of(fileName);
            try {
                String content = Files.readString(filePath, StandardCharsets.UTF_8);
                Graph graph = Graph.parseGraph(content);
                graph.depthSearch();
            } catch (Exception e) {
                System.out.println("Erro ao ler o arquivo: " + fileName + "\n" + e.toString());
            }
        }
    }
}