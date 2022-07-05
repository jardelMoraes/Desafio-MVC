package com.desafio.mvc.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.mvc.entities.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Integer>{

    @Query("select m from Receita m"
		+ " join m.ingredientes e "
		+ "where e.descricao like %:search% ")
	Page<Receita> findByNomeOrTempoPreparoOrRendimento(@Param("search") String search, Pageable pageable);
}
