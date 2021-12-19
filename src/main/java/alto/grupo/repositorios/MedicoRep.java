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
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") String provincia);

    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido% AND c.provincia=:provincia AND c.ciudad=:ciudad")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") String provincia, @Param("ciudad") String ciudad);

    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido% AND c.provincia=:provincia AND c.ciudad=:ciudad AND c.especialidad1 LIKE %:esp%")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") String provincia, @Param("ciudad") String ciudad,@Param("esp") String esp);
    
    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido% AND c.provincia=:provincia AND  c.especialidad1 LIKE %:esp%")
    public List<Medico> BuscarNombreApellidoProvinciaEsp1(@Param("nombre") String nombre, @Param("apellido") String apellido, @Param("provincia") String provincia,@Param("esp") String esp);

    @Query("SELECT c FROM Medico c WHERE c.nombre LIKE %:nombre% AND c.apellido LIKE %:apellido%")
    public List<Medico> BuscarNombreApellidoProvincia(@Param("nombre") String nombre, @Param("apellido") String apellido);

    @Query("SELECT c FROM Medico c WHERE c.especialidad1 LIKE %:esp1% OR c.especialidad2 LIKE %:esp2% OR c.especialidad3 LIKE %:esp3% AND c.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("esp2") String esp2, @Param("esp3") String esp3, @Param("prov") String prov);

    @Query("SELECT c FROM Medico c WHERE c.especialidad1 LIKE %:esp1% OR c.especialidad2 LIKE %:esp2% AND c.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("esp2") String esp2,  @Param("prov") String prov);

    @Query("SELECT c FROM Medico c WHERE c.especialidad1 LIKE %:esp1%  AND c.provincia=:prov")
    public List<Medico> BuscarNombreApellidoProvinciaEsp(@Param("esp1") String esp1, @Param("prov") String prov);

    @Query("SELECT c FROM Medico c LEFT JOIN c.centrosMedicos m WHERE m=:centro_medico")
    public List<Medico> BuscarCentroMedico(@Param("centro_medico") String centroMedico);
}
