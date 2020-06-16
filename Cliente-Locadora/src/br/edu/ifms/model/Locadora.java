package br.edu.ifms.model;

import java.io.Serializable;

public class Locadora implements Serializable{
	private Long ID;
	private String nome;
	private String login;
	private String senha;
	
	
	
	
	public Locadora(Long iD, String nome, String login, String senha) {
		super();
		ID = iD;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	

}
