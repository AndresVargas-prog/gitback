package Hospital.HospitalSpring.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Hospital.HospitalSpring.domain.Doctor;
import Hospital.HospitalSpring.domain.Usuario;

public class ValidarImp implements Validar{

	public static Scanner entrada = new Scanner(System.in);
	
	@Override
	public boolean validar(Usuario usr) {
		
		boolean con = false, us = false;
		try {
			
			File f = new File("F:\\HospitalSpring\\WebContent\\WEB-INF\\Usuarios.txt");
			FileReader fr = new FileReader(f);
			
			 if (!f.exists()) {
	             f.createNewFile();
	         }
			
			 entrada = new Scanner(fr);
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		
		String USR = "E-mail: "+usr.getUsuario();
		String CON = "Contraseña: "+usr.getContrasena();
		
		while(entrada.hasNextLine()) {
			
				String nex = entrada.nextLine();
				if(nex.equals(USR)) {
					us = true;
				}
				if(nex.equals(CON)) {
					con = true;
				}
		}
		
		if(us == true && con == true)
			return true;
		
		return false;
	}

	@Override
	public void Registro(Doctor doc){
		
		try {
			File f = new File("F:\\HospitalSpring\\WebContent\\WEB-INF\\Usuarios.txt");
			FileWriter fw = new FileWriter(f,true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.newLine();
			bw.append("Nombre: " + doc.getNombre());
			bw.newLine();
			bw.append("Edad: " + doc.getEdad());
			bw.newLine();
			bw.append("Sexo: " + doc.getSexo());
			bw.newLine();
			bw.append("Direccion: " + doc.getDireccion());
			bw.newLine();
			bw.append("Especialidad: " + doc.getEspecialidad());
			bw.newLine();
			bw.append("E-mail: " + doc.getCorreo());
			bw.newLine();
			bw.append("Contraseña: " + doc.getContrasena());
			bw.newLine();
			bw.close();
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		System.out.print("Registros Guardados");
	}

	@Override
	public Doctor crearDoctor(Usuario usu) {
		boolean us = false,con = false;
		String nex = "nada";
		Doctor doc = new Doctor();

		try {
			File f = new File("F:\\HospitalSpring\\WebContent\\WEB-INF\\Usuarios.txt");
			FileReader fr = new FileReader(f);
			
			entrada = new Scanner(fr);
	
			doc.setNombre("Juan");
			
			String USR = "E-mail: "+usu.getUsuario();
			String CON = "Contraseña: "+usu.getContrasena();
			String bueno = "";
			
			while(entrada.hasNextLine()) {
				
				nex = entrada.nextLine(); 
				
				if(nex.isEmpty())
					nex = entrada.nextLine();
						
				
				if(nex.equals(USR)) {
					us = true;
				}
				
				if(nex.equals(CON)) {
					con = true;
				}
				
				String palabra[] = nex.split(" ");
				
				if(palabra[0].charAt(0) == 'N') {
			    	bueno = " ";
			    	for(int i = 1; i < palabra.length; i++) {
			    		bueno = bueno + palabra[i] + " ";
			    	}
			    }
	
				if(us == true && con == true) {
						doc.setNombre(bueno);
						return doc;
					}    
			}
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		return doc;
	}

}
