package br.edu.ifms.operacoes.locacoes;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class ListarLocacoesPorCliente extends JFrame  implements ActionListener {
	
	private String login;
	JButton botaoVoltar;
	JComboBox<String> comboLocadoras;
	List<Carro> carros = new ArrayList<Carro>();
	List<Locacao> locacoes = new ArrayList<Locacao>();
	List<Locadora> locadoras = new ArrayList<Locadora>();
	Locadora locadoraSelecionada;
	Locacao locSelecionada;
	Carro carroSelecionado;
	JTable table = new JTable();
	Cliente clienteSelecionado;
	ButtonGroup listaCarros = new ButtonGroup();
	String col[] = {"Placa", "Carro", "Locadora", "ID"};
	DefaultTableModel modeloTabela = new DefaultTableModel(col,0);
	JScrollPane painel = new JScrollPane();


	private InterfaceServidorLocadora msi;
	
	public ListarLocacoesPorCliente(String login, Cliente cliente) throws RemoteException, ClassNotFoundException {
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
		
		this.carros = msi.listarTodosCarros();
		this.locadoras = msi.listarLocadoras();

		
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

		boolean teste = false;
		
		locacoes = msi.listarLocacoes();
		int index;
		for(Locacao loc : locacoes) {
			System.out.println(loc.getClienteID().equals((long)clienteSelecionado.getId()));
			if(loc.getClienteID().equals((long)clienteSelecionado.getId())) {
				Carro carroLocado;
				for(Carro c : this.msi.listarTodosCarros()) {
					if(c.getPlaca().equals(loc.getCarroID())){
						carroLocado = c;
						Object[] linha = {c.getPlaca(),c.getNome(), loc.getLocadoraRetiradaID(),locacoes.indexOf(loc)};
						this.modeloTabela.addRow(linha);

					}
				}

			}
		}


		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(20,300,100,20);
		botaoVoltar.setForeground(Color.WHITE);
		botaoVoltar.setBackground(Color.RED);
		botaoVoltar.addActionListener(this);
		add(botaoVoltar);
		
		//Ao clicar na linha, exibe as informações da locação na tela, para o usuário
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				
				if (table.getSelectedRow() > -1) {


					for(Carro c : carros) {
						if(c.getPlaca().equals(table.getValueAt(table.getSelectedRow(), 0).toString())){
							carroSelecionado = c;
							System.out.println("Carro selecionado!");
						}

					}
					for(Locadora l : locadoras) {
						if(l.getID().equals(table.getValueAt(table.getSelectedRow(), 2))) {
							locadoraSelecionada = l;
						}
					}
					
					locSelecionada = locacoes.get((int)table.getValueAt(table.getSelectedRow(), 3));
					
					String mensagem = ("Carro > " + carroSelecionado.getNome() + " - " + carroSelecionado.getPlaca()+ ""
							+ "\nCliente: "+ cliente.getNome() + "\nRetirada:\n\tLocadora > " + locadoraSelecionada.getNome() +"\n\tHorário: " + locSelecionada.getHorarioLocacao());
					JOptionPane.showMessageDialog(null, mensagem);
				}
				/*private Date horarioLocacao, horarioDevolucao;
	private Double valor;
	private Long clienteID, locadoraRetiradaID, locadoraDevolucaoID;
	private String carroID;*/
				

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoVoltar) {
			ListarLocacoes listaLocacoes = null;
			listaLocacoes = new ListarLocacoes(this.login);
			listaLocacoes.listar();
			this.dispose();

		}else {
			//if(e.getSource())

		}
	}

	public void listarLocacoes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);		
	}


}
