package com.desafio.mvc.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.desafio.mvc.repositories.IngredienteRepository;
import com.desafio.mvc.repositories.ModoPreparoRepository;
import com.desafio.mvc.services.ReceitaService;

@Controller
public class PrincipalController {

	@Autowired
	private ReceitaService receitaService;

	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private ModoPreparoRepository modoPreparoRepository;

    // abrir pagina home
	@GetMapping({"/", "/home"})
	public String home(ModelMap model) {
		model.addAttribute("receita", receitaService.findAll());
		return "home";
	}
	
	@GetMapping({"/{id}"})
	public String visualizacaoReceita(@PathVariable("id") Integer receita_id, ModelMap model) {
		model.addAttribute("receita", receitaService.findById(receita_id))
		;
		model.addAttribute("ingredientes", ingredienteRepository.findAllByReceitaId(receita_id));

		model.addAttribute("modoPreparo", modoPreparoRepository.findAllByReceitaId(receita_id));

		return "detalhesReceita";
	}
	

	// abrir pagina login
	@GetMapping({"/login"})
	public String login() {
		
		return "login";
	}

    @GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Crendenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}

	@GetMapping({"/acesso-negado"})
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
		return "error";
	}
}
