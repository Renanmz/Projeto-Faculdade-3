package com.api.Agro.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbOcurrencia")
public class OcorrenciaModel implements Serializable  {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //geração automatica do id
	private long id;
	
	@Column(nullable = false, length = 70)
	private String estacao;
	
	@Column(nullable = false, length = 70)
	private String clima;
	
	@Lob 
	@Column(columnDefinition = "text")
	private String descricao;
	
	@ManyToOne
    @JoinColumn(name = "fazenda_id")
    private FazendaModel fazenda;

    @ManyToOne
    @JoinColumn(name = "doenca_id")
    private DoencaModel doenca;
	
	@Column(nullable = false)
	private LocalDate data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstacao() {
		return estacao;
	}

	public void setEstacao(String estacao) {
		this.estacao = estacao;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public FazendaModel getFazenda() {
		return fazenda;
	}

	public void setFazenda(FazendaModel fazenda) {
		this.fazenda = fazenda;
	}

	public DoencaModel getDoenca() {
		return doenca;
	}

	public void setDoenca(DoencaModel doenca) {
		this.doenca = doenca;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
	
}
