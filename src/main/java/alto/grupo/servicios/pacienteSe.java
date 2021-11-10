/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.paciente;
import alto.grupo.enums.Genero;
import alto.grupo.enums.GrupoS;
import alto.grupo.enums.Provincia;
import alto.grupo.repositorios.pacienteRep;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vani
 */
@Service
public class pacienteSe {
    
    @Autowired
    pacienteRep parep;
    
    public paciente Crearpaciente(String DNI, String nombre, String apellido, Date fechaNac, Genero genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, GrupoS grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave){
        Optional<paciente> paci=parep.findById(DNI);
        if(!paci.isPresent()){
            paciente pac=new paciente();
            pac.setDNI(DNI);
            pac.setNombre(nombre);
            
            
        }
        
        
        
        
    }
    
   
    
    
}
