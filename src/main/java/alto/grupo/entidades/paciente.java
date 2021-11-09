/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.entidades;

import java.util.Date;
import javax.persistence.Entity;
import alto.grupo.enums.*;
import java.io.Serializable;
import javax.persistence.Id;

/**
 *
 * @author vani
 */

@Entity
public class paciente implements Serializable {
    
    @Id
    private Integer DNI;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private Genero genero;
    private String estadoCivil; //enum?
    private String telefono;
    private String mail;
    private String nombreContacto;
    private String telefonoContacto;
    private GrupoS grupoS;
    private String obraS1;
    private String nAfiliadoOS1;
    private String obraS2;
    private String nAfiliadoOS2;
    private String obraS3;
    private String nAfiliadoOS3;
    private String nacionalidad; //enum?
    private Provincia provincia;
    private String ciudad; // revisar como lo hacemos, query? bajar base de datos?
    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String otros;
    private String clave;
    private String hola;
    private String variable;

    public paciente() {
    }
    
    

    public Integer getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public GrupoS getGrupoS() {
        return grupoS;
    }

    public String getObraS1() {
        return obraS1;
    }

    public String getnAfiliadoOS1() {
        return nAfiliadoOS1;
    }

    public String getObraS2() {
        return obraS2;
    }

    public String getnAfiliadoOS2() {
        return nAfiliadoOS2;
    }

    public String getObraS3() {
        return obraS3;
    }

    public String getnAfiliadoOS3() {
        return nAfiliadoOS3;
    }

    public String getNacionalidad() {
        return nacionalidad;
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

    public void setDNI(Integer DNI) {
        this.DNI = DNI;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public void setGrupoS(GrupoS grupoS) {
        this.grupoS = grupoS;
    }

    public void setObraS1(String obraS1) {
        this.obraS1 = obraS1;
    }

    public void setnAfiliadoOS1(String nAfiliadoOS1) {
        this.nAfiliadoOS1 = nAfiliadoOS1;
    }

    public void setObraS2(String obraS2) {
        this.obraS2 = obraS2;
    }

    public void setnAfiliadoOS2(String nAfiliadoOS2) {
        this.nAfiliadoOS2 = nAfiliadoOS2;
    }

    public void setObraS3(String obraS3) {
        this.obraS3 = obraS3;
    }

    public void setnAfiliadoOS3(String nAfiliadoOS3) {
        this.nAfiliadoOS3 = nAfiliadoOS3;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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
    
    
    
}
