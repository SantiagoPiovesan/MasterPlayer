package br.senai.sc.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.senai.sc.controller.MusicaController;
import br.senai.sc.model.Musica;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;
import br.senai.sc.utils.MusicaTableModel;

public class GerenciarMusicasUI extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfAlterarNome;
	private JTextField jtfAlterarArtista;
	private JTextField jtfAlterarAlbum;
	private JTable jtListaMusicas;
	private JTextField jtfAlterarGenero;
	private JTextField jtfAlterarAno;
	private Playlist playlistPadrao = new Playlist("Todas as Músicas"); // Playlist padrao dos usuarios

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GerenciarMusicasUI frame = new GerenciarMusicasUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param usuario 
	 */
	public GerenciarMusicasUI(final Usuario usuario) {
		setClosable(true);
		
		final MusicaController verificaDadosMusica = new MusicaController();
		
		setTitle("Gerenciar M\u00FAsicas");
		setBounds(100, 100, 600, 500);
		
		JLabel jlMusicasCadastradas = new JLabel("M\u00FAsicas Cadastradas:");
		
		JScrollPane jspTabelaMusicas = new JScrollPane();
		
		JButton jbExcluir = new JButton("Excluir");
		jbExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				try {
					Musica musicaSelecionada = new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)).get(jtListaMusicas.getSelectedRow());													
					MusicaController mc = new MusicaController();
					mc.excluirMusicaDaPlaylist(musicaSelecionada, playlistPadrao, usuario);
					jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)));
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Selecione a música desejada!");
				}catch (SQLException e) {					
					e.printStackTrace();
				}				
				
			}
		});
		
		JButton jbAlterar = new JButton("Alterar");
		jbAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Musica selecionada
				Musica musicaSelecionada = new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)).get(jtListaMusicas.getSelectedRow());

				// Novo nome preenchido
				Musica novosDadosMusica = new Musica();
				
				novosDadosMusica.setNomeMusica(jtfAlterarNome.getText());
				novosDadosMusica.setArtistaMusica(jtfAlterarArtista.getText());
				novosDadosMusica.setAlbumMusica(jtfAlterarAlbum.getText());
				novosDadosMusica.setGeneroMusica(jtfAlterarGenero.getText());				
				novosDadosMusica.setAnoMusica(Integer.parseInt(jtfAlterarAno.getText()));
				
				// Verifica dados da musica
				try {
					verificaDadosMusica.editarNomePlaylist(musicaSelecionada, novosDadosMusica);
				} catch (SQLException e) {					
					e.printStackTrace();
				}
				
				jtfAlterarNome.setText("");
				jtfAlterarArtista.setText("");
				jtfAlterarAlbum.setText("");
				jtfAlterarGenero.setText("");
				jtfAlterarAno.setText("");				
				jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)));
			}
		});
		
		JLabel jlNome = new JLabel("Nome:");
		
		JLabel jlArtista = new JLabel("Artista:");
		
		JLabel jlAlbum = new JLabel("\u00C1lbum:");
		
		jtfAlterarNome = new JTextField();
		jtfAlterarNome.setColumns(10);
		
		jtfAlterarArtista = new JTextField();
		jtfAlterarArtista.setColumns(10);
		
		jtfAlterarAlbum = new JTextField();
		jtfAlterarAlbum.setColumns(10);
		
		JLabel jlExcluirMusicaSelecionada = new JLabel("Excluir M\u00FAsica Selecionada:");
		
		JLabel jlAlterarDadosDaMusicaSelecionada = new JLabel("Alterar Dados da M\u00FAsica Selecionada:");
		
		jtfAlterarGenero = new JTextField();
		jtfAlterarGenero.setColumns(10);
		
		jtfAlterarAno = new JTextField();
		jtfAlterarAno.setColumns(10);
		
		JLabel jtGenero = new JLabel("G\u00EAnero:");
		
		JLabel jtAno = new JLabel("Ano:");
		
		JLabel jtAlterarMusicaSelecionada = new JLabel("Aplicar Altera\u00E7\u00E3o:");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(jlMusicasCadastradas)
						.addComponent(jspTabelaMusicas, GroupLayout.PREFERRED_SIZE, 559, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(jlAlterarDadosDaMusicaSelecionada)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(jtGenero, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
										.addComponent(jlAlbum)
										.addComponent(jlArtista)
										.addComponent(jlNome)
										.addComponent(jtAno, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(jtfAlterarGenero, Alignment.LEADING)
											.addComponent(jtfAlterarAlbum, Alignment.LEADING)
											.addComponent(jtfAlterarArtista, Alignment.LEADING)
											.addComponent(jtfAlterarNome, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
										.addComponent(jtfAlterarAno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(jbAlterar)
								.addComponent(jtAlterarMusicaSelecionada, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
								.addComponent(jbExcluir)
								.addComponent(jlExcluirMusicaSelecionada))))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jlMusicasCadastradas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jspTabelaMusicas, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlAlterarDadosDaMusicaSelecionada)
						.addComponent(jlExcluirMusicaSelecionada))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfAlterarNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jlNome)
						.addComponent(jbExcluir))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtfAlterarArtista, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jlArtista))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtfAlterarAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jlAlbum))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtGenero)
								.addComponent(jtfAlterarGenero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtfAlterarAno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jtAno)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addComponent(jtAlterarMusicaSelecionada)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbAlterar)))
					.addGap(122))
		);
		
		
		
		jtListaMusicas = new JTable();
		jtListaMusicas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Musica musicaSelecionada = new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)).get(jtListaMusicas.getSelectedRow());
				jtfAlterarNome.setText(musicaSelecionada.getNomeMusica());
				jtfAlterarArtista.setText(musicaSelecionada.getArtistaMusica());
				jtfAlterarAlbum.setText(musicaSelecionada.getAlbumMusica());			
				jtfAlterarGenero.setText(musicaSelecionada.getGeneroMusica());
				jtfAlterarAno.setText(musicaSelecionada.getAnoMusica().toString());				
			}
		});
		jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)));
		jspTabelaMusicas.setViewportView(jtListaMusicas);
		getContentPane().setLayout(groupLayout);

	}
}
