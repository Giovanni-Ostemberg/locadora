package Armazenamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;

public class Registros implements Serializable {


	private static final long serialVersionUID = 1L;
	private ArrayList<Carro> carros = new ArrayList<>();
	private ArrayList<Cliente> clientes = new ArrayList<>();
	private ArrayList<Locacao> locacoes = new ArrayList<>();
	private ArrayList<Locadora> locadoras = new ArrayList<>();
	


	//Método que recupera as informações do arquivo para a classe atual
	public Registros recuperarRegistros() throws IOException, ClassNotFoundException {
		
		
		//Locadora l = new Locadora((long) 1, "admin", "admin", "admin");
		Registros registros = new Registros();
		//registros.locadoras.add(l);	
		//salvarRegistros();
		
		
		ObjectInputStream in = null;
		FileInputStream arq = null;
		try {
			arq = new FileInputStream("registros.ser");
			in = new ObjectInputStream(arq);
			registros = (Registros) in.readObject();
		} catch (FileNotFoundException e3) {
			System.out.println("falhou");
		}
		
		this.setCarros(registros.getCarros());
		this.setClientes(registros.getClientes());
		this.setLocacoes(registros.getLocacoes());
		this.setLocadoras(registros.getLocadoras());
		
		return registros;
	}


	//Método que salva o estado atual da classe para o arquivo 
	public void salvarRegistros() throws ClassNotFoundException, IOException {
		
		for(Cliente c : this.clientes) {
			System.out.println(c.getNome() + " | " + c.getId() + " | " + c.getCategoriaHabilitacao());
		}
		
		for(Locadora l : locadoras) {
			System.out.println(l.getLogin());
		}

		
		try {
			try (FileOutputStream arquivo = new FileOutputStream("registros.ser")) {
				try (ObjectOutputStream registros = new ObjectOutputStream(arquivo)) {
					registros.writeObject(this);
					registros.flush();
					registros.close();
				}
				arquivo.flush();
				arquivo.close();
			}
			System.out.println("Registros salvos com sucesso!");

		} catch (Exception e) {
			System.out.println("Não foi possível encontrar o arquivo");
		}
		
		recuperarRegistros();

	}
	
	
	//Método que retorna a locadora de acordo com o login
	public Locadora getLocadora(String login) throws ClassNotFoundException, IOException {
		this.recuperarRegistros();
		for(Locadora l : this.locadoras) {
			System.out.println(l);
			if(l.getLogin().equals(login)) {
				return l;
			}
		}	
		return null;
	}
	
	public ArrayList<Carro> getCarros() {
		return carros;
	}


	public void setCarros(ArrayList<Carro> carros) {
		this.carros = carros;
	}


	public ArrayList<Cliente> getClientes() {
		return clientes;
	}


	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}


	public ArrayList<Locacao> getLocacoes() {
		return locacoes;
	}


	public void setLocacoes(ArrayList<Locacao> locacoes) {
		this.locacoes = locacoes;
	}


	public ArrayList<Locadora> getLocadoras() {
		return locadoras;
	}


	public void setLocadoras(ArrayList<Locadora> locadoras) {
		this.locadoras = locadoras;
	}
	
	



}
