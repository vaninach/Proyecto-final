/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Genero;
import alto.grupo.enums.GrupoS;
import alto.grupo.errores.Errores;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.transaction.Transactional;
import alto.grupo.repositorios.PacienteRep;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author vani
 */
@Service
public class PacienteSe implements UserDetailsService {

    @Autowired private PacienteRep parep;

    // ======================== CRUD ======================
    @Transactional
    public void crear(Paciente paciente) throws Errores{
        Optional<Paciente> paci = parep.findById(paciente.getDNI());
        if (!paci.isPresent()) {
            try {
                validar(paciente.getNombre());
                validar(paciente.getApellido());
                validar(paciente.getFechaNac()); // esta validacion?? no haria falta porque aparece el calensaario?
                validar(paciente.getGenero()); 
                validar(paciente.getEstadoCivil());
                validar(paciente.getTelefono());
                validar(paciente.getMail());
                validar(paciente.getNombreContacto());
                validar(paciente.getTelefonoContacto());
               // validar(paciente.getGrupoS());
                validar(paciente.getObraS1());
                validar(paciente.getnAfiliadoOS1());
                validar(paciente.getObraS2());
                validar(paciente.getnAfiliadoOS2());
                validar(paciente.getObraS3());
                validar(paciente.getnAfiliadoOS3());
                validar(paciente.getNacionalidad());
                validar(paciente.getProvincia());
                validar(paciente.getCiudad());
                validar(paciente.getCalle());
                validar(paciente.getNumero());
                validar(paciente.getPiso());
                validar(paciente.getDepartamento());
                // validar(paciente.getOtros()); <-- si puede ser nulo
                validar(paciente.getClave());  // no seria otro metodo? que aparte verifique que las dos claves son =?
                String encriptada = new BCryptPasswordEncoder().encode(paciente.getClave());
                paciente.setClave(encriptada);

                parep.save(paciente);

            } catch (Errores e) {
                throw new Errores(e.getMessage());
            }
        } else {
            throw new Errores("El paciente ya tiene una cuenta");
        }
    }
    
    @Transactional
    public void Crearpaciente(String DNI, String nombre, String apellido, String fechaNac, String genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, String grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, String provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        Optional<Paciente> paci = parep.findById(DNI);
        if (!paci.isPresent()) {

            try {
                validar(nombre);
                validar(apellido);
                validar(fechaNac);
                validar(genero);  
                validar(estadoCivil);
                validar(telefono);
                validar(mail);
                validar(nombreContacto);
                validar(telefonoContacto);
                validar(grupoS);
                //validar(obraS1);  // No lo vamos a validar en la creacion
                //validar(nAfiliadoOS1);    // se pueden agregar luego
                //validar(obraS2);
                //validar(nAfiliadoOS2);
                //validar(obraS3);
                //validar(nAfiliadoOS3);
                validar(nacionalidad);
                validar(provincia);
                validar(ciudad);
                validar(calle);
                validar(numero);
                validar(piso);
                validar(departamento);
                // validar(otros); <-- si puede ser nulo
                validar(clave);  // no seria otro metodo? que aparte verifique que las dos claves son =?

                Paciente pac = new Paciente();
                pac.setDNI(DNI);
                pac.setNombre(nombre);
                pac.setApellido(apellido);
                pac.setFechaNac(fechaNac);
                pac.setGenero(genero);
                pac.setEstadoCivil(estadoCivil);
                pac.setTelefono(telefono);
                pac.setMail(mail);
                pac.setNombreContacto(nombreContacto);
                pac.setTelefonoContacto(telefonoContacto);
                pac.setGrupoS(grupoS);
                pac.setObraS1(obraS1);
                pac.setnAfiliadoOS1(nAfiliadoOS1);
                pac.setObraS2(obraS2);
                pac.setnAfiliadoOS2(nAfiliadoOS2);
                pac.setObraS3(obraS3);
                pac.setnAfiliadoOS3(nAfiliadoOS3);
                pac.setNacionalidad(nacionalidad);
                pac.setProvincia(provincia);
                pac.setCiudad(ciudad);
                pac.setCalle(calle);
                pac.setNumero(numero);
                pac.setPiso(piso);
                pac.setDepartamento(departamento);
                pac.setOtros(otros);
                
                String encriptada = new BCryptPasswordEncoder().encode(clave);
                pac.setClave(encriptada);

                parep.save(pac);

            } catch (Errores e) {
                throw new Errores(e.getMessage());
            }
        } else {
            throw new Errores("El paciente ya tiene una cuenta");
        }
    }

    @Transactional
    public void Modificar(String DNI, String nombre, String apellido, String fechaNac, String genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, String grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, String provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {

        Optional<Paciente> paciop = parep.findById(DNI);
        if (paciop.isPresent()) {

            Paciente pac = paciop.get();

            validar(nombre);
            pac.setNombre(nombre);
            validar(apellido);
            pac.setApellido(apellido);
            
            validar(fechaNac);  
            pac.setFechaNac(fechaNac);
            
            validar(genero);
            pac.setGenero(genero);
            
            validar(estadoCivil);
            pac.setEstadoCivil(estadoCivil);
            
            validar(telefono);
            pac.setTelefono(telefono);
            
            validar(mail);
            pac.setMail(mail);
            
            validar(nombreContacto);
            pac.setNombreContacto(nombreContacto);
            validar(telefonoContacto);
            pac.setTelefonoContacto(telefonoContacto);
            
            validar(grupoS);
            pac.setGrupoS(grupoS);
            
            pac.setObraS1(obraS1);      // puede ser vacio
            if(!(obraS1==null || obraS1.isEmpty())){ validar(nAfiliadoOS1);System.out.println("aca afiliado so1"); }
            pac.setnAfiliadoOS1(nAfiliadoOS1);
            
            pac.setObraS1(obraS2); // puede ser vacio
            if(!(obraS2==null || obraS2.isEmpty())){ validar(nAfiliadoOS2);System.out.println("aca afiliado so2");}
            pac.setnAfiliadoOS1(nAfiliadoOS2);
            
            pac.setObraS3(obraS3); // puede ser vacio
            if(!(obraS3==null || obraS3.isEmpty())){ validar(nAfiliadoOS3);System.out.println("aca afiliado so3");}
            pac.setnAfiliadoOS3(nAfiliadoOS3);
            
            validar(nacionalidad);
            pac.setNacionalidad(nacionalidad);
            
            validar(provincia);  
            pac.setProvincia(provincia);
            
            validar(ciudad);
            pac.setCiudad(ciudad);
            
            validar(calle);
            pac.setCalle(calle);
            
            validar(numero);
            pac.setNumero(numero);
            
            pac.setPiso(piso);      // puede ser vacio
            
            validar(departamento);
            pac.setDepartamento(departamento);
            
            pac.setOtros(otros);      // puede ser vacio
            
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            if(buscarCambiosClave(clave))
                pac.setClave(encriptada);

            parep.save(pac);

        } else {
            throw new Errores("El paciente no exite en la base de datos");
        }

    }

    @Transactional
    public void Eliminar(String DNI) throws Errores {
        Optional<Paciente> paci = parep.findById(DNI);
        if (paci.isPresent()) {
            parep.deleteById(DNI);
        } else {
            throw new Errores("No se encontro el paciente solicitado");
        }

    }
    // ======================== END CRUD ======================
    
    
    // ======================== SERVICE FOR QUERIES ======================   
    @Transactional
    public Paciente BuscarPorDNI(String DNI) throws Errores {
        Optional<Paciente> paci = parep.findById(DNI);
        if (paci.isPresent()) {
            return paci.get();
        } else {
            throw new Errores("No se encontro el paciente solicitado");
        }
    }

    @Transactional
    public List<Paciente> BuscarPorNAPC(String nombre, String apellido, String provincia, String ciudad) throws Errores {
        List<Paciente> paci = parep.BuscarNombreApellidoProvincia(nombre, apellido, provincia, ciudad);
        if (!paci.isEmpty()) {
            return paci;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }

    @Transactional
    public List<Paciente> BuscarPorNAPC(String nombre, String apellido, String provincia) throws Errores {
        List<Paciente> paci = parep.BuscarNombreApellidoProvincia(nombre, apellido, provincia);
        if (!paci.isEmpty()) {
            return paci;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }

    @Transactional
    public List<Paciente> BuscarPorNAPC(String nombre, String apellido) throws Errores {
        List<Paciente> paci = parep.BuscarNombreApellidoProvincia(nombre, apellido);
        if (!paci.isEmpty()) {
            return paci;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }
    // ======================== END SERVICE FOR QUERIES ======================
    
    
    // ================= VALIDACIONES Y BUSQUEDA DE CAMBIOS ==================
    // Actualmente, busqueda de cambios y validaciones son basicamente iguales
    // Plan futuro para busqueda de cambios es comparar con la bdd
    // Entonces necesitara validar sus argumentos y luego compararlos con bdd
    private Boolean buscarCambiosClave(String text) {
        return !(text == null || text.isEmpty());
    }
    
    private void validar(String texto) throws Errores {
        if (texto == null || texto.isEmpty()) {
            throw new Errores("Los datos no pueden ser nulos.");
        }
    }
    
    private void validar(Integer nb) throws Errores {
        if (nb == null){
            throw new Errores("Los datos no pueden ser nulos");
        }
    }
    
    // Posible metodo util para la lista de Long (centros medicos)
    private void validar(Long l) throws Errores {
        
    }
    
    
    // ============== END VALIDACIONES Y BUSQUEDA DE CAMBIOS =================

//    @Override
//    public UserDetails loadUserByUsername(String DNI) throws UsernameNotFoundException {
//        try {
//            
//            System.out.println(DNI+"flaksalfk");
//            Paciente pac = BuscarPorDNI(DNI);
//           
//                List<GrantedAuthority> permisos = new ArrayList<>();
//
//                GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PACIENTE");
//
//                permisos.add(p1);
//
//                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//                HttpSession session = attr.getRequest().getSession(true);
//                session.setAttribute("pacientesesion", pac);
//
//                User user = new User(pac.getDNI(), pac.getClave(), permisos);
//                System.out.println(pac.getDNI()+" "+pac.getClave());
//                return user;
//            
//
//        } catch (Errores ex) {
//            System.out.println("paciente no valido");
//            return null;
//        }
//        
//        
//
//    }
    @Autowired
    private PacienteRep usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
        System.out.println(userName+"efwl");
        Optional<Paciente> usersList = usersRepository.findById(userName);
        System.out.println(userName+"efwl");
        if (usersList.isPresent()) {
            Paciente users = usersList.get();
            System.out.println(users.getDNI()+" "+users.getClave());
            List<String> roleList = new ArrayList<String>();
            //for (Role role : users.getRoles()) {
                roleList.add("PACIENTE");
            //}
            
             ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("pacientesesion", users);

            return User.builder()
                    .username(users.getDNI())
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
