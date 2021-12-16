/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.Medico;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.CentroMedicoRep;
import alto.grupo.servicios.CentroMedicoSe;
import alto.grupo.servicios.MedicoSe;
import java.util.ArrayList;
import java.util.List;
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
//@RequestMapping("/")
public class CentroMedicoController {

    @Autowired
    private CentroMedicoSe centroMedicose;

    @Autowired
    private CentroMedicoRep centroMedicorep;
    
    @Autowired
    private MedicoSe medicoSe;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/CentroMedico/login")
    public String login() {
        return "CentroMedico/loginCentro.html";
    }

    @RequestMapping("/CentroMedico/inicioCentroMedico")
    public String incioCentroMedico() {
        return "CentroMedico/sidebarCentroMedico.html";
    }

    @GetMapping("/NuevoCentroMedico")
    public String CentroMedico(Model modelo, CentroMedico cmedico) {
        modelo.addAttribute("cmedico", cmedico);
        return "CentroMedico/NuevoCentroMedico.html";
    }

    @PostMapping("/NuevoCentroMedico")
    public String nuevoCentroMedico(Model modelo, CentroMedico cmedico, String clave2) throws Errores {
        modelo.addAttribute("cmedico", cmedico);

        try {
            if (!cmedico.getClave().equals(clave2)) {
                throw new Errores("Las claves deben coincidir, intentelo nuevamente");
            }
            centroMedicose.crear(cmedico);
            sendEmail(cmedico.getMail());
        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            modelo.addAttribute("mensaje", mensaje);
            return "CentroMedico/NuevoCentroMedico.html";
        }

        modelo.addAttribute("cmedico", cmedico);
        return "redirect:/NuevoCentroMedico";
    }

    @GetMapping("CentroMedico/editar-perfil-CM")
    public String modificarMedico(Model modelo, HttpSession session, @RequestParam Long codigo, final CentroMedico cmedico) {

        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        modelo.addAttribute("cmedico", cmed);

        if (cmed.getCodigoRegistro().intValue() != codigo) {
            return "redirect:/NuevoCentroMedico";
        }

        //Optional<CentroMedico> cmed2=centroMedicorep.findById(codigo);
        modelo.addAttribute("centromedico", cmed);

        return "CentroMedico/modificarCentroMedico";
    }

    @PostMapping("CentroMedico/modificarCentroMedicos")
    public String modificarCentroMedico2(final CentroMedico cmed, HttpSession session, Model model, String clave2) {
        try {

            if (clave2 != null && !cmed.getClave().equals(clave2)) {
                throw new Errores("Las claves no coinciden, intentelo nuevamente");
            }
            model.addAttribute("cmedico", cmed);
            centroMedicose.modificarCentro(cmed.getCodigoRegistro(), cmed.getNombre(), cmed.getTelefono(), cmed.getMail(), cmed.getProvincia(), cmed.getCiudad(), cmed.getCalle(), cmed.getNumero(), cmed.getPiso(), cmed.getDepartamento(), cmed.getOtros(), cmed.getClave());
            session.setAttribute("centromedicosesion", cmed);

        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            model.addAttribute("mensaje", mensaje);
            return "CentroMedico/modificarCentroMedico";
        }
        return "CentroMedico/modificarCentroMedico";
    }
    
    
    // ===================== VINCULACION DE MEDICOS A CM ==========================
    @GetMapping("CentroMedico/VincularM")
    public String BuscarM(HttpSession session, Model model) {

        return "CentroMedico/Vincular-M";
    }

    @PostMapping("/CentroMedico/VincularM")
    public String resultadosBuscarM(HttpSession session, Model model, @RequestParam Integer matricula) {
        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");

        if (cmed == null) {
            model.addAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }

        try{
            Medico med=medicoSe.BuscarPorMatricula(matricula);
        List<Medico> listaM = new ArrayList<>();
            System.out.println(med+"+++++++++++++++++++++++");
            listaM.add(med);
            model.addAttribute("listaM", listaM);
        }
        catch(Errores er){
            model.addAttribute("mensaje", er.getMessage());
        }

        return "CentroMedico/Vincular-M";
    }

    @GetMapping("/CentroMedico/ElegirM")
    public String ElegirM(HttpSession session, Integer id, Model model, RedirectAttributes re) throws Errores {
        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        if (cmed == null) {
            model.addAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }

        try {
            centroMedicose.ModificarMedicos(cmed.getCodigoRegistro(), id);
            
        } catch (Errores ex) {
            re.addFlashAttribute("mensaje", ex.getMessage());
            model.addAttribute("mensaje", ex.getMessage());
            return "redirect:/CentroMedico/VincularM";
        }

        model.addAttribute("mensaje", "Se vinculó el medico exitosamente!!");
        re.addFlashAttribute("mensaje", "Se vinculó el medico exitosamente!");

        return "redirect:/CentroMedico/VincularM";
    }

