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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mariano
 */
@Controller
@RequestMapping("/")
public class MedicoController {

    @Autowired
    private MedicoSe medicose;

    @RequestMapping("/admin/login")
    public String adminlogin() {
        return "logIn_1.html";
    }

    @RequestMapping("/admin/dashboard")
    public String admindashboard() {
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

}
