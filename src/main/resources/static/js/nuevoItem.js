// Validacion de diametro segun el acero en nuevo item
$(document).ready(function() {
	$('select#acero').on('change', function() {
		var acero = $('select#acero').val();
		if (acero == "ANGULOS") {
			$('select.valAngulos').css({
				"display" : "block"
			});
			$('select.valHierro').css({
				"display" : "none"
			});
			$('select.valClavos').css({
				"display" : "none"
			});
			$('select.valAlambres').css({
				"display" : "none"
			});
			$('select.valAlambron').css({
				"display" : "none"
			});
			// Cambio el name con Jquery
			$("select.valAngulos").attr("name","diametro");
			$("select.valHierro").attr("name","OTRO");
			$("select.valClavos").attr("name","OTRO");
			$("select.valAlambres").attr("name","OTRO");
			$("select.valAlambron").attr("name","OTRO");
		}
		if (acero == "ADN420S" || acero == "ADN420" || acero == "AL220" || acero == "ATR500") {
			$('select.valHierro').css({
				"display" : "block"
			});
			$('select.valAngulos').css({
				"display" : "none"
			});
			$('select.valClavos').css({
				"display" : "none"
			});
			$('select.valAlambres').css({
				"display" : "none"
			});
			$('select.valAlambron').css({
				"display" : "none"
			});
			// Cambio el name con Jquery
			$("select.valHierro").attr("name","diametro");
			$("select.valAngulos").attr("name","OTRO");
			$("select.valClavos").attr("name","OTRO");
			$("select.valAlambres").attr("name","OTRO");
			$("select.valAlambron").attr("name","OTRO");
		}
		if (acero == "CLAVOS") {
			$('select.valClavos').css({
				"display" : "block"
			});
			$('select.valAlambres').css({
				"display" : "none"
			});
			$('select.valHierro').css({
				"display" : "none"
			});
			$('select.valAngulos').css({
				"display" : "none"
			});
			$('select.valAlambron').css({
				"display" : "none"
			});
			// Cambio el name con Jquery
			$("select.valClavos").attr("name","diametro");
			$("select.valHierro").attr("name","OTRO");
			$("select.valAlambres").attr("name","OTRO");
			$("select.valAngulos").attr("name","OTRO");
			$("select.valAlambron").attr("name","OTRO");
		}
		if (acero == "ALAMBRES") {
			$('select.valAlambres').css({
				"display" : "block"
			});
			$('select.valClavos').css({
				"display" : "none"
			});
			$('select.valHierro').css({
				"display" : "none"
			});
			$('select.valAngulos').css({
				"display" : "none"
			});
			$('select.valAlambron').css({
				"display" : "none"
			});
			// Cambio el name con Jquery
			$("select.valAlambres").attr("name","diametro");
			$("select.valHierro").attr("name","OTRO");
			$("select.valAlambron").attr("name","OTRO");
			$("select.valClavos").attr("name","OTRO");
			$("select.valAngulos").attr("name","OTRO");
		}
		if (acero == "ALAMBRON") {
			$('select.valAlambron').css({
				"display" : "block"
			});
			$('select.valAlambres').css({
				"display" : "none"
			});
			$('select.valClavos').css({
				"display" : "none"
			});
			$('select.valHierro').css({
				"display" : "none"
			});
			$('select.valAngulos').css({
				"display" : "none"
			});
			// Cambio el name con Jquery
			$("select.valAlambron").attr("name","diametro");
			$("select.valHierro").attr("name","OTRO");
			$("select.valAlambres").attr("name","OTRO");
			$("select.valClavos").attr("name","OTRO");
			$("select.valAngulos").attr("name","OTRO");
		}
	});
	
	// ELIMINAR EL SELECT SOBRANTE AL HACER CLICK EN GUARDAR
	$('.botonNuevoItem').click(function() {
		var acero = $('select#acero').val();
		if (acero == "ANGULOS") {
			$('select.valHierro').remove();
			$('select.valAlambron').remove();
			$('select.valClavos').remove();
			$('select.valAlambres').remove();
		}
		if (acero == "ADN420S" || acero == "ADN420" || acero == "AL220") {
			$('select.valAngulos').remove();
			$('select.valClavos').remove();
			$('select.valAlambres').remove();
			$('select.valAlambron').remove();
		}
		if (acero == "CLAVOS") {
			$('select.valAngulos').remove();
			$('select.valHierro').remove();
			$('select.valAlambres').remove();
			$('select.valAlambron').remove();
		}
		if (acero == "ALAMBRES") {
			$('select.valAngulos').remove();
			$('select.valHierro').remove();
			$('select.valClavos').remove();
			$('select.valAlambron').remove();
		}
		if (acero == "ALAMBRON") {
			$('select.valAngulos').remove();
			$('select.valHierro').remove();
			$('select.valClavos').remove();
			$('select.valAlambres').remove();
		}
	});
});