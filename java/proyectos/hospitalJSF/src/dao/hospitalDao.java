package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class hospitalDao implements IhospitalDao{
	
	private DataSource ds;

	public hospitalDao() {
		ds = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		}
		catch(NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Doctor> read() {
		List<Doctor> list = null;
		Connection con = null;
		try {
			con = ds.getConnection();
			
			if(con == null)
				throw new SQLException("Can't get database Connection");
			
			PreparedStatement ps = con.prepareStatement("select email, first_name, last_name, password,phone,fax from Usuario");
			
			ResultSet rs = ps.executeQuery();
			list = new ArrayList<Doctor>();
			
			while(rs.next()) {
				Doctor doc = new Doctor();
				
				doc.setNombre(rs.getString("Nombre"));
				doc.setEdad(rs.getInt("edad"));
				doc.setSexo(rs.getString("sexo"));
				doc.setDireccion(rs.getString("Direccion"));
				doc.setEspecialidad(rs.getString("Especialidad"));
				doc.setCorreo(rs.getString("Correo"));
				doc.setContrasena(rs.getString("contraseña"));
				
				list.add(doc);
			}
			return list;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public void insert(Doctor doctor) {
		
		String sql = "INSERT INTO DOCTORES (NOMBRE, EDAD, SEXO, DIRECCION, ESPECIALIDAD, CORREO,"
				+ " CONTRASEÑA) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, doctor.getNombre());
			ps.setInt(2, doctor.getEdad());
			ps.setString(3, doctor.getSexo());
			ps.setString(4, doctor.getDireccion());
			ps.setString(5, doctor.getEspecialidad());
			ps.setString(6, doctor.getCorreo());
			ps.setString(7, doctor.getContrasena());
			ps.executeUpdate();
			ps.close();
			System.out.print("Registro Guardado");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}

	@Override
	public void update(Doctor doctor) {
		String sqlquery = "update DOCTORES set NOMBRE = ?, EDAD = ?, "
				+ "SEXO = ?, DIRECCION = ?, ESPECIALIDAD = ? WHERE CORREO = ?";
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sqlquery);
			ps.setString(1, doctor.getNombre());
			ps.setInt(2, doctor.getEdad());
			ps.setString(3, doctor.getSexo());
			ps.setString(4, doctor.getDireccion());
			ps.setString(5, doctor.getEspecialidad());
			ps.setString(6, doctor.getCorreo());
			ps.executeUpdate();
			conn.close();
			System.out.print("Registro Modificado");
		}
		catch(SQLException sq) {
			sq.printStackTrace();
		}
	}

	@Override
	public void delete(String correo) {
		String sqlquery = "DELETE FROM DOCTORES WHERE CORREO = ?";
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sqlquery);
			ps.setString(1, correo);
			ps.executeQuery();
			ps.close();
			System.out.print("Registro borrado");
		}
		catch(SQLException sq) {
			sq.printStackTrace();
		}
	}

	@Override
	public Doctor EncontrarDoctor(String nombre) {
		String sql = "SELECT * FROM DOCTORES WHERE NOMBRE = ?";
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nombre);
			Doctor doc = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				doc = new Doctor(rs.getString("NOMBRE"), rs.getInt("EDAD"), rs.getString("SEXO"),
						rs.getString("DIRECCION"),rs.getString("ESPECIALIDAD"),rs.getString("CORREO")
						,rs.getString("CONTRASEÑA"));
			}
			rs.close();
			ps.close();
			return doc;
		} 
		catch (SQLException e) {
			throw new RuntimeException(e);
		} 
		finally {
			if (conn != null) {
				try {
					conn.close();
				} 
				catch (SQLException e) {}
			}
		}
	}

	@Override
	public String login(String correo, String contra) {
		String sql = "SELECT Nombre FROM DOCTORES WHERE CORREO = ? and CONTRASEÑA = ?";
		Connection conn = null;
		String nom = "";

		try {
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, correo);
			ps.setString(2, contra);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nom = rs.getString("NOMBRE");
			}
			rs.close();
			ps.close();
			return nom;
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if (conn != null) {
				try {
					conn.close();
				} 
				catch (SQLException e) {}
			}
		}
		return nom;
	}
	
	public Doctor consulta(String nombre) {
		String sql = "SELECT edad,sexo,direccion,especialidad,correo,contraseña FROM DOCTORES WHERE NOMBRE = ?";
		Connection conn = null;
		Doctor doc = null;
		
		try {
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
				
			if (rs.next()) {
					doc = new Doctor(nombre , rs.getInt("EDAD"), rs.getString("SEXO"), rs.getString("DIRECCION"),
							rs.getString("ESPECIALIDAD"), rs.getString("CORREO"), rs.getString("CONTRASEÑA"));
			}
			rs.close();
			ps.close();
			return doc;
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if (conn != null) {
				try {
					conn.close();
				} 
				catch (SQLException e) {}
			}
		}
		return doc;
	}
}
