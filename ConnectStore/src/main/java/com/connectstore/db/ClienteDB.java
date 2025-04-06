package com.connectstore.db;

import com.connectstore.pessoa.Autenticacao;
import com.connectstore.pessoa.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDB extends ConnectionDB {

    public void insertCliente(Cliente cliente) {
        String insert = "INSERT INTO cliente (nome, cpf, email, senha, telefone, rua, bairro, endereco) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement smt = con.prepareStatement(insert)) {
            smt.setString(1, cliente.getNome());
            smt.setString(2, cliente.getCpf());
            smt.setString(3, cliente.getEmail());
            smt.setString(4, cliente.getSenha());
            smt.setString(5, cliente.getTelefone());
            smt.setString(6, cliente.getRua());
            smt.setString(7, cliente.getBairro());
            smt.setInt(8, cliente.getEndereco());

            int linhas = smt.executeUpdate();
            System.out.println("Cliente Adicionado: ");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public void selectCliente(int id) {
        String select = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("🔎 Cliente encontrado:");
                System.out.println("ID: " + id);
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Rua: " + rs.getString("rua"));
                System.out.println("Bairro: " + rs.getString("bairro"));
                System.out.println("Endereço: " + rs.getString("endereco"));
            } else {
                System.out.println("Nenhum cliente encontrado com ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }


    public void updateCliente(Cliente cliente, int idCliente) {
        String update = "UPDATE cliente SET nome = ?, cpf = ?, email = ?, senha = ?, telefone = ?, rua = ?, bairro = ?, endereco = ? WHERE id_cliente = ?";
        try (PreparedStatement smt = con.prepareStatement(update)) {
            smt.setString(1, cliente.getNome());
            smt.setString(2, cliente.getCpf());
            smt.setString(3, cliente.getEmail());
            smt.setString(4, cliente.getSenha());
            smt.setString(5, cliente.getTelefone());
            smt.setString(6, cliente.getRua());
            smt.setString(7, cliente.getBairro());
            smt.setInt(8, cliente.getEndereco());
            smt.setInt(9, idCliente);

            int linhas = smt.executeUpdate();
            System.out.println(linhas > 0 ? "Cliente atualizado com sucesso." : "Nenhum cliente atualizado. Verifique o ID.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }


    public void deletarCliente(int id) {
        String delete = "DELETE FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement smt = con.prepareStatement(delete)) {
            smt.setInt(1, id);
            int linhas = smt.executeUpdate();
            System.out.println(linhas > 0 ? "🗑️ Cliente removido com sucesso." : "Cliente não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }

    public int autenticar(String email, String senha) {
        String select = "SELECT id_cliente FROM cliente WHERE email = ? AND senha = ?";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                return set.getInt("id_cliente");
            } else {
                return 0; // Cliente não encontrado
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return 0;
    }

    public void mostrarTodosOsClientes(){
        String select = "SELECT * FROM cliente";
        try (PreparedStatement stmt = con.prepareStatement(select)) {
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                System.out.println("===================================================");
                String nome = set.getString("nome");
                String email = set.getString("email");
                String telefone = set.getString("telefone");

                System.out.println("Nome: "+nome);
                System.out.println("Email: "+email);
                System.out.println("Telefone: "+telefone);
                System.out.println("===================================================");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }
}
