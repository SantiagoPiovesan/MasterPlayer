package br.senai.sc.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import br.senai.sc.dao.UsuarioDAO;
import br.senai.sc.model.Usuario;

public class AcessoController {
	
	// CONSTANTES
	private static Integer TAMANHO_MAXIMO_APELIDO = 20;
	private static Integer TAMANHO_MINIMO_APELIDO = 3;
	private static Integer TAMANHO_SENHA = 6;

	// VALIDA LOGIN
	// ================================================================================
	public boolean validaLogin(Usuario usuario) throws Exception {
		// APELIDO
		try{
			if (usuario.getApelidoUsuario().equals("")) {
				throw new Exception("ERRO: O campo APELIDO deve ser preenchido!");
			}
			if (usuario.getApelidoUsuario().length() > TAMANHO_MAXIMO_APELIDO
					|| usuario.getApelidoUsuario().length() < TAMANHO_MINIMO_APELIDO) {
				throw new Exception("ERRO: O APELIDO deve ter de 3 a 20 caracteres!");
			}
			// SENHA
			if (usuario.getSenhaUsuario().equals("")) {
				throw new Exception("ERRO: O campo SENHA deve ser preenchido!");
			}
			if (usuario.getSenhaUsuario().length() > TAMANHO_SENHA
					|| usuario.getSenhaUsuario().length() < TAMANHO_SENHA) {
				throw new Exception("ERRO: O campo SENHA deve ter 6 caracteres!");
			}
			
			// Se nao houve nenhum erro, verifica usuario no banco
			boolean validacaoLogin = UsuarioDAO.getInstance().loginUsuario(usuario);
			if(!validacaoLogin){
				JOptionPane.showMessageDialog(null, "Login incorreto!");				
			}
			return validacaoLogin;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}	
	}

