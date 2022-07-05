package com.desafio.mvc.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Boolean ativo;
    private String email;
    private String senha;

    @ManyToMany
    private List<Perfil> perfis;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Receita> receitas;

    public Usuario() {
		super();
	}


    public Usuario(Integer id, boolean ativo, String name, String email, String senha, List<Perfil> perfis) {
        this.id = id;
        this.ativo = ativo;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.perfis = perfis;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    //Adiciona um novo perfil ao usuário
    public void addPerfil(PerfilTipo perfil) {
        if(this.perfis == null) {
            this.perfis = new ArrayList<>();
        }
        this.perfis.add(new Perfil(perfil.getCodigo()));
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ativo=" + ativo +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfis=" + perfis +
                '}';
    }
}
