package br.edu.ifms.cadastros;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import Armazenamento.Registros;
import br.edu.ifms.model.Carro;
import br.edu.ifms.model.Locadora;

public class Cadastros {

	static Scanner ler = new Scanner(System.in);
	static Registros r = new Registros();


	//Menu terminal de cadastros do servidor
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		System.out.println("Bem-vindo!\nInforme a operação desejada:\n1 - Cadastro de carros\n2 - Cadastro de locadoras\n3 - Sair");
		int opt = ler.nextInt();
		while(opt!=3) {
			switch(opt) {
			case 1:
				CadastrarCarros();
				break;

			case 2:
				CadastrarLocadoras();
				break;

			case 3:
				break;
			}

			System.out.println("Bem-vindo!\nInforme a operação desejada:\n1 - Cadastro de carros\n2 - Cadastro de locadoras\n3 - Sair");
			opt = ler.nextInt();
		}


	}

	//Função para cadastro de carros
	public static void CadastrarCarros() throws ClassNotFoundException, IOException {
		r = r.recuperarRegistros();

		System.out.println("------ CADASTRO CARROS ------\nModelo > ");
		ler.nextLine();
		String nome = ler.nextLine();
		System.out.println("Placa > ");
		String placa = ler.nextLine();

		Boolean teste = false;
		List<Carro> carros = r.getCarros();

		//Checa se a Placa do novo carro ainda está disponível
		while(teste == false) {
			teste = true;
			for(Carro c : carros) {
				if(placa.equals(c.getPlaca()) ) {
					teste=false;
					System.out.println("Placa já existente, informe nova!");
					System.out.println("Placa > ");
					placa = ler.nextLine();
				}				
			}
		}

		System.out.println("Preço/s > ");
		Double precoPorSegundo = ler.nextDouble();
		System.out.println("Restrição > ");
		ler.nextLine();
		String restricao = ler.nextLine();
		Locadora disponibilidade = null;

		//Checa se o login existe
		while(disponibilidade==null) {
			System.out.println("Locadora(Login) > ");
			String loginLocadora = ler.nextLine();

			disponibilidade = r.getLocadora(loginLocadora);

			if(disponibilidade == null)
				System.out.println("Locadora não encontrada, informe um login válido.");
		}



		Carro novoCarro = new Carro(placa, nome, restricao, disponibilidade, precoPorSegundo);

		//Salva o novo carro nos registros
		r.getCarros().add(novoCarro);
		r.salvarRegistros();

		System.out.println("Carro Salvo com Sucesso!");

	}


	//Método de cadastro de locadoras
	public static void CadastrarLocadoras() throws ClassNotFoundException, IOException {
		r = r.recuperarRegistros();

		System.out.println("------ CADASTRO LOCADORAS ------\nNome > ");
		ler.nextLine();
		String nome = ler.nextLine();

		System.out.println("Login > ");
		ler.nextLine();
		String login = ler.nextLine();
		System.out.println("Senha > ");
		String senha = ler.nextLine();
		System.out.println("ID > ");
		Long id = ler.nextLong();
		List<Locadora> locadoras = r.getLocadoras();
		Boolean teste = false;

		//Checa se o ID da nova locadora está disponível
		while(teste == false) {
			teste = true;
			for(Locadora l : locadoras) {
				if(id == (long)l.getID()) {
					teste=false;
					System.out.println("ID já existente, informe novo!");
					System.out.println("ID > ");
					id = ler.nextLong();
				}				
			}
		}

		Locadora novaLocadora = new Locadora(id, nome, login, senha);



		//Salva a locadora nos registros
		r.getLocadoras().add(novaLocadora);
		r.salvarRegistros();

		System.out.println("Locadora Salva com Sucesso!");

	}

}
