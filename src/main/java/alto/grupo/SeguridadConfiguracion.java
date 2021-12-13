/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo;

import alto.grupo.servicios.PacienteSe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author vani
 */
@Configuration
@Order(3)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {


    @Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	PacienteSe userDetailsServiceImpl;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
	}

  
        @Override
    protected void configure(HttpSecurity http) throws Exception{
        
//       http
//			.antMatcher("/**")
//			.authorizeRequests().anyRequest().permitAll()//.authenticated()
//			.and().formLogin().loginPage("/login")
//				.defaultSuccessUrl("/dashboard", true)
//				.failureUrl("/accessdenied2")
//			.permitAll()
//			.and().logout().logoutSuccessUrl("/login");
//		
//		http.csrf().disable();
                
                
                
                http.antMatcher("/Paciente/**")
			.authorizeRequests().anyRequest().permitAll()//.authenticated()
			.and().formLogin().loginPage("/Paciente/login")
				.defaultSuccessUrl("/Paciente/inicioPaciente", true)
			.permitAll()
                        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
.logoutSuccessUrl("/inicio").deleteCookies("JSESSIONID")
.invalidateHttpSession(true) ;
			//.and().logout().logoutUrl("/logout").logoutSuccessUrl("/inicio")
			//.and().exceptionHandling().accessDeniedPage("/accessdenied");
		http.csrf().disable();
                
    }
    
    
  
}


