package br.edu.ifms.server;

//Para executar, utilizar login>admin, senha> admin
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener  {

	private static final long serialVersionUID = 1L;
	//private Scanner ler = new Scanner(System.in);
	private String login, senha;
	static boolean sessaoIniciada = false;
	private JButton botao; 
	private JLabel labelLogin, labelSenha;
	private JTextField textLogin, textSenha;
	
	 // A interface para o objecto remoto
	private InterfaceServidorLocadora msi;

	public Client(boolean sessaoIniciada) {
		setLayout(null);
		
		//Componentes Login, se a sess�o ainda n�o foi iniciada
		labelLogin = new JLabel("Login: ");
		textLogin = new JTextField("");
		textLogin.setBounds(150,40,100,20);
		labelLogin.setBounds(50,40,100,20);
		labelLogin.setForeground(Color.white);
		add(textLogin);
		add(labelLogin);



		//Componentes Senha
		labelSenha = new JLabel("Senha: ");
		textSenha = new JPasswordField("");
		textSenha.setBounds(150,80,100,20);
		labelSenha.setBounds(50,80,100,20);
		labelSenha.setForeground(Color.white);
		add(textSenha);
		add(labelSenha);

		//Componentes Bot�o
		botao = new JButton("Enviar");
		botao.setBounds(150,120,100,20);
		botao.setMnemonic('o');
		botao.setToolTipText("ok!");
		botao.setForeground(Color.GREEN);
		botao.addActionListener(this);
		add(botao);
		
		//Buscando interface do servidor
		try
		{
			msi = (InterfaceServidorLocadora) Naming.lookup("rmi://localhost:8888/ServidorLocadora");
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("O cliente n�o pode ser iniciado.\n"+e);				
			System.exit(0);
		}

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public static void main (String[] argv)
	{	
		
		if(sessaoIniciada==false) {
			
			Client c = new Client(sessaoIniciada);
			c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			c.getContentPane().setBackground(Color.DARK_GRAY);
			c.setResizable(false);
			c.setSize(350,480);
			c.setVisible(true);
			c.setLocationRelativeTo(null);

		}else {
			Client c = new Client(sessaoIniciada);
			c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			c.setResizable(false);
			c.setSize(350,480);
			c.setLocationRelativeTo(null);
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Se o bot�o foi clicado
		if(e.getSource() == this.botao) {
			
			//Recebendo os dados do input
			login = textLogin.getText();
			senha = textSenha.getText();
			System.out.println("Login > " + login + "\nSenha > " + senha);
			try {
				//Se a sess�o for iniciada, ok. Caso contr�rio, refazer a opera��o
				sessaoIniciada = msi.autenticar(login, senha);		
				if(sessaoIniciada == false) {
					System.out.println("Dados incorretos!");
					JOptionPane.showMessageDialog(null, "Usu�rio ou senha inv�lidos!");
				}
			} catch (RemoteException e1) {
				System.out.println("Falha");
			}
			
			if(sessaoIniciada==true) {	
				//Com a sess�o iniciada, fechamos a janela e seguimos para o menu principal
				this.dispose();
				MenuPrincipal menu = new MenuPrincipal(login);
				menu.showMenu();
			}
		}
	}
}
