/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author vani
 */
@Entity
public class HistoriasClinicas {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private String DNI;
    private String fechaVisita;
    private String especialidad;
    private Integer matricula;
    
    private Long centroMedico;
    private String informe;

    
    
    public HistoriasClinicas() {
    }
    
    public String getId() {
        return id;
    }
    
    public String getDNI() {
        return DNI;
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public Long getCentromedico() {
        return centroMedico;
    }

    public String getInforme() {
        return informe;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public void setCentromedico(Long centroMedico) {
        this.centroMedico = centroMedico;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }
    
    
}
