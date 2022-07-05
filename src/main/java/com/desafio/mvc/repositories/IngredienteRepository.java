package com.desafio.mvc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.mvc.entities.Ingrediente;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {

    @Query("select p from Ingrediente p where p.descricao like %:search% ")
    Page<Ingrediente> findByIdOrDescricao(@Param("search") String search, Pageable pageable);

    @Query("select p from Ingrediente p where p.receita.id = :receita_id")
    Page<Ingrediente> findAllByReceita(Integer receita_id, Pageable pageable);

    
    List<Ingrediente> findAllByReceitaId(Integer receita_id);
}
    
