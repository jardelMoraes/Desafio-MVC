package com.desafio.mvc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.desafio.mvc.entities.Ingrediente;
import com.desafio.mvc.entities.ModoPreparo;
import com.desafio.mvc.entities.Perfil;
import com.desafio.mvc.entities.Receita;
import com.desafio.mvc.entities.Usuario;
import com.desafio.mvc.repositories.IngredienteRepository;
import com.desafio.mvc.repositories.ModoPreparoRepository;
import com.desafio.mvc.repositories.PerfilRepository;
import com.desafio.mvc.repositories.ReceitaRepository;
import com.desafio.mvc.repositories.UsuarioRepository;


@Service
public class DBService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ModoPreparoRepository modoPreparoRepository;

    public void instantiateDatabase() {

        //Criando Perfis
        
        Perfil perfil1 = new Perfil(null, "ADIMIN");
        Perfil perfil2 = new Perfil(null, "COMUM");
 
        this.perfilRepository.saveAll(Arrays.asList(perfil1, perfil2));

        List<Perfil> perfis = new ArrayList<>();
        List<Perfil> perfisComum = new ArrayList<>();


        perfis.add(perfil1);
        perfisComum.add(perfil2);

        //Criando Usuários

        String senha1 = new BCryptPasswordEncoder().encode("Gft@1234");
        Usuario usuario1 = new Usuario(null, true , "GFTAdmin", "admin@gft.com", senha1, perfis);
        this.usuarioRepository.save(usuario1);

        String senha3 = new BCryptPasswordEncoder().encode("Gft@1234");
        Usuario usuario3 = new Usuario(null, true , "GFTComun", "usuario@gft.com", senha3, perfisComum);
        this.usuarioRepository.save(usuario3);

        String senha2 = new BCryptPasswordEncoder().encode("123456");
        Usuario usuario2 = new Usuario(null, true , "Jardel", "jardel@email.com", senha2, perfis);
        this.usuarioRepository.save(usuario2);

        //Criando Receitas
        Receita receita1 = new Receita(null, "Bolo de chocolate", "40 minutos", "10 porções", usuario1);
        this.receitaRepository.save(receita1);
        Receita receita2 = new Receita(null, "Bolo de cenoura", "40 minutos", "10 porções", usuario1);
        this.receitaRepository.save(receita2);
        Receita receita3 = new Receita(null, "Bolo de limão", "40 minutos", "10 porções", usuario1);
        this.receitaRepository.save(receita3);
        Receita receita4 = new Receita(null, "Bolo de morango", "40 minutos", "10 porções", usuario1);
        this.receitaRepository.save(receita4);
        
        //Criando Ingredientes
        Ingrediente ingrediente1 = new Ingrediente(null, "1 xícara de óleo", receita1);
        this.ingredienteRepository.save(ingrediente1);
        Ingrediente ingrediente2 = new Ingrediente(null, "1 xícara de açúcar", receita1);
        this.ingredienteRepository.save(ingrediente2);
        Ingrediente ingrediente3 = new Ingrediente(null, "1 xícara de farinha de trigo", receita1);
        this.ingredienteRepository.save(ingrediente3);
        Ingrediente ingrediente4 = new Ingrediente(null, "1 xícara de chocolate", receita1);
        this.ingredienteRepository.save(ingrediente4);
        Ingrediente ingrediente5 = new Ingrediente(null, "1 xícara de leite", receita1);
        this.ingredienteRepository.save(ingrediente5);

        Ingrediente ingrediente6 = new Ingrediente(null, "1 xícara de óleo", receita2);
        this.ingredienteRepository.save(ingrediente6);
        Ingrediente ingrediente7 = new Ingrediente(null, "1 xícara de açúcar", receita2);
        this.ingredienteRepository.save(ingrediente7);
        Ingrediente ingrediente8 = new Ingrediente(null, "1 xícara de farinha de trigo", receita2);
        this.ingredienteRepository.save(ingrediente8);
        Ingrediente ingrediente9 = new Ingrediente(null, "1 xícara de chocolate", receita2);
        this.ingredienteRepository.save(ingrediente9);
        Ingrediente ingrediente10 = new Ingrediente(null, "1 xícara de leite", receita2);
        this.ingredienteRepository.save(ingrediente10);

        Ingrediente ingrediente11 = new Ingrediente(null, "1 xícara de óleo", receita3);
        this.ingredienteRepository.save(ingrediente11);
        Ingrediente ingrediente12 = new Ingrediente(null, "1 xícara de açúcar", receita3);
        this.ingredienteRepository.save(ingrediente12);
        Ingrediente ingrediente13 = new Ingrediente(null, "1 xícara de farinha de trigo", receita3);
        this.ingredienteRepository.save(ingrediente13);
        Ingrediente ingrediente14 = new Ingrediente(null, "1 xícara de chocolate", receita3);
        this.ingredienteRepository.save(ingrediente14);
        Ingrediente ingrediente15 = new Ingrediente(null, "1 xícara de leite", receita3);
        this.ingredienteRepository.save(ingrediente15);

        Ingrediente ingrediente16 = new Ingrediente(null, "1 xícara de óleo", receita4);
        this.ingredienteRepository.save(ingrediente16);
        Ingrediente ingrediente17 = new Ingrediente(null, "1 xícara de açúcar", receita4);
        this.ingredienteRepository.save(ingrediente17);
        Ingrediente ingrediente18 = new Ingrediente(null, "1 xícara de farinha de trigo", receita4);
        this.ingredienteRepository.save(ingrediente18);
        Ingrediente ingrediente19 = new Ingrediente(null, "1 xícara de chocolate", receita4);
        this.ingredienteRepository.save(ingrediente19);
        Ingrediente ingrediente20 = new Ingrediente(null, "1 xícara de leite", receita4);
        this.ingredienteRepository.save(ingrediente20);



        //Criando Modos de proparo
        ModoPreparo modoPreparo1 = new ModoPreparo(null, "Misturar tudo", receita1);
        this.modoPreparoRepository.save(modoPreparo1);
        ModoPreparo modoPreparo2 = new ModoPreparo(null, "Bater tudo", receita1);
        this.modoPreparoRepository.save(modoPreparo2);
        ModoPreparo modoPreparo3 = new ModoPreparo(null, "Colocar no forno", receita1);
        this.modoPreparoRepository.save(modoPreparo3);

        ModoPreparo modoPreparo4 = new ModoPreparo(null, "Misturar tudo", receita2);
        this.modoPreparoRepository.save(modoPreparo4);
        ModoPreparo modoPreparo5 = new ModoPreparo(null, "Bater tudo", receita2);
        this.modoPreparoRepository.save(modoPreparo5);
        ModoPreparo modoPreparo6 = new ModoPreparo(null, "Colocar no forno", receita2);
        this.modoPreparoRepository.save(modoPreparo6);

        ModoPreparo modoPreparo7 = new ModoPreparo(null, "Misturar tudo", receita3);
        this.modoPreparoRepository.save(modoPreparo7);
        ModoPreparo modoPreparo8 = new ModoPreparo(null, "Bater tudo", receita3);
        this.modoPreparoRepository.save(modoPreparo8);
        ModoPreparo modoPreparo9 = new ModoPreparo(null, "Colocar no forno", receita3);
        this.modoPreparoRepository.save(modoPreparo9);

        ModoPreparo modoPreparo10 = new ModoPreparo(null, "Misturar tudo", receita4);
        this.modoPreparoRepository.save(modoPreparo10);
        ModoPreparo modoPreparo11 = new ModoPreparo(null, "Bater tudo", receita4);
        this.modoPreparoRepository.save(modoPreparo11);
        ModoPreparo modoPreparo12 = new ModoPreparo(null, "Colocar no forno", receita4);
        this.modoPreparoRepository.save(modoPreparo12);

    }
    
}
