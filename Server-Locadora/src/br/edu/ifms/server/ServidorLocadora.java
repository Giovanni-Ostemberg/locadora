package br.edu.ifms.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import Armazenamento.Registros;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;

public class ServidorLocadora extends UnicastRemoteObject implements InterfaceServidorLocadora {

	private static final long serialVersionUID = 1L;
	private Registros registros;

	public ServidorLocadora() throws ClassNotFoundException, IOException {
		super();
		System.out.println("Buscando Registros");
		this.registros = new Registros();
		registros.recuperarRegistros();
	}






	public boolean autenticar(String login, String senha) throws ClassNotFoundException, IOException {
		Locadora locadora = registros.getLocadora(login);
		if(locadora!=null&&locadora.getSenha().equals(senha)) {
			return true;
		}else {
			return false;
		}	
	}

	public boolean cadastrarCliente(Cliente cliente) {
		this.registros.getClientes().add(cliente);
		this.registros.salvarRegistros();
		return true;		
	}






	@Override
	public List<Carro> listarCarrosDisponiveis() {
		List<Carro> carrosDisponiveis = new ArrayList<Carro>();
		for(Carro c : this.registros.getCarros()) {
			if(c.getDisponibilidade()!=null) {
				carrosDisponiveis.add(c);
			}
		}
		return carrosDisponiveis;
	}






	@Override
	public List<Locadora> listarLocadoras() throws RemoteException, ClassNotFoundException {
		List<Locadora> locadoras = new ArrayList<Locadora>();	
		for(Locadora l : this.registros.getLocadoras()) {
			locadoras.add(l);
		}
		return locadoras;
	}






	@Override
	public List<Cliente> listarClientes() throws RemoteException, ClassNotFoundException {
		List<Cliente> clientes = new ArrayList<>();
		for(Cliente c : this.registros.getClientes()) {
			clientes.add(c);
		}
		return clientes;
	}






	@Override
	public List<Locacao> listarLocacoes() throws RemoteException, ClassNotFoundException {
		List<Locacao> locacoes = new ArrayList<>();
		for(Locacao l : this.registros.getLocacoes()) {
			locacoes.add(l);
		}
		return locacoes;
	}






	@Override
	public List<Carro> listarTodosCarros() throws RemoteException, ClassNotFoundException {
		return this.registros.getCarros();
	}






	@Override
	public boolean novaLocacao(Locacao locacao, Carro carro) throws RemoteException, ClassNotFoundException, IOException {
		
		Carro carroAtualizado = null;
		int index = -1;
		//Atualizando a disponibilidade do carro
		for(Carro c : this.registros.getCarros()) {
			if(c.getPlaca().equals(carro.getPlaca())){
				index =this.registros.getCarros().indexOf(carro);
				carroAtualizado = c;
				}
		}
		
		
		carroAtualizado.setDisponibilidade(null);
		if(index != -1) {
		this.registros.getCarros().add(index, carroAtualizado);
		}else {
			System.out.println("Falha ao encontrar o carro nos registros");
		}
		
		//adicionando locação e o carro atualizado aos registros
		this.registros.getLocacoes().add(locacao);
		this.registros.salvarRegistros();
		return false;
	}




}
