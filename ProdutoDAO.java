import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static final String FILE_NAME = "src/main/resources/produtos.txt";

    public static List<Produto> loadProdutos() {
        List<Produto> produtos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            produtos = (List<Produto>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Arquivo não encontrado, retorna lista vazia
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public static void saveProdutos(List<Produto> produtos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(produtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
