/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.HistoriasClinicas;
import alto.grupo.entidades.Medico;
import alto.grupo.entidades.Paciente;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.HistClinicaRep;
import alto.grupo.servicios.CentroMedicoSe;
import alto.grupo.servicios.HistClinicaSe;
import alto.grupo.servicios.MedicoSe;
import alto.grupo.servicios.PacienteSe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class PacienteController {

    @Autowired
    private PacienteSe pacientese;

    @Autowired
    private HistClinicaSe histclinicase;

    @Autowired
    private HistClinicaRep histclinicarep;

    @Autowired
    private MedicoSe medicose;

    @Autowired
    private CentroMedicoSe centromedicose;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/Paciente/login")
    public String login() {
        return "Paciente/loginPaciente.html";
    }

    @GetMapping("CentroMedico/NuevoPaciente")
    public String Paciente(Model modelo, Paciente paciente) {
        modelo.addAttribute("paciente", paciente);
        return "Paciente/NuevoPaciente.html";
    }

    @PostMapping("CentroMedico/NuevoPaciente")
    public String nuevoPaciente(Model modelo, Paciente paciente, String clave2) throws Errores {
        //System.out.println(paciente.getnAfiliadoOS2()+" "+paciente.getObraS3()+" "+paciente.getTelefonoContacto());

        try {

            if (!paciente.getClave().equals(clave2)) {
                throw new Errores("Las claves no coinciden, intentelo nuevamente");
            }

            pacientese.Crearpaciente(paciente.getDNI(), paciente.getNombre(), paciente.getApellido(), paciente.getFechaNac(), paciente.getGenero(), paciente.getEstadoCivil(), paciente.getTelefono(), paciente.getMail(), paciente.getNombreContacto(), paciente.getTelefonoContacto(), paciente.getGrupoS(), paciente.getObraS1(), paciente.getnAfiliadoOS1(), paciente.getObraS2(), paciente.getnAfiliadoOS2(), paciente.getObraS3(), paciente.getnAfiliadoOS3(), paciente.getNacionalidad(), paciente.getProvincia(), paciente.getCiudad(), paciente.getCalle(), paciente.getNumero(), paciente.getPiso(), paciente.getDepartamento(), paciente.getOtros(), paciente.getClave());
            sendEmail(paciente.getMail());
        } catch (Errores ex) {

            String mensaje = ex.getMessage();
            modelo.addAttribute("mensaje", mensaje);
            return "Paciente/NuevoPaciente.html";

        }
        return "redirect:/CentroMedico/NuevoPaciente";
    }

//agregado por nacho //
    @RequestMapping("/Paciente/inicioPaciente")
    public String incioCentroMedico() {
        return "Paciente/principalPaciente.html";
    }
//////////////////////

    @GetMapping("/Paciente/editar-perfil")
    public String modificarPaciente(Model modelo, HttpSession session, @RequestParam String DNI, final Paciente paciente) {

        Paciente pac = (Paciente) session.getAttribute("pacientesesion");

        if (pac.getDNI() == null || !pac.getDNI().equals(DNI)) {
            return "redirect:/inicio";
        }

        try {
            Paciente pac2 = pacientese.BuscarPorDNI(DNI);
            modelo.addAttribute("paciente", pac);
        } catch (Errores ex) {
            Logger.getLogger(PacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Paciente/modificarpaciente";
    }

    @PostMapping("/Paciente/modificar2")
    public String modificarPaciente2(final Paciente paciente, HttpSession session, Model model, String clave2) {
        try {

            if (clave2 != null && !paciente.getClave().equals(clave2)) {
                throw new Errores("Las claves no coinciden, intentelo nuevamente");
            }

            System.out.println(paciente.getProvincia());
            pacientese.Modificar(paciente.getDNI(), paciente.getNombre(), paciente.getApellido(), paciente.getFechaNac(), paciente.getGenero(), paciente.getEstadoCivil(), paciente.getTelefono(), paciente.getMail(), paciente.getNombreContacto(), paciente.getTelefonoContacto(), paciente.getGrupoS(), paciente.getObraS1(), paciente.getnAfiliadoOS1(), paciente.getObraS2(), paciente.getnAfiliadoOS2(), paciente.getObraS3(), paciente.getnAfiliadoOS3(), paciente.getNacionalidad(), paciente.getProvincia(), paciente.getCiudad(), paciente.getCalle(), paciente.getNumero(), paciente.getPiso(), paciente.getDepartamento(), paciente.getOtros(), paciente.getClave());
            session.setAttribute("pacientesesion", paciente);
        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            model.addAttribute("mensaje", mensaje);
        }
        return "Paciente/modificarpaciente";
    }

    @GetMapping("/Paciente/BuscarHistoriasClinicas")

    public String BuscarHC(HttpSession session, Model model, String fecha, String especialidad) {

        return "Paciente/BuscarHistoriasClinicas";
    }

    @PostMapping("/Paciente/BuscarHistoriasClinicas")

    public String BuscarHC2(HttpSession session, Model model, @RequestParam String fecha, @RequestParam String especialidad, RedirectAttributes re) throws Errores {
        List<HistoriasClinicas> lista = new ArrayList<>();
        List<String> listaMedicos = new ArrayList<>();
        List<String> listaCentroMedico = new ArrayList<>();

        model.addAttribute("fecha", fecha);
        model.addAttribute("especialidad", especialidad);

        Paciente pac = (Paciente) session.getAttribute("pacientesesion");

        if (pac == null) {
            return "redirect:/inicio";

        }
        System.out.println(fecha + " " + especialidad);
        if ((fecha == null || fecha.isEmpty()) && (especialidad == null || especialidad.isEmpty())) {
            try {
                lista = histclinicase.buscarPorDNI(pac.getDNI());
                System.out.println("entramos al 1");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros del paciente";
                model.addAttribute("mensaje", mensaje);
            }
        } else if ((fecha == null || fecha.isEmpty())) {
            try {
                lista = histclinicase.buscarPorDNIEspecialidad(pac.getDNI(), especialidad);
                System.out.println("entramos al 2");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la especialidad solicitada";
                model.addAttribute("mensaje", mensaje);
            }
        } else if ((especialidad == null || especialidad.isEmpty())) {
            try {
                lista = histclinicase.buscarPorDNIFecha(pac.getDNI(), fecha);
                System.out.println("entramos al 3");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha solicitada";
                model.addAttribute("mensaje", mensaje);
            }
        } else {
            try {
                lista = histclinicase.buscarPorDNIFechaEspecialidad(pac.getDNI(), fecha, especialidad);
                System.out.println("entramos al 4");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha y especialidad solicitados";
                model.addAttribute("mensaje", mensaje);
            }

        }

        if (lista.size() != 0) {

            for (HistoriasClinicas historiasClinicas : lista) {
                try {
                    Medico med = medicose.BuscarPorMatricula(historiasClinicas.getMatricula());
                    if (med != null) {
                        listaMedicos.add(med.getNombre() + " " + med.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedicos.add("INVALID");
                }

                try {
                    CentroMedico cmed = centromedicose.buscarPorCodigo(historiasClinicas.getCentromedico());
                    if (cmed != null) {
                        listaCentroMedico.add(cmed.getNombre());
                    }
                } catch (Errores ex) {
                    listaCentroMedico.add("INVALID");
                }

            }

        }

        model.addAttribute("lista", lista);
        model.addAttribute("listamedico", listaMedicos);
        model.addAttribute("listacentromedico", listaCentroMedico);

        re.addFlashAttribute("lista", lista);
        re.addFlashAttribute("listamedico", listaMedicos);
        re.addFlashAttribute("listacentromedico", listaCentroMedico);

        return "Paciente/BuscarHistoriasClinicas";
    }

    @GetMapping("/Paciente/MostrarHistoriaClinica")
    public String MostrarHC(HttpSession session, Model model, String id, RedirectAttributes re) {

        Paciente pac = (Paciente) session.getAttribute("pacientesesion");

        if (pac == null) {
            System.out.println("redireccionando1");
            return "redirect:/inicio";

        }

        Optional<HistoriasClinicas> histc = histclinicarep.findById(id);

        if (histc.isPresent()) {
            HistoriasClinicas historiac = histc.get();
            if (!historiac.getDNI().equals(pac.getDNI())) {
                System.out.println("redireccionando3" + historiac.getDNI() + " " + pac.getDNI());
                return "redirect:/inicio";
            }
            System.out.println("informe " + historiac.getInforme());
            re.addFlashAttribute("historiac", historiac);
            model.addAttribute("historiac", historiac);
        } else {
            model.addAttribute("mensaje", "No se encontró ninguna historia clinica con el id solicitado");
            re.addFlashAttribute("mensaje", "No se encontró ninguna historia clinica con el id solicitado");
        }

        return "redirect:/Paciente/BuscarHistoriasClinicas";
        // return "Paciente/MostrarHistoriaclinica.html";
    }

    
    
    
    
    
    
    
    @GetMapping("/Paciente/BuscarPorMedico")

    public String BuscarMedico(HttpSession session, Model model, String nombre, String apellido, String especialidad) {

        return "Paciente/BuscarMedico";
    }

    
    @PostMapping("/Paciente/BuscarPorMedico")

    public String BuscarMedico2(HttpSession session, Model model, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String especialidad, @RequestParam String provincia, @RequestParam String ciudad, RedirectAttributes re) throws Errores {
        List<Medico> listaMedicos = new ArrayList<>();
        List<CentroMedico> listaCentroMedico = new ArrayList<>();

        model.addAttribute("nombre", nombre);
        model.addAttribute("apellido", apellido);
        model.addAttribute("especialidad", especialidad);

        Paciente pac = (Paciente) session.getAttribute("pacientesesion");

        if (pac == null) {
            return "redirect:/inicio";

        }
        // System.out.println(nombre + " " + especialidad);

        if(nombre==null || nombre.isEmpty() || apellido==null || apellido.isEmpty()){
            model.addAttribute("mensaje", "Debe completar el nombre y apellido del medico!");
            
            return "Paciente/BuscarMedico";
        }
        
        //Buscar por nombre, apellido, 
        if (   (provincia == null || provincia.isEmpty()) && (ciudad == null || ciudad.isEmpty()) && (especialidad == null || especialidad.isEmpty())) {
            try {
                System.out.println("entro 1"+ nombre + apellido);
                listaMedicos = medicose.BuscarPorNAPC(nombre, apellido);
                System.out.println(listaMedicos);
                System.out.println(listaMedicos.size());
            } catch (Errores ex) {
                model.addAttribute("mensaje", "No se encontraron medicos, vuelva a intentar");
            }
        } //Buscar por nombre apellido ciudad y provincia
        else if ((especialidad == null || especialidad.isEmpty())) {
            try {
                System.out.println("entro 2");
                listaMedicos = medicose.BuscarPorNAPC(nombre, apellido, provincia, ciudad);
            } catch (Errores ex) {
                model.addAttribute("mensaje", "No se encontraron medicos, vuelva a intentar");
            }
        } // nombre apellido provincia
        else if ((especialidad == null || especialidad.isEmpty()) && (ciudad == null || ciudad.isEmpty())) {
            try {
                System.out.println("entro 3");
                listaMedicos = medicose.BuscarPorNAPC(nombre, apellido, provincia);
            } catch (Errores ex) {
                model.addAttribute("mensaje", "No se encontraron medicos, vuelva a intentar");
            }
        } //todos los campos
        else if (((provincia == null || provincia.isEmpty()) && (ciudad == null || ciudad.isEmpty()) && (especialidad == null || especialidad.isEmpty()) && (nombre == null || nombre.isEmpty()) && (apellido == null || apellido.isEmpty()))) {
            try {
                System.out.println("entro 4");
                listaMedicos = medicose.BuscarPorNAPC(nombre, apellido, provincia, ciudad, especialidad);
            } catch (Errores ex) {
                model.addAttribute("mensaje", "No se encontraron medicos, vuelva a intentar");
            }
        } else {
            
            System.out.println("entro 5");

            model.addAttribute("mensaje", "No se puede realizar la busqueda, intente completando otros campos");

        }

//        if (lista.size() != 0) {
//
//            for (HistoriasClinicas historiasClinicas : lista) {
//                try {
//                    Medico med = medicose.BuscarPorMatricula(historiasClinicas.getMatricula());
//                    if (med != null) {
//                        listaMedicos.add(med.getNombre() + " " + med.getApellido());
//                    }
//                } catch (Errores ex) {
//                    listaMedicos.add("INVALID");
//                }
//
//                try {
//                    CentroMedico cmed = centromedicose.buscarPorCodigo(historiasClinicas.getCentromedico());
//                    if (cmed != null) {
//                        listaCentroMedico.add(cmed.getNombre());
//                    }
//                } catch (Errores ex) {
//                    listaCentroMedico.add("INVALID");
//                }
//
//            }
//
//        }
        model.addAttribute("listamedico", listaMedicos);
        //   model.addAttribute("listacentromedico", listaCentroMedico);

        re.addFlashAttribute("listamedico", listaMedicos);
//        re.addFlashAttribute("listacentromedico", listaCentroMedico);

        return "Paciente/BuscarMedico";
    }

    @GetMapping("/Paciente/MostrarCentrosMedicos")
    public String MostrarHC(HttpSession session, Model model, Integer id, RedirectAttributes re) {
        List<CentroMedico> listaCM_real=new ArrayList<CentroMedico>();
        
        Paciente pac = (Paciente) session.getAttribute("pacientesesion");

        if (pac == null) {
            System.out.println("redireccionando1");
            return "redirect:/inicio";

        }

        try {
            Medico med = medicose.BuscarPorMatricula(id);

            List<Long> listaCM=medicose.MostrarCentrosMedicos(med.getMatricula());
            
            for (Long long1 : listaCM) {
                CentroMedico cmed=centromedicose.buscarPorCodigo(long1);
                listaCM_real.add(cmed);
            }
        
        //model.addAttribute("listaCM", listaCM);

            System.out.println("Hemos llegado hasta aqui=========================================");
                
            
            if (listaCM.size() != 0) {
                
                System.out.println("Hemos llegado hasta aqui nuevamente=========================================");
                System.out.println(listaCM_real);
                
                
                re.addFlashAttribute("centrosm", listaCM_real);
                model.addAttribute("centrosm", listaCM_real); 
            } else {
                model.addAttribute("mensaje", "No se encontró ningún Centro Medico asociado");
                re.addFlashAttribute("mensaje", "No se encontró ningún Centro Medico asociado");
            }
        } catch (Errores ex) {
            model.addAttribute("mensaje", ex.getMessage());
        }

        return "redirect:/Paciente/BuscarPorMedico";
        // return "Paciente/MostrarHistoriaclinica.html";
    }
    
    
    
    
    
    
    
    
    

    void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Log In correcto");
        msg.setText("El usuario ha sido registrado con exito");

        javaMailSender.send(msg);

    }

}
