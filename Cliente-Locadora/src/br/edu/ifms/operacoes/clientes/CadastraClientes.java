package br.edu.ifms.operacoes.clientes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.edu.ifms.menus.MenuClientes;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.server.InterfaceServidorLocadora;

//Esta Classe será responsável por enviar o cliente ao servidor, para que este seja gravado nos registros
public class CadastraClientes extends JFrame implements ActionListener {

	private String login, nome;
	private String categoriaHabilitacao;
	private Long id;
	private JLabel labelNome, labelId, labelCat;
	private JTextField textNome, textId;
	private JCheckBox A, B, C, D, E;
	private JButton submit, voltar;
	private InterfaceServidorLocadora msi;



	/*
 	Um cliente deve possuir os seguintes atributos:
		Long id;
		String nome;
		char[] categoriaHabilitacao;
	 */

	public CadastraClientes(String login) {
		setLayout(null);

		//Componentes do input do nome do cliente
		labelNome = new JLabel("Nome: ");
		textNome = new JTextField("");
		textNome.setBounds(150,40,100,20);
		labelNome.setBounds(50,40,100,20);
		labelNome.setForeground(Color.white);
		labelNome.setBackground(Color.DARK_GRAY);
		add(textNome);
		add(labelNome);



		//Componentes Id
		labelId = new JLabel("Id: ");
		textId = new JTextField("");
		textId.setBounds(150,80,100,20);
		labelId.setBounds(50,80,100,20);
		labelId.setForeground(Color.white);
		labelId.setBackground(Color.DARK_GRAY);
		add(textId);
		add(labelId);

		//Componentes CheckBox categoria
		A = new JCheckBox("A");
		A.setBounds(110,150,100,20);
		A.setForeground(Color.WHITE);
		A.setBackground(Color.DARK_GRAY);
		add(A);

		B = new JCheckBox("B");
		B.setBounds(220,150,100,20);
		B.setForeground(Color.WHITE);
		B.setBackground(Color.DARK_GRAY);
		add(B);

		C = new JCheckBox("C");
		C.setBounds(110,180,100,20);
		C.setForeground(Color.WHITE);
		C.setBackground(Color.DARK_GRAY);
		add(C);

		D = new JCheckBox("D");
		D.setBounds(220,180,120,20);
		D.setForeground(Color.WHITE);
		D.setBackground(Color.DARK_GRAY);
		add(D);

		E = new JCheckBox("E");
		E.setBounds(110,210,120,20);
		E.setForeground(Color.WHITE);
		E.setBackground(Color.DARK_GRAY);
		add(E);

		labelCat = new JLabel("Categorias");
		labelCat.setBounds(50,120,100,20);
		labelCat.setForeground(Color.WHITE);
		labelCat.setBackground(Color.DARK_GRAY);
		add(labelCat);


		submit = new JButton("Enviar");
		submit.setBounds(220,250,100,20);
		submit.setToolTipText("Menu de clientes");
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

			Cliente c = new Cliente();

			System.out.println("textNome > " + textNome.getText() + "\nID > " + textId.getText());
			//Setando os parametros na classe

			String categorias = "";
			if(A.isSelected()) {
				categorias+=" A |";
			}if(B.isSelected()){
				categorias+=" B |";
			}if(C.isSelected()) {
				categorias+=" C |";
			}if(D.isSelected()) {
				categorias+=" D |";
			}if(E.isSelected()) {
				categorias+=" E ";
			}

			if(textNome.getText() == "" ||textId.getText() == "" || categorias == "" ) {
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos");

			}else {
				c.setNome(textNome.getText());
				c.setId(Long.parseLong(textId.getText()));
				c.setCategoriaHabilitacao(categorias);

				try {
					msi.cadastrarCliente(c);
					JOptionPane.showMessageDialog(null, "Cliente Cadastrado com sucesso!");

				} catch (RemoteException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Não foi possível cadastrar");


				}
			}

		}

		//Ação do botão Voltar
		if(e.getSource() == voltar) {
			MenuClientes menu = new MenuClientes(login);
			menu.showMenuClientes();
			this.dispose();

		}


	}

	//Clase para exibir o formulário
	public void formulario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);
	}

}
