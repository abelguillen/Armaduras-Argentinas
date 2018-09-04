$(document).ready(function() {
	// VALIDAR NRO DE CODIGO - AJAX
	$('input#numSap').on('keyup change blur', function() {
		var nroSAP = $('input#numSap').val();
		var erro = $('#errorInputNroSAPVal').val();
		if(erro = "error") {
			$('#errorInputNroSAPVal').remove();
		}
		if (nroSAP.length == 7) {
			$.get('/nroSap' + '/' + nroSAP, function(data) {
				if (data == false) {
					$('input#numSap').removeClass('invalido');
					$('input#numSap').addClass('valido');
				}
				else {
					if(erro = "error") {
						$('#errorInputNroSAPVal').remove();
					}
					$('input#numSap').removeClass('valido');
					$('input#numSap').addClass('invalido');
					var errNroSapRep = "<p value='error' id='errorInputNroSAPVal' style='color:red;'>El codigo ingresado ya existe</p>";
					$('input#numSap').after(errNroSapRep);
				}
			});
		}
	});
	
	// VALIDACION INPUT DE NRO DE SAP
	$('input#numSap').on('keyup change blur', function() {
		var cadena = $('input#numSap').val();
		var err = $('#errorInputNroSap').val();
		if(err = "error") {
			$('#errorInputNroSap').remove();
		}
		var tieneL = tieneLetras(cadena);
		// Si es invalido
		if (cadena.length != 7 || tieneL == true) {
			$('input#numSap').removeClass('valido');
			$('input#numSap').addClass('invalido');
			var errInputNroSap = "<p value='error' id='errorInputNroSap' style='color:red;'>El campo debe ser numerico y debe contener 7 caracteres</p>";
			$('input#numSap').after(errInputNroSap);
		} 
		// Si es valido
		else if (cadena.length == 7 && tieneL == false) {
			$('input#numSap').removeClass('invalido');
			$('input#numSap').addClass('valido');
		}
	});
	
	//VALIDACION INPUT DE DESCRIPCION
	$('input#descripcionCod').on('keyup change blur', function() {
		var cadena = $('input#descripcionCod').val();
		var err = $('#errInputDescrCod').val();
		console.log(cadena);
		if(err = "error") {
			$('#errInputDescrCod').remove();
		}
		var tieneL = tieneLetras(cadena);
		// Si es invalido
		if (cadena.length == 0) {
			$('input#errInputDescrCod').removeClass('valido');
			$('input#errInputDescrCod').addClass('invalido');
			var errInputDescrCod = "<p value='error' id='errInputDescrCod' style='color:red;'>El campo no puede ser vacio</p>";
			$('input#descripcionCod').after(errInputDescrCod);
		} 
		// Si es valido
		else if (cadena.length >= 1) {
			console.log("valido");
			$('input#fechaRemito').removeClass('invalido');
			$('input#fechaRemito').addClass('valido');
		}
	});
});

//FUNCION PARA SABER SI UNA CADENA TIENE LETRAS
function tieneLetras(cadena) {
	letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				return true;
			}
		}
	}
	return false;
}