package com.api.Agro.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.Agro.model.DoencaModel;

import com.api.Agro.repository.DoencaRepository;

import jakarta.annotation.PostConstruct;


@Component
public class DoencaUtils {

	@Autowired 
	DoencaRepository doencarepository;

	
	//@PostConstruct
	public void saveDoencas() {
		
		List<DoencaModel> doencaList = new ArrayList<>();
		DoencaModel doenca1 = new DoencaModel();
		

		doenca1.setNome("Ergostismo");
		doenca1.setTipo("Doença");
		doenca1.setData(LocalDate.now());
		doenca1.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		
		DoencaModel doenca2 = new DoencaModel();
		

		doenca2.setNome("Oídio");
		doenca2.setTipo("Doença");

		doenca2.setData(LocalDate.now());
		doenca2.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

		doencaList.add(doenca1);
        doencaList.add(doenca2);
		
        for(DoencaModel doenca: doencaList){
        	DoencaModel doencaSaved = doencarepository.save(doenca);
            System.out.println(doencaSaved.getId());
        }
	}
}