	// VALIDA CADASTRO
	// ================================================================================
	public boolean validaCadastro(Usuario usuario) throws Exception {
		// NOME
		try{
			if (usuario.getNomeUsuario().equals("")) {
				throw new Exception("ERRO: O campo NOME deve ser preenchido!");
			}
//			Pattern pt = Pattern.compile("/^[a-zA-Z ]$/");
//			Matcher mt = pt.matcher(usuario.getNomeUsuario());
//			if (!mt.find()) {
//				throw new Exception("ERRO: O NOME deve conter apenas letras!");
//			}
			if (usuario.getNomeUsuario().length() > TAMANHO_MAXIMO_APELIDO
					|| usuario.getNomeUsuario().length() < TAMANHO_MINIMO_APELIDO) {
				throw new Exception("ERRO: O NOME deve ter de 3 a 50 caracteres!");
			}
			if (usuario.getNomeUsuario().contains("1")
				|| usuario.getNomeUsuario().contains("2")
				|| usuario.getNomeUsuario().contains("3")
				|| usuario.getNomeUsuario().contains("4")
				|| usuario.getNomeUsuario().contains("5")
				|| usuario.getNomeUsuario().contains("6")
				|| usuario.getNomeUsuario().contains("7")
				|| usuario.getNomeUsuario().contains("8")
				|| usuario.getNomeUsuario().contains("9")
				|| usuario.getNomeUsuario().contains("0")) {
				throw new Exception("ERRO: O NOME não deve possuir números!");
			}
			
			// EMAIL
					Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
					Matcher m = p.matcher(usuario.getEmailUsuario());
					if (!m.find()) {
						throw new Exception("ERRO: Digite um EMAIL válido!");
					}
	
			// APELIDO
			if (usuario.getApelidoUsuario().equals("")) {
				throw new Exception("ERRO: O campo APELIDO deve ser preenchido!");
			}
			if (usuario.getApelidoUsuario().length() > TAMANHO_MAXIMO_APELIDO
					|| usuario.getApelidoUsuario().length() < TAMANHO_MINIMO_APELIDO) {
				throw new Exception("ERRO: O APELIDO deve ter de 3 a 20 caracteres!");
			}
			if (usuario.getApelidoUsuario().contains("1")
					|| usuario.getApelidoUsuario().contains("2")
					|| usuario.getApelidoUsuario().contains("3")
					|| usuario.getApelidoUsuario().contains("4")
					|| usuario.getApelidoUsuario().contains("5")
					|| usuario.getApelidoUsuario().contains("6")
					|| usuario.getApelidoUsuario().contains("7")
					|| usuario.getApelidoUsuario().contains("8")
					|| usuario.getApelidoUsuario().contains("9")
					|| usuario.getApelidoUsuario().contains("0")) {
				throw new Exception("ERRO: O APELIDO não deve possuir números!");
			}
	
			// SENHA
			if (usuario.getSenhaUsuario().equals("")) {
				throw new Exception("ERRO: O campo SENHA deve ser preenchido!");
			}
			if (usuario.getSenhaUsuario().length() > TAMANHO_SENHA
					|| usuario.getSenhaUsuario().length() < TAMANHO_SENHA) {
				throw new Exception("ERRO: O campo SENHA deve ter 6 caracteres!");
			}
			// Se nao houve nenhum erro, cadastra no banco			
			UsuarioDAO.getInstance().cadastroUsuario(usuario);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}

	// VALIDA CONFIGURACOES DE CONTA
	// ================================================================================
	// APELIDO
	public void verificaAlteracao(Usuario usuario) throws Exception {
		try {
//			APELIDO
			if (usuario.getApelidoUsuario().equals("")) {
				throw new Exception("ERRO: O campo APELIDO deve ser preenchido!");
			}
			if (usuario.getApelidoUsuario().length() > TAMANHO_MAXIMO_APELIDO) {
				throw new Exception("ERRO: O APELIDO deve ter no máximo 10 caracteres!");
			}
			if (usuario.getApelidoUsuario().length() < TAMANHO_MINIMO_APELIDO) {
				throw new Exception("ERRO: O APELIDO deve ter no mínimo 3 caracteres!");
			}
			if (usuario.getApelidoUsuario().contains("1")
					|| usuario.getApelidoUsuario().contains("2")
					|| usuario.getApelidoUsuario().contains("3")
					|| usuario.getApelidoUsuario().contains("4")
					|| usuario.getApelidoUsuario().contains("5")
					|| usuario.getApelidoUsuario().contains("6")
					|| usuario.getApelidoUsuario().contains("7")
					|| usuario.getApelidoUsuario().contains("8")
					|| usuario.getApelidoUsuario().contains("9")
					|| usuario.getApelidoUsuario().contains("0")) {
				throw new Exception("ERRO: O APELIDO não deve possuir números!");
			}	
//			SENHA
			if (usuario.getSenhaUsuario().equals("")) {
				throw new Exception("ERRO: O campo SENHA deve ser preenchido!");
			}
			if (usuario.getSenhaUsuario().length() > TAMANHO_SENHA
					|| usuario.getSenhaUsuario().length() < TAMANHO_SENHA) {
				throw new Exception("ERRO: O campo SENHA deve ter 6 caracteres!");
			}
			UsuarioDAO.getInstance().verificaApelido(usuario);
			UsuarioDAO.getInstance().editarUsuario(usuario);
			JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	// CRIA DIRETORIO DE MUSICAS DO USUARIO
		// ================================================================================
		public String criaDiretorioUsuario(Usuario usuario) {
			String caminhoDir = "musicas\\" +usuario.getApelidoUsuario() +"\\";
			File diretorio = new File(caminhoDir);
			diretorio.mkdir();
			return caminhoDir;
		}

		// METODOS DE CONTROLE
		// ================================================================================
		public Usuario dadosDoUsuario(Usuario usuario) throws SQLException {
			return UsuarioDAO.getInstance().dadosUsuario(usuario);
		}
		
		public void excluirConta(Usuario usuario) throws SQLException, IOException {
			UsuarioDAO.getInstance().excluirConta(usuario);
			File diretorioUsuario = new File(usuario.getDiretorioUsuario());						
			FileUtils.deleteDirectory(diretorioUsuario);
		}
}
