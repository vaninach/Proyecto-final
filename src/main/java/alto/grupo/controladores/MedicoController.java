/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools || Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.HistoriasClinicas;
import alto.grupo.entidades.Medico;
import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.CentroMedicoSe;
import alto.grupo.servicios.HistClinicaSe;
import alto.grupo.servicios.MedicoSe;
import alto.grupo.servicios.PacienteSe;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    
    @Autowired
    private CentroMedicoSe centromedicoSe;
    
    @Autowired
    private HistClinicaSe histclinSe;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/Medico/login")
    public String Medicologin() {
        return "Medico/loginDoctor.html";
    }


//    @GetMapping("/NuevoMedico")
//    public String Medico() {
//        return "Medico/doctor.html";
//    }
//
//    @PostMapping("/NuevoMedico")
//    public String nuevoMedico(Integer matricula, String nombre, String apellido, String fechaNac,String genero, String mail, String provincia, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3) throws Errores {
//        System.out.println("\n\n\nPOST MAPPING NUEVOMEDICO");
//        medicose.crear(matricula, nombre, apellido, fechaNac, genero, mail, provincia, ciudad, otros, clave, especialidad1, especialidad2, especialidad3, null);
//        return "Medico/doctor.html";
//    }

    //agregado por nacho //
    @RequestMapping("/Medico/inicioMedico")
    public String incioCentroMedico() {
        return "Medico/principalMedico.html";
    }
