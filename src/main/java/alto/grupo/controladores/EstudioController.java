/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.Estudios;
import alto.grupo.entidades.Medico;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.servicios.EstudiosSe;
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
public class EstudioController {
    
@Autowired
private EstudiosSe estudiose;
  
@GetMapping("/NuevoEstudio")
public String Estudio(Model modelo,Estudios estudio){
    modelo.addAttribute("estudio",estudio);
    return "Estudios/NuevoEstudio.html";
}

@PostMapping("/NuevoEstudio")
public String nuevoEstudio (Model modelo,Estudios estudios) throws Errores{
    
    estudiose.crear(estudios.getDNI(),null,estudios.getEspecialidad(),estudios.getMatriculaInforme(),estudios.getMatriculaPide(),estudios.getCentroMedico(),estudios.getArchivo(),estudios.getInforme());
    return "Estudios/NuevoEstudio.html";
}
   
    
}
