package br.edu.ifms.menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.edu.ifms.operacoes.carros.ListarCarrosDisponiveis;
import br.edu.ifms.operacoes.clientes.CadastraClientes;
import br.edu.ifms.server.MenuPrincipal;

public class MenuCarros extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton botaoDisponiveis, botaoDevolucao, botaoVoltar;
	String login;

	public MenuCarros(String login) {
		
		setLayout(null);
		this.login = login;

		//Bot�o para a listagem dos carros dispon�veis para loca��o
		botaoDisponiveis = new JButton("Carros Dispon�veis");
		botaoDisponiveis.setBounds(120,40,100,20);
		botaoDisponiveis.setToolTipText("Lista de carros disponiveis");
		botaoDisponiveis.setForeground(Color.RED);
		botaoDisponiveis.addActionListener(this);
		add(botaoDisponiveis);

		//Bot�o para registrar a devolu��o de um carro atualmente locado
		botaoDevolucao = new JButton("Registrar devolu��o");
		botaoDevolucao.setBounds(120,80,100,20);
		botaoDevolucao.setToolTipText("Menu de clientes");
		botaoDevolucao.setForeground(Color.RED);
		botaoDevolucao.addActionListener(this);
		add(botaoDevolucao);

		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(120,160,100,20);
		botaoVoltar.setToolTipText("Menu de clientes");
		botaoVoltar.setForeground(Color.RED);
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
			}
		}

	}
}

