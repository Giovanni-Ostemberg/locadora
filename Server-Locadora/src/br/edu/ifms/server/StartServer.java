package br.edu.ifms.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Armazenamento.Registros;

public class StartServer {

	public static void main(String[] args) {
		{
			try
			{
				System.out.println("Starting server...");
				/*Registros registros = new Registros();
				registros.recuperarRegistros();*/
				Registry registry = LocateRegistry.createRegistry(8888);
				Naming.rebind("rmi://localhost:8888/ServidorLocadora", new ServidorLocadora());
				System.out.println("Servidor Iniciado!");
			}
			catch (Exception e)
			{
				System.out.println("Erro:\n"+e.toString());
			}

		}

	}
}
