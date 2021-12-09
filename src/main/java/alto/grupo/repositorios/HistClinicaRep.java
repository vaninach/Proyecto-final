/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.HistoriasClinicas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author miact
 */
@Repository
public interface HistClinicaRep extends JpaRepository<HistoriasClinicas, String> {
    // ==================== POSIBLE SOBRECARGA DE METODOS ====================
    // Proposito: llamar a la "misma" funcion segun lo que el usuario complete
    // en el front
    // Referencia: PacienteSe.java y PacienteRep.java de la Vani
    
    @Query("SELECT c FROM HistoriasClinicas c WHERE c.DNI LIKE :DNI ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarDNI(@Param("DNI") String DNI);
    
    @Query("SELECT c FROM HistoriasClinicas c WHERE c.DNI LIKE :DNI AND c.especialidad LIKE %:especialidad% ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarDNIEspecialidad(@Param("DNI") String DNI, @Param("especialidad") String especialidad);
    
    // Funciona asi para los tipo Date??
    @Query("SELECT c FROM HistoriasClinicas c WHERE c.DNI LIKE :DNI AND c.fechaVisita LIKE :fechaVisita ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarDNIFecha(@Param("DNI") String DNI, @Param("fechaVisita") String fechaVisita);
    
    // Funciona asi para los tipo Date??
    @Query("SELECT c FROM HistoriasClinicas c WHERE c.DNI LIKE :DNI AND c.fechaVisita LIKE :fechaVisita AND c.especialidad LIKE %:especialidad% ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarDNIFechaEspecialidad(@Param("DNI") String DNI, @Param("fechaVisita") String fechaVisita, @Param("especialidad") String especialidad);

    @Query("SELECT c FROM HistoriasClinicas c WHERE c.matricula LIKE :matricula ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarMatricula(@Param("matricula") String matricula);

    @Query("SELECT c FROM HistoriasClinicas c WHERE c.matricula LIKE :matricula AND c.DNI LIKE :DNI ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarMatriculaDNI(@Param("matricula") String matricula, @Param("DNI") String DNI);
   
    @Query("SELECT c FROM HistoriasClinicas c WHERE c.centroMedico LIKE %:centroMedico% ORDER BY c.fechaVisita DESC")
    public List<HistoriasClinicas> buscarCentroMedico(@Param("centroMedico") Long centroMedico);
    
}
