// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Controlador.java

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.*;

public class Controlador extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
    private Connection dbcon;
  //  ConectaBanco conexao;
    Dados modeloDados;
    PesquisarDAO ps;
    PrintWriter out;
 //   int inicio;
 //   int fim;
    String siteQueUsuarioEsta;
    int contador=0;
    HttpSession session;
	
	
    public Controlador()
    {
        System.out.println("criou novo");
        modeloDados = new Dados();
        
        
  //      inicio = 0;
 //       fim = 2;
    }

    protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
        
    {
    	
    	doPost(httpservletrequest, httpservletresponse);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
    	
    	
    	// foi necessário implementar sessão, pois o objeto ps de um usuário estava sendo modificado por outro usuário 
    	session = req.getSession();
    	
    	ps = (PesquisarDAO)session.getAttribute("ps");
    	if(ps ==null)
    		ps = new PesquisarDAO();
    	System.out.println("valor do objeto:"+ps);
    	
    	session.setAttribute("ps",ps);
    	System.out.println("sessão:" + session.getId());
    	
    	
        out = resp.getWriter();
        
        String termo = req.getParameter("termo").trim().toUpperCase();
        String comando = req.getParameter("comando").toUpperCase();
        String modulo = req.getParameter("modulo").toUpperCase();
        siteQueUsuarioEsta = req.getParameter("site").toUpperCase();
        System.out.println(termo);
        modeloDados.setTermo(termo);
        modeloDados.setModulo(modulo);
       
        executarComando(comando);
       // destroy();
     //   conexao.fecharConexao();
    }

    public void executarComando(String comando)
    {
    	
        if(comando.equals("PESQUISAR") && (modeloDados.getModulo().equals("NET")))
        {
            List listaRetorno = new ArrayList();
            String resultado = ps.pesquisarSites(modeloDados.getTermo(), false);
            if(!resultado.equals(""))
            {
                String resultadoSeparado[] = resultado.split("#");
                listaRetorno.add(resultado);
            } else
            {
                listaRetorno.add("Pesquisa não retornou resultado");
            }
            out.print(listaRetorno);
        }
        
        if(comando.equals("MAIS") && (modeloDados.getModulo().equals("NET")))
        {
            List listaRetorno = new ArrayList();
            String resultado = ps.pesquisarSites(modeloDados.getTermo(), true);
            if(!resultado.equals(""))
            {
                String resultadoSeparado[] = resultado.split("#");
                listaRetorno.add(resultado);
            } else
            {
                listaRetorno.add("Pesquisa não retornou resultado");
            }
            out.print(listaRetorno);
        }
        
       
        
        
        if(comando.equals("PESQUISAR") && (modeloDados.getModulo().equals("WIKI")))
        {
            List listaRetorno = new ArrayList();
            String resultado = ps.pesquisar(modeloDados.getTermo());
            modeloDados.setResultado(resultado);
            
            System.out.println(resultado);
            listaRetorno.add(resultado);
            System.out.println(listaRetorno);
            out.print(listaRetorno);
        }
        
        
        if(comando.equals("PESQUISAR") && (modeloDados.getModulo().equals("DENTROSITE")))
        {
            List listaRetorno = new ArrayList();
            String resultado = ps.pesquisarDentroSite(modeloDados.getTermo(), siteQueUsuarioEsta, false);
            modeloDados.setResultado(resultado);
            System.out.println(resultado);
            listaRetorno.add(resultado);
            System.out.println(listaRetorno);
            out.print(listaRetorno);
        }
        
        
        if(comando.equals("PESQUISARTOPICO") && (modeloDados.getModulo().equals("DENTROSITE")))
        {
            List listaRetorno = new ArrayList();
            String resultado = ps.acessarTopicoSite(modeloDados.getTermo(), siteQueUsuarioEsta);
            modeloDados.setResultado(resultado);
            System.out.println(resultado);
            listaRetorno.add(resultado);
            System.out.println(listaRetorno);
            out.print(listaRetorno);
        }
        
        
        
        
        
        if(comando.equals("ASSOCIAR"))
        {
            List listaRetorno = new ArrayList();
            boolean maisTermos = false;
            String resultado = ps.pesquisarAssociacoesWiki(modeloDados.getTermo(), maisTermos);
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
        
        
        
        if(comando.equals("ACESSARGREETING"))
        {
        	List listaRetorno = new ArrayList();
            String resultado = ps.acessarGreetingDoSite(siteQueUsuarioEsta);
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
        
        if(comando.equals("ACESSAR"))
        {
            List listaRetorno = new ArrayList();
            boolean maisTermos = false;
            String resultado = ps.acessarSite(siteQueUsuarioEsta, maisTermos);
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
        
        if(comando.equals("MAIS") && modeloDados.getModulo().equals("DENTROSITE"))
        {
            List listaRetorno = new ArrayList();
            boolean maisTermos = true;
            String resultado = ps.acessarSite(siteQueUsuarioEsta, maisTermos);
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
        
        
        if(comando.equals("TÓPICOS") && modeloDados.getModulo().equals("DENTROSITE"))
        {
            List listaRetorno = new ArrayList();
            boolean maisTermos = true;
            String resultado = ps.acessarSite(siteQueUsuarioEsta, maisTermos);
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
        
        
        
        if(comando.equals("MAIS") && modeloDados.getModulo().equals("WIKI"))
        {
        	System.out.println("MAIS E WIKI");
            List listaRetorno = new ArrayList();
            boolean maisTermos = true;
            String resultado = ps.pesquisarAssociacoesWiki(modeloDados.getTermo(), maisTermos);
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
        if(comando.equals("PROPAGANDA"))
        {
            List listaRetorno = new ArrayList();
            String resultado = ps.acessarPropaganda();
            listaRetorno.add(resultado);
            out.print(listaRetorno);
        }
    }

    public static void main(String args[])
    {
        Controlador c = new Controlador();
        c.modeloDados.setTermo("REDAÇÃO");
        c.modeloDados.setModulo("WIKI");
        c.executarComando("PESQUISAR");
    }

    
}
