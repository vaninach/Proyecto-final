/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.Estudios;
import alto.grupo.entidades.Paciente;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.EstudiosSe;
import alto.grupo.servicios.PacienteSe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mariano
 */

@Controller
@RequestMapping("/")
public class PacienteController {
    
@Autowired
private PacienteSe pacientese;
  

//  @RequestMapping("login")
//    public String login() {
//        return "logIn.html";
//    }
    
//   @GetMapping("/admin/login")
//    public String adlogin() {
//        return "logIn_1.html";
//    }
    @RequestMapping("/login")
	public String login() {
		return "logIn.html";
	}

          @RequestMapping("/admin/login")
	public String adminlogin() {
		return "logIn_1.html";
	}

	@RequestMapping("/admin/dashboard")
	public String admindashboard() {
		return "dashboard.html";
	}
    
    
@GetMapping("/NuevoPaciente")
public String Paciente(Model modelo,Paciente paciente){
    modelo.addAttribute("paciente",paciente);
    return "Paciente/NuevoPaciente.html";
}

@PostMapping("/NuevoPaciente")
public String nuevoPaciente (Model modelo,Paciente paciente) throws Errores{
    System.out.println(paciente.getnAfiliadoOS2()+" "+paciente.getObraS3()+" "+paciente.getTelefonoContacto());
    pacientese.Crearpaciente(paciente.getDNI(), paciente.getNombre(), paciente.getApellido(), paciente.getFechaNac(), null, paciente.getEstadoCivil(), paciente.getTelefono(), paciente.getMail(), paciente.getNombreContacto(), paciente.getTelefonoContacto(), null, paciente.getObraS1(), paciente.getnAfiliadoOS1(), paciente.getObraS2(), paciente.getnAfiliadoOS2(), paciente.getObraS3(), paciente.getnAfiliadoOS3(), paciente.getNacionalidad(), null, paciente.getCiudad(), paciente.getCalle(), paciente.getNumero(), paciente.getPiso(), paciente.getDepartamento(),paciente.getOtros(), paciente.getClave());
    return "Paciente/NuevoPaciente.html";
}
   
    
}
