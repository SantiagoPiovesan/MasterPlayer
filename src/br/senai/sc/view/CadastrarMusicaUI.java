package br.senai.sc.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import br.senai.sc.controller.MusicaController;
import br.senai.sc.model.Musica;
import br.senai.sc.model.Usuario;

public class CadastrarMusicaUI extends JInternalFrame {		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jlMusica;
	private MusicaController verificaDados = new MusicaController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					CadastrarMusicaUI frame = new CadastrarMusicaUI(null);
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
	public CadastrarMusicaUI(final Usuario usuario) {
		setTitle("Cadastrar M\u00FAsica");
		setBounds(100, 100, 362, 107);		
		
		JLabel jlAdicionarNovaMusica = new JLabel("Adicionar Nova M\u00FAsica:");

		JButton jbProcurar = new JButton("Procurar");
		jbProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Seletor de multiplos arquivos
				JFileChooser chooser = new JFileChooser("D:\\Músicas"); // CAMINHO DEFAULT DO SELETOR
				chooser.setMultiSelectionEnabled(true);
				chooser.showOpenDialog(null);				
				File[] files = chooser.getSelectedFiles();

				//Instancia todas as musicas selecionadas
				for (int i = 0; i < files.length; i++) {	
					Musica musica = new Musica();
					try {						
						//Leitor de tags
						AudioFile file = null;
						file = AudioFileIO.read(files[i]);
						org.jaudiotagger.tag.Tag tag = file.getTag();				
						file.getAudioHeader().getSampleRateAsNumber();
						
						//Define atributos de musica						
						musica.setCaminhoMusica(files[i].getAbsolutePath());
						musica.setNomeMusica(tag.getFirst(FieldKey.TITLE));
//						musica.setArtistaMusica(tag.getFirst(FieldKey.ARTIST));
						musica.setArtistaMusica(tag.getFirst(FieldKey.ALBUM_ARTIST));
						musica.setAlbumMusica(tag.getFirst(FieldKey.ALBUM));
						musica.setGeneroMusica(tag.getFirst(FieldKey.GENRE));
						musica.setAnoMusica(Integer.parseInt(tag.getFirst(FieldKey.YEAR)));
						musica.setUsuarioMusica(usuario);
						musica.setUsuario_idUsuario(usuario.getIdUsuario());
						jlMusica.setText("Adicionando " +tag.getFirst(FieldKey.TITLE));
						
						//Verifica dados da musica						
						verificaDados.verificaDadosMusica(musica, usuario);	
						
					} catch (CannotReadException | IOException | TagException
							| ReadOnlyFileException | InvalidAudioFrameException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						musica.setAnoMusica(0);
					}
				}
			}
		});


		JButton jbSair = new JButton("Sair");
		jbSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		jlMusica = new JLabel("");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(jlAdicionarNovaMusica)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbProcurar, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbSair, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addComponent(jlMusica))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlAdicionarNovaMusica)
						.addComponent(jbProcurar)
						.addComponent(jbSair))
					.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
					.addComponent(jlMusica)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);

	}
}
