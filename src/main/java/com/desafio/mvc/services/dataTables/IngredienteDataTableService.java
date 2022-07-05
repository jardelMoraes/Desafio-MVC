package com.desafio.mvc.services.dataTables;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.desafio.mvc.entities.Ingrediente;
import com.desafio.mvc.repositories.IngredienteRepository;


@Service
public class IngredienteDataTableService {

    private String[] cols = {"id", "descricao"};

    public Map<String, Object> execute(Integer receita_id, IngredienteRepository ingredienteRepository, HttpServletRequest request)  {

        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int draw = Integer.parseInt(request.getParameter("draw"));

        int current = currentPage(start, length);

        String colunm = colunmName(request);
        Sort.Direction direction = orderBy(request);
        String search = searchBy(request);

        Pageable pageable = PageRequest.of(current, length, direction, colunm); 

        Page<Ingrediente> page = queryBy(receita_id, search, ingredienteRepository, pageable);

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());

        return json;
    }

    private Page<Ingrediente> queryBy(Integer receita_id, String search , IngredienteRepository ingredienteRepository, Pageable pageable) {

        if (search.isEmpty()) {
			return ingredienteRepository.findAllByReceita(receita_id, pageable);
		}

        return ingredienteRepository.findByIdOrDescricao(search, pageable);
    }

    private String searchBy(HttpServletRequest request) {
		
        return request.getParameter("search[value]").isEmpty()
                ? ""
                : request.getParameter("search[value]");
    }

    private Sort.Direction orderBy(HttpServletRequest request) {
        String order = request.getParameter("order[0][dir]"); 
        Sort.Direction sort = Sort.Direction.ASC;
        if (order.equals("desc")) {
            sort = Sort.Direction.DESC;
        }
        return sort;
    }

    private String colunmName(HttpServletRequest request) {
        int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
        return cols[iCol];
    }

    private int currentPage(int start, int length) {
        return start / length;
    }
        


    
}
