// SETEAR UN DEFAULT PARA LOS DATEPICKER
jQuery(function ($) {
	$.datepicker.regional['es'] = {
		closeText: 'Cerrar',
		currentText: 'Hoy',
		monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
		'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
		monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
		'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
		dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
		dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié;', 'Juv', 'Vie', 'Sáb'],
		dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
		weekHeader: 'Sm',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ''
	};
	$.datepicker.setDefaults($.datepicker.regional['es']);
});

// DATEPICKER FECHA DE REMITO

$(function() {
	$("#fechaRemito").datepicker({
		dateFormat : "dd/mm/yy",
	});
});
$(document).ready(function() {
	// VALIDAR NRO DE REMITO - AJAX
	$('input#nroRemito').on('keyup change blur', function() {
		var nroRem = $('input#nroRemito').val();
		var erro = $('#errorInputNroRemRep').val();
		if(erro = "error") {
			$('#errorInputNroRemRep').remove();
		}
		if (nroRem != null && nroRem.length != 0 && nroRem.length == 13) {
			$.get('/remito' + '/' + nroRem, function(data) {
				if (data == false) {
					$('input#nroRemito').removeClass('invalido');
					$('input#nroRemito').addClass('valido');
				}
				else {
					if(erro = "error") {
						$('#errorInputNroRemRep').remove();
					}
					$('input#nroRemito').removeClass('valido');
					$('input#nroRemito').addClass('invalido');
					var errNroRemRep = "<p value='error' id='errorInputNroRemRep' style='color:red;'>El nro de remito ingresado ya existe</p>";
					$('input#nroRemito').after(errNroRemRep);
				}
			});
		}
	});
	
	// REDIRECCIONAR CUANDO HACE CLICK
	$(".filaRemito").click(function() {
		var href = $(this).attr('href');
		var id = "";
		var cont = 0;
		for (i = 0; i < href.length; i++) {
			if (href.charAt(i) == "/") 
			{
				cont++;
			}
			if (cont >= 2) {
				id += href.charAt(i);
			}
		}
		var estadoDelRemito = $('tr.visto td#estado span').html();
		if (estadoDelRemito == 'Anulado'){
			alert("El remito esta anulado!")
		} else {
			var hre = '/mostrarRemitos' + id;
			window.document.location = hre;
		}	
	});
	
	// REDIRECCIONAR CUANDO HACE CLICK ACERBRAG
	$(".filaRemitoAcerBrag").click(function() {
		var href = $(this).attr('href');
		var id = "";
		var cont = 0;
		for (i = 0; i < href.length; i++) {
			if (href.charAt(i) == "/") 
			{
				cont++;
			}
			if (cont >= 2) {
				id += href.charAt(i);
			}
		}
		var estadoDelRemito = $('tr.visto td#estado span').html();
		if (estadoDelRemito == 'Anulado'){
			alert("El remito esta anulado!")
		} else {
			var hre = '/mostrarRemitosAcerBrag' + id;
			window.document.location = hre;
		}	
	});
	
	// VALIDACION INPUT DE NRO DE REMITO
	$('input#nroRemito').on('keyup change blur', function() {
		var cadena = $('input#nroRemito').val();
		var err = $('#errorInputNroRemito').val();
		if(err = "error") {
			$('#errorInputNroRemito').remove();
		}
		var tieneL = tieneLetras(cadena);
		// Si es invalido
		if (cadena.length != 13 || tieneL == true) {
			$('input#nroRemito').removeClass('valido');
			$('input#nroRemito').addClass('invalido');
			var errInputNroRemito = "<p value='error' id='errorInputNroRemito' style='color:red;'>El campo debe tener el formato: 0001-00000XXX y debe contener 13 caracteres</p>";
			$('input#nroRemito').after(errInputNroRemito);
		} 
		// Si es valido
		else if (cadena.length == 13 && tieneL == false) {
			$('input#nroRemito').removeClass('invalido');
			$('input#nroRemito').addClass('valido');
		}
		// Si tiene "/" o "." es invalido
		for (var i = 0; i < cadena.length; i++) {
			if (cadena.charAt(i) == "/" || cadena.charAt(i) == ".") {
				$('input#nroRemito').removeClass('valido');
				$('input#nroRemito').addClass('invalido');
			}
		}
	});
	
//VALIDACION INPUT FECHA DE REMITO

	// FECHA
	$('input#fechaRemito').on('keyup change blur', function() {
		var cadena = $('input#fechaRemito').val();
		var err = $('#errInputfechaRemito').val();
		if(err = "error") {
			$('#errInputfechaRemito').remove();
		}
		var tieneL = tieneLetras(cadena);
		// Si es invalido
		if (cadena.length != 10 || tieneL == true) {
			console.log("invalido");
			$('input#fechaRemito').removeClass('valido');
			$('input#fechaRemito').addClass('invalido');
			var errInputfechaRemito = "<p value='error' id='errInputfechaRemito' style='color:red;'>El campo debe ser numerico y de formato DD/MM/AAAA</p>";
			$('input#fechaRemito').after(errInputfechaRemito);
		} 
		// Si es valido
		else if (cadena.length == 10 && tieneL == false) {
			console.log("valido");
			$('input#fechaRemito').removeClass('invalido');
			$('input#fechaRemito').addClass('valido');
		}
	});

	// CANTIDAD DE PEDIDOS - HACER APARECER INPUTS
	$('#cantidadInputs').on('change', function() {
		var cantidad = $('#cantidadInputs').val();
		var inputUno = $('#pedUno').val();
		var inputDos = $('#pedDos').val();
		var inputTres = $('#pedTres').val();
		if (cantidad == "1") {
			$('#pedUno').addClass('visible');
			$('#pedDos').removeClass('visible');
			$('#pedDos').addClass('oculto');
			$('#pedTres').removeClass('visible');
			$('#pedTres').addClass('oculto');
		}
		if (cantidad == "2") {
			$('#pedUno').addClass('visible');
			$('#pedDos').removeClass('oculto');
			$('#pedDos').addClass('visible');
			$('#pedTres').removeClass('visible');
			$('#pedTres').addClass('oculto');
		}
		if (cantidad == "3") {
			$('#pedUno').addClass('visible');
			$('#pedDos').removeClass('oculto');
			$('#pedDos').addClass('visible');
			$('#pedTres').removeClass('oculto');
			$('#pedTres').addClass('visible');
		}
	});
});

// FUNCION PARA VALIDAR EL NUMERO DE REMITO AL HACER CLICK EN GUARDAR
function nroDeRemito() {
	if ($('#nroRemito').hasClass('invalido')) {
		return false;
	} else {
		return true;
	}
}
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