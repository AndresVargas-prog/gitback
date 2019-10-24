package efectivale.viajes.tarjeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.evsa.WebServiceRest.WebServiceREST;
import com.evsa.pedido.login.Bitacora;

import efectivale.viajes.monedero.Dispersion;

public class ActualizaEmpleados {
	private Logger Log = Logger.getLogger(Dispersion.class);
	private Bitacora bitacora = null;
	private Map<?, ?> post = null;
	private Connection conn = null;
	private Connection connpd = null;
	WebServiceREST webService = new WebServiceREST();
	String usuariosws;
	Double monederoSaldo;
	boolean busca = false;
	int opc = 1;
	String conexion = "";

	class Empleado {
		// Datos Obtenidos del Web Service
		private String tnombre = null;
		private String ttelefono = null;
		private String tcorreoP = null;
		private String templeadoId = null;
		private String ttarjeta = null;
		private String tcorreoant = null;

		private String nombre;
		private String telefono;
		private String correoP;
		public String tarjeta;
		public String empleadoId;
		private String correoant;
		private String correoe;
	}

	private Empleado empl = new Empleado();

	public void doProceso(Bitacora bitacora, Map<?, ?> post) {

		try {
			// Bitacora y post disponibles a la clase
			this.bitacora = bitacora;
			this.post = post;
			conn = bitacora.getConnection("dbviajes");
			connpd = bitacora.getConnection("pdviajes");
			conn.setAutoCommit(false);
			connpd.setAutoCommit(false);

			// Intercambio inicial
			if (bitacora.intercambioID == -1) {
				bitacora.bitacoraDialogo = getDialogoActualizacionMail();
				bitacora.intercambioID = 1;
				//System.out.println("Una Vez");
				return;
			}
			if (bitacora.intercambioID == 1) {
				System.out.println("Este es el post " + post.toString());
				if (post.containsKey("xoCancelar")) {
					bitacora.bitacoraNotificacion = "Se cancelo la Actualizaci&oacute;n del empleado";
					bitacora.intercambioID = 1;
					System.out.println("Cancelar");
					return;
				}
				if (post.containsKey("xoBuscar")) {
					System.out.println("Buscar de verdad");
					empl.templeadoId = ((String[]) post.get("empleadoid"))[0].trim();
					empl.ttarjeta = ((String[]) post.get("tarjeta"))[0].trim();
					empl.tcorreoP = ((String[]) post.get("correo"))[0].trim();
					//System.out.println("emp   "+empl.templeadoId+"  tar   "+empl.ttarjeta+"   corre   "+empl.tcorreoP);
					BuscaUsuario();
					bitacora.bitacoraDialogo = getDialogoActualizacionMail();
					bitacora.intercambioID = 1;
					return;
				}
				if (post.containsKey("xoConfirmar")) {
					System.out.println("se metio al confirmar");
					//// System.out.println("<script> </script>");
					if (Actualiza()) {
						bitacora.bitacoraNotificacion = "Actualizaci&oacute;n Realizada al empleado " + empl.nombre;
						bitacora.bitacoraDialogo = getDialogoActualizacionMail();
						bitacora.intercambioID = 1;
						conn.rollback();
						connpd.rollback();
						return;
					}
					//System.out.println("False en actualizar()");
					bitacora.bitacoraDialogo = getDialogoActualizacionMail();
					bitacora.intercambioID = 1;
					return;
				}
			}
		} catch (Exception ex) {
			Log.error("Se genero un error A la hora de Realizar La Actualizacion", ex);
			bitacora.bitacoraErrorCraso = "Se genero un error al procesar tu solicitud, por favor intentalo nuevamente";
			bitacora.intercambioID = -1;
		} finally {
			bitacora.closeConnection(conn);
			bitacora.closeConnection(connpd);
		}
	}

