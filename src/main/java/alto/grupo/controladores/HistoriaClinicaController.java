/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.HistoriasClinicas;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.HistClinicaRep;
import alto.grupo.servicios.HistClinicaSe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author vani
 */

@Controller
//@RequestMapping("/")
public class HistoriaClinicaController {
    
@Autowired
private HistClinicaSe HistoriaClinicase;

@Autowired
private HistClinicaRep HistoriaClinicarep;
  


    
@GetMapping("/NuevaHistoriaClinica")
public String HistoriaClinica(Model modelo, HistoriasClinicas hclinica){
    modelo.addAttribute("hclinica",hclinica);
    return "HistoriasClinicas/NuevoHistoriaClinica.html";
}

@PostMapping("/NuevaHistoriaClinica")
public String nuevoHistoriaClinica (Model modelo,HistoriasClinicas hclinica) throws Errores{
    HistoriaClinicase.crear(hclinica); 
    modelo.addAttribute("hclinica",hclinica);
    return "redirect:/NuevaHistoriaClinica";
}
   
//Por ahora no vamos a habilitar esta opcion
// @GetMapping("editar-perfil-CM")
//    public String modificarMedico(Model modelo,HttpSession session,@RequestParam Long codigo,final CentroMedico cmedico) {
//        
//        CentroMedico cmed= (CentroMedico)session.getAttribute("centromedicosesion");
//        modelo.addAttribute("cmedico",cmed);
//        
//        if(cmed.getCodigoRegistro().intValue()!=codigo){
//            return "redirect:/NuevoCentroMedico";
//        }
//        
//        //Optional<CentroMedico> cmed2=centroMedicorep.findById(codigo);
//        modelo.addAttribute("centromedico",cmed);
//        
//        return "CentroMedico/modificarCentroMedico";
//    }

    
//     @PostMapping("modificarCentroMedicos")
//    public String modificarCentroMedico2(final CentroMedico cmed,HttpSession session, Model model) {
//        try {
//            centroMedicose.modificarCentro(cmed.getCodigoRegistro(), cmed.getNombre(), cmed.getTelefono(), cmed.getMail(), null, cmed.getCiudad(), cmed.getCalle(), cmed.getNumero(), cmed.getPiso(), cmed.getDepartamento(), cmed.getOtros(), cmed.getClave());
//            session.setAttribute("centromedicosesion", cmed);
//            model.addAttribute("cmedico",cmed);
//        } catch (Errores ex) {
//            Logger.getLogger(HistoriaClinicaController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "CentroMedico/modificarCentroMedico";
//    }




    
}
