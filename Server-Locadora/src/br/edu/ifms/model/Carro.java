package br.edu.ifms.model;

import java.util.List;

public class Carro {
	private String placa;
	private String nome;
	private String restricao;
	private List<Locadora> disponibilidade;
	private Double precoPorSegundo;
	
	
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
	public List<Locadora> getDisponibilidade() {
		return disponibilidade;
	}
	public void setDisponibilidade(List<Locadora> disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	public Double getPrecoPorSegundo() {
		return precoPorSegundo;
	}
	public void setPrecoPorSegundo(Double precoPorSegundo) {
		this.precoPorSegundo = precoPorSegundo;
	}
	
	

}
