package com.connectstore;


import com.connectstore.db.*;
import com.connectstore.pessoa.Cliente;
import com.connectstore.pessoa.Funcionario;
import com.connectstore.produto.Carrinho;
import com.connectstore.produto.Produto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int opt = 0;
        ClienteDB clienteDB = new ClienteDB();

        Scanner sc = new Scanner(System.in);
        System.out.println("CONNECT STORE");
        String menu = """
                [1] - Área funcionário
                [2] - Área Cliente
                [3] - Sair
                """;


        while(opt != 4){
            System.out.println(menu);
            System.out.print("Escolha a opção: ");
            opt = sc.nextInt();
            switch (opt){
                case 1:
                    areaFuncionario();
                    break;
                case 2:
                    System.out.print("Informe seu email: ");
                    String email = sc.next();
                    System.out.print("Informe sua senha: ");
                    String senha = sc.next();

                    //autenticação do cliente
                    int idCliente = clienteDB.autenticar(email, senha);
                    if (idCliente > 0){
                        areaCliente(idCliente);
                    }else{
                        System.out.println("Cliente não encontrado!");
                    }

                    break;

                case 3:
                    System.out.println("Saindo ...");
                    break;
            }
        }

    }

    public static void areaCliente(int idCliente){
        CarrinhoDB carrinhoDB = new CarrinhoDB();
        ClienteDB clienteDB = new ClienteDB();
        VendaDB vendaDB = new VendaDB();

        String menu = """
                [1] Adicionar item ao carrinho de compra
                [2] Remover item do carrinho de compra
                [3] Mostrar lista de itens no carrinho
                [4] Mostrar itens comprados
                [5] Voltar
                """;


        int opt = 0;
        Scanner input = new Scanner(System.in);

        while(opt != 5){
            System.out.println(menu);
            System.out.print("Qual opção: ");
            opt = input.nextInt();

            switch (opt){
                case 1:
                    System.out.print("Informe o id do produto: ");
                    int idProduto = input.nextInt();

                    System.out.print("Informe a quantidade: ");
                    int quantidade = input.nextInt();

                    carrinhoDB.adicionarItem(new Carrinho(idCliente, idProduto, LocalDate.now(), quantidade));
                    break;

                case 2:
                    System.out.print("Remove da compra do carrinho: ");
                    int idCarrinho = input.nextInt();
                    carrinhoDB.removerItem(idCarrinho);
                case 3:
                    carrinhoDB.listarItensPorCliente(idCliente);
                    break;

                case 4:
                    vendaDB.listarVendasPorClienteProduto(idCliente);
                    break;


            }
        }
    }

    public static void areaFuncionario(){
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        ClienteDB clienteDB = new ClienteDB();
        ProdutoDB produtoDB = new ProdutoDB();

        VendaDB vendaDB = new VendaDB();

        System.out.println("ÁREA FUNCIONÁRIO");
        String menu = """
                [1] - Cadastro de Cliente
                [2] - Adicionar Produto
                [3] - Adicionar compra
                [4] - Remover cliente
                [5] - Mostrar todos clientes
                [6] - Mostrar todos os produtos;
                [7] - Mostrar todas as compras do cliente
                """;

        while(opt != 10){
            System.out.println(menu);
            System.out.print("Escolha a opção: ");
            opt = scanner.nextInt();
            int idCliente;

            //CLIENTE
            switch (opt){
                case 1:
                    System.out.println("CADASTRO DE CLIENTE");

                    System.out.print("Digite o nome: ");
                    String nome = scanner.next();

                    System.out.print("Digite o CPF (apenas números): ");
                    String cpf = scanner.next();

                    System.out.print("Digite o e-mail: ");
                    String email = scanner.next();

                    System.out.print("Digite a senha (8 caracteres): ");
                    String senha = scanner.next();

                    System.out.print("Digite o telefone: ");
                    String telefone = scanner.next();

                    System.out.print("Digite a rua: ");
                    String rua = scanner.next();

                    System.out.print("Digite o bairro: ");
                    String bairro = scanner.next();

                    System.out.print("Digite o número do endereço: ");
                    int endereco = scanner.nextInt();

                    clienteDB.insertCliente(
                            new Cliente(nome,cpf,email,telefone, senha, rua, bairro, endereco)
                    );


                    break;
                    //PRODUTO
                case 2:
                    System.out.print("Digite o nome do produto: ");
                    String nomeProduto = scanner.next();

                    System.out.print("Digite o preço: ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine(); // limpa o buffer

                    System.out.print("Digite a marca: ");
                    String marca = scanner.next();

                    System.out.print("Digite o modelo: ");
                    String modelo = scanner.next();


                    scanner.nextLine();

                    System.out.print("Digite a descrição: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Digite o tipo: ");
                    String tipo = scanner.next();

                    System.out.print("Digite a quantidade em estoque: ");
                    int estoque = scanner.nextInt();

                    Produto produto = new Produto(nomeProduto, preco, marca, modelo, descricao, tipo, estoque);

                    produtoDB.adicionarProduto(produto);
                    break;

                case 3:
                    System.out.print("Informe o id do cliente: ");
                    idCliente = scanner.nextInt();
                    System.out.println(idCliente);
                    System.out.print("Informe o id do funcionário:");
                    int idFuncionario= scanner.nextInt();

                    int idVenda = vendaDB.inserirVenda(LocalDate.now(), idCliente, idFuncionario); //adicionada o id do produto
                    adicionarProdutos(idVenda);
                    break;

                case 4:
                    System.out.print("Informe o id do cliente: ");
                    idCliente = scanner.nextInt();
                    clienteDB.deletarCliente(idCliente);
                    break;

                case 5:
                    System.out.println("Lista de clientes");
                    clienteDB.mostrarTodosOsClientes();
                    break;

                case 6:
                    produtoDB.mostrarTodosOsProdutos();
                    break;

                case 7:
                    System.out.println("Informe o id do cliente: ");
                    int idCli= scanner.nextInt();
                    vendaDB.listarVendasPorClienteProduto(idCli);
                    break;

                case 8:
                    System.out.print("Informe o id do produto: ");
                    int idProduto = scanner.nextInt();

                    produtoDB.removerProduto(idProduto);

                    break;


            }
        }



    }

    public static void adicionarProdutos(int idVenda){
        Scanner scanner = new Scanner(System.in);
        VendaProdutoDB vendaProdutoDB = new VendaProdutoDB();

        int opt = 1;

        while (opt != 2) {
            System.out.print("Informe o id do produto: ");
            int idProduto = scanner.nextInt();
            System.out.println("Informe a quantidade dos itens: ");
            int quantidade = scanner.nextInt();

            vendaProdutoDB.inserirVendaProduto(quantidade, idProduto, idVenda);

            System.out.print("quer continuar? [1] - sim | [2] - não");

            opt = scanner.nextInt();
        }
    }

}