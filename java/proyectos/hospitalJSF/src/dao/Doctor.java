package dao;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.swing.JOptionPane;

@ManagedBean(name = "doctor")
@RequestScoped
public class Doctor {

		private Date fecha;
		private static String nombre;
		private int edad;
		private String sexo;
		private String direccion;
		private String especialidad;
		private String correo;
		private String contrasena;
		
		public Doctor() {
			
		}
		
		public Doctor(String nombre, int edad, String sexo, String direccion, String especialidad, String correo, String contrasena) {
			this.nombre = nombre;
			this.edad = edad;
			this.sexo = sexo;
			this.direccion = direccion;
			this.especialidad = especialidad;
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
			return direccion;
		}

		public String getEspecialidad() {
			return especialidad;
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
			this.direccion = direccion;
		}

		public void setEspecialidad(String especialidad) {
			this.especialidad = especialidad;
		}

		public void setCorreo(String correo) {
			this.correo = correo;
		}

		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}
		
		public Date getFecha() {
			Date fa = new Date();
			return this.fecha = fa;
		}
		
		public String getRegistro() {
			Doctor doc = new Doctor(nombre, edad, sexo, direccion, especialidad, correo, contrasena);
			IhospitalDao hospitaldao = new hospitalDao();
			
			hospitaldao.insert(doc);
			JOptionPane.showMessageDialog(null, "Registro Guardado");
			return "registro";
		}
		
		public String getValidar() {
			IhospitalDao hospitaldao = new hospitalDao();
			String nombre = hospitaldao.login(correo, contrasena);
			
			if(nombre != "") {
				this.nombre = nombre;
				return "admin";
			}
			JOptionPane.showMessageDialog(null, "Usuario y/o Contraseña Incorrectos");
			return "login";
		}
		
		public String getModificar() {
			IhospitalDao hospitaldao = new hospitalDao();
			Doctor doctor = new Doctor(nombre, edad, sexo, direccion, especialidad, correo, contrasena);
			hospitaldao.update(doctor);
			return "login";
		}
		
		public String getConsulta() {
			IhospitalDao hospitaldao = new hospitalDao();
			Doctor doc = hospitaldao.consulta(nombre);
			if(doc != null) {
			this.nombre = doc.getNombre();
			this.edad = doc.getEdad();
			this.sexo = doc.getSexo();
			this.especialidad = doc.getEspecialidad();
			this.direccion = doc.getDireccion();
			this.correo = doc.getCorreo();
			this.contrasena = doc.getContrasena();
			return "modifica";
			}
			JOptionPane.showMessageDialog(null, "Error al ");
			return "modifica";
			}
		
}
