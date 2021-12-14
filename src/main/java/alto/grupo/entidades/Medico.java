/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author vani
 */
@Entity
public class Medico {
    
    @Id
    private Integer matricula;
    private String nombre;
    private String apellido;
    private String fechaNac;
    private String genero;
    private String mail;
    private String provincia;
    private String ciudad; // revisar como lo hacemos, query? bajar base de datos?
    private String otros;
    private String clave;
    
    private String especialidad1;
    private String especialidad2;
    private String especialidad3;
    
    // ======== Para persistir listas de Integers: ========
    @ElementCollection
    @CollectionTable(name = "medico_centros_medicos", joinColumns = @JoinColumn(name = "medico_matricula"))
    @Column(name = "centro_medico")
    private List<Long> centrosMedicos=new ArrayList<>();  //

    public Medico() {
    }

    
    
    public Integer getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getGenero() {
        return genero;
    }

    public String getMail() {
        return mail;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getOtros() {
        return otros;
    }

    public String getClave() {
        return clave;
    }

    public String getEspecialidad1() {
        return especialidad1;
    }

    public String getEspecialidad2() {
        return especialidad2;
    }

    public String getEspecialidad3() {
        return especialidad3;
    }

    public List<Long> getCentrosMedicos() {
        return centrosMedicos;
    }

    public void setMatricula(Integer Matricula) {
        this.matricula = Matricula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setEspecialidad1(String especialidad1) {
        this.especialidad1 = especialidad1;
    }

    public void setEspecialidad2(String especialidad2) {
        this.especialidad2 = especialidad2;
    }

    public void setEspecialidad3(String especialidad3) {
        this.especialidad3 = especialidad3;
    }

    public void setCentrosMedicos(List<Long> centrosMedicos) {
        this.centrosMedicos = centrosMedicos;
    }

    @Override
    public String toString() {
        return "Medico{" + "matricula=" + matricula + ", nombre=" + nombre + ", apellido=" + apellido + '}';
    }
    
    
 
    
}
