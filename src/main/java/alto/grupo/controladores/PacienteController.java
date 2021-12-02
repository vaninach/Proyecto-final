/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.HistoriasClinicas;
import alto.grupo.entidades.Paciente;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.HistClinicaSe;
import alto.grupo.servicios.PacienteSe;
import java.util.ArrayList;
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
public class PacienteController {
    
@Autowired
private PacienteSe pacientese;


@Autowired
private HistClinicaSe histclinicase;
  
    @RequestMapping("/login")
	public String login() {
		return "Paciente/loginPaciente.html";
	}

    
    
    
@GetMapping("/NuevoPaciente")
public String Paciente(Model modelo,Paciente paciente){
    modelo.addAttribute("paciente",paciente);
    return "Paciente/NuevoPaciente.html";
}

@PostMapping("/NuevoPaciente")
public String nuevoPaciente (Model modelo,Paciente paciente) throws Errores{
    //System.out.println(paciente.getnAfiliadoOS2()+" "+paciente.getObraS3()+" "+paciente.getTelefonoContacto());
    pacientese.Crearpaciente(paciente.getDNI(), paciente.getNombre(), paciente.getApellido(), paciente.getFechaNac(), null, paciente.getEstadoCivil(), paciente.getTelefono(), paciente.getMail(), paciente.getNombreContacto(), paciente.getTelefonoContacto(), null, paciente.getObraS1(), paciente.getnAfiliadoOS1(), paciente.getObraS2(), paciente.getnAfiliadoOS2(), paciente.getObraS3(), paciente.getnAfiliadoOS3(), paciente.getNacionalidad(), null, paciente.getCiudad(), paciente.getCalle(), paciente.getNumero(), paciente.getPiso(), paciente.getDepartamento(),paciente.getOtros(), paciente.getClave());
    return "Paciente/NuevoPaciente.html";
}
   
 @GetMapping("editar-perfil")
    public String modificarPaciente(Model modelo,HttpSession session,@RequestParam String DNI,final Paciente paciente) {
        
        Paciente pac= (Paciente)session.getAttribute("pacientesesion");
        
        if(pac.getDNI()==null || !pac.getDNI().equals(DNI)){
            return "redirect:/NuevoPaciente";
        }
        
        try {
            Paciente pac2=pacientese.BuscarPorDNI(DNI);
            modelo.addAttribute("paciente",pac);
        } catch (Errores ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Paciente/modificarpaciente";
    }

    
     @PostMapping("modificar2")
    public String modificarPaciente2(final Paciente paciente,HttpSession session, Model model) {
        try {
            pacientese.Modificar(paciente.getDNI(), paciente.getNombre(), paciente.getApellido(), paciente.getFechaNac(), null, paciente.getEstadoCivil(), paciente.getTelefono(), paciente.getMail(), paciente.getNombreContacto(), paciente.getTelefonoContacto(), null, paciente.getObraS1(), paciente.getnAfiliadoOS1(), paciente.getObraS2(), paciente.getnAfiliadoOS2(), paciente.getObraS3(), paciente.getnAfiliadoOS3(), paciente.getNacionalidad(), null, paciente.getCiudad(), paciente.getCalle(), paciente.getNumero(), paciente.getPiso(), paciente.getDepartamento(),paciente.getOtros(), paciente.getClave());
            session.setAttribute("pacientesesion", paciente);
        } catch (Errores ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Paciente/modificarpaciente";
    }

    
    @GetMapping("BuscarHistoriasClinicas")
    
    public String BuscarHC(HttpSession session,Model model,String fecha,String especialidad){
        
        
        return "Paciente/BuscarHistoriasClinicas";
    }
    
    
     @PostMapping("BuscarHistoriasClinicas")
    
    public String BuscarHC2(HttpSession session,Model model,@RequestParam String fecha,@RequestParam String especialidad) throws Errores{
        List<HistoriasClinicas> lista=new ArrayList<>();
        
        model.addAttribute("fecha",fecha);
        model.addAttribute("especialidad",especialidad);
        
        Paciente pac= (Paciente)session.getAttribute("pacientesesion");
        
        if(pac==null){
            throw new Error("Debe registrarse!");
        }
         System.out.println(fecha+" "+ especialidad);
        if( (fecha==null || fecha.isEmpty()) && (especialidad==null || especialidad.isEmpty())){
            lista=histclinicase.buscarPorDNI(pac.getDNI());
            System.out.println("entramos al 1");
        }
        
        else if( (fecha==null || fecha.isEmpty()) ){
            lista=histclinicase.buscarPorDNIEspecialidad(pac.getDNI(),especialidad);
            System.out.println("entramos al 2");
        }
        
        else if((especialidad==null || especialidad.isEmpty())){
            lista=histclinicase.buscarPorDNIFecha(pac.getDNI(), fecha);
            System.out.println("entramos al 3");
        }
        
        else{
            lista=histclinicase.buscarPorDNIFechaEspecialidad(pac.getDNI(), fecha, especialidad);
            System.out.println("entramos al 4");
        }
        
        model.addAttribute("lista",lista);
        
        
        return "Paciente/BuscarHistoriasClinicas";
    }
}
