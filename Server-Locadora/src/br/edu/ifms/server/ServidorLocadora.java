package br.edu.ifms.server;

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;

import Armazenamento.Registros;
import br.edu.ifms.model.Cliente;
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

	
	

}
