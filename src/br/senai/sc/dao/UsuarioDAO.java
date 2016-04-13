package br.senai.sc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.senai.sc.model.ConnectionUtil;
import br.senai.sc.model.Usuario;

public class UsuarioDAO {

	private static UsuarioDAO instance;

//	Conexão com o banco
	private java.sql.Connection con = ConnectionUtil.getConnection();
	PreparedStatement stmt;
	ResultSet rs;
	String query = "";

	public static UsuarioDAO getInstance(){
		if ( instance == null ){
			instance = new UsuarioDAO();
		}
		return instance;
	}

//	LOGIN USUARIO
	public boolean loginUsuario(Usuario usuario) throws SQLException{
		try {
			query = "SELECT apelidoUsuario FROM usuario WHERE apelidoUsuario = ? AND senhaUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, usuario.getApelidoUsuario());
			stmt.setString(2, usuario.getSenhaUsuario());				
			rs = stmt.executeQuery();

			// Se o select nao retornar nada, login esta incorreto
			if (!rs.next()) {					
				return false;
			}
		} catch (SQLException e) {				
			e.printStackTrace();
		}
		return true;
	}

//	CADASTRO DE USUARIOS
	public void cadastroUsuario(Usuario usuario) throws SQLException{
		try {		
			//Cadastra usuario
			query = "INSERT INTO usuario (nomeUsuario, emailUsuario, apelidoUsuario, senhaUsuario, diretorioUsuario) " +
					"VALUE(?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, usuario.getNomeUsuario());
			stmt.setString(2, usuario.getEmailUsuario());
			stmt.setString(3, usuario.getApelidoUsuario());
			stmt.setString(4, usuario.getSenhaUsuario());
			stmt.setString(5, usuario.getDiretorioUsuario());
			stmt.execute();
			con.commit();

			//Pega id do usuario cadastrado
			query = "SELECT idUsuario FROM usuario WHERE apelidoUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, usuario.getApelidoUsuario());
			rs = stmt.executeQuery();
			while (rs.next()) {
				usuario.setIdUsuario(rs.getInt("idUsuario"));
			}

			//Cria playlist Todas as Músicas do usuario
			query = "INSERT INTO playlist (NomePlayList, Usuario_idUsuario) VALUES (?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, "Todas as Músicas");
			stmt.setInt(2, usuario.getIdUsuario());				
			stmt.execute();
			con.commit();

		}  catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}

	public Usuario dadosUsuario(Usuario usuario) throws SQLException {
		query = "SELECT * FROM usuario WHERE apelidoUsuario = ? AND senhaUsuario = ?";
		stmt = con.prepareStatement(query);
		stmt.setString(1, usuario.getApelidoUsuario());
		stmt.setString(2, usuario.getSenhaUsuario());
		rs = stmt.executeQuery();
		while (rs.next()) {				
			usuario.setIdUsuario(rs.getInt("idUsuario"));
			usuario.setNomeUsuario(rs.getString("nomeUsuario"));
			usuario.setEmailUsuario(rs.getString("emailUsuario"));
			usuario.setApelidoUsuario(rs.getString("apelidoUsuario"));
			usuario.setSenhaUsuario(rs.getString("senhaUsuario"));
			usuario.setDiretorioUsuario(rs.getString("diretorioUsuario"));
		}

		return usuario;
	}

//	VERIRIFCAR SE O APELIDO JA ESTA EM USO
	public void verificaApelido(Usuario usuario) throws SQLException {
		try {
			query = "SELECT idUsuario FROM Usuario WHERE ApelidoUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, usuario.getApelidoUsuario());				
			rs = stmt.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "Usuario Disponivel!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	EDITAR APELIDO E SENHA
	public void editarUsuario(Usuario usuario) throws SQLException {
		try { 
			query = "UPDATE usuario SET ApelidoUsuario = ?, SenhaUsuario = ? WHERE idUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, usuario.getApelidoUsuario());
			stmt.setString(2, usuario.getSenhaUsuario());
			stmt.setInt(3, usuario.getIdUsuario());
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}

//	EXCLUIR CONTA	
	public void excluirConta(Usuario usuario) throws SQLException {
		try {
			query = "DELETE FROM usuario WHERE idUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, usuario.getIdUsuario());
			stmt.execute();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}


//	VERIFICAR SE O USUARIO JA FOI CADASTRADO
	public boolean verificaUsuarioExistente(Usuario usuario) throws SQLException {
		try {
			query = "SELECT idUsuario FROM usuario WHERE ApelidoUsuario = ? AND emailUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, usuario.getApelidoUsuario());
			stmt.setString(2, usuario.getEmailUsuario());			
			rs = stmt.executeQuery();

			//	Se o select nao retornar nada, o usuario pode ser cadastrado!
			while (!rs.next()) {					
				return true;
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
