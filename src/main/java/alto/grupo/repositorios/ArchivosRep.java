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
    
    /**
     *
     * @return
     */
    //@Query("SELECT c FROM Archivos c")
    

 //   List<Archivos> findAll();
    
}
