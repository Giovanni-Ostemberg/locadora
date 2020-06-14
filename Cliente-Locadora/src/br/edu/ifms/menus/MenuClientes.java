package br.edu.ifms.menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.edu.ifms.operacoes.clientes.CadastraClientes;
import br.edu.ifms.server.MenuPrincipal;

public class MenuClientes extends JFrame implements ActionListener {
	
	JButton botaoCadastro, botaoLocacoesPorCliente, botaoNovaLocacao, botaoVoltar;
	private static final long serialVersionUID = 1L;
	private String login;

	public MenuClientes(String login) {
		
		this.login = login;
		
		setLayout(null);
		
		
		
		botaoCadastro = new JButton("Cadastro");
		botaoCadastro.setBounds(120,40,100,20);
		botaoCadastro.setToolTipText("Menu de clientes");
		botaoCadastro.setForeground(Color.RED);
		botaoCadastro.addActionListener(this);
		add(botaoCadastro);
		
		botaoLocacoesPorCliente = new JButton("Listar Locações");
		botaoLocacoesPorCliente.setBounds(120,80,100,20);
		botaoLocacoesPorCliente.setToolTipText("Menu de clientes");
		botaoLocacoesPorCliente.setForeground(Color.RED);
		botaoLocacoesPorCliente.addActionListener(this);
		add(botaoLocacoesPorCliente);

		botaoNovaLocacao = new JButton("Nova Locação");
		botaoNovaLocacao.setBounds(120,120,100,20);
		botaoNovaLocacao.setToolTipText("Menu de clientes");
		botaoNovaLocacao.setForeground(Color.RED);
		botaoNovaLocacao.addActionListener(this);
		add(botaoNovaLocacao);
		
		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(120,160,100,20);
		botaoVoltar.setToolTipText("Menu de clientes");
		botaoVoltar.setForeground(Color.RED);
		botaoVoltar.addActionListener(this);
		add(botaoVoltar);
		
	}
	
	public void showMenuClientes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.botaoCadastro) {
			CadastraClientes cadastro= new CadastraClientes(this.login);
			cadastro.formulario();
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
