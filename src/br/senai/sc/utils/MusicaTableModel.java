package br.senai.sc.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.senai.sc.model.Musica;

public class MusicaTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private static final int COL_ARTISTA = 0;
	private static final int COL_NOME = 1;
	private static final int COL_ALBUM = 2;
	private static final int COL_GENERO = 3;
	private static final int COL_ANO = 4;	

	private List<Musica> valores;

	// Esse é um construtor, que recebe a nossa lista de clientes
	public MusicaTableModel(List<Musica> valores) {
		this.valores = new ArrayList<Musica>(valores);
	}

	public int getRowCount() {
		// Quantas linhas tem sua tabela? Uma para cada item da lista.
		return valores.size();
	}

	public int getColumnCount() {
		// Quantidade de colunas na tabela
		return 5;
	}

	public String getColumnName(int column) {
		// Qual é o nome das nossas colunas?
		String retorno = "";
		switch (column) {		
		case COL_NOME:
			retorno = "Nome";
			break;			
		case COL_ARTISTA:
			retorno = "Artista";
			break;
		case COL_ALBUM:
			retorno = "Album";
			break;
		case COL_GENERO:
			retorno = "Gênero";
			break;
		case COL_ANO:
			retorno = "Ano";
			break;
		default:
			retorno = null;
			break;
		}
		
		return retorno;
	}

	public Object getValueAt(int row, int column) {
		// Precisamos retornar o valor da coluna column e da linha row.
		Musica musica = valores.get(row);
		Object retorno;
		switch (column) {		
		case COL_NOME:
			retorno = musica.getNomeMusica();
			break;			
		case COL_ARTISTA:
			retorno = musica.getArtistaMusica();
			break;
		case COL_ALBUM:
			retorno = musica.getAlbumMusica();
			break;
		case COL_GENERO:
			retorno = musica.getGeneroMusica();
			break;
		case COL_ANO:
			retorno = musica.getAnoMusica();
			break;
		default:
			retorno = null;
			break;
		}
		
		return retorno;

	}	

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Musica musica = valores.get(rowIndex);
		// Vamos alterar o valor da coluna columnIndex na linha rowIndex com o
		// valor aValue passado no parâmetro.
		// Note que vc poderia alterar 2 campos ao invés de um só.
		
		switch (columnIndex) {		
		case COL_NOME: musica.setNomeMusica(aValue.toString());			
			break;
		case COL_ARTISTA: musica.setArtistaMusica(aValue.toString());			
			break;
		case COL_ALBUM: musica.setAlbumMusica(aValue.toString());			
			break;
		case COL_GENERO: musica.setGeneroMusica(aValue.toString());			
			break;
		case COL_ANO: musica.setAnoMusica(Integer.parseInt(aValue.toString()));			
			break;
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
	public Musica get(int is) {
		return valores.get(is);
	}
	
}
