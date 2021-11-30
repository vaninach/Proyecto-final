/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.CentroMedicoRep;
import alto.grupo.servicios.CentroMedicoSe;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public String modificarMedico(Model modelo,HttpSession session,@RequestParam Long codigo,final CentroMedico cmedico) {
        
        CentroMedico cmed= (CentroMedico)session.getAttribute("centromedicosesion");
        modelo.addAttribute("cmedico",cmed);
        
        if(cmed.getCodigoRegistro().intValue()!=codigo){
            return "redirect:/NuevoCentroMedico";
        }
        
        //Optional<CentroMedico> cmed2=centroMedicorep.findById(codigo);
        modelo.addAttribute("centromedico",cmed);
        
        return "CentroMedico/modificarCentroMedico";
    }

    
     @PostMapping("modificarCentroMedicos")
    public String modificarCentroMedico2(final CentroMedico cmed,HttpSession session, Model model) {
        try {
            centroMedicose.modificarCentro(cmed.getCodigoRegistro(), cmed.getNombre(), cmed.getTelefono(), cmed.getMail(), null, cmed.getCiudad(), cmed.getCalle(), cmed.getNumero(), cmed.getPiso(), cmed.getDepartamento(), cmed.getOtros(), cmed.getClave());
            session.setAttribute("centromedicosesion", cmed);
            model.addAttribute("cmedico",cmed);
        } catch (Errores ex) {
            Logger.getLogger(CentroMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "CentroMedico/modificarCentroMedico";
    }




    
}
