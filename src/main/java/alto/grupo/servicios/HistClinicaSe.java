/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.HistoriasClinicas;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.HistClinicaRep;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author miact
 */

@Service
public class HistClinicaSe {
    @Autowired HistClinicaRep histClinRep;
    
    @Transactional
    public void crear(String id, String DNI, Date fechaVisita, String especialidad, Integer matricula, Integer centroMedico, String informe) throws Errores{
        Optional<HistoriasClinicas> histClinOpt = histClinRep.findById(id);
        
        if(!histClinOpt.isPresent()){
            try {
                validar(DNI);
                // validar(fechaVisita);    Date
                validar(especialidad);
                // validar(matricula);      Integer
                // validar(centromedico);   Integer
                validar(informe);
                
                // == si ninguna validacion da error, crear y persistir ==  
                HistoriasClinicas histC = new HistoriasClinicas();
                histC.setDNI(DNI);
                histC.setFechaVisita(fechaVisita);
                histC.setEspecialidad(especialidad);
                histC.setMatricula(matricula);
                histC.setCentromedico(centroMedico);
                histC.setInforme(informe);    
                
                histClinRep.save(histC);
                
            } catch (Errores e) {
                System.out.println(e);
            }
        } else {
            throw new Errores("Error en la creacion de la historia clinica.");
        }
    }
    
    private void validar(String nombre) throws Errores {
        if (nombre == null || nombre.isEmpty()) {
            throw new Errores("El dato " + nombre + "no es valido");
        }
    }
    
    public void modificar(String DNI, Date fechaVisita, String especialidad, Integer matricula, Integer centroMedico, String informe) throws Errores{
        Optional<HistoriasClinicas> histClinOpt = histClinRep.findById(DNI);
        
        if(histClinOpt.isPresent()){
           HistoriasClinicas histC = histClinOpt.get();
           if(buscarCambios(DNI))
               histC.setDNI(DNI);
           // if(buscarCambios(fechaVisita))  Date
           histC.setFechaVisita(fechaVisita);
           if(buscarCambios(especialidad))
               histC.setEspecialidad(especialidad);
           // if(buscarCambios(matricula))  Integer
           histC.setMatricula(matricula);
           // if(buscarCambios(centroMedico))  Integer
           histC.setCentromedico(centroMedico);
           if(buscarCambios(informe))
               histC.setInforme(informe);
           
           histClinRep.save(histC);
           
        } else {
            throw new Errores("No se encuentra la historia clinica eque busca modificar en la base de datos.");
        }       
    }
    
    public void eliminar(String id) throws Errores {
        Optional<HistoriasClinicas> histClinOpt = histClinRep.findById(id);
        
        if(histClinOpt.isPresent()) {
            histClinRep.deleteById(id);
        } else {
            throw new Errores("No se encontró la historia clinica solicitada");
        }
    }
    
    private Boolean buscarCambios(String text) {
        return text == null || text.isEmpty();
    }
}
