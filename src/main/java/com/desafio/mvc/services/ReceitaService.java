package com.desafio.mvc.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.mvc.dto.ReceitaDTO;
import com.desafio.mvc.entities.Receita;
import com.desafio.mvc.entities.Usuario;
import com.desafio.mvc.repositories.ReceitaRepository;
import com.desafio.mvc.services.exceptions.ObjectNotFoundException;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    Principal principal;

    @Transactional(readOnly = false)
	public void salvar(Receita receita) {
        receita.setId(null);	
		receitaRepository.save(receita);
	}

    @Transactional(readOnly = false)
	public void editarReceita(ReceitaDTO receitaDTO, Usuario usuarioCorrente) {


        Receita editarReceita = receitaRepository.findById(receitaDTO.getId()).get();
        editarReceita.setNome(receitaDTO.getNome());
        editarReceita.setTempoPreparo(receitaDTO.getTempoPreparo());
        editarReceita.setRendimento(receitaDTO.getRendimento());
        editarReceita.setUsuario(usuarioCorrente);
        receitaRepository.save(editarReceita);
	}

    @Transactional(readOnly = false)
    public void excluirReceita(Integer id) {
        receitaRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public Receita findById(Integer id) {
        Optional<Receita> receita = receitaRepository.findById(id);
        return receita.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "
        + id + ", tipo: " + Receita.class.getName() ));
    }

    @Transactional(readOnly = false)
    public List<Receita> findAll() {
        return receitaRepository.findAll();
    }

    
}
