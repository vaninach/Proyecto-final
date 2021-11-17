/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.MedicoSe;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@GetMapping("/NuevoMedico")
public String Medico(){
    return "Medico/NuevoMedico.html";
}

@PostMapping("/NuevoMedico")
public String nuevoMedico (Integer matricula, String nombre, String apellido, String mail, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3) throws Errores{
    
    medicose.crear(matricula, nombre, apellido, null, null, mail, null, ciudad, otros, clave, especialidad1, especialidad2, especialidad3, null);
    return "Medico/NuevoMedico.html";
}
    
}
