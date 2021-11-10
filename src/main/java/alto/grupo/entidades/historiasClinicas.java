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
public class historiasClinicas {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private String DNI;
    private Date fechaVisita;
    private String especialidad;
    private Integer matricula;
    @ManyToOne
    private centroMedico centromedico;
    private String informe;

    
    
    public historiasClinicas() {
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

    public Integer getMatricula() {
        return matricula;
    }

    public centroMedico getCentromedico() {
        return centromedico;
    }

    public String getInforme() {
        return informe;
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

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public void setCentromedico(centroMedico centromedico) {
        this.centromedico = centromedico;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }
    
    
}
