/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.servicios.CentroMedicoSe;
import alto.grupo.servicios.MedicoSe;
import alto.grupo.servicios.PacienteSe;
import javax.activation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author vani
 */
@Configuration
@Order(2)
public class SeguridadConfiguracionMedico extends WebSecurityConfigurerAdapter {


   @Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	MedicoSe userDetailsServiceImpl;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.headers().frameOptions().sameOrigin().and().authorizeRequests()
//                .antMatchers("/css/*","/js/*","/img/*")
//                .permitAll()
//                .and().formLogin()
//                .loginPage("/admin/login")
//                .loginProcessingUrl("/admin/login")
//               // .usernameParameter("DNI")
//               // .passwordParameter("password")
//                .defaultSuccessUrl("/NuevoMedico")
//                .permitAll()
//                .and().logout().logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();

System.out.println("hola2");
			http.antMatcher("/admin/**")
			.authorizeRequests().anyRequest().permitAll()//.authenticated()
			.and().formLogin().loginPage("/admin/login")
				.defaultSuccessUrl("/NuevoMedico", true)
				.failureUrl("/admin/accessdenied")
			.permitAll()
			.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout")
			.and().exceptionHandling().accessDeniedPage("/admin/accessdenied");
		http.csrf().disable();
	}	
    
    
  
}


