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

    public void Crearpaciente(String DNI, String nombre, String apellido, Date fechaNac, Genero genero, String estadoCivil, String telefono, String mail, String nombreContacto, String telefonoContacto, GrupoS grupoS, String obraS1, String nAfiliadoOS1, String obraS2, String nAfiliadoOS2, String obraS3, String nAfiliadoOS3, String nacionalidad, Provincia provincia, String ciudad, String calle, String numero, String piso, String departamento, String otros, String clave) throws Errores {
        Optional<paciente> paci = parep.findById(DNI);
        if (!paci.isPresent()) {

            try {
                
                validar(DNI);
                validar(nombre);
                validar(apellido);
//                validar(fechaNac); // esta validacion??
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
                validar(otros);
                validar(clave);
                
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

    private void validar(String nombre) throws Errores {
        if (nombre == null || nombre.isEmpty()) {
            throw new Errores("el dato " + nombre + "no es valido");
        }

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
