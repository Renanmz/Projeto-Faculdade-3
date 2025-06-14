package com.api.Agro.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.Agro.model.DoencaModel;
import com.api.Agro.model.FazendaModel;
import com.api.Agro.model.OcorrenciaModel;
import com.api.Agro.repository.DoencaRepository;
import com.api.Agro.repository.FazendaRepository;
import com.api.Agro.repository.OcorrenciaRepository;

import jakarta.annotation.PostConstruct;

@Component
public class OcorrenciaUtils {

	@Autowired 
	OcorrenciaRepository ocorrenciarepository;

	@Autowired
	DoencaRepository doencaRepository;

	@Autowired
	FazendaRepository fazendaRepository;
	
	//@PostConstruct
	public void saveOcorrencias() {
		
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
		
		doenca1 = doencaRepository.save(doenca1);
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

		 doenca2 = doencaRepository.save(doenca2);
		
FazendaModel fazenda1 = new FazendaModel();
		
		fazenda1.setNomefazenda("Alquioda Farm");
		fazenda1.setCidade("Stardew Valley");
		fazenda1.setRegiao("Brasil, Sudeste, São Paulo");
		fazenda1.setData(LocalDate.now());

		fazenda1 = fazendaRepository.save(fazenda1); 
		
		FazendaModel fazenda2 = new FazendaModel();
		
		fazenda2.setNomefazenda("Paloma Farm");
		fazenda2.setCidade("Stardew Valley");
		fazenda2.setRegiao("Brasil, Sudeste, Rio de Janeiro");
		fazenda2.setData(LocalDate.now());

		fazenda2 = fazendaRepository.save(fazenda2); 
		
		List<OcorrenciaModel> ocorrenciaList = new ArrayList<>();
		OcorrenciaModel ocorrencia1 = new OcorrenciaModel();
		
		ocorrencia1.setNumero("0001");
		ocorrencia1.setEstacao("Outono");
		ocorrencia1.setClima("Nublado");
		ocorrencia1.setDoenca(doenca1);
		ocorrencia1.setFazenda(fazenda1);		
		ocorrencia1.setData(LocalDate.now());
		ocorrencia1.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		
		OcorrenciaModel ocorrencia2 = new OcorrenciaModel();
		
		ocorrencia2.setNumero("0002");
		ocorrencia2.setEstacao("Primavera");
		ocorrencia2.setClima("Ensolarado");
		ocorrencia2.setDoenca(doenca2);
		ocorrencia2.setFazenda(fazenda2);
		ocorrencia2.setData(LocalDate.now());
		ocorrencia2.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

		ocorrenciaList.add(ocorrencia1);
        ocorrenciaList.add(ocorrencia2);
		
        for(OcorrenciaModel ocorrencia: ocorrenciaList){
        	OcorrenciaModel ocorrenciaSaved = ocorrenciarepository.save(ocorrencia);
            System.out.println(ocorrenciaSaved.getId());
        }
	}
}