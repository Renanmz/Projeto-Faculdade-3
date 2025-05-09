package com.example.Agronegocio.model;

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
	
	@Lob // para o banco aceitar texto grande e ter boa performace
	@Column(columnDefinition = "text")
	private String texto;
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	
	
	
	
	
}
