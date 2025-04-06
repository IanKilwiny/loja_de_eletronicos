package com.connectstore.db;

import java.sql.*;

public class VendaProdutoDB extends ConnectionDB {

    // CREATE
    public void inserirVendaProduto(int quantidade, int idProduto, int idVenda) {
        String insert = "INSERT INTO venda_produtos (qtd, fk_produto, fk_venda) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(insert)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, idProduto);
            stmt.setInt(3, idVenda);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? " Produto adicionado à venda com sucesso." : " Falha ao adicionar produto à venda.");
        } catch (SQLException e) {
            System.out.println(" Erro ao inserir produto na venda: " + e.getMessage());
        }
    }

    // READ - Buscar um item da venda pelo ID
    public void buscarVendaProduto(int idVendaProduto) {
        String select = "SELECT * FROM venda_produtos WHERE id_venda_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            stmt.setInt(1, idVendaProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println(" Produto encontrado na venda:");
                System.out.println("ID Venda Produto: " + rs.getInt("id_venda_produto"));
                System.out.println("Quantidade: " + rs.getInt("quantidade"));
                System.out.println("Produto (ID): " + rs.getInt("fk_produto"));
                System.out.println("Venda (ID): " + rs.getInt("fk_venda"));
            } else {
                System.out.println(" Produto da venda com ID " + idVendaProduto + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao buscar produto na venda: " + e.getMessage());
        }
    }

    // READ - Listar todos os itens de todas as vendas
    public void listarTodosItensVenda() {
        String select = "SELECT * FROM venda_produtos";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Itens vendidos:");
            while (rs.next()) {
                System.out.println("---------------------------");
                System.out.println("ID Venda Produto: " + rs.getInt("id_venda_produto"));
                System.out.println("Quantidade: " + rs.getInt("quantidade"));
                System.out.println("Produto (ID): " + rs.getInt("fk_produto"));
                System.out.println("Venda (ID): " + rs.getInt("fk_venda"));
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao listar itens de venda: " + e.getMessage());
        }
    }

    // UPDATE - Atualizar a quantidade de um item da venda
    public void atualizarVendaProduto(int idVendaProduto, int novaQuantidade) {
        String update = "UPDATE venda_produtos SET qtd = ? WHERE id_venda_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(update)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idVendaProduto);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? " Quantidade atualizada com sucesso." : " Produto de venda não encontrado.");
        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar quantidade: " + e.getMessage());
        }
    }

    // DELETE - Remover produto da venda
    public void deletarVendaProduto(int idVendaProduto) {
        String delete = "DELETE FROM venda_produtos WHERE id_venda_produto = ?";
        try (PreparedStatement stmt = con.prepareStatement(delete)) {
            stmt.setInt(1, idVendaProduto);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "🗑 Produto removido da venda com sucesso." : " Produto da venda não encontrado.");
        } catch (SQLException e) {
            System.out.println(" Erro ao deletar produto da venda: " + e.getMessage());
        }
    }

}
