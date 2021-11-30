/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.repositorios;

import alto.grupo.entidades.Medico;
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

    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido% AND c.provincia=:provincia")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") Provincia provincia);

    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido% AND c.provincia=:provincia AND c.ciudad=:ciudad")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") Provincia provincia, @Param("ciudad") String ciudad);

    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido%")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido);

    @Query("SELECT c FROM Medico c WHERE (c.especialidad1 LIKE %:esp1% OR c.especialidad2 LIKE %:esp2% OR c.especialidad3 LIKE %:esp3%) AND c.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("esp2") String esp2, @Param("esp3") String esp3, @Param("prov") Provincia prov);

    @Query("SELECT c FROM Medico c WHERE (c.especialidad1 LIKE %:esp1% OR c.especialidad2 LIKE %:esp2%) AND c.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("esp2") String esp2,  @Param("prov") Provincia prov);

    @Query("SELECT c FROM Medico c WHERE (c.especialidad1 LIKE %:esp1% ) AND c.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("prov") Provincia prov);

    // ATENCION! Espera el codigo de registro (unico) en vez del nombre del centro (puede existir otro centro con mismo nombre)
    @Query("SELECT c FROM Medico c LEFT JOIN c.centrosMedicos m WHERE m=:codigo_registro")
    public List<Medico> BuscarCentroMedico(@Param("codigo_registro") Long codigo_registro);
}
