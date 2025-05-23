package com.api.Agro.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.Agro.model.FazendaModel;
import com.api.Agro.repository.FazendaRepository;

import jakarta.annotation.PostConstruct;

@Component
public class FazendaUtils {
	
	@Autowired
	FazendaRepository fazendarepository;
	
	//@PostConstruct
	public void saveFazendas() {
		
		List<FazendaModel> fazendaList = new ArrayList<>();
		FazendaModel fazenda1 = new FazendaModel();
		

		fazenda1.setNomefazenda("Alquioda Farm");
		fazenda1.setCidade("Stardew Valley");
		fazenda1.setRegiao("Brasil, Sudeste, São Paulo");
		fazenda1.setData(LocalDate.now());

		FazendaModel fazenda2 = new FazendaModel();
		
		fazenda2.setNomefazenda("Paloma Farm");
		fazenda2.setCidade("Stardew Valley");
		fazenda2.setRegiao("Brasil, Sudeste, Rio de Janeiro");
		fazenda2.setData(LocalDate.now());

		fazendaList.add(fazenda1);
		fazendaList.add(fazenda2);
		
        for(FazendaModel fazenda: fazendaList){
        	FazendaModel fazendaSaved = fazendarepository.save(fazenda);
            System.out.println(fazendaSaved.getId());
        }
	}
}
