// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Dados.java

import java.sql.*;
import java.util.ArrayList;

public class Dados
{

	//  public Dados modeloDados;
	    private Connection dbcon;
	    private String banco;
	    private String modulo;
	    private String ondeEstou;
	    private String resultado;
	    private String termo;
	    private ArrayList comandos;
	
    public Dados()
    {
      //  modeloDados = null;
        termo = new String();
        comandos = new ArrayList();
    }

    public String getBanco()
    {
        return banco;
    }

    public void setBanco(String banco)
    {
        this.banco = banco;
    }

    public String getModulo()
    {
        return modulo;
    }

    public void setModulo(String modulo)
    {
        this.modulo = modulo;
    }

    public String getResultado()
    {
        return resultado;
    }

    public void setResultado(String resultado)
    {
        this.resultado = resultado;
    }

    public String getTermo()
    {
        return termo;
    }

    public void setTermo(String termo)
    {
        this.termo = termo.toUpperCase();
    }

    public String getOndeEstou()
    {
        return ondeEstou;
    }

    public void setOndeEstou(String ondeEstou)
    {
        this.ondeEstou = ondeEstou;
    }

    public ArrayList getComandos()
    {
        return comandos;
    }

    public void setComandos(String comandos)
    {
        this.comandos.add(comandos);
    }

  /*  public void preencherArrayListComandos()
    {
        try
        {
            PreparedStatement st = dbcon.prepareStatement("Select * from comandosgramatica");
            for(ResultSet rs = st.executeQuery(); rs.next(); modeloDados.setComandos(rs.getString(1)));
        }
        catch(Exception exception) { }
    }*/

  
}
