package br.edu.ifms.operacoes.clientes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.edu.ifms.menus.MenuClientes;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.operacoes.locacoes.CadastrarNovaLocacao;
import br.edu.ifms.operacoes.locacoes.ListarLocacoesPorCliente;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class ListarCarrosDisponiveisPorCliente extends JFrame  implements ActionListener  {

	private String login;
	JButton botaoVoltar;
	JComboBox<String> comboLocadoras;
	List<Carro> carrosDisponiveis = new ArrayList<Carro>();
	List<Locadora> locadoras = new ArrayList<Locadora>();
	JTable table = new JTable();
	Locadora locadoraSelecionada;
	Cliente clienteSelecionado;
	ButtonGroup listaCarros = new ButtonGroup();
	String col[] = {"Carro", "Placa", "restrição"};
	DefaultTableModel modeloTabela = new DefaultTableModel(col,0);
	JScrollPane painel = new JScrollPane();


	private InterfaceServidorLocadora msi;

	public ListarCarrosDisponiveisPorCliente(String login, Cliente cliente) throws RemoteException, ClassNotFoundException {
		this.login = login;
		this.clienteSelecionado = cliente;

		setLayout(null);


		//conexão com o servidor
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

		this.locadoras = msi.listarLocadoras();
		for(Locadora l : locadoras) {
			if(l.getLogin().equals(this.login)) {
				this.locadoraSelecionada = l;
			}
		}

		//Apresentação Gráfica da Tabela
		table = new JTable(modeloTabela);
		table.setForeground(Color.WHITE);
		table.setBackground(Color.DARK_GRAY);


		//Estilo do cabeçalho da tabela
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(Color.DARK_GRAY);
		headerRenderer.setForeground(Color.WHITE);
		for (int i = 0; i < table.getModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
		}

		painel = new JScrollPane(table);
		painel.setBounds(45,50,250,200);
		painel.setForeground(Color.WHITE);
		painel.setBackground(Color.DARK_GRAY);
		add(painel);

		try {
			carrosDisponiveis = msi.listarCarrosDisponiveis();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		boolean teste = false;
		for(Carro c : carrosDisponiveis) {
			if(c.getDisponibilidade().getID().equals(this.locadoraSelecionada.getID()) && cliente.getCategoriaHabilitacao().contains(c.getRestricao())) {

				teste = true;
				Object[] linha = {c.getPlaca(),c.getNome(),c.getRestricao()};
				this.modeloTabela.addRow(linha);

			}
		}


		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(20,300,100,20);
		botaoVoltar.setForeground(Color.WHITE);
		botaoVoltar.setBackground(Color.RED);
		botaoVoltar.addActionListener(this);
		add(botaoVoltar);


		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				Carro carroSelecionado = null;
				if (table.getSelectedRow() > -1) {

					System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
					ListarLocacoesPorCliente listar = null;

					for(Carro c : carrosDisponiveis) {
						if(c.getPlaca()==table.getValueAt(table.getSelectedRow(), 0)){
							carroSelecionado = c;
							CadastrarNovaLocacao nova = new CadastrarNovaLocacao(clienteSelecionado, locadoraSelecionada,carroSelecionado, login);
							nova.cadastro();
							dispose();
						}

					}

				}

			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoVoltar) {
			PesquisarClientesPorNome listaClientes = null;
			try {
				listaClientes = new PesquisarClientesPorNome(this.login);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			listaClientes.showLista();
			this.dispose();

		}else {
			//if(e.getSource())

		}
	}

	public void listarCarros() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);		
	}

}
