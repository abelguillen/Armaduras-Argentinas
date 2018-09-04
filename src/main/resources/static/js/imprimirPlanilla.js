$(document).ready(function(){
	var anchoFila = $("#filaCabIP").width();
	
	var anchoItem = document.getElementById("anchoItem").offsetWidth;
	var anchoPosicion = document.getElementById("anchoPosicion").offsetWidth;
	var totColLogo = anchoItem + anchoPosicion + "px";
	console.log(totColLogo);
	
	var anchoAcero = document.getElementById("anchoAcero").offsetWidth;
	var anchoDiametro = document.getElementById("anchoDiametro").offsetWidth;
	var totColCliente = anchoAcero + anchoDiametro + "px";
	console.log(totColCliente);
	
	var anchoCantidad = document.getElementById("anchoCantidad").offsetWidth;
	var anchoEstructura = document.getElementById("anchoEstructura").offsetWidth;
	var anchoDibujo = document.getElementById("anchoDibujo").offsetWidth;
	var anchoA = document.getElementById("anchoA").offsetWidth;
	var totColObra = anchoCantidad + anchoEstructura + anchoDibujo + anchoA + "px";
	console.log(totColObra);
	
	var anchoB = document.getElementById("anchoB").offsetWidth;
	var anchoC = document.getElementById("anchoC").offsetWidth;
	var anchoD = document.getElementById("anchoD").offsetWidth;
	var anchoE = document.getElementById("anchoE").offsetWidth;
	var anchoF = document.getElementById("anchoF").offsetWidth;
	var anchoG = document.getElementById("anchoG").offsetWidth;
	var anchoH = document.getElementById("anchoH").offsetWidth;
	var anchoH1 = document.getElementById("anchoH1").offsetWidth;
	var anchoH2 = document.getElementById("anchoH2").offsetWidth;
	var anchoLParcial = document.getElementById("anchoLParcial").offsetWidth;
	var anchoLTotal = document.getElementById("anchoLTotal").offsetWidth;
	var anchoPeso = document.getElementById("anchoPeso").offsetWidth;
	var anchoObservaciones = document.getElementById("anchoObservaciones").offsetWidth;
	var totColDescrObs = anchoB + anchoC + anchoD + anchoE + anchoF + anchoG + anchoH + anchoH1 + anchoH2 + anchoLParcial + anchoLTotal + anchoPeso + anchoObservaciones + "px";
	console.log(totColDescrObs);
	
	document.getElementById("logo").style.width = totColLogo;
	document.getElementById("colCliente").style.width = totColCliente;
	document.getElementById("colObra").style.width = totColObra;
	document.getElementById("colDescObs").style.width = totColDescrObs;
	
});