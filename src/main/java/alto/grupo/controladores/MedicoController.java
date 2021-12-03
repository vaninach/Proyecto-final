/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.Medico;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.MedicoSe;
import java.util.Date;
import java.util.List;
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
public class MedicoController {

    @Autowired
    private MedicoSe medicose;

    @RequestMapping("/Medico/login")
    public String Medicologin() {
        return "Medico/loginDoctor.html";
    }

    @RequestMapping("/Medico/dashboard")
    public String Medicodashboard() {
        return "dashboard.html";
    }

    @GetMapping("/NuevoMedico")
    public String Medico() {
        return "Medico/NuevoMedico.html";
    }

    @PostMapping("/NuevoMedico")
    public String nuevoMedico(Integer matricula, String nombre, String apellido, String fechaNac, String mail, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3) throws Errores {

        medicose.crear(matricula, nombre, apellido, fechaNac, null, mail, null, ciudad, otros, clave, especialidad1, especialidad2, especialidad3, null);
        return "Medico/NuevoMedico.html";
    }

    @GetMapping("/NuevoMedico2")
    public String Medico2(Model modelo, Medico medico) {
        modelo.addAttribute("medico", medico);
        return "Medico/NuevoMedico_1.html";
    }

    @PostMapping("/NuevoMedico2")
    public String nuevoMedico2(Model modelo, Medico medico) throws Errores {
        modelo.addAttribute("medico", medico);
        System.out.println("todo correcto");
        //Podria hacerce un metodo crear medico que reciba ya un medico y lo persistamos directamente (haciendo las validaciones)
        //medicose.crear(matricula, nombre, apellido, null, null, mail, null, ciudad, otros, clave, especialidad1, especialidad2, especialidad3, null);
        return "Medico/NuevoMedico_1.html";
    }
    
    
     @GetMapping("editar-perfil-M")
    public String modificarMedico(Model modelo,HttpSession session,@RequestParam Integer matricula,final Medico medico) {
        
        Medico med= (Medico)session.getAttribute("medicosesion");
        
        if(med.getMatricula().intValue()!=matricula){
            return "redirect:/NuevoMedico2";
        }
        
        try {
            
            Medico med2=medicose.BuscarPorMatricula(matricula);
            modelo.addAttribute("medico",med);
        } catch (Errores ex) {
            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Medico/modificarMedico";
    }

    
     @PostMapping("modificarMedicos")
    public String modificarMedico2(final Medico med,HttpSession session, Model model) {
        try {
            medicose.modificar(med.getMatricula(), med.getNombre(), med.getApellido(), med.getFechaNac(), null, med.getMail(), null, med.getCiudad(), med.getOtros(), med.getClave(), med.getEspecialidad1(), med.getEspecialidad2(), med.getEspecialidad3(), null);
            session.setAttribute("medicosesion", med);
        } catch (Errores ex) {
            Logger.getLogger(MedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Medico/modificarMedico";
    }

    

}
