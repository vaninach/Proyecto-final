/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo;

import alto.grupo.servicios.PacienteSe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author vani
 */
    
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {
    @Autowired
    public PacienteSe pase;
    
    @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(pase).passwordEncoder(new BCryptPasswordEncoder()); 
        }
        
        @Override
    protected void configure(HttpSecurity http) throws Exception{
        
        http.headers().frameOptions().sameOrigin().and().authorizeRequests()
                .antMatchers("/css/*","/js/*","/img/*")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("DNI")
                .passwordParameter("password")
                .defaultSuccessUrl("/NuevoPaciente")
                .permitAll()
                .and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
                
    }
  
}
