package dao;

import java.util.List;

public interface IhospitalDao {

	public void insert(Doctor doctor);
	public void update(Doctor doctor);
	public void delete(String nombre);
	public Doctor EncontrarDoctor(String nombre);
	public List<Doctor> read(); 
	public String login(String correo, String contra);
	public Doctor consulta(String nombre);
}
