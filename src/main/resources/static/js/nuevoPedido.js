// Validacion de diametro segun el acero en nuevo item
$(document).ready(function() {
	$('select#tipoPedido').on('change', function() {
		var tipoPedido = $(this).val();
		if(tipoPedido == 'Armado') {
			$('input#elementos').css('display', 'block');
			$('input#elementos').prop('required', true);
		}
		else {
			$('input#elementos').css('display', 'none');
			$('input#elementos').prop('required', false);
		}
	});
});