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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.edu.ifms.menus.MenuCarros;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class PesquisarClientesPorNome extends JFrame  implements ActionListener {

	private String login;
	JButton botaoVoltar, botaoPesquisar;
	JTextField pesquisaNome;
	private JLabel labelNome, labelId, labelCat, labelLocadora, labelPreco;
	private JTextField textNome, textId, textLocadora, textCat, textPreco;
	Locadora locadora;
	List<Locadora> locadoras = new ArrayList<>();
	List<Cliente> resultadoClientes = new ArrayList<>();
	List<Locacao> locacoes = new ArrayList<>();


	//Parâmetros da Tabela
	JTable table = new JTable();
	String col[] = {"Id", "Nome", "Habilitação"};
	DefaultTableModel modeloTabela = new DefaultTableModel(col,0);
	JScrollPane painel = new JScrollPane();

	private InterfaceServidorLocadora msi;

	public PesquisarClientesPorNome(String login) throws RemoteException, ClassNotFoundException {
		this.login = login;
		

		setLayout(null);



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
		this.locacoes = msi.listarLocacoes();
		pesquisaNome = new JTextField("");
		pesquisaNome.setBounds(25,20,150,20);
		add(pesquisaNome);


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

		botaoPesquisar = new JButton("Pesquisar");
		botaoPesquisar.setBounds(195,20,100,20);
		botaoPesquisar.setForeground(Color.WHITE);
		botaoPesquisar.setBackground(Color.BLUE);
		botaoPesquisar.addActionListener(this);
		add(botaoPesquisar);

		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(20,300,100,20);
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

				if(this.modeloTabela.getRowCount()!=0) {
					int linhas = this.modeloTabela.getRowCount();
					System.out.println(this.modeloTabela.getRowCount());
					for(int i = 0; i<linhas;i++) {				
						modeloTabela.removeRow(0);
					}
				}



				boolean teste = false;
				String selecionado = pesquisaNome.getText().toString();

				try {
					resultadoClientes = msi.listarClientes();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}

				for(Cliente c : resultadoClientes) {
					if(c.getNome().contains(selecionado)) {

						teste = true;
						Object[] linha = {c.getId(),c.getNome(),c.getCategoriaHabilitacao()};
						this.modeloTabela.addRow(linha);

					}
				}

				SwingUtilities.updateComponentTreeUI(painel);
				this.invalidate();
				this.validate();
				this.repaint();



				//Retorna uma mensagem caso a busca não encontre nenhum resultado
				if(teste==false) {
					JOptionPane.showMessageDialog(null, "Não há clientes disponíveis com este nome!");
					SwingUtilities.updateComponentTreeUI(this);

				}


				//Criação de um evento de clique sobre o cliente retornado pela pesquisa
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent e) {
						Cliente clienteSelecionado = null;
						if (table.getSelectedRow() > -1) {

							System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
							ListarCarrosDisponiveisPorCliente listar = null;

							for(Cliente c : resultadoClientes) {
								if(c.getId()==(long)table.getValueAt(table.getSelectedRow(), 0)){
									clienteSelecionado = c;
								}

							}
							
							//Testa se o cliente possui uma locacão em aberto
							boolean testeLocacao = false;
							for(Locacao loc : locacoes) {
								System.out.println("Locação encontrada: " + clienteSelecionado.getId().equals((long)loc.getClienteID()) );
								System.out.println(loc.getLocadoraDevolucaoID());
								if(clienteSelecionado.getId().equals((long)loc.getClienteID()) && loc.getLocadoraDevolucaoID()==null) {
									testeLocacao = true;
									JOptionPane.showMessageDialog(null, "Este cliente já possui uma locação em aberto!");									
								}

							}
							if(testeLocacao==false) {
								try {
									listar = new ListarCarrosDisponiveisPorCliente(login, clienteSelecionado);
								} catch (RemoteException e1) {
									e1.printStackTrace();
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								}
								listar.listarCarros();
								dispose();
							}
						
						}

					}
				});

			}

		}
	}

	public void showLista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setSize(350,480);
		setVisible(true);
		setLocationRelativeTo(null);


	}

}
