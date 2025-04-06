package com.connectstore.pessoa;

public class Cliente extends Pessoa{
    private String cpf;
    private String bairro;
    private String rua;
    private int numEndereco;
    private String email;
    private String senha;

    public Cliente(String nome, String cpf, String email, String telefone, String senha, String rua, String bairro, int endereco){
        super(nome, telefone);
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.rua = rua;
        this.bairro = bairro;
        this.numEndereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getEndereco() {

        return numEndereco;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getEmail() {
        return email;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
