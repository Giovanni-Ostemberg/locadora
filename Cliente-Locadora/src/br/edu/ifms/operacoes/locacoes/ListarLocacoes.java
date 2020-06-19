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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.edu.ifms.menus.MenuPrincipal;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.operacoes.clientes.ListarCarrosDisponiveisPorCliente;
import br.edu.ifms.operacoes.clientes.PesquisarClientesPorNome;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class ListarLocacoes extends JFrame  implements ActionListener   {


	private String login;
	JButton botaoVoltar, botaoPesquisar;
	JTextField pesquisaNome;
	private JLabel labelNome, labelId, labelCat, labelLocadora, labelPreco;
	private JTextField textNome, textId, textLocadora, textCat, textPreco;
	Locadora locadora;
	List<Locadora> locadoras = new ArrayList<>();
	List<Cliente> resultadoClientes = new ArrayList<>();
	List<Locacao> locacoes = new ArrayList<>();
	JRadioButton radioCliente, radioLocadora;
	JScrollPane painel = new JScrollPane();
	private InterfaceServidorLocadora msi;


	//Parâmetros da Tabela
	JTable table = new JTable();
	String col[] = {"Id", "Nome"};
	DefaultTableModel modeloTabela = new DefaultTableModel(col,0);

	public ListarLocacoes(String login) {
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

		try {
			this.locadoras = msi.listarLocadoras();
		} catch (RemoteException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			this.locacoes = msi.listarLocacoes();
		} catch (RemoteException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.resultadoClientes = msi.listarClientes();
		} catch (RemoteException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		pesquisaNome = new JTextField("");
		pesquisaNome.setBounds(25,20,150,20);
		add(pesquisaNome);
		
		ButtonGroup grupoBotoes = new ButtonGroup();

		radioCliente = new JRadioButton("Cliente");
		radioCliente.setBackground(Color.DARK_GRAY);
		radioCliente.setForeground(Color.WHITE);
		radioCliente.setBounds(25,40,70,20);
		radioCliente.setSelected(true);
		add(radioCliente);

		radioLocadora = new JRadioButton("Locadora");
		radioLocadora.setBackground(Color.DARK_GRAY);
		radioLocadora.setForeground(Color.WHITE);
		radioLocadora.setBounds(95,40,100,20);
		add(radioLocadora);
		
		grupoBotoes.add(radioCliente);
		grupoBotoes.add(radioLocadora);


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
		painel.setBounds(45,80,250,200);
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
		botaoVoltar.setBounds(20,310,100,20);
		botaoVoltar.setForeground(Color.WHITE);
		botaoVoltar.setBackground(Color.RED);
		botaoVoltar.addActionListener(this);
		add(botaoVoltar);		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoVoltar) {
			MenuPrincipal main = new MenuPrincipal(this.login);
			main.showMenu();
			this.dispose();

		}else {
			if(e.getSource() == botaoPesquisar) {
				if(radioCliente.isSelected()) {

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
							Object[] linha = {c.getId(),c.getNome()};
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
								ListarLocacoesPorCliente listar = null;

								for(Cliente c : resultadoClientes) {
									if(c.getId()==(long)table.getValueAt(table.getSelectedRow(), 0)){
										clienteSelecionado = c;
									}

								}

								//Testa se o cliente possui uma locacão em aberto
								boolean testeLocacao = false;
								
								if(testeLocacao==false) {
									try {
										listar = new ListarLocacoesPorCliente(login, clienteSelecionado);
									} catch (RemoteException e1) {
										e1.printStackTrace();
									} catch (ClassNotFoundException e1) {
										e1.printStackTrace();
									}
									listar.listarLocacoes();
									dispose();
								}

							}

						}
					});

				}else {
					if(radioLocadora.isSelected()) {
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
							locadoras = msi.listarLocadoras();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}

						for(Locadora l : locadoras) {
							if(l.getNome().contains(selecionado)) {

								teste = true;
								Object[] linha = {l.getID(),l.getNome()};
								this.modeloTabela.addRow(linha);

							}
						}

						SwingUtilities.updateComponentTreeUI(painel);
						this.invalidate();
						this.validate();
						this.repaint();



						//Retorna uma mensagem caso a busca não encontre nenhum resultado
						if(teste==false) {
							JOptionPane.showMessageDialog(null, "Não há locadoras disponíveis com este nome!");
							SwingUtilities.updateComponentTreeUI(this);

						}


						//Criação de um evento de clique sobre o cliente retornado pela pesquisa
						table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							public void valueChanged(ListSelectionEvent e) {
								Locadora locadoraSelecionada = null;
								if (table.getSelectedRow() > -1) {

									System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
									ListarCarrosDisponiveisPorCliente listar = null;

									for(Locadora l : locadoras) {
										if(l.getID().equals((long)table.getValueAt(table.getSelectedRow(), 0))){
											locadoraSelecionada = l;
										}

									}

									//Testa se a locadora possui uma locacão em aberto
									boolean testeLocacao = false;
									try {
										ListarLocacoesPorLocadora listarPorLocadora = new ListarLocacoesPorLocadora(login, locadoraSelecionada);
										listarPorLocadora.listarLocacoes();
										dispose();
									} catch (RemoteException | ClassNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
						
									/*if(testeLocacao==false) {
										try {
											//listar = new ListarCarrosDisponiveisPorCliente(login,locadoraSelecionada);
										} catch (RemoteException e1) {
											e1.printStackTrace();
										} catch (ClassNotFoundException e1) {
											e1.printStackTrace();
										}
										listar.listarCarros();
										dispose();
									}*/

								}

							}
						});
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
