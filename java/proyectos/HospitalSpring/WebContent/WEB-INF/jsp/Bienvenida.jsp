<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hospital Niños Heroes</title>
</head>
<body>
	<form action="Login" method="get">
			<h3 align=center> Bienvenido Doctor@ <%out.print(request.getAttribute("nombre")); %> A su Hospital Niños Heroes</h3>
			<h4 align="center"><%out.print(request.getAttribute("fecha")); %></h4>
			<br><br>
			<table>
				<tr><td>Hoy tiene pendiente 4 consultas:</td></tr>
				   <tr><td></td><td>* Josefa Ortiz, 67 años, fractura en la columna, pide Radiografia.</td></tr>
				   <tr><td></td><td>* Luis Felipe Gomez, 26 años, rodilla fracturada, Rayos-X.</td></tr>
				   <tr><td></td><td>* Luisa Fernanda Quirorte, 23, Presion baja.</td></tr><tr></tr><tr></tr>
				   <tr><td></td><td>* Ramon VillaMaria Huerta, 79 años, Dolor en la columna vertebral.</td></tr>					
				<tr><td></td><td><input type="submit" value="Cerrar Sesion" align="center"></td></tr>
			</table>
	</form>
</body>
</html> 