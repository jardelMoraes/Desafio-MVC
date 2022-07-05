package com.desafio.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.mvc.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

}
    
