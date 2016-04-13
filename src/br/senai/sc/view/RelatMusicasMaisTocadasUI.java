package br.senai.sc.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import br.senai.sc.controller.MusicaController;
import br.senai.sc.model.Usuario;
import br.senai.sc.utils.MusicaTableModel_RMMT;

import com.toedter.calendar.JDateChooser;

public class RelatMusicasMaisTocadasUI extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable jtTabelaRelatorio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RelatMusicasMaisTocadasUI frame = new RelatMusicasMaisTocadasUI(null);
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
	public RelatMusicasMaisTocadasUI(final Usuario usuario) {
		
		final JDateChooser jdcDataInicial;
		final JDateChooser jdcDataFinal;
		jdcDataInicial = new JDateChooser();		
		jdcDataFinal = new JDateChooser();
		
		setClosable(true);
		setTitle("Relat\u00F3rio M\u00FAsicas Mais Tocadas");
		setBounds(100, 100, 600, 425);
		
		JLabel jlInformeAbaixo = new JLabel("Informe abaixo o per\u00EDodo desejado para gerar o relat\u00F3rio:");
		
		JLabel jlDataInicial = new JLabel("Data Inicial:");
		
		JLabel jlDataFinal = new JLabel("Data Final:");
		
		JButton jbGerarRelatorio = new JButton("Gerar Relat\u00F3rio");
		jbGerarRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					jtTabelaRelatorio.setModel(new MusicaTableModel_RMMT(new MusicaController().consultaMaistocadas(usuario, jdcDataInicial, jdcDataFinal)));
				} catch (SQLException e1) {					
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Selecione as datas");
					
				}
			}
		});
		
		JScrollPane jspTabelaRelatorio = new JScrollPane();
		
		JButton jbSair = new JButton("Sair");
		jbSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(jlInformeAbaixo)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(jlDataInicial)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jdcDataInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(17)
										.addComponent(jlDataFinal)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jdcDataFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jbGerarRelatorio))
									.addComponent(jspTabelaRelatorio, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 559, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(266)
							.addComponent(jbSair)))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jlInformeAbaixo)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(jlDataInicial)
							.addComponent(jlDataFinal))
						.addComponent(jdcDataInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jdcDataFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jbGerarRelatorio))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jspTabelaRelatorio, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(jbSair)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		jtTabelaRelatorio = new JTable();
		jtTabelaRelatorio.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},				
			},
			new String[] {
				"Nome", "Artista", "\u00C1lbum", "Ano"
			}
		));
		jspTabelaRelatorio.setViewportView(jtTabelaRelatorio);
		getContentPane().setLayout(groupLayout);

	}
}
