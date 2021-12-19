/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import alto.grupo.entidades.Archivos;
import alto.grupo.repositorios.ArchivosRep;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Mariano
 */
@Controller
public class ArchivosController {

    @Autowired
    private ArchivosRep archivosrep;

//    @GetMapping("/Archivos")
//    public String Archivos(Model modelo, Archivos archivo) {
//
//        List<Archivos> listarchivos = archivosrep.findAll();
//        modelo.addAttribute("listarchivos", listarchivos);
//        return "Archivos.html";
//    }
//
//    @PostMapping("/upload")
//    public String upload(@RequestParam("archivo") MultipartFile multipartfile, RedirectAttributes ra) throws IOException {
//        String nombrearchivo = StringUtils.cleanPath(multipartfile.getOriginalFilename());
//        Archivos archivo = new Archivos();
//        archivo.setNombre(nombrearchivo);
//        archivo.setContenido(multipartfile.getBytes());
//        archivo.setTamaño(multipartfile.getSize());
//        archivo.setSubida(new Date());
//        archivosrep.save(archivo);
//        ra.addFlashAttribute("message", "El archivo fue subido correctamente");
//        return "redirect:/Archivos";
//
//    }

//    @GetMapping("/download")
//    public void downloadFile(@RequestParam("id") Long id, HttpServletResponse response) throws Exception {
//        System.out.println(id + " Por aca anda...");
//        Optional<Archivos> result = archivosrep.findById(id);
//        System.out.println("Por aca sigue andando...2");
//        if (!result.isPresent()) {
//            throw new Exception("No se ha encontrado archivo con el ID" + id);
//        }
//
//        Archivos archivo = result.get();
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=" + archivo.getNombre();
//
//        response.setHeader(headerKey, headerValue);
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        outputStream.write(archivo.getContenido());
//        outputStream.close();
//
//    }
//    
//    
    
    
    
//    @GetMapping("/CentroMedico/NuevoEstudio")
//    public String NArchivos(Model modelo, Archivos archivo) {
//
//        List<Archivos> listarchivos = archivosrep.findAll();
//        modelo.addAttribute("listarchivos", listarchivos);
//        return "CentroMedico/NuevoEstudio";
//    }
//
//    @PostMapping("/CentroMedico/NuevoEstudio")
//    public String upload(@RequestParam("archivo") MultipartFile multipartfile, String DNI,String fechaVisita,Integer matPide, String especialidad, String nombreEst,Integer matInf, RedirectAttributes ra) throws IOException {
//        
//        
//        String nombrearchivo = StringUtils.cleanPath(multipartfile.getOriginalFilename());
//        Archivos archivo = new Archivos();
//        
//        archivo.setDNI(DNI);
//        archivo.setFechaVisita(fechaVisita);
//        archivo.setMatriculaPide(matPide);
//        archivo.setEspecialidad(especialidad);
//        archivo.setNombreEst(nombreEst);
//        archivo.setMatriculaInforme(matInf);
//        
//        archivo.setNombre(nombrearchivo);
//        archivo.setContenido(multipartfile.getBytes());
//        archivo.setTamaño(multipartfile.getSize());
//        archivo.setSubida(new Date());
//        archivosrep.save(archivo);
//        ra.addFlashAttribute("message", "El archivo fue subido correctamente");
//        return "redirect:/CentroMedico/NuevoEstudio";
//
//    }

//    @GetMapping("/download")
//    public void downloadFile(@RequestParam("id") Long id, HttpServletResponse response) throws Exception {
//        System.out.println(id + " Por aca anda...");
//        Optional<Archivos> result = archivosrep.findById(id);
//        System.out.println("Por aca sigue andando...2");
//        if (!result.isPresent()) {
//            throw new Exception("No se ha encontrado archivo con el ID" + id);
//        }
//
//        Archivos archivo = result.get();
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=" + archivo.getNombre();
//
//        response.setHeader(headerKey, headerValue);
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        outputStream.write(archivo.getContenido());
//        outputStream.close();
//
//    }
//    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
