/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.paciente;
import alto.grupo.enums.Genero;
import alto.grupo.enums.GrupoS;
import alto.grupo.enums.Provincia;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.pacienteRep;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vani
 */
@Service
public class pacienteSe implements UserDetailsService {

    @Autowired
    pacienteRep parep;

    //@Transactional
    public void Crearpaciente(String DNI, String nombre, String apellido, Date fechaNac, Genero genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, GrupoS grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        Optional<paciente> paci = parep.findById(DNI);
        if (!paci.isPresent()) {

            try {

                validar(DNI);
                validar(nombre);
                validar(apellido);
//                validar(fechaNac); // esta validacion?? no haria falta porque aparece el calensaario?
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
                

                paciente pac = new paciente();
                pac.setDNI(DNI);
                pac.setNombre(nombre);
                pac.setApellido(apellido);
                pac.setFechaNac(fechaNac);
                pac.setGenero(genero);
                pac.setEstadoCivil(estadoCivil);
                pac.setTelefono(telefono);
                pac.setMail(mail);
                pac.setNombreContacto(nombreContacto);
                pac.setTelefono(telefono);
                pac.setGrupoS(grupoS);
                pac.setObraS1(obraS1);
                pac.setnAfiliadoOS1(nAfiliadoOS1);
                pac.setObraS1(obraS2);
                pac.setnAfiliadoOS1(nAfiliadoOS2);
                pac.setObraS1(obraS3);
                pac.setnAfiliadoOS1(nAfiliadoOS3);
                pac.setNacionalidad(nacionalidad);
                pac.setProvincia(provincia);
                pac.setCiudad(ciudad);
                pac.setCalle(calle);
                pac.setNumero(numero);
                pac.setPiso(piso);
                pac.setDepartamento(departamento);
                pac.setOtros(otros);
                pac.setClave(clave);

                parep.save(pac);

            } catch (Errores e) {
                System.out.println(e);
            }
        } else {
            throw new Errores("El paciente ya tiene una cuenta");
        }

        return;

    }

    public void Modificar(String DNI, String nombre, String apellido, Date fechaNac, Genero genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, GrupoS grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {

        Optional<paciente> paciop = parep.findById(DNI);
        if (paciop.isPresent()) {

                paciente pac=paciop.get();

                if(BuscarCambios(DNI))
                    pac.setDNI(DNI);
                if(BuscarCambios(nombre))
                    pac.setNombre(nombre);
                if(BuscarCambios(apellido))
                    pac.setApellido(apellido);
           //     if(BuscarCambios(fechaNac))  //ver esta validacion
                pac.setFechaNac(fechaNac);
           //     if(BuscarCambios(genero))     //ver esta validacion
                pac.setGenero(genero);
                if(BuscarCambios(estadoCivil))
                    pac.setEstadoCivil(estadoCivil);
                if(BuscarCambios(telefono))
                    pac.setTelefono(telefono);
                if(BuscarCambios(mail))
                    pac.setMail(mail);
                if(BuscarCambios(nombreContacto))
                    pac.setNombreContacto(nombreContacto);
                if(BuscarCambios(telefono))
                    pac.setTelefono(telefono);
              //  if(BuscarCambios(grupoS))     //ver esta validacion
                pac.setGrupoS(grupoS);
                if(BuscarCambios(obraS1))
                    pac.setObraS1(obraS1);
                if(BuscarCambios(nAfiliadoOS1))
                    pac.setnAfiliadoOS1(nAfiliadoOS1);
                if(BuscarCambios(obraS2))
                    pac.setObraS1(obraS2);
                if(BuscarCambios(nAfiliadoOS2))
                    pac.setnAfiliadoOS1(nAfiliadoOS2);
                if(BuscarCambios(obraS3))
                    pac.setObraS1(obraS3);
                if(BuscarCambios(nAfiliadoOS3))
                    pac.setnAfiliadoOS1(nAfiliadoOS3);
                if(BuscarCambios(nacionalidad))
                    pac.setNacionalidad(nacionalidad);
              //  if(BuscarCambios(provincia))  // ver esta validacion
                pac.setProvincia(provincia);
                if(BuscarCambios(ciudad))
                    pac.setCiudad(ciudad);
                if(BuscarCambios(calle))
                    pac.setCalle(calle);
                if(BuscarCambios(numero))
                    pac.setNumero(numero);
                if(BuscarCambios(piso))
                    pac.setPiso(piso);
                if(BuscarCambios(departamento))
                    pac.setDepartamento(departamento);
                if(BuscarCambios(otros))
                    pac.setOtros(otros);
                if(BuscarCambios(clave))
                    pac.setClave(clave);

                parep.save(pac);

           
        } else {
            throw new Errores("El paciente no exite en la base de datos");
        }


        
        
    }

    public void Eliminar(String DNI) throws Errores {
        Optional<paciente> paci = parep.findById(DNI);
        if (paci.isPresent()) {
            parep.deleteById(DNI);
        } else {
            throw new Errores("No se encontro el paciente solicitado");
        }

    }
    
    
    public paciente BuscarPorDNI(String DNI) throws Errores{
        Optional<paciente> paci = parep.findById(DNI);
        if (paci.isPresent()) {
            return paci.get();
        } else {
            throw new Errores("No se encontro el paciente solicitado");
        }
    }
    
    
    
    

    private void validar(String nombre) throws Errores {
        if (nombre == null || nombre.isEmpty()) {
            throw new Errores("El dato " + nombre + "no es valido");
        }

    }
    
    private Boolean BuscarCambios(String nombre) {
        if (nombre == null && nombre.isEmpty()) {
            return false;
        }
        else return true;

    }

//     @Override
//    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
//        paciente pac = rec.BuscarPorUsuario(usuario);
//        if (cliente != null) {
//
//            List<GrantedAuthority> permisos = new ArrayList<>();
//
//            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_AUTORIZADO");
//
//            permisos.add(p1);
//            
//            
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            HttpSession session=attr.getRequest().getSession(true);
//            session.setAttribute("usuariosesion", cliente);
//
//            User user = new User(cliente.getUsuario(), cliente.getClave(), permisos);
//            return user;
//        } else {
//            return null;
//        }
//    }
    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
