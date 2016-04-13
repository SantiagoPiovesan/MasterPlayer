package br.senai.sc.controller;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.senai.sc.dao.PlaylistDAO;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;



public class PlaylistController {
	
	private static Integer TAMANHO_MAXIMO_NOME = 20;

	// Controle dos dados inseridos pelo usuario
	public boolean verificaDados(Playlist playlist) throws Exception {
		return true; // VERIFICAR NOME DA PLAYLIST, POR EXEMPLO			
	}

	public void inserirPlaylist(Playlist playlist) throws HeadlessException, Exception{	
		try {
			if (playlist.getNomePlaylist().equals("")) {
				throw new Exception("ERRO: A Playlist deve possuir um NOME!");
			}	
			if (playlist.getNomePlaylist().length() > TAMANHO_MAXIMO_NOME) {
				throw new Exception("ERRO: ERRO: O NOME da PLAYLIST deve ter no máximo 20 caracteres!");
			}
			if (verificaDados(playlist)) {
					PlaylistDAO.getInstance().inserirPlaylist(playlist);	
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
 	
	}


	public ArrayList<Playlist> listaPlaylist(Usuario usuario){		
		//return PlaylistDAO.getInstance().listaPlaylists(usuario);
		return PlaylistDAO.getInstance().listaPlaylistsGerenciamento(usuario);
	}
	
	public void excluirPlaylist(Playlist playlistSelecionada, Usuario usuario) throws SQLException {
		PlaylistDAO.getInstance().excluirPlayList(playlistSelecionada, usuario);
		
	}
	
	public void editarNomePlaylist(Playlist playlistSelecionada, String novoNome) throws HeadlessException, Exception {
		if (verificaDados(playlistSelecionada)) {
			PlaylistDAO.getInstance().editarNomePlaylist(playlistSelecionada, novoNome);	
		} else {
			JOptionPane.showMessageDialog(null, "Dados incorretos");
		}
				
	}
}
