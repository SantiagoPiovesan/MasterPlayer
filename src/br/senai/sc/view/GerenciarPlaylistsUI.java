package br.senai.sc.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import br.senai.sc.controller.MusicaController;
import br.senai.sc.controller.PlaylistController;
import br.senai.sc.model.Musica;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;
import br.senai.sc.utils.MusicaTableModel;
import br.senai.sc.utils.PlaylistTableModel;

public class GerenciarPlaylistsUI extends JInternalFrame {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfEscreverNovoNome;
	private JTextField jtfCriarNovoNome;
	private JTable jtListaPlaylists;
	private JTable jtMusicaPlaylist;
	private PlaylistController verificaDadosPlaylist = new PlaylistController();
	private Playlist todasAsMusicas = new Playlist("Todas as Músicas"); // Playlist padrao dos usuarios
	private JTable jtTodasAsMusicas;
	private JComboBox<Playlist> jcbPlaylists;	
	private DefaultComboBoxModel<Playlist> modelPlaylist;
	private ArrayList<Playlist> listaPlaylist;
	private Musica musicaSelecionada;	
	private JScrollPane jspListaPlaylists;
	private JScrollPane jspMusicasPlaylist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GerenciarPlaylistsUI frame = new GerenciarPlaylistsUI(null);
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
	public GerenciarPlaylistsUI(final Usuario usuario) {
		setClosable(true);		

		setTitle("Gerenciar Playlists");
		setBounds(100, 100, 800, 500);

		JLabel jlListaDePlaylists = new JLabel("Lista de Playlists:");

		JLabel jlAlterarNome = new JLabel("Editar Playlist:");

		jtfEscreverNovoNome = new JTextField();
		jtfEscreverNovoNome.setColumns(10);

		JButton jbExcluirPlaylist = new JButton("Excluir Playlist");
		jbExcluirPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {					
					Playlist playlistSelecionada = new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)).get(jtListaPlaylists.getSelectedRow());								
					PlaylistController pc = new PlaylistController();
					pc.excluirPlaylist(playlistSelecionada, usuario);
					jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
					jtfEscreverNovoNome.setText("");
					jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
					listaPlaylist = new PlaylistController().listaPlaylist(usuario);
					modelPlaylist.removeAllElements();
					for (Playlist playlist2 : listaPlaylist) {
						modelPlaylist.addElement(playlist2);
					}
					jcbPlaylists.setModel(modelPlaylist);
				} catch (NullPointerException e) {					
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Selecionar playlist desejada!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Atualiza tabela de musicas da playlist
				jtMusicaPlaylist = new JTable();
				jspMusicasPlaylist.setViewportView(jtMusicaPlaylist);
			}
		});

		JButton jbAlterar = new JButton("Alterar");
		jbAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					// Playlist selecionada
					Playlist playlistSelecionada = new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)).get(jtListaPlaylists.getSelectedRow());

					// Novo nome preenchido
					String novoNome = jtfEscreverNovoNome.getText();			

					// Verifica dados da playlist
					verificaDadosPlaylist.editarNomePlaylist(playlistSelecionada, novoNome);
				} catch (NullPointerException e) { e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) { JOptionPane.showMessageDialog(null, "Selecionar playlist desejada!");
				} catch (SQLException e) { e.printStackTrace();
				} catch (HeadlessException e) {	e.printStackTrace();
				} catch (Exception e) {	e.printStackTrace(); }
				
				jtfEscreverNovoNome.setText("");
				jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
				listaPlaylist = new PlaylistController().listaPlaylist(usuario);
				modelPlaylist.removeAllElements();
				for (Playlist playlist1 : listaPlaylist) {
					modelPlaylist.addElement(playlist1);
				}
				jcbPlaylists.setModel(modelPlaylist);
			}
		});

		// CRIAR PLAYLIST
		JLabel jlCriarNovaPlaylist = new JLabel("Criar Playlist:");

		jtfCriarNovoNome = new JTextField(null);
		jtfCriarNovoNome.setColumns(10);
		
		JButton btnCriar = new JButton("Criar");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Instancia Playlist
				Playlist playlist = new Playlist();

				// Define atributos da playlist
				playlist.setNomePlaylist(jtfCriarNovoNome.getText());							
				playlist.setUsuario_idUsuario(usuario.getIdUsuario());

				// Insere muscas na playlist
				try {
					verificaDadosPlaylist.inserirPlaylist(playlist);
				} catch (Exception e) {					
					e.printStackTrace();
				}
				
				jtfCriarNovoNome.setText("");
				jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
				listaPlaylist = new PlaylistController().listaPlaylist(usuario);
				modelPlaylist.removeAllElements();
				for (Playlist playlist2 : listaPlaylist) {
					modelPlaylist.addElement(playlist2);
				}
				jcbPlaylists.setModel(modelPlaylist);
			}
		});
		
		jspListaPlaylists = new JScrollPane();
		
		jspMusicasPlaylist = new JScrollPane();
		
		JLabel jlMusicasPlaylist = new JLabel("M\u00FAsicas da Playlist:");
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.DARK_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JScrollPane jspTodasAsMusicas = new JScrollPane();
		
		JButton jbExcluirMusica = new JButton("Excluir M\u00FAsica");
		jbExcluirMusica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				try {
					Playlist playlistSelecionada = new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)).get(jtListaPlaylists.getSelectedRow());
					Musica musicaSelecionada = new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)).get(jtMusicaPlaylist.getSelectedRow());													
					MusicaController mc = new MusicaController();
					mc.excluirMusicaDaPlaylist(musicaSelecionada, playlistSelecionada, usuario);					
					jtMusicaPlaylist.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)));		
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Selecionar playlist e música desejada!");
				} catch (SQLException e) {					
					e.printStackTrace();
				}
				
			}
		});
		
		JButton jbAdicionar = new JButton("Adicionar");
		jbAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				try {
					Playlist playlistComboBox = (Playlist) jcbPlaylists.getSelectedItem();					
					MusicaController mc = new MusicaController();
					mc.inserirMusicaPlaylist(musicaSelecionada, playlistComboBox);					
				} catch (NullPointerException e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cadastre uma playlist!");
				} catch (ArrayIndexOutOfBoundsException e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Selecione a música!");
				} catch (SQLException e) {					
					e.printStackTrace();
				}				
				
			}
		});

		JLabel jlPlaylistSelecionada = new JLabel("Escolha a Playlist");


		// ComboBox de playlist 
		listaPlaylist = new PlaylistController().listaPlaylist(usuario);
		jcbPlaylists = new JComboBox<Playlist>();
		modelPlaylist = new DefaultComboBoxModel<Playlist>();
		for (Playlist playlist : listaPlaylist) {
			modelPlaylist.addElement(playlist);
		}
		jcbPlaylists.setModel(modelPlaylist);

		
		final GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(jspMusicasPlaylist, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(jlListaDePlaylists)
							.addGap(304))
						.addComponent(jspListaPlaylists, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(jlCriarNovaPlaylist)
								.addComponent(jlAlterarNome))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(jtfEscreverNovoNome, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
								.addComponent(jtfCriarNovoNome, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCriar, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
								.addComponent(jbAlterar, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbExcluirPlaylist, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addGap(2))
						.addComponent(jlMusicasPlaylist)
						.addComponent(jbExcluirMusica))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jlPlaylistSelecionada)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jcbPlaylists, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbAdicionar))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(jspTodasAsMusicas, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addComponent(jspTodasAsMusicas, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlPlaylistSelecionada)
						.addComponent(jbAdicionar)
						.addComponent(jcbPlaylists, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(60, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 447, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(jlListaDePlaylists)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jspListaPlaylists, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtfEscreverNovoNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jlAlterarNome)
								.addComponent(jbExcluirPlaylist)
								.addComponent(jbAlterar))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jtfCriarNovoNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCriar)
								.addComponent(jlCriarNovaPlaylist))
							.addGap(46)
							.addComponent(jlMusicasPlaylist)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jspMusicasPlaylist, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbExcluirMusica)
							.addGap(61))))
		);
		
		
		
		// Tabela de playlists
		jtListaPlaylists = new JTable();		
		jtListaPlaylists.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {					
					Playlist playlistSelecionada = new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)).get(jtListaPlaylists.getSelectedRow());
					// Tabela de musicas
					jtMusicaPlaylist = new JTable();
					jspMusicasPlaylist.setViewportView(jtMusicaPlaylist);	
					jtMusicaPlaylist.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)));			
					jtMusicaPlaylist.getColumnModel().getColumn(0).setResizable(false);
					jtMusicaPlaylist.getColumnModel().getColumn(0).setPreferredWidth(200);
					jtMusicaPlaylist.getColumnModel().getColumn(1).setResizable(false);
					jtMusicaPlaylist.getColumnModel().getColumn(1).setPreferredWidth(100);
					getContentPane().setLayout(groupLayout);
					jtfEscreverNovoNome.setText(playlistSelecionada.getNomePlaylist());
				} catch (IndexOutOfBoundsException e2) {
					
				}				
			}
		});
		jspListaPlaylists.setViewportView(jtListaPlaylists);		
		jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
		jtListaPlaylists.getColumnModel().getColumn(0).setResizable(false);
		jtListaPlaylists.getColumnModel().getColumn(0).setPreferredWidth(200);		
		getContentPane().setLayout(groupLayout);
		
		
		// Tabela com todas as musicas		
		jtTodasAsMusicas = new JTable();
		jtTodasAsMusicas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {				
					musicaSelecionada = new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, todasAsMusicas)).get(jtTodasAsMusicas.getSelectedRow());					
										
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}				
			}
		});		
		jtTodasAsMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, todasAsMusicas)));
		jspTodasAsMusicas.setViewportView(jtTodasAsMusicas);
		
		
		
		
		 
	}
}