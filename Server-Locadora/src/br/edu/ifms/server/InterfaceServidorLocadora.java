package br.edu.ifms.server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
public interface InterfaceServidorLocadora  extends Remote {
	public boolean autenticar(String login, String senha) throws RemoteException, ClassNotFoundException, IOException;
	public boolean cadastrarCliente(Cliente cliente) throws RemoteException, ClassNotFoundException, IOException;
	public List<Carro> listarCarrosDisponiveis() throws RemoteException, ClassNotFoundException;
}
