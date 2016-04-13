package br.senai.sc.dao;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sc.controller.MusicaController;
import br.senai.sc.model.ConnectionUtil;
import br.senai.sc.model.Musica;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;

import com.mysql.jdbc.Statement;
import com.toedter.calendar.JDateChooser;

public class MusicaDAO {

	// Utilizado para o singleton
	private static MusicaDAO instance;
	// Conexão com o banco
	java.sql.Connection con = ConnectionUtil.getConnection();
	PreparedStatement stmt;
	ResultSet rs;
	String query = "";

	// Utilizar somente enquanto nao houver implementacao com o BD
	private ArrayList<Musica> listaMusicas = new ArrayList<Musica>();

	// Singleton
	public static MusicaDAO getInstance() {
		if (instance == null) {
			instance = new MusicaDAO();
		}
		return instance;
	}

	// INSERIR MUSICAS NO BD
	public void inserirMusica(Musica musica, Usuario usuario) throws Exception {
		// this.listaMusicas.add(musica); // Temporario

		try {
			// Insere musica no banco
			query = "INSERT INTO musica (nome, artista, album, genero, ano, dataCadastro, usuario_idUsuario) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			stmt = con.prepareStatement(query);			
			stmt.setString(1, musica.getNomeMusica());
			stmt.setString(2, musica.getArtistaMusica());
			stmt.setString(3, musica.getAlbumMusica());
			stmt.setString(4, musica.getGeneroMusica());
			stmt.setInt(5, musica.getAnoMusica());
			stmt.setDate(6, new Date(new java.util.Date().getTime()));
			stmt.setInt(7, musica.getUsuario_idUsuario());
			stmt.execute();
			con.commit();

			// Pega id da ultima musica cadastrada
			query = "SELECT MAX(idMusica) FROM musica";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				musica.setIdMusica(rs.getInt("MAX(idMusica)"));
			}
			
			// Define caminho da musica com o novo nome - prefixo contem ID da musica
			MusicaController mc = new MusicaController();
			musica.setCaminhoMusica(mc.prepareCopy(musica, usuario));
			query = "UPDATE MUSICA SET caminho = ? WHERE idMusica = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, musica.getCaminhoMusica());
			stmt.setInt(2, musica.getIdMusica());			
			stmt.executeUpdate();
			con.commit();

			// Pega id da playlist Todas as Músicas do usuario logado
			String nomePlaylist = "Todas as Músicas";
			int id = musica.getUsuarioMusica().getIdUsuario();
			query = "SELECT idPlaylist FROM playlist WHERE nomePlaylist = ? AND usuario_idUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, nomePlaylist);
			stmt.setInt(2, id);
			rs = stmt.executeQuery();
			Playlist playlist = new Playlist();
			while (rs.next()) {
				playlist.setIdPlaylist(rs.getInt("idPlaylist"));
			}

			// Popula tabela musica_has_playlist
			query = "INSERT INTO musica_has_playlist (musica_idMusica, playlist_idPlaylist) VALUES (?, ?)";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, musica.getIdMusica());
			stmt.setInt(2, playlist.getIdPlaylist());
			stmt.execute();
			con.commit();

