package br.edu.ifms.model;

import java.io.Serializable;
import java.util.Date;

public class Locacao implements Serializable {
	private Date horarioLocacao, horarioDevolucao;
	private Double valor;
	private Long clienteID, locadoraRetiradaID, locadoraDevolucaoID;
	private String carroID;


	public Date getHorarioLocacao() {
		return horarioLocacao;
	}
	public void setHorarioLocacao(Date horarioLocacao) {
		this.horarioLocacao = horarioLocacao;
	}
	public Date getHorarioDevolucao() {
		return horarioDevolucao;
	}
	public void setHorarioDevolucao(Date horarioDevolucao) {
		this.horarioDevolucao = horarioDevolucao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Long getClienteID() {
		return clienteID;
	}
	public void setClienteID(Long clienteID) {
		this.clienteID = clienteID;
	}
	public String getCarroID() {
		return carroID;
	}
	public void setCarroID(String carroID) {
		this.carroID = carroID;
	}
	public Long getLocadoraRetiradaID() {
		return locadoraRetiradaID;
	}
	public void setLocadoraRetiradaID(Long locadoraRetiradaID) {
		this.locadoraRetiradaID = locadoraRetiradaID;
	}
	public Long getLocadoraDevolucaoID() {
		return locadoraDevolucaoID;
	}
	public void setLocadoraDevolucaoID(Long locadoraDevolucaoID) {
		this.locadoraDevolucaoID = locadoraDevolucaoID;
	}


}
