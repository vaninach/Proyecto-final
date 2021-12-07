/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools || Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.Medico;
import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.MedicoSe;
import alto.grupo.servicios.PacienteSe;
import java.util.ArrayList;
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
    
    @Autowired
    private PacienteSe pacienteSe;

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
    //agregado por nacho //
    @RequestMapping("/Medico/inicioMedico")
	public String incioCentroMedico() {
		return "Medico/sidebarMedico.html";
	}
//////////////////////

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

    @GetMapping("Medico/BuscarPaciente")
    public String BuscarHC(HttpSession session, Model model){
        Medico med = (Medico)session.getAttribute("medicosesion");

        if(!(med == null)){
            model.addAttribute("miciudad", med.getCiudad());
        }else{
            System.out.println("MEDICO NULL");
        }
        return "Medico/BuscarPaciente";
    }

    @PostMapping("/Medico/BuscarPaciente")
    public String resultadosBusquedaPaciente(HttpSession session, Model model, @RequestParam String nombre,@RequestParam String apellido, @RequestParam String DNI, @RequestParam String ciudad) throws Errores{
        List<Paciente> lista = new ArrayList<>();
        List<Paciente> listaEdad = new ArrayList<>();  ///// para calcular la edad y pasarlo al front

//        model.addAttribute("nombre",nombre);      // <- pienso que no hacen falta
//        model.addAttribute("apellido",apellido);

        Medico med = (Medico)session.getAttribute("medicosesion");

        if(med == null){
            throw new Error("Debe registrarse!");
        }
        
        // si tengo DNI
        if( !(DNI==null || DNI.isEmpty()) ){
            Paciente pac = pacienteSe.BuscarPorDNI(DNI);
            lista.add(pac);
            System.out.println("Busqueda por DNI");
        }
        //else if(!(ciudad==null || ciudad.isEmpty() )){
        //    lista = pacienteSe.BuscarPorNAPC(nombre, apellido, null, ciudad);
        //}

        else{   // tengo nombre y apellido
            lista = pacienteSe.BuscarPorNAPC(nombre,apellido);
            System.out.println("Busqueda por nombre y apellido");
        }

        model.addAttribute("lista",lista);
        
        return "Medico/BuscarPaciente";
    }
    
    @PostMapping("/Medico/AgregarHistoriaClinica")
    public String agregarHC(HttpSession session, @RequestParam String fechaVisita, @RequestParam String especialidad, @RequestParam String centroMedico, @RequestParam String informe){
        Medico med = (Medico)session.getAttribute("medicosesion");

        if(med == null){
            throw new Error("Debe registrarse!");
        }
        
        if(fechaVisita==null || fechaVisita.isEmpty() || especialidad==null || especialidad.isEmpty() || centroMedico==null || centroMedico.isEmpty() || informe==null || informe.isEmpty()){
            System.out.println("===========================================================\nERROEERRRRERAREG");
            throw new Error("Todos los campos deben ser completados.");
        }
        
        return "Medico/BuscarPaciente";
    }
}
