// REDIRECCIONAR AL CLICKEAR INGRESOS DE MP
jQuery(document).ready(function($) {
	$(".ingresoRemitos").click(function() {
		var href = $(this).attr('href');
		var id = "";
		var cont = 0;
		for (i = 0; i < href.length; i++) {
			if (href.charAt(i) == "/") {
				cont++;
			}
			if (cont >= 3) {
				id += href.charAt(i);
			}
		}
		var hre = '/materiaprima/ingresomp' + id;
		window.document.location = hre;
	});
});