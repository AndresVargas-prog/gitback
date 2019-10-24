/**
 * Andres Vargas
 * */

function FuncionRB(radioValue) {
    var tr = radioValue.val();
    var row = document.getElementById(tr);
    $("#tarjeta").val(row.cells[1].innerText);
    $("#tarjeta2").val(row.cells[1].innerText);
    $("#correo").val(row.cells[6].innerText);
    $("#empleadoid").val(row.cells[3].innerText);
    $("#empleadoid2").val(row.cells[3].innerText);
    $("#nombrehiid").val(row.cells[4].innerText);
    $("#correoanthiid").val(row.cells[6].innerText);
    $("#correophiid").val(row.cells[6].innerText);
    $("#telefonohiid").val(row.cells[5].innerText);
}

$(document).ready(function(){
	$("#xoactualiza").on("click", function(event){
		event.preventDefault();

		var tar = document.getElementById("tarjeta").value;
		
		if(tar == "" || tar == null){
			alert("Numero de Tarjeta Obligatorio");
			return;
		}else{
			if(tar.length != 16){
				alert("El numero de Tarjeta solo debe tener 16 digitos");
				return;
			}else{
				if(!$.isNumeric(tar)){
					alert("El Numero de Tarjeta solo debe llevar Numeros");
					return;
				}
			}
		}
		
		var nombre = document.getElementById("nombrehiid").value
		var correop = document.getElementById("correophiid").value
		var correoant = document.getElementById("correoanthiid").value
		var telefono = document.getElementById("telefonohiid").value
		var empleadoid = document.getElementById("empleadoid2").value
		var tarjeta = document.getElementById("tarjeta2").value
		
		document.getElementById("nombreid").value = nombre;
		document.getElementById("correopid").value = correop;
		document.getElementById("correoantid").value = correop;
		document.getElementById("telefonoid").value = telefono;
		document.getElementById("empleadoidid").value = empleadoid;
		document.getElementById("tarjeta2id").value = tarjeta;
		
		$("#ModalActualizaMail").modal('show');
	});
});

$(document).ready(function(){
	$("#xoactualizar").on("click", function(event){
		event.preventDefault();
			
		var tar = document.getElementById("tarjeta").value;
		
		if(tar == "" || tar == null){
			alert("Numero de Tarjeta Obligatorio");
			return;
		}else{
			if(tar.length != 16){
				alert("El numero de Tarjeta solo debe tener 16 digitos")
				return;
			}else{
				if(!$.isNumeric(tar)){
					alert("El Numero de Tarjeta solo debe llevar Numeros")
					return;
				}
			}
		}
		
		var nombre = document.getElementById("nombrehiid").value
		var correop = document.getElementById("correophiid").value
		var correoant = document.getElementById("correoanthiid").value
		var telefono = document.getElementById("telefonohiid").value
		var empleadoid = document.getElementById("empleadoid2").value
		var tarjeta = document.getElementById("tarjeta2").value
		
		document.getElementById("nombreid").value = nombre;
		document.getElementById("correopid").value = correop;
		document.getElementById("correoantid").value = correop;
		document.getElementById("telefonoid").value = telefono;
		document.getElementById("empleadoidid").value = empleadoid;
		document.getElementById("tarjeta2id").value = tarjeta;
		
		$("#ModalActualizaMail").modal('show');
	});
	
});