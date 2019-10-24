public boolean tablatempws(){
    String ClienteConsignatario = "" + webService.validaRegistro(""+ bitacora.getClienteID(), 6, "0", "IZQ") 
                                     + webService.validaRegistro(""+ bitacora.getConsignatarioID(), 5, "0", "IZQ");
//      -------------------------------------------------------------------------------------------
                        /** Este String Es para cuando le den al boton de Modificar **/
    String entity = "{          
                         \"ClienteConsignatario\" : \" '"+ ClienteConsignatario +"'\",
                         \"ClaveEmpleado\" : \" '"+ empleadoId +"'\" ,
                         \"Tarjeta\" : \" '"+ Tarjeta +"'\",
                         \"EmailNuevo\" : \" '"+ emailNuevoPer +"'\",
                         \"TelefonoNuevo\" : \" '"+ TelNuevo +"'\"
                    }";
    String respuesta = "", codRespuesta = "", descripcion = "";

    try{
        response = webService.llamadaREST("api", "ActualizaMail", entity, MediaType.APPLICATION_JSON_TYPE, bitacora);
    }catch(Exception e){
        log.error("Se Presento un error a la hora de llamar al Web Service.." + e.toString());
        e.printTrack();
        return false;
    }

    if(response.equals(""))
        return false;
//      -------------------------------------------------------------------------------------------
    String entity = "{ 
                        \"IDSolicitud\" : '"+ bitacora.getBitacoraID() +"',
                        \"Cliente\" : '"+ cliente +"',
                        \"Consignatario\" : '"+ Consignatario +"'
                    }";

    String respuesta = "";
    String tarjetaid = "";
    String cuenta = "";
    String[] saldo = null;
    String nombreEmbosado = ""; // posible nombre del empleado // 

    response = "[" + response + "]";

    JSONArray arrayjson = new JSONArray(response);

    JSONObject object = new JSONObject(0);

    codRespuesta = object.getString("CodRespuesta");

    if(!codRespuesta.equals("0"))
        return false;

    JSONArray jsonmoves = (JSONArray) object.getString("Movimientos");

    if(jsonmoves == 0)
        return false;
    
    usuariosws = "{";
    try{
        for(int i = 0; i < jsonmoves.length(); i++){
            object = jsonmoves.getJSONObjetct(i);
            if(object.getString("Cuenta").equals(ClienteConsignatario)){
                if(!object.getString("Saldo").isEmpty())
                    monederoSaldo = Double.parseDouble(object.getString("Saldo"));
                else
                    return false;
            }
            String val = object.getString("Status");

            if(!val.isEmpty()){
                if(!val.equals("2") && !val.equals("0") && !val.equals("7") && !val.equals("8"))
                continue;
            }else{
                continue;
                }
            if(objeto.getString("Tarjeta").isEmpty() || objeto.getString("Cuenta").isEmpty()
				|| objeto.getString("NombreEmbozado").isEmpty()	|| objeto.getString("Saldo").isEmpty())
                    continue;
            
            cuenta = object.getString("Tarjeta").subString(6,13);
            tarjetaid = object.getString("Tarjeta").subString(8,16);
            saldo = object.getString("Saldo").split("\\.");

            if(!usuariosws.equals("{"))
                usuariosws += ",";
            
            usuariosws += "{" + object.getString("Tarjeta") + "," + Integer.parseInt(object.getString("Cuenta"))
                        + "," + object.getString("NombreEmbosado") + "," + tarjetaid + "," + saldo[0] + "." 
                        + saldo[1].subString(0,2) + ",''" + "}";
        }
    }catch(Exception e){
        e.printTrack();
        return false;
    }
    usuariosws += "}";
    return true; 
}

