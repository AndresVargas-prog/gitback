<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registro</title>

<form method="POST" align="center">
	<table align="center">
		<tr>
			<th colspan="3">Introdusca sus datos de Registro<br><br><br></th>
		</tr>
		
		<tr><td>Nombre:        </td><td><input type="text" name="nombrer" value="${nombrer}"></td></tr>
		<tr><td>Edad:          </td><td><input type="text" name="edadr" value="${edadr}"></td></tr>
		<tr><td>Sexo:<br></td></tr>
		<tr><td>H			   </td><td><input type="radio" name="sexo" value="Hombre"></td></tr>
		<tr><td>M			   </td><td><input type="radio" name="sexo" value="Mujer"></td></tr>
		<tr><td>Direccion:     </td><td><input type = "text" name="direccionr" value="${direccionr}"></td></tr>
		<tr><td>Especialidad:  </td><td><input type="text" name="especialidadr" value="${especialidadr}"></td></tr>
		<tr><td>email:		   </td><td><input type="text" name="emailr" value="${emailr}"></td></tr>
		<tr><td>Contraseña:	   </td><td><input type="password" name="contrar" value="${contrar}"></td></tr>
		<tr><td></td><td><br><input type="submit" value="Enviar"></td><td></td></tr>
	</table>
</form>

</body>
</html>