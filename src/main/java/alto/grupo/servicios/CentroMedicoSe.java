/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.CentroMedico;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.CentroMedicoRep;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// Verificar List
@Service
public class CentroMedicoSe {
    
    
    @Autowired
    CentroMedicoRep centroRep;

    
    //Metodo para Crear Centro de salud.
    @Transactional
    public void crearCentro (Integer codigoRegistro, String nombre, String telefono, String mail, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        
  
    
    Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro);
    
    if (!CentroMedicoOpt.isPresent()){
        try{
        validar(nombre);
        validar(telefono);
        validar(mail);
        // validar provincia?
        validar(ciudad);
        validar(calle);
        validar(numero);
        validar(piso);
        validar(departamento);
        validar(otros);
        validar(clave);
        // Si no hay error, crear Centro de Salud
        
        CentroMedico centro = new CentroMedico();
        centro.setCodigoRegistro(codigoRegistro);
        centro.setNombre(nombre);
        centro.setTelefono(telefono);
        centro.setMail(mail);
        centro.setCiudad(ciudad);
        centro.setCalle(calle);
        centro.setNumero(numero);
        centro.setPiso(piso);
        centro.setDepartamento(departamento);
        centro.setOtros(otros);
        centro.setClave(clave);
        centroRep.save(centro);
    
    
    } catch (Errores e) {
            System.out.println(e);
    }
      }else{
            throw new Errores("Ya existe un Centro de salud con el registro ingresado.");
            }
    }
    
    
     
     @Transactional
     public void modificarCentro(Integer codigoRegistro, String nombre, String telefono, String mail, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
         
      Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro);   
      
      
      if (CentroMedicoOpt.isPresent()){
      CentroMedico centro =CentroMedicoOpt.get();
      
             //if(buscarCambios(DNI))
               //histC.setDNI(DNI);
    
          if (buscarCambios(nombre)) {
              centro.setNombre(nombre);}
          if (buscarCambios(telefono)) {
              centro.setTelefono(telefono);}
          if (buscarCambios(mail)) {
              centro.setMail(mail);}
          if (buscarCambios(ciudad)) {
              centro.setCiudad(ciudad);}
          if (buscarCambios(calle)) {
              centro.setNombre(calle);}
          if (buscarCambios(numero)) {
              centro.setNombre(numero);}    
          if (buscarCambios(piso)) {
              centro.setNombre(piso);}
          if (buscarCambios(departamento)) {
              centro.setNombre(departamento);}    
          if (buscarCambios(otros)) {
              centro.setNombre(otros);}
          if (buscarCambios(clave)) {
              centro.setNombre(clave);}
          
          centroRep.save(centro);
          
      }    else {
          
          throw new Errores("No encontramos un centro médico con ese código de registro");
      }
              
              
              
      }
     @Transactional
     public void eliminarCentro (Integer codigoRegistro) throws Errores{
         Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro); 
         
         if (CentroMedicoOpt.isPresent()){
             
             centroRep.deleteById(codigoRegistro);
         } else {
             throw new Errores("No se encuentra el centro médico solicitado");
         }
     }
     
     private Boolean buscarCambios(String text) {
        return text == null || text.isEmpty();
    }
     
     

public void validar(String texto) throws Errores {
        if (texto == null || texto.isEmpty()) {
            throw new Errores("El dato " + texto + "no es valido");
        }
     }

}    
    
            
    
    
    
    




          
        
  
