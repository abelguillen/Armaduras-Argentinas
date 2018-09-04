$(document).ready(function() {
	$('input.formato').on('keyup change blur', function() {
		var cadena = $('input.formato').val();
		validaFormato(cadena, 'formato');
	});
	$('input.posicionF').on('keyup change blur', function() {
		var cadena = $('input.posicionF').val();
		validaPosicionF(cadena, 'posicionF');
	});
	$('input.lados').on('keyup change blur', function() {
		var cadena = $('input.lados').val();
		validaLados(cadena, 'lados');
	});
	
	$('input.cant_doblados').on('keyup change blur', function() {
		var cadena = $('input.cant_doblados').val();
		validaCantidadDoblados(cadena, 'cant_doblados');
	});
	
	$('input.kgEnPlanta').on('keyup change blur', function(){
		var cadena = $('input.kgEnPlanta').val();
		validaKgEnPlanta(cadena,'kgEnPlanta');
	})
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

function validaPosicionF(cadena, input) {
	var errorTelefono = "<p value='error' id='errorTelefono' style='color:red;'>El campo debe estar completo.</p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorTelefono').html() == null) {
			$('input.' + input).after(errorTelefono);
		}
	} else {
		$('p#errorTelefono').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
			}
		}
	}
}

function validaKgEnPlanta(cadena, input) {
	var errorKgEnPlanta = "<p value='error' id='errorKgEnPlanta' style='color:red;'>El campo debe estar completo.</p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorKgEnPlanta').html() == null) {
			$('input.' + input).after(errorKgEnPlanta);
		}
	} else {
		$('p#errorKgEnPlanta').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
			}
		}
	}
}

function validaFormato(cadena, input) {
	var errorFormato = "<p value='error' id='errorFormato' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorFormato').html() == null) {
			$('input.' + input).after(errorFormato);
		}
	} else {
		$('p#errorFormato').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}

}

function validaLados(cadena, input) {
	var errorLados = "<p value='error' id='errorLados' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorLados').html() == null) {
			$('input.' + input).after(errorLados);
		}
	} else {
		$('p#errorLados').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
			}
		}
	}
}

function validaCantidadDoblados(cadena, input) {
	var errorDoblados = "<p value='error' id='errorDoblados' style='color:red;'>El campo debe estar completo.</p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorDoblados').html() == null) {
			$('input.' + input).after(errorDoblados);
		}
	} else {
		$('p#errorDoblados').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
			}
		}
	}
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
			}
		}
	}
}

