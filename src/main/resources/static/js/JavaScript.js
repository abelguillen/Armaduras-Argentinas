//FORMATO URL
function llegoUrl(url) {
	$('input.formatoURL').val(url);
	$('img.srcFormato').attr("src", url);
};
// AJAX FORMATO
$(document).ready(function() {
	$('input#FORMATO').on('keyup change blur', function() {
		var posicion = $('input#FORMATO').val();
		$.get('/formato' + '/' + posicion, function(data) {
			var nueva = "";
			for (var i = 6; i < data.length - 7; i++) {
				var caracter = data.charAt(i);
				nueva += data.charAt(i);
			}
			$('input#dibujo').val(nueva);
			$("img#dibujo").attr("src", nueva);
			$("img#dibujo").css({
				"padding-left" : "27%",
				"height" : "115px"
			});
		});
		// CANTIDAD DE LADOS EN FORMATOS
		$.get('/lados' + '/' + posicion, function(data) {
			var cantidad_lados = data;
			for (var x = 1; x <= 8; x++) {
				if (x <= cantidad_lados) {
					$('input.' + x).attr('type', 'text');
				} else {
					$('input.' + x).attr('type', 'hidden');
				}
			}
		});
	});
});

// AJAX lTotal EN NUEVO ITEM
$(document).ready(
		function() {
			$('input#cantidad').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.1').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.2').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.3').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.4').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.5').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.6').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
$(document).ready(
		function() {
			$('input.7').on(
					'keyup change blur',
					function() {
						var cantidad = $('input#cantidad').val();
						var a = $('input.1').val();
						var b = $('input.2').val();
						var c = $('input.3').val();
						var d = $('input.4').val();
						var e = $('input.5').val();
						var f = $('input.6').val();
						var g = $('input.7').val();
						if (cantidad == '') {
							cantidad = 0;
						}
						if (a == '') {
							a = 0;
						}
						if (b == '') {
							b = 0;
						}
						if (c == '') {
							c = 0;
						}
						if (d == '') {
							d = 0;
						}
						if (e == '') {
							e = 0;
						}
						if (f == '') {
							f = 0;
						}
						if (g == '') {
							g = 0;
						}
						$.get('/item/lTotal' + '/' + cantidad + '/' + a + '/'
								+ b + '/' + c + '/' + d + '/' + e + '/' + f
								+ '/' + g, function(data) {
							$('input#lTotalAJAX').val(data);
						});
					});
		});
// NO DEJAR CONTINUAR SI PONE UN DIAMETRO QUE NO CORRESPONDE
$(document)
		.ready(
				function() {
					$('input#diametro')
							.on(
									'change',
									function() {
										var diametro = $('input#diametro')
												.val();
										var errorDiam = "<p value='error' id='errorDiam' style='color:red;'>Ingrese un diametro correcto</p>";
										if (diametro != "4,2"
												&& diametro != "4.2"
												&& diametro != "6"
												&& diametro != "8"
												&& diametro != "10"
												&& diametro != "12"
												&& diametro != "16"
												&& diametro != "20"
												&& diametro != "25"
												&& diametro != "32"
												&& diametro != "2.71"
												&& diametro != "2,71"
												&& diametro != "7.40"
												&& diametro != "7,40"
												&& diametro != "9.02"
												&& diametro != "9,02"
												&& diametro != "10.71"
												&& diametro != "10,71") {
											
											$('input.diametro').removeClass(
													'valido');
											$('input.diametro').addClass(
													'invalido');
											if ($('p#errorDiam').html() == null) {
												$('input#diametro').after(
														errorDiam);
											}
										} else {
											$('p#errorDiam').remove();
											$('input.diametro').removeClass(
													'invalido');
											$('input.diametro').addClass(
													'valido');
										}
									});
				});
/*
 * $(document).ready(function() { $('input#cantidad').on('change',function() {
 * var diametro = $('input#diametro').val(); if (diametro != "4,2" && diametro !=
 * "4.2" && diametro != "6" && diametro != "8" && diametro != "10" && diametro !=
 * "12" && diametro != "16" && diametro != "20" && diametro != "25" && diametro !=
 * "32") {
 * 
 * alert("HAS INTRODUCIDO UN DIAMETRO QUE NO CORRESPONDE!"); } }); });
 * 
 * $(document).ready(function() { $('input#FORMATO').on('change',function() {
 * var diametro = $('input#diametro').val(); if (diametro != "4,2" && diametro !=
 * "4.2" && diametro != "6" && diametro != "8" && diametro != "10" && diametro !=
 * "12" && diametro != "16" && diametro != "20" && diametro != "25" && diametro !=
 * "32") { alert("HAS INTRODUCIDO UN DIAMETRO QUE NO CORRESPONDE!"); } }); });
 */

// AJAX FORMATO MOFICIAR
$(document).ready(function() {
	if ($('input#FORMATO').val() != 0) {
		var posicion = $('input#FORMATO').val();
		$.get('/formato' + '/' + posicion, function(data) {
			var nueva = "";
			for (var i = 6; i < data.length - 7; i++) {
				var caracter = data.charAt(i);
				nueva += data.charAt(i);
			}
			$('input#dibujo').val(nueva);
			$("img#dibujo").attr("src", nueva);
			$("img#dibujo").css({
				"padding-left" : "27%",
				"height" : "115px"
			});
		});
		// CANTIDAD DE LADOS EN FORMATOS
		$.get('/lados' + '/' + posicion, function(data) {
			var cantidad_lados = data;
			for (var x = 1; x <= 8; x++) {
				if (x <= cantidad_lados) {
					$('input.' + x).attr('type', 'text');
				} else {
					$('input.' + x).attr('type', 'hidden');
				}
			}
		});
	}
});

// AJAX SECTOR
/*
 * $(document).ready(function(){ if ($('select.plano').val()!=null &&
 * $('select.plano').val().length !=0 ){ var plano = $('select.plano').val();
 * $.get('/sectores' + '/' + plano, function(data) { if (data[0] == null){
 * alert("No hay sectores cargados para ese plano"); } }); $.get('/sectores' +
 * '/' + plano, function(data) { var select = $(".sector"), options = '';
 * for(var i = 0 ; i < data.length ; i ++) { options += "<option>"+ data[i] +"</option>"; }
 * select.append(options); }); }
 * 
 * $('select.plano').change(function() { $('select.subsector option').remove();
 * $('select.sector option').remove(); var plano = $('select.plano').val();
 * $.get('/sectores' + '/' + plano, function(data) { if (data.length == 0){
 * window.alert("No hay sectores cargados para ese plano"); } var select =
 * $(".sector"), options = ''; for(var i = 0 ; i < data.length ; i ++) { options += "<option>"+
 * data[i] +"</option>"; } select.append(options); }); });
 * 
 * //AJAX SUBSECTOR $.get('/sectores' + '/' + plano, function(data) { if
 * (data[0] != null){ var sector = data[0]; $.get('/subsectores' + '/' + sector,
 * function(data) { if ($('input').val() != "" && data[0] == null){ alert("No
 * hay subsectores cargados para ese sector"); } }); $.get('/subsectores' + '/' +
 * sector, function(data) { var select = $(".subsector"), options = ''; for(var
 * i = 0 ; i < data.length ; i ++) { options += "<option>"+ data[i] +"</option>"; }
 * select.append(options); }); } });
 * 
 * $('select.sector').change(function() { $('select.subsector option').remove();
 * var sector = $('select.sector').val(); $.get('/subsectores' + '/' + sector,
 * function(data) { if (data.length == 0){ window.alert("No hay subsectores
 * cargados para ese sector"); } var select = $(".subsector"), options = '';
 * for(var i = 0 ; i < data.length ; i ++) { options += "<option>"+ data[i] +"</option>"; }
 * select.append(options); }); });
 * 
 * });
 */

// AJAX OBRAS
$(document).ready(
		function() {
			if ($('select.cliente').val() != null
					&& $('select.cliente').val().length != 0) {
				var cliente = $('select.cliente').val();
				$.get('/obras' + '/' + cliente, function(data) {
					if (data.length == 0) {
						window.alert("No hay obras cargadas en ese cliente");
					}
				});
				$.get('/obras' + '/' + cliente, function(data) {
					var select = $(".obra"), options = '';
					for (var i = 0; i < data.length; i++) {
						options += "<option>" + data[i] + "</option>";
					}
					select.append(options);
				});
			}

			$('select.cliente').change(function() {
				$('select.obra option').remove();
				var plano = $('select.cliente').val();
				$.get('/obras' + '/' + plano, function(data) {
					if (data.length == 0) {
						window.alert("No hay obras cargadas en ese cliente");
					}
					var select = $(".obra"), options = '';
					for (var i = 0; i < data.length; i++) {
						options += "<option>" + data[i] + "</option>";
					}
					select.append(options);
				});
			});
		});

// AJAX OBRAS CON PEDIDOS
$(document).ready(
		function() {
			if ($('select.cliente1').val() != null
					&& $('select.cliente1').val().length != 0) {
				var cliente = $('select.cliente1').val();
				$.get('/obrasConPedidos' + '/' + cliente, function(data) {
					if (data.length == 0) {
						window.alert("No hay obras cargadas en ese cliente");
					}
				});
				$.get('/obrasConPedidos' + '/' + cliente, function(data) {
					var select = $(".obra1"), options = '';
					for (var i = 0; i < data.length; i++) {
						options += "<option>" + data[i] + "</option>";
					}
					select.append(options);
				});
			}

			$('select.cliente1').change(function() {
				$('select.obra1 option').remove();
				var plano = $('select.cliente1').val();
				$.get('/obrasConPedidos' + '/' + plano, function(data) {
					if (data.length == 0) {
						// Este es el alert que tira en remitos al cargar el
						// cliente ARMATRON que,
						// no tiene pedidos (Tira no hay obras cargadas)
						window.alert("No hay obras cargadas en ese cliente");
					}
					var select = $(".obra1"), options = '';
					for (var i = 0; i < data.length; i++) {
						options += "<option>" + data[i] + "</option>";
					}
					select.append(options);
				});
			});
		});

// AJAX PEDIDOS
$(document).ready(
		function() {
			if ($('select.cliente1').val() != null
					&& $('select.cliente1').val().length != 0
					&& $('select.obra1').val() != null
					&& $('select.obra1').val().length != 0) {
				var cliente = $('select.cliente1').val();
				var obra = $('select.obra1').val();
				obra = obra.replace(".", "_");
				obra = obra.replace("°", "_");
				$.get('/pedidos' + '/' + cliente + '/' + obra, function(data) {
					var select = $(".pedido1"), options = '';
					for (var i = 0; i < data.length; i++) {
						options += "<option>" + data[i] + "</option>";
					}
					select.append(options);
				});
			}
			$('select.obra1').on('keyup change blur', function() {
				$('select.pedido1 option').remove();
				var cliente = $('select.cliente1').val();
				var obra = $('select.obra1').val();
				obra = obra.replace(".", "_");
				obra = obra.replace("°", "_");
				$.get('/pedidos' + '/' + cliente + '/' + obra, function(data) {
					var select = $(".pedido1"), options = '';
					for (var i = 0; i < data.length; i++) {
						options += "<option>" + data[i] + "</option>";
					}
					select.append(options);
				});
			});
		});

// TABLAS DE ITEMS / PEDIDOS
// $(document).ready(function() {
// $('#myTable').DataTable();
// });

// ORDENAR POR ITEM EN ITEMS
$(document).ready(function() {
	$('#myTablee').DataTable({
		"order" : [ [ 1, "asc" ] ]
	});
});
$(document).ready(
		function() {
			$('#myTable').DataTable(
					{
						"lengthMenu" : [ [ -1, 5, 10, 25, 50, 100 ],
								[ "All", 5, 10, 25, 50, 100 ] ]
					});
		});

// ALERT PARA / O - EN FECHA DE NUEVO PEDIDO
$(document).ready(function() {
	$('input.fecha').keyup(function() {
		var fecha = $('input.fecha').val();
		for (var i = 0; i < fecha.length; i++) {
			if (fecha.charAt(i) == "/" || fecha.charAt(i) == "-") {
				window.alert("Has introducido un caracter invalido!");
			}
		}
	});
});
// PARA NOMBRE DE FANTASIA
$(document).ready(function() {
	$('input.nombreFantasia').keyup(function() {
		var fecha = $('input.nombreFantasia').val();
		for (var i = 0; i < fecha.length; i++) {
			if (fecha.charAt(i) == "/" || fecha.charAt(i) == ".") {
				window.alert("Has introducido un caracter invalido!");
			}
		}
	});
});

// CUANDO SE MODIFICA LA ESTRUCUTRA, LA POSICION VUELVE A 1 O 1A
$(document).ready(function() {
	var estructura = $('input.estructura').val();
	$('input.estructura').change(function() {
		if ($('input.estructura').val() != estructura) {
			$('input.posicion').val(1);
		}
	});
});

// $(document).ready(function(){
// function imprimir() {
// window.print();
// FormSubmit();
// function FormSubmit() {
// var submitBtn = document.getElementsByClassName('print default');
// if (submitBtn) {
// $('button.print').click();
// }
// }
// }
// });

// REDIRECCIONAR AL CLICKEAR EN MOSTRAR PEDIDOS (ETIQUETADO)
jQuery(document).ready(function($) {
	$(".clickable-row").click(function() {
		var href = $(this).attr('href');
		var estado = $('tr.visto > td.estado').text();
		var codigo = $('tr.visto > td.codigo').text();
		if (estado == "PENDIENTE" && codigo.length == 8) {
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
			// var hre = '/planosectorsubsector' + '/elegir' + id ;
			var hre = '/productos' + '/nuevoItem' + id;
		} else {
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
			var hre = '/productos' + '/items' + id;
		}

		window.document.location = hre;
	});
});
// COLOR DE FONDO AL PASAR MOUSE POR PEDIDOS
$(document).ready(function() {
	$("tbody tr").mouseover(function() {
		$(this).css("cursor", "pointer");
		if ($(this).hasClass("visto")) {
			$(this).removeClass("visto");
		} else {
			$(this).addClass("visto");
		}
	});
	$("tbody tr").mouseout(function() {
		$(this).css("cursor", "pointer");
		if ($(this).hasClass("visto")) {
			$(this).removeClass("visto");
		}
	});
});

// VALIDACIONES CLIENTES, OBRAS, PEDIDOS, FORMATOS e ITEMS
$(document).ready(function() {
	$('input.codigo').on('keyup change blur', function() {
		var cadena = $('input.codigo').val();
		formularioCodigo(cadena, 'codigo');
	});
	$('input.codigoObra').on('keyup change blur', function() {
		var cadena = $('input.codigoObra').val();
		formularioCodigoObra(cadena, 'codigoObra');
	});
	$('input.nro_cuilCliente').on('keyup change blur', function() {
		var cadena = $('input.nro_cuilCliente').val();
		formularioCUIL(cadena, 'nro_cuilCliente');
	});
	$('input.direccion').on('keyup change blur', function() {
		var cadena = $('input.direccion').val();
		formularioD(cadena, 'direccion');
	});
	$('input.condpago').on('keyup change blur', function() {
		var cadena = $('input.condpago').val();
		formularioCP(cadena, 'condpago');
	});
	$('input.contacto').on('keyup change blur', function() {
		var cadena = $('input.contacto').val();
		formulario(cadena, 'contacto');
	});
	$('input.nombreFantasia').on('keyup change blur', function() {
		var cadena = $('input.nombreFantasia').val();
		formularioNombreFantasia(cadena, 'nombreFantasia');
	});
	$('input.nombreDeObra').on('keyup change blur', function() {
		var cadena = $('input.nombreDeObra').val();
		formularioNombreFantasia(cadena, 'nombreDeObra');
	});
	$('input.razonSocial').on('keyup change blur', function() {
		var cadena = $('input.razonSocial').val();
		formulario(cadena, 'razonSocial');
	});
	$('input.dirfiscal').on('keyup change blur', function() {
		var cadena = $('input.dirfiscal').val();
		formularioDF(cadena, 'dirfiscal');
	});
	$('input.telefono').on('keyup change blur', function() {
		var cadena = $('input.telefono').val();
		formularioTelefonoCliente(cadena, 'telefono');
	});
	// Pedidos
	$('input.fech').on('keyup change blur', function() {
		var cadena = $('input.fech').val();
		formularioTelefono(cadena, 'fech');
	});
	$('input.descrip').on('keyup change blur', function() {
		var cadena = $('input.descrip').val();
		formularioDescrip(cadena, 'descrip');
	});
	$('input.4').on('keyup change blur', function() {
		var cadena = $('input.4').val();
		formularioMedida(cadena, '4');
	});
	$('input.6').on('keyup change blur', function() {
		var cadena = $('input.6').val();
		formularioMedida(cadena, '6');
	});
	$('input.8').on('keyup change blur', function() {
		var cadena = $('input.8').val();
		formularioMedida(cadena, '8');
	});
	$('input.10').on('keyup change blur', function() {
		var cadena = $('input.10').val();
		formularioMedida(cadena, '10');
	});
	$('input.12').on('keyup change blur', function() {
		var cadena = $('input.12').val();
		formularioMedida(cadena, '12');
	});
	$('input.16').on('keyup change blur', function() {
		var cadena = $('input.16').val();
		formularioMedida(cadena, '16');
	});
	$('input.20').on('keyup change blur', function() {
		var cadena = $('input.20').val();
		formularioMedida(cadena, '20');
	});
	$('input.25').on('keyup change blur', function() {
		var cadena = $('input.25').val();
		formularioMedida(cadena, '25');
	});
	$('input.32').on('keyup change blur', function() {
		var cadena = $('input.32').val();
		formularioMedida(cadena, '32');
	});
	$('input.oc').on('keyup change blur', function() {
		var cadena = $('input.oc').val();
		formulario(cadena, 'oc');
	});
	// Formato
	/*
	$('input.formato').on('keyup change blur', function() {
		var cadena = $('input.formato').val();
		formularioDescrip(cadena, 'formato');
	});
	$('input.posicionF').on('keyup change blur', function() {
		var cadena = $('input.posicionF').val();
		formularioTelefono(cadena, 'posicionF');
	});
	$('input.lados').on('keyup change blur', function() {
		var cadena = $('input.lados').val();
		formularioTelefono(cadena, 'lados');
	});
	
	$('input.cant_doblados').on('keyup change blur', function() {
		var cadena = $('input.cant_doblados').val();
		formularioCantDoblados(cadena, 'cant_doblados');
	});
	*/
	
	// Items
	$('input.estructura').on('keyup change blur', function() {
		var cadena = $('input.estructura').val();
		formularioDescrip(cadena, 'estructura');
	});
	$('input.posicion').on('keyup change blur', function() {
		var cadena = $('input.posicion').val();
		formulario(cadena, 'posicion');
	});
	$('input.cantidad').on('keyup change blur', function() {
		var cadena = $('input.cantidad').val();
		formularioTelefono(cadena, 'cantidad');
	});
	$('input.observaciones').on('keyup change blur', function() {
		var cadena = $('input.observaciones').val();
		formularioDescrip(cadena, 'observaciones');
	});
});
// FUNCION FORMULARIO
function formulario(cadena, input) {
	var errorFormulario = "<p value='error' id='errorFormulario' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorFormulario').html() == null) {
			$('input.' + input).after(errorFormulario);
		}
	} else {
		$('p#errorFormulario').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioCant_lados(cadena, input) {
	var errorFormulario = "<p value='error' id='errorFormulario' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorFormulario').html() == null) {
			$('input.' + input).after(errorFormulario);
		}
	} else {
		$('p#errorFormulario').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioD(cadena, input) {
	var errorFormularioD = "<p value='error' id='errorFormularioD' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorFormularioD').html() == null) {
			$('input.' + input).after(errorFormularioD);
		}
	} else {
		$('p#errorFormularioD').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioCP(cadena, input) {
	var errorFormularioCP = "<p value='error' id='errorFormularioCP' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorFormularioCP').html() == null) {
			$('input.' + input).after(errorFormularioCP);
		}
	} else {
		$('p#errorFormularioCP').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioDF(cadena, input) {
	var errorFormularioDF = "<p value='error' id='errorFormularioDF' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorFormularioDF').html() == null) {
			$('input.' + input).after(errorFormularioDF);
		}
	} else {
		$('p#errorFormularioDF').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioDescrip(cadena, input) {
	var errorDesPed = "<p value='error' id='errorDesPed' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorDesPed').html() == null) {
			$('input.' + input).after(errorDesPed);
		}
	} else {
		$('p#errorDesPed').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioMedida(cadena, input) {
	if (input == "4") {
		input = "4,2"
	}
	if (cadena == "4,2") {
		input = "4,2";
	}
	if (cadena == "4.2") {
		cadena = "4,2";
		input = "4,2";
	}
	/*
	 * if (cadena != input) { $('input.' + input).removeClass('valido');
	 * $('input.' + input).addClass('invalido'); } else { $('input.' +
	 * input).removeClass('invalido'); $('input.' + input).addClass('valido'); }
	 */
}
function formularioNombreFantasia(cadena, input) {
	var errorNombreFantasia = "<p value='error' id='errorNombreFantasia' style='color:red;'>El campo debe estar completo. </p>";
	if (cadena == "" || cadena == null || cadena.length == 0
			|| cadena.length > 28) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorNombreFantasia').html() == null) {
			$('input.' + input).after(errorNombreFantasia);
		}
	} else {
		$('p#errorNombreFantasia').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
}
function formularioTelefono(cadena, input) {
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
function formularioTelefonoCliente(cadena, input) {
	var errorTelefonoCliente = "<p value='error' id='errorTelefonoCliente' style='color:red;'>El campo debe tener solo numeros (se aceptan guiones, barras o puntos).</p>";
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorTelefonoCliente').html() == null) {
			$('input.' + input).after(errorTelefonoCliente);
		}
	} else {
		$('p#errorTelefonoCliente').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
				if ($('p#errorTelefonoCliente').html() == null) {
					$('input.' + input).after(errorTelefonoCliente);
				}
			}
		}
	}
}
function formularioCodigo(cadena, input) {
	var errorCodigoCliente = "<p value='error' id='errorCodigoCliente' style='color:red;'>El campo debe tener 3 caracteres, sin puntos ni barras. </p>";
	if (cadena.length != 3) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorCodigoCliente').html() == null) {
			$('input.' + input).after(errorCodigoCliente);
		}
	} else {
		$('p#errorCodigoCliente').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	for (var i = 0; i < cadena.length; i++) {
		if (cadena.charAt(i) == "/" || cadena.charAt(i) == ".") {
			$('input.' + input).removeClass('valido');
			$('input.' + input).addClass('invalido');
			if ($('p#errorCodigoCliente').html() == null) {
				$('input.' + input).after(errorCodigoCliente);
			}
		}
	}
}
function formularioCodigoObra(cadena, input) {
	var errorCodigoObra = "<p value='error' id='errorCodigoObra' style='color:red;'>El campo debe tener 2 caracteres, sin puntos ni barras. </p>";
	if (cadena.length != 2) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorCodigoObra').html() == null) {
			$('input.' + input).after(errorCodigoObra);
		}
	} else {
		$('p#errorCodigoObra').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	for (var i = 0; i < cadena.length; i++) {
		if (cadena.charAt(i) == "/" || cadena.charAt(i) == ".") {
			$('input.' + input).removeClass('valido');
			$('input.' + input).addClass('invalido');
			if ($('p#errorCodigoObra').html() == null) {
				$('input.' + input).after(errorCodigoObra);
			}
		}
	}
}
function formularioCUIL(cadena, input) {
	var errorCUILCliente = "<p value='error' id='errorCUILCliente' style='color:red;'>El campo debe tener 11 caracteres numericos.</p>";
	if (cadena.length != 11) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
		if ($('p#errorCUILCliente').html() == null) {
			$('input.' + input).after(errorCUILCliente);
		}
	} else {
		$('p#errorCUILCliente').remove();
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/-."
	for (var i = 0; i < cadena.length; i++) {
		for (var x = 0; x < letras.length; x++) {
			if (cadena.charAt(i) == letras.charAt(x)) {
				$('input.' + input).removeClass('valido');
				$('input.' + input).addClass('invalido');
				if ($('p#errorCUILCliente').html() == null) {
					$('input.' + input).after(errorCUILCliente);
				}
			}
		}
	}
}

// FORMULARIO DE REGISTRO DE USUARIOS
$(document).ready(function() {
	$('input.nombre').on('keyup change blur', function() {
		var cadena = $('input.nombre').val();
		formularioA(cadena, 'nombre');
	});
	$('input.apellido').on('keyup change blur', function() {
		var cadena = $('input.apellido').val();
		formularioA(cadena, 'apellido');
	});
	$('input.tipo').on('keyup change blur', function() {
		var cadena = $('input.tipo').val();
		formularioA(cadena, 'tipo');
	});
	$('input.correoelectronico').on('keyup change blur', function() {
		var cadena = $('input.correoelectronico').val();
		mail(cadena, 'correoelectronico');
	});
	$('input.contrasenia').on('keyup change blur', function() {
		var cadena = $('input.contrasenia').val();
		formularioA(cadena, 'contrasenia');
	});
	$('input.recontrasenia').on('keyup change blur', function() {
		var cadena1 = $('input.contrasenia').val();
		var cadena = $('input.recontrasenia').val();
		if (cadena == cadena1 && cadena1.length != 0 && cadena.length != 0) {
			$('input.recontrasenia').removeClass('invalido');
			$('input.recontrasenia').addClass('valido');
		} else {
			$('input.recontrasenia').removeClass('valido');
			$('input.recontrasenia').addClass('invalido');
		}
	});
});

