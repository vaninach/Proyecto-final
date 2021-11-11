/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.medico;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.repositorios.medicoRep;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author miact
 */
@Service
public class medicoSe {
    
    @Autowired medicoRep medRep;
    
    public medico crearMedico(Integer matricula, String nombre, String apellido, Date fechaNac, Genero genero, String mail, Provincia provincia, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3, List<Integer> centrosMedicos){
        Optional<medico> medOpt =  medRep.findById(matricula);
        
        if(!medOpt.isPresent()){
            medico med = new medico();
            med.setMatricula(matricula);
            med.setNombre(nombre);
            med.setApellido(apellido);
            med.setFechaNac(fechaNac);
            med.setGenero(genero);
            med.setMail(mail);
            med.setProvincia(provincia);
            med.setCiudad(ciudad);
            med.setOtros(otros);
            med.setClave(clave);
            med.setEspecialidad1(especialidad1);
            med.setEspecialidad2(especialidad2);
            med.setEspecialidad3(especialidad3);
            med.setCentrosMedicos(centrosMedicos);
            return med;
        }else{
            
        }
        
        //return medOpt.get();
    }
}
