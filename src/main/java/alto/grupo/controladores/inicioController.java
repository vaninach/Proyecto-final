/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vani
 */

@Controller
@RequestMapping("/")
public class inicioController {
    
    
      @RequestMapping("/inicio")
	public String login() {
		return "inicio.html";
	}
    
}
