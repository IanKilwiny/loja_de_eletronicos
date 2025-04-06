package com.connectstore.db;

import com.connectstore.produto.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDB extends ConnectionDB{


    public void adicionarProduto(Produto produto) {
        String insert = "INSERT INTO produto(nome, preco, marca, modelo, descricao, tipo, estoque) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = con.prepareStatement(insert)) {
            stm.setString(1, produto.nome());
            stm.setDouble(2, produto.preco());
            stm.setString(3, produto.marca());
            stm.setString(4, produto.modelo());
            stm.setString(5, produto.descricao());
            stm.setString(6, produto.tipo());
            stm.setInt(7, produto.estoque());

            System.out.println(stm.executeUpdate() > 0 ? "✅ Produto adicionado com sucesso." : "❌ Produto não foi adicionado.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    // READ - Buscar um produto por ID
    public void buscarProduto(int idProduto) {
        String select = "SELECT * FROM produto WHERE id_produto = ?";
        try (PreparedStatement stm = con.prepareStatement(select)) {
            stm.setInt(1, idProduto);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println("🔎 Produto encontrado:");
                System.out.println("ID: " + rs.getInt("id_produto"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$" + rs.getDouble("preco"));
                System.out.println("Marca: " + rs.getString("marca"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Descrição: " + rs.getString("descricao"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
            } else {
                System.out.println("❌ Produto com ID " + idProduto + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
    }

    // READ - Listar todos os produtos
    public void mostrarTodosOsProdutos() {
        String select = "SELECT * FROM produto";
        try (PreparedStatement stm = con.prepareStatement(select)) {
            ResultSet rs = stm.executeQuery();

            System.out.println("Lista de produtos cadastrados:");
            while (rs.next()) {
                System.out.println("\n-------------------------------");
                System.out.println("ID: " + rs.getInt("id_produto"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$" + rs.getDouble("preco"));
                System.out.println("Marca: " + rs.getString("marca"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Descrição: " + rs.getString("descricao"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("----------------------------------------------\n");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
    }

    // UPDATE - Atualizar preço de um produto
    public void atualizarPrecoProduto(int idProduto, double novoPreco) {
        String update = "UPDATE produto SET preco = ? WHERE id_produto = ?";
        try (PreparedStatement stm = con.prepareStatement(update)) {
            stm.setDouble(1, novoPreco);
            stm.setInt(2, idProduto);

            System.out.println(stm.executeUpdate() > 0 ?
                    "Preço atualizado com sucesso." :
                    "Produto com ID " + idProduto + " não foi encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar preço do produto: " + e.getMessage());
        }
    }

    // DELETE
    public void removerProduto(int id) {
        String delete = "DELETE FROM produto WHERE id_produto = ?";
        try (PreparedStatement stm = con.prepareStatement(delete)) {
            stm.setInt(1, id);

            System.out.println(stm.executeUpdate() > 0 ?
                    "Produto removido com sucesso." :
                    "Produto com ID " + id + " não foi encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }
}
