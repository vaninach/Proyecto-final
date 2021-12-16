/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.Archivos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mariano
 */
@Repository
public interface ArchivosRep extends JpaRepository<Archivos, Long> {
    
    @Query("SELECT c FROM archivos c WHERE c.DNI=:DNI ORDER BY c.fechaVisita DESC")
    public List<Archivos> buscarDNI(@Param("DNI") String DNI);
    
    
     @Query("SELECT c FROM archivos c WHERE c.DNI=:DNI AND c.fechaVisita=:fechaVisita ORDER BY c.fechaVisita DESC")
    public List<Archivos> buscarDNIFecha(@Param("DNI") String DNI,@Param("fechaVisita") String fechaVisita);
    
     @Query("SELECT c FROM archivos c WHERE c.DNI=:DNI AND c.especialidad:=especialidad ORDER BY c.fechaVisita DESC")
    public List<Archivos> buscarDNIEsp(@Param("DNI") String DNI,@Param("especialidad") String especialidad);
    
     @Query("SELECT c FROM archivos c WHERE c.DNI=:DNI AND c.fechaVisita=:fechaVisita AND c.especialidad:=especialidad ORDER BY c.fechaVisita DESC")
    public List<Archivos> buscarDNIFechaEsp(@Param("DNI") String DNI,@Param("fechaVisita") String fechaVisita,@Param("especialidad") String especialidad);
    
    
    
    /**
     *
     * @return
     */
    //@Query("SELECT c FROM Archivos c")
    

 //   List<Archivos> findAll();
    
}
