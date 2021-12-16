/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.servicios;

import alto.grupo.entidades.Archivos;
import alto.grupo.repositorios.ArchivosRep;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Mariano
 */
@Service
public class ArchivosSe {
    
//    @Autowired
//    private ArchivosRep archivosrepo;
//    
//    
//    
//    @Transactional
//    public void subirArchivo() throws IOException{
//        
//        File file = new File("C:\\Users\\Mariano\\Desktop\\reintegro.pdf");
//        Archivos archivo = new Archivos();
//        
//        archivo.setNombre(file.getName());
//        
//        byte[] bytes = Files.readAllBytes(file.toPath());
//        
//        archivo.setContenido(bytes);
//        
//        long tamañoarchivo = bytes.length;
//        archivo.setTamaño(tamañoarchivo);
//        archivo.setSubida(new Date());
//        System.out.println(archivo.getNombre());
//       Archivos archivoguardado = archivosrepo.save(archivo);
//       
//       
//       
//       
//        
//        
//        
//       
//        
//        
//        
//        
//    }
//    
}
 