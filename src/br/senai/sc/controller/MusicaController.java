package br.senai.sc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import br.senai.sc.dao.MusicaDAO;
import br.senai.sc.model.Musica;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;

import com.toedter.calendar.JDateChooser;

public class MusicaController {
	
	private static Integer TAMANHO_MAXIMO_CAMPOS = 50;
	private static Integer TAMANHO_ANO = 4;
	private static Integer TAMANHO_MAXIMO_CAMINHO = 200;
	private int numMusica;	
	
	public int getNumMusica() {
		return numMusica;
	}

	public void setNumMusica(int numMusica) {
		this.numMusica = numMusica;
	}

	
	//----------------------------------------------------------------------------------
	// CONTROLES MUSICA
	//----------------------------------------------------------------------------------
		
	// Pega informações sobre a musica em reproducao
	public Musica musicaAtual(Usuario usuario, Integer totalMusicas, Playlist playlistSelecionada){		
		Musica musica = listaMusicasDaPlaylist(usuario, playlistSelecionada).get(numMusica);						
		return musica;
	}
	
	public void musicaInicial(Integer numMusicaInicial){		
		this.numMusica = numMusicaInicial;
	}
	
	public void musicaSeguinte(Integer totalMusicas){        
        this.numMusica++;        
        if ( this.numMusica > totalMusicas-1) {
            this.numMusica = 0;
        }
    }
	
	public void musicaAnterior(Integer totalMusicas){
        this.numMusica--;
        if ( this.numMusica < 0) {
            this.numMusica = totalMusicas - 1;
        }
    }
	
	//----------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------
	
	public void verificaDadosMusica(Musica musica, Usuario usuario) throws SQLException{
		try {
//			NOME
			if (musica.getNomeMusica().equals("")) {
				throw new Exception("ERRO: A música deve possuir um NOME!");
			}
			if (musica.getNomeMusica().length() > TAMANHO_MAXIMO_CAMPOS) {
				throw new Exception("ERRO: O NOME da MUSICA deve ter no máximo 50 caracteres!");
			}
//			ARTISTA
			if (musica.getArtistaMusica().equals("")) {
				throw new Exception("ERRO: A música deve possuir um ARTISTA!");
			}
			if (musica.getArtistaMusica().length() > TAMANHO_MAXIMO_CAMPOS) {
				throw new Exception("ERRO: O NOME do ARTISTA deve ter no máximo 50 caracteres!");
			}
//			ALBUM
			if (musica.getAlbumMusica().length() > TAMANHO_MAXIMO_CAMPOS) {
				throw new Exception("ERRO: O NOME do ÁLBUM deve ter no máximo 50 caracteres!");
			}
//			GENERO
			if (musica.getGeneroMusica().length() > TAMANHO_MAXIMO_CAMPOS) {
				throw new Exception("ERRO: O GÊNERO da MUSICA deve ter no máximo 50 caracteres!");
			}
//			ANO
			if (musica.getAnoMusica().toString().length() > TAMANHO_ANO) {
				throw new Exception("ERRO: O ANO de uma MÚSICA deve ter no máximo 4 caracteres! Ex: 2014.");
			}
//			CAMINHO
			if (musica.getCaminhoMusica().length() > TAMANHO_MAXIMO_CAMINHO) {
				throw new Exception("O NOME da MÚSICA é muito grande!");
			}
//			EXTENSAO
			musica.getCaminhoMusica().toLowerCase();
			if (!musica.getCaminhoMusica().contains(".mp3")) {
				throw new Exception("A música deve ser MP3!");
			}
//			INSERE MUSICA NO BD			
			MusicaDAO.getInstance().inserirMusica(musica, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	

	//----------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------
	
	public String prepareCopy(Musica musica, Usuario usuario) throws IOException {
		String novoCaminho = null;
		File srcArquivo = new File(musica.getCaminhoMusica());
		File dstArquivo = new File(usuario.getDiretorioUsuario() +musica.getIdMusica() +" - " +musica.getArtistaMusica() +" - " +musica.getNomeMusica() +".mp3");		
		if(copy(srcArquivo, dstArquivo, true)){
			novoCaminho = usuario.getDiretorioUsuario() +musica.getIdMusica() +" - " +musica.getArtistaMusica() +" - " +musica.getNomeMusica() +".mp3";
		}		
		return novoCaminho;
		

	}
	
	public boolean copy(File origem, File destino, boolean overwrite) throws IOException{ 
		Date date = new Date();
		if (destino.exists() && !overwrite){ 
//			System.err.println(destino.getName()+" já existe, ignorando..."); 
			return false; 
		} 
		FileInputStream fisOrigem = new FileInputStream(origem); 
		FileOutputStream fisDestino = new FileOutputStream(destino); 
		FileChannel fcOrigem = fisOrigem.getChannel();   
		FileChannel fcDestino = fisDestino.getChannel();   
		fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);   
		fisOrigem.close();   
		fisDestino.close(); 
		Long time = new Date().getTime() - date.getTime();
		return true;
	}
	

	//----------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------
	
	public ArrayList<Musica> listaMusica(Usuario usuario){
		return MusicaDAO.getInstance().listaMusicas(usuario);
	}

	public List<Musica> listaMusicasDaPlaylist(Usuario usuario, Playlist playlistSelecionada) {
		return MusicaDAO.getInstance().listaMusicasDaPlaylist(usuario, playlistSelecionada);		
	}
	
	public ArrayList<Musica> consultaMaistocadas(Usuario usuario, JDateChooser jdcDataInicial, JDateChooser jdcDataFinal) throws SQLException {
		return MusicaDAO.getInstance().consultaMaisTocadas(usuario, jdcDataInicial, jdcDataFinal);
		
	}
	public ArrayList<Musica> consultaAdicionadas(Usuario usuario, JDateChooser jdcDataInicial, JDateChooser jdcDataFinal) throws SQLException {
		return MusicaDAO.getInstance().consultaMusicasAdicionadas(usuario, jdcDataInicial, jdcDataFinal);
	}

	public void excluirMusicaDaPlaylist(Musica musicaSelecionada, Playlist playlistSelecionada, Usuario usuario) throws SQLException {
		if (playlistSelecionada.getNomePlaylist().equals("Todas as Músicas")) {
			//Excluir do bd
			MusicaDAO.getInstance().excluirDoBanco(musicaSelecionada, playlistSelecionada, usuario);
			File arquivoMusica = new File(usuario.getDiretorioUsuario() +musicaSelecionada.getIdMusica() +" - " 
					+musicaSelecionada.getArtistaMusica() +" - " +musicaSelecionada.getNomeMusica() +".mp3");			
			arquivoMusica.delete();
		} else {
			//Excluir da playlist
			MusicaDAO.getInstance().excluirDaPlaylist(musicaSelecionada, playlistSelecionada, usuario);	
		}
				
	}

	public void adicionarRegistroReproducao(Musica musica) throws SQLException {
		MusicaDAO.getInstance().inserirMusicaRegistroReproducao(musica);				
	}

	public void inserirMusicaPlaylist(Musica musicaSelecionada,	Playlist playlistSelecionada) throws SQLException {		
		MusicaDAO.getInstance().inserirMusicaPlaylist(musicaSelecionada, playlistSelecionada);
	}	

	public void editarNomePlaylist(Musica musicaSelecionada, Musica novosDadosMusica) throws SQLException {
		MusicaDAO.getInstance().editar(musicaSelecionada, novosDadosMusica);		
	}
}
