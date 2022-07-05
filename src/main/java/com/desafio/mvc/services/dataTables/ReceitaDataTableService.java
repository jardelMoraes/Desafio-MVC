package com.desafio.mvc.services.dataTables;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.desafio.mvc.entities.Receita;
import com.desafio.mvc.repositories.ReceitaRepository;


@Service
public class ReceitaDataTableService {



    private String[] cols = {"id", "nome", "rendimento", "tempoPreparo",  "ingredientes","modosPreparo", "rendimento",  "usuario" }; 

    public Map<String, Object> execute(ReceitaRepository receitaRepository, HttpServletRequest request)  {

        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int draw = Integer.parseInt(request.getParameter("draw"));

        int current = currentPage(start, length);

        String colunm = colunmName(request);
        Sort.Direction direction = orderBy(request);
        String search = searchBy(request);

        Pageable pageable = PageRequest.of(current, length, direction, colunm); 

        Page<Receita> page = queryBy(search, receitaRepository, pageable);

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent().stream().map(ReceitaDataTableService::toMap).toArray());

        return json;
    }

    private Page<Receita> queryBy(String search ,ReceitaRepository receitaRepository, Pageable pageable) {

        if (search.isEmpty()) {
			return receitaRepository.findAll(pageable);
		}

        return receitaRepository.findByNomeOrTempoPreparoOrRendimento(search, pageable);
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

    private static <R> R toMap(Receita receita1) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", receita1.getId());
        map.put("nome", receita1.getNome());
        map.put("rendimento", receita1.getRendimento());
        map.put("tempoPreparo", receita1.getTempoPreparo());
        map.put("ingredientes", receita1.getIngredientes());
        map.put("modosPreparo", receita1.getModosPreparo());
        map.put("usuario", receita1.getUsuario().getName());
        return (R) map;
    }
        
}
