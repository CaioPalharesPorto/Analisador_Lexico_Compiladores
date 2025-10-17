import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Caminho do arquivo de teste
        String sourceCodePath = "C:\\Users\\caiop\\OneDrive\\Documentos\\Compiladores\\Trabalho_Compiladores\\Trabalho_Compiladores_1\\Trabalho_Compiladores\\testes\\Teste5.txt";
        
        try ( // Abrir o arquivo
            FileReader fileReader = new FileReader(sourceCodePath)) {
            Lexer lexer = new Lexer(fileReader);
            Token token;

            // Lê e imprime cada token gerado
            System.out.println("\nTOKENS:\n");
            while ((token = lexer.scan()) != null) {
                System.out.println("  " + token);
            }   
            
            // Imprimir a tabela de símbolos
            List<Word> reservadas   = new ArrayList<>();
            List<Word> identificadores = new ArrayList<>();
            for (Word w : lexer.symbols().values()) {
                if (w.tag == Tag.ID) identificadores.add(w);
                else                 reservadas.add(w);
            }

            Comparator<Word> byLex = Comparator.comparing(w -> w.lexeme);
            reservadas.sort(byLex);
            identificadores.sort(byLex);

            System.out.println("\nTABELA DE SÍMBOLOS:");
            System.out.println("\nPalavras-reservadas:\n");
            for (Word w : reservadas) {
                System.out.println("  " + w); // KW(nome)
            }

            System.out.println("\nIdentificadores:\n");
            for (Word w : identificadores) {
                System.out.println("  " + w); // ID(nome)
            }
        }
    }
}
