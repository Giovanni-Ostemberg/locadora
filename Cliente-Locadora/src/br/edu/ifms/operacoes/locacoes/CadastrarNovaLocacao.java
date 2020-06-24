package br.edu.ifms.operacoes.locacoes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.edu.ifms.menus.MenuClientes;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class CadastrarNovaLocacao extends JFrame implements ActionListener  {

	private String login, nome;
	private String categoriaHabilitacao;
	private Long id;
	private JLabel labelNome, labelLocadora, labelHora, labelcarro;
	private JLabel textNome, textLocadora, textCarro;
	private JCheckBox A, B, C, D, E;
	private JButton submit, voltar;
	private InterfaceServidorLocadora msi;
	private Locadora locadora;
	private Cliente cliente;
	private Carro carro;
	Date horarioLocacao = new Date();

	

	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/uuuu - HH:mm:ss");
	String dataFormatada;


	public CadastrarNovaLocacao(Cliente cliente, Locadora locadoraSelecionada, Carro carro, String login) {
		this.cliente = cliente;
		this.locadora = locadoraSelecionada;
		this.carro = carro;
		this.login = login;

		setLayout(null);

		//Componentes do input do nome do cliente
		labelNome = new JLabel("Cliente: ");
		textNome = new JLabel(cliente.getNome());
		textNome.setBounds(150,40,100,20);
		textNome.setForeground(Color.WHITE);
		labelNome.setBounds(50,40,100,20);
		labelNome.setForeground(Color.white);
		labelNome.setBackground(Color.DARK_GRAY);
		add(textNome);
		add(labelNome);



		//Componentes Id
		labelLocadora = new JLabel("Locadora: ");
		textLocadora = new JLabel(locadora.getNome());
		textLocadora.setBounds(150,80,150,20);
		textLocadora.setForeground(Color.WHITE);
		labelLocadora.setBounds(50,80,150,20);
		labelLocadora.setForeground(Color.white);
		labelLocadora.setBackground(Color.DARK_GRAY);
		add(textLocadora);
		add(labelLocadora);

		//Componentes Id
		labelcarro = new JLabel("Carro:");
		textCarro = new JLabel(carro.getNome() + " - " + carro.getPlaca());
		textCarro.setBounds(150,120,170,20);
		textCarro.setForeground(Color.WHITE);
		labelcarro.setBounds(50,120,150,20);
		labelcarro.setForeground(Color.white);
		labelcarro.setBackground(Color.DARK_GRAY);
		add(textCarro);
		add(labelcarro);

		//Componentes Etiqueta do grupo das categorias
		labelHora = new JLabel(formato.format(horarioLocacao));
		labelHora.setBounds(50,160,170,20);
		labelHora.setForeground(Color.WHITE);
		labelHora.setBackground(Color.DARK_GRAY);
		add(labelHora);



		submit = new JButton("Confirmar");
		submit.setBounds(220,250,100,20);
		submit.setToolTipText("Confirmar os dados");
		submit.setForeground(Color.WHITE);
		submit.setBackground(Color.BLUE);
		submit.addActionListener(this);
		add(submit);

		voltar = new JButton("Voltar");
		voltar.setBounds(20,250,100,20);
		voltar.setForeground(Color.WHITE);
		voltar.setBackground(Color.RED);
		voltar.addActionListener(this);
		add(voltar);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//Requisição ao servidor
		if(e.getSource() == submit) {
			try
			{
				msi = (InterfaceServidorLocadora) Naming.lookup("rmi://localhost:8888/ServidorLocadora");
			}
			catch (MalformedURLException er) {
				er.printStackTrace();
			}
			catch (Exception err)
			{
				System.out.println("O cliente não pode ser iniciado.\n"+e);				
				System.exit(0);
			}

			Locacao loc = new Locacao();
			//Setando os parametros na classe
			loc.setCarroID(this.carro.getPlaca());
			loc.setClienteID((long)this.cliente.getId());
			loc.setLocadoraRetiradaID((long)this.locadora.getID());
			loc.setHorarioLocacao(horarioLocacao);




			try {
				msi.novaLocacao(loc, carro);
				JOptionPane.showMessageDialog(null, "Locação confirmada!");

			} catch (RemoteException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possível cadastrar");



			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}



		//Ação do botão Voltar
		if(e.getSource() == voltar) {
			MenuClientes menu = new MenuClientes(login);
			menu.showMenuClientes();
			this.dispose();

		}
	}

	public void cadastro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);
	}

}
