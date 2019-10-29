package Hospital.HospitalSpring.web;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Hospital.HospitalSpring.domain.Doctor;
import Hospital.HospitalSpring.domain.Usuario;
import Hospital.HospitalSpring.service.Validar;
import Hospital.HospitalSpring.service.ValidarImp;

@Controller
@RequestMapping("/Login")
public class LoginServlet {
	Validar validar = new ValidarImp();
	@RequestMapping(method = RequestMethod.GET)
	public void setForm() {	
	}

	@RequestMapping(method = RequestMethod.POST)
	public String Login(@RequestParam("emaill") String email, @RequestParam("contral") String contra, Model model ) {
		Usuario usr = new Usuario(email,contra);
		boolean paso = validar.validar(usr);
		
		if(paso) {
			Date fecha = new Date();
			Doctor doc = validar.crearDoctor(usr);
			model.addAttribute("nombre",doc.getNombre());
			model.addAttribute("fecha", fecha);
			return "Bienvenida";
		}
		return "Error";
	}
	
}
