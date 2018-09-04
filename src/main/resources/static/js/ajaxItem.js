//$(document).ready(function() {
//	var posicion = $('input#posicion').val();
//	$.get('/formato' + '/' + posicion, function(data) {
//		alert("Data: " + data);
//		var string = $('p').text();
//		$('input#FORMATO').val(string);
//	});
//});
//AJAX ITEM
$(document).ready(function() {
	$('input#FORMATO').change(function(){
		var posicion = $('input#FORMATO').val();
		$.get('/formato' + '/' + posicion, function( data ) {
			var nueva = "";
			for (var i = 6; i< data.length -7; i++) {
				var caracter = data.charAt(i);
				nueva+=data.charAt(i);
			}
			$('input#dibujo').val(nueva);
		});
	});
});
//TABLAS DE ITEMS / PEDIDOS
$(document).ready(function(){
    $('#myTable').DataTable();
});









//$(document).ready(function() {
// if($('input#posicion').val() != null){
// var posicion = $('input#posicion').val();
// (function (posicion){
// $.ajax({url: '/formato' + "/" + posicion,
// success: function (resultado){
// $('input#FORMATO').val(resultado);
// },
// error: (function (resultado){
// alert("La posicion que pusiste no tiene ningun formato!");
// })
// });
// });
// };
//});
// $('button').click(
// function()
// {$.ajax({url: '/cambiarDireccion/1',
// success: function(resultado){
// direccionAnterior = $('#direccion').text();
// $('#direccion').text(direccionAnterior + 5);
// },
// error: function(resultado){
//			
// //var obj = jQuery.parseJSON( resultado );
// //alert( obj.name === "John" )
//				
// //alert(resultado.firstName)
// alert("DISCULPA NO FUNCINA EL SERVIDOR; NUESTROS EXPERTOS HAN SIDO
// ALERTADOS");
// }
//		
// });
//		
// });
//	
// } );
