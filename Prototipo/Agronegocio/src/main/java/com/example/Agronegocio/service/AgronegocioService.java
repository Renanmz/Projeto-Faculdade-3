package com.example.Agronegocio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Agronegocio.model.AgronegocioPostModel;
import com.example.Agronegocio.repository.AgronegocioPostRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;


@Service
public class AgronegocioService {

	
	@Autowired // para ser o constructor da classe
	AgronegocioPostRepository agronegociorepository;
	
	public List<AgronegocioPostModel> findAll() {

		return agronegociorepository.findAll();
	}


	public Optional<AgronegocioPostModel> findById(long id) {

	return agronegociorepository.findById(id); 

	}

	@Transactional //se der algo errado na transação vai cancelar tudo e voltar os dados inteiros
	public AgronegocioPostModel save(AgronegocioPostModel post) {

		return agronegociorepository.save(post);
	}

	@Transactional
	public void delete(AgronegocioPostModel post) {
		agronegociorepository.delete(post);
		
	}
}
