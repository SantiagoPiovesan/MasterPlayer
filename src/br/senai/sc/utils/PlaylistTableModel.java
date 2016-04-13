package br.senai.sc.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sc.model.Playlist;

public class PlaylistTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int COL_NOME = 0;
	//private static final int COL_IDPLAYLIST = 1;

	private List<Playlist> valores;

	// Esse é um construtor, que recebe a nossa lista de playlists
	public PlaylistTableModel(ArrayList<Playlist> arrayList) {
		this.valores = new ArrayList<Playlist>(arrayList);
	}

	public int getRowCount() {
		// Quantas linhas tem sua tabela? Uma para cada item da lista.
		return valores.size();
	}

	public int getColumnCount() {
		// Quantas colunas tem a tabela? Nesse exemplo, só 4.
		return 1;
	}

	public String getColumnName(int column) {
		// Qual é o nome das nossas colunas?
		String retorno = "";
		switch (column) {
		case COL_NOME:
			retorno = "Nome";
			break;
//		case COL_IDPLAYLIST:
//			retorno = "ID";
//			break;
		default:
			retorno = "";
		}

		return retorno;
	}

	public Object getValueAt(int row, int column) {		
		Playlist playlist = valores.get(row);
		Object retorno = null;
		switch (column) {
		case COL_NOME:
			retorno = playlist.getNomePlaylist();
			break;
//		case COL_IDPLAYLIST:
//			retorno = playlist.getIdPlaylist();
//			break;
		default:
			retorno = null;
		}

		return retorno;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Playlist playlist = valores.get(rowIndex);		
		switch (columnIndex) {
		case COL_NOME:
			playlist.setNomePlaylist(aValue.toString());
			break;
//		case COL_IDPLAYLIST:
//			playlist.setIdPlaylist(Integer.parseInt(aValue.toString()));
//			break;
		default:
			break;
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		// Qual a classe das nossas colunas? Como estamos exibindo texto, é
		// string.
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// Indicamos se a célula da rowIndex e da columnIndex é editável. Nossa
		// tabela toda é.
		return true;
	}

	// Já que esse tableModel é de clientes, vamos fazer um get que retorne um
	// objeto cliente inteiro.
	// Isso elimina a necessidade de chamar o getValueAt() nas telas.
	public Playlist get(int row) {
		return valores.get(row);
	}
}
