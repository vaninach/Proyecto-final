

package alto.grupo.controladores;

import alto.grupo.entidades.Archivos;
import alto.grupo.entidades.CentroMedico;
import alto.grupo.entidades.HistoriasClinicas;
import alto.grupo.entidades.Medico;
import alto.grupo.entidades.Paciente;
import alto.grupo.errores.Errores;
import alto.grupo.repositorios.ArchivosRep;
import alto.grupo.servicios.ArchivosSe;
import alto.grupo.servicios.CentroMedicoSe;
import alto.grupo.servicios.HistClinicaSe;
import alto.grupo.servicios.MedicoSe;
import alto.grupo.servicios.PacienteSe;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Mariano
 */
@Controller
@RequestMapping("/")
public class MedicoController {

    @Autowired
    private MedicoSe medicose;
    
    @Autowired
    private PacienteSe pacienteSe;
    
    @Autowired
    private CentroMedicoSe centromedicoSe;
    
    @Autowired
    private HistClinicaSe histclinSe;
    
    @Autowired
    private ArchivosSe archivosSe;
    
    @Autowired
    private ArchivosRep arvhivosrep;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/Medico/login")
    public String Medicologin() {
        return "Medico/loginDoctor.html";
    }


    //agregado por nacho //
    @RequestMapping("/Medico/inicioMedico")
    public String inicioCentroMedico() {
        return "Medico/principalMedico.html";
    }

    @GetMapping("CentroMedico/NuevoMedico2")
    public String Medico2(Model modelo, Medico medico) {
        modelo.addAttribute("medico", medico);
        return "Medico/NuevoMedico_1.html";
    }

    @PostMapping("CentroMedico/NuevoMedico2")
    public String nuevoMedico2(Model modelo, Medico medico,String clave2,RedirectAttributes re) throws Errores {
        modelo.addAttribute("medico", medico);

        try {
            if(!medico.getClave().equals(clave2)) throw new Errores("Las claves no coinciden, intentelo nuevamente");
            medicose.crear(medico);
            re.addFlashAttribute("mensajeE", "Usuario creado con ??xito");
            sendEmail(medico.getMail());
        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            modelo.addAttribute("mensaje", mensaje);
            return "Medico/NuevoMedico_1.html";
        }
        return "redirect:NuevoMedico2";
    }

    @GetMapping("Medico/editar-perfil-M")
    public String modificarMedico(Model modelo, HttpSession session, @RequestParam Integer matricula, final Medico medico) {
        
        Medico med = (Medico) session.getAttribute("medicosesion");

        if (med.getMatricula().intValue() != matricula) {
            return "redirect:/NuevoMedico2";
        }
        modelo.addAttribute("medico", med);

        return "Medico/modificarMedico";
    }

    @PostMapping("Medico/modificarMedicos")
    public String modificarMedico2(final Medico med, HttpSession session, Model model,String clave2) {
        try {
            
             if(clave2!=null && !med.getClave().equals(clave2)) throw new Errores("Las claves no coinciden, intentelo nuevamente");
            
            medicose.modificar(med.getMatricula(), med.getNombre(), med.getApellido(), med.getFechaNac(), med.getGenero(), med.getMail(), med.getProvincia(), med.getCiudad(), med.getOtros(), med.getClave(), med.getEspecialidad1(), med.getEspecialidad2(), med.getEspecialidad3(), null);
            session.setAttribute("medicosesion", med);
        } catch (Errores ex) {
            String mensaje = ex.getMessage();
            model.addAttribute("mensaje", mensaje);
            return "Medico/modificarMedico";
        }
        return "Medico/modificarMedico";
    }

    void sendEmail(String email) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Log In correcto");
        msg.setText("El usuario ha sido registrado con exito");

