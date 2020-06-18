package br.edu.ifms.menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.edu.ifms.operacoes.locacoes.ListarLocacoes;

public class MenuPrincipal extends JFrame implements ActionListener {
	
	String login;
	JButton botaoClientes;
	JButton botaoCarros, botaoLocacoes;

	
	public MenuPrincipal(String login) {
		this.login = login;
		
		setLayout(null);
		
		
		
		botaoClientes = new JButton("Clientes");
		botaoClientes.setBounds(70,40,200,20);
		botaoClientes.setToolTipText("Menu de clientes");
		botaoClientes.setForeground(Color.RED);
		botaoClientes.addActionListener(this);
		add(botaoClientes);
		
		botaoCarros = new JButton("Carros");
		botaoCarros.setBounds(70,80,200,20);
		botaoCarros.setToolTipText("Menu de carros");
		botaoCarros.setForeground(Color.RED);
		botaoCarros.addActionListener(this);
		add(botaoCarros);
		
		botaoLocacoes = new JButton("Locações");
		botaoLocacoes.setBounds(70,120,200,20);
		botaoLocacoes.setToolTipText("Pesquisar Locações");
		botaoLocacoes.setForeground(Color.RED);
		botaoLocacoes.addActionListener(this);
		add(botaoLocacoes);
		
		
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
			}else {
				if(e.getSource() == this.botaoLocacoes) {
					ListarLocacoes listaLocacoes = new ListarLocacoes(login);
					listaLocacoes.listar();
					this.dispose();
				}
			}
		}
		
	}

}
