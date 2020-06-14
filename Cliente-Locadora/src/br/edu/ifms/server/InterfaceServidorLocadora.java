package br.edu.ifms.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ifms.model.Cliente;

public interface InterfaceServidorLocadora extends Remote {
	public boolean autenticar(String login, String senha) throws RemoteException;
	public boolean cadastrarCliente(Cliente cliente) throws RemoteException;
}
