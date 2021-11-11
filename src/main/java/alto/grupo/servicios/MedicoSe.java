/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Medico;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alto.grupo.repositorios.MedicoRep;

/**
 *
 * @author miact
 */
@Service
public class MedicoSe {
    
    @Autowired MedicoRep medRep;
    
    @Transactional
    public void crear(Integer matricula, String nombre, String apellido, Date fechaNac, Genero genero, String mail, Provincia provincia, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3, List<Integer> centrosMedicos) throws Errores{
        Optional<Medico> medOpt =  medRep.findById(matricula);
        
        if(!medOpt.isPresent()){
            try {
                // validar(matricula); Integer
                validar(nombre);
                validar(apellido);
                // validar(fechaNac);  Date
                // validar(genero);  enum
                validar(mail);
                // validar(provincia); enum
                validar(ciudad);  // si usamos la API, esto va a cambiar
                // validar(otros); Este si puede ser nulo
                validar(clave);
                validar(especialidad1);
                validar(especialidad2);
                validar(especialidad3);
                // validar(centrosMedicos); List de integers
                
                // == si ninguna validacion da error, crear y persistir ==  
                Medico med = new Medico();
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

                medRep.save(med);

            } catch (Errores e) {
                System.out.println(e);
            }
            
        }else{
            throw new Errores("Ya existe un médico con la matricula ingresada.");
        }
    }
    
    public void validar(String texto) throws Errores {
        if (texto == null || texto.isEmpty()) {
            throw new Errores("Los datos no pueden ser nulos.");
        }
    }
    
    public void modificar(Integer matricula, String nombre, String apellido, Date fechaNac, Genero genero, String mail, Provincia provincia, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3, List<Integer> centrosMedicos) throws Errores{
        Optional<Medico> medOpt = medRep.findById(matricula);
        
        // ==========================================================
        // PROBLEMA: sin validacion => posibles cpos vacios seteados
        // SOLUCION: pre-llenado de fields
        // ==========================================================
        
        if(medOpt.isPresent()){
            Medico med = medOpt.get();
            // if(buscarCambios(matricula))  Integer
                med.setMatricula(matricula);
            if(buscarCambios(nombre))
                med.setNombre(nombre);
            if(buscarCambios(apellido))
                med.setApellido(apellido);
            // if(buscarCambios(fechaNac))   Date
            med.setFechaNac(fechaNac);
            // if(buscarCambios(genero))    Enum
                med.setGenero(genero);
            if(buscarCambios(mail))
                med.setMail(mail);
            // if(buscarCambios(provincia)) Enum
            med.setProvincia(provincia);
            if(buscarCambios(ciudad))
                med.setCiudad(ciudad);
            if(buscarCambios(otros))
                med.setOtros(otros);
            if(buscarCambios(clave))
                med.setClave(clave);
            if(buscarCambios(especialidad1))
                med.setEspecialidad1(especialidad1);
            if(buscarCambios(especialidad2))
                med.setEspecialidad1(especialidad2);
            if(buscarCambios(especialidad3))
                med.setEspecialidad1(especialidad3);
            // if(buscarCambios(centrosMedicos))    List<int>
            med.setCentrosMedicos(centrosMedicos);
           
            medRep.save(med);
        } else {
            throw new Errores("No se encuentra el médico de matricula " + matricula + " en la base de datos");
        }
    }
    
    public void eliminar(Integer matricula) throws Errores {
        Optional<Medico> medOpt = medRep.findById(matricula);
        if(medOpt.isPresent()){
            medRep.deleteById(matricula);
        } else {
            throw new Errores("No se encuentra el médico de matricula " + matricula + " en la base de datos");
        }

    }
    
    private Boolean buscarCambios(String text) {
        return text == null || text.isEmpty();
    }
    
}
