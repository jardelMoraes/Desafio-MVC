package com.desafio.mvc.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 3, max = 100, message = "Campo Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Length(min = 3, max = 100, message = "Campo Tempo de preparo deve ter entre 3 e 100 caracteres") 
    private String tempoPreparo;

    @Length(min = 3, max = 100, message = "Campo Rendimento deve ter entre 3 e 100 caracteres") 
    private String rendimento;
    
    @JsonIgnore
    @OneToMany(mappedBy = "receita")
    private List<Ingrediente> ingredientes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "receita")
    private List<ModoPreparo> modosPreparo = new ArrayList<>();
    
    @ManyToOne
    private Usuario usuario;

    public Receita(Integer id, String nome, String tempoPreparo, String rendimento, List<Ingrediente> ingredientes, List<ModoPreparo> modosPreparo, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.tempoPreparo = tempoPreparo;
        this.rendimento = rendimento;
        this.ingredientes = ingredientes;
        this.modosPreparo = modosPreparo;
        //this.usuario = usuario;
    }

    public Receita() {
    }

    public Receita(Integer id, String nome, String tempoPreparo, String rendimento, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.tempoPreparo = tempoPreparo;
        this.rendimento = rendimento;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(String tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getRendimento() {
        return rendimento;
    }

    public void setRendimento(String rendimento) {
        this.rendimento = rendimento;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<ModoPreparo> getModosPreparo() {
        return modosPreparo;
    }

    public void setModosPreparo(List<ModoPreparo> modosPreparo) {
        this.modosPreparo = modosPreparo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Receita{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tempoPreparo='" + tempoPreparo + '\'' +
                ", rendimento='" + rendimento + '\'' +
                ", ingredientes=" + ingredientes +
                ", modosPreparo=" + modosPreparo +
                '}';
    }
}
