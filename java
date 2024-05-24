package com.example.crud;

import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private double preco;

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', preco=" + preco + '}';
    }
}

package com.example.crud;

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

package com.example.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        produtos = ProdutoDAO.loadProdutos();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Deletar Produto");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a nova linha

            switch (opcao) {
                case 1:
                    adicionarProduto(scanner);
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    atualizarProduto(scanner);
                    break;
                case 4:
                    deletarProduto(scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            ProdutoDAO.saveProdutos(produtos);
        } while (opcao != 5);

        scanner.close();
    }

    private static void adicionarProduto(Scanner scanner) {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o preço do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();  // Consumir a nova linha

        Produto produto = new Produto(id, nome, preco);
        produtos.add(produto);
        System.out.println("Produto adicionado com sucesso!");
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private static void atualizarProduto(Scanner scanner) {
        System.out.print("Digite o ID do produto a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha

        Produto produto = produtos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if (produto != null) {
            System.out.print("Digite o novo nome do produto: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o novo preço do produto: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();  // Consumir a nova linha

            produto.setNome(nome);
            produto.setPreco(preco);
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void deletarProduto(Scanner scanner) {
        System.out.print("Digite o ID do produto a ser deletado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha

        Produto produto = produtos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

        if (produto != null) {
            produtos.remove(produto);
            System.out.println("Produto deletado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}
