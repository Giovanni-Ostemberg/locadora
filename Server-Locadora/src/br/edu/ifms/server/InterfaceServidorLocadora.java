package br.edu.ifms.server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Cliente;
import br.edu.ifms.model.Locacao;
import br.edu.ifms.model.Locadora;
public interface InterfaceServidorLocadora  extends Remote {
	public boolean autenticar(String login, String senha) throws RemoteException, ClassNotFoundException, IOException;
	public boolean cadastrarCliente(Cliente cliente) throws RemoteException, ClassNotFoundException, IOException;
	public boolean novaLocacao(Locacao locacao, Carro carro)  throws RemoteException, ClassNotFoundException, IOException;
	public List<Carro> listarCarrosDisponiveis() throws RemoteException, ClassNotFoundException;
	public List<Carro> listarTodosCarros() throws RemoteException, ClassNotFoundException;
	public List<Locadora> listarLocadoras() throws RemoteException, ClassNotFoundException;
	public List<Cliente> listarClientes()  throws RemoteException, ClassNotFoundException;
	public List<Locacao> listarLocacoes() throws RemoteException, ClassNotFoundException;
}