public void modalActualiza(){
String form = "";
//		/* Construccion Modal */
		/* Modal Actualizar */
		form += "<div class='modal fade' id='ModalActualizaMail' tabindex='-1' role='dialog' aria-labelledby='memberModalLabel' aria-hidden='true'>"
				+ " <div class='modal-dialog'>" 
				+ "  <div class='modal-content'>" 
				+ "     <div class='modal-header'>"
				+ "      <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
				+ "      <h3 class='pull-left no-margin'>Confirmaci&oacute;n  de Dispersi&oacute;n Tarjeta</h3>" + "      </div>"
				+ " <div class='modal-body'>"
				+ "	<div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Dispersi&oacute;n a la tarjeta..&nbsp;&nbsp;</label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"tarjetaid\" id='tarjetaid' value="+tar.xtarjeta+"  class='form-control' type=\"text\" readonly>"
				+ "		</div> " 
				+ "	</div> " 
				+ "	<br><div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Empleado&nbsp;&nbsp;</label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"empleadoid\" id='empleadoid' value="+tar.xempleado+" class='form-control' type=\"text\" readonly>"
				+ "		</div> " 
				+ "	</div> " 
				+ "	<br><div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Monto de dispersi&oacute;n: </label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"montoDispersionid\" id='montoDispersionid' value="+tar.xmontoDispersion+" class='form-control' type=\"text\" readonly>"
				+ "		</div> " 
				+ "	</div> "
				+ "	<br><div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Comentarios: </label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"conceptoid\" id='conceptoid' value='"+tar.xconcepto+"' class='form-control' type=\"text\" readonly>"
				+ "		</div> " 
				+ "	</div> "
				+ "  <h4>.<h4>"
				+ "     </div>" 
				+ "   	<div class='modal-footer'>"
				+ "   		<button name='xoConfirmar' type=\"submit\" class='btn btn-group btn-success'><span>Confirmar</span></button>"
				+ " 		<button id='CancelDispersion' type='button' class='btn btn-group btn-danger' data-dismiss='modal' title='Cancelar'> <span> Cancelar </span></button>"
				+ "  	</div>" 
				+ "   </div>" 
				+ "  </div>" 
				+ " </div>" 
				+ " </div>";
		/* Fin JS MODAL CONFIMAR */		
		return form;
}

/*
    Datos que entrega el web service en la consulta de tarjetas
    IDSolicitud 
    CodRespuesta
    DescRespuesta
    Tarjeta
    Cuenta                      Tendre que sacar el nombre de la base de datos
    FechaVigencia
    Status
    DescripcionStatus
    Saldo 

    Ejemplo para la actualiacion del correo
    {
        "ClienteConsignatario":"00000100020",
        "ClaveEmpleado":"1018",
        "Tarjeta":"5429170081656004",
        "EmailNuevo":"nuevomail@gmail.com",
        "TelefonoNuevo":"7224032999"
    }

*/

