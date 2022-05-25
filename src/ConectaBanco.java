// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConectaBanco.java

import java.io.PrintStream;
import java.sql.*;

public class ConectaBanco
{

    public ConectaBanco()
    {
        con = null;
        carregarDriver();
    }

    public void carregarDriver()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Carregado");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("O driver do Mysql não pode ser carregado!");
        }
    }

    public Connection obterConexao()
    {
        try
        {
            if(con == null || con.isClosed())
            {
                con = DriverManager.getConnection("jdbc:mysql://www.websonora.com.br:3306/websonor_2012jan01", "database", "password");
                System.out.println("Conexão Obtida com Sucesso");
            }
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("SQLException ")).append(e.getMessage()).toString());
            e.printStackTrace();
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder("Exception ")).append(e.getMessage()).toString());
        }
        return con;
    }

    public void fecharConexao()
    {
        try
        {
            if(con != null && !con.isClosed())
            {
                con.close();
                System.out.println("Conexão Encerrada!");
            }
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("SQLException ")).append(e.getMessage()).toString());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder("Exception ")).append(e.getMessage()).toString());
        }
    }

    Connection con;
}