//////////////////////

    @GetMapping("CentroMedico/NuevoMedico2")
    public String Medico2(Model modelo, Medico medico) {
        modelo.addAttribute("medico", medico);
        return "Medico/NuevoMedico_1.html";
    }

    @PostMapping("CentroMedico/NuevoMedico2")
    public String nuevoMedico2(Model modelo, Medico medico,String clave2) throws Errores {
        modelo.addAttribute("medico", medico);

        try {
            if(!medico.getClave().equals(clave2)) throw new Errores("Las claves no coinciden, intentelo nuevamente");
            medicose.crear(medico);
            sendEmail(medico.getMail());
        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            modelo.addAttribute("mensaje", mensaje);
            return "Medico/NuevoMedico_1.html";
        }
        return "redirect:NuevoMedico2";
    }

    @GetMapping("Medico/editar-perfil-M")
    public String modificarMedico(Model modelo, HttpSession session, @RequestParam Integer matricula, final Medico medico) {
        
        System.out.println("fhjadshfasdfadñlsakfj");
        Medico med = (Medico) session.getAttribute("medicosesion");

        if (med.getMatricula().intValue() != matricula) {
            return "redirect:/NuevoMedico2";
        }
        modelo.addAttribute("medico", med);

        return "Medico/modificarMedico";
    }

    @PostMapping("Medico/modificarMedicos")
    public String modificarMedico2(final Medico med, HttpSession session, Model model,String clave2) {
        try {
            
             if(clave2!=null && !med.getClave().equals(clave2)) throw new Errores("Las claves no coinciden, intentelo nuevamente");
            
            medicose.modificar(med.getMatricula(), med.getNombre(), med.getApellido(), med.getFechaNac(), med.getGenero(), med.getMail(), med.getProvincia(), med.getCiudad(), med.getOtros(), med.getClave(), med.getEspecialidad1(), med.getEspecialidad2(), med.getEspecialidad3(), null);
            session.setAttribute("medicosesion", med);
        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            model.addAttribute("mensaje", mensaje);
            return "Medico/modificarMedico";
        }
        return "Medico/modificarMedico";
    }

    void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Log In correcto");
        msg.setText("El usuario ha sido registrado con exito");

        javaMailSender.send(msg);

    }
    @GetMapping("Medico/BuscarPaciente")
    public String BuscarHC(HttpSession session, Model model){
        Medico med = (Medico)session.getAttribute("medicosesion");

        if(!(med == null)){
            model.addAttribute("miprovincia", med.getProvincia());
            model.addAttribute("miciudad", med.getCiudad());
        }else{
            System.out.println("MEDICO NULL");
        }
        return "Medico/BuscarPaciente";
    }

    @PostMapping("/Medico/BuscarPaciente")
    public String resultadosBusquedaPaciente(HttpSession session, Model model, @RequestParam String nombre,@RequestParam String apellido, @RequestParam String DNI, @RequestParam String provincia, @RequestParam String ciudad) throws Errores{
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
        else if(!(provincia==null || provincia.isEmpty() )){
            if(!(ciudad==null || ciudad.isEmpty() )){
                lista = pacienteSe.BuscarPorNAPC(nombre, apellido, provincia, ciudad);
            }else{
                lista = pacienteSe.BuscarPorNAPC(nombre, apellido, provincia);
            }
        }

        else{   // tengo nombre y apellido
            lista = pacienteSe.BuscarPorNAPC(nombre,apellido);
            System.out.println("Busqueda por nombre y apellido");
        }

        model.addAttribute("lista",lista);
        
        return "Medico/BuscarPaciente";
    }
    
    @GetMapping("/Medico/BuscarHistoriasClinicas")
    public String buscarHC(HttpSession session, String dni, Model model) throws Errores{
        List<HistoriasClinicas> listaHC = new ArrayList<>();
        List<String> listaMedicos = new ArrayList<>();
        List<String> listaCentroMedico = new ArrayList<>();

        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            throw new Error("Debe registrarse!");
        }
        
        Paciente pac = pacienteSe.BuscarPorDNI(dni); 
        
        model.addAttribute("paciente", pac);
        
        try {
            listaHC = histclinSe.buscarPorDNI(pac.getDNI());
        } catch (Errores ex) {
            String mensaje = "No se encontraron historias clinicas del paciente";
            model.addAttribute("mensaje", mensaje);
        }
        
        if (listaHC.size() != 0){
            for (HistoriasClinicas historiasClinicas : listaHC) {
                try {
                    Medico medico = medicose.BuscarPorMatricula(historiasClinicas.getMatricula());
                    if (medico != null) {
                        listaMedicos.add(medico.getNombre() + " " + medico.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedicos.add("INVALID");
                }

                try {
                    CentroMedico cmed = centromedicoSe.buscarPorCodigo(historiasClinicas.getCentromedico());
                    if (cmed != null) {
                        listaCentroMedico.add(cmed.getNombre());
                    }
                } catch (Errores ex) {
                    listaCentroMedico.add("INVALID");
                }
            }
        }
                
        model.addAttribute("listaHC",listaHC);
        model.addAttribute("listamedico", listaMedicos);
        model.addAttribute("listacentromedico", listaCentroMedico);
        
        return "Medico/BuscarHC";
    }
    
    @PostMapping("/Medico/BuscarHistoriasClinicas")
    public String verHCfiltro(HttpSession session, Model model, @RequestParam String DNI, @RequestParam String fechaVisita, @RequestParam String especialidad, @RequestParam String centroMedico, @RequestParam(required=false) String soloMio, RedirectAttributes re){
        List<HistoriasClinicas> listaHC = new ArrayList<>();
        List<String> listaMedicos = new ArrayList<>();
        List<String> listaCentroMedico = new ArrayList<>();
        Paciente pac = new Paciente();
        
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        try{
            pac = pacienteSe.BuscarPorDNI(DNI); 
        } catch (Errores ex) {
            String mensaje = "No se encontraron registros del paciente solicitado";
            model.addAttribute("mensaje", mensaje);
        }

        if (!(centroMedico == null || centroMedico.isEmpty())   ) {
            try {
                listaHC = histclinSe.buscarPorDNICentroMedico(pac.getDNI(), centroMedico);
                System.out.println("\nBusca por DNI y CM");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros del paciente en el centro médico solicitado";
                model.addAttribute("mensaje", mensaje);
            }
        } else if (!(especialidad == null || especialidad.isEmpty())) {
            try {
                listaHC = histclinSe.buscarPorDNIEspecialidad(pac.getDNI(), especialidad);
                System.out.println("\nBusca por DNI y especialidad");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la especialidad solicitada para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        } else if (!(fechaVisita == null || fechaVisita.isEmpty())) {
            try {
                listaHC = histclinSe.buscarPorDNIFecha(pac.getDNI(), fechaVisita);
                System.out.println("\nBusca por DNI y fecha");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha solicitada para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        } else if (!(fechaVisita == null || fechaVisita.isEmpty()) && !(especialidad == null || especialidad.isEmpty())){
            try {
                listaHC = histclinSe.buscarPorDNIFechaEspecialidad(pac.getDNI(), fechaVisita, especialidad);
                System.out.println("\nBusca por DNI, fecha y especialidad");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha y especialidad solicitados para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        } else {
            try {
                listaHC = histclinSe.buscarPorDNI(pac.getDNI());
                System.out.println("\nBusca SOLO por DNI (trae todas las HC)");
            } catch (Errores ex) {
                String mensaje = "No se encontraron historias clinicas para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        }       

        if("on".equals(soloMio)){
            Iterator<HistoriasClinicas> itr = listaHC.iterator();
            while(itr.hasNext()) {
                HistoriasClinicas histClin = itr.next();
                if(!Objects.equals(histClin.getMatricula(), med.getMatricula())){
                    itr.remove();
                }                
            }
        }
        
        if (listaHC.size() != 0) {
            for (HistoriasClinicas historiasClinicas : listaHC) {
                try {
                    Medico medico = medicose.BuscarPorMatricula(historiasClinicas.getMatricula());
                    if (medico != null) {
                        listaMedicos.add(medico.getNombre() + " " + medico.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedicos.add("INVALID");
                }

                try {
                    CentroMedico cmed = centromedicoSe.buscarPorCodigo(historiasClinicas.getCentromedico());
                    if (cmed != null) {
                        listaCentroMedico.add(cmed.getNombre());
                    }
                } catch (Errores ex) {
                    listaCentroMedico.add("INVALID");
                }
            }
        }
        
        model.addAttribute("paciente", pac);
      
        model.addAttribute("listaHC",listaHC);
        re.addFlashAttribute("listaHC",listaHC);
        model.addAttribute("listamedico", listaMedicos);
        re.addFlashAttribute("listamedico",listaMedicos);
        model.addAttribute("listacentromedico", listaCentroMedico);
        re.addFlashAttribute("listacentromedico",listaCentroMedico);
        
        return "Medico/BuscarHC";
        //return "inicio";
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
    
    
    
    
       @GetMapping("Medico/VincularCM")
    public String BuscarCM(HttpSession session, Model model){
        
        return "Medico/Vincular-CM";
    }

    @PostMapping("/Medico/VincularCM")
    public String resultadosBusquedaPaciente(HttpSession session, Model model, @RequestParam Long RegistroCM) throws Errores{
        
        CentroMedico cmed=centromedicoSe.buscarPorCodigo(RegistroCM);
        List<CentroMedico> listaCM=new ArrayList<>();
        
        

        Medico med = (Medico)session.getAttribute("medicosesion");

        if(med == null){
            model.addAttribute("mensaje", "Debe Registrarse!!");
            
            return "redirect:/inicio";
        }
        
       if(cmed!=null){
           listaCM.add(cmed);
           model.addAttribute("listaCM", listaCM);
       }
       
        
        return "Medico/Vincular-CM";
    }
    
    @GetMapping("/Medico/ElegirCM")
    public String ElegirCM(HttpSession session, Long id, Model model,RedirectAttributes re) throws Errores{
        
        
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        try{
        medicose.ModificarCentrosMedicos(med.getMatricula(), id);
        }
        catch(Errores ex){
            re.addFlashAttribute("mensaje", ex.getMessage());
            model.addAttribute("mensaje", ex.getMessage());
            return "redirect:/Medico/VincularCM";
        }
        
        
        model.addAttribute("mensaje", "Se vinculó el centro medico exitosamente!!");
        re.addFlashAttribute("mensaje", "Se vinculó el centro medico exitosamente!");
        
        return "redirect:/Medico/VincularCM";
    }
    
    
    @GetMapping("/Medico/MostrarCM")
    public String MostrarCM(HttpSession session, Model model,RedirectAttributes re) throws Errores{
        
        
        

        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        
        List<Long> listaCM=medicose.MostrarCentrosMedicos(med.getMatricula());

        
        model.addAttribute("listaCM", listaCM);
        
        
        return "/Medico/Mostrar-CM";
    }
    
    @GetMapping("/Medico/EliminarCM")
    public String MostrarCM(HttpSession session, Long id, Model model,RedirectAttributes re) throws Errores{
        
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        
        medicose.EliminarCentrosMedicos(med.getMatricula(),id);

        
        re.addFlashAttribute("mensaje", "Registro eliminado exitosamente");
        
        
        return "redirect:/Medico/MostrarCM";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
