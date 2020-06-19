package br.edu.ifms.operacoes.carros;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.edu.ifms.menus.MenuCarros;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class DevolverCarro  extends JFrame implements ActionListener  {
	
	
	JButton botaoVoltar, botaoPesquisar, botaoDevolucao;
	JComboBox<String> comboCarros;
	private JLabel labelNome, labelId, labelCat, labelCliente, labelPreco;
	private JTextField textNome, textId, textCliente, textLocadoraRetirada, textPreco;
	String login;
	List<Carro> carrosLocados = new ArrayList<Carro>();
	List<Locacao> locacoesEmAberto = new ArrayList<Locacao>();
	List<Cliente> clientes = new ArrayList<Cliente>();
	Locacao locacaoSelecionada;
	Locadora locadoraDevolucao;
	
	private InterfaceServidorLocadora msi;
	public DevolverCarro(String login) throws RemoteException, ClassNotFoundException {
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
		
		labelCliente = new JLabel("Cliente: ");
		textCliente = new JTextField("");
		textCliente.setBounds(150,140,100,20);
		textCliente.setForeground(Color.white);
		textCliente.setBackground(Color.DARK_GRAY);
		labelCliente.setBounds(50,140,100,20);
		labelCliente.setForeground(Color.white);
		labelCliente.setBackground(Color.DARK_GRAY);
		add(textCliente);
		add(labelCliente);



		//Componentes restrição
		labelCat = new JLabel("Loc. Ret.: ");
		textLocadoraRetirada = new JTextField("");
		textLocadoraRetirada.setBounds(90,180,50,20);
		textLocadoraRetirada.setForeground(Color.white);
		textLocadoraRetirada.setBackground(Color.DARK_GRAY);
		labelCat.setBounds(50,180,50,20);
		labelCat.setForeground(Color.white);
		labelCat.setBackground(Color.DARK_GRAY);
		add(textLocadoraRetirada);
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
		
		this.carrosLocados = msi.listarTodosCarros();
		comboCarros = new JComboBox<String>();
		for(Carro c : carrosLocados) {
			if(c.getDisponibilidade()==null) {
			comboCarros.addItem(c.getNome() + " - " + c.getPlaca());
			}
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
		
		botaoDevolucao = new JButton("Registrar Devolução");
		botaoDevolucao.setBounds(200,250,100,20);
		botaoDevolucao.setForeground(Color.WHITE);
		botaoDevolucao.setBackground(Color.GREEN);
		botaoDevolucao.addActionListener(this);
		add(botaoDevolucao);
		
		for(Locacao loc : msi.listarLocacoes()) {
			if(loc.getLocadoraDevolucaoID()==null) {
				locacoesEmAberto.add(loc);
			}
		}
		
		for(Locadora l : msi.listarLocadoras()) {
			if(l.getLogin().equals(this.login)) {
				System.out.println("Locadora: " + l.getNome());
				locadoraDevolucao = l;
			}
		}
		
		this.clientes = msi.listarClientes();
		
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
				
			
				Carro selecionado = null;
				for(Carro c : carrosLocados) {
	
					if(c.getPlaca().equals(placa)) {
						System.out.println("Carro encontrado" + c.getPlaca());
						this.textPreco.setText(c.getPrecoPorSegundo().toString());
						this.textId.setText(c.getPlaca());
						this.textNome.setText(c.getNome());
						selecionado = c;
					}
				}
				
				for(Locacao loc2 : locacoesEmAberto) {
					if(loc2.getCarroID().equals(selecionado.getPlaca())) {
						locacaoSelecionada = loc2;
						
						this.textLocadoraRetirada.setText(loc2.getLocadoraRetiradaID().toString());
						for(Cliente c : clientes) {
							if(c.getId().equals(loc2.getClienteID())) {
								this.textCliente.setText(c.getNome());
							}
						}				
					}
				}
			}else {
				if(e.getSource() == botaoDevolucao) {
					System.out.println(locadoraDevolucao.getID());
					locacaoSelecionada.setLocadoraDevolucaoID(locadoraDevolucao.getID());
					locacaoSelecionada.setHorarioDevolucao(new Date());
					try {
						msi.devolucao(locacaoSelecionada, locadoraDevolucao);
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
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