        javaMailSender.send(msg);

    }
    
    
    
    @GetMapping("Medico/BuscarPaciente")
    public String BuscarHC(HttpSession session, Model model){
        Medico med = (Medico)session.getAttribute("medicosesion");

        if(!(med == null)){
            model.addAttribute("miprovincia", med.getProvincia());
            model.addAttribute("miciudad", med.getCiudad());
        }else{
            System.out.println("MEDICO NULL");
        }
        return "Medico/BuscarPaciente";
    }

    @PostMapping("/Medico/BuscarPaciente")
    public String resultadosBusquedaPaciente(HttpSession session, Model model, @RequestParam String nombre,@RequestParam String apellido, @RequestParam String DNI, @RequestParam String provincia, @RequestParam String ciudad, RedirectAttributes re) throws Errores{
        List<Paciente> lista = new ArrayList<>();
        List<String> listaEdad = new ArrayList<>();  ///// para calcular la edad y pasarlo al front

        Medico med = (Medico)session.getAttribute("medicosesion");

        if(med == null){
            re.addFlashAttribute("mensajeR", "Debe registrarse!");
            return "redirect:/inicio";
        }
        
        // si tengo DNI
        if( !(DNI==null || DNI.isEmpty()) ){
            Paciente pac = pacienteSe.BuscarPorDNI(DNI);
            lista.add(pac);
        }
        else if(!(provincia==null || provincia.isEmpty() )){
            if(!(ciudad==null || ciudad.isEmpty() )){
                lista = pacienteSe.BuscarPorNAPC(nombre, apellido, provincia, ciudad);
            }else{
                lista = pacienteSe.BuscarPorNAPC(nombre, apellido, provincia);
            }
        }

        else{   // tengo nombre y apellido
            lista = pacienteSe.BuscarPorNAPC(nombre,apellido);
        }

        // calcular edad
        for (Paciente paciente : lista) {
            System.out.println("CALCULANDO EDAD PARA " + paciente.getNombre());
            String edad;
            String fechaNacS = paciente.getFechaNac();
            //SimpleDateFormat formatter = new SimpleDateFormat("");
            LocalDate hoy = LocalDate.now(); 
            try {
                LocalDate fechaNac = LocalDate.parse(fechaNacS); 
                Period p = Period.between(fechaNac, hoy);
                listaEdad.add(Integer.toString(p.getYears()));
            } catch (Exception e) {  // si no puede parsear la fecha de nacimiento
                listaEdad.add("-");
                System.out.println("Fallo.....");
            }
        }
        
        model.addAttribute("lista",lista);
        model.addAttribute("listaedad",listaEdad);
        System.out.println("\n\n\nEDAD");
        System.out.println(listaEdad);
        
        return "Medico/BuscarPaciente";
    }
    
    @GetMapping("/Medico/BuscarHistoriasClinicas")
    public String buscarHC(HttpSession session, String dni, Model model) throws Errores{
        List<HistoriasClinicas> listaHC = new ArrayList<>();
        List<String> listaMedicos = new ArrayList<>();
        List<String> listaCentroMedico = new ArrayList<>();

        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            throw new Error("Debe registrarse!");
        }
        
        Paciente pac = pacienteSe.BuscarPorDNI(dni); 
        
        model.addAttribute("paciente", pac);
        
        try {
            listaHC = histclinSe.buscarPorDNI(pac.getDNI());
        } catch (Errores ex) {
            String mensaje = "No se encontraron historias clinicas del paciente";
            model.addAttribute("mensaje", mensaje);
        }
        
        if (listaHC.size() != 0){
            for (HistoriasClinicas historiasClinicas : listaHC) {
                try {
                    Medico medico = medicose.BuscarPorMatricula(historiasClinicas.getMatricula());
                    if (medico != null) {
                        listaMedicos.add(medico.getNombre() + " " + medico.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedicos.add("INVALID");
                }

                try {
                    CentroMedico cmed = centromedicoSe.buscarPorCodigo(historiasClinicas.getCentromedico());
                    if (cmed != null) {
                        listaCentroMedico.add(cmed.getNombre());
                    }
                } catch (Errores ex) {
                    listaCentroMedico.add("INVALID");
                }
            }
        }
                
        model.addAttribute("listaHC",listaHC);
        model.addAttribute("listamedico", listaMedicos);
        model.addAttribute("listacentromedico", listaCentroMedico);
        
        return "Medico/BuscarHC";
    }
    
    @PostMapping("/Medico/BuscarHistoriasClinicas")
    public String verHCfiltro(HttpSession session, Model model, @RequestParam String DNI, @RequestParam String fechaVisita, @RequestParam String especialidad, @RequestParam String centroMedico, @RequestParam(required=false) String soloMio, RedirectAttributes re){
        List<HistoriasClinicas> listaHC = new ArrayList<>();
        List<String> listaMedicos = new ArrayList<>();
        List<String> listaCentroMedico = new ArrayList<>();
        Paciente pac = new Paciente();
        
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        try{
            pac = pacienteSe.BuscarPorDNI(DNI); 
        } catch (Errores ex) {
            String mensaje = "No se encontraron registros del paciente solicitado";
            model.addAttribute("mensaje", mensaje);
        }

        if (!(centroMedico == null || centroMedico.isEmpty())   ) {
            try {
                listaHC = histclinSe.buscarPorDNICentroMedico(pac.getDNI(), centroMedico);
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros del paciente en el centro m??dico solicitado";
                model.addAttribute("mensaje", mensaje);
            }
        } else if (!(especialidad == null || especialidad.isEmpty())) {
            try {
                listaHC = histclinSe.buscarPorDNIEspecialidad(pac.getDNI(), especialidad);
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la especialidad solicitada para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        } else if (!(fechaVisita == null || fechaVisita.isEmpty())) {
            try {
                listaHC = histclinSe.buscarPorDNIFecha(pac.getDNI(), fechaVisita);
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha solicitada para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        } else if (!(fechaVisita == null || fechaVisita.isEmpty()) && !(especialidad == null || especialidad.isEmpty())){
            try {
                listaHC = histclinSe.buscarPorDNIFechaEspecialidad(pac.getDNI(), fechaVisita, especialidad);
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha y especialidad solicitados para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        } else {
            try {
                listaHC = histclinSe.buscarPorDNI(pac.getDNI());
            } catch (Errores ex) {
                String mensaje = "No se encontraron historias clinicas para el paciente elegido.";
                model.addAttribute("mensaje", mensaje);
            }
        }       

        if("on".equals(soloMio)){
            Iterator<HistoriasClinicas> itr = listaHC.iterator();
            while(itr.hasNext()) {
                HistoriasClinicas histClin = itr.next();
                if(!Objects.equals(histClin.getMatricula(), med.getMatricula())){
                    itr.remove();
                }                
            }
        }
        
        if (listaHC.size() != 0) {
            for (HistoriasClinicas historiasClinicas : listaHC) {
                try {
                    Medico medico = medicose.BuscarPorMatricula(historiasClinicas.getMatricula());
                    if (medico != null) {
                        listaMedicos.add(medico.getNombre() + " " + medico.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedicos.add("INVALID");
                }

                try {
                    CentroMedico cmed = centromedicoSe.buscarPorCodigo(historiasClinicas.getCentromedico());
                    if (cmed != null) {
                        listaCentroMedico.add(cmed.getNombre());
                    }
                } catch (Errores ex) {
                    listaCentroMedico.add("INVALID");
                }
            }
        }
        
        model.addAttribute("paciente", pac);
      
        model.addAttribute("listaHC",listaHC);
        re.addFlashAttribute("listaHC",listaHC);
        model.addAttribute("listamedico", listaMedicos);
        re.addFlashAttribute("listamedico",listaMedicos);
        model.addAttribute("listacentromedico", listaCentroMedico);
        re.addFlashAttribute("listacentromedico",listaCentroMedico);
        
        return "Medico/BuscarHC";
        //return "inicio";
    }
    
    @GetMapping("/Medico/AgregarHistoriaClinica")
    public String agregarHC(Model model, HttpSession session, String dni/*, @RequestParam String fechaVisita, @RequestParam String especialidad, @RequestParam String centroMedico, @RequestParam String informe*/){
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            throw new Error("Debe registrarse!");
        }

        HistoriasClinicas hclinica = new HistoriasClinicas();
        hclinica.setDNI(dni);
        hclinica.setMatricula(med.getMatricula());
        hclinica.setEspecialidad(med.getEspecialidad1());
        hclinica.setMatricula(med.getMatricula());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        hclinica.setFechaVisita(formatter.format(date));
        
        model.addAttribute("hclinica",hclinica);
        
        return "HistoriasClinicas/NuevoHistoriaClinica";
    }
    
    // ======================= VINCULACION DE CM A MEDICOS ==========================
    @GetMapping("Medico/VincularCM")
    public String BuscarCM(HttpSession session, Model model){
        return "Medico/Vincular-CM";
    }

    @PostMapping("/Medico/VincularCM")
    public String resultadosBusquedaPaciente(HttpSession session, Model model, @RequestParam Long RegistroCM){
        Medico med = (Medico)session.getAttribute("medicosesion");

        if(med == null){
            model.addAttribute("mensaje", "Debe Registrarse!!");
            
            return "redirect:/inicio";
        }
        
       try{
           CentroMedico cmed=centromedicoSe.buscarPorCodigo(RegistroCM);
        List<CentroMedico> listaCM=new ArrayList<>();
        
           listaCM.add(cmed);
           model.addAttribute("listaCM", listaCM);
       }
       catch(Errores er){
           model.addAttribute("mensaje", er.getMessage());
       }
       
        
        return "Medico/Vincular-CM";
    }
    
    
    
    
//       @GetMapping("Medico/VincularCM")
//    public String BuscarCM(HttpSession session, Model model){
//        
//        return "Medico/Vincular-CM";
//    }
//
//    @PostMapping("/Medico/VincularCM")
//    public String resultadosBusquedaPaciente(HttpSession session, Model model, @RequestParam Long RegistroCM) throws Errores{
//        
//        CentroMedico cmed=centromedicoSe.buscarPorCodigo(RegistroCM);
//        List<CentroMedico> listaCM=new ArrayList<>();
//        
//        
//
//        Medico med = (Medico)session.getAttribute("medicosesion");
//
//        if(med == null){
//            model.addAttribute("mensaje", "Debe Registrarse!!");
//            
//            return "redirect:/inicio";
//        }
//        
//       if(cmed!=null){
//           listaCM.add(cmed);
//           model.addAttribute("listaCM", listaCM);
//       }
//       
//        
//        return "Medico/Vincular-CM";
//    }
    
    @GetMapping("/Medico/ElegirCM")
    public String ElegirCM(HttpSession session, Long id, Model model,RedirectAttributes re) throws Errores{
        
        
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        try{
        medicose.ModificarCentrosMedicos(med.getMatricula(), id);
        }
        catch(Errores ex){
            re.addFlashAttribute("mensaje", ex.getMessage());
            model.addAttribute("mensaje", ex.getMessage());
            return "redirect:/Medico/VincularCM";
        }
        
        
        model.addAttribute("mensaje", "Se vincul?? el centro medico exitosamente!!");
        re.addFlashAttribute("mensaje", "Se vincul?? el centro medico exitosamente!");
        
        return "redirect:/Medico/VincularCM";
    }
    
    
    @GetMapping("/Medico/MostrarCM")
    public String MostrarCM(HttpSession session, Model model,RedirectAttributes re) throws Errores{
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        
        List<Long> listaCM=medicose.MostrarCentrosMedicos(med.getMatricula());
        if(listaCM.size()!=0){
            List<CentroMedico> listaCM_fin=new ArrayList<>();
            for (Long long1 : listaCM) {
                CentroMedico cmed=centromedicoSe.buscarPorCodigo(long1);
                listaCM_fin.add(cmed);
            }
            model.addAttribute("listaCM", listaCM_fin);
        }
        else{
            model.addAttribute("mensaje", "No se encontraron Centros Medicos asociados");
        }
        return "/Medico/Mostrar-CM";
    }
    
    @GetMapping("/Medico/EliminarCM")
    public String MostrarCM(HttpSession session, Long id, Model model,RedirectAttributes re) throws Errores{
        
        Medico med = (Medico)session.getAttribute("medicosesion");
        if(med == null){
            return "redirect:/inicio";
        }
        
        
        medicose.EliminarCentrosMedicos(med.getMatricula(),id);

        
        re.addFlashAttribute("mensaje", "Registro eliminado exitosamente");
        
        
        return "redirect:/Medico/MostrarCM";
    }
    
    
    
    
       @GetMapping("/Medico/BuscarPorEstudios")

    public String BuscarE(HttpSession session, Model model,String dni) throws Errores {

        Paciente pac = pacienteSe.BuscarPorDNI(dni); 
        
        model.addAttribute("paciente", pac);
        
        return "Medico/BuscarEstudios";
    }

    @PostMapping("/Medico/BuscarPorEstudios")
    public String BuscarE(HttpSession session, Model model,@RequestParam String DNI ,@RequestParam String fecha, @RequestParam String especialidad, String nombreEst, String centroM, RedirectAttributes re) throws Errores {
        List<Archivos> listaAr=new ArrayList<>();
        List<String> listaMedP=new ArrayList<>();
        List<String> listaMedH=new ArrayList<>();
        List<String> listaCM=new ArrayList<>();
       
        Paciente pac = pacienteSe.BuscarPorDNI(DNI); 
        
        model.addAttribute("paciente", pac);
        
        Medico med = (Medico) session.getAttribute("medicosesion");

        if (med == null) {
            re.addFlashAttribute("mensaje", "Debe iniciar Sesi??n!");
            return "redirect:/inicio";

        }
        
        //Buscar por fecha
        if ( !(fecha == null || fecha.isEmpty()) && (especialidad == null || especialidad.isEmpty()) && (nombreEst == null || nombreEst.isEmpty())&& (centroM == null || centroM.isEmpty()) ) {
            try {
                listaAr = archivosSe.BuscarPorDNIFecha(DNI, fecha);
                System.out.println("entramos al 1");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros del paciente";
                model.addAttribute("mensaje", mensaje);
            }
        } 
        //Buscar por especialidad
        else if (   (fecha == null || fecha.isEmpty()) && !(especialidad == null || especialidad.isEmpty()) && (nombreEst == null || nombreEst.isEmpty())&& (centroM == null || centroM.isEmpty())   ) {
            try {
                listaAr = archivosSe.BuscarPorDNIEsp(DNI, especialidad);
                System.out.println("entramos al 2");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la especialidad solicitada";
                model.addAttribute("mensaje", mensaje);
            }
        } 
        //Buscar por fecha y especialidad
        else if (  !(fecha == null || fecha.isEmpty()) && !(especialidad == null || especialidad.isEmpty()) && (nombreEst == null || nombreEst.isEmpty())&& (centroM == null || centroM.isEmpty())  ) {
            try {
                listaAr = archivosSe.BuscarPorDNIFechaEsp(DNI, fecha, especialidad);
                System.out.println("entramos al 3");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha solicitada";
                model.addAttribute("mensaje", mensaje);
            }
        } 
        
         //Buscar por todos
        else if (  (fecha == null || fecha.isEmpty()) && (especialidad == null || especialidad.isEmpty()) && (nombreEst == null || nombreEst.isEmpty())&& (centroM == null || centroM.isEmpty())  ) {
            try {
                listaAr = archivosSe.BuscarPorDNI(DNI);
                System.out.println("entramos al 3");
            } catch (Errores ex) {
                String mensaje = "No se encontraron registros en la fecha solicitada";
                model.addAttribute("mensaje", mensaje);
            }
        } 
        //Alguna busqueda que no tuvimos en cuenta
        else {
            String mensaje = "No se puede realizar la busqueda con los parametros solicitados, intentelo nuevamente";
                model.addAttribute("mensaje", mensaje);
        }

        if (listaAr.size() != 0) {

            for (Archivos ar : listaAr) {
                try {
                    Medico med2 = medicose.BuscarPorMatricula(ar.getMatriculaPide());
                    if (med2 != null) {
                        listaMedP.add(med2.getNombre() + " " + med2.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedP.add("INVALID");
                }
                
                try {
                    Medico med2 = medicose.BuscarPorMatricula(ar.getMatriculaInforme());
                    if (med2 != null) {
                        listaMedH.add(med2.getNombre() + " " + med2.getApellido());
                    }
                } catch (Errores ex) {
                    listaMedH.add("INVALID");
                }

                try {
                    CentroMedico cmed = centromedicoSe.buscarPorCodigo(ar.getCentroMedico());
                    if (cmed != null) {
                        listaCM.add(cmed.getNombre());
                    }
                } catch (Errores ex) {
                    listaCM.add("INVALID");
                }

            }

        }

        model.addAttribute("lista", listaAr);
        model.addAttribute("listamedicoP", listaMedP);
        model.addAttribute("listamedicoH", listaMedH);
        model.addAttribute("listacentromedico", listaCM);

        re.addFlashAttribute("lista", listaAr);
        re.addFlashAttribute("listamedicoP", listaMedP);
        re.addFlashAttribute("listamedicoH", listaMedH);
        re.addFlashAttribute("listacentromedico", listaCM);

        return "Medico/BuscarEstudios";
    }

   
    
    
    
     @GetMapping("/Medico/download")
    public void downloadFile(@RequestParam("id") Long id, HttpServletResponse response) throws Exception {
        System.out.println(id + " Por aca anda...");
        Optional<Archivos> result = arvhivosrep.findById(id);
        System.out.println("Por aca sigue andando...2");
        if (!result.isPresent()) {
            throw new Exception("No se ha encontrado archivo con el ID" + id);
        }
        
        
        

        Archivos archivo = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + archivo.getNombre();

        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(archivo.getContenido());
        outputStream.close();
        
        
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
