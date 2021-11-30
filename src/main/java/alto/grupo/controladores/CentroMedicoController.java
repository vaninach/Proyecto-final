/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.Estudios;
import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.CentroMedicoRep;
import alto.grupo.servicios.CentroMedicoSe;
import alto.grupo.servicios.EstudiosSe;
import alto.grupo.servicios.PacienteSe;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Mariano
 */

@Controller
@RequestMapping("/")
public class CentroMedicoController {
    
@Autowired
private CentroMedicoSe centroMedicose;

@Autowired
private CentroMedicoRep centroMedicorep;
  


    @RequestMapping("/CentroMedico/login")
	public String login() {
		return "logIn_1_1.html";
	}

    
    
    
@GetMapping("/NuevoCentroMedico")
public String CentroMedico(Model modelo,CentroMedico cmedico){
    modelo.addAttribute("cmedico",cmedico);
    return "CentroMedico/NuevoCentroMedico.html";
}

@PostMapping("/NuevoCentroMedico")
public String nuevoCentroMedico (Model modelo,CentroMedico cmedico) throws Errores{
    //System.out.println(paciente.getnAfiliadoOS2()+" "+paciente.getObraS3()+" "+paciente.getTelefonoContacto());
    centroMedicose.crear(cmedico.getCodigoRegistro(), cmedico.getNombre(), cmedico.getTelefono(), cmedico.getMail(), null, cmedico.getCiudad(), cmedico.getCalle(), cmedico.getNumero(), cmedico.getPiso(), cmedico.getDepartamento(), cmedico.getOtros(), cmedico.getClave());
    modelo.addAttribute("cmedico",cmedico);
    return "CentroMedico/NuevoCentroMedico.html";
}
   

 @GetMapping("editar-perfil-CM")
    public String modificarMedico(Model modelo,HttpSession session,@RequestParam Long codigo,final CentroMedico centromedico) {
        
        CentroMedico cmed= (CentroMedico)session.getAttribute("centromedicosesion");
        
        
        if(cmed.getCodigoRegistro().intValue()!=codigo){
            return "redirect:/NuevocentroMedico";
        }
        
        //Optional<CentroMedico> cmed2=centroMedicorep.findById(codigo);
        modelo.addAttribute("centromedico",cmed);
        
        return "CentroMedico/modificarCentroMedico";
    }

    
//     @PostMapping("modificarCentroMedicos")
//    public String modificarMedico2(final Medico med,HttpSession session, Model model) {
//        try {
//            medicose.modificar(med.getMatricula(), med.getNombre(), med.getApellido(), med.getFechaNac(), null, med.getMail(), null, med.getCiudad(), med.getOtros(), med.getClave(), med.getEspecialidad1(), med.getEspecialidad2(), med.getEspecialidad3(), null);
//            session.setAttribute("medicosesion", med);
//        } catch (Errores ex) {
//            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "Medico/modificarMedico";
//    }




    
}
