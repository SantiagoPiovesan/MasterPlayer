package br.senai.sc.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.senai.sc.controller.AcessoController;
import br.senai.sc.model.Usuario;

public class ConfiguracoesContaUI extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfEscreverNovoApelido;
	private JTextField jtfMostraNome;
	private JTextField jtfMostraEmail;
	private JTextField jtfMostraApelido;
	private JPasswordField jpsNovaSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfiguracoesContaUI frame = new ConfiguracoesContaUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfiguracoesContaUI(final Usuario usuario) {
		setClosable(true);
		setTitle("Configura\u00E7\u00F5es de Conta");
		setBounds(100, 100, 380, 385);
		
		JLabel jlNome = new JLabel("Nome:");
		
		JLabel jlEmail = new JLabel("E-mail:");
		
		JLabel jlApelido = new JLabel("Apelido:");
		
		JLabel jlNovoApelido = new JLabel("Novo Apelido:");
		
		jtfEscreverNovoApelido = new JTextField();
		jtfEscreverNovoApelido.setColumns(10);
		
		JLabel jlNovaSeha = new JLabel("Nova Senha:");
		
		JButton jbSalvar = new JButton("Salvar");
		jbSalvar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
//				ALTERAR
				usuario.setApelidoUsuario(jtfEscreverNovoApelido.getText());
				usuario.setSenhaUsuario(jpsNovaSenha.getText());
				AcessoController ac = new AcessoController();
				try {
					ac.verificaAlteracao(usuario);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JLabel jlDesejaExcluirSuaConta = new JLabel("Deseja excluir sua conta?");
		
		JButton jbExcluirConta = new JButton("Excluir Conta");
		jbExcluirConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				EXCLUIR
				Integer op = JOptionPane.showConfirmDialog(null, "Deseja excluir sua conta?", "Excluir Conta", JOptionPane.YES_NO_CANCEL_OPTION);
				if (op == 0) {
					AcessoController ac = new AcessoController();
					try {
						ac.excluirConta(usuario);
						dispose();
						setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						System.exit(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		
		jtfMostraNome = new JTextField();
		jtfMostraNome.setEnabled(false);
		jtfMostraNome.setColumns(10);
		jtfMostraNome.setText(usuario.getNomeUsuario());
		
		jtfMostraEmail = new JTextField();
		jtfMostraEmail.setEnabled(false);
		jtfMostraEmail.setColumns(10);
		jtfMostraEmail.setText(usuario.getEmailUsuario());
		
		jtfMostraApelido = new JTextField();
		jtfMostraApelido.setEnabled(false);
		jtfMostraApelido.setColumns(10);
		jtfMostraApelido.setText(usuario.getApelidoUsuario());
		
		JLabel lblInformaes = new JLabel("Informa\u00E7\u00F5es:");
		
		JLabel lblAlterarDados = new JLabel("Alterar Dados:");
		
		jpsNovaSenha = new JPasswordField();
		
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
							.addComponent(lblAlterarDados))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(12)
										.addComponent(jlApelido)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jtfMostraApelido, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
									.addGroup(groupLayout.createSequentialGroup()
										.addContainerGap()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblInformaes)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(9)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
													.addComponent(jlEmail)
													.addComponent(jlNome))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
													.addComponent(jtfMostraNome, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
													.addComponent(jtfMostraEmail, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))))))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(jlNovoApelido)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jtfEscreverNovoApelido, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
							.addGap(121)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jlNovaSeha)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jpsNovaSenha, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
					.addGap(121))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(109)
					.addComponent(jbSalvar, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jbSair)
					.addContainerGap(186, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jlDesejaExcluirSuaConta)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jbExcluirConta)
					.addContainerGap(234, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInformaes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlNome)
						.addComponent(jtfMostraNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlEmail)
						.addComponent(jtfMostraEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jtfMostraApelido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jlApelido))
					.addGap(33)
					.addComponent(lblAlterarDados)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlNovoApelido)
						.addComponent(jtfEscreverNovoApelido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlNovaSeha)
						.addComponent(jpsNovaSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jbSalvar)
						.addComponent(jbSair))
					.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jlDesejaExcluirSuaConta)
						.addComponent(jbExcluirConta))
					.addGap(25))
		);
		getContentPane().setLayout(groupLayout);

	}
}
