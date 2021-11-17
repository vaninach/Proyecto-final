/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.Estudios;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.EstudiosSe;
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
public class EstudioController {
    
@Autowired
private EstudiosSe estudiose;
  
@GetMapping("/NuevoEstudio")
public String Estudio(Model modelo,Estudios estudios){
    modelo.addAttribute("estudios",estudios);
    return "Estudios/NuevoEstudio.html";
}

@PostMapping("/NuevoEstudio")
public String nuevoEstudio (Model modelo,Estudios estudios) throws Errores{
    System.out.println(estudios.getCentroMedico());
    estudiose.crear(estudios.getDNI(),null,estudios.getEspecialidad(),estudios.getMatriculaInforme(),estudios.getMatriculaPide(),estudios.getCentroMedico(),estudios.getArchivo(),estudios.getInforme());
    return "Estudios/NuevoEstudio.html";
}
   
    
}
