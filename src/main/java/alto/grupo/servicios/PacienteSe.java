/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Paciente;
import alto.grupo.enums.Genero;
import alto.grupo.enums.GrupoS;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.transaction.Transactional;
import alto.grupo.repositorios.PacienteRep;
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
                validar(paciente.getEstadoCivil());
                validar(paciente.getTelefono());
                validar(paciente.getMail());
                validar(paciente.getNombreContacto());
                validar(paciente.getTelefonoContacto());
                validar(paciente.getObraS1());
                validar(paciente.getnAfiliadoOS1());
                validar(paciente.getObraS2());
                validar(paciente.getnAfiliadoOS2());
                validar(paciente.getObraS3());
                validar(paciente.getnAfiliadoOS3());
                validar(paciente.getNacionalidad());
                validar(paciente.getCiudad());
                validar(paciente.getCalle());
                validar(paciente.getNumero());
                validar(paciente.getPiso());
                validar(paciente.getDepartamento());
                // validar(paciente.getOtros()); <-- si puede ser nulo
                validar(paciente.getClave());  // no seria otro metodo? que aparte verifique que las dos claves son =?

                parep.save(paciente);

            } catch (Errores e) {
                System.out.println(e);
            }
        } else {
            throw new Errores("El paciente ya tiene una cuenta");
        }
    }
    
    @Transactional
    public void Crearpaciente(String DNI, String nombre, String apellido, String fechaNac, Genero genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, GrupoS grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        Optional<Paciente> paci = parep.findById(DNI);
        if (!paci.isPresent()) {

            try {

                validar(DNI);
                validar(nombre);
                validar(apellido);
                validar(fechaNac); // esta validacion?? no haria falta porque aparece el calensaario?
                validar(estadoCivil);
                validar(telefono);
                validar(mail);
                validar(nombreContacto);
                validar(telefonoContacto);
                validar(obraS1);
                validar(nAfiliadoOS1);
                validar(obraS2);
                validar(nAfiliadoOS2);
                validar(obraS3);
                validar(nAfiliadoOS3);
                validar(nacionalidad);
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
                System.out.println(e);
            }
        } else {
            throw new Errores("El paciente ya tiene una cuenta");
        }
    }

    @Transactional
    public void Modificar(String DNI, String nombre, String apellido, String fechaNac, Genero genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, GrupoS grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {

        Optional<Paciente> paciop = parep.findById(DNI);
        if (paciop.isPresent()) {

            Paciente pac = paciop.get();

            if (BuscarCambios(DNI))
                pac.setDNI(DNI);
            if (BuscarCambios(nombre))
                pac.setNombre(nombre);
            if (BuscarCambios(apellido))
                pac.setApellido(apellido);
            if(BuscarCambios(fechaNac))  //ver esta validacion
                pac.setFechaNac(fechaNac);
            //     if(BuscarCambios(genero))     //ver esta validacion
            pac.setGenero(genero);
            if (BuscarCambios(estadoCivil))
                pac.setEstadoCivil(estadoCivil);
            if (BuscarCambios(telefono))
                pac.setTelefono(telefono);
            if (BuscarCambios(mail))
                pac.setMail(mail);
            if (BuscarCambios(nombreContacto))
                pac.setNombreContacto(nombreContacto);
            if (BuscarCambios(telefono))
                pac.setTelefono(telefono);
            //  if(BuscarCambios(grupoS))     //ver esta validacion
            pac.setGrupoS(grupoS);
            if (BuscarCambios(obraS1))
                pac.setObraS1(obraS1);
            if (BuscarCambios(nAfiliadoOS1))
                pac.setnAfiliadoOS1(nAfiliadoOS1);
            if (BuscarCambios(obraS2))
                pac.setObraS1(obraS2);
            if (BuscarCambios(nAfiliadoOS2))
                pac.setnAfiliadoOS1(nAfiliadoOS2);
            if (BuscarCambios(obraS3))
                pac.setObraS1(obraS3);
            if (BuscarCambios(nAfiliadoOS3))
                pac.setnAfiliadoOS1(nAfiliadoOS3);
            if (BuscarCambios(nacionalidad))
                pac.setNacionalidad(nacionalidad);
            //  if(BuscarCambios(provincia))  // ver esta validacion
            pac.setProvincia(provincia);
            if (BuscarCambios(ciudad))
                pac.setCiudad(ciudad);
            if (BuscarCambios(calle))
                pac.setCalle(calle);
            if (BuscarCambios(numero))
                pac.setNumero(numero);
            if (BuscarCambios(piso))
                pac.setPiso(piso);
            if (BuscarCambios(departamento))
                pac.setDepartamento(departamento);
            if (BuscarCambios(otros))
                pac.setOtros(otros);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            if (BuscarCambios(clave))
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
    public List<Paciente> BuscarPorNAPC(String nombre, String apellido, Provincia provincia, String ciudad) throws Errores {
        List<Paciente> paci = parep.BuscarNombreApellidoProvincia(nombre, apellido, provincia, ciudad);
        if (!paci.isEmpty()) {
            return paci;
        } else {
            throw new Errores("No se encontro ningun paciente");
        }
    }

    @Transactional
    public List<Paciente> BuscarPorNAPC(String nombre, String apellido, Provincia provincia) throws Errores {
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
    
    

    private void validar(String nombre) throws Errores {
        if (nombre == null || nombre.isEmpty()) {
            throw new Errores("El dato " + nombre + "no es valido");
        }

    }

    private Boolean BuscarCambios(String nombre) {
        if (nombre == null && nombre.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

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
