package com.desafio.mvc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.mvc.dto.ModoPreparoDTO;
import com.desafio.mvc.entities.ModoPreparo;
import com.desafio.mvc.repositories.ModoPreparoRepository;
import com.desafio.mvc.services.exceptions.ObjectNotFoundException;

@Service
public class ModoPreparoService {

    @Autowired
    private ReceitaService receitaService;
    
    @Autowired
    private ModoPreparoRepository modoPreparoRepository;

    @Transactional(readOnly = false)
    public void salvarModoPreparo(Integer receita_id , ModoPreparo modoPreparo) {
        modoPreparo.setId(null);
		modoPreparo.setReceita(receitaService.findById(receita_id));
		modoPreparoRepository.save(modoPreparo);
    }

    @Transactional(readOnly = false)
    public ModoPreparo findById(Integer id) {
        Optional<ModoPreparo> ingrediente = modoPreparoRepository.findById(id);
        return ingrediente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "
        + id + ", tipo: " + ModoPreparo.class.getName() ));
    }

    @Transactional(readOnly = false)
	public void editarModoPreparo(ModoPreparoDTO modoPreparoDTO) {
        
        ModoPreparo editarmModoPreparo = modoPreparoRepository.findById(modoPreparoDTO.getId()).get();
        editarmModoPreparo.setEtapaPreparo(modoPreparoDTO.getEtapaPreparo());
        modoPreparoRepository.save(editarmModoPreparo);
	}

    @Transactional(readOnly = false)
    public void excluirModoPreparo(Integer id) {
        modoPreparoRepository.deleteById(id);
    }


}
