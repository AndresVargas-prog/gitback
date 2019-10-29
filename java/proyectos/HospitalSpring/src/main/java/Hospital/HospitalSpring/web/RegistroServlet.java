package Hospital.HospitalSpring.web;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import Hospital.HospitalSpring.domain.Doctor;
import Hospital.HospitalSpring.service.Validar;
import Hospital.HospitalSpring.service.ValidarImp;

@Controller
@RequestMapping("/Registro")
@SessionAttributes("doctor")
public class RegistroServlet {
	Validar validar = new ValidarImp();
	
	@RequestMapping(method = RequestMethod.GET)
	public void setupForm() {
		//Doctor doctor = new Doctor();
		//model.addAttribute("doctor", doctor);
		//return "Registro";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String registro(@RequestParam("nombrer") String nombre, @RequestParam("edadr") int edad, @RequestParam("sexo") String sexo,
			@RequestParam("direccionr") String direccion, @RequestParam("especialidadr") String especialidad, @RequestParam("emailr") String email,
			@RequestParam("contrar") String contra, Model model) {
		Doctor doc = new Doctor(nombre, edad, sexo, direccion, especialidad, email, contra);
		validar.Registro(doc);
		
		Date fecha = new Date();
		model.addAttribute("fecha", fecha);
		model.addAttribute("nombre",doc.getNombre());
		return "Bienvenida";
	}
	/*
	@RequestMapping(method = RequestMethod.POST)
	public String Registro(@ModelAttribute("doctor") Doctor doc,BindingResult result,
			SessionStatus status, Model model) {
		validar.Registro(doc);
		model.addAttribute("nombre",doc.getNombre());
		return "Bienvenida";
	}*/
}
