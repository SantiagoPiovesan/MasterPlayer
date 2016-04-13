package br.senai.sc.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.senai.sc.controller.AcessoController;
import br.senai.sc.model.Usuario;

public class PrincipalUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jfPrincipalUI;
	private JTextField jtfApelidoLogin;
	private JTextField jtfNomeCadastro;
	private JTextField jtfEmailCadastro;
	private JTextField jtfApelidoCadastro;
	private JPasswordField jpfSenhaLogin;
	private JPasswordField jpfSenhaCadastro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalUI frame = new PrincipalUI();
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
	 */
	public PrincipalUI() {		
		setTitle("MasterPlayer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 445);
		jfPrincipalUI = new JPanel();
		jfPrincipalUI.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jfPrincipalUI);
		
		JLabel jlBemVindo = new JLabel("Bem Vindo ao Master Player!");
		jlBemVindo.setFont(new Font("Arial", Font.BOLD, 15));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JLabel jlPossuiUmaConta = new JLabel("J\u00E1 Possui uma conta? Fa\u00E7a Login abaixo:");
		jlPossuiUmaConta.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel jlApelidoLogin = new JLabel("Apelido:");
		
		jtfApelidoLogin = new JTextField();
		jtfApelidoLogin.setColumns(10);
		
		JLabel jlSenhaLogin = new JLabel("Senha:");
		
		JButton jbEntrarLogin = new JButton("Entrar");
		jbEntrarLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				AcessoController acessCont = new AcessoController();
				Usuario usuario = new Usuario();
				usuario.setApelidoUsuario(jtfApelidoLogin.getText());
				usuario.setSenhaUsuario(jpfSenhaLogin.getText());
				boolean loginCorreto = false;
				
				// Validar dados de login
				try {
					loginCorreto = acessCont.validaLogin(usuario);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}				
				// Obter dados do usuario cadastrado
				try {
					acessCont.dadosDoUsuario(usuario);
				} catch (SQLException e) {					
					e.printStackTrace();
				}
				if(loginCorreto){
					dispose();
					DisplayUI display = new DisplayUI(usuario);
					display.setVisible(true);
					JOptionPane.showMessageDialog(null, "Bem Vindo " +     usuario.getApelidoUsuario() + "!");
				} else {					
					jtfApelidoLogin.setText("");
					jpfSenhaLogin.setText("");
				}
				
			}
		});
		
		JLabel jlAindaNaoCadastrado = new JLabel("Ainda n\u00E3o \u00E9 Cadastrado? Cadastre-se abaixo:");
		jlAindaNaoCadastrado.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel jlNomeCadastro = new JLabel("Nome:");
		
		JLabel jlEmailCadastro = new JLabel("E-mail:");
		
		JLabel jlApelidoCadastro = new JLabel("Apelido:");
		
		JLabel jlSenhaCadastro = new JLabel("Senha:");
		
		jtfNomeCadastro = new JTextField();
		jtfNomeCadastro.setColumns(10);
		
		jtfEmailCadastro = new JTextField();
		jtfEmailCadastro.setColumns(10);
		
		jtfApelidoCadastro = new JTextField();
		jtfApelidoCadastro.setColumns(10);
		
		JButton jbCadastro = new JButton("Cadastrar");
		jbCadastro.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				AcessoController acessCont = new AcessoController();
				Usuario usuario = new Usuario();
				usuario.setNomeUsuario(jtfNomeCadastro.getText());
				usuario.setEmailUsuario(jtfEmailCadastro.getText());
				usuario.setApelidoUsuario(jtfApelidoCadastro.getText());
				usuario.setSenhaUsuario(jpfSenhaCadastro.getText());
				usuario.setDiretorioUsuario(acessCont.criaDiretorioUsuario(usuario));
				// Valida dados do novo usuario
				try {
					if(acessCont.validaCadastro(usuario)){
						jtfApelidoCadastro.setText("");						
						jtfEmailCadastro.setText("");
						jtfNomeCadastro.setText("");
						jpfSenhaCadastro.setText("");
						JOptionPane.showMessageDialog(null, "Usuário cadastrado!");
						dispose();
						DisplayUI display = new DisplayUI(usuario);
						display.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
		});
		
		
		JLabel jlLogo = new JLabel("");
		jlLogo.setIcon(new ImageIcon(PrincipalUI.class.getResource("/br/senai/sc/images/geral/LogoTeste.png")));
		
		jpfSenhaLogin = new JPasswordField();
		
		jpfSenhaCadastro = new JPasswordField();
		GroupLayout gl_jfPrincipalUI = new GroupLayout(jfPrincipalUI);
		gl_jfPrincipalUI.setHorizontalGroup(
			gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jfPrincipalUI.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
						.addComponent(jlBemVindo)
						.addGroup(gl_jfPrincipalUI.createSequentialGroup()
							.addComponent(jlLogo)
							.addGap(39)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_jfPrincipalUI.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_jfPrincipalUI.createSequentialGroup()
											.addGap(6)
											.addComponent(jlSenhaLogin)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(jpfSenhaLogin))
										.addGroup(gl_jfPrincipalUI.createSequentialGroup()
											.addComponent(jlApelidoLogin)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(jtfApelidoLogin))
										.addComponent(jlPossuiUmaConta))
									.addPreferredGap(ComponentPlacement.RELATED, 11, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_jfPrincipalUI.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
										.addComponent(jlAindaNaoCadastrado)
										.addGroup(gl_jfPrincipalUI.createSequentialGroup()
											.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.TRAILING)
												.addComponent(jlEmailCadastro)
												.addComponent(jlNomeCadastro)
												.addComponent(jlApelidoCadastro)
												.addComponent(jlSenhaCadastro))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
												.addComponent(jpfSenhaCadastro, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
												.addComponent(jtfNomeCadastro, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
												.addComponent(jtfApelidoCadastro, 193, 202, Short.MAX_VALUE)
												.addComponent(jtfEmailCadastro, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
											.addGap(9))
										.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)))
								.addGroup(gl_jfPrincipalUI.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jbCadastro)
									.addGap(100))
								.addGroup(gl_jfPrincipalUI.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jbEntrarLogin, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
									.addGap(105)))
							.addGap(211)))
					.addGap(109))
		);
		gl_jfPrincipalUI.setVerticalGroup(
			gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jfPrincipalUI.createSequentialGroup()
					.addContainerGap()
					.addComponent(jlBemVindo)
					.addGap(18)
					.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_jfPrincipalUI.createSequentialGroup()
							.addComponent(jlPossuiUmaConta)
							.addGap(18)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.BASELINE)
								.addComponent(jlApelidoLogin)
								.addComponent(jtfApelidoLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.BASELINE)
								.addComponent(jpfSenhaLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jlSenhaLogin))
							.addGap(6)
							.addComponent(jbEntrarLogin)
							.addGap(18)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jlAindaNaoCadastrado)
							.addGap(18)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.BASELINE)
								.addComponent(jlNomeCadastro)
								.addComponent(jtfNomeCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.BASELINE)
								.addComponent(jlEmailCadastro)
								.addComponent(jtfEmailCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.BASELINE)
								.addComponent(jlApelidoCadastro)
								.addComponent(jtfApelidoCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_jfPrincipalUI.createParallelGroup(Alignment.BASELINE)
								.addComponent(jlSenhaCadastro)
								.addComponent(jpfSenhaCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbCadastro))
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
						.addComponent(jlLogo))
					.addContainerGap())
		);
		jfPrincipalUI.setLayout(gl_jfPrincipalUI);
	}
}
