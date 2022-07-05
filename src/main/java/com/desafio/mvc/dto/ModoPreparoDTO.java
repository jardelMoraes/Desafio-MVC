package com.desafio.mvc.dto;


import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModoPreparoDTO {
   
    private Integer id;

    @Length(min = 3, max = 100, message = "Campo Etapa de preparo deve ter entre 3 e 100 caracteres")
    private String etapaPreparo;

    @Override
    public String toString() {
        return "ModoPreparo{" +
                "id=" + id +
                ", descricao='" + etapaPreparo +
                '}';
    }
}
