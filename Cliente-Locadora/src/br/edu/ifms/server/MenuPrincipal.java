package br.edu.ifms.server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.edu.ifms.menus.MenuCarros;
import br.edu.ifms.menus.MenuClientes;

public class MenuPrincipal extends JFrame implements ActionListener {
	
	String login;
	JButton botaoClientes;
	JButton botaoCarros, botaoLocadoras;

	
	public MenuPrincipal(String login) {
		this.login = login;
		
		setLayout(null);
		
		
		
		botaoClientes = new JButton("Clientes");
		botaoClientes.setBounds(120,40,100,20);
		botaoClientes.setToolTipText("Menu de clientes");
		botaoClientes.setForeground(Color.RED);
		botaoClientes.addActionListener(this);
		add(botaoClientes);
		
		botaoCarros = new JButton("Carros");
		botaoCarros.setBounds(120,80,100,20);
		botaoCarros.setToolTipText("Menu de carros");
		botaoCarros.setForeground(Color.RED);
		botaoCarros.addActionListener(this);
		add(botaoCarros);
		
		botaoLocadoras = new JButton("Locadoras");
		botaoLocadoras.setBounds(120,120,100,20);
		botaoLocadoras.setToolTipText("Menu de locadoras");
		botaoLocadoras.setForeground(Color.RED);
		botaoLocadoras.addActionListener(this);
		add(botaoCarros);
		
	}

	//método que imprime o menu na tela do usuário logado
	public void showMenu() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Encaminha o usuário ao menu de clientes
		if(e.getSource() == this.botaoClientes) {
			MenuClientes menuClientes = new MenuClientes(this.login);
			menuClientes.showMenuClientes();
			this.dispose();
			
		}else {
			//Encaminha o usuário ao menu de carros
			if(e.getSource() == this.botaoCarros) {
				MenuCarros menuCarros = new MenuCarros(this.login);
				menuCarros.showMenuCarros();
				this.dispose();
			}
		}
		
	}

}
