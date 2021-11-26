/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Medico;
import alto.grupo.enums.Genero;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alto.grupo.repositorios.MedicoRep;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author miact
 */
@Service
public class MedicoSe implements UserDetailsService {
    
    @Autowired private MedicoRep medRep;
    
    // ======================== CRUD ======================
    @Transactional
    public void crear(Medico medico) throws Errores {
        Optional<Medico> medOpt =  medRep.findById(medico.getMatricula());
        
        if(!medOpt.isPresent()){
            try {
                // validar(matricula); Integer
                validar(medico.getNombre());
                validar(medico.getApellido());
                validar(medico.getFechaNac());
                // validar(genero);  enum
                validar(medico.getMail());
                // validar(provincia); enum
                validar(medico.getCiudad());  // si usamos la API, esto va a cambiar
                // validar(otros); Este si puede ser nulo
                validar(medico.getClave());
                String encriptada = new BCryptPasswordEncoder().encode(medico.getClave());
                medico.setClave(encriptada);
                validar(medico.getEspecialidad1());
                validar(medico.getEspecialidad2());
                validar(medico.getEspecialidad3());
                // validar(centrosMedicos); List de integers
                
                // == si ninguna validacion da error,  persistir ==  
                medRep.save(medico);

            } catch (Errores e) {
                System.out.println(e);
            }
            
        }else{
            throw new Errores("Ya existe un médico con la matricula ingresada.");
        }
    }
    
    @Transactional
    public void crear(Integer matricula, String nombre, String apellido, String fechaNac, Genero genero, String mail, Provincia provincia, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3, List<Integer> centrosMedicos) throws Errores{

        Optional<Medico> medOpt =  medRep.findById(matricula);
        
        if(!medOpt.isPresent()){
            try {
                // validar(matricula); Integer
               
                validar(nombre);
                
                validar(apellido);
                
                validar(fechaNac);
                // validar(genero);  enum
                validar(mail);
                
                // validar(provincia); enum
                validar(ciudad);  // si usamos la API, esto va a cambiar
               
                // validar(otros); Este si puede ser nulo
                validar(clave);
               
                validar(especialidad1);
                
                validar(especialidad2);
                
                validar(especialidad3);
                
                // validar(centrosMedicos); List de integers
                
                // == si ninguna validacion da error, crear y persistir ==  
                Medico med = new Medico();
                med.setMatricula(matricula);
                med.setNombre(nombre);
                med.setApellido(apellido);
                med.setFechaNac(fechaNac);
                med.setGenero(genero);
                med.setMail(mail);
                med.setProvincia(provincia);
                med.setCiudad(ciudad);
                med.setOtros(otros);
                String encriptada = new BCryptPasswordEncoder().encode(clave);
                med.setClave(encriptada);
                med.setEspecialidad1(especialidad1);
                med.setEspecialidad2(especialidad2);
                med.setEspecialidad3(especialidad3);
                med.setCentrosMedicos(centrosMedicos);

                medRep.save(med);

            } catch (Errores e) {
                System.out.println(e);
            }
            
        }else{
            throw new Errores("Ya existe un médico con la matricula ingresada.");
        }
    }
    
    @Transactional
    public void modificar(Integer matricula, String nombre, String apellido, String fechaNac, Genero genero, String mail, Provincia provincia, String ciudad, String otros, String clave, String especialidad1, String especialidad2, String especialidad3, List<Integer> centrosMedicos) throws Errores{

        Optional<Medico> medOpt = medRep.findById(matricula);
        
        // ==========================================================
        // PROBLEMA: sin validacion => posibles cpos vacios seteados
        // SOLUCION: pre-llenado de fields
        // ==========================================================
        
        if(medOpt.isPresent()){
            Medico med = medOpt.get();
            // if(buscarCambios(matricula))  Integer
                med.setMatricula(matricula);
            if(buscarCambios(nombre))
                med.setNombre(nombre);
            if(buscarCambios(apellido))
                med.setApellido(apellido);
            if(buscarCambios(fechaNac)) 
            med.setFechaNac(fechaNac);
            // if(buscarCambios(genero))    Enum
                med.setGenero(genero);
            if(buscarCambios(mail))
                med.setMail(mail);
            // if(buscarCambios(provincia)) Enum
            med.setProvincia(provincia);
            if(buscarCambios(ciudad))
                med.setCiudad(ciudad);
            if(buscarCambios(otros))
                med.setOtros(otros);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            if(buscarCambios(clave))
                med.setClave(encriptada);
            if(buscarCambios(especialidad1))
                med.setEspecialidad1(especialidad1);
            if(buscarCambios(especialidad2))
                med.setEspecialidad1(especialidad2);
            if(buscarCambios(especialidad3))
                med.setEspecialidad1(especialidad3);
            // if(buscarCambios(centrosMedicos))    List<int>
            med.setCentrosMedicos(centrosMedicos);
           
            medRep.save(med);
        } else {
            throw new Errores("No se encuentra el médico de matricula " + matricula + " en la base de datos");
        }
    }
    
    @Transactional
    public void eliminar(Integer matricula) throws Errores {
        Optional<Medico> medOpt = medRep.findById(matricula);
        if(medOpt.isPresent()){
            medRep.deleteById(matricula);
        } else {
            throw new Errores("No se encuentra el médico de matricula " + matricula + " en la base de datos");
        }

    }
    
    private Boolean buscarCambios(String text) {
        return text == null || text.isEmpty();
    }
    
    // ======================== END CRUD ======================
    
    
    // ======================== SERVICE FOR QUERIES ======================   
    @Transactional
    public Medico BuscarPorMatricula(Integer matricula) throws Errores{
        Optional<Medico> med = medRep.findById(matricula);
        if (med.isPresent()) {
            return med.get();
        } else {
            throw new Errores("No se encontro el medico solicitado");
        }
    }
    
     @Transactional
    public List<Medico> BuscarPorNAPC(String nombre,String apellido,Provincia provincia, String ciudad) throws Errores{
        List<Medico> med = medRep.BuscarNombreApellidoProvincia(nombre, apellido, provincia, ciudad);
        if (!med.isEmpty()) {
            return med;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
    
     @Transactional
    public List<Medico> BuscarPorNAPC(String nombre,String apellido,Provincia provincia) throws Errores{
        List<Medico> med = medRep.BuscarNombreApellidoProvincia(nombre, apellido, provincia);
        if (!med.isEmpty()) {
            return med;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
    
     @Transactional
    public List<Medico> BuscarPorNAPC(String nombre,String apellido) throws Errores{
        List<Medico> med = medRep.BuscarNombreApellidoProvincia(nombre, apellido);
        if (!med.isEmpty()) {
            return med;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
    
    
     @Transactional
    public List<Medico> BuscarPorNAPCE(String esp1,String esp2,String esp3,Provincia provincia) throws Errores{
        List<Medico> med = medRep.BuscarNombreApellidoProvinciaEsp(esp1, esp2, esp3, provincia);
        if (!med.isEmpty()) {
            return med;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
     @Transactional
    public List<Medico> BuscarPorNAPCE(String esp1,String esp2,Provincia provincia) throws Errores{
        List<Medico> med = medRep.BuscarNombreApellidoProvinciaEsp(esp1, esp2, provincia);
        if (!med.isEmpty()) {
            return med;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
    
    
     @Transactional
    public List<Medico> BuscarPorNAPCE(String esp1,Provincia provincia) throws Errores{
        List<Medico> med = medRep.BuscarNombreApellidoProvinciaEsp(esp1, provincia);
        if (!med.isEmpty()) {
            return med;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
    // ======================== END SERVICE FOR QUERIES ======================
    
    
    
    
    public void validar(String texto) throws Errores {
        if (texto == null || texto.isEmpty()) {
            throw new Errores("Los datos no pueden ser nulos.");
        }
    }
    
    //Hacer la busqueda de centros medicos, para eso necesito tener hecho el repositorio de centro medico
//    
//            @Autowired
//            PacienteSe pase;
// @Override
//    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
//        try {
//            Medico med = BuscarPorMatricula(Integer.parseInt(matricula));
//           
//                List<GrantedAuthority> permisos = new ArrayList<>();
//
//                GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_MEDICO_AUTORIZADO");
//
//                permisos.add(p1);
//
//                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//                HttpSession session = attr.getRequest().getSession(true);
//                session.setAttribute("medicosesion", med);
//
//                User user = new User(med.getMatricula().toString(), med.getClave(), permisos);
//                System.out.println("fedew"+med.getMatricula()+" "+med.getClave());
//                return user;
//            
//
//        } catch (Errores ex) {
//            System.out.println("medico no valido");
//            
//            
//        }
//        
//        return null;
//
//    }


     @Autowired
    private MedicoRep usersRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
        System.out.println("holasldkfjadsfjadñs");
        Optional<Medico> usersList = usersRepository.findById(Integer.parseInt(userName));
        System.out.println(userName+"efwl");
        if (usersList.isPresent()) {
            Medico users = usersList.get();
            System.out.println(users.getMatricula()+" "+users.getClave());
            List<String> roleList = new ArrayList<String>();
            //for (Role role : users.getRoles()) {
                roleList.add("MEDICO");
            //}

             ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("medicosesion", users);
            
            return User.builder()
                    .username(users.getMatricula().toString())
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
