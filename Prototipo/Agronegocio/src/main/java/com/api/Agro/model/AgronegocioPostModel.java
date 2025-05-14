package com.api.Agro.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_POST")
public class AgronegocioPostModel implements Serializable{

	public static final long serialVersionUID = 1L;
	
	/*nome da fazenda;
	cidade;
	qual a PRAGA ou a DOENÇA;
	região do acontecimento;
	data (estação do ano);
	clima atual;*/
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //geração automatica do id
	private long id;
	
	@Column(nullable = false, length = 70)
	private String nomefazenda;
	
	@Column(nullable = false, length = 70)
	private String cidade;
	
	@Column(nullable = false, length = 70)
	private String doenca;
	
	@Column(nullable = false, length = 200)
	private String regiao;
	
	@Column(nullable = false, length = 70)
	private String estacao;
	
	@Column(nullable = false, length = 70)
	private String clima;
	
	@Lob // para o banco aceitar texto grande e ter boa performace
	@Column(columnDefinition = "text")
	private String descricao;
	
	@Column(nullable = false)
	private LocalDate data;
	
	
	
	

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

	public String getDoenca() {
		return doenca;
	}

	public void setDoenca(String doenca) {
		this.doenca = doenca;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
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

	
	public LocalDate getData() {
		return data;
	}
	

	public void setData(LocalDate data) {
		this.data = data;
	}
	




	
	
}
