$(document).ready(function() {
	// VALIDAR NRO DE REFERENCIA - AJAX
	$('input#referencia').on('keyup change blur', function() {
		var nroReferencia = $('input#referencia').val();
		var erro = $('#errorInputNroReferenciaRep').val();
		console.log(nroReferencia);
		
		if(erro = "error") {
			$('#errorInputNroReferenciaRep').remove();
		}
		if (nroReferencia.length == 10) {
			$.get('/nroReferencia' + '/' + nroReferencia, function(data) {
				if (data == false) {
					$('input#referencia').removeClass('invalido');
					$('input#referencia').addClass('valido');
				}
				else {
					if(erro = "error") {
						$('#errorInputNroReferenciaRep').remove();
					}
					$('input#referencia').removeClass('valido');
					$('input#referencia').removeClass('invalido');
					$('input#referencia').addClass('atencion');
					var errNroRefRep = "<p value='error' id='errorInputNroReferenciaRep' style='color:#2E2EFE;'>Atencion: El nro de referencia agregado ya existe. Si guarda la informacion de la chapa, esta se guardara bajo el nro de referencia previamente ingresado.</p>";
					$('input#referencia').after(errNroRefRep);
				}
			});
		}
	});
	
	// VALIDACION INPUT DE NRO DE REFERENCIA
	$('input#referencia').on('keyup change blur', function() {
		var cadena = $('input#referencia').val();
		var err = $('#errorInputNroReferencia').val();
		if(err = "error") {
			$('#errorInputNroReferencia').remove();
		}
		var tieneL = tieneLetras(cadena);
		// Si es invalido
		if (cadena.length != 10 || tieneL == true) {
			$('input#referencia').removeClass('valido');
			$('input#referencia').addClass('invalido');
			var errInputNroReferencia = "<p value='error' id='errorInputNroReferencia' style='color:red;'>El campo debe ser numerico y debe contener 10 caracteres</p>";
			$('input#referencia').after(errInputNroReferencia);
		} 
		// Si es valido
		else { 
			if (cadena.length == 10 && tieneL == false) {
				$('input#referencia').removeClass('invalido');
				$('input#referencia').addClass('valido');
			}
		}
	});
	
	//VALIDACION INPUT DE CANTIDAD
	$('input#cantidadMP').on('keyup change blur', function() {
		var cadena = $('input#cantidadMP').val();
		var err = $('#errInputCantidadMP').val();
		var erra = $('#errInputCantidadMPa').val();
		if(err = "error") {
			$('#errInputCantidadMP').remove();
		}
		if(erra = "error") {
			$('#errInputCantidadMPa').remove();
		}
		var tieneL = tieneLetras(cadena);
		var tieneC = tieneComa(cadena);
		// Si es invalido
		if (cadena.length == 0 || tieneL == true) {
			$('input#cantidadMP').removeClass('valido');
			$('input#cantidadMP').addClass('invalido');
			var errInputCantidadMP = "<p value='error' id='errInputCantidadMP' style='color:red;'>El campo debe ser numerico</p>";
			$('input#cantidadMP').after(errInputCantidadMP);
		}
		if (tieneC) {
			$('input#cantidadMP').removeClass('valido');
			$('input#cantidadMP').addClass('invalido');
			var errInputCantidadMPa = '<p value="error" id="errInputCantidadMPa" style="color:red;">El separador decimal debe ser "."</p>';
			$('input#cantidadMP').after(errInputCantidadMPa);
		}
		// Si es valido
		else 
			if (cadena.length >= 2)
		{
			$('input#cantidadMP').removeClass('invalido');
			$('input#cantidadMP').addClass('valido');
		}
	});
	
	//VALIDACION INPUT DE LOTE
	$('input#lote').on('keyup change blur', function() {
		var cadenaLote = $('input#lote').val();
		var material = $('select#material').val();
		var err = $('#errInputLoteMP').val();
		if(err = "error") {
			$('#errInputLoteMP').remove();
		}
		var tieneL = tieneLetras(cadenaLote);
		// Si es invalido
		if (cadenaLote.length != 10 || tieneL == true) 
		{
			$('input#lote').removeClass('valido');
			$('input#lote').addClass('invalido');
			var errInputLoteMP = "<p value='error' id='errInputLoteMP' style='color:red;'>El campo debe ser numerico y de 10 digitos</p>";
			$('input#lote').after(errInputLoteMP);
		}
		// Si es valido
		else 
			if (cadenaLote.length == 10) 
		{
				$('input#lote').removeClass('invalido');
				$('input#lote').addClass('valido');
		}
	});
				
	//VALIDACION INPUT DE LOTE - AJAX
	$('input#lote').on('keyup change blur', function() {
		var cadenaLote = $('input#lote').val();
		var material = $('select#material').val();
		var err = $('#errInputLoteInvalido').val();
		if(err = "errorI") {
			$('#errInputLoteInvalido').remove();
		}
		// Si es valido
		if (cadenaLote.length == 10) {
			$.get('/nroLote' + '/' + cadenaLote + '/' + material, function(data) {
				if (data == false) {
					$('input#lote').removeClass('invalido');
					$('input#lote').addClass('valido');
				}
				else {
					if(err = "errorI") {
						$('#errInputLoteInvalido').remove();
					}
					$('input#lote').removeClass('valido');
					$('input#lote').addClass('invalido');
					var errInputLoteInvalido = "<p value='errorI' id='errInputLoteInvalido' style='color:red;'>Lote repetido</p>";
					$('input#lote').after(errInputLoteInvalido);
				}
			});
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

function tieneComa(cadena) {
	letras = ","
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				return true;
			}
		}
	}
	return false;
}