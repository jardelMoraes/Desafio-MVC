package com.desafio.mvc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModoPreparo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 3, max = 100, message = "Campo Etapa de preparo deve ter entre 3 e 100 caracteres")
    private String etapaPreparo;

    @ManyToOne
    private Receita receita;

    @Override
    public String toString() {
        return "ModoPreparo{" +
                "id=" + id +
                ", descricao='" + etapaPreparo +
                '}';
    }

}
