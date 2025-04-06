package com.connectstore.pessoa;

import java.sql.Time;
import java.time.LocalTime;

public class Funcionario extends Pessoa {
    private String matricula;
    private double salario;
    private String setor;
    private LocalTime inicioTurno;
    private LocalTime fimTurno;

    public Funcionario(String nome, String telefone, String matricula, double salario, String setor) {
        super(nome, telefone);
        this.matricula = matricula;
        this.salario = salario;
        this.setor = setor;
    }


    public void setInicioTurno(LocalTime inicioTurno) {
        this.inicioTurno = inicioTurno;
    }

    public void setFimTurno(LocalTime fimTurno) {
        this.fimTurno = fimTurno;
    }

    public String getMatricula() {
        return matricula;
    }

    public double getSalario() {
        return salario;
    }

    public String getSetor() {
        return setor;
    }

    public LocalTime getInicioTurno() {
        return inicioTurno;
    }

    public LocalTime getFimTurno() {
        return fimTurno;
    }
}