    @GetMapping("/CentroMedico/MostrarM")
    public String MostrarM(HttpSession session, Model model, RedirectAttributes re) throws Errores {
        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        if (cmed == null) {
            model.addAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }

        List<Integer> listaM = centroMedicose.MostrarMedicos(cmed.getCodigoRegistro());

        if(listaM.size()!=0){
            List<Medico> listaM_fin=new ArrayList<>();
            for (Integer long1 : listaM) {
                Medico med=medicoSe.BuscarPorMatricula(long1);
                listaM_fin.add(med);
            }
            System.out.println(listaM_fin);
            model.addAttribute("listaM", listaM_fin);
        }
        else{
            model.addAttribute("mensaje", "No se encontraron Medicos asociados");
        }  
        return "/CentroMedico/Mostrar-M";
    }

    @GetMapping("/CentroMedico/EliminarM")
    public String MostrarM(HttpSession session, Integer id, Model model, RedirectAttributes re) throws Errores {

        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        if (cmed == null) {
            model.addAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }

        centroMedicose.EliminarMedicos(cmed.getCodigoRegistro(), id);

        re.addFlashAttribute("mensaje", "Registro eliminado exitosamente");

        return "redirect:/CentroMedico/MostrarM";
    }
    
    // ===================== VINCULACION DE OS A CM ==========================
    @GetMapping("CentroMedico/VincularOS")
    public String BuscarOS(HttpSession session, Model model, RedirectAttributes re)  throws Errores {
        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        if (cmed == null) {
            re.addFlashAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }
        
        List<String> listaOS1 = centroMedicose.MostrarObrasSociales(cmed.getCodigoRegistro());
        if(listaOS1.size()!=0){
            List<String> listaOS = new ArrayList<>();
            for (String string : listaOS1) {
                listaOS.add(string);
            }
            System.out.println(listaOS);
            model.addAttribute("listaOS", listaOS);
            model.addAttribute("nombreCmed", cmed.getNombre());
        }
        else{
            model.addAttribute("mensaje", "No se encontraron Obras Sociales asociadas");
        }
        return "CentroMedico/Vincular-OS";
    }
    
    @PostMapping("/CentroMedico/VincularOS")
    public String ElegirOS(HttpSession session, String nombreOS, Model model, RedirectAttributes re) throws Errores {
        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        if (cmed == null) {
            re.addFlashAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }
        
        if(nombreOS == null || nombreOS.isEmpty()){
            re.addFlashAttribute("mensaje", "Por favor, ingrese un nombre para la obra social.");
            return "redirect:/CentroMedico/VincularOS";
        }

        try {
            centroMedicose.ModificarOS(cmed.getCodigoRegistro(), nombreOS);           
        } catch (Errores ex) {
            re.addFlashAttribute("mensaje", ex.getMessage());
            model.addAttribute("mensaje", ex.getMessage());
            return "redirect:/CentroMedico/VincularOS";
        }
        
        List<String> listaOS1 = centroMedicose.MostrarObrasSociales(cmed.getCodigoRegistro());
        if(listaOS1.size()!=0){
            List<String> listaOS = new ArrayList<>();
            for (String string : listaOS1) {
                listaOS.add(string);
            }
            System.out.println(listaOS);
            re.addFlashAttribute("listaOS", listaOS);
            re.addFlashAttribute("nombreCmed", cmed.getNombre());
        }
        else{
            re.addFlashAttribute("mensaje", "No se encontraron Obras Sociales asociadas");
        }  

        model.addAttribute("mensaje", "Se vinculó la obra social exitosamente!!");
        re.addFlashAttribute("mensaje", "Se vinculó la obra social exitosamente!");

        return "redirect:/CentroMedico/VincularOS";
    }

    @GetMapping("/CentroMedico/EliminarOS")
    public String MostrarOS(HttpSession session, String nombreOS, Model model, RedirectAttributes re) throws Errores {

        CentroMedico cmed = (CentroMedico) session.getAttribute("centromedicosesion");
        if (cmed == null) {
            re.addFlashAttribute("mensaje", "Debe Registrarse!!");
            return "redirect:/inicio";
        }

        centroMedicose.EliminarOS(cmed.getCodigoRegistro(), nombreOS);

        re.addFlashAttribute("mensaje", "Registro (" + nombreOS + ") eliminado exitosamente.");
        
        List<String> listaOS1 = centroMedicose.MostrarObrasSociales(cmed.getCodigoRegistro());
        if(listaOS1.size()!=0){
            List<String> listaOS = new ArrayList<>();
            for (String string : listaOS1) {
                listaOS.add(string);
            }
            System.out.println(listaOS);
            re.addFlashAttribute("listaOS", listaOS);
            re.addFlashAttribute("nombreCmed", cmed.getNombre());
        }
        else{
            re.addFlashAttribute("mensaje", "No se encontraron Obras Sociales asociadas.");
        }  

        return "redirect:/CentroMedico/VincularOS";
    }
    
    // ====================== MAIL SENDER =======================

    void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Log In correcto");
        msg.setText("El usuario ha sido registrado con exito");

        javaMailSender.send(msg);

    }

}
