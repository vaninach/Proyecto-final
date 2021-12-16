/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.CentroMedico;
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
                validar(centroMedico.getProvincia());
                validar(centroMedico.getCiudad());
                validar(centroMedico.getCalle());
                validar(centroMedico.getNumero());
                //validar(centroMedico.getPiso()); --> si puede ser nulo
                validar(centroMedico.getDepartamento());
                // validar(centroMedico.getOtros()); --> si puede ser nulo
                validar(centroMedico.getClave());
                String encriptada = new BCryptPasswordEncoder().encode(centroMedico.getClave());
                centroMedico.setClave(encriptada);
                // Si no hay error, persistir Centro de Salud
                centroRep.save(centroMedico);
            } catch (Errores e) {
                throw new Errores(e.getMessage());
            }
        }else{
              throw new Errores("Ya existe un Centro de salud con el registro ingresado.");
        }
    }
    
    @Transactional
    public void crear (Long codigoRegistro, String nombre, String telefono, String mail, String provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        
        Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro);

        if (!CentroMedicoOpt.isPresent()){
            try{
                validar(nombre);
                validar(telefono);
                validar(mail);
                validar(provincia);
                validar(ciudad);
                validar(calle);
                validar(numero);
                //validar(piso); --> si puede ser nulo
                validar(departamento);
                //validar(otros); --> si puede ser nulo
                validar(clave);
                // Si no hay error, crear Centro de Salud

                CentroMedico centro = new CentroMedico();
                centro.setCodigoRegistro(codigoRegistro);
                centro.setNombre(nombre);
                centro.setTelefono(telefono);
                centro.setMail(mail);
                centro.setCiudad(provincia);
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
                throw new Errores(e.getMessage());
            }
        }else{
              throw new Errores("Ya existe un Centro de salud con el registro ingresado.");
        }
    }
    
     
    @Transactional
    public void modificarCentro(Long codigoRegistro, String nombre, String telefono, String mail, String provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {

        Optional<CentroMedico> CentroMedicoOpt = centroRep.findById(codigoRegistro);   

        if (CentroMedicoOpt.isPresent()){
        CentroMedico centro =CentroMedicoOpt.get();

            validar(nombre);
                centro.setNombre(nombre);
            validar(telefono);
                centro.setTelefono(telefono);
            validar(mail);
                centro.setMail(mail);
            validar(provincia);
                centro.setProvincia(provincia);
            validar(ciudad);
                centro.setCiudad(ciudad);
            validar(calle);
                centro.setNombre(calle);
            validar(numero);
                centro.setNumero(numero);    
            centro.setPiso(piso);   // puede ser vacio
            validar(departamento);
                centro.setDepartamento(departamento);    
            centro.setOtros(otros); //  puede ser vacio
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            if (buscarCambiosClave(clave))
                centro.setClave(encriptada);

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
    
    
    // =============== VINCULACION DE MEDICOS A CM ===============
    @Transactional
    public void ModificarMedicos(Long codigo,Integer matricula) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed=cmedOpt.get();
            List<Integer> listaM=cmed.getMedicos();
            if(listaM.contains(matricula)) throw new Errores("El registro ya se encuentra en la lista");
            listaM.add(matricula);
            cmed.setMedicos(listaM);
            centroRep.save(cmed);
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }

    }    
    
    @Transactional
    public List<Integer> MostrarMedicos(Long codigo) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed=cmedOpt.get();
            List<Integer> listaM=cmed.getMedicos();
            return listaM;
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }

    }    
    
    @Transactional
    public void EliminarMedicos(Long codigo,Integer matricula) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed=cmedOpt.get();
            List<Integer> listaM=cmed.getMedicos();
            
            listaM.remove(matricula);
            
            cmed.setMedicos(listaM);
            centroRep.save(cmed);
            
            
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }

    }
    
    // =============== VINCULACION DE OS A CM ===============
    @Transactional
    public void ModificarOS(Long codigo, String nombreOS) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed = cmedOpt.get();
            List<String> listaOS = cmed.getObrasSociales();
            if(listaOS.contains(nombreOS)) throw new Errores("La obra social ya se encuentra en la lista");
            listaOS.add(nombreOS);
            cmed.setObrasSociales(listaOS);
            centroRep.save(cmed);
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }
    }
    
    @Transactional
    public List<String> MostrarObrasSociales(Long codigo) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed=cmedOpt.get();
            List<String> listaOS=cmed.getObrasSociales();
            return listaOS;
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }
    } 
    
    @Transactional
    public void EliminarOS(Long codigo, String nombreOS) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed=cmedOpt.get();
            List<String> listaOS = cmed.getObrasSociales();
            
            listaOS.remove(nombreOS);
            System.out.println(nombreOS);
            
            cmed.setObrasSociales(listaOS);
            centroRep.save(cmed);
            
            
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }

    }
    
    // =============== VINCULACION DE ESPECIALIDADES A CM ===============
    @Transactional
    public List<String> MostrarEspecialidades(Long codigo) throws Errores {
        Optional<CentroMedico> cmedOpt = centroRep.findById(codigo);
        if(cmedOpt.isPresent()){
            CentroMedico cmed=cmedOpt.get();
            List<String> listaE=cmed.getEspecialidades();
            return listaE;
        } else {
            throw new Errores("No se encuentra centro medico con codigo " + codigo + " en la base de datos");
        }

    }  
    
    
    
    
    
    // ======================== END CRUD ======================
    
    
    // ======================== SERVICE FOR QUERIES ======================
    public CentroMedico buscarPorCodigo(Long matricula) throws Errores{
        Optional<CentroMedico> centroMed = centroRep.findById(matricula);
        if (centroMed.isPresent()) {
            return centroMed.get();
        } else {
            throw new Errores("No se encontro el centro médico solicitado");
        }
    }
    
    
    public List<CentroMedico> buscarPorProvincia(String provincia) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarProvincia(provincia);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la provincia (" + provincia + ") solicitada.");
        }
    }
     
    public List<CentroMedico> buscarPorProvincia(String provincia,String nombre) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarProvincia(provincia,nombre);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la provincia (" + provincia + ") solicitada.");
        }
    }
     
    public List<CentroMedico> buscarPorProvinciaCiudad(String provincia, String ciudad) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarProvinciaCiudad(provincia,ciudad);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la provincia (" + provincia + ") y ciudad (" + ciudad + ")solicitadas.");
        }
    }
     
    public List<CentroMedico> buscarPorProvinciaCiudad(String provincia, String ciudad,String nombre) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarProvinciaCiudad(provincia,ciudad,nombre);
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
     
    public List<CentroMedico> buscarPorMatriculaProvincia(String matricula, String provincia) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarMatriculaProvincia(matricula, provincia);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente para el médico solicitado (matricula: " + matricula + ") en " + provincia + ".");
        }
    }
     
    public List<CentroMedico> buscarPorMatriculaProvinciaCiudad(String matricula, String provincia, String ciudad) throws Errores{
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
    
    public List<CentroMedico> buscarPorEspecialidadProvincia(String especialidad, String provincia) throws Errores{
        List<CentroMedico> centrosMedicos = centroRep.buscarEspecialidadProvincia(especialidad, provincia);
        if (!centrosMedicos.isEmpty()) {
            return centrosMedicos;
        } else {
            throw new Errores("No se encontra ningun centro medico correspondiente a la especialidad (" + especialidad + ") en " + provincia + ".");
        }
    }
    
    public List<CentroMedico> buscarPorEspecialidadProvinciaCiudad(String especialidad, String provincia, String ciudad) throws Errores{
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
    
            
    
    
    
    




          
        
  
