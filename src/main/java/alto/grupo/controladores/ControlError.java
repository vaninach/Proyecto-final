/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alto.grupo.controladores;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author vani
 */
@Controller
public class ControlError implements ErrorController{
    
    @RequestMapping(value="/error", method = {RequestMethod.GET,RequestMethod.POST})
    
    public ModelAndView RenderError(HttpServletRequest httpRequest){
        ModelAndView errorPag=new ModelAndView("error");
        String errormsg="";
        int httpErrorCode=getErrorCode(httpRequest);
        switch(httpErrorCode){
            case 400:
                errormsg="El recurso solicitado no existe";
                break;
            case 403:
                errormsg="No tiene permisos para acceder al recurso";
                break;
            case 401:
                errormsg="No se encuentra autorizado";
                break;
            case 404:
                errormsg="El resurso solicitado no fue encontrado";
                break;
            case 500:
                errormsg="Ocurio un error interno";
                break;
        }
        
        errorPag.addObject("codigo",httpErrorCode);
        errorPag.addObject("mensaje",errormsg);
        
        return errorPag;
    }
    
    
    private int getErrorCode (HttpServletRequest httpRequest){
        Map mapa =httpRequest.getParameterMap();
        for(Object key: mapa.keySet()){
            String[] valores = (String[]) mapa.get(key);
            for(String valor:valores){
                System.out.println(key.toString()+":"+ valor);
            }
        }
        
        Enumeration<String> atributos = httpRequest.getAttributeNames();
        while(atributos.hasMoreElements()){
            String key=atributos.nextElement();
            System.out.println(key+":"+httpRequest.getAttribute(key));
        }
        
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
        
    }
}

