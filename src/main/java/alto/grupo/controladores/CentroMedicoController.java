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
    public String nuevoCentroMedico(Model modelo, CentroMedico cmedico,String clave2) throws Errores {
        modelo.addAttribute("cmedico", cmedico);
        
        try {
            if(!cmedico.getClave().equals(clave2)) throw new Errores("Las claves deben coincidir, intentelo nuevamente");
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
    public String modificarCentroMedico2(final CentroMedico cmed, HttpSession session, Model model) {
        try {
            model.addAttribute("cmedico", cmed);
            centroMedicose.modificarCentro(cmed.getCodigoRegistro(), cmed.getNombre(), cmed.getTelefono(), cmed.getMail(), cmed.getProvincia(), cmed.getCiudad(), cmed.getCalle(), cmed.getNumero(), cmed.getPiso(), cmed.getDepartamento(), cmed.getOtros(), cmed.getClave());
            session.setAttribute("centromedicosesion", cmed);
            
        } catch (Errores ex) {
            String mensaje=ex.getMessage();
            model.addAttribute("mensaje",mensaje);
            return "CentroMedico/modificarCentroMedico";
        }
        return "CentroMedico/modificarCentroMedico";
    }

    void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Log In correcto");
        msg.setText("El usuario ha sido registrado con exito");

        javaMailSender.send(msg);

    }

}
