package br.com.lucasnunes.dal;
import java.sql.*;

public class ModuloDeConexao {
    
    //Método responsável para estabelecer conexão com banco
    public static Connection conector(){
        
        java.sql.Connection conexao = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/desafio";
        String user = "root";
        String password = "";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
