package br.senai.sc.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import br.senai.sc.controller.MusicaController;
import br.senai.sc.controller.PlaylistController;
import br.senai.sc.model.Musica;
import br.senai.sc.model.Playlist;
import br.senai.sc.model.Usuario;
import br.senai.sc.utils.MusicaTableModel;
import br.senai.sc.utils.PlaylistTableModel;

public class DisplayUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable jtListaMusicas;
	private MusicaController musicaController = new MusicaController();	
	private JTable jtListaPlaylists;
	private AudioPlayer reproduzir = null;
	private JLabel jlNomeMusicaTocando;	
	private JLabel jlNomeArtistaTocando;
	private JLabel jlMusicas;
	private MusicaTableModel musicaTable = null;
	private Playlist playlistSelecionada = null;  
	private Musica musicaSelecionada = null;
	private Playlist playlistPadrao = new Playlist("Todas as Músicas"); // Playlist padrao dos usuarios
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayUI frame = new DisplayUI(null);
					frame.setVisible(true);
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param usuario2 
	 */
	
	public DisplayUI(final Usuario usuario) {		
		
		if (usuario == null) {
			JOptionPane.showMessageDialog(null, "TAIX ABRINDO NA TELA ERRADA, IXTOPÔ!");
			dispose();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			System.exit(0);	
		}
		
		
		setTitle("MasterPlayer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		
		JMenuBar jmbListaDeMenu = new JMenuBar();
		setJMenuBar(jmbListaDeMenu);
		
		JMenu jmMinhaConta = new JMenu("Minha Conta");
		jmMinhaConta.setFont(new Font("Arial", Font.BOLD, 14));
		jmbListaDeMenu.add(jmMinhaConta);
		
		JMenuItem jmiConfiguraes = new JMenuItem("Configurações");
		jmiConfiguraes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfiguracoesContaUI configContaUI = new ConfiguracoesContaUI(usuario);			
				
				configContaUI.setFocusable(true);
				configContaUI.moveToFront();
				configContaUI.requestFocus();
				getContentPane().add(configContaUI,0);
				try {
					configContaUI.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				configContaUI.setVisible(true);	
				configContaUI.addInternalFrameListener(new InternalFrameListener() {  
		            public void internalFrameClosed(InternalFrameEvent e) { 
//		            	dispose();
//						PrincipalUI principalUI = new PrincipalUI();
//						principalUI.setVisible(true);			            	
		            }  
		            public void internalFrameActivated(InternalFrameEvent arg0) {}
		            public void internalFrameClosing(InternalFrameEvent arg0) {}
		            public void internalFrameDeactivated(InternalFrameEvent arg0) {}
		            public void internalFrameDeiconified(InternalFrameEvent arg0) {}
		            public void internalFrameIconified(InternalFrameEvent arg0) {}
		            public void internalFrameOpened(InternalFrameEvent arg0) {}   
		        });
			}
		});
		jmiConfiguraes.setFont(new Font("Arial", Font.PLAIN, 12));
		jmMinhaConta.add(jmiConfiguraes);
		
		JMenuItem jmiSair = new JMenuItem("Sair");
		jmiSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				PrincipalUI principalUI = new PrincipalUI();
				principalUI.setVisible(true);
			}
		});
		jmiSair.setFont(new Font("Arial", Font.PLAIN, 12));
		jmMinhaConta.add(jmiSair);
		
		JMenu jmMinhasMsicas = new JMenu("M\u00FAsicas");
		jmMinhasMsicas.setFont(new Font("Arial", Font.BOLD, 14));
		jmbListaDeMenu.add(jmMinhasMsicas);
		
		JMenuItem jmiVerTodasAs = new JMenuItem("Cadastrar Novas Músicas");
		jmiVerTodasAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				
				CadastrarMusicaUI cadMusicaUI = new CadastrarMusicaUI(usuario);
				cadMusicaUI.setFocusable(true);
				cadMusicaUI.moveToFront();
				cadMusicaUI.requestFocus();
			    getContentPane().add(cadMusicaUI,0);
			    try {     
			    	cadMusicaUI.setSelected(true);
			    } catch (PropertyVetoException e) {
			     // TODO Auto-generated catch block
			     e.printStackTrace();
			    }
			    cadMusicaUI.setVisible(true);
			    cadMusicaUI.addInternalFrameListener(new InternalFrameListener() {  
			            public void internalFrameClosed(InternalFrameEvent e) { 
			            	jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)));			            	
			            }  
			            public void internalFrameActivated(InternalFrameEvent arg0) {}
			            public void internalFrameClosing(InternalFrameEvent arg0) {}
			            public void internalFrameDeactivated(InternalFrameEvent arg0) {}
			            public void internalFrameDeiconified(InternalFrameEvent arg0) {}
			            public void internalFrameIconified(InternalFrameEvent arg0) {}
			            public void internalFrameOpened(InternalFrameEvent arg0) {}   
			        });
			}
		});
		jmiVerTodasAs.setFont(new Font("Arial", Font.PLAIN, 12));
		jmMinhasMsicas.add(jmiVerTodasAs);
		
		JMenuItem jmiGerenciarMusicas = new JMenuItem("Gerenciar M\u00FAsicas");
		jmiGerenciarMusicas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GerenciarMusicasUI gerMusicasUI = new GerenciarMusicasUI(usuario);
			    gerMusicasUI.setFocusable(true);
			    gerMusicasUI.moveToFront();
			    gerMusicasUI.requestFocus();
			    getContentPane().add(gerMusicasUI,0);
			    try {     
			     gerMusicasUI.setSelected(true);
			    } catch (PropertyVetoException e) {			     
			     e.printStackTrace();
			    }
			    gerMusicasUI.setVisible(true);
			    gerMusicasUI.addInternalFrameListener(new InternalFrameListener() {  
			            public void internalFrameClosed(InternalFrameEvent e) { 
			            	jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
			            	jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistPadrao)));
			            }  
			            public void internalFrameActivated(InternalFrameEvent arg0) {}
			            public void internalFrameClosing(InternalFrameEvent arg0) {}
			            public void internalFrameDeactivated(InternalFrameEvent arg0) {}
			            public void internalFrameDeiconified(InternalFrameEvent arg0) {}
			            public void internalFrameIconified(InternalFrameEvent arg0) {}
			            public void internalFrameOpened(InternalFrameEvent arg0) {}   
			        });
			}
		});
		jmMinhasMsicas.add(jmiGerenciarMusicas);
		
		JMenu jmPlaylists = new JMenu("Playlists");
		jmPlaylists.setFont(new Font("Arial", Font.BOLD, 14));
		jmbListaDeMenu.add(jmPlaylists);
		
		JMenuItem jmiGerenciarPlaylists = new JMenuItem("Gerenciar Playlists");
		jmiGerenciarPlaylists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {						
				GerenciarPlaylistsUI gerPlaylistsUI = new GerenciarPlaylistsUI(usuario);
			    gerPlaylistsUI.setFocusable(true);
			    gerPlaylistsUI.moveToFront();
			    gerPlaylistsUI.requestFocus();
			    getContentPane().add(gerPlaylistsUI,0);
			    try {     
			     gerPlaylistsUI.setSelected(true);
			    } catch (PropertyVetoException e) {			     
			     e.printStackTrace();
			    }
			    gerPlaylistsUI.setVisible(true);
			    gerPlaylistsUI.addInternalFrameListener(new InternalFrameListener() {  
			            public void internalFrameClosed(InternalFrameEvent e) { 
			            	jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
			            	jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)));
			            }  
			            public void internalFrameActivated(InternalFrameEvent arg0) {}
			            public void internalFrameClosing(InternalFrameEvent arg0) {}
			            public void internalFrameDeactivated(InternalFrameEvent arg0) {}
			            public void internalFrameDeiconified(InternalFrameEvent arg0) {}
			            public void internalFrameIconified(InternalFrameEvent arg0) {}
			            public void internalFrameOpened(InternalFrameEvent arg0) {}   
			        });
			}
		});
		jmiGerenciarPlaylists.setFont(new Font("Arial", Font.PLAIN, 12));
		jmPlaylists.add(jmiGerenciarPlaylists);
		
		JMenu jmRelatrios = new JMenu("Relatórios");
		jmRelatrios.setFont(new Font("Arial", Font.BOLD, 14));
		jmbListaDeMenu.add(jmRelatrios);
		
		JMenuItem jmiMsicasMaisTocadas = new JMenuItem("Músicas Mais Tocadas");
		jmiMsicasMaisTocadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RelatMusicasMaisTocadasUI maisTocadas = new RelatMusicasMaisTocadasUI(usuario);				
				maisTocadas.setFocusable(true);
				maisTocadas.moveToFront();
				maisTocadas.requestFocus();
				getContentPane().add(maisTocadas,0);
				try {
					maisTocadas.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				maisTocadas.setVisible(true);
			}
		});
		jmiMsicasMaisTocadas.setFont(new Font("Arial", Font.PLAIN, 12));
		jmRelatrios.add(jmiMsicasMaisTocadas);
		
		JMenuItem jmiMsicasAdicionadasRecentemente = new JMenuItem("Últimas Músicas Adicionadas");
			jmiMsicasAdicionadasRecentemente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RelatMusicasAdicionadasUI adicionadas = new RelatMusicasAdicionadasUI(usuario);
					adicionadas.setFocusable(true);
					adicionadas.moveToFront();
					adicionadas.requestFocus();
					getContentPane().add(adicionadas,0);
					try {
						adicionadas.setSelected(true);
					} catch (PropertyVetoException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}	
					adicionadas.setVisible(true);
				}
			});
		
		jmiMsicasAdicionadasRecentemente.setFont(new Font("Arial", Font.PLAIN, 12));
		jmRelatrios.add(jmiMsicasAdicionadasRecentemente);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JProgressBar jpbProgressoMusica = new JProgressBar();
		
		jlNomeMusicaTocando = new JLabel("");
		jlNomeMusicaTocando.setHorizontalAlignment(SwingConstants.RIGHT);
		jlNomeMusicaTocando.setFont(new Font("Arial", Font.BOLD, 14));
		
		final JScrollPane jspTabelaMusicas = new JScrollPane();
		
		JSeparator jseparator = new JSeparator();
		
		jlMusicas = new JLabel("Todas as M\u00FAsicas");
		jlMusicas.setFont(new Font("Arial", Font.BOLD, 12));
		
		
		// BOTAO AVANCAR
		JButton jbNext = new JButton("");
		jbNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					reproduzir.close();									
					musicaController.musicaSeguinte(jtListaMusicas.getRowCount());
					jlNomeMusicaTocando.setText(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getNomeMusica());					
					jlNomeArtistaTocando.setText(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getArtistaMusica());
					reproduzir = new AudioPlayer(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getCaminhoMusica(), true);
					musicaController.adicionarRegistroReproducao(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada));
					reproduzir.start();	
				} catch (NullPointerException | SQLException e) {
					//e.printStackTrace();
				}
				
			}
		});
		jbNext.setIcon(new ImageIcon(DisplayUI.class.getResource("/br/senai/sc/images/display/Next.png")));		
		
		
		// BOTAO VOLTAR
		JButton jbPrevious = new JButton("");
		jbPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					reproduzir.close();					
					musicaController.musicaAnterior(jtListaMusicas.getRowCount());
					jlNomeMusicaTocando.setText(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getNomeMusica());
					jlNomeArtistaTocando.setText(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getArtistaMusica());
					reproduzir = new AudioPlayer(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getCaminhoMusica(), true);
					musicaController.adicionarRegistroReproducao(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada));
					reproduzir.start();
				} catch (NullPointerException | SQLException e) {
					//e.printStackTrace();
				}				
			}
		});
		jbPrevious.setIcon(new ImageIcon(DisplayUI.class.getResource("/br/senai/sc/images/display/Previous.png")));
		

		// BOTAO STOP
		JButton jbStop = new JButton("");
		jbStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					jlNomeMusicaTocando.setText("");
					jlNomeArtistaTocando.setText("");
					reproduzir.close();
					reproduzir = null;					
				} catch (NullPointerException e) {
					//e.printStackTrace();
				}				

			}
		});
		jbStop.setIcon(new ImageIcon(DisplayUI.class.getResource("/br/senai/sc/images/display/Stop.png")));
		
		
		// BOTAO PLAY
		JButton jbPlay = new JButton("");
		jbPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				Integer numMusica = null; //numero/ordem da musica na tabela de musicas				
				try {
					musicaTable = new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada));
					musicaSelecionada = musicaTable.get(jtListaMusicas.getSelectedRow());					
					numMusica = musicaSelecionada.getIndexMusica();					
					if(reproduzir != null){
						reproduzir.close();						
						reproduzir = null;
					}
				} catch (ArrayIndexOutOfBoundsException e) {		
					//e.printStackTrace();
					numMusica = 0;					
				}						
				
				
				// Se nenhuma musica estiver tocando, instancia uma nova
				if (reproduzir == null) {					
					try {						
						musicaController.musicaInicial(numMusica);
						musicaController.adicionarRegistroReproducao(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada));
						reproduzir = new AudioPlayer(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getCaminhoMusica(),true);
						jlNomeMusicaTocando.setText(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getNomeMusica());
						jlNomeArtistaTocando.setText(musicaController.musicaAtual(usuario, jtListaMusicas.getRowCount(), playlistSelecionada).getArtistaMusica());
						reproduzir.start();
					} catch (SQLException e) {						
						e.printStackTrace();
					} catch (IndexOutOfBoundsException e) {
						JOptionPane.showMessageDialog(null, "Playlist não possui músicas");
					} catch (IllegalThreadStateException e) {
						//musica ja esta em execucao
					}						
				}								
			}
		});
		jbPlay.setIcon(new ImageIcon(DisplayUI.class.getResource("/br/senai/sc/images/display/Play.png")));
		
		JLabel jlPlaylists = new JLabel("Playlists:");
		jlPlaylists.setFont(new Font("Arial", Font.BOLD, 12));
		
		JScrollPane jspTabelaPlaylists = new JScrollPane();
		
		jlNomeArtistaTocando = new JLabel("");
		jlNomeArtistaTocando.setHorizontalAlignment(SwingConstants.RIGHT);
		jlNomeArtistaTocando.setFont(new Font("Arial", Font.BOLD, 14));
		
		JButton jbTodasAsMusicas = new JButton("Todas as M\u00FAsicas");
		jbTodasAsMusicas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playlistSelecionada = playlistPadrao;
				jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)));
				jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
			}
		});
		final GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(jlMusicas)
								.addComponent(jspTabelaMusicas, GroupLayout.PREFERRED_SIZE, 759, GroupLayout.PREFERRED_SIZE))
							.addGap(55)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(jlPlaylists, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
								.addComponent(jspTabelaPlaylists, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
								.addComponent(jbTodasAsMusicas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(jpbProgressoMusica, GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(jseparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(593))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(jlNomeArtistaTocando, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jlNomeMusicaTocando, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
							.addGap(193)
							.addComponent(jbPrevious, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbStop, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbPlay, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbNext)
							.addGap(499)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(21)
					.addComponent(jlMusicas)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(jspTabelaMusicas, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(jseparator, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(jbTodasAsMusicas)
							.addGap(30)
							.addComponent(jlPlaylists, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jspTabelaPlaylists, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jpbProgressoMusica, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(jbNext, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jbPlay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jbStop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jbPrevious, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(jlNomeArtistaTocando, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jlNomeMusicaTocando, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
					.addContainerGap(33, GroupLayout.PREFERRED_SIZE))
		);
		
	
		jtListaPlaylists = new JTable();		
		jtListaPlaylists.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {					
					playlistSelecionada = new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)).get(jtListaPlaylists.getSelectedRow());					
					jtListaMusicas = new JTable();
					jspTabelaMusicas.setViewportView(jtListaMusicas);					
					jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)));			
					jtListaMusicas.getColumnModel().getColumn(0).setResizable(false);
					jtListaMusicas.getColumnModel().getColumn(0).setPreferredWidth(200);
					jtListaMusicas.getColumnModel().getColumn(1).setResizable(false);
					jtListaMusicas.getColumnModel().getColumn(1).setPreferredWidth(100);
					getContentPane().setLayout(gl_contentPane);
					jlMusicas.setText(playlistSelecionada.getNomePlaylist());
				} catch (IndexOutOfBoundsException e2) {}				
			}
		});
		// Bloco abaixo exibe todas as musicas sem que uma playlist esteja selecionada
		playlistSelecionada = playlistPadrao;
		jtListaMusicas = new JTable();
		jspTabelaMusicas.setViewportView(jtListaMusicas);
		jtListaMusicas.setModel(new MusicaTableModel(new MusicaController().listaMusicasDaPlaylist(usuario, playlistSelecionada)));		
		jtListaMusicas.getColumnModel().getColumn(0).setResizable(false);
		jtListaMusicas.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtListaMusicas.getColumnModel().getColumn(1).setResizable(false);
		jtListaMusicas.getColumnModel().getColumn(1).setPreferredWidth(100);
		getContentPane().setLayout(gl_contentPane);
		
		jspTabelaPlaylists.setViewportView(jtListaPlaylists);		
		jtListaPlaylists.setModel(new PlaylistTableModel(new PlaylistController().listaPlaylist(usuario)));
		jtListaPlaylists.getColumnModel().getColumn(0).setResizable(false);
		jtListaPlaylists.getColumnModel().getColumn(0).setPreferredWidth(200);		
		getContentPane().setLayout(gl_contentPane);
		
	}
}