			// Executa rollback se houver algum erro no insert
		} catch (SQLException | IOException e) {
			con.rollback();
			e.printStackTrace();
		}		
	}

	// LISTAS MUSICAS DO USUARIO LOGADO
	public ArrayList<Musica> listaMusicas(Usuario usuario) {
		try {
			this.listaMusicas = new ArrayList<Musica>();
			query = "SELECT * FROM musica WHERE usuario_idUsuario = ?";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, usuario.getIdUsuario());
			rs = stmt.executeQuery();
			while (rs.next()) {
				Musica m = new Musica();
				m.setIdMusica(rs.getInt("idMusica"));
				m.setCaminhoMusica(rs.getString("caminho"));
				m.setNomeMusica(rs.getString("nome"));
				m.setArtistaMusica(rs.getString("artista"));
				m.setAlbumMusica(rs.getString("album"));
				m.setAnoMusica(rs.getInt("ano"));
				m.setDataCadastroMusica(rs.getDate("dataCadastro"));				
				this.listaMusicas.add(m);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.listaMusicas;
	}

	// EDITAR MUSICA
	public void editar(Musica musicaSelecionada, Musica novosDadosMusica) throws SQLException {
		try {
			query = "UPDATE MUSICA SET nome = ?, artista = ?, album = ?, genero = ?, ano = ? WHERE idMusica = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, novosDadosMusica.getNomeMusica());
			stmt.setString(2, novosDadosMusica.getArtistaMusica());
			stmt.setString(3, novosDadosMusica.getAlbumMusica());
			stmt.setString(4, novosDadosMusica.getGeneroMusica());			
			stmt.setInt(5, novosDadosMusica.getAnoMusica());
			stmt.setInt(6, musicaSelecionada.getIdMusica());

			stmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}

	}

	// EXCLUIR MUSICA
	public void excluir(Integer id) throws SQLException {
		try {
			query = "DELETE FROM MUSICA WHERE idMusica = ?";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		}
	}

	// LISTAR MUSICAS DA PLAYLIST SELECIONADA
	public List<Musica> listaMusicasDaPlaylist(Usuario usuario, Playlist playlistSelecionada) {
		try {
			this.listaMusicas = new ArrayList<Musica>();
			query = "SELECT m.* FROM musica_has_playlist mp, musica m, playlist p, usuario u " +
					"WHERE mp.musica_idmusica = m.idmusica " +
					"AND (mp.playlist_idplaylist = p.idPlaylist AND p.nomePlaylist = ?) " +
					"AND (u.nomeUsuario = ? AND u.idusuario = m.usuario_idUsuario)";
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, playlistSelecionada.getNomePlaylist());
			stmt.setString(2, usuario.getNomeUsuario());			
			rs = stmt.executeQuery();
			int indexMusica = 0;
			while (rs.next()) {				
				Musica m = new Musica();
				m.setIdMusica(rs.getInt("idMusica"));
				m.setCaminhoMusica(rs.getString("caminho"));
				m.setNomeMusica(rs.getString("nome"));
				m.setArtistaMusica(rs.getString("artista"));
				m.setAlbumMusica(rs.getString("album"));
				m.setGeneroMusica(rs.getString("genero"));
				m.setAnoMusica(rs.getInt("ano"));
				m.setDataCadastroMusica(rs.getDate("dataCadastro"));				
				m.setIndexTabelaMusica(indexMusica);				
				indexMusica++;
				this.listaMusicas.add(m);
			}
		} catch (SQLException e) {e.printStackTrace();}
		return this.listaMusicas;
	}
	
	// RELATORIO MUSICAS ADICIONADAS
		public ArrayList<Musica> consultaMusicasAdicionadas(Usuario usuario, JDateChooser jdcDataInicial, JDateChooser jdcDataFinal) throws SQLException{
			try {
				this.listaMusicas = new ArrayList<Musica>();
//				Date dataInicial = new Date(jdcDataInicial.getDate().getTime());
//				Date dataFinal = new Date(jdcDataFinal.getDate().getTime());
				query = "SELECT * FROM musica WHERE usuario_idUsuario = ? AND dataCadastro BETWEEN ? AND ?";
				
				stmt = con.prepareStatement(query);
				stmt.setInt(1, usuario.getIdUsuario());
				stmt.setDate(2, new Date(jdcDataInicial.getDate().getTime()));
				stmt.setDate(3, new Date(jdcDataFinal.getDate().getTime()));				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Musica m = new Musica();
					m.setIdMusica(rs.getInt("idMusica"));
					m.setCaminhoMusica(rs.getString("caminho"));
					m.setNomeMusica(rs.getString("nome"));
					m.setArtistaMusica(rs.getString("artista"));
					m.setAlbumMusica(rs.getString("album"));
					m.setAnoMusica(rs.getInt("ano"));
					m.setDataCadastroMusica(rs.getDate("dataCadastro"));
					m.setUsuario_idUsuario(rs.getInt("usuario_idUsuario"));
					this.listaMusicas.add(m);
				}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			return this.listaMusicas;

		}
		
		// RELATORIO MUSICAS MAIS TOCADAS
        public ArrayList<Musica> consultaMaisTocadas(Usuario usuario, JDateChooser jdcDataInicial, JDateChooser jdcDataFinal) throws SQLException{
            try {
                this.listaMusicas = new ArrayList<Musica>();
//                Date dataInicial = new Date(jdcDataInicial.getDate().getTime());
//                Date dataFinal = new Date(jdcDataFinal.getDate().getTime());
                //Conta quantidade de musicas e organiza por ordem decrescente
                query = "SELECT Musica_IdMusica, count(*) FROM RegistroReproducao WHERE datareprod BETWEEN ? AND ? group by musica_idMusica ORDER BY count(*) DESC";
                stmt = con.prepareStatement(query);   
                stmt.setDate(1, new Date(jdcDataInicial.getDate().getTime()));
				stmt.setDate(2, new Date(jdcDataFinal.getDate().getTime()));				
                ResultSet rs1 = stmt.executeQuery();                
                while (rs1.next()) {
                	int musica_idMusica = rs1.getInt("musica_idMusica");
                	//Seleciona dados da musica de acordo com o ID da musica obtido na consulta anterior
                	query = "SELECT * FROM musica WHERE idMusica = ? AND usuario_idUsuario = ?";
                	stmt = con.prepareStatement(query);     
                	stmt.setInt(1, musica_idMusica);
                	stmt.setInt(2, usuario.getIdUsuario());
                    ResultSet rs2 = stmt.executeQuery();
                    while (rs2.next()) {
                    	Musica m = new Musica();
	                    m.setIdMusica(rs2.getInt("idMusica"));
	                    m.setCaminhoMusica(rs2.getString("caminho"));
	                    m.setNomeMusica(rs2.getString("nome"));
	                    m.setArtistaMusica(rs2.getString("artista"));
	                    m.setAlbumMusica(rs2.getString("album"));
	                    m.setAnoMusica(rs2.getInt("ano"));
	                    m.setDataCadastroMusica(rs2.getDate("dataCadastro"));
	                    m.setUsuario_idUsuario(rs2.getInt("usuario_idUsuario"));
	                    m.setQntdReproducaoMusica(rs1.getInt("count(*)"));
	                    this.listaMusicas.add(m);
                    }
                }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
            return this.listaMusicas;

        }

        // Excluir musica da playlist selecionada
        public void excluirDaPlaylist(Musica musicaSelecionada,	Playlist playlistSelecionada, Usuario usuario) throws SQLException {			
        	try {
        		query = "DELETE FROM musica_has_playlist WHERE Musica_idMusica = ? AND PlayList_idPlayList = ?";
        		stmt = con.prepareStatement(query);	
        		stmt.setInt(1, musicaSelecionada.getIdMusica());
        		stmt.setInt(2, playlistSelecionada.getIdPlaylist());
        		stmt.executeUpdate();
        		con.commit();
        	} catch (SQLException e) {
        		con.rollback();
        		e.printStackTrace();
        	}


        }

        // Excluir musica do banco
        public void excluirDoBanco(Musica musicaSelecionada,	Playlist playlistSelecionada, Usuario usuario) throws SQLException {
        	try {
        		query = "DELETE FROM musica WHERE idMusica= ?";						
        		stmt = con.prepareStatement(query);	
        		stmt.setInt(1, musicaSelecionada.getIdMusica());
        		stmt.executeUpdate();
        		con.commit();
        	} catch (SQLException e) {
        		con.rollback();
        		e.printStackTrace();
        	}	
        }
		
		// Inserir registro de reproducao da musica tocada
		public void inserirMusicaRegistroReproducao(Musica musica) throws SQLException {
			try {
				// Insere musica no registro de reproducao
				query = "INSERT INTO RegistroReproducao (dataReprod, Musica_idMusica) VALUES (?, ?)";
				stmt = con.prepareStatement(query);
				stmt.setDate(1, new Date(new java.util.Date().getTime()));
				stmt.setInt(2, musica.getIdMusica());
				stmt.execute();
				con.commit();
			} catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
			}
		}

		 //Inserir uma musica em uma Playlist
		public void inserirMusicaPlaylist(Musica musica, Playlist playlist) throws SQLException {
			try {
				query = "INSERT INTO musica_has_playlist (musica_IdMusica, Playlist_IdPlayList) VALUES (?, ?)";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, musica.getIdMusica());
				stmt.setInt(2, playlist.getIdPlaylist());
				stmt.execute();
				con.commit();
			}catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
			}
		}

}

