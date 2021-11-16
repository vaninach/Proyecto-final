/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author vani
 */
public class Estudios {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private String DNI;
    private Date fechaVisita;
    private String especialidad;
    private Integer matriculaInforme;
    private Integer matriculaPide;
    private Integer centromedico;
    private String archivo; // investigar como van los pdf
    private String informe;

    public Estudios() {
    }
    
    public String getId() {
        return id;
    }

    public String getDNI() {
        return DNI;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Integer getMatriculaInforme() {
        return matriculaInforme;
    }

    public Integer getMatriculaPide() {
        return matriculaPide;
    }

    public Integer getCentromedico() {
        return centromedico;
    }

    public String getArchivo() {
        return archivo;
    }

    public String getInforme() {
        return informe;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setMatriculaInforme(Integer matriculaInforme) {
        this.matriculaInforme = matriculaInforme;
    }

    public void setMatriculaPide(Integer matriculaPide) {
        this.matriculaPide = matriculaPide;
    }

    public void setCentromedico(Integer centromedico) {
        this.centromedico = centromedico;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    
}
