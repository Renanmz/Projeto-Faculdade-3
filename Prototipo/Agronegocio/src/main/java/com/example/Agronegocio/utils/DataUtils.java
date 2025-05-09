package com.example.Agronegocio.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Agronegocio.model.AgronegocioPostModel;
import com.example.Agronegocio.repository.AgronegocioPostRepository;

@Component
public class DataUtils {
	
	@Autowired // para ser o constructor da classe
	AgronegocioPostRepository agronegociorepository;
	
	//@PostConstruct
		public void savePosts() {
			
			List<AgronegocioPostModel> postList = new ArrayList<>();
			AgronegocioPostModel post1 = new AgronegocioPostModel();
		}

}
