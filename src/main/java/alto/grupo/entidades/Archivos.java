/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Mariano
 */
@Entity
public class Archivos implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   
    private String nombre;
    private long tamaño;
    
   
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date subida;
    private byte[] contenido;
    
    
    private String DNI;
    private String fechaVisita;
    private String especialidad;
    private String nombreEst;
    private Integer matriculaInforme;
    private Integer matriculaPide;
    private Long centroMedico;

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setFechaVisita(String fechaVisita) {
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

    public void setCentroMedico(Long centroMedico) {
        this.centroMedico = centroMedico;
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

    public Integer getMatriculaInforme() {
        return matriculaInforme;
    }

    public Integer getMatriculaPide() {
        return matriculaPide;
    }

    public Long getCentroMedico() {
        return centroMedico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Archivos() {
    }

    public Archivos(Long id, String nombre, long tamaño) {
        this.id = id;
        this.nombre = nombre;
        this.tamaño = tamaño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTamaño() {
        return tamaño;
    }

    public void setTamaño(long tamaño) {
        this.tamaño = tamaño;
    }

    public Date getSubida() {
        return subida;
    }

    public void setSubida(Date subida) {
        this.subida = subida;
    }

    public byte[] getContenido() {
        return contenido;
    } 

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public String getNombreEst() {
        return nombreEst;
    }

    public void setNombreEst(String nombreEst) {
        this.nombreEst = nombreEst;
    }
    
    
   
    
    
   
           
           
           
}
