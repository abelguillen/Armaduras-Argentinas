$(document).ready(function() {
	// SI OBSERVACIONES ESTA VACIO, CORREME EL TOTAL A LA DERECHA (imprimirRemito)
	var obser = $('span.obserRem').val();
	if (obser == null || obser.length == 0) {
		console.log("hola");
		$("span.totalGlobal").css({
			"padding-left" : "707px"
		});
	}
});