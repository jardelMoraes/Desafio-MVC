package com.desafio.mvc.dto;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteDTO {

    private Integer id;
    
    @Length(min = 3, max = 100, message = "Campo descricao deve ter entre 3 e 100 caracteres")
    private String descricao;

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", descricao='" + descricao +
                '}';
    }
}

