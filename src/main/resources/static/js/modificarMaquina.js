// MAQUINA

// Consume JSON del WebService y lo setea en el modal de modificarMaquina
$(document).ready(function() {
	$('button#modificarMaquina').click(function() {
		var idMaquina = $('tr.visto p#idMaquina').text();
		$.ajax({
			url: '/maquina/buscar/' + idMaquina,
			method: "POST",
			success: function(respuesta) {
				var id = respuesta.id;
				var clasificacion = respuesta.clasificacion;
				var marca = respuesta.marca;
				var modelo = respuesta.modelo;
				var tipoMP = respuesta.tipoMP;
				var diametroMin = respuesta.diametroMin;
				var diametroMax = respuesta.diametroMax;
				var merma = respuesta.merma;
				
				console.log(id);
				console.log(clasificacion);
				console.log(marca);
				
				$("input#id").val(id);
				$("#clasificacion option[value="+clasificacion+"]").attr("selected",true);
				$("input#marca").val(marca);
				$("input#modelo").val(modelo);
				$("#tipoMP option[value="+tipoMP+"]").attr("selected",true);
				$("#diametroMin option[value="+diametroMin+"]").attr("selected",true);
				$("#diametroMax option[value="+diametroMax+"]").attr("selected",true);
				$("input#merma").val(merma);
				$('#formMaquina').attr('action', '/maquina/modificar/' + id);
			}
		});
	});
});