/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.enums.Provincia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mariano
 */
@Repository
public interface CentroMedicoRep extends JpaRepository<CentroMedico, Integer> {
    // ==================== POSIBLE SOBRECARGA DE METODOS ====================
    // Proposito: llamar a la "misma" funcion segun lo que el usuario complete
    // en el front
    // Referencia: PacienteSe.java y PacienteRep.java de la Vani
    
    @Query("SELECT c FROM CentroMedico c WHERE c.provincia=:provincia")
    public List<CentroMedico> buscarProvincia(@Param("provincia") Provincia provincia);

    @Query("SELECT c FROM CentroMedico c WHERE c.provincia=:provincia AND c.ciudad=:ciudad")
    public List<CentroMedico> buscarProvinciaCiudad(@Param("provincia") Provincia provincia,@Param("ciudad") String ciudad);
    
//    @Query("SELECT c FROM CentroMedico c WHERE c.medicos.matricula LIKE :matricula")
//    public List<CentroMedico> buscarMatricula(@Param("matricula") String matricula);
//    
//    @Query("SELECT c FROM CentroMedico c WHERE c.matricula LIKE :matricula AND c.provincia=:provincia")
//    public List<CentroMedico> buscarMatriculaProvincia(@Param("matricula") String matricula, @Param("provincia") Provincia provincia);
//
//    @Query("SELECT c FROM CentroMedico c WHERE c.matricula LIKE :matricula AND c.provincia=:provincia AND c.ciudad=:ciudad")
//    public List<CentroMedico> buscarMatriculaProvinciaCiudad(@Param("matricula") String matricula, @Param("provincia") Provincia provincia,@Param("ciudad") String ciudad);
//
//    @Query("SELECT c FROM CentroMedico c WHERE c.nombre LIKE :%nombre%")
//    public List<CentroMedico> buscarNombre(@Param("nombre") String nombre);
    
//    @Query("SELECT c FROM CentroMedico c WHERE c.especialidad LIKE :%especialidad%")
//    public List<CentroMedico> buscarEspecialidad(@Param("especialidad") String especialidad);
//    
//    @Query("SELECT c FROM CentroMedico c WHERE c.especialidad LIKE :%especialidad% AND c.provincia=:provincia")
//    public List<CentroMedico> buscarEspecialidadProvincia(@Param("especialidad") String especialidad, @Param("provincia") Provincia provincia);
//
//    @Query("SELECT c FROM CentroMedico c WHERE c.especialidad LIKE :%especialidad% AND c.provincia=:provincia AND c.ciudad=:ciudad")
//    public List<CentroMedico> buscarEspecialidadProvinciaCiudad(@Param("especialidad") String especialidad, @Param("provincia") Provincia provincia,@Param("ciudad") String ciudad);

}
