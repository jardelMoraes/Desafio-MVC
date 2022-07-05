package com.desafio.mvc.controllers;

import java.security.Principal;
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

import com.desafio.mvc.dto.ReceitaDTO;
import com.desafio.mvc.entities.Receita;
import com.desafio.mvc.entities.Usuario;
import com.desafio.mvc.repositories.ReceitaRepository;
import com.desafio.mvc.services.ReceitaService;
import com.desafio.mvc.services.UsuarioService;
import com.desafio.mvc.services.dataTables.ReceitaDataTableService;

@Controller
@RequestMapping("receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ReceitaRepository receitaRepository;

    @GetMapping({"", "/", "/gerenciadorReceitas"})
	public String abrir(Receita receita) {

		return "/receitas/gerenciadorReceitas";
	}

    @GetMapping("/gerenciadorReceitas/listar")
    public ResponseEntity<?> dataTables(HttpServletRequest request) {
        
        Map<String, Object> data = new ReceitaDataTableService().execute(receitaRepository, request);
        System.out.println(data);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/gerenciadorReceitas/delete/{id}")
    public ResponseEntity<?> excluirReceita(@PathVariable("id") Integer id) {
        receitaService.excluirReceita(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("gerenciadorReceitas/editar/{id}")
    public ResponseEntity<?> RecuperarDadosReceita(@PathVariable("id") Integer id) {
         Receita obj = receitaService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/gerenciadorReceitas/editar")
	public ResponseEntity<?> editarReceita(@Valid ReceitaDTO receitaDTO, BindingResult result, Principal principal) {

        if (result.hasErrors()) {
        
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        Usuario usuarioCorrente = usuarioService.buscarPorEmail(principal.getName());
        
		receitaService.editarReceita(receitaDTO, usuarioCorrente);
		
		return ResponseEntity.ok().build();
	}
    
    @PostMapping("/gerenciadorReceitas/salvar")
	public ResponseEntity<?> salvar(@Valid Receita receita, BindingResult result, Principal principal) {

        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        receita.setUsuario(usuarioService.buscarPorEmail(principal.getName()));

		receitaService.salvar(receita);

		return ResponseEntity.ok().build();
	}
    
}
