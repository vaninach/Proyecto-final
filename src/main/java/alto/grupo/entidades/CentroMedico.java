/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import alto.grupo.enums.Provincia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author vani
 */
@Entity
public class CentroMedico {
    
    @Id
    private Integer codigoRegistro;
    private String nombre;
    private String telefono;
    private String mail;
    private Provincia provincia;
    private String ciudad; // revisar como lo hacemos, query? bajar base de datos?
    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String otros;
    private String clave;
   
    
    // ======== Para persistir listas de Strings e Integers: ========
    @ElementCollection
    @CollectionTable(name = "centroMedico_obrasSociales", joinColumns = @JoinColumn(name = "codigoRegistro"))
    private List<String> obrasSociales;
    
    @ElementCollection
    @CollectionTable(name = "centroMedico_especialidades", joinColumns = @JoinColumn(name = "codigoRegistro"))
    private List<String> especialidades;
    
    @ElementCollection
    @CollectionTable(name = "centroMedico_medicos", joinColumns = @JoinColumn(name = "codigoRegistro"))
    private List<Integer> medicos;
    
    
    public CentroMedico() {
    }
    
    public void setCodigoRegistro(Integer codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setObrasSociales(List<String> obrasSociales) {
        this.obrasSociales = obrasSociales;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }

    public void setMedicos(List<Integer> medicos) {
        this.medicos = medicos;
    }

    
    
    
    public Integer getCodigoRegistro() {
        return codigoRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
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

    public String getCalle() {
        return calle;
    }

    public String getNumero() {
        return numero;
    }

    public String getPiso() {
        return piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getOtros() {
        return otros;
    }

    public String getClave() {
        return clave;
    }

    public List<String> getObrasSociales() {
        return obrasSociales;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public List<Integer> getMedicos() {
        return medicos;
    }
    
    
    
}
