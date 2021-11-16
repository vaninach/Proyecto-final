/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.Medico;
import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Provincia;
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
public interface MedicoRep extends JpaRepository<Medico, Integer> {

    @Query("SELECT c FROM Medico WHERE C.nombre LIKE %:nombre% AND C.apellido LIKE %:apellido% AND C.provincia=:provincia")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") Provincia provincia);

    @Query("SELECT c FROM Medico WHERE C.nombre LIKE %:nombre% AND C.apellido LIKE %:apellido% AND C.provincia=:provincia AND C.ciudad=:ciudad")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") Provincia provincia, @Param("ciudad") String ciudad);

    @Query("SELECT c FROM Medico WHERE C.nombre LIKE %:nombre% AND C.apellido LIKE %:apellido%")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido);

    @Query("SELECT c FROM Medico WHERE (C.especialidad1 LIKE %:esp1% OR C.especialidad2 LIKE %:esp2% OR C.especialidad3 LIKE %:esp3%) AND C.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("esp2") String esp2, @Param("esp3") String esp3, @Param("prov") Provincia prov);

    @Query("SELECT c FROM Medico WHERE (C.especialidad1 LIKE %:esp1% OR C.especialidad2 LIKE %:esp2%) AND C.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("esp2") String esp2,  @Param("prov") Provincia prov);

    @Query("SELECT c FROM Medico WHERE (C.especialidad1 LIKE %:esp1% ) AND C.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("prov") Provincia prov);

}
