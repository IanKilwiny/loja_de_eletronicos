package com.connectstore.db;

import java.sql.*;
import java.time.LocalDate;

public class VendaDB extends ConnectionDB {


    public int inserirVenda(LocalDate dataVenda, int idCliente, int idFuncionario) {
        String sql = "INSERT INTO venda (data_venda, fk_cliente, fk_funcionario) VALUES (?, ?, ?)";
        int idGerado = -1;

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(dataVenda));
            stmt.setInt(2, idCliente);
            stmt.setInt(3, idFuncionario);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            } else {
                System.out.println(" Venda não foi inserida.");
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao inserir venda: " + e.getMessage());
        }

        return idGerado;
    }

    public void buscarVenda(int idVenda) {
        String select = "SELECT * FROM venda WHERE id_venda = ?";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            stmt.setInt(1, idVenda);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println(" Venda encontrada:");
                System.out.println("ID: " + rs.getInt("id_venda"));
                System.out.println("Data: " + rs.getDate("data_venda"));
                System.out.println("Cliente (ID): " + rs.getInt("fk_cliente"));
                System.out.println("Funcionário (ID): " + rs.getInt("fk_funcionario"));
            } else {
                System.out.println(" Nenhuma venda com ID " + idVenda + " foi encontrada.");
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao buscar venda: " + e.getMessage());
        }
    }


    public void listarTodasAsVendas() {
        String select = "SELECT * FROM venda";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println(" Lista de Vendas:");
            while (rs.next()) {
                System.out.println("-----------------------------");
                System.out.println("ID Venda: " + rs.getInt("id_venda"));
                System.out.println("Data: " + rs.getDate("data_venda"));
                System.out.println("Cliente (ID): " + rs.getInt("fk_cliente"));
                System.out.println("Funcionário (ID): " + rs.getInt("fk_funcionario"));
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao listar vendas: " + e.getMessage());
        }
    }

    public void atualizarVenda(int idVenda, int novoCliente, int novoFuncionario) {
        String update = "UPDATE venda SET fk_cliente = ?, fk_funcionario = ? WHERE id_venda = ?";
        try (PreparedStatement stmt = con.prepareStatement(update)) {
            stmt.setInt(1, novoCliente);
            stmt.setInt(2, novoFuncionario);
            stmt.setInt(3, idVenda);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? " Venda atualizada com sucesso." : " Venda não encontrada.");
        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void deletarVenda(int idVenda) {
        String delete = "DELETE FROM venda WHERE id_venda = ?";
        try (PreparedStatement stmt = con.prepareStatement(delete)) {
            stmt.setInt(1, idVenda);
            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "🗑 Venda removida com sucesso." : " Venda não encontrada.");
        } catch (SQLException e) {
            System.out.println(" Erro ao deletar venda: " + e.getMessage());
        }
    }

    public void listarVendasPorClienteProduto(int idCliente) {
        String sql = """
            SELECT 
                cl.nome AS cliente, 
                pr.nome AS produto,
                pr.preco AS preco 
                vd.data_venda 
            FROM cliente cl
            INNER JOIN venda vd ON cl.id_cliente = ?
            INNER JOIN venda_produtos vdp ON vd.id_venda = vdp.fk_venda
            INNER JOIN produto pr ON vdp.fk_produto = pr.id_produto
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            System.out.println(" Vendas por Cliente e Produto:");
            while (rs.next()) {
                String cliente = rs.getString("cliente");
                String produto = rs.getString("produto");
                double preco = rs.getDouble("produto");
                java.sql.Date dataVenda = rs.getDate("data_venda");

                System.out.println("\n----------------------------");
                System.out.println("Cliente: " + cliente);
                System.out.println("Produto: " + produto);
                System.out.println("Preco: " + preco);
                System.out.println("Data da Venda: " + dataVenda);
                System.out.println("------------------------------\n");
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao executar relatório: " + e.getMessage());
        }
    }
}
