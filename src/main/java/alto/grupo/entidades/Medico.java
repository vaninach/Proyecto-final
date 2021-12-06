/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
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
    @Enumerated(EnumType.STRING)  // en la bdd guarda strings (FEM.., MASC..) en lugar de numeros (0,1)
    private Genero genero;
    private String mail;
    @Enumerated(EnumType.STRING)  // en la bdd guarda strings en lugar de numeros
    private Provincia provincia;
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
    private List<Integer> centrosMedicos;  //

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

    public Genero getGenero() {
        return genero;
    }

    public String getMail() {
        return mail;
    }

    public Provincia getProvincia() {
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

    public List<Integer> getCentrosMedicos() {
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

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setProvincia(Provincia provincia) {
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

    public void setCentrosMedicos(List<Integer> centrosMedicos) {
        this.centrosMedicos = centrosMedicos;
    }
    
    
 
    
}
