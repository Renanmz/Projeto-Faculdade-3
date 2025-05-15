package com.api.Agro.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.Agro.model.AgronegocioPostModel;
import com.api.Agro.repository.AgronegocioPostRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataUtils {
	
	@Autowired // para ser o constructor da classe
	AgronegocioPostRepository agronegociorepository;
	
	//@PostConstruct
		public void savePosts() {
			
			List<AgronegocioPostModel> postList = new ArrayList<>();
			AgronegocioPostModel post1 = new AgronegocioPostModel();
			
			post1.setNomefazenda("Alquioda Farm");
			post1.setCidade("Stardew Valley");
			post1.setNome("Ergostismo");
			post1.setTipo("Doença");
			post1.setEstacao("Outono");
			post1.setRegiao("Brasil, Sudeste, São Paulo");
			post1.setClima("Nublado");
			post1.setData(LocalDate.now());
			post1.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
	        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
	        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
	        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
	        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
	        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
	        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
			
			AgronegocioPostModel post2 = new AgronegocioPostModel();
			
			post2.setNomefazenda("Paloma Farm");
			post2.setCidade("Stardew Valley");
			post2.setNome("Oídio");
			post2.setTipo("Doença");
			post2.setEstacao("Primavera");
			post2.setRegiao("Brasil, Sudeste, Rio de Janeiro");
			post2.setClima("Ensolarado");
			post2.setData(LocalDate.now());
			post2.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
	        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
	        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
	        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
	        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
	        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
	        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

			postList.add(post1);
	        postList.add(post2);
	        
	        for(AgronegocioPostModel post: postList){
	        	AgronegocioPostModel postSaved = agronegociorepository.save(post);
	            System.out.println(postSaved.getId());
	        }
		}

}
