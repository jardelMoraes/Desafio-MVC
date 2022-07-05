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

import com.desafio.mvc.dto.IngredienteDTO;
import com.desafio.mvc.entities.Ingrediente;
import com.desafio.mvc.repositories.IngredienteRepository;
import com.desafio.mvc.services.IngredienteService;
import com.desafio.mvc.services.dataTables.IngredienteDataTableService;



@Controller
@RequestMapping("receitas/gerenciadorUnitario")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping("/{id}")
	public String abrir(@PathVariable("id") Integer id, Ingrediente ingrediente) {
		
		return "/receitas/gerenciadorUnitario";
	}

    @GetMapping("/gerenciadorUnitario/listarIngredientes/{id}")
    public ResponseEntity<?> dataTablesIngredientes(@PathVariable("id") Integer receita_id, HttpServletRequest request) {
        Map<String, Object> data = new IngredienteDataTableService().execute(receita_id, ingredienteRepository, request);
        System.out.println(data);
        
        return ResponseEntity.ok(data);
    }

    @PostMapping("/gerenciadorUnitario/criarIngrediente/{id}")
    public ResponseEntity<?> salvarIngrediente(@PathVariable("id") Integer receita_id, @Valid Ingrediente ingrediente, BindingResult result) {
		
		if (result.hasErrors()) {
			
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			
			return ResponseEntity.unprocessableEntity().body(errors);
		}

        ingredienteService.salvarIngrediente(receita_id, ingrediente);

		return ResponseEntity.ok().build();
	}

    @GetMapping("gerenciadorUnitario/editarIngrediente/{id}")
    public ResponseEntity<?> RecuperarDadosIngrediente(@PathVariable("id") Integer id) {
         Ingrediente obj = ingredienteService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("gerenciadorUnitario/editarIngrediente")
	public ResponseEntity<?> editarIngrediente(@Valid IngredienteDTO ingredienteDTO, BindingResult result) {

       

        if (result.hasErrors()) {
        
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        ingredienteService.editarIngrediente(ingredienteDTO);
        
		return ResponseEntity.ok().build();
	}

    @GetMapping("gerenciadorUnitario/excluirIngrediente/{id}")
    public ResponseEntity<?> excluirIngrediente(@PathVariable("id") Integer id) {
        ingredienteService.excluirIngrediente(id);
        return ResponseEntity.ok().build();
    }
}
