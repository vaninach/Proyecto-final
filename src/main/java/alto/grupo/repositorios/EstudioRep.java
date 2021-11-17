/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.Estudios;
import java.util.Date;
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
public interface EstudioRep extends JpaRepository<Estudios, String>{
    // ==================== POSIBLE SOBRECARGA DE METODOS ====================
    // Proposito: llamar a la "misma" funcion segun lo que el usuario complete
    // en el front
    // Referencia: PacienteSe.java y PacienteRep.java de la Vani
    
    @Query("SELECT c FROM Estudios c WHERE c.DNI LIKE :DNI")
    public List<Estudios> buscarDNI(@Param("DNI") String DNI);
    
    @Query("SELECT c FROM Estudios c WHERE c.DNI LIKE :DNI AND c.especialidad LIKE %:especialidad%")
    public List<Estudios> buscarDNIEspecialidad(@Param("DNI") String DNI, @Param("especialidad") String especialidad);
    
    // Funciona asi para los tipo Date??
    @Query("SELECT c FROM Estudios c WHERE c.DNI LIKE :DNI AND c.fechaVisita LIKE :fechaVisita")
    public List<Estudios> buscarDNIFecha(@Param("DNI") String DNI, @Param("fechaVisita") Date fechaVisita);
    
    // Funciona asi para los tipo Date??
    @Query("SELECT c FROM Estudios c WHERE c.DNI LIKE :DNI AND c.fechaVisita LIKE :fechaVisita AND c.especialidad LIKE %:especialidad%")
    public List<Estudios> buscarDNIFechaEspecialidad(@Param("DNI") String DNI, @Param("fechaVisita") Date fechaVisita, @Param("especialidad") String especialidad);

    @Query("SELECT c FROM Estudios c WHERE c.matriculaInforme LIKE :matriculaInforme")
    public List<Estudios> buscarMatriculaInforme(@Param("matriculaInforme") String matriculaInforme);
    
    @Query("SELECT c FROM Estudios c WHERE c.matriculaPide LIKE :matriculaPide")
    public List<Estudios> buscarMatriculaPide(@Param("matriculaPide") String matriculaPide);

    @Query("SELECT c FROM Estudios c WHERE c.matriculaInforme LIKE :matriculaInforme AND c.DNI LIKE :DNI")
    public List<Estudios> buscarMatriculaInformeDNI(@Param("matriculaInforme") String matriculaInforme, @Param("DNI") String DNI);
    
    @Query("SELECT c FROM Estudios c WHERE c.matriculaPide LIKE :matriculaPide AND c.DNI LIKE :DNI")
    public List<Estudios> buscarMatriculaPideDNI(@Param("matriculaPide") String matriculaPide, @Param("DNI") String DNI);
    
//    @Query("SELECT c FROM Estudios c WHERE c.centroMedico LIKE :%centroMedico%")
//    public List<Estudios> buscarCentroMedico(@Param("centroMedico") Integer centroMedico);
}
