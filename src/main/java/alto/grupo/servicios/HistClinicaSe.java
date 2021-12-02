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
public class HistClinicaSe {

    @Autowired private HistClinicaRep histClinRep;

    // ======================== CRUD ======================
    @Transactional
    public void crear(HistoriasClinicas histClinica) throws Errores {
        
        
        
            try {
                validar(histClinica.getDNI());
                validar(histClinica.getFechaVisita());  
                validar(histClinica.getEspecialidad());
                // validar(matricula);      Integer
                // validar(centromedico);   Integer
                validar(histClinica.getInforme());
                
                // == si ninguna validacion da error, persistir ==     
                histClinRep.save(histClinica);
            } catch (Errores e) {
                System.out.println(e);
            }
       
    }
    
    @Transactional
    public void crear(String id, String DNI, String fechaVisita, String especialidad, Integer matricula, Long centroMedico, String informe) throws Errores{
        Optional<HistoriasClinicas> histClinOpt = histClinRep.findById(id);
        
        if(!histClinOpt.isPresent()){
            try {
                validar(DNI);
                 validar(fechaVisita);  
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

    private void validar(String text) throws Errores {
        if (text == null || text.isEmpty()) {
            throw new Errores("El dato " + text + "no es valido");
        }
    }

    @Transactional
    public void modificar(String id, String DNI, String fechaVisita, String especialidad, Integer matricula, Long centroMedico, String informe) throws Errores{
        Optional<HistoriasClinicas> histClinOpt = histClinRep.findById(id);
        
        if(histClinOpt.isPresent()){
           HistoriasClinicas histC = histClinOpt.get();
           if(buscarCambios(DNI))
               histC.setDNI(DNI);
           if(buscarCambios(fechaVisita)) 
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

    @Transactional
    public void eliminar(String id) throws Errores {
        Optional<HistoriasClinicas> histClinOpt = histClinRep.findById(id);

        if (histClinOpt.isPresent()) {
            histClinRep.deleteById(id);
        } else {
            throw new Errores("No se encontró la historia clinica solicitada");
        }
    }
    // ======================== END CRUD ======================
    
    
    // ======================== SERVICE FOR QUERIES ======================
    
    // ==================== POSIBLE SOBRECARGA DE METODOS ====================
    // Proposito: llamar a la "misma" funcion segun lo que el usuario complete
    // en el front
    // Referencia: PacienteSe.java y PacienteRep.java de la Vani
    public List<HistoriasClinicas> buscarPorDNI(String DNI) throws Errores {
        List<HistoriasClinicas> histClin = histClinRep.buscarDNI(DNI);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + ")");
        }
    }

    public List<HistoriasClinicas> buscarPorDNIEspecialidad(String DNI, String especialidad) throws Errores {
        List<HistoriasClinicas> histClin = histClinRep.buscarDNIEspecialidad(DNI, especialidad);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + ") y la especialidad (" + especialidad + ")");
        }
    }

    public List<HistoriasClinicas> buscarPorDNIFecha(String DNI, String fechaVisita) throws Errores {
        List<HistoriasClinicas> histClin = histClinRep.buscarDNIFecha(DNI, fechaVisita);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + ") y la fecha (" + fechaVisita + ")");
        }
    }

    public List<HistoriasClinicas> buscarPorDNIFechaEspecialidad(String DNI, String fechaVisita, String especialidad) throws Errores {
        List<HistoriasClinicas> histClin = histClinRep.buscarDNIFechaEspecialidad(DNI, fechaVisita, especialidad);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al DNI solicitado (" + DNI + "), la especialidad (" + especialidad + ") y la fecha (" + fechaVisita + ")");
        }
    }

    public List<HistoriasClinicas> buscarPorMatricula(String matricula) throws Errores {
        List<HistoriasClinicas> histClin = histClinRep.buscarMatricula(matricula);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente a la matricula solicitada (" + matricula + ")");
        }
    }

    public List<HistoriasClinicas> buscarPorMatriculaDNI(String matricula, String DNI) throws Errores {
        List<HistoriasClinicas> histClin = histClinRep.buscarMatriculaDNI(matricula, DNI);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente a la matricula solicitada (" + matricula + ") y al DNI solicitado (" + DNI + ")");
        }
    }
    
    public List<HistoriasClinicas> buscarPorCentroMedico(Long centroMedico) throws Errores{
        List<HistoriasClinicas> histClin = histClinRep.buscarCentroMedico(centroMedico);
        if (!histClin.isEmpty()) {
            return histClin;
        } else {
            throw new Errores("No se encontro ninguna historia clinica correspondiente al código de establecimiento (" + centroMedico + ")");
        }
    }
    // ======================== END SERVICE FOR QUERIES ======================
    
    
    private Boolean buscarCambios(String text) {
        return text == null || text.isEmpty();
    }

    
}
