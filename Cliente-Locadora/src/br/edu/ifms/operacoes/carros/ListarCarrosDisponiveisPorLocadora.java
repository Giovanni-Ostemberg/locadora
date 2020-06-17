package br.edu.ifms.operacoes.carros;

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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.edu.ifms.menus.MenuCarros;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Locadora;
import br.edu.ifms.server.InterfaceServidorLocadora;

public class ListarCarrosDisponiveisPorLocadora extends JFrame  implements ActionListener {

	private String login;
	JButton botaoVoltar, botaoPesquisar;
	JComboBox<String> comboLocadoras;
	private JLabel labelNome, labelId, labelCat, labelLocadora, labelPreco;
	private JTextField textNome, textId, textLocadora, textCat, textPreco;
	List<Carro> carrosDisponiveis = new ArrayList<Carro>();
	//List<Carro> carrosDisponiveisPorLocadora = new ArrayList<Carro>();
	List<Locadora> locadoras = new ArrayList<Locadora>();
	JTable table = new JTable();
	Locadora locadoraSelecionada;
	ButtonGroup listaCarros = new ButtonGroup();
	String col[] = {"Carro", "Placa", "restrição"};
	DefaultTableModel modeloTabela = new DefaultTableModel(col,0);
	JScrollPane painel = new JScrollPane();


	private InterfaceServidorLocadora msi;





	public ListarCarrosDisponiveisPorLocadora(String login) throws RemoteException, ClassNotFoundException {
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
		comboLocadoras = new JComboBox<String>();
		for(Locadora l : locadoras) {
			comboLocadoras.addItem(l.getID() + " - " + l.getNome());
		}
		comboLocadoras.setBounds(25,20,150,20);
		add(comboLocadoras);

		
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


				ArrayList<JRadioButton> botoes = new ArrayList<>();

				boolean teste = false;
				String selecionado = comboLocadoras.getSelectedItem().toString();
				String id = selecionado.substring(0, selecionado.indexOf(" "));
				System.out.println(Long.parseLong(id));

				for(Locadora l : locadoras) {
					if(l.getID()==Long.parseLong(id)) {
						this.locadoraSelecionada = l;
						System.out.println("Locadora Encontrada");
					}
				}


				try {
					carrosDisponiveis = msi.listarCarrosDisponiveis();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}

				for(Carro c : carrosDisponiveis) {
					if((long)c.getDisponibilidade().getID() == this.locadoraSelecionada.getID()) {

						teste = true;
						Object[] linha = {c.getPlaca(),c.getNome(),c.getRestricao()};
						this.modeloTabela.addRow(linha);

					}
				}

				SwingUtilities.updateComponentTreeUI(painel);
				this.invalidate();
				this.validate();
				this.repaint();




				if(teste==false) {
					JOptionPane.showMessageDialog(null, "Não há carros disponíveis nesta locadora!");
					SwingUtilities.updateComponentTreeUI(this);

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
