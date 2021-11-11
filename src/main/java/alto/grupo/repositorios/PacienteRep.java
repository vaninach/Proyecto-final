/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Provincia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vani
 */

@Repository
public interface PacienteRep extends JpaRepository<Paciente, String>{
    
    @Query("SELECT c FROM Paciente WHERE C.nombre LIKE %:nombre% AND C.apellido LIKE %:apellido% AND C.provincia=:provincia")
    public List<Paciente> BuscarNombreApellidoProvincia(@Param("nombre") String nombre,@Param("apellido") String apellido,@Param("provincia") Provincia provincia);
    
    @Query("SELECT c FROM Paciente WHERE C.nombre LIKE %:nombre% AND C.apellido LIKE %:apellido%")
    public List<Paciente> BuscarNombreApellido(@Param("nombre") String nombre,@Param("apellido") String apellido);
    
    
}