// FUNCION FORMULARIO
function formularioA(cadena, input) {
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
	} else {
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	for (var i = 0; i < cadena.length; i++) {
		if (cadena.charAt(i) == "/" || cadena.charAt(i) == ".") {
			$('input.' + input).removeClass('valido');
			$('input.' + input).addClass('invalido');
		}
	}
}
function mail(cadena, input) {
	if (cadena == "" || cadena == null || cadena.legth == 0) {
		$('input.' + input).removeClass('valido');
		$('input.' + input).addClass('invalido');
	} else {
		$('input.' + input).removeClass('invalido');
		$('input.' + input).addClass('valido');
	}
	for (var i = 0; i < cadena.length; i++) {
		if (cadena.charAt(i) == " ") {
			$('input.' + input).removeClass('valido');
			$('input.' + input).addClass('invalido');
		}
	}
}

// ESTRUCTURA
$(document)
		.ready(
				function() {
					$("#button-blue")
							.click(
									function() {
										$("#errEst").css({
											"display" : "none"
										});
										$("#errObs").css({
											"display" : "none"
										});
										$("#input_est")
												.addClass(
														'validate[required,custom[email]] feedback-input estructura');
										$("#input_est").css({
											'border' : '2px solid #9a9a9a'
										});
										$("#input_obs")
												.addClass(
														'validate[required,custom[email]] feedback-input estructura');
										$("#input_obs").css({
											'border' : '2px solid #9a9a9a'
										});
										if ($("#input_est").val().length > 20) {
											$("#input_est").css({
												"border" : "2px solid red"
											});
											$("#errEst").css({
												"display" : "block"
											});

											if ($("#input_obs").val().length > 20) {
												$("#input_obs").css({
													"border" : "2px solid red"
												});
												$("#errObs").css({
													"display" : "block"
												});
											}
										} else if ($("#input_obs").val().length > 20) {
											$("#input_obs").css({
												"border" : "2px solid red"
											});
											$("#errObs").css({
												"display" : "block"
											});
										}

										else {
											$("#nuevoItem").submit();
										}
									});
				});

// CANTIDAD DE LADOS EN FORMATOS
// $(document).ready(function() {
// $('input.formato').on('keyup change',function() {
// var posicion = $('input.formato').val();
// $.get('/lados' + '/' + posicion, function(data) {
// var cantidad_lados = data;
// for (var x = 1 ; x <= cantidad_lados ; x++){
// $('input.' + x).attr('type', 'text');
// }
// });
// });
// });
