package br.edu.ifms.menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.edu.ifms.operacoes.carros.ListarCarrosDisponiveis;
import br.edu.ifms.operacoes.carros.ListarCarrosDisponiveisPorLocadora;
import br.edu.ifms.operacoes.clientes.CadastraClientes;
import br.edu.ifms.server.MenuPrincipal;

public class MenuCarros extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton botaoDisponiveis, botaoDevolucao, botaoVoltar, botaoDisponiveisPorLocadora;
	String login;

	public MenuCarros(String login) {

		setLayout(null);
		this.login = login;

		//Botão para a listagem dos carros disponíveis para locação
		botaoDisponiveis = new JButton("Carros Disponíveis");
		botaoDisponiveis.setBounds(95,40,150,20);
		botaoDisponiveis.setToolTipText("Lista de carros disponiveis");
		botaoDisponiveis.setForeground(Color.RED);
		botaoDisponiveis.addActionListener(this);
		add(botaoDisponiveis);

		botaoDisponiveisPorLocadora = new JButton("Disponivel por Locadora");
		botaoDisponiveisPorLocadora.setBounds(95,80,150,20);
		botaoDisponiveisPorLocadora.setToolTipText("Menu de clientes");
		botaoDisponiveisPorLocadora.setForeground(Color.RED);
		botaoDisponiveisPorLocadora.addActionListener(this);
		add(botaoDisponiveisPorLocadora);

		//Botão para registrar a devolução de um carro atualmente locado
		botaoDevolucao = new JButton("Registrar devolução");
		botaoDevolucao.setBounds(95,120,150,20);
		botaoDevolucao.setToolTipText("Menu de clientes");
		botaoDevolucao.setForeground(Color.RED);
		botaoDevolucao.addActionListener(this);
		add(botaoDevolucao);

		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(95,200,150,20);
		botaoVoltar.setToolTipText("Menu de clientes");
		botaoVoltar.setForeground(Color.WHITE);
		botaoVoltar.setBackground(Color.RED);
		botaoVoltar.addActionListener(this);
		add(botaoVoltar);
	}

	public void showMenuCarros() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ListarCarrosDisponiveis lista = null;
		if(e.getSource() == this.botaoDisponiveis) {

			try {
				lista = new ListarCarrosDisponiveis(this.login);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lista.listar();
			this.dispose();		
		}else{
			if(e.getSource() == this.botaoVoltar) {
				MenuPrincipal main = new MenuPrincipal(this.login);
				main.showMenu();
				this.dispose();
			}else {
				if(e.getSource() == this.botaoDisponiveisPorLocadora) {
					ListarCarrosDisponiveisPorLocadora carrosPorLocadora = null;
					try {
						carrosPorLocadora = new ListarCarrosDisponiveisPorLocadora(this.login);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					carrosPorLocadora.listar();
					this.dispose();

				}
			}

		}
	}
}



