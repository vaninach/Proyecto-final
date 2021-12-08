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
@Order(1)
public class SeguridadConfiguracionCentroMedico extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CentroMedicoSe userDetailsServiceImpl;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

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
        http.antMatcher("/CentroMedico/**")
                .authorizeRequests().anyRequest().permitAll()//.authenticated()
                .and().formLogin().loginPage("/CentroMedico/login")
                .defaultSuccessUrl("/CentroMedico/inicioCentroMedico", true)
                .failureUrl("/CentroMedico/login?error")
                .permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/inicio")
                .and().exceptionHandling().accessDeniedPage("/accessdenied");
        http.csrf().disable();

    }

}
