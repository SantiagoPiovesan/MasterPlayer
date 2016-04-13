package br.senai.sc.model;

import java.util.Date;

public class Musica {

	private Integer idMusica;
	private String caminhoMusica;
	private String nomeMusica;
	private String artistaMusica;
	private String albumMusica;
	private String generoMusica;
	private Integer anoMusica;
	private Usuario usuarioMusica;
	private Date dataCadastroMusica;
	private Integer usuario_idUsuario;
	private Integer QntdReproducaoMusica;	
	private Integer indexMusica;
	
	public Integer getIdMusica() {
		return idMusica;
	}
	public void setIdMusica(Integer idMusica) {
		this.idMusica = idMusica;
	}
	public String getCaminhoMusica() {
		return caminhoMusica;
	}
	public void setCaminhoMusica(String caminhoMusica) {
		this.caminhoMusica = caminhoMusica;
	}
	public String getNomeMusica() {
		return nomeMusica;
	}
	public void setNomeMusica(String nomeMusica) {
		this.nomeMusica = nomeMusica;
	}
	public String getArtistaMusica() {
		return artistaMusica;
	}
	public void setArtistaMusica(String artistaMusica) {
		this.artistaMusica = artistaMusica;
	}
	public String getAlbumMusica() {
		return albumMusica;
	}
	public void setAlbumMusica(String albumMusica) {
		this.albumMusica = albumMusica;
	}	        
	public String getGeneroMusica() {
		return generoMusica;
	}
	public void setGeneroMusica(String generoMusica) {
		this.generoMusica = generoMusica;
	}
	public Integer getAnoMusica() {
		return anoMusica;
	}
	public void setAnoMusica(Integer anoMusica) {
		this.anoMusica = anoMusica;
	}
	public Usuario getUsuarioMusica() {
		return usuarioMusica;
	}
	public void setUsuarioMusica(Usuario usuarioMusica) {
		this.usuarioMusica = usuarioMusica;
	}
	public Date getDataCadastroMusica() {
		return dataCadastroMusica;
	}
	public void setDataCadastroMusica(Date dataCadastroMusica) {
		this.dataCadastroMusica = dataCadastroMusica;
	}
	public Integer getUsuario_idUsuario() {
		return usuario_idUsuario;
	}
	public void setUsuario_idUsuario(Integer usuario_idUsuario) {
		this.usuario_idUsuario = usuario_idUsuario;
	}
	public Integer getQntdReproducaoMusica() {
		return QntdReproducaoMusica;
	}
	public void setQntdReproducaoMusica(Integer qntdReproducaoMusica) {
		QntdReproducaoMusica = qntdReproducaoMusica;
	}
	public Integer getIndexMusica() {
		return indexMusica;
	}
	public void setIndexTabelaMusica(Integer indexMusica) {
		this.indexMusica = indexMusica;
	}
	
	
	
	
	
}