	private void BuscaUsuario() {
		//System.out.println("Realmente se metio a buscar");
		if (empl.ttarjeta != null && empl.ttarjeta.length() != 0) {
			if (!Bitacora.isDigitos(empl.templeadoId)) {
				bitacora.bitacoraErrorCraso = "El numero de Empleado debe ser un numero entero";
				return;
			}
			opc = 1;
			conexion = "select tbw.tuws2, tbw.tuws1,tbw.tuws2, tm.tnuec, lower(tbw.name), tm.ttele , lower(tm.tmail) from tablausrsws(?) as tbw join ttarj as t on(tbw.tuws1 = t.tnuta and t.tnuta = ?) "
					+ "join tmemp as tm on (tm.tidem = t.tidem) join tcuen as tc on (tc.tnucu = t.tnucu) order by tm.tmail;";
			//conexion = "select * from sp_tarjetasv(?,\'\',\'\')";
		}
		else if (empl.templeadoId != null && empl.templeadoId.length() != 0) {
			if (!Bitacora.isDigitos(empl.ttarjeta)) {
				bitacora.bitacoraErrorCraso = "El numero de la tarjeta debe ser un numero entero";
				return;
			}
			opc = 2;
			conexion = "select tbw.tuws2, tbw.tuws1,tbw.tuws2, tm.tnuec, lower(tbw.name), tm.ttele , lower(tm.tmail) from tablausrsws(?) as tbw join ttarj as t on(tbw.tuws1 = t.tnuta) "
					+ "join tmemp as tm on (tm.tidem = t.tidem and tm.tnuec = ?) join tcuen as tc on (tc.tnucu = t.tnucu) order by tm.tmail;";
			//conexion = "select * from sp_tarjetasv(\'\',?,\'\')";
		}
		else if (empl.tcorreoP.length() != 0 && empl.tcorreoP != null) {
			if (!isEmail(empl.tcorreoP)) {
				bitacora.bitacoraErrorCraso = " Error en correo no valido, Introduce un Correo Valido";
				return;
			}
			opc = 3;
			conexion = "select tbw.tuws2, tbw.tuws1,tbw.tuws2, tm.tnuec, lower(tbw.name), tm.ttele , lower(tm.tmail) from tablausrsws(?) as tbw join ttarj as t on(tbw.tuws1 = t.tnuta) "
					+ "join tmemp as tm on (tm.tidem = t.tidem and tm.tmail = upper(?)) join tcuen as tc on (tc.tnucu = t.tnucu) order by tm.tmail;";
			//conexion = "select * from sp_tarjetasv(\'\',\'\',?)";
		}
		else {
			opc = 4;
			conexion = "select tbw.tuws2, tbw.tuws1,tbw.tuws2, tm.tnuec, lower(tbw.name), tm.ttele , lower(tm.tmail) from tablausrsws(?) as tbw join ttarj as t on(tbw.tuws1 = t.tnuta) "
					+ "join tmemp as tm on (tm.tidem = t.tidem) join tcuen as tc on (tc.tnucu = t.tnucu) order by tm.tmail;";
			
			bitacora.bitacoraNotificacion = "Debes Ingresar Un Dato Para Poder Buscar";
			busca = false;
			return;
		}
		//System.out.println("Encontro algo");
		busca = true;
	}

