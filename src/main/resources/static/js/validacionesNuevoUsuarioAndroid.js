$(document).ready(function() {
$('input.codigoAndroid').on('keyup change blur', function() {
		var codigo = $('input.codigoAndroid').val();
		if(codigo.length == 3){
		$.get('/codigoRepetidoUsuarioAndroid' + '/' + codigo, function(data) {
			if(data){
				var errorFormulario = "<p value='error' id='errorFormulario' style='color:red;'>El codigo ya esta en uso. </p>";
					$('input.codigoAndroid').removeClass('valido');
					$('input.codigoAndroid').addClass('invalido');
					if ($('p#errorFormulario').html() == null) {
						$('input.codigoAndroid').after(errorFormulario);
					}	
			}else {
				$('p#errorFormulario').remove();
				$('input.codigoAndroid').removeClass('invalido');
				$('input.codigoAndroid').addClass('valido');
			}
		});
		}
	});
var codigoAntiguo = $('input.codigoAndroidModificar').val();
$('input.codigoAndroidModificar').on('keyup change blur', function() {
	var codigo = $('input.codigoAndroidModificar').val();
	if(codigo.length == 3 && codigoAntiguo != codigo){
	$.get('/codigoRepetidoUsuarioAndroid' + '/' + codigo, function(data) {
		if(data){
			var errorFormulario = "<p value='error' id='errorFormulario' style='color:red;'>El codigo ya esta en uso. </p>";
				$('input.codigoAndroidModificar').removeClass('valido');
				$('input.codigoAndroidModificar').addClass('invalido');
				if ($('p#errorFormulario').html() == null) {
					$('input.codigoAndroidModificar').after(errorFormulario);
				}	
		}else {
			$('p#errorFormulario').remove();
			$('input.codigoAndroidModificar').removeClass('invalido');
			$('input.codigoAndroidModificar').addClass('valido');
		}
	});
	}
});
$('input.fechaAndroid').on('keyup change blur', function() {
	var fecha = $('input.fechaAndroid').val();
	var errorFormulario = "<p value='error' id='errorFormulario' style='color:red;'>La fecha es invalida. </p>";
	if (!validarFecha(fecha)) {
		$('input.fechaAndroid').removeClass('valido');
		$('input.fechaAndroid').addClass('invalido');
		if ($('p#errorFormulario').html() == null) {
			$('input.fechaAndroid').after(errorFormulario);
		}
	} else {
		$('p#errorFormulario').remove();
		$('input.fechaAndroid').removeClass('invalido');
		$('input.fechaAndroid').addClass('valido');
	}
});
});

//Valida una fecha ingresada como "m/d/aaaa"
//- Si no es válida, devuelve false
//- Si es válida, devuelve un objeto {d:"día",m:"mes",a:"año",date:date}
//- Parámetro: UTC (opcional) si se debe devolver {date:(date)} en UTC
//
function validarFecha(texto, UTC = false) {
 let fechaValida = regexValidarFecha(),
     // fechaValida = /^(?:(?:(0?[1-9]|1\d|2[0-8])[/](0?[1-9]|1[0-2])|(29|30)[/](0?[13-9]|1[0-2])|(31)[/](0?[13578]|1[02]))[/](0{2,3}[1-9]|0{1,2}[1-9]\d|0?[1-9]\d{2}|[1-9]\d{3})|(29)[/](0?2)[/](\d{1,2}(?:0[48]|[2468][048]|[13579][26])|(?:0?[48]|[13579][26]|[2468][048])00))$/,
     grupos;
     
 if (grupos = fechaValida.exec(texto)) {
     //Unir día mes y año desde los grupos que pueden haber coincidido
     let d = [grupos[1],grupos[3],grupos[5],grupos[8]].join(''),
         m = [grupos[2],grupos[4],grupos[6],grupos[9]].join(''),
         a = [grupos[7],grupos[10]].join(''),
         date = new Date(0);

     //Obtener la fecha en formato local o UTC
     if (UTC) {
         date.setUTCHours(0);
         date.setUTCFullYear(a,parseInt(m,10) - 1,d);
     } else {
         date.setHours(0);
         date.setFullYear(a,parseInt(m,10) - 1,d);
     }
     
     //Devolver como objeto con cada número por separado
     return {
         d: d,
         m: m,
         a: a,
         date: date
     };
 }
 return false; //No es fecha válida
}
//Devuelve el regex para validar una fecha.
//- dividido en variables para que se entienda y se pueda mantener/editar.
//
function regexValidarFecha() {
 let sep              = "[/]",
 
     dia1a28          = "(0?[1-9]|1\\d|2[0-8])",
     dia29            = "(29)",
     dia29o30         = "(29|30)",
     dia31            = "(31)",
     
     mes1a12          = "(0?[1-9]|1[0-2])",
     mes2             = "(0?2)",
     mesNoFeb         = "(0?[13-9]|1[0-2])",
     mes31dias        = "(0?[13578]|1[02])",
     
     diames29Feb      = dia29+sep+mes2,
     diames1a28       = dia1a28+sep+mes1a12,
     diames29o30noFeb = dia29o30+sep+mesNoFeb,
     diames31         = dia31+sep+mes31dias,
     diamesNo29Feb    = "(?:"+diames1a28+"|"+diames29o30noFeb+"|"+diames31+")",
     
     anno1a9999     = "(0{2,3}[1-9]|0{1,2}[1-9]\\d|0?[1-9]\\d{2}|[1-9]\\d{3})",
     annoMult4no100   = "\\d{1,2}(?:0[48]|[2468][048]|[13579][26])",
     annoMult400      = "(?:0?[48]|[13579][26]|[2468][048])00",
     annoBisiesto     = "("+annoMult4no100+"|"+annoMult400+")",
     
     fechaNo29Feb     = diamesNo29Feb+sep+anno1a9999,
     fecha29Feb       = diames29Feb+sep+annoBisiesto,
     
     fechaFinal       = "^(?:"+fechaNo29Feb+"|"+fecha29Feb+")$";
 
 return new RegExp(fechaFinal);
}