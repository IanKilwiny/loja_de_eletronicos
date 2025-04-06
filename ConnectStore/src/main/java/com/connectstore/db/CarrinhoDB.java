package com.connectstore.db;

import com.connectstore.pessoa.Cliente;
import com.connectstore.produto.Carrinho;
import com.connectstore.produto.Produto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDB extends ConnectionDB{


        public void adicionarItem(Carrinho carrinho) {
            String sql = "INSERT INTO carrinho_produto (fk_cliente, fk_produto, quantidade, data_insercao) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, carrinho.fkCliente());
                stmt.setInt(2, carrinho.fkProduto());
                stmt.setInt(3, carrinho.quantidade());
                stmt.setDate(4, Date.valueOf(carrinho.dataInsercao()));
                stmt.executeUpdate();
                System.out.println("Item adicionado ao carrinho");
            } catch (SQLException e) {
                System.out.println(e);
            }
        }



        public void listarItensPorCliente(int fkCliente ) {

            String sql = """
                SELECT 
                    c.nome AS nome_cliente,
                    p.nome AS nome_produto,
                    p.preco AS preco_produto,
                    cp.fk_cliente,
                    cp.fk_produto,
                    cp.quantidade,
                    cp.data_insercao
                FROM carrinho_produto cp
                INNER JOIN cliente c ON cp.fk_cliente = c.id_cliente
                INNER JOIN produto p ON cp.fk_produto = p.id_produto
                WHERE cp.fk_cliente = ?
            """;

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, fkCliente);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    System.out.println("\n================================================================");

                    String nomeCliente = rs.getString("nome_cliente");
                    String nomeProduto = rs.getString("nome_produto");
                    double precoProduto = rs.getDouble("preco_produto");
                    int quantidade = rs.getInt("quantidade");
                    LocalDate dataInsercao = rs.getDate("data_insercao").toLocalDate();

                    System.out.println("Nome do cliente: "+nomeCliente);
                    System.out.println("Nome do Produto: "+nomeProduto);
                    System.out.println("Preço do Produto: "+precoProduto);
                    System.out.println("Quantidade: "+quantidade);
                    System.out.println("Data de Inserção: "+dataInsercao);

                    System.out.println("===================================================================\n");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void atualizarQuantidade(int fkCliente, int fkProduto, int novaQuantidade){
            String sql = "UPDATE carrinho_produto SET quantidade = ? WHERE fk_cliente = ? AND fk_produto = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, novaQuantidade);
                stmt.setInt(2, fkCliente);
                stmt.setInt(3, fkProduto);
                stmt.executeUpdate();
                System.out.println("Carrinho Atualizado");
            }catch (SQLException e){
                System.out.println(e);
            }
        }

        public void removerItem(int idCarrinho){
            String remove = "DELETE FROM carrinho_produto WHERE id_carrinho_produto = ?";
            try {
                PreparedStatement stm = this.con.prepareStatement(remove);
                stm.setInt(1, idCarrinho);

                System.out.println(stm.executeUpdate() > 1 ? "Produto deletado" :"Não encontrado");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void limparCarrinho(int fkCliente) {
            String sql = "DELETE FROM carrinho_produto WHERE fk_cliente = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, fkCliente);
                stmt.executeUpdate();
                System.out.println("Carrinho limpado com sucesso!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}
