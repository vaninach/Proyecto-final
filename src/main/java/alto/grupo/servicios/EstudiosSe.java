/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Estudios;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.EstudioRep;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author miact
 */
@Service
public class EstudiosSe {
    
    @Autowired EstudioRep estRep;
    
    @Transactional
    public void crear(String id, String DNI, Date fechaVisita, String especialidad, Integer matriculaInforme, Integer matriculaPide, Integer centroMedico, String archivo, String informe) throws Errores {
        Optional<Estudios> estOpt = estRep.findById(id);
        
        if(!estOpt.isPresent()){
            try {
                validar(DNI);
                // validar(fechaVisita);    Date
                validar(especialidad);
                // validar(matriculaInforme);      Integer
                // validar(matriculaPide);      Integer
                // validar(centromedico);   Integer
                validar(archivo);  // Posible PDF?
                validar(informe);
                
                // == si ninguna validacion da error, crear y persistir ==  
                Estudios estud = new Estudios();
                estud.setDNI(DNI);
                estud.setFechaVisita(fechaVisita);
                estud.setEspecialidad(especialidad);
                estud.setMatriculaInforme(matriculaInforme);
                estud.setMatriculaPide(matriculaPide);
                estud.setCentromedico(centroMedico);
                estud.setArchivo(archivo);
                estud.setInforme(informe);    
                
                estRep.save(estud);
                
            } catch (Errores e) {
                System.out.println(e);
            }
        } else {
            throw new Errores("Error en la creacion del estudio.");
        }
    }
    
    private void validar(String text) throws Errores {
        if (text == null || text.isEmpty()) {
            throw new Errores("El dato " + text + "no es valido");
        }
    }
    
    @Transactional
    public void modificar(String id, String DNI, Date fechaVisita, String especialidad, Integer matriculaInforme, Integer matriculaPide, Integer centroMedico, String archivo, String informe) throws Errores{
        Optional<Estudios> estOpt = estRep.findById(id);
        
        if(estOpt.isPresent()){
           Estudios estud = estOpt.get();
           if(buscarCambios(DNI))
               estud.setDNI(DNI);
           // if(buscarCambios(fechaVisita))  Date
           estud.setFechaVisita(fechaVisita);
           if(buscarCambios(especialidad))
               estud.setEspecialidad(especialidad);
           // if(buscarCambios(matriculaInforme))  Integer
           estud.setMatriculaInforme(matriculaInforme);
           // if(buscarCambios(matriculaPide))  Integer
           estud.setMatriculaPide(matriculaPide);
           // if(buscarCambios(centroMedico))  Integer
           estud.setCentromedico(centroMedico);
           if(buscarCambios(archivo))   // PDF?
               estud.setInforme(archivo);
           if(buscarCambios(informe))
               estud.setInforme(informe);
           
           estRep.save(estud);
           
        } else {
            throw new Errores("No se encuentra la historia clinica eque busca modificar en la base de datos.");
        }      
    }
    
    @Transactional
    public void eliminar(String id) throws Errores {
        Optional<Estudios> estOpt = estRep.findById(id);
        
        if(estOpt.isPresent()) {
            estRep.deleteById(id);
        } else {
            throw new Errores("No se encontró el estudio solicitado");
        }
    }
    
    private Boolean buscarCambios(String text) {
        return text == null || text.isEmpty();
    }    
    
    // ==================== POSIBLE SOBRECARGA DE METODOS ====================
    // Proposito: llamar a la "misma" funcion segun lo que el usuario complete
    // en el front
    // Referencia: PacienteSe.java y PacienteRep.java de la Vani
    
    public List<Estudios> buscarPorDNI(String DNI) throws Errores{
        List<Estudios> histClin = estRep.buscarDNI(DNI);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + ")");
        }
    }
    
    public List<Estudios> buscarPorDNIEspecialidad(String DNI, String especialidad) throws Errores{
        List<Estudios> histClin = estRep.buscarDNIEspecialidad(DNI, especialidad);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + ") y la especialidad (" + especialidad + ")");
        }
    }
    
    public List<Estudios> buscarPorDNIFecha(String DNI, Date fechaVisita) throws Errores{
        List<Estudios> histClin = estRep.buscarDNIFecha(DNI, fechaVisita);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + ") y la fecha (" + fechaVisita + ")");
        }
    }
    
    public List<Estudios> buscarPorDNIFechaEspecialidad(String DNI, Date fechaVisita, String especialidad) throws Errores{
        List<Estudios> histClin = estRep.buscarDNIFechaEspecialidad(DNI, fechaVisita, especialidad);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + "), la especialidad (" + especialidad + ") y la fecha (" + fechaVisita + ")");
        }
    }
    
    public List<Estudios> buscarPorMatriculaInforme(String matriculaInforme) throws Errores{
        List<Estudios> histClin = estRep.buscarMatriculaInforme(matriculaInforme);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente a la matricula solicitada (" + matriculaInforme + ")");
        }
    }
    
    public List<Estudios> buscarPorMatriculaPide(String matriculaPide) throws Errores{
        List<Estudios> histClin = estRep.buscarMatriculaPide(matriculaPide);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente a la matricula solicitada (" + matriculaPide + ")");
        }
    }
    
    public List<Estudios> buscarPorMatriculaInformeDNI(String matriculaInforme, String DNI) throws Errores{
        List<Estudios> histClin = estRep.buscarMatriculaInformeDNI(matriculaInforme, DNI);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente a la matricula solicitada (" + matriculaInforme + ") y al DNI solicitado (" + DNI + ")");
        }
    }
    
    public List<Estudios> buscarPorMatriculaPideDNI(String matriculaPide, String DNI) throws Errores{
        List<Estudios> histClin = estRep.buscarMatriculaPideDNI(matriculaPide, DNI);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente a la matricula solicitada (" + matriculaPide + ") y al DNI solicitado (" + DNI + ")");
        }
    }
    
//    public List<Estudios> buscarPorCentroMedico(Integer centroMedico) throws Errores{
//        List<Estudios> histClin = estRep.buscarCentroMedico(centroMedico);
//        if (!histClin.isEmpty()) {
//            return histClin;
//        } else {
//            throw new Errores("No se encontro ninguna historia clinica correspondiente al código de establecimiento (" + centroMedico + ")");
//        }
//    }
    
}
