package com.desafio.mvc.services;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.mvc.dto.IngredienteDTO;
import com.desafio.mvc.entities.Ingrediente;
import com.desafio.mvc.repositories.IngredienteRepository;
import com.desafio.mvc.services.exceptions.ObjectNotFoundException;

@Service
public class IngredienteService {

    @Autowired
    private ReceitaService receitaService;
    
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Transactional(readOnly = false)
    public void salvarIngrediente(Integer receita_id , Ingrediente ingrediente) {
        ingrediente.setId(null);
		ingrediente.setReceita(receitaService.findById(receita_id));
		ingredienteRepository.save(ingrediente);
    }

    @Transactional(readOnly = false)
    public Ingrediente findById(Integer id) {
        Optional<Ingrediente> ingrediente = ingredienteRepository.findById(id);
        return ingrediente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "
        + id + ", tipo: " + Ingrediente.class.getName() ));
    }

    @Transactional(readOnly = false)
	public void editarIngrediente(IngredienteDTO ingredienteDTO) {
        
        Ingrediente editarIngrediente = ingredienteRepository.findById(ingredienteDTO.getId()).get();
        editarIngrediente.setDescricao(ingredienteDTO.getDescricao());
        ingredienteRepository.save(editarIngrediente);
	}

    @Transactional(readOnly = false)
    public void excluirIngrediente(Integer id) {
        ingredienteRepository.deleteById(id);
    }



}