/* Clase para edicion del correo
    package efectivale.viajes.tarjeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.evsa.WebServiceRest.WebServiceREST;
import com.evsa.pedido.login.Bitacora;

import efectivale.viajes.monedero.Dispersion;

public class ActualizaEmpleados {
	private Logger Log = Logger.getLogger(Dispersion.class);
	private Bitacora bitacora = null;
	private Map<?, ?> post = null;
	private Connection conn = null;
	WebServiceREST webService = new WebServiceREST();
	String usuariosws;
	boolean busca = false;
	int opc = 0;
	private String conexion = "";
	
	class Empleado {
		// Datos Obtenidos del Web Service
		private String tnombre = null;
		private String ttelefono = null;
		private String tcorreoP = null;
		private String empleadoId = null;
		private String ttarjeta = null;

		private String nombre;
		private int telefono;
		private String correoP;
	}

	private Empleado empl = new Empleado();

	public void doProceso(Bitacora bitacora, Map<?, ?> post) {

		try {
			// Bitacora y post disponibles a la clase
			this.bitacora = bitacora;
			this.post = post;
			conn = bitacora.getConnection("dbviajes");
			conn.setAutoCommit(false);

			// Intercambio inicial
			if (bitacora.intercambioID == -1) {
				bitacora.bitacoraDialogo = getDialogoActualizacionMail();
				bitacora.intercambioID = 1;
				return;
			}
			if (bitacora.intercambioID == 1) {
				if (post.containsKey("xoCancelar")) {
					bitacora.bitacoraNotificacion = "Se cancelo la Actualizaci&oacute;n del empleado";
					bitacora.intercambioID = 1;
					return;
				}
				if(post.containsKey("xoBuscar")) {
					conexion = "";
					empl.empleadoId = ((String[]) post.get("nombre"))[0].trim();
					empl.ttarjeta = ((String[]) post.get("tarjeta"))[0].trim();
					
					if(empl.empleadoId != null && empl.empleadoId.length() != 0) {
						opc = 1;
						conexion = "select tbw.tuws2, tbw.tuws1,tbw.tuws2, tbu.employee_number, tbw.name, tbu.phone, tbu.email, tbu.email_product " + 
								"from tablausrsws(?) as tbw join tarjeta as tt on (tt.tnuta = tbw.tuws1) " + 
								"join tbl_users tbu on (tbu.employee_number = tt.empleadoid and tt.empleadoid = ?)";
						}
					
					if(empl.ttarjeta != null && empl.ttarjeta.length() != 0) {
						opc = 2;
						conexion = "select tbw.tuws2, tbw.tuws1,tbw.tuws2, tbu.employee_number, tbw.name, tbu.phone, tbu.email, tbu.email_product " + 
								"from tablausrsws(?) as tbw join tarjeta as tt on (tt.tnuta = tbw.tuws1 and tbw.tuws1 = ?) " + 
								"join tbl_users tbu on (tbu.employee_number = tt.empleadoid)";
						}
					
					if (!Bitacora.isDigitos(empl.ttarjeta)) {
						bitacora.bitacoraErrorCraso = "El numero de la tarjeta debe ser un numero entero";
						return;
					}
					if (!Bitacora.isDigitos(empl.empleadoId)) {
						bitacora.bitacoraErrorCraso = "El numero de la cuenta debe ser un numero entero";
						return;
					}
					busca = true;
					bitacora.bitacoraDialogo = getDialogoActualizacionMail();
					bitacora.intercambioID = 1;
					return;
				}
				if (post.containsKey("xoConfirmar")) {
					if (Actualiza()) {
						conn.rollback();
						bitacora.bitacoraNotificacion = "Actualizaci&oacute;n Realizada al empleado" + empl.nombre;
						bitacora.bitacoraDialogo = getDialogoActualizacionMail();
						bitacora.intercambioID = 1;
						return;
					}
					bitacora.bitacoraDialogo = getDialogoActualizacionMail();
					bitacora.intercambioID = 1;
				}
			}

		} catch (Exception ex) {
			Log.error("Se genero un error A la hora de Realizar La Actualizacion", ex);
			bitacora.bitacoraErrorCraso = "Se genero un error al procesar tu solicitud, por favor intentalo nuevamente";
			bitacora.intercambioID = -1;
		} finally {
			bitacora.closeConnection(conn);
		}

	}

	private boolean BuscaUsuario() {
		
		return false;
	}

	private boolean Actualiza() {
		try {
			if(!datosPost()) {
				return false;
			}
			
			if(!UsuarioExiste()) {
				conn.rollback();				
			}
			
			if(!doActualizacion()) {
				
			}
			if(!doActualizacionws()) {
				
			}
		}catch(Exception e) {
			try {
				conn.rollback();
			}catch(Exception ex){
				ex.printStackTrace();
				Log.error("Ocurrio Un Error en la Actualizaion del Empleado");
				return false;
			}
		}
		return true;
	}
	
	private boolean doActualizacionws() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean doActualizacion() {
		
		return false;
	}

	private boolean UsuarioExiste() {
			
		return true;
	}

	private boolean datosPost() {
		if(post.containsKey("nombrehiid")) {
			empl.tnombre = ((String[]) post.get("nombrehiid"))[0].trim();
		}
		if(post.containsKey("correophiid")) {
			empl.tcorreoP = ((String[]) post.get("correophiid"))[0].trim();
		}
		if(post.containsKey("telefonohiid")) {
			empl.ttelefono = ((String[]) post.get("telefonohiid"))[0].trim();
		}
		
		if(empl.tnombre.length() == 0|| empl.tnombre == null) {
			bitacora.bitacoraErrorCraso = "El campo Nombre es obligatorio";
			return false;
		}
		if(empl.tcorreoP.length() == 0 || empl.tcorreoP == null) {
			bitacora.bitacoraErrorCraso = "El campo correo es obligatorio";
			return false;
		}else {
			if(!isEmail(empl.tcorreoP)) {
				bitacora.bitacoraErrorCraso = "Introduce un Correo Valido";
				return false;
			}
		}
		if(empl.ttelefono.length() == 0 || empl.ttelefono.length() != 10) {
			bitacora.bitacoraErrorCraso = "Ingresa un Numero de Telefono Valido (10 Digitos)";
			return false;
		}
		
		try {
			empl.nombre = empl.tnombre.trim();
			empl.correoP = empl.tcorreoP.trim();
			 bitacora.isDigitos(empl.ttelefono);
		}catch(Exception e){
			Log.error("Ocurrio un error a la hora de actualizar los datos del empleado");
			e.printStackTrace();
		}
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
			String Base = "dbviajes";
			// Contamos el total de filas
			if (tablausrsws()) {
					ps = conn.prepareStatement("select tbw.tuws2, tbw.tuws1,tbw.tuws2, tbu.employee_number, tbw.name, tbu.phone, tbu.email, tbu.email_product"
							+ "from tablausrsws(?) as tbw join tarjeta as tt on (tt.tnuta = tbw.tuws1 and tt.cuentaid = cast(tbw.tuws2 as int8) and tt.tarjetaid = cast(tbw.tuws4 as int8))"
							+ " join tbl_users as tbu on (tbu.employee_number = tt.empleadoid)");
					ps.setString(1, usuariosws);
				/*ps = conn.prepareStatement("select tbw.tuws2, tbw.tuws1,tbw.tuws2, tbu.employee_number, tbw.name, tbu.phone, tbu.email, tbu.email_product from tablausrsws(?) as tbw join tbl_users tbu using(name)");
					else {
						if(opc == 1) {
							ps = null;
							ps = conn.prepareStatement(conexion);
							ps.setString(1, usuariosws);
							ps.setString(2, empl.ttarjeta);
						}else {
							ps = conn.prepareStatement(conexion);
							ps.setString(1, usuariosws);
							ps.setString(2, empl.empleadoId);
						}
						busca = false;
					}*/
			} else {
				bitacora.bitacoraNotificacion = "Error, No fue posible obtener Información de los Usuarios";
				return "";
			}
			// Datos dispersiÃ³n
			dialogo = "	<script type='text/javascript' src='js/jquery.min.js'></script>"
					+ "	<script type='text/javascript' src='js/jquery-1.11.1.min.js'></script>"
					+ "	<script type='text/javascript' src='js/bootstrap.min.js'></script>"
					+ "	<script type='text/javascript' src='js/ActEmpscript.js'></script>"
					+ "	<link type='text/css' href='resources/bootstrap.min.css' rel='stylesheet'>"

					+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"DT\"><tr><td>"
					+ "<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
					+ "<tr><li class=\"list-group-item list-group-item-info\">Busqueda de Tarjeta</li></tr>";

			dialogo += "<tr><td class=\"DFC11\">Tarjeta:</td>" + "<td class=\"DFC12\">"
					+ "<input name=\"tarjeta\" id=\"tarjeta\" type=\"text\" value=\"\" /></td></tr>"
					+ "<tr><td class=\"DFC11\">Empleado Id:</td>" + "<td class=\"DFC12\">"
					+ "<input name=\"empleadoid\" id=\"empleadoid\" type=\"text\" value=\"\" /></td></tr>"
					+ "<tr><td colspan='2' align='center'>"
					+ "<imput name=\"xoBuscar\" type=\"submit\" value=\"Buscar\" accesskey=\"A\" class=\"btn btn-success btn-sm\" />Buscar</button>&nbsp;"
					+ "<button name=\"xoactualiza\"  type=\"button\" onclick=\"Modelmenu();\" value=\"Actualizar\" accesskey=\"S\" class=\"btn btn-info btn-sm\" />Actualizar</button>&nbsp;"
					+ "<button name=\"xoCancelar\" type=\"submit\" value=\"Cancelar\" accesskey=\"C\" class=\"btn btn-danger btn-sm\" />Cancelar</button></td><tr>"
					+ "</table></td></tr>" + "</td></tr></table></td><tr>";

			dialogo += "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"DT\"><tr><td>"
					+ "<BR><BR><BR><table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class='table table-sm'>"
					+ "<thead>" + "<li class=\"list-group-item list-group-item-info\">Lista de Usuarios</li>"
					+ "<th scope=\"col\" width='35px'>#</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='150px'>Tarjeta</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='80px'>Cuenta</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='115px' >Empleado Id</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='250px'>Nombre Empleado</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='50px'>Telefono</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='115px'>Correo Personal</th>" + "<th scope=\"col\"></th>"
					+ "<th scope=\"col\" width='115px'>Correo Empresa</th>" + "<th scope=\"col\"></th>";

			String Consulta = ps.toString();
			dialogo += "<script src='js/getTabla.js'></script>" + "<script>" + "ini(\"" + Base + "\",\"" + Consulta
					+ "\",0,20,\"si\")" + "</script>";
			dialogo += "<tbody  id='xoTabla'> </tbody>";
			dialogo += "</table></td></tr><tr><td align=\"center\">"
					+ "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellp adding=\"0\">"
					+ "<tr><td align=\"left\">"
					+ "<td><input name=\"xoCancelar\" type=\"submit\" value=\"Cancelar\"  /></td>&nbsp;"
					+ "<td><button name=\"xoactualiza\" id='xoactualiza' type='button' value=\"Actualizar\"/>Actualizar</td>"
					+ "			<td cospan='2' align='center'><input type='text' id='xoMostrando' size='10' disabled/></td>"
					+ "			<td cospan='2' align='center'>  "
					+ "				Mostrar: <select id='xoMostrar' onchange='CambiarLimit()'>"
					+ "				<option value='20'>20" + "				<option value='30'>30"
					+ "				<option value='50'>50" + "			</select></td>"
					+ "			<td cospan='2' align='center'><input type='button' id='xoAnterior'  value='Anterior'  /></td>"
					+ "			<td cospan='2' align='center'><input type='button' id='xoSiguiente' value='Siguiente' /></td> "
					+ "</tr></table></td></tr></table>";
			
			dialogo += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellp adding=\"0\" > "
					+ "		<tr>"
					+ "			<td><input name=\"nombreid\" type=\"text\" id='nombrehiid' readonly/></td>"
					+ "			<td><input name=\"correoid\" type=\"text\" id='correophiid' readonly/></td>"
					+ "			<td><input name=\"telefonoid\" type=\"text\" id='telefonohiid' readonly/></td>"
					+ "		</tr>"
					+ "</table>";
			/* Tabla */
			dialogo += ModalActualiza();

			ps.close();
			ps = null;
		} catch (Exception e) {
			Log.error("Se genero un error en mostrar dialogo inicial dispersión desde el monedero Viajes", e);
		} finally {
			bitacora.closeConnection(conn);
			bitacora.closeConnection(ps);
		}
		return dialogo;
	}

	private boolean tablausrsws() throws JSONException {
		System.out.println("Entro a la funcion");
		String Cliente = webService.validaRegistro("" + bitacora.getClienteID(), 6, "0", "IZQ");
		String Consignatario = webService.validaRegistro("" + bitacora.getConsignatarioID(), 5, "0", "IZQ");
		String ClienteConsig = Cliente + Consignatario;
		String entity = "{ " + "\"IDSolicitud\" : '" + bitacora.getBitacoraID() + "' , " + "\"Cliente\" : '" + Cliente
				+ "' , " + "\"Consignatario\" : '" + Consignatario + "'" + "}";

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
		
		JSONArray arrajson = new JSONArray(response);

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
				if (object.getString("Cuenta").equals(ClienteConsig)) {
					if (!object.getString("Saldo").isEmpty()) {
						double monederoSaldo = Double.parseDouble(object.getString("Saldo"));
					}else{System.out.println("false aqui monedero empty");
						return false;}
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
				String cuenta = object.getString("Tarjeta").substring(8,13);
				saldo = object.getString("Saldo").split("\\.");
				
				if (!usuariosws.equals("{"))
					usuariosws += ",";
					
				usuariosws += "{" + object.getString("Tarjeta") + "," + cuenta
					+ "," + object.getString("NombreEmbozado") + "," + tarjetaid + "," + saldo[0] + "."
					+ saldo[1].substring(0, 2) + ",\'\'" + "}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		usuariosws += "}";
		return true;
	}

	private String ModalActualiza() {
		String form = "";
		/* Construccion Modal */
		/* Modal Actualizar */
		form += "<div class='modal fade' id='ModalActualizaMail' tabindex='-1' role='dialog' aria-labelledby='memberModalLabel' aria-hidden='true'>"
				+ " <div class='modal-dialog'>" 
				+ "  <div class='modal-content'>" 
				+ "     <div class='modal-header'>"
				+ "      <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"
				+ "      <h3 class='pull-left no-margin'>Actualizaci&oacute;n de los dats del Usuario</h3>" + "      </div>"
				+ " <div class='modal-body'>"
				+ "	<div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Nombre De Empleado..&nbsp;&nbsp;</label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"nombre\" id='nombreid' value="+empl.nombre+"  class='form-control' type=\"text\" readonly>"
				+ "		</div> " 
				+ "	</div> " 
				+ "	<br><div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Correo Personal</label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"correop\" id='correopid' value="+empl.tcorreoP+" class='form-control' type=\"text\">"
				+ "		</div> " 
				+ "	</div> " 
				+ "	<br><div class='row'> "
				+ "		<h5><label class='col-sm-4 control-label'> Telefono </label></h5>"
				+ "		<div class='col-sm-8'> "
				+ "			<input name=\"telefono\" id='telefonoid' value="+empl.telefono+" class='form-control' type=\"text\">"
				+ "		</div> " 
				+ "	</div> "
				+ "  <h4> <h4>"
				+ "     </div>" 
				+ "   	<div class='modal-footer'>"
				+ "   		<button name='xoConfirmar' type=\"submit\" class='btn btn-group btn-success'><span>Confirmar</span></button>"
				+ " 		<button id='CancelDispersion' type='button' class='btn btn-group btn-danger' data-dismiss='modal' title='Cancelar'> <span> Cancelar </span></button>"
				+ "  	</div>" 
				+ "   </div>" 
				+ "  </div>" 
				+ " </div>" 
				+ " </div>";
		/* Fin JS MODAL CONFIMAR */		
		return form;
	}
	
	private static boolean isEmail(String email) {
	       
        Pattern patronEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$");
   
        Matcher mEmail = patronEmail.matcher(email.toLowerCase());
        if (mEmail.matches()){
           return true; 
        }
        return false;
    }
}

 */