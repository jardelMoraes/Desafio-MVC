package com.desafio.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.desafio.mvc.entities.PerfilTipo;
import com.desafio.mvc.services.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADIMIN = PerfilTipo.ADIMIN.getDescricao();
	private static final String COMUM = PerfilTipo.COMUM.getDescricao();

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			// acessos públicos liberados
			.antMatchers( "/assets/**", "/bootstrap/**").permitAll()	
			.antMatchers("/", "/home").permitAll()
			// acessos privados admin
			.antMatchers( "/receitas/**").hasAuthority(ADIMIN)
			.antMatchers( "/home/**").hasAnyAuthority(ADIMIN, COMUM)
			.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-error")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
			.and()
			.exceptionHandling()
			.accessDeniedPage("/acesso-negado")
			.and()
			    .csrf().disable().cors();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	}
	

}
