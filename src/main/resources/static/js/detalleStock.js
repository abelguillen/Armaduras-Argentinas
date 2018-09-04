// REDIRECCIONAR AL CLICKEAR EN STOCK
jQuery(document).ready(function($) {
	$(".detalleStock").click(function() {
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
		var hre = '/stock/detalle' + id;
		window.document.location = hre;
	});
});