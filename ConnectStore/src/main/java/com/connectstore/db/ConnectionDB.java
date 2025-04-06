package com.connectstore.db;

import com.connectstore.pessoa.Cliente;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class ConnectionDB {
    protected String  url;
    protected String user;
    protected String password;
    protected Connection con;

    public ConnectionDB(){
       this.url = "jdbc:mysql://localhost:3306/connectstore";
       this.user = "root";
       this.password = "1234";
        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println("Mensagem: "+e.getMessage());
        }
    }

    public void conexao(){

    }


}
