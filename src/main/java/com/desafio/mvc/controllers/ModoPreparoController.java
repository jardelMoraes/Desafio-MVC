package com.desafio.mvc.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desafio.mvc.dto.ModoPreparoDTO;
import com.desafio.mvc.entities.ModoPreparo;
import com.desafio.mvc.repositories.ModoPreparoRepository;
import com.desafio.mvc.services.ModoPreparoService;
import com.desafio.mvc.services.dataTables.ModoPreparoDataTableService;



@Controller
@RequestMapping("receitas/gerenciadorUnitario")
public class ModoPreparoController {

    @Autowired
    private ModoPreparoRepository modoPreparoRepository;

    @Autowired
    private ModoPreparoService modoPreparoService;

    @GetMapping("/gerenciadorUnitario/listarModosPreparo/{id}")
    public ResponseEntity<?> dataTablesIngredientes(@PathVariable("id") Integer receita_id, HttpServletRequest request) {
        Map<String, Object> data = new ModoPreparoDataTableService().execute(receita_id, modoPreparoRepository, request);
        System.out.println(data);
        
        return ResponseEntity.ok(data);
    }

    @PostMapping("/gerenciadorUnitario/criarModoPreparo/{id}")
    public ResponseEntity<?> salvarModoPreparo(@PathVariable("id") Integer receita_id, @Valid ModoPreparo modoPreparo, BindingResult result) {
		
		if (result.hasErrors()) {
			
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			
			return ResponseEntity.unprocessableEntity().body(errors);
		}

        modoPreparoService.salvarModoPreparo(receita_id, modoPreparo);

		return ResponseEntity.ok().build();
	}

    @GetMapping("gerenciadorUnitario/editarModoPreparo/{id}")
    public ResponseEntity<?> RecuperarDadosModoPreparo(@PathVariable("id") Integer id) {
         ModoPreparo obj = modoPreparoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("gerenciadorUnitario/editarModoPreparo")
	public ResponseEntity<?> editarIngrediente(@Valid ModoPreparoDTO modoPreparoDTO, BindingResult result) {

       

        if (result.hasErrors()) {
        
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        modoPreparoService.editarModoPreparo(modoPreparoDTO);
        
		return ResponseEntity.ok().build();
	}

    @GetMapping("gerenciadorUnitario/excluirModoPreparo/{id}")
    public ResponseEntity<?> excluirModoPreparo(@PathVariable("id") Integer id) {
        modoPreparoService.excluirModoPreparo(id);
        return ResponseEntity.ok().build();
    }
}
