package br.senai.sc.model;

import java.util.ArrayList;

public class Playlist {

	private Integer idPlaylist;
	private String nomePlaylist;
	private Integer usuario_idUsuario;
	private ArrayList<Musica> musicasPlaylist;	

	
	public Playlist() {		
		
	}
	public Playlist(String nomePlaylist) {		
		this.nomePlaylist = nomePlaylist;
	}
	
	public Integer getIdPlaylist() {
		return idPlaylist;
	}
	public void setIdPlaylist(Integer idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public String getNomePlaylist() {
		return nomePlaylist;
	}
	public void setNomePlaylist(String nomePlaylist) {
		this.nomePlaylist = nomePlaylist;
	}		
	public Integer getUsuario_idUsuario() {
		return usuario_idUsuario;
	}
	public void setUsuario_idUsuario(Integer usuario_idUsuario) {
		this.usuario_idUsuario = usuario_idUsuario;
	}
	public ArrayList<Musica> getMusicasPlaylist() {
		return musicasPlaylist;
	}
	public void setMusicasPlaylist(ArrayList<Musica> musicasPlaylist) {
		this.musicasPlaylist = musicasPlaylist;
	}
	
	public String toString(){
		return this.nomePlaylist;
	}
	
	
}
