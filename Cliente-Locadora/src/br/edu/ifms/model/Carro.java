package br.edu.ifms.model;

import java.io.Serializable;

public class Carro implements Serializable {
	private String placa;
	private String nome;
	private String restricao;
	private Locadora disponibilidade;
	private Double precoPorSegundo;
	
	
	
	
	public Carro(String placa, String nome, String restricao, Locadora disponibilidade, Double precoPorSegundo) {
		super();
		this.placa = placa;
		this.nome = nome;
		this.restricao = restricao;
		this.disponibilidade = disponibilidade;
		this.precoPorSegundo = precoPorSegundo;
	}
	
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRestricao() {
		return restricao;
	}
	public void setRestricao(String restricao) {
		this.restricao = restricao;
	}
	public Double getPrecoPorSegundo() {
		return precoPorSegundo;
	}
	public void setPrecoPorSegundo(Double precoPorSegundo) {
		this.precoPorSegundo = precoPorSegundo;
	}
	public Locadora getDisponibilidade() {
		return disponibilidade;
	}
	public void setDisponibilidade(Locadora disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
	
	
}