	private boolean Actualiza() {
		try {
			if (!datosPost()) {
				System.out.println("Return en datospost");
				return false;
			}

			if (!UsuarioExiste()) {
				System.out.println("rollback en UsuarioExiste");
				conn.rollback();
				return false;
			}

			if (!doActualizacion()) {
				System.out.println("rollback en doActualizacion");
				conn.rollback();
				connpd.rollback();
				return false;
			}
/*
			if (!doActualizacionws()) {
				System.out.println("rollback en doActualizacionws");
				conn.rollback();
				connpd.rollback();
				return false;
			}
*/
			System.out.println("paso las validaciones");
		} catch (Exception e) {
			try {
				conn.rollback();
				connpd.rollback();
			} catch (Exception ex) {
				Log.error("Ocurrio Un Error en la Actualizaion del Empleado");
				ex.printStackTrace();
				return false;
			}
			Log.error("Ocurrio Un Error en la Actualizaion del Empleado");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean doActualizacionws() {
		String ClienteConsignatario = "" + webService.validaRegistro("" + bitacora.getClienteID(), 6, "0", "IZQ")
				+ webService.validaRegistro("" + bitacora.getConsignatarioID(), 5, "0", "IZQ");
		// System.out.println(ClienteConsignatario);
		String entity = "{" + "\"ClienteConsignatario\":\"" + ClienteConsignatario + "\"," + "\"ClaveEmpleado\":\""
				+ empl.empleadoId + "\"," + "\"Tarjeta\":\"" + empl.tarjeta + "\"," + "\"EmailNuevo\":\"" + empl.correoP
				+ "\"," + "\"TelefonoNuevo\":\"" + empl.telefono + "\"" + "}";

		String response = "";
		String codRespuesta = "";
		String Descripcion = "";

		//// System.out.println("Codifo Entity : " + entity);
		try {
			response = webService.llamadaREST("api", "ActualizarMail", entity, MediaType.APPLICATION_JSON_TYPE,
					bitacora);
			//// System.out.println(response);

		} catch (Exception e) {
			Log.error("Se Presento un error a la hora de llamar al Web Service.." + e.toString());
			e.printStackTrace();
			return false;
		}

		if (response.equals(""))
			return false;

		response = "[" + response + "]";
		try {

			JSONArray arrajson = new JSONArray(response);

			JSONObject object = arrajson.getJSONObject(0);

			codRespuesta = object.getString("CodRespuesta");
			Descripcion = object.getString("DescRespuesta");
		} catch (Exception e) {
			Log.error("Se Presento un error a la hora de llamar al Web Service.." + e.toString());
			e.printStackTrace();
			return false;
		}
		// se le quito la negacion para que nos arroje una confirmacion
		if (codRespuesta.equals("9999999")) {
			bitacora.bitacoraNotificacion = "No se Realizo La Actualizaci&oacute;n : \"" + Descripcion + "\"";
			return false;
		} else {
			bitacora.bitacoraNotificacion = "Actualizacion Exitosa";
			return true;
		}
	}

	private boolean doActualizacion() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows = 0;

		try {
			ps = conn.prepareStatement(
					"SELECT COUNT(*) FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = tbl_users.id_user WHERE "
							+ "tbl_users.email = lower(?) and tbl_cards.id_type = 1;");
			ps.setString(1, empl.correoant);
			rs = ps.executeQuery();

			if (rs.next())
				rows = rs.getInt("count");

			bitacora.closeConnection(ps,rs);
			/**
			 * ps = conn.prepareStatement("select * from tbl_users order by id_user desc;");
			 * rs = ps.executeQuery(); int id_user = rs.getInt("id_user") + 1; int empnum =
			 * Integer.parseInt(rs.getString("employee_number")); String email_Produc =
			 * rs.getString("email_product");
			 * 
			 * ps = conn.prepareStatement("insert into values (?,1,?,lower(?),lower(?),\'
			 * \', null,null, ?, ?)"); ps.setInt(1, id_user); ps.setInt(2, empnum);
			 * ps.setString(3, empl.correoP); ps.setString(4, empl.nombre); ps.setString(5,
			 * empl.telefono);
			 */
			if (rows > 1) {
				String query = "SELECT efectivale_backoffice_sp_reasign_card_vyg(" + "?, " /** No de Tarjeta */ // --
																												// empl.tarjeta
						+ "?, " /** Correo con la que esta asignada la tarjeta que se quiere mover */ // --
																										// empl.correoant
						+ "?, " /** Correo Nuevo al que se quiere asignar que no exista en tbl_users */ // --
																										// empl.correop
						+ "?, " /** Correo de la empresa a la que pertenece el usuario nuevo no la tarjeta */ // --
																												// empl.correoe
						+ "?, " /** Nombre del usuario nuevo */ // -- empl.nombre
						+ "''," /** Apellido ? */
						+ "?, " /** numero de empleado de usuario nuevo */ // -- empl.noempleado
						+ "'Admin'," /** -- posicion de usuario nuevo */ // -- default admin
						+ "?, " /** -- Numero de Telefono de usuario nuevo */ // -- empl.telefono
						+ "'CDMX',  " // -- default cdmx
						+ "''" // -- Zip Code
						+ ");";

				ps = conn.prepareStatement(query);

				ps.setString(1, empl.tarjeta);
				ps.setString(2, empl.correoant);
				ps.setString(3, empl.correoP);
				ps.setString(4, empl.correoe);
				ps.setString(5, empl.nombre);
				ps.setString(6, empl.empleadoId);
				ps.setString(7, empl.telefono);

				System.out.println("1 " + empl.tarjeta + " 2 " + empl.correoant + " 3 " + empl.correoP + " 4 "
						+ empl.correoe + " 5 " + empl.nombre + " 6 " + empl.empleadoId + " 7 " + empl.telefono);
				rs = ps.executeQuery();
				if (!rs.next()) {
					bitacora.bitacoraNotificacion = "Error al desvincular la tarjeta";
					return false;
				}

				ps = connpd.prepareStatement(
						"update tmemp set ttele = ?, tmail = upper(?) where concat(tnoem || ' ' || tappa || ' ' || tapma) = upper(?)");
				ps.setString(1, empl.telefono);
				ps.setString(2, empl.correoP);
				ps.setString(3, empl.nombre);

				if (ps.executeUpdate() == 1) {
					ps = conn.prepareStatement("update tbl_users set email = ?, phone = ? where employee_number = ?");
					ps.setString(1, empl.correoP);
					ps.setString(2, empl.telefono);
					ps.setString(3, empl.empleadoId);

					if (ps.executeUpdate() != 1) 
						return false;
					
				}

			} else if (rows == 1) {
				ps = connpd.prepareStatement(
						"update tmemp set ttele = ?, tmail = upper(?) where concat(tnoem || ' ' || tappa || ' ' || tapma) = upper(?)");
				ps.setString(1, empl.telefono);
				ps.setString(2, empl.correoP);
				ps.setString(3, empl.nombre);

				if (ps.executeUpdate() == 1) {
					ps = conn.prepareStatement("update tbl_users set email = ?, phone = ? where employee_number = ?");
					ps.setString(1, empl.correoP);
					ps.setString(2, empl.telefono);
					ps.setString(3, empl.empleadoId);

					if (ps.executeUpdate() != 1) 
						return false;
					
				}
			} else {
				bitacora.bitacoraNotificacion = "Error no encontro ningun resultado ";
				return false;
			}

		} catch (Exception e) {
			bitacora.bitacoraNotificacion = "Error -- > " + e.toString();
			Log.error("Ocurrio un Error al Actualizar en la Base de Datos \"DBViajes\"");
			e.printStackTrace();
			return false;
		} finally {
			bitacora.closeConnection(ps,rs);
		}
		//// System.out.println("Paso el metodo doActualizacion");
		return true;
	}

	private boolean UsuarioExiste() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connpd.prepareStatement("select * from tmemp where tnuec = ?;");
			ps.setString(1, empl.empleadoId);
			
			rs = ps.executeQuery();
			
			if(rs.next())
				empl.correoe = rs.getString("tmailc").trim();
			
			bitacora.closeConnection(ps,rs);

			ps = conn.prepareStatement("select * from tbl_users where employee_number = ?");
			ps.setString(1, empl.empleadoId);
			rs = ps.executeQuery();

			if (!rs.next()) {
				System.out.println("Creando Usuario en la base de datos");
				
				int id_us = 0;
				String query1 = "select max(id_user) as max from tbl_users as tu";
				String query2 = "select * from tbl_users as tu where id_user = ?;";
				String query3 = "insert into tbl_users values(?, 1,? , lower(?), lower(?), ' ', null,' ',?, ?, 'admin',' ',null,now(),true, now(), ' ', ' ');";

				ps = conn.prepareStatement(query1);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					id_us = Integer.parseInt(rs.getString("max"));
					
					ps = conn.prepareStatement(query2);
					ps.setInt(1, id_us);
					
					rs = ps.executeQuery();
					
					if(rs.next()) {
						
						id_us = Integer.parseInt(rs.getString("id_user")) + 1;
						
						ps = conn.prepareStatement(query3);
						ps.setInt(1, id_us);
						ps.setString(2, ""+empl.empleadoId);
						ps.setString(3, empl.correoP);
						ps.setString(4, empl.nombre);
						ps.setString(5, empl.telefono);
						ps.setString(6, empl.correoe);
						
						if(ps.executeUpdate() != 1) {
							bitacora.bitacoraNotificacion = "Error No se encontro Usuario en la base de datos y No se Pudo Crear";
							return false;
						}
					}else
						bitacora.bitacoraErrorCraso = "Error en consulta --> " + ps.toString();
				}else
					bitacora.bitacoraErrorCraso = "No se pudo obtener el numero maximo de usuario --> " + ps.toString();
			}
			return true;
		} catch (Exception e) {
			bitacora.bitacoraNotificacion = "Error --> " + e.toString();
			Log.error("Ocurrio un Problema a la hora de Buscar el usuario en la Base de Datos \"DBViajes\"");
			e.printStackTrace();
			return false;
		} finally {
			bitacora.closeConnection(ps, rs);
		}
	}

	private boolean datosPost() {
		if (post.containsKey("nombreid")) {// JAZMIN AGUILAR SARABIA
			empl.tnombre = ((String[]) post.get("nombreid"))[0].trim();
			System.out.println("nombre " + empl.tnombre);
		}
		if (post.containsKey("correopid")) { // jazmin@gmail.com
			empl.tcorreoP = ((String[]) post.get("correopid"))[0].trim();
			System.out.println("correo " + empl.tcorreoP);
		}
		if (post.containsKey("telefonoid")) { // 5581490002
			empl.ttelefono = ((String[]) post.get("telefonoid"))[0].trim();
			System.out.println("telefo " + empl.ttelefono);
		}
		if (post.containsKey("empleadoidid")) {// 1300112
			empl.templeadoId = ((String[]) post.get("empleadoidid"))[0].trim();
			System.out.println("id emp" + empl.templeadoId);
		}
		if (post.containsKey("tarjeta2id")) {// 5429170089217007
			empl.ttarjeta = ((String[]) post.get("tarjeta2id"))[0].trim();
			System.out.println("tar " + empl.ttarjeta);
		}
		if (post.containsKey("correoantid")) {// 5429170089217007
			empl.tcorreoant = ((String[]) post.get("correoantid"))[0].trim();
			System.out.println("correo ant " + empl.tcorreoant);
		}
		/*if (post.containsKey("correoeid")) {// 5429170089217007
			empl.tcorreoe = ((String[]) post.get("correoeid"))[0].trim();
			System.out.println("correo em " + empl.tcorreoe);
		}*/
		// validacion del nombre
		if (empl.tnombre.length() == 0 || empl.tnombre == null) {
			bitacora.bitacoraNotificacion = "El campo Nombre es obligatorio";
			return false;
		}
		// validacion del correo Personal
		if (empl.tcorreoP.length() == 0 || empl.tcorreoP == null) {
			bitacora.bitacoraNotificacion = "El campo correo es obligatorio";
			return false;
		} else {
			if (!isEmail(empl.tcorreoP)) {
				bitacora.bitacoraErrorCraso = " Error en correo no valido, Introduce un Correo Valido";
				return false;
			}
		}
		// validacion del correo Anterior
		if (empl.tcorreoant.length() == 0 || empl.tcorreoant == null) {
			bitacora.bitacoraNotificacion = "El campo correo es obligatorio";
			return false;
		} else {
			if (!isEmail(empl.tcorreoant)) {
				bitacora.bitacoraErrorCraso = " Error en correo no valido, Introduce un Correo Valido";
				return false;
			}
		}
		// validacion del correo de la empresa
		/*if (empl.tcorreoe.length() == 0 || empl.tcorreoe == null) {
			bitacora.bitacoraNotificacion = "El campo correo es obligatorio";
			return false;
		} else {
			if (!isEmail(empl.tcorreoe)) {
				bitacora.bitacoraErrorCraso = " Error en correo no valido, Introduce un Correo Valido";
				return false;
			}
		}*/
		// validacion del numero telefono
		if (empl.ttelefono.length() == 0 || empl.ttelefono.length() != 10) {
			bitacora.bitacoraErrorCraso = "Ingresa un Numero de Telefono Valido (Entero de 10 Digitos)";
			return false;
		}
		if (!Bitacora.isDigitos(empl.ttelefono)) {
			bitacora.bitacoraErrorCraso = "Ingresa un Numero de Telefono Valido (Entero de 10 Digitos)";
			return false;
		}
		// validacion del empleadoId
		if (empl.templeadoId.length() == 0 || empl.templeadoId == null) {
			bitacora.bitacoraErrorCraso = "El empleadoId debe de Ser Entero";
			return false;
		}
		// validacion de la tarjeta
		if (empl.ttarjeta.length() != 16 || empl.ttarjeta == null) {
			bitacora.bitacoraErrorCraso = "La tarjeta debe de ser de 16 digitos, ingrese una tarjeta valida";
			return false;
		} else {
			if (!Bitacora.isDigitos(empl.ttarjeta)) {
				bitacora.bitacoraErrorCraso = "La Tarjeta solo debe contener numeros (16)";
				return false;
			}
		}

		try {
			empl.nombre = empl.tnombre.trim();
			empl.correoP = empl.tcorreoP.trim();
			empl.telefono = empl.ttelefono.trim();
			empl.empleadoId = empl.templeadoId.trim();
			empl.tarjeta = empl.ttarjeta.trim();
			empl.correoant = empl.tcorreoant.trim();
			//empl.correoe = empl.tcorreoe.trim();

		} catch (Exception e) {
			Log.error("Ocurrio un error a la hora de actualizar los datos del empleado");
			e.printStackTrace();
			return false;
		}
		System.out.println("Paso el metodo datospost");
		return true;
	}

	private String getDialogoActualizacionMail() {
		// Primer dato a capturar
		// Construimos el detalle del dialogo
		PreparedStatement ps = null;
		String dialogo = "";
		bitacora.dialogoDatoEnfocado = "tarjetaID";

		try {
			// Preparamos conexion a Base de Datos
			String Base = "pdviajes";
			// Contamos el total de filas
			if (tablausrsws()) {

				if (!busca) {
					ps = connpd.prepareStatement("select tbw.tuws2, tbw.tuws1,tbw.tuws2, tm.tnuec, lower(tbw.name),tm.ttele , lower(tm.tmail) from tablausrsws(?) as tbw "
											 + "join ttarj as t on(tbw.tuws1 = t.tnuta) join tmemp as tm using(tidem) join tcuen as tc using (tnucu) order by tm.tmail;");
					ps.setString(1, usuariosws);
							System.out.println("Consulta " + ps.toString());
				} else {
					if (opc == 1) {
						ps = connpd.prepareStatement(conexion);
						ps.setString(1, usuariosws);
						ps.setString(2, empl.ttarjeta);
					} else if (opc == 2) {
						ps = connpd.prepareStatement(conexion);
						ps.setString(1, usuariosws);
						ps.setString(2, empl.templeadoId);
					} else if (opc == 3) {
						ps = connpd.prepareStatement(conexion);
						ps.setString(1, usuariosws);
						ps.setString(2, empl.tcorreoP);
					} else {
						ps = connpd.prepareStatement(conexion);
					}
					busca = false;
				}
			} else {
				bitacora.bitacoraNotificacion = "Error, No fue posible obtener Información de los Usuarios";
				return "";
			}

			// Datos dispersiÃ³n
			dialogo = "	<script type='text/javascript' src='js/jquery.min.js'></script>"
					+ "	<script type='text/javascript' src='js/jquery-1.11.1.min.js'></script>"
					+ "	<script type='text/javascript' src='js/bootstrap.min.js'></script>"
					+ "	<script type='text/javascript' src='js/ActEmpScript.js'></script>"
					+ "	<link type='text/css' href='resources/bootstrap.min.css' rel='stylesheet'>"

					+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"DT\"><tr><td>"
					+ "<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "<tr><li class=\"list-group-item list-group-item-info\">Busqueda de Usuario</li></tr>";

			dialogo += "<tr><td class=\"DFC11\">Tarjeta:</td>" + "<td class=\"DFC12\">"
					+ "<input name=\"tarjeta\" id=\"tarjeta\" type=\"text\" value=\"\" /></td></tr>"
					+ "<tr><td class=\"DFC11\">Empleado Id:</td>" + "<td class=\"DFC12\">"
					+ "<input name=\"empleadoid\" id=\"empleadoid\" type=\"text\" value=\"\" /></td></tr>"
					+ "<tr><td class=\"DFC11\">Correo :</td>" + "<td class=\"DFC12\">"
					+ "<input name=\"correo\" id=\"correo\" type=\"text\" value=\"\" /></td></tr>"
					+ "<tr><td colspan='2' align='center'>"
					+ "<button name=\"xoBuscar\" type=\"submit\" value=\"Buscar\" class=\"btn btn-success btn-sm\">Buscar</button>&nbsp;"
					+ "<button name=\"xoactualiza\"  type=\"button\" id=\"xoactualiza\" value=\"Actualizar\" class=\"btn btn-info btn-sm\">Actualizar</button>&nbsp;"
					+ "<button name=\"xoCancelar\" type=\"submit\" value=\"Cancelar\" class=\"btn btn-danger btn-sm\">Cancelar</button></td><tr>"
					+ "</table></td></tr>" + "</td></tr></table></td><tr>";

			dialogo += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"DT\"><tr><td>"
					+ "<BR><BR><BR><table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class='table table-sm'>"
					+ "<thead>" + "<li class=\"list-group-item list-group-item-info\">Lista de Usuarios</li>"
					+ "<th scope=\"col\" width='35px'>#</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='140px'>Tarjeta</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='50px'>Cuenta</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='50px'>Numero Empleado</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='150px'>Nombre Empleado</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='40px'>Telefono</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='40px'>Correo Personal</th>" + "<th scope=\"col\"></th>";
			
			String Consulta = ps.toString();
			dialogo += "<script src='js/getTabla.js'></script>" + "<script>" + "ini(\"" + Base + "\",\"" + Consulta
					+ "\",0,20,\"si\")" + "</script>";
			dialogo += "<tbody  id='xoTabla'> </tbody>";
			dialogo += "</table></td></tr><tr><td align=\"center\">"
					+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellp adding=\"0\">"
					+ "<tr><td align=\"left\">"
					+ "<td><input name=\"xoCancelar\" type=\"submit\" value=\"Cancelar\"  /></td>&nbsp;"
					+ "<td><button name=\"xoactualizar\" id='xoactualizar' type='button' value=\"Actualizar\"/>Actualizar</td>"
					+ "			<td cospan='2' align='center'><input type='text' id='xoMostrando' size='10' disabled/></td>"
					+ "			<td cospan='2' align='center'>  "
					+ "				Mostrar: <select id='xoMostrar' onchange='CambiarLimit()'>"
					+ "				<option value='20'>20" + "				<option value='30'>30"
					+ "				<option value='50'>50" + "			</select></td>"
					+ "			<td cospan='2' align='center'><input type='button' id='xoAnterior'  value='Anterior'  /></td>"
					+ "			<td cospan='2' align='center'><input type='button' id='xoSiguiente' value='Siguiente' /></td> "
					+ "</tr></table></td></tr></table>";

			dialogo += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellp adding=\"0\" hidden=\"hidden\"> "
					+ "		<tr>"
					+ "			<td><input name=\"nombreid\" type=\"text\" id='nombrehiid' hidden=\"hidden\" readonly/></td>"
					+ "			<td><input name=\"correoid\" type=\"text\" id='correophiid' hidden=\"hidden\" readonly/></td>"
					+ "			<td><input name=\"correoantid\" type=\"text\" id='correoanthiid' hidden=\"hidden\" readonly/></td>"
					+ "			<td><input name=\"correoeid\" type=\"text\" id='correoehiid' hidden=\"hidden\" readonly/></td>"
					+ "			<td><input name=\"telefonoidid\" type=\"text\" id='telefonohiid' hidden=\"hidden\" readonly/></td>"
					+ "			<td><input name=\"empleadoid2\" type=\"text\" id='empleadoid2' hidden=\"hidden\" readonly/></td>"
					+ "			<td><input name=\"tarjeta2\" type=\"text\" id='tarjeta2' hidden=\"hidden\" readonly/></td>"
					+ "		</tr>" + "</table>";
			/* Tabla */
			dialogo += ModalActualiza();

		} catch (Exception e) {
			Log.error("Se genero un error al mostrar el dialogo de Actualiza Usuarios", e);
		} finally {
			bitacora.closeConnection(ps);
		}
		return dialogo;
	}

	private boolean tablausrsws() {
		
		 ////System.out.println("Entro a la funcion"); 
		String Cliente = webService.validaRegistro("" + bitacora.getClienteID(), 6, "0", "IZQ");
		
		String Consignatario = webService.validaRegistro("" + bitacora.getConsignatarioID(), 5, "0", "IZQ"); 
		String ClienteConsig = Cliente + Consignatario; 
		
		String entity = "{ " 
							+ "\"IDSolicitud\" : '" + bitacora.getBitacoraID() + "' , " 
							+ "\"Cliente\" : '" + Cliente + "' , " 
							+ "\"Consignatario\" : '" + Consignatario + "'" 
						+ "}";
		  
		String response = ""; 
		String codRespuesta = ""; 
		String tarjetaid = "";
		String[] saldo = null;
		  
		try{ 
			response = webService.llamadaREST("api", "ConsultarSaldosClienteConsignatario", entity, MediaType.APPLICATION_JSON_TYPE, bitacora); 
		}catch(Exception e){
		  Log.error("Se Presento un error a la hora de llamar al Web Service.." + e.toString()); 
		  e.printStackTrace(); 
		  return false; 
		}
		  
		  if(response.equals("")) 
			  return false;
		  
		  response = "[" + response + "]";
		  
		  JSONArray arrajson;
		try {
			arrajson = new JSONArray(response);
		
		  
		  JSONObject object = arrajson.getJSONObject(0);
		  
		  codRespuesta = object.getString("CodRespuesta");
		  
		  if (!codRespuesta.equals("0")) 
			  return false;
		  
		  JSONArray jsonmoves = (JSONArray) object.getJSONArray("Movimientos");
		  
		  
		
		  if (jsonmoves.length() == 0) 
			  return false;
		  
		  usuariosws = "{"; 
		  try { 
				for (int i = 0; i < jsonmoves.length(); i++) {

					object = jsonmoves.getJSONObject(i);
					if (object.getString("Cuenta").equals(ClienteConsig))
						if (!object.getString("Saldo").isEmpty()) {
							monederoSaldo = Double.parseDouble(object.getString("Saldo"));
						} else {
							//// System.out.println("false aqui monedero empty");
							return false;
						}

					String val = object.getString("Status");

					if (!val.isEmpty()) {

						if (!val.equals("2") && !val.equals("0") && !val.equals("8"))
							continue;

					} else {
						continue;
					}

					if (object.getString("Tarjeta").isEmpty() || object.getString("Cuenta").isEmpty()
							|| object.getString("NombreEmbozado").isEmpty() || object.getString("Saldo").isEmpty())
						continue;

					tarjetaid = object.getString("Tarjeta").substring(8, 16);
					String cuenta = object.getString("Tarjeta").substring(8, 13);
					saldo = object.getString("Saldo").split("\\.");

					if (!usuariosws.equals("{"))
						usuariosws += ",";

					usuariosws += "{" + object.getString("Tarjeta") + "," + cuenta + ","
							+ object.getString("NombreEmbozado") + "," + tarjetaid + "," + saldo[0] + "."
							+ saldo[1].substring(0, 2) + ",\'\'" + "}";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			usuariosws += "}";
		} catch (Exception e) {
			Log.error("Se genero un error al obtener información del WS ", e);
			e.printStackTrace();
			return false;
		}
		 return true;
		
	}

	private String ModalActualiza() {
		String form = "";
		/* Construccion Modal */
		/* Modal Actualizar */
		form += "<div class='modal fade' id='ModalActualizaMail' tabindex='-1' role='dialog' aria-labelledby='memberModalLabel' aria-hidden='true'>"
				+ " <div class='modal-dialog'>" + "  <div class='modal-content'>" + "     <div class='modal-header'>"
				+ "      <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
				+ "      <h3 class='pull-left no-margin'>Actualizaci&oacute;n de los datos del Usuario</h3>"
				+ "      </div>" 
				+ " <div class='modal-body'>" 
				+ "	<div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Nombre De Empleado..&nbsp;&nbsp;</label></h5>"
				+ "		<div class='col-sm-8'> " 
				+ "			<input name=\"nombreid\" id='nombreid' value= " + empl.tnombre + "  class='form-control' type=\"text\" readonly>" 
				+ " 	</div> " 
				+ "	</div> "
				+ "	<br><div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Correo Personal</label></h5>"
				+ "		<div class='col-sm-8'> " 
				+ "			<input name=\"correopid\" id='correopid' value=" + empl.tcorreoP + " class='form-control' type=\"text\">" 
				+ "		</div> " 
				+ "	</div> "
				+ "	<br><div class='row'> " 
				+ "		<h5><label class='col-sm-4 control-label'> Telefono </label></h5>"
				+ "		<div class='col-sm-8'> " 
				+ "			<input name=\"telefonoid\" id='telefonoid' value=" + empl.ttelefono + " class='form-control' type=\"text\">" 
				+ "		</div> " 
				+ "	</div> "
//				+ "	<br><div class='row'> " 
//				+ "		<h5><label class='col-sm-4 control-label'> Email Empresa </label></h5>"
//				+ "		<div class='col-sm-8'> " 
//				+ "			<input name=\"correoeid\" id='correoeid' value=" + empl.tcorreoe + " class='form-control' type=\"text\">" 
//				+ "		</div> " 
//				+ "	</div> "
				+ "	<br><div class='row'> " 
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"empleadoidid\" id='empleadoidid' value=" + empl.templeadoId + " class='form-control' type=\"hidden\" readonly>"
				+ "			<input name=\"correoantid\" id='correoantid' value=" + empl.tcorreoant + " class='form-control' type=\"hidden\" readonly>"
				+ "			<input name=\"tarjeta2id\" id='tarjeta2id' value=" + empl.ttarjeta + " class='form-control' type=\"hidden\" readonly>" 
				+ "		</div> " 
				+ "	</div> " 
				+ "  <h4> <h4>"
				+ "     </div>"
				+ "   	<div class='modal-footer'>"
				+ "	<button name=\"xoConfirmar\" id = 'xoConfirmar' type=\"submit\" class='btn btn-group btn-success'><span>Confirmar</span></button>"
				+ "	<button id='CancelDispersion' type='button' class='btn btn-group btn-danger' data-dismiss='modal' title='Cancelar'> <span> Cancelar </span></button>"
				+ "	</div>" 
				+ "   </div>" 
				+ "  </div>" 
				+ " </div>" 
				+ " </div>";
		/* Fin JS MODAL CONFIMAR */
		return form;
	}

	private static boolean isEmail(String email) {

		Pattern patronEmail = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");

		Matcher mEmail = patronEmail.matcher(email.toLowerCase());
		if (mEmail.matches()) {
			return true;
		}
		return false;
	}
}
