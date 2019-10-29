package Hospital.HospitalSpring.service;

import Hospital.HospitalSpring.domain.Doctor;
import Hospital.HospitalSpring.domain.Usuario;

public interface Validar {

		public boolean validar(Usuario usr);
		
		public void Registro(Doctor doc);
		
		public Doctor crearDoctor(Usuario usu);
}
