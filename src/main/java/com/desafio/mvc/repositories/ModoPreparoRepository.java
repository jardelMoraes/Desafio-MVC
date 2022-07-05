package com.desafio.mvc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.mvc.entities.ModoPreparo;

@Repository
public interface ModoPreparoRepository extends JpaRepository<ModoPreparo, Integer> {

    @Query("select p from ModoPreparo p where p.etapaPreparo like %:search% ")
    Page<ModoPreparo> findByIdOrDescricao(@Param("search") String search, Pageable pageable);

    @Query("select p from ModoPreparo p where p.receita.id = :receita_id")
    Page<ModoPreparo> findAllByReceita(Pageable pageable, Integer receita_id);

    
    List<ModoPreparo> findAllByReceitaId(Integer receita_id);
}
    
