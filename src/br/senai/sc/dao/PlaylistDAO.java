package br.senai.sc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.senai.sc.model.ConnectionUtil;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;

public class PlaylistDAO {

	// Utilizado para o singleton
	private static PlaylistDAO instance;
	// Conexão com o banco
	private java.sql.Connection con = ConnectionUtil.getConnection();
	PreparedStatement stmt;	
	ResultSet rs;
	String query = "";
	
	// Utilizar somente enquanto nao houver implementacao com o BD
	private ArrayList<Playlist> listaPlaylists = new ArrayList<Playlist>();
	// Singleton
	public static PlaylistDAO getInstance(){
		if (instance == null){
			instance = new PlaylistDAO();
		}
		return instance;
	}
	
	// Inserir playlist no BD
	public void inserirPlaylist(Playlist playlist) throws SQLException{		
		try {
			query = "INSERT INTO playlist (nomePlaylist, usuario_idUsuario) VALUES (?, ?)";					
			stmt = con.prepareStatement(query);
			stmt.setString(1, playlist.getNomePlaylist());			
			stmt.setInt(2, playlist.getUsuario_idUsuario());			
			stmt.execute();
			con.commit();			
			
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}
	
	// Listar playlists do usuario logado - DISPLAY
//	public ArrayList<Playlist> listaPlaylists(Usuario usuario) {		
//		try {
//			this.listaPlaylists = new ArrayList<Playlist>();
//			query = "SELECT * FROM playlist WHERE usuario_idUsuario = ?";			
//			stmt = con.prepareStatement(query);
//			stmt.setInt(1, usuario.getIdUsuario());
//			rs = stmt.executeQuery();				
//			while (rs.next()) {
//				Playlist m = new Playlist();
//				m.setNomePlaylist(rs.getString("nomePlaylist"));
//				m.setIdPlaylist(rs.getInt("idPlaylist"));				
//				this.listaPlaylists.add(m);
//			}
//				
//		} catch (SQLException e) {		
//			e.printStackTrace();
//		}		
//		return this.listaPlaylists;		
//	}
	
	// Listar playlists do usuario logado - GERENCIAR PLAYLIST
	public ArrayList<Playlist> listaPlaylistsGerenciamento(Usuario usuario) {
		try {
			this.listaPlaylists = new ArrayList<Playlist>();
			query = "SELECT * FROM playlist WHERE usuario_idUsuario = ? and NomePlayList <> ?";
			stmt = con.prepareStatement(query);			
			stmt.setInt(1, usuario.getIdUsuario());
			stmt.setString(2, "Todas as músicas");
			rs = stmt.executeQuery();	
			while (rs.next()) {
				Playlist m = new Playlist();
				m.setNomePlaylist(rs.getString("nomePlaylist"));
				m.setIdPlaylist(rs.getInt("idPlaylist"));
				this.listaPlaylists.add(m);
			}

		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return this.listaPlaylists;	
	}

//	// Listar musicas da playlist
//	public ArrayList<Musica> musicasPlaylist() {		
//		try {
//			this.listaMusicasPlaylist = new ArrayList<Musica>();
//			query = "SELECT m.* FROM musica_has_playlist mp,musica m WHERE mp.musica_idmusica = m.idmusica AND mp.playlist_idplaylist = ?";
//			java.sql.Statement stmt = con.createStatement();				
//			ResultSet rs = stmt.executeQuery(query);
//
//			while (rs.next()) {
//				Playlist m = new Playlist();
//				//Usuario u = new Usuario();
//				m.setNomePlaylist(rs.getString("nome"));
//				//u.setApelidoUsuario(rs.getString("apelido"));
//				//m.setUsuarioPlaylist(rs.getString("usuario"));
//
//
//				this.listaPlaylists.add(m);
//			}
//
//		} catch (SQLException e) {		
//			e.printStackTrace();
//		}
//		//System.out.println(this.listaPlaylists.get(0).getNomePlaylist());
//		return this.listaMusicasPlaylist;		
//	}

	// EDITAR NOME PLAYLIST
	public void editarNomePlaylist(Playlist playlist, String novoNome) throws SQLException {
		try {
			query = "UPDATE playlist SET nomePlayList = ? WHERE idPlaylist = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, novoNome);
			stmt.setInt(2, playlist.getIdPlaylist());			
			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}

	}
	
	// EXCLUIR PLAYLIST
	public void excluirPlayList(Playlist playlist, Usuario usuario) throws SQLException {
		try {
			query = "DELETE FROM PLAYLIST WHERE idPlayList = ? AND Usuario_IdUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, playlist.getIdPlaylist());			
			stmt.setInt(2, usuario.getIdUsuario());			
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}
}
