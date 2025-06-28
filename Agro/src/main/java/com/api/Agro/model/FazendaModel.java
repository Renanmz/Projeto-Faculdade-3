package com.api.Agro.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tbfazenda")

public class FazendaModel implements Serializable  {

	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //geração automatica do id
	private long id;
	
	@Column(nullable = false, length = 70)
	private String nomefazenda;
	
	@Column(nullable = false, length = 70)
	private String cidade;
	
	@Column(nullable = false, length = 200)
	private String regiao;
	
	@Column(nullable = false)
	private LocalDate data;

	@OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OcorrenciaModel> ocorrencias;
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomefazenda() {
		return nomefazenda;
	}

	public void setNomefazenda(String nomefazenda) {
		this.nomefazenda = nomefazenda;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	
	
}
