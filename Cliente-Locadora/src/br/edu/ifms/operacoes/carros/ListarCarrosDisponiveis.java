package br.edu.ifms.operacoes.carros;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.edu.ifms.menus.MenuCarros;
import br.edu.ifms.model.Carro;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class ListarCarrosDisponiveis extends JFrame implements ActionListener {
	
	JButton botaoVoltar, botaoPesquisar;
	JComboBox<String> comboCarros;
	private JLabel labelNome, labelId, labelCat, labelLocadora, labelPreco;
	private JTextField textNome, textId, textLocadora, textCat, textPreco;
	String login;
	List<Carro> carrosDisponiveis = new ArrayList<Carro>();
	
	private InterfaceServidorLocadora msi;


	public ListarCarrosDisponiveis(String login) throws RemoteException, ClassNotFoundException {
		this.login = login;
		
		setLayout(null);
		
		labelNome = new JLabel("Nome: ");
		textNome = new JTextField("");
		textNome.setBounds(150,60,100,20);
		textNome.setBackground(Color.DARK_GRAY);
		textNome.setForeground(Color.white);
		labelNome.setBounds(50,60,100,20);
		labelNome.setForeground(Color.white);
		labelNome.setBackground(Color.DARK_GRAY);
		add(textNome);
		add(labelNome);



		//Componentes Id
		labelId = new JLabel("Placa: ");
		textId = new JTextField("");
		textId.setBounds(150,100,100,20);
		textId.setForeground(Color.white);
		textId.setBackground(Color.DARK_GRAY);
		labelId.setBounds(50,100,100,20);
		labelId.setForeground(Color.white);
		labelId.setBackground(Color.DARK_GRAY);
		add(textId);
		add(labelId);
		
		labelLocadora = new JLabel("Locadora: ");
		textLocadora = new JTextField("");
		textLocadora.setBounds(150,140,100,20);
		textLocadora.setForeground(Color.white);
		textLocadora.setBackground(Color.DARK_GRAY);
		labelLocadora.setBounds(50,140,100,20);
		labelLocadora.setForeground(Color.white);
		labelLocadora.setBackground(Color.DARK_GRAY);
		add(textLocadora);
		add(labelLocadora);



		//Componentes restrição
		labelCat = new JLabel("Placa: ");
		textCat = new JTextField("");
		textCat.setBounds(90,180,50,20);
		textCat.setForeground(Color.white);
		textCat.setBackground(Color.DARK_GRAY);
		labelCat.setBounds(50,180,50,20);
		labelCat.setForeground(Color.white);
		labelCat.setBackground(Color.DARK_GRAY);
		add(textCat);
		add(labelCat);
		
		//Componentes Preço
		labelPreco = new JLabel("Preço: ");
		textPreco = new JTextField("");
		textPreco.setForeground(Color.white);
		textPreco.setBackground(Color.DARK_GRAY);
		textPreco.setBounds(190,180,50,20);
		labelPreco.setBounds(150,180,50,20);
		labelPreco.setForeground(Color.white);
		labelPreco.setBackground(Color.DARK_GRAY);
		add(textPreco);
		add(labelPreco);
		
		
		
		

		
		try
		{
			msi = (InterfaceServidorLocadora) Naming.lookup("rmi://localhost:8888/ServidorLocadora");
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("O cliente não pode ser iniciado.\n"+e);				
			System.exit(0);
		}
		
		this.carrosDisponiveis = msi.listarCarrosDisponiveis();
		comboCarros = new JComboBox<String>();
		for(Carro c : carrosDisponiveis) {
			comboCarros.addItem(c.getNome() + " - " + c.getPlaca());
		}
		comboCarros.setBounds(10,20,150,20);
	   add(comboCarros);
	   
	   botaoPesquisar = new JButton("Pesquisar");
		botaoPesquisar.setBounds(170,20,100,20);
		botaoPesquisar.setForeground(Color.WHITE);
		botaoPesquisar.setBackground(Color.BLUE);
		botaoPesquisar.addActionListener(this);
		add(botaoPesquisar);
	    
	    botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(20,250,100,20);
		botaoVoltar.setForeground(Color.WHITE);
		botaoVoltar.setBackground(Color.RED);
		botaoVoltar.addActionListener(this);
		add(botaoVoltar);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoVoltar) {
			MenuCarros menu = new MenuCarros(login);
			menu.showMenuCarros();
			this.dispose();

		}else {
			if(e.getSource() == botaoPesquisar) {			
				String placa = comboCarros.getSelectedItem().toString();
				placa = placa.substring(placa.lastIndexOf(" ")+1);
				System.out.println(placa);
				
				for(Carro c : carrosDisponiveis) {
					if(c.getPlaca().equals(placa)) {
						System.out.println("Carro encontrado" + c.getPlaca());
						this.textPreco.setText(c.getPrecoPorSegundo().toString());
						this.textId.setText(c.getPlaca());
						this.textNome.setText(c.getNome());
						this.textLocadora.setText(c.getDisponibilidade().getNome());
						this.textCat.setText(c.getRestricao());
					}
				}
				

			}
			
		}
	}


	public void listar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);
	}

}
