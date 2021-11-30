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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service
public class CentroMedicoSe implements UserDetailsService{
    
    @Autowired private CentroMedicoRep centroRep;
    
    // ======================== CRUD ======================
    @Transactional
    public void crear(CentroMedico centroMedico) throws Errores {  // para recibir un objeto directamente del front
        Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(centroMedico.getCodigoRegistro());

        if (!CentroMedicoOpt.isPresent()){
            try{
                validar(centroMedico.getNombre());
                validar(centroMedico.getTelefono());
                validar(centroMedico.getMail());
                // validar provincia?  -- puede que finalmente sea un String
                validar(centroMedico.getCiudad());
                validar(centroMedico.getCalle());
                validar(centroMedico.getNumero());
                validar(centroMedico.getPiso());
                validar(centroMedico.getDepartamento());
                validar(centroMedico.getOtros());
                validar(centroMedico.getClave());
                String encriptada = new BCryptPasswordEncoder().encode(centroMedico.getClave());
                centroMedico.setClave(encriptada);
                // Si no hay error, persistir Centro de Salud
                centroRep.save(centroMedico);
            } catch (Errores e) {
                System.out.println(e);
            }
        }else{
              throw new Errores("Ya existe un Centro de salud con el registro ingresado.");
        }
    }
    
    @Transactional
    public void crear (Long codigoRegistro, String nombre, String telefono, String mail, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        
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
                String encriptada = new BCryptPasswordEncoder().encode(clave);
                centro.setClave(encriptada);
                centroRep.save(centro);

            } catch (Errores e) {
                System.out.println(e);
            }
        }else{
              throw new Errores("Ya existe un Centro de salud con el registro ingresado.");
        }
    }
    
     
    @Transactional
    public void modificarCentro(Long codigoRegistro, String nombre, String telefono, String mail, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {

        Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro);   

        if (CentroMedicoOpt.isPresent()){
        CentroMedico centro =CentroMedicoOpt.get();

            validar(nombre);
                centro.setNombre(nombre);
            validar(telefono);
                centro.setTelefono(telefono);
            validar(mail);
                centro.setMail(mail);
            validar(ciudad);
                centro.setCiudad(ciudad);
            validar(calle);
                centro.setNombre(calle);
            validar(numero);
                centro.setNombre(numero);    
            validar(piso);
                centro.setNombre(piso);
            validar(departamento);
                centro.setNombre(departamento);    
            centro.setNombre(otros); //  puede ser vacio
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            if (buscarCambiosClave(clave))
                centro.setNombre(encriptada);

            centroRep.save(centro);

        } else {
            throw new Errores("No encontramos un centro médico con ese código de registro");
        }
    }
    
    @Transactional
    public void eliminarCentro (Long codigoRegistro) throws Errores{
        Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro); 

        if (CentroMedicoOpt.isPresent()){

            centroRep.deleteById(codigoRegistro);
        } else {
            throw new Errores("No se encuentra el centro médico solicitado");
        }
    }
    // ======================== END CRUD ======================
    
    
    // ======================== SERVICE FOR QUERIES ======================
    public List<CentroMedico> buscarPorProvincia(Provincia provincia) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarProvincia(provincia);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la provincia (" + provincia + ") solicitada.");
        }
    }
     
    public List<CentroMedico> buscarPorProvinciaCiudad(Provincia provincia, String ciudad) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarProvinciaCiudad(provincia,ciudad);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la provincia (" + provincia + ") y ciudad (" + ciudad + ")solicitadas.");
        }
    }
     
    public List<CentroMedico> buscarPorMatricula(String matricula) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarMatricula(matricula);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente para el médico solicitado (matricula: " + matricula + ").");
        }
    }
     
    public List<CentroMedico> buscarPorMatriculaProvincia(String matricula, Provincia provincia) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarMatriculaProvincia(matricula, provincia);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente para el médico solicitado (matricula: " + matricula + ") en " + provincia + ".");
        }
    }
     
    public List<CentroMedico> buscarPorMatriculaProvinciaCiudad(String matricula, Provincia provincia, String ciudad) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarMatriculaProvinciaCiudad(matricula, provincia, ciudad);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente para el médico solicitado (matricula: " + matricula + ") en " + provincia + ", " + ciudad + ".");
        }
    }
     
    public List<CentroMedico> buscarPorNombre(String nombre) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarNombre(nombre);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico cde nombre: " + nombre + ".");
        }
    }
    
    public List<CentroMedico> buscarPorEspecialidad(String especialidad) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarEspecialidad(especialidad);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la especialidad (" + especialidad + ") solicitada.");
        }
    }
    
    public List<CentroMedico> buscarPorEspecialidadProvincia(String especialidad, Provincia provincia) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarEspecialidadProvincia(especialidad, provincia);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la especialidad (" + especialidad + ") en " + provincia + ".");
        }
    }
    
    public List<CentroMedico> buscarPorEspecialidadProvinciaCiudad(String especialidad, Provincia provincia, String ciudad) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarEspecialidadProvinciaCiudad(especialidad, provincia, ciudad);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la especialidad (" + especialidad + ") en " + provincia + ", " + ciudad + ".");
        }
    }
    // ======================== END SERVICE FOR QUERIES ====================== 
     
    private Boolean buscarCambiosClave(String text) {
       return !(text == null || text.isEmpty());
    }

    private void validar(String texto) throws Errores {
        if (texto == null || texto.isEmpty()) {
            throw new Errores("Los datos no pueden ser nulos");
        }
    }
    
    private void validar(Integer nb) throws Errores {
        
    }
    
    

 @Autowired
    private CentroMedicoRep usersRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



 @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
        System.out.println("holasldkfjadsfjadñs");
        Optional<CentroMedico> usersList = usersRepository.findById(Long.parseLong(userName));
        System.out.println(userName+"efwl");
        if (usersList.isPresent()) {
            CentroMedico users = usersList.get();
            System.out.println(users.getCodigoRegistro()+" "+users.getClave());
            List<String> roleList = new ArrayList<String>();
            //for (Role role : users.getRoles()) {
                roleList.add("CENTRO_MEDICO");
            //}

             ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("centromedicosesion", users);
            
            return User.builder()
                    .username(users.getCodigoRegistro().toString())
                    //change here to store encoded password in db
                    .password(users.getClave())
                 //   .disabled(users.isDisabled())
                  //  .accountExpired(users.isAccountExpired())
                  //  .accountLocked(users.isAccountLocked())
                   // .credentialsExpired(users.isCredentialsExpired())
                    .roles(roleList.toArray(new String[0]))
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
    }







}    
    
            
    
    
    
    




          
        
  
