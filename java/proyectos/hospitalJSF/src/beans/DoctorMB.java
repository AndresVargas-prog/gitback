package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import dao.Doctor;
import dao.IhospitalDao;
import dao.hospitalDao;

@ManagedBean( name = "doc")
public class DoctorMB {
	
	public List<Doctor> getUsuariosList(){
		IhospitalDao hospitaldao = new hospitalDao();
		return hospitaldao.read();
	}
}