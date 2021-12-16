/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Archivos;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.ArchivosRep;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Mariano
 */
@Service
public class ArchivosSe {
    
    @Autowired
    ArchivosRep archivoRep;

     @Transactional
    public List<Archivos> BuscarPorDNI(String DNI) throws Errores {
        List<Archivos> ar = archivoRep.buscarDNI(DNI);
        if(ar.size()!=0) return ar;
        else  throw new Errores("No se encontraron estudios");
        
    }
    
      @Transactional
    public List<Archivos> BuscarPorDNIFecha(String DNI,String fecha) throws Errores {
        List<Archivos> ar = archivoRep.buscarDNIFecha(DNI, fecha);
        if(ar.size()!=0) return ar;
        else  throw new Errores("No se encontraron estudios");
        
    }
    
       @Transactional
    public List<Archivos> BuscarPorDNIFechaEsp(String DNI,String fecha,String especialidad) throws Errores {
        List<Archivos> ar = archivoRep.buscarDNIFechaEsp(DNI, fecha, especialidad);
        if(ar.size()!=0) return ar;
        else  throw new Errores("No se encontraron estudios");
        
    }
    
       @Transactional
    public List<Archivos> BuscarPorDNIEsp(String DNI,String especialidad) throws Errores {
        List<Archivos> ar = archivoRep.buscarDNIEsp(DNI, especialidad);
        if(ar.size()!=0) return ar;
        else  throw new Errores("No se encontraron estudios");
        
    }
}
 