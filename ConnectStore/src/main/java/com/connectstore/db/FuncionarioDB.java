package com.connectstore.db;

import com.connectstore.pessoa.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class FuncionarioDB extends ConnectionDB {

    public void insertFuncionario(Funcionario atd) {
        String insert = "INSERT INTO funcionario (nome, matricula, setor, idade, telefone, salario, hora_entrada, hora_saida) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement smt = con.prepareStatement(insert)) {
            smt.setString(1, atd.getNome());
            smt.setString(2, atd.getMatricula());
            smt.setString(3, atd.getSetor());
            smt.setString(5, atd.getTelefone());
            smt.setDouble(6, atd.getSalario());
            smt.setTime(7, Time.valueOf(atd.getInicioTurno()));
            smt.setTime(8, Time.valueOf(atd.getFimTurno()));

            int linhas = smt.executeUpdate();
            System.out.println("✅ Funcionário inserido com sucesso. Linhas afetadas: " + linhas);
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir funcionário: " + e.getMessage());
        }
    }

    public void buscarFuncionario(int id) {
        String select = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        try (PreparedStatement stm = con.prepareStatement(select)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println("🔎 Funcionário encontrado:");
                System.out.println("ID: " + rs.getInt("id_funcionario"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Matrícula: " + rs.getString("matricula"));
                System.out.println("Setor: " + rs.getString("setor"));
                System.out.println("Idade: " + rs.getInt("idade"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Salário: R$" + rs.getDouble("salario"));
                System.out.println("Entrada: " + rs.getTime("hora_entrada"));
                System.out.println("Saída: " + rs.getTime("hora_saida"));
            } else {
                System.out.println("❌ Funcionário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    public void pegarTodosFuncionarios() {
        String select = "SELECT * FROM funcionario";
        try (PreparedStatement stm = con.prepareStatement(select)) {
            ResultSet rs = stm.executeQuery();
            System.out.println("📋 Lista de Funcionários:");
            while (rs.next()) {
                System.out.println("-------------------------");
                System.out.println("ID: " + rs.getInt("id_funcionario"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Matrícula: " + rs.getString("matricula"));
                System.out.println("Setor: " + rs.getString("setor"));
                System.out.println("Telefone: " + rs.getString("telefone"));
                System.out.println("Salário: R$" + rs.getDouble("salario"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar funcionários: " + e.getMessage());
        }
    }


    public void updateFuncionario(Funcionario atd, int idFuncionario) {
        String update = "UPDATE funcionario SET nome = ?, matricula = ?, setor = ?, telefone = ?, salario = ?, hora_entrada = ?, hora_saida = ? WHERE id_funcionario = ?";
        try (PreparedStatement smt = con.prepareStatement(update)) {
            smt.setString(1, atd.getNome());
            smt.setString(2, atd.getMatricula());
            smt.setString(3, atd.getSetor());
            smt.setString(5, atd.getTelefone());
            smt.setDouble(6, atd.getSalario());
            smt.setTime(7, Time.valueOf(atd.getInicioTurno()));
            smt.setTime(8, Time.valueOf(atd.getFimTurno()));
            smt.setInt(9, idFuncionario);

            int linhas = smt.executeUpdate();
            System.out.println(linhas > 0 ? "✅ Funcionário atualizado com sucesso." : "❌ Nenhum funcionário foi atualizado.");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    public void deletarFuncionario(int id) {
        String delete = "DELETE FROM funcionario WHERE id_funcionario = ?";
        try (PreparedStatement smt = con.prepareStatement(delete)) {
            smt.setInt(1, id);
            int linhas = smt.executeUpdate();
            System.out.println(linhas > 0 ? "🗑️ Funcionário removido com sucesso." : "❌ Funcionário não encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao deletar funcionário: " + e.getMessage());
        }
    }
}
