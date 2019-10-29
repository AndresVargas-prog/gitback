<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<h3 align="center">Login</h3>

<form method="POST">
	<table align="center">
		
		<tr><td>E-mail: </td><td><input type="text" name="emaill" value="${emaill}"><br><br></td></tr>
		
		<tr><td>Contraseña: </td><td><input type="password" name="contral" value="${contral}"><br><br></td></tr>
		
		<tr><td><a href="Registro">Registrate</a></td>  <td><input type="submit" value="Login"></td></tr>
	</table>
	</form>

</body>
</html>