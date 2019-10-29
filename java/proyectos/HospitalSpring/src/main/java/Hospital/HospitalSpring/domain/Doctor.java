package Hospital.HospitalSpring.domain;

public class Doctor {

		private String nombre;
		private int edad;
		private String sexo;
		private String Direccion;
		private String Especialidad;
		private String correo;
		private String contrasena;
		
		public Doctor() {
			
		}
		
		public Doctor(String nombre, int edad, String sexo, String direccion, String especialidad, String correo, String contrasena) {
			this.nombre = nombre;
			this.edad = edad;
			this.sexo = sexo;
			Direccion = direccion;
			Especialidad = especialidad;
			this.correo = correo;
			this.contrasena = contrasena;
		}

		public String getNombre() {
			return nombre;
		}

		public int getEdad() {
			return edad;
		}

		public String getSexo() {
			return sexo;
		}

		public String getDireccion() {
			return Direccion;
		}

		public String getEspecialidad() {
			return Especialidad;
		}

		public String getCorreo() {
			return correo;
		}

		public String getContrasena() {
			return contrasena;
		}
		
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public void setEdad(int edad) {
			this.edad = edad;
		}

		public void setSexo(String sexo) {
			this.sexo = sexo;
		}

		public void setDireccion(String direccion) {
			Direccion = direccion;
		}

		public void setEspecialidad(String especialidad) {
			Especialidad = especialidad;
		}

		public void setCorreo(String correo) {
			this.correo = correo;
		}

		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}
		
		
}
