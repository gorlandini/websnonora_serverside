// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PesquisarDAO.java

import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

public class PesquisarDAO {
	ConectaBanco conexao = null;
	private static final long serialVersionUID = 1L;
	private Connection dbcon;
	Dados modeloDados;
	boolean primeiraVezChamouMais = true;
	int inicio;
	int fim;

	public PesquisarDAO() {
		System.out.println("iniciou pesquisarDAO");
		modeloDados = new Dados();
		inicio = 1;
		fim = 3;
		conectar();
		/*
		 * try { dbcon = conexao.getConnection(); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public void conectar() {
		conexao = new ConectaBanco();
		dbcon = conexao.obterConexao();
	}

	public void desconectar() {
		conexao.fecharConexao();
	}

	public String pesquisar(String t) {

		String termo = "";
		String descricao = "";
		termo = t;
		try {
			conectar();
			PreparedStatement st = dbcon
					.prepareStatement("Select descricao from termos where termo=?");
			st.setString(1, termo);
			System.out.println(st);
			ResultSet rs = st.executeQuery();
			rs.first();
			descricao = rs.getString(1);
			System.out.println(st);
			desconectar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			descricao = "";
			return descricao;
		}
		return descricao;
	}

	public String pesquisarAssociacoesWiki(String t, boolean maisTermos) {
		System.out.println("PrimeiraVEz chamou mais:" + primeiraVezChamouMais);
		String termo = t;
		String resultado = "";
		int qtdRegistros = 0;
		try {
			conectar();
			PreparedStatement st;
			if ((!maisTermos) || (primeiraVezChamouMais)) {
				inicio = 0;
				st = dbcon
						.prepareStatement("Select termo from termos where descricao like ? LIMIT ?,?");
				st.setString(1, (new StringBuilder(String.valueOf('%')))
						.append(termo.trim()).append('%').toString());
				st.setInt(2, inicio);
				st.setInt(3, fim);
				primeiraVezChamouMais = false;
			} else {
				inicio += 3;
				qtdRegistros = 0;
				st = dbcon
						.prepareStatement("Select termo from termos where descricao like ? LIMIT ?,?");
				st.setString(1, (new StringBuilder(String.valueOf('%')))
						.append(termo.trim()).append('%').toString());
				st.setInt(2, inicio);
				st.setInt(3, fim);
			}
			ResultSet rs = st.executeQuery();
			System.out.println(st);
			while (rs.next()) {
				qtdRegistros++;
				resultado = (new StringBuilder(String.valueOf(resultado)))
						.append(rs.getString(1)).append("#").toString();
			}
			System.out.println((new StringBuilder("quantidade registros:"))
					.append(qtdRegistros).toString());
			if (qtdRegistros < 3)
			{
				inicio = 0;
				primeiraVezChamouMais = true;
			}	
			System.out.println("PrimeiraVEz chamou mais:" + primeiraVezChamouMais);
			desconectar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado.trim();
		
	}
	
	
	public String acessarGreetingDoSite(String nomeDoSite)
	{
		String descricao="";
		try {
			conectar();
			PreparedStatement st = null;
			String sql = "Select greeting,greetingmp3 from sites where nomeSite=?";
						
			
			System.out.println(sql);
			st = dbcon.prepareStatement(sql);
			st.setString(1, nomeDoSite);
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				descricao = descricao + rs.getString(1);
				if(!rs.getString(2).equals(""))
				descricao = descricao + "#" + rs.getString(2);
				else
					descricao = descricao + "#" + "SEMGREETINGMP3";
			}
			desconectar();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return descricao;
	}

	public String acessarSite(String nomeDosite, boolean maisTermos) {

		String descricao = "";
		int qtdRegistros = 0;

		try {
			conectar();
			PreparedStatement st = null;
			if ((!maisTermos) || (primeiraVezChamouMais)) {
				System.out.println(maisTermos + " " +primeiraVezChamouMais);
				inicio = 0;
				String sql = (new StringBuilder("Select topico from "))
						.append(nomeDosite)
						.append(" where flag=1 order by codTopico DESC LIMIT ?,?").toString();

				st = dbcon.prepareStatement(sql);
				st.setInt(1, inicio);
				st.setInt(2, fim);
				System.out.println("1º " + st);
				primeiraVezChamouMais = false;
			} else {
				inicio += 3;
				qtdRegistros = 0;
				String sql = (new StringBuilder("Select topico from "))
						.append(nomeDosite)
						.append(" where flag=1 order by codTopico DESC LIMIT ?,?").toString();
				st = dbcon.prepareStatement(sql);
				st.setInt(1, inicio);
				st.setInt(2, fim);
				System.out.println("MAIS " + st);

			}

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				qtdRegistros++;
				descricao = descricao + rs.getString(1) + "#";

			}
			System.out.println((new StringBuilder("quantidade registros:"))
					.append(qtdRegistros).toString());
			if (qtdRegistros < 3)
			{
				inicio = 0;
				primeiraVezChamouMais = true;
			}
			desconectar();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return descricao;
	}

	
	
	
	
	
	public String acessarTopicoSite(String topicoSite,String nomeDosite)
	{
		String descricao="";
		PreparedStatement st;
		try {
			conectar();
			
			
			String sql = (new StringBuilder("Select conteudo from "))
					.append( nomeDosite)
					.append(" where topico like ? ").toString();
					
			
			st = dbcon.prepareStatement(sql);
			st.setString(1, (new StringBuilder(String.valueOf('%')))
						.append(topicoSite.trim()).append('%').toString());
			
			
			
			System.out.println(st);
			ResultSet rs = st.executeQuery();
			rs.first();
			descricao = rs.getString(1);
			System.out.println(st);
			desconectar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			descricao = "";
			return descricao;
		}
		return descricao;
		
		
	}
	
	public boolean pesquisarSeNaoEhComando(String termo) {
		boolean ehcomando = true;
		for (int i = 0; i < modeloDados.getComandos().size(); i++)
			if (((String) modeloDados.getComandos().get(i)).equals(termo))
				ehcomando = false;

		return ehcomando;
	}

	public void preencherArrayListComandos() {
		try {
			conectar();
			PreparedStatement st = dbcon
					.prepareStatement("Select * from comandosgramatica");
			for (ResultSet rs = st.executeQuery(); rs.next(); modeloDados
					.setComandos(rs.getString(1)))
				;
			desconectar();
		} catch (Exception exception) {
		}
	}

	public String pesquisarSites(String t, boolean maisTermos) {
		String termo = "";
		String resultado = "";
		termo = t;
		int qtdRegistros = 0;
		try {
			conectar();
			PreparedStatement st;
			if ((!maisTermos) || (primeiraVezChamouMais)) {
				inicio = 0;
				fim = 3;
				st = dbcon
						.prepareStatement("Select nomeTabela from sites where palavrasChave like ? order by ranking LIMIT ?,?");
				st.setString(1, (new StringBuilder(String.valueOf('%')))
						.append(termo.trim()).append('%').toString());
				// st.setString(1, "\"%" + termo.trim() + "%\"");
				st.setInt(2, inicio);
				st.setInt(3, fim);
				System.out.println(st);
				primeiraVezChamouMais = false;
			} else {
				qtdRegistros = 0;
				inicio += 3;
				fim += 2;
				st = dbcon
						.prepareStatement("Select nomeTabela from sites where palavras_chave like ? order by ranking LIMIT ?,?");
				st.setString(1, (new StringBuilder(String.valueOf('%')))
						.append(termo.trim()).append('%').toString());
				st.setInt(2, inicio);
				st.setInt(3, fim);
			}
			ResultSet rs = st.executeQuery();
			System.out.println(st);
			while (rs.next()) {
				qtdRegistros++;
				System.out.println(rs.getString(1));
				resultado = (new StringBuilder(String.valueOf(resultado)))
						.append(rs.getString(1)).append("#").toString();
			}
			if (qtdRegistros < 3)
			{
				inicio = 0;
				primeiraVezChamouMais = true;
			}
			desconectar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}

	public String acessarPropaganda() {
		String propaganda = null;
		try {
			conectar();
			PreparedStatement st = dbcon
					.prepareStatement("Select propaganda from propagandas");
			for (ResultSet rs = st.executeQuery(); rs.next();)
				propaganda = rs.getString(1);
			desconectar();
		} catch (Exception exception) {
		}
		return propaganda;
	}

	public static void main(String args[]) {
		PesquisarDAO p = new PesquisarDAO();
		//System.out.println(p.pesquisarAssociacoesWiki("POESIA INFORMATIZADA",
			//	false));
		System.out.println(p.acessarSite("CASTADELLI",
				false));
	}

	public String pesquisarDentroSite(String termo, String siteQueUsuarioEsta, boolean maisTermos) {
		
		String descricao = "";
		int qtdRegistros = 0;
		String resultado = "";
		try {
			conectar();
			PreparedStatement st;
			
			if ((!maisTermos) || (primeiraVezChamouMais)) {
				inicio = 0;
				fim = 3;
			String sql = (new StringBuilder("Select topico from "))
					.append( siteQueUsuarioEsta)
					.append(" where flag=1 and conteudo like ? order by dtCriacao DESC LIMIT ?,?").toString();
					
			
			st = dbcon
			.prepareStatement(sql);
			st.setString(1, (new StringBuilder(String.valueOf('%')))
						.append(termo.trim()).append('%').toString());
			st.setInt(2, inicio);
			st.setInt(3, fim);
			System.out.println(st);
			primeiraVezChamouMais = false;
			} else {
				qtdRegistros = 0;
				inicio += 3;
				fim += 2;
				String sql = (new StringBuilder("Select topico from "))
						.append( siteQueUsuarioEsta)
						.append(" where flag=1 and conteudo like ? order by dtCriacao DESC LIMIT ?,?").toString();
						
				
				st = dbcon
				.prepareStatement(sql);
				st.setString(1, (new StringBuilder(String.valueOf('%')))
							.append(termo.trim()).append('%').toString());
				st.setInt(2, inicio);
				st.setInt(3, fim);
				
				
				
			}
			
			
			ResultSet rs = st.executeQuery();
			rs.first();
			
			while (rs.next()) {
				qtdRegistros++;
				System.out.println(rs.getString(1));
				resultado = (new StringBuilder(String.valueOf(resultado)))
						.append(rs.getString(1)).append("#").toString();
			}
			if (qtdRegistros < 3)
			{
				inicio = 0;
				primeiraVezChamouMais = true;
			}
			
			
			
			desconectar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			descricao = "";
			return resultado;
		}
		return resultado;
	}

}
