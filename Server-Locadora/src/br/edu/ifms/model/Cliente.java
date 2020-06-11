package br.edu.ifms.model;

public class Cliente {
	private Long id;
	private String nome;
	private char[] categoriaHabilitacao;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public char[] getCategoriaHabilitacao() {
		return categoriaHabilitacao;
	}
	public void setCategoriaHabilitacao(char[] categoriaHabilitacao) {
		this.categoriaHabilitacao = categoriaHabilitacao;
	}

}
