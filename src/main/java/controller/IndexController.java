package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.CliEnOb;
import model.Cliente;
import model.CodigoMP;
import model.Fecha;
import model.Formato;
import model.IngresoMP;
import model.IngresoMP_temp;
import model.IngresoRemitos;
import model.Item;
import model.Motivo;
import model.Obra;
import model.Pedido;
import model.PedidoPSS;
import model.Remito;
import model.RemitoPedido;
import model.TablaCalculos;
import model.Usuario;
import model.UsuarioAndroid;
import model.pedFormatoRemito;
import model.Stock;

@Controller
public class IndexController {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public IndexController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Funcion que verifica si hay letras en un String
	public boolean tieneLetra(String posi) {
		boolean tieneLetra = false;
		for (int i = 0; i < posi.length(); i++) {
			if (posi.charAt(i) == 'a' || posi.charAt(i) == 'b' || posi.charAt(i) == 'c' || posi.charAt(i) == 'd'
					|| posi.charAt(i) == 'e' || posi.charAt(i) == 'f' || posi.charAt(i) == 'g' || posi.charAt(i) == 'h'
					|| posi.charAt(i) == 'i' || posi.charAt(i) == 'j' || posi.charAt(i) == 'k' || posi.charAt(i) == 'l'
					|| posi.charAt(i) == 'm' || posi.charAt(i) == 'n' || posi.charAt(i) == 'o' || posi.charAt(i) == 'p'
					|| posi.charAt(i) == 'q' || posi.charAt(i) == 'r' || posi.charAt(i) == 's' || posi.charAt(i) == 't'
					|| posi.charAt(i) == 'u' || posi.charAt(i) == 'v' || posi.charAt(i) == 'w' || posi.charAt(i) == 'x'
					|| posi.charAt(i) == 'y' || posi.charAt(i) == 'z' || posi.charAt(i) == 'A' || posi.charAt(i) == 'B'
					|| posi.charAt(i) == 'C' || posi.charAt(i) == 'D' || posi.charAt(i) == 'E' || posi.charAt(i) == 'F'
					|| posi.charAt(i) == 'G' || posi.charAt(i) == 'H' || posi.charAt(i) == 'I' || posi.charAt(i) == 'J'
					|| posi.charAt(i) == 'K' || posi.charAt(i) == 'L' || posi.charAt(i) == 'M' || posi.charAt(i) == 'N'
					|| posi.charAt(i) == 'O' || posi.charAt(i) == 'P' || posi.charAt(i) == 'Q' || posi.charAt(i) == 'R'
					|| posi.charAt(i) == 'S' || posi.charAt(i) == 'T' || posi.charAt(i) == 'U' || posi.charAt(i) == 'V'
					|| posi.charAt(i) == 'W' || posi.charAt(i) == 'X' || posi.charAt(i) == 'Y' || posi.charAt(i) == 'Z'
					|| posi.charAt(i) == '-' || posi.charAt(i) == '/' || posi.charAt(i) == 'ñ'
					|| posi.charAt(i) == 'Ñ') {
				tieneLetra = true;
			}
		}
		return tieneLetra;
	}

	// INGRESO MP
	@RequestMapping(value = "/materiaprima", method = RequestMethod.GET)
	public String materiaPrima(Model template) {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "materiaprima";

		}
	}

	// ADMINISTRACION
	@RequestMapping(value = "/administracion", method = RequestMethod.GET)
	public String administracion(Model template) {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "administracion";
		}
		
	}
	/*
	@RequestMapping(value = "/maquina/alta", method = RequestMethod.GET)
    public String altaMaquina(Model template) {
		
		return "altaMaquina";
    }
	*/
	// CONTAR STOCK
	// Se utiliza solamente a la hora de contar el stock de la planta
	@RequestMapping(value = "/contarStock", method = RequestMethod.GET)
	public String contarStock(Model template) {

		ArrayList<IngresoMP> listaDeIngresosMP = traerIngresosMP();

		String totKilos = " ";
		double totKilosD = 0;

		/*
		 * 0006079436 0006079438 0006079439 0006079498 0006079499 0006108999 0006079500
		 * 0006079501 0006079510 0006079525 0006079530 0006079531 0006108996 0006079532
		 * 0006079560 0006155777 0008010490 0008015949
		 */

		String var = "0008015949";

		for (IngresoMP imp : listaDeIngresosMP) {
			String material = imp.getMaterial();
			String cantidad = imp.getCantidad();
			cantidad = cantidad.replace(',', '.');
			double cantidadD = Double.parseDouble(cantidad);
			if (material.equals(var)) {
				totKilosD += cantidadD;
			}
		}

		totKilos = totKilosD + "";
		this.jdbcTemplate.update("UPDATE stock SET KGTeorico=? WHERE CodMat=?;", totKilos, var);

		return "materiaprima";
	}

	// CODIGO MP
	@RequestMapping(value = "/codigosmp", method = RequestMethod.GET)
	public String codigosMP(Model template) {

		return "codigosMP";
	}

	// INGRESO MP
	@RequestMapping(value = "/ingresomp", method = RequestMethod.GET)
	public String ingresoMP(Model template) {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "ingresoMP";

		}
	}

	// INGRESO MP - Manual - GET
	@RequestMapping(value = "/materiaprima/ingresomp/ingresar", method = RequestMethod.GET)
	public String ingresoMPManualGet(Model template) {

		ArrayList<CodigoMP> listaDeCodigosMP = traerCodigosMP();
		template.addAttribute("listaDeCodigosMP", listaDeCodigosMP);

		return "nuevoIngresoMPManual";
	}

	// INGRESO MP - Manual - POST
	@RequestMapping(value = "/materiaprima/ingresomp/ingresar", method = RequestMethod.POST)
	public String ingresoMPManualPost(@RequestParam(value = "referencia") String referencia,
			@RequestParam(value = "material") String material, @RequestParam(value = "cantidadMP") String cantidadMP,
			@RequestParam(value = "lote") String lote, @RequestParam(value = "destinatario") String destinatario,
			Model template) {

		String descripcion = null;
		String estado = "EN TRANSITO";

		ArrayList<CodigoMP> listaDeCodigosMP = traerCodigosMP();
		template.addAttribute("listaDeCodigosMP", listaDeCodigosMP);

		// Se setea la descripcion correspondiente al codigo de material
		for (CodigoMP cmp : listaDeCodigosMP) {
			String codSap = cmp.getCodSap();
			String descrip = cmp.getDescripcion();
			if (codSap.equals(material)) {
				descripcion = descrip;
			}
		}

		this.jdbcTemplate
				.update("INSERT INTO ingresomp(Referencia, Material, Descripcion, Cantidad, Lote, Destinatario) "
						+ "VALUES (?,?,?,?,?,?);", referencia, material, descripcion, cantidadMP, lote, destinatario);

		// EVALUA SI EL NRO DE REFERENCIA ESTA INGRESADO. DE LO CONTRARIO, LO INGRESA.
		ArrayList<IngresoRemitos> listaDeIngresosDeRemitos = traerIngresosDeRemitos();
		boolean referenciaRep = tieneNroRefRepetido(listaDeIngresosDeRemitos, referencia);
		if (referenciaRep == false) {
			this.jdbcTemplate.update("INSERT INTO ingresoremitos (Referencia, Estado) VALUES(?,?);", referencia,
					estado);
		}

		return "redirect:" + "/materiaprima/ingresomp/ingresar";
	}

	// INGRESO MP - TXT - GET
	@RequestMapping(value = "/materiaprima/ingresomp/ingresarTXT", method = RequestMethod.GET)
	public String ingresoMPTXTGet(Model template) {

		return "nuevoIngresoMPTXT";
	}

	// INGRESO MP - TXT - POST
	@RequestMapping(value = "/materiaprima/ingresomp/ingresarTXT", method = RequestMethod.POST)
	public String ingresoMPTXTPost(@RequestParam(value = "archivoTXT") MultipartFile txt, Model template)
			throws IOException {
		if (txt.getBytes().length != 0) {
			String content = new String(txt.getBytes());
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(content);
			String estado = "EN TRANSITO";
			// ESTO ES PARA EMPEZAR DIRECTAMENTE EN EL CONTENIDO
			scanner.nextLine();
			scanner.nextLine();
			scanner.nextLine();
			// ANALIZAMOS LINEA POR LINEA
			String referencia = "";
			while (scanner.hasNext()) {
				String linea = scanner.nextLine();
				String material = "", descripcion = "", cantidad = "", umb = "", lote = "", destinatario = "";
				if (linea.charAt(0) != '-') {
					IngresoMP imp = new IngresoMP();
					String[] lineaSeparada = imp.separarPorPipe(linea);

					referencia = lineaSeparada[0].trim();
					material = "000" + lineaSeparada[1].trim();
					descripcion = lineaSeparada[2].trim();
					cantidad = lineaSeparada[3].substring(0, lineaSeparada[3].length() - 1).trim();
					cantidad = cantidad.replace(",", "");
					umb = lineaSeparada[4].trim();
					lote = lineaSeparada[5].trim();
					destinatario = lineaSeparada[6].trim();
					if (destinatario.equals("0005161556")) {
						destinatario = "PROPIO";
					} else {
						destinatario = "TERCEROS";
					}
				}

				// EVALUA SI EL NRO DE REFERENCIA ESTA INGRESADO.
				ArrayList<IngresoRemitos> listaDeIngresosDeRemitos = traerIngresosDeRemitos();
				boolean referenciaRep = tieneNroRefRepetido(listaDeIngresosDeRemitos, referencia);
				if (referenciaRep) {
					String textoError = "Ha ocurrido un error. El archivo que desea ingresar contiene un numero de"
							+ " referencia (" + referencia
							+ ") ya existente. Por favor revise el archivo y vuelva a intentar.";
					template.addAttribute("nroRefRep", true);
					template.addAttribute("textoError", textoError);
					return "resultados";
				}

				// TODO Hacer validaciones
				if (!referencia.equals("") && !material.equals("") && !descripcion.equals("") && !cantidad.equals("")
						&& !umb.equals("") && !lote.equals("") && !destinatario.equals("")) {
					this.jdbcTemplate.update(
							"INSERT INTO ingresomp (Referencia, Material, Descripcion, Cantidad, UMB, Lote, "
									+ "Destinatario) VALUES(?, ?, ?, ?, ?, ?, ?);",
							referencia, material, descripcion, cantidad, umb, lote, destinatario);
				}
			}
			// INSERTA EN LA TABLA ingresoremitos
			this.jdbcTemplate.update("INSERT INTO ingresoremitos (Referencia, Estado) VALUES(?,?);", referencia,
					estado);

			scanner.close();
			return "redirect:" + "/ingresomp";
		} else {
			template.addAttribute("cargoArchivo", false);
			return "resultados";
		}
	}

	// LISTA DE INGRESOS MP - DETALLE
	@RequestMapping("/materiaprima/ingresomp/listaDeIngresosMP/{id}")
	public String ingresoMPDetalle(@PathVariable(value = "id") int idIngresoRemitos, Model template) {

		String referencia = "";
		ArrayList<IngresoRemitos> listaDeIngresosDeRemitos = traerIngresosDeRemitos();
		ArrayList<IngresoMP> listaDeIngresosMP = traerIngresosMP();
		ArrayList<IngresoMP> toRemove = new ArrayList<IngresoMP>();

		// RECORRE EL ARRAY PARA ENCONTRAR EL NRO DE REFERENCIA
		for (IngresoRemitos ir : listaDeIngresosDeRemitos) {
			int id = (int) ir.getID();
			String ref = ir.getReferencia();
			if (id == idIngresoRemitos) {
				referencia = ref;
			}
		}

		// DEJA EN EL ARRAY AQUELLOS INGRESOS QUE COINCIDAN CON EL NRO DE REFERENCIA
		for (IngresoMP imp : listaDeIngresosMP) {
			String ref = imp.getReferencia();
			if (!ref.equals(referencia)) {
				toRemove.add(imp);
			}
		}

		listaDeIngresosMP.removeAll(toRemove);

		template.addAttribute("listaDeIngresosMP", listaDeIngresosMP);

		return "listaDeIngresosMPDetalle";
	}

	// LISTA DE INGRESOS MP - EN TRANSITO
	@RequestMapping(value = "/materiaprima/ingresomp/listaDeIngresosMP", method = RequestMethod.GET)
	public String listaDeIngresosMP(Model template) {

		ArrayList<IngresoRemitos> listaDeIngresosDeRemitos = traerIngresosDeRemitos();

		ArrayList<IngresoRemitos> toRemove = new ArrayList<IngresoRemitos>();

		// DEJA EN EL ARRAY AQUELLOS INGRESOS QUE ESTEN EN TRANSITO
		for (IngresoRemitos ir : listaDeIngresosDeRemitos) {
			String estado = ir.getEstado();
			if (!estado.equals("EN TRANSITO")) {
				toRemove.add(ir);
			}
		}

		listaDeIngresosDeRemitos.removeAll(toRemove);
		template.addAttribute("listaDeIngresosDeRemitos", listaDeIngresosDeRemitos);

		template.addAttribute("fecha", "fecha");

		return "listaDeIngresosDeRemitos";
	}

	// LISTA DE INGRESOS MP - HISTORICO
	@RequestMapping(value = "/materiaprima/ingresomp/listaDeIngresosMPHist", method = RequestMethod.GET)
	public String listaDeIngresosMPHist(Model template) {

		ArrayList<IngresoRemitos> listaDeIngresosDeRemitos = traerIngresosDeRemitos();

		ArrayList<IngresoRemitos> toRemove = new ArrayList<IngresoRemitos>();

		// DEJA EN EL ARRAY AQUELLOS INGRESOS QUE ESTEN EN TRANSITO
		for (IngresoRemitos ir : listaDeIngresosDeRemitos) {
			String estado = ir.getEstado();
			if (estado.equals("EN TRANSITO")) {
				toRemove.add(ir);
			}
		}

		listaDeIngresosDeRemitos.removeAll(toRemove);
		template.addAttribute("listaDeIngresosDeRemitos", listaDeIngresosDeRemitos);

		template.addAttribute("fecha", "fecha");

		return "listaDeIngresosDeRemitosHist";
	}

	// ELIMINAR INGRESO MP
	@RequestMapping(value = "/materiaprima/ingresomp/eliminar/{id}", method = RequestMethod.GET)
	public String eliminarIngresoMP(@PathVariable(value = "id") int idIngresoMP, Model template) {
		jdbcTemplate.update("DELETE FROM ingresomp WHERE ID = ?;", idIngresoMP);
		return "redirect:" + "/materiaprima/ingresomp/listaDeIngresosMP";
	}

	// STOCK
	@RequestMapping(value = "/stock", method = RequestMethod.GET)
	public String Stock(Model template) {

		ArrayList<Stock> listaStock = traerStock();
		template.addAttribute("listaStock", listaStock);

		return "stock";
	}

	// STOCK
	@RequestMapping(value = "/stock/detalle/{codMat}", method = RequestMethod.GET)
	public String Stock(@PathVariable(value = "codMat") String codMat, Model template) {

		// Se eliminan los 000 de adelante del codigo de material
		// codMat = codMat.substring(3, codMat.length());

		ArrayList<IngresoRemitos> listaDeIngresosDeRemitos = traerIngresosDeRemitos();
		ArrayList<IngresoMP> listaDeIngresosMP = traerIngresosMP();
		ArrayList<IngresoMP> toRemoveIngresosMP = new ArrayList<IngresoMP>();

		// DEJO EN listaDeIngresosMP SOLO LOS CODIGOS DE MP QUE SE SOLICITAN
		for (IngresoMP imp : listaDeIngresosMP) {
			String material = imp.getMaterial();
			if (!material.equals(codMat)) {
				// Agrego lo que quiero borrar al siguiente Array
				toRemoveIngresosMP.add(imp);
			}
		}
		listaDeIngresosMP.removeAll(toRemoveIngresosMP);
		// Vacio el Array
		toRemoveIngresosMP.clear();

		// DEJO EN listaDeIngresosMP SOLO LOS REGISTROS HISTORICOS
		for (IngresoMP imp : listaDeIngresosMP) {
			String referencia = imp.getReferencia();
			for (IngresoRemitos ir : listaDeIngresosDeRemitos) {
				String ref = ir.getReferencia();
				String estado = ir.getEstado();
				if (ref.equals(referencia) && estado.equals("EN TRANSITO")) {
					toRemoveIngresosMP.add(imp);
				}
			}
		}
		listaDeIngresosMP.removeAll(toRemoveIngresosMP);

		template.addAttribute("listaDeIngresosMP", listaDeIngresosMP);

		return "detalleStock";
	}

	// NUEVO CODIGO MP - GET
	@RequestMapping(value = "/materiaprima/codigos/nuevoCodigo", method = RequestMethod.GET)
	public String nuevoCodigoGet(Model template) {

		return "nuevoCodigo";
	}

	// NUEVO CODIGO MP - POST
	@RequestMapping(value = "/materiaprima/codigos/nuevoCodigo", method = RequestMethod.POST)
	public String nuevoCodigoPost(@RequestParam(value = "numSAP") String numSAP,
			@RequestParam(value = "familia") String familia,
			@RequestParam(value = "descripcionCod") String descripcionCod,
			@RequestParam(value = "tipoMaterial") String tipoMaterial, Model template) {

		boolean flagNumSap = tieneLetra(numSAP);
		if (numSAP.length() == 7 && flagNumSap == false) {
			if (descripcionCod.length() != 0) {
				this.jdbcTemplate.update(
						"INSERT INTO codigomp(CodSAP, Familia, Descripcion, TipoMaterial) VALUES (?,?,?,?);", numSAP,
						familia, descripcionCod, tipoMaterial);
				this.jdbcTemplate.update(
						"INSERT INTO stock(Familia, CodMat, Descripcion, TipoMaterial) VALUES (?,?,?,?);", familia,
						numSAP, descripcionCod, tipoMaterial);
				return "redirect:" + "/materiaprima/codigos/listaDeCodigosMP";
			}
		}
		template.addAttribute("camposObligatorios", false);
		return "resultados";
	}

	// LISTA DE CODIGOS MP
	@RequestMapping(value = "/materiaprima/codigos/listaDeCodigosMP", method = RequestMethod.GET)
	public String listaDeCodigosMP(Model template) {

		// TRAE TODOS LOS CODIGOS
		ArrayList<CodigoMP> listaDeCodigosMP = traerCodigosMP();
		template.addAttribute("listaDeCodigosMP", listaDeCodigosMP);

		// SI NO HAY CODIGOS REGISTRADOS
		if (listaDeCodigosMP.size() == 0) {
			template.addAttribute("hayCodigosMP", false);
			return "resultados";
		}
		// SI HAY CODIGOS REGISTRADOS
		else {
			return "listaDeCodigosMP";
		}
	}

	// ELIMINAR CODIGO MP
	@RequestMapping(value = "/materiaprima/codigos/eliminar/{id}", method = RequestMethod.GET)
	public String eliminarCodigoMP(@PathVariable(value = "id") int idCodigoMP, Model template) {
		jdbcTemplate.update("DELETE FROM codigomp WHERE ID = ?;", idCodigoMP);
		return "redirect:" + "/materiaprima/codigos/listaDeCodigosMP";
	}

	// MODIFICAR CODIGO MP - GET
	@RequestMapping(value = "/materiaprima/codigos/modificar/{id}", method = RequestMethod.GET)
	public String modificarCodigoMPGet(@PathVariable(value = "id") int idCodigoMP, Model template) {

		// TRAE LOS CODIGOS
		SqlRowSet rowCodigosMP;
		rowCodigosMP = jdbcTemplate.queryForRowSet("SELECT * FROM codigomp WHERE ID = ?;", idCodigoMP);
		rowCodigosMP.next();

		String numSAP = rowCodigosMP.getString("CodSAP");
		String familia = rowCodigosMP.getString("Familia");
		String descripcionCod = rowCodigosMP.getString("Descripcion");
		String tipoMaterial = rowCodigosMP.getString("TipoMaterial");

		template.addAttribute("numSAP", numSAP);
		template.addAttribute("familiaop", familia);
		template.addAttribute("descripcionCod", descripcionCod);
		template.addAttribute("tipoMaterialop", tipoMaterial);
		return "modificarCodigoMP";
	}

	// MODIFICAR CODIGO MP - POST
	@RequestMapping(value = "/materiaprima/codigos/modificar/{id}", method = RequestMethod.POST)
	public String modificarCodigoMPPost(@PathVariable(value = "id") int idCodigoMP,
			@RequestParam(value = "numSAP") String numSAP, @RequestParam(value = "familia") String familia,
			@RequestParam(value = "descripcionCod") String descripcionCod,
			@RequestParam(value = "tipoMaterial") String tipoMaterial, Model template) {

		boolean flagNumSap = tieneLetra(numSAP);
		if (numSAP.length() == 7 && flagNumSap == false) {
			if (descripcionCod.length() != 0) {
				this.jdbcTemplate.update(
						"UPDATE codigomp SET CodSAP=?, Familia=?, Descripcion=?, TipoMaterial=? WHERE ID=?;", numSAP,
						familia, descripcionCod, tipoMaterial, idCodigoMP);
				return "redirect:" + "/ingresomp/listaDeCodigosMP";
			}
		}
		template.addAttribute("camposObligatorios", false);
		return "resultados";
	}

	// USUARIOS

	// PRE INICIO
	// GET
	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String preInicioGET() {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "preInicio";

		}
	}

	// POST
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public String preInicioPOST(@RequestParam(value = "ema") String emailForm,
			@RequestParam(value = "contra") String contraseniaForm, HttpServletRequest request, Model template) {
		if (emailForm.length() != 0 && contraseniaForm.length() != 0) {
			SqlRowSet usuarios;
			usuarios = jdbcTemplate.queryForRowSet("SELECT * FROM usuarios;");

			ArrayList<Usuario> usuario = new ArrayList<Usuario>();
			while (usuarios.next()) {
				long id = usuarios.getInt("id");
				String nombre = usuarios.getString("Nombre");
				String apellido = usuarios.getString("Apellido");
				String confirmacion = usuarios.getString("Confirmacion");
				String correo = usuarios.getString("Correo");
				String contrasenia = usuarios.getString("contrasenia");
				Usuario u = new Usuario(id, nombre, apellido, confirmacion, correo, contrasenia);
				usuario.add(u);
				if (confirmacion.length() == 0) {
					template.addAttribute("cuenta", true);
					return "preInicio";
				}
				if (correo.equals(emailForm) && contrasenia.equals(contraseniaForm)) {
					request.getSession().setAttribute("miVariable", correo);
					return "redirect:" + "/";
				}
			}

		}
		template.addAttribute("email", emailForm);
		template.addAttribute("contrasenia", contraseniaForm);
		template.addAttribute("usuario", true);
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "preInicio";

		}	}

	// REGISTRAR EMPLEADO WEB
	// GET
	@RequestMapping(value = "/administracion/nuevoUsuarioWeb", method = RequestMethod.GET)
	public String registrarseGet(Model template) {
		SqlRowSet tiposQ;
		tiposQ = jdbcTemplate.queryForRowSet("SELECT * FROM tipos;");
		ArrayList<String> tipos = new ArrayList<String>();
		while (tiposQ.next()) {
			String tipo = tiposQ.getString("Tipos");
			tipos.add(tipo);
		}
		template.addAttribute("tipos", tipos);
		return "registrarse";
	}

	// POST
	@RequestMapping(value = "/administracion/nuevoUsuarioWeb", method = RequestMethod.POST)
	public String registrarsePost(@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "apellido") String apellido, @RequestParam(value = "tipo") String tipo,
			@RequestParam(value = "correo") String correo, @RequestParam(value = "contrasenia") String contrasenia,
			@RequestParam(value = "Rcontrasenia") String rContrasenia, Model template) {

		// VERIFICAR DE NO REPETIR CORREOS
		SqlRowSet usuarios;
		usuarios = jdbcTemplate.queryForRowSet("SELECT * FROM usuarios;");
		ArrayList<Usuario> usuario = new ArrayList<Usuario>();
		while (usuarios.next()) {
			long id1 = usuarios.getInt("id");
			String nombre1 = usuarios.getString("Nombre");
			String apellido1 = usuarios.getString("Apellido");
			String confirmacion = usuarios.getString("Confirmacion");
			String correo1 = usuarios.getString("Correo");
			String contrasenia1 = usuarios.getString("contrasenia");
			Usuario u = new Usuario(id1, nombre1, apellido1, confirmacion, correo1, contrasenia1);
			usuario.add(u);
			if (correo1.equals(correo)) {
				template.addAttribute("correo", false);
				return "resultados";
			}
		}
		if (!contrasenia.equals(rContrasenia)) {
			template.addAttribute("contrasenia", false);
			return "resultados";
		}
		if (nombre.matches("([a-z]|[A-Z])") || apellido.matches("([a-z]|[A-Z])") || tipo.matches("([a-z]|[A-Z])")) {
			template.addAttribute("tipoBien", false);
			return "resultados";
		}
		if (nombre.length() != 0 && apellido.length() != 0 && correo.length() != 0 && contrasenia.length() != 0) {
			this.jdbcTemplate.update(
					"INSERT INTO usuarios(Nombre, Apellido, Confirmacion, Correo, Contrasenia) VALUES (?,?,?,?,?);",
					nombre, apellido, "habilitada", correo, contrasenia);
			this.jdbcTemplate.update("INSERT INTO tiposusuarios(Tipo,CORREO_Usuario) VALUES (?,?);", tipo, correo);
			template.addAttribute("ingresar", true);
			return "resultados";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// NUEVO TIPO DE EMPLEADO WEB

	// GET
	@RequestMapping(value = "/usuarios/nuevotipo", method = RequestMethod.GET)
	public String nuevoTipo(Model template) {
		return "nuevoTipo";
	}

	// POS
	@RequestMapping(value = "/usuarios/nuevotipo", method = RequestMethod.POST)
	public String nuevoTipo(@RequestParam(value = "tipo") String tipo, Model template) {
		if (!tipo.matches("([a-z]|[A-Z])")) {
			template.addAttribute("tipoBien", false);
			return "resultados";
		}
		if (tipo.length() != 0) {
			tipo = tipo.toUpperCase();
			this.jdbcTemplate.update("INSERT INTO tipos(Tipos) VALUES (?);", tipo);
			return "redirect:" + "/";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// FIN DE NUEVO TIPO

	// REGISTRAR USUARIO ANDROID
	// GET
	@RequestMapping(value = "/administracion/nuevoUsuarioAndroid", method = RequestMethod.GET)
	public String nuevoUsuarioAndroidGet(Model template) {
		SqlRowSet codigosBD;
		codigosBD = jdbcTemplate.queryForRowSet("SELECT codigo FROM usuariosandroid;");
		ArrayList<String> codigos = new ArrayList<String>();
		while (codigosBD.next()) {
			String codigo = codigosBD.getString("codigo");
			codigos.add(codigo);
		}
		template.addAttribute("codigos", codigos);
		return "registrarseAndroid";
	}

	// POST
	@RequestMapping(value = "/administracion/nuevoUsuarioAndroid", method = RequestMethod.POST)
	public String nuevoUsuarioAndroidPost(@RequestParam(value = "codigo") String codigo,
			@RequestParam(value = "nombre") String nombre, @RequestParam(value = "apellido") String apellido,
			@RequestParam(value = "fechaDeNacimiento") String fechaDeNacimiento,
			@RequestParam(value = "contrasenia") String contrasenia,
			@RequestParam(value = "Rcontrasenia") String rContrasenia, Model template) {

		// VERIFICAR DE NO REPETIR CODIGOS
		SqlRowSet codigosBD;
		codigosBD = jdbcTemplate.queryForRowSet("SELECT codigo FROM usuariosandroid;");
		while (codigosBD.next()) {
			if (codigosBD.getString("codigo").equals(codigo)) {
				template.addAttribute("codigoRepetido", true);
				return "resultados";
			}
		}
		// VALIDAR LARGO DEL CODIGO
		if (codigo.length() != 3) {
			template.addAttribute("codInvalido", true);
			return "resultados";
		}
		// VALIDAR FECHA DE NACIMIENTO
		if (!fechaDeNacimiento.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")) {
			template.addAttribute("fechaValida", false);
			return "resultados";
		}
		// VERIFICAR CONTRASEÑAS
		if (!contrasenia.equals(rContrasenia)) {
			template.addAttribute("contrasenia", false);
			return "resultados";
		}
		// VERIFICAR QUE SOLO CONTEGAN LETRAS EL NOMBRE Y APELLIDO
		if (nombre.matches("([a-z]|[A-Z])") || apellido.matches("([a-z]|[A-Z])")) {
			template.addAttribute("tipoBien", false);
			return "resultados";
		}
		// INSERT CON VERIFICACION DE CAMPOS COMPLETOS
		if (nombre.length() != 0 && apellido.length() != 0 && contrasenia.length() != 0
				&& fechaDeNacimiento.length() != 0) {
			this.jdbcTemplate.update(
					"INSERT INTO usuariosandroid(codigo, nombre, apellido, contraseña, fechaDeNacimiento) VALUES (?,?,?,?,?);",
					codigo, nombre, apellido, contrasenia, fechaDeNacimiento);
			template.addAttribute("ingresar", true);
			return "resultados";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// LISTA DE USUARIOS ANDROID
	@RequestMapping(value = "/administracion/listaDeUsuariosAndroid", method = RequestMethod.GET)
	public String listaDeUsuariosAndroid(Model template) {

		// TRAE A LOS USUARIOS ANDROID
		ArrayList<UsuarioAndroid> listaDeUsuariosAndroid = traerUsuariosAndroid();
		template.addAttribute("listaDeUsuariosAndroid", listaDeUsuariosAndroid);

		// SI NO HAY USUARIOS ANDROID REGISTRADOS
		if (listaDeUsuariosAndroid.size() == 0) {
			template.addAttribute("hayUsuariosAndroid", false);
			return "resultados";
		}
		// SI HAY USUARIOS ANDROID REGISTRADOS
		else {
			return "listaDeUsuariosAndroid";
		}
	}

	// LISTA DE USUARIOS WEB
	@RequestMapping(value = "/administracion/listaDeUsuariosWeb", method = RequestMethod.GET)
	public String listaDeUsuariosWeb(Model template) {

		// TRAE A LOS USUARIOS WEB
		ArrayList<Usuario> listaDeUsuariosWeb = traerUsuariosWeb();
		template.addAttribute("listaDeUsuariosWeb", listaDeUsuariosWeb);

		// SI NO HAY USUARIOS WEB REGISTRADOS
		if (listaDeUsuariosWeb.size() == 0) {
			template.addAttribute("hayUsuariosWeb", false);
			return "resultados";
		}
		// SI HAY USUARIOS WEB REGISTRADOS
		else {
			return "listaDeUsuariosWeb";
		}
	}

	// ELIMINAR USUARIO ANDROID
	@RequestMapping("/administracion/usuarioAndroid/eliminar/{id}")
	public String eliminarUsuarioAndroid(@PathVariable(value = "id") int id, Model template) {
		jdbcTemplate.update("DELETE FROM usuariosandroid WHERE id = ?;", id);
		return "redirect:" + "/administracion/listaDeUsuariosAndroid";
	}

	// ELIMINAR USUARIO WEB
	@RequestMapping("/administracion/usuarioWeb/eliminar/{id}")
	public String eliminarUsuarioWeb(@PathVariable(value = "id") int id, Model template) {
		jdbcTemplate.update("DELETE FROM usuarios WHERE id = ?;", id);
		return "redirect:" + "/administracion/listaDeUsuariosWeb";
	}

	// MODIFICAR USUARIO WEB - GET
	@RequestMapping("/administracion/usuarioWeb/modificar/{id}")
	public String modificarUsuarioWebGet(@PathVariable(value = "id") int id, Model template) {

		// TRAER TIPOS
		SqlRowSet tiposQ;
		tiposQ = jdbcTemplate.queryForRowSet("SELECT * FROM tipos;");
		ArrayList<String> tipos = new ArrayList<String>();
		while (tiposQ.next()) {
			String tipo = tiposQ.getString("Tipos");
			tipos.add(tipo);
		}
		template.addAttribute("tipos", tipos);

		// TRAE AL USUARIO WEB

		SqlRowSet usuarioWebBD;
		usuarioWebBD = jdbcTemplate.queryForRowSet("SELECT * FROM usuarios WHERE ID = ?;", id);
		usuarioWebBD.next();
		String nombre = usuarioWebBD.getString("Nombre");
		String apellido = usuarioWebBD.getString("Apellido");
		String confirmacion = usuarioWebBD.getString("Confirmacion");
		String correo = usuarioWebBD.getString("Correo");
		String contrasenia = usuarioWebBD.getString("Contrasenia");
		template.addAttribute("id", id);
		template.addAttribute("nombre", nombre);
		template.addAttribute("apellido", apellido);
		template.addAttribute("confirmacion", confirmacion);
		template.addAttribute("correo", correo);
		template.addAttribute("contrasenia", contrasenia);
		return "modificarUsuarioWeb";
	}

	// MODIFICAR USUARIO WEB - POST
	@RequestMapping(value = "/administracion/usuarioWeb/modificar/{id}", method = RequestMethod.POST)
	public String modificarUsuarioWebPost(@PathVariable(value = "id") int id,
			@RequestParam(value = "nombre") String nombre, @RequestParam(value = "apellido") String apellido,
			// @RequestParam(value = "confirmacion") String confirmacion,
			@RequestParam(value = "correo") String correo, @RequestParam(value = "contrasenia") String contrasenia,
			@RequestParam(value = "Rcontrasenia") String rContrasenia, Model template) {

		// VERIFICAR CONTRASEÑAS
		if (!contrasenia.equals(rContrasenia)) {
			template.addAttribute("contrasenia", false);
			return "resultados";
		}
		// VERIFICAR QUE SOLO CONTEGAN LETRAS EL NOMBRE Y APELLIDO
		/*
		 * if (nombre.matches("([a-z]|[A-Z])") || apellido.matches("([a-z]|[A-Z])")) {
		 * template.addAttribute("tipoBien", false); return "resultados"; }
		 */
		if (nombre.length() != 0 && apellido.length() != 0 && contrasenia.length() != 0) {
			this.jdbcTemplate.update(
					"UPDATE usuarios SET Nombre=?, Apellido=?, Confirmacion=?, Correo=?, Contrasenia=? WHERE ID=?;",
					nombre, apellido, "habilitada", correo, contrasenia, id);
			return "redirect:" + "/administracion/listaDeUsuariosWeb";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// MODIFICAR USUARIO ANDROID - GET
	@RequestMapping("/administracion/usuarioAndroid/modificar/{id}")
	public String modificarUsuarioAndroidGet(@PathVariable(value = "id") int id, Model template) {

		// TRAE AL USUARIO ANDROID
		SqlRowSet usuariAndroidBD;
		usuariAndroidBD = jdbcTemplate.queryForRowSet("SELECT * FROM usuariosandroid WHERE ID = ?;", id);
		usuariAndroidBD.next();
		String codigo = usuariAndroidBD.getString("codigo");
		String nombre = usuariAndroidBD.getString("nombre");
		String apellido = usuariAndroidBD.getString("apellido");
		String contrasenia = usuariAndroidBD.getString("contraseña");
		String fechaDeNacimiento = usuariAndroidBD.getString("fechaDeNacimiento");
		template.addAttribute("id", id);
		template.addAttribute("codigo", codigo);
		template.addAttribute("nombre", nombre);
		template.addAttribute("apellido", apellido);
		template.addAttribute("contrasenia", contrasenia);
		template.addAttribute("fechaDeNacimiento", fechaDeNacimiento);
		return "modificarUsuarioAndroid";
	}

	// MODIFICAR USUARIO ANDROID - POST
	@RequestMapping(value = "/administracion/usuarioAndroid/modificar/{id}", method = RequestMethod.POST)
	public String modificarUsuarioAndroidPost(@PathVariable(value = "id") int id,
			@RequestParam(value = "codigo") String codigo, @RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "apellido") String apellido,
			@RequestParam(value = "fechaDeNacimiento") String fechaDeNacimiento,
			@RequestParam(value = "contrasenia") String contrasenia,
			@RequestParam(value = "Rcontrasenia") String rContrasenia, Model template) {

		// VERIFICAR DE NO REPETIR CODIGOS
		SqlRowSet codigosBD;
		codigosBD = jdbcTemplate.queryForRowSet("SELECT codigo FROM usuariosandroid WHERE ID = ?;", id);
		codigosBD.next();
		String codigoAnterior = codigosBD.getString("codigo");
		codigosBD = jdbcTemplate.queryForRowSet("SELECT codigo FROM usuariosandroid;");
		while (codigosBD.next()) {
			if (codigosBD.getString("codigo").equals(codigo) && !codigoAnterior.equals(codigo)) {
				template.addAttribute("codigoRepetido", true);
				return "resultados";
			}
		}

		// VALIDAR LARGO DEL CODIGO
		if (codigo.length() != 3) {
			template.addAttribute("codInvalido", true);
			return "resultados";
		}
		// VALIDAR FECHA DE NACIMIENTO
		if (!fechaDeNacimiento.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$")) {
			template.addAttribute("fechaValida", false);
			return "resultados";
		}
		// VERIFICAR CONTRASEÑAS
		if (!contrasenia.equals(rContrasenia)) {
			template.addAttribute("contrasenia", false);
			return "resultados";
		}
		// VERIFICAR QUE SOLO CONTEGAN LETRAS EL NOMBRE Y APELLIDO
		if (nombre.matches("([a-z]|[A-Z])") || apellido.matches("([a-z]|[A-Z])")) {
			template.addAttribute("tipoBien", false);
			return "resultados";
		}
		if (nombre.length() != 0 && apellido.length() != 0 && fechaDeNacimiento.length() != 0
				&& contrasenia.length() != 0 && codigo.length() != 0) {
			this.jdbcTemplate.update(
					"UPDATE usuariosandroid SET codigo=?, nombre=?, apellido=?, contraseña=?, fechaDeNacimiento=? WHERE ID=?;",
					codigo, nombre, apellido, contrasenia, fechaDeNacimiento, id);
			return "redirect:" + "/administracion/listaDeUsuariosAndroid";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// DESCONECTARSE
	@RequestMapping("/salir")
	public String Salir(Model template, HttpServletRequest request) {
		String variableDeSesion = (String) request.getSession().getAttribute("miVariable");
		variableDeSesion = null;
		request.getSession().setAttribute("miVariable", variableDeSesion);
		return "redirect:" + "/ingresar";
	}

	// FIN DE USUARIOS

	// ABEL

	// TRAE LAS OBRAS CON PEDIDOS, BUSCA POR Cliente ; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "obrasConPedidos/{cliente}", method = RequestMethod.GET)
	@ResponseBody
	public String[] obrasConPedidos(@PathVariable(value = "cliente") String cliente, Model template) {
		SqlRowSet obras;
		obras = jdbcTemplate.queryForRowSet("SELECT DISTINCT Obra FROM pedidos WHERE Cliente = ?;", cliente);
		int i = 0;
		while (obras.next()) {
			i++;
		}
		obras = jdbcTemplate.queryForRowSet("SELECT DISTINCT Obra FROM pedidos WHERE Cliente = ?;", cliente);
		String[] ldo = new String[i];
		int a = 0;
		while (obras.next()) {
			String nombreObra = obras.getString("Obra");
			ldo[a] = nombreObra;
			a++;
		}
		return ldo;
	}

	// FIN DE TRAE LAS OBRASreturn listaDeObras;

	// EVALUAR NRO DE REMITO - AJAX
	@RequestMapping(value = "remito/{nroRemito}", method = RequestMethod.GET)
	@ResponseBody
	public boolean Remito(@PathVariable(value = "nroRemito") String nroRemito, Model template) {
		// Declaro flag
		boolean nroRepetido = false;
		// Traigo a todos los remitos
		ArrayList<Remito> listaRemitos = traerRemitos();
		// Recorro y evaluo si el remito ya esta guardado en la base
		for (Remito r : listaRemitos) {
			String nRem = r.getNumeroDeRemito();
			if (nRem.equals(nroRemito)) {
				nroRepetido = true;
			}
		}
		return nroRepetido;
	}

	// EVALUAR NRO DE SAP - AJAX
	@RequestMapping(value = "nroSap/{nroSap}", method = RequestMethod.GET)
	@ResponseBody
	public boolean NumeroRemito(@PathVariable(value = "nroSap") String nroSap, Model template) {
		// Declaro flag
		boolean nroRepetido = false;
		// Traigo todos los codigos
		ArrayList<CodigoMP> listaCodigosMP = traerCodigosMP();
		// Recorro y evaluo si el codigo ya esta guardado en la base
		for (CodigoMP c : listaCodigosMP) {
			String nCod = c.getCodSap();
			if (nCod.equals(nroSap)) {
				nroRepetido = true;
			}
		}
		return nroRepetido;
	}

	// EVALUAR NRO DE REFERENCIA - AJAX
	@RequestMapping(value = "nroReferencia/{nroReferencia}", method = RequestMethod.GET)
	@ResponseBody
	public boolean NumeroReferencia(@PathVariable(value = "nroReferencia") String nroReferencia, Model template) {
		// Declaro flag
		boolean nroRepetido = false;
		// Traigo todos los nros de referencia
		ArrayList<IngresoMP> listaDeIngresosMP = traerIngresosMP();
		// Recorro y evaluo si el codigo ya esta guardado en la base
		for (IngresoMP imp : listaDeIngresosMP) {
			String nRef = imp.getReferencia();
			if (nRef.equals(nroReferencia)) {
				nroRepetido = true;
			}
		}
		return nroRepetido;
	}

	// EVALUAR NRO DE LOTE - AJAX
	@RequestMapping(value = "nroLote/{nroLote}/{material}", method = RequestMethod.GET)
	@ResponseBody
	public boolean NumeroLote(@PathVariable(value = "nroLote") String nroLote,
			@PathVariable(value = "material") String material, Model template) {
		// Declaro flag
		boolean loteRepetido = false;

		// Recorro Array y valido
		ArrayList<IngresoMP> listaDeIngresosMP = traerIngresosMP();
		for (IngresoMP mp : listaDeIngresosMP) {
			String nroMat = mp.getMaterial();
			String lote = mp.getLote();
			if (nroMat.equals(material)) {
				if (lote.equals(nroLote)) {
					loteRepetido = true;
				}
			}
		}

		System.out.println("Lote: " + nroLote);
		System.out.println("Material: " + material);
		System.out.println("Respuesta: " + loteRepetido);

		// Retorno
		return loteRepetido;
	}

	// TRAE LAS OBRAS, BUSCA POR Cliente ; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "obras/{cliente}", method = RequestMethod.GET)
	@ResponseBody
	public String[] obras(@PathVariable(value = "cliente") String cliente, Model template) {
		ArrayList<CliEnOb> listaObrass = traerobrasceo();
		ArrayList<CliEnOb> listaObras = new ArrayList<CliEnOb>();
		for (CliEnOb ceo : listaObrass) {
			String nomFan = ceo.getNombreFantasia();
			if (nomFan.equals(cliente)) {
				listaObras.add(ceo);
			}
		}
		SqlRowSet obras;
		obras = jdbcTemplate.queryForRowSet("SELECT DISTINCT Obra FROM pedidos WHERE Cliente = ?;", cliente);
		String[] ldo = new String[10];
		int a = 0;
		while (obras.next()) {
			String nombreObra = obras.getString("Obra");
			ldo[a] = nombreObra;
			a++;
		}

		String[] listaDeObras = new String[listaObras.size()];
		for (int i = 0; i < listaObras.size(); i++) {
			listaDeObras[i] = listaObras.get(i).getNombreDeObra();
		}
		return listaDeObras;
	}

	// FIN DE TRAE LAS OBRASreturn listaDeObras;

	// TRAE LOS PEDIDOS, BUSCA POR Cliente Y Obra; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "pedidos/{cliente}/{obra}", method = RequestMethod.GET)
	@ResponseBody
	public String[] pedidos(@PathVariable(value = "cliente") String cliente, 
							@PathVariable(value = "obra") String obra, Model template) {
		
		// INICIALIZAMOS LAS VARIBLES NECESARIAS
		SqlRowSet pedidos, remitos, remitosAcerBrag;
		
		ArrayList<String> codigosDePedidos = new ArrayList<String>();
		ArrayList<String> codigosDePedidosUsadosEnRemitos = new ArrayList<String>();
		ArrayList<String> aEnviar = new ArrayList<String>();
		
		// HACEMOS LAS QUERY'S NECESARIAS
		pedidos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Cliente = ? AND Obra LIKE ?;", cliente, obra);
		remitos = jdbcTemplate.queryForRowSet("SELECT DISTINCT CodigoDePedido FROM remitospedidos");
		remitosAcerBrag = jdbcTemplate.queryForRowSet("SELECT DISTINCT CodigoDePedido FROM remitospedidosacerbrag");
		
		// AGREGAMOS LO TRAIDO EN LAS QUERY'S A SUS RESPECTIVOS ARRAYLIST'S
		while (remitos.next()) codigosDePedidosUsadosEnRemitos.add(remitos.getString("CodigoDePedido"));
		while (remitosAcerBrag.next()) codigosDePedidosUsadosEnRemitos.add(remitosAcerBrag.getString("CodigoDePedido"));
		while (pedidos.next()) codigosDePedidos.add(pedidos.getString("Codigo"));
		
		// SACAMOS TODOS LOS CODIGOS QUE SE HAYAN UTILIZADO EN REMITOS
		for (int posicion = 0; posicion < codigosDePedidos.size(); posicion++) {
			if (!codigosDePedidosUsadosEnRemitos.contains(codigosDePedidos.get(posicion))) {
				aEnviar.add(codigosDePedidos.get(posicion));
			}
		}
		
		// VALIDAMOS POR SI SE USARON TODOS LOS CODIGO DISPONIBLES
		if (aEnviar.size() == 0) {
			String[] sinCodigosParaUtilizar = new String[1];
			sinCodigosParaUtilizar[0] = "NO HAY CODIGO DISPONIBLE";
			return sinCodigosParaUtilizar;
		}
		
		// HACEMOS EL CAMBIO A STRING[] PORQUE NO SE COMO MANEJAR UN ARRAYLIST
		// CON JAVASCRIPT
		String[] pedidosConClienteObra = new String[aEnviar.size()];
		for (int posicion = 0; posicion < pedidosConClienteObra.length; posicion++) {
			pedidosConClienteObra[posicion] = aEnviar.get(posicion);
		}
		return pedidosConClienteObra;
	}

	// FIN DE TRAE LAS OBRASreturn listaDeObras;

	// FUNCION QUE TRAE PEDIDOS POR OBRA Y POR CLIENTE
	private ArrayList<Pedido> traerPedidosPOYPC(String obra1, String cli) {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Obra = ? and Cliente = ?;", obra1, cli);
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String otros = pedidosBD.getString("otros");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			String elementos = pedidosBD.getString("elementos");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, otros, estado, oc, pedido, elementos);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}

	// FUNCION QUE REDONDEA VARIABLES (TIPO DOUBLE) PARA ARRIBA
	private int redondearDouble(double nroOriginal) {
		int convertidoInt = (int) nroOriginal;
		double nroOriginalM = convertidoInt;
		int retval = Double.compare(nroOriginal, nroOriginalM);
		// DEVUELVE 1 SI NO SON IGUALES
		if (retval == 1) {
			convertidoInt++;
		}
		return convertidoInt;
	}

	// FUNCION QUE TRAE A TODOS LOS ITEMS BUSCA POR IDITEM
	@SuppressWarnings("unused")
	private ArrayList<Item> traerItemsII(int idItem) {
		SqlRowSet itemsBD;
		itemsBD = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE ID = ?;", idItem);
		ArrayList<Item> listaItems = new ArrayList<Item>();
		while (itemsBD.next()) {
			int id = itemsBD.getInt("id");
			int idpedido = itemsBD.getInt("idpedido");
			int item = itemsBD.getInt("item");
			String posicion = itemsBD.getString("posicion");
			String acero = itemsBD.getString("acero");
			String material = itemsBD.getString("material");
			String diametro = itemsBD.getString("diametro");
			String cantidad = itemsBD.getString("cantidad");
			String formato = itemsBD.getString("formato");
			String dibujo = itemsBD.getString("dibujo");
			String a = itemsBD.getString("a");
			String b = itemsBD.getString("b");
			String c = itemsBD.getString("c");
			String d = itemsBD.getString("d");
			String e = itemsBD.getString("e");
			String f = itemsBD.getString("f");
			String g = itemsBD.getString("g");
			String h = itemsBD.getString("h");
			String h1 = itemsBD.getString("h1");
			String h2 = itemsBD.getString("h2");
			String lParcial = itemsBD.getString("lParcial");
			String lTotal = itemsBD.getString("lTotal");
			String lCortar = itemsBD.getString("lCortar");
			String peso = itemsBD.getString("peso");
			String observaciones = itemsBD.getString("observaciones");
			String codigo = itemsBD.getString("Codigo");
			String estructura = itemsBD.getString("estructura");
			Item i = new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, formato, dibujo, a, b,
					c, d, e, f, g, h, h1, h2, lParcial, lTotal, lCortar, peso, observaciones, codigo, estructura);
			listaItems.add(i);
		}
		return listaItems;
	}

	// FUNCION QUE TRAE EL MOTIVO POR ID DE PEDIDO
	private Motivo traerMotivoPorIdPedido(int idPedido) {
		SqlRowSet motivoQ;
		motivoQ = jdbcTemplate.queryForRowSet(
				"SELECT * FROM motivos WHERE ID_Pedido = ? AND ID = (SELECT MAX(ID) FROM motivos WHERE ID_Pedido = ?);",
				idPedido, idPedido);
		if (motivoQ.next() == true) {
			int id = motivoQ.getInt("ID");
			String motivo = motivoQ.getString("Motivo");
			String fecha = motivoQ.getString("Fecha");
			int id_Pedido = motivoQ.getInt("ID_Pedido");
			Motivo mot = new Motivo(id, motivo, fecha, id_Pedido);
			return mot;
		} else {
			Motivo mot = new Motivo(0, "No se ha modificado la fecha", "", 0);
			return mot;
		}
	}

	// FUNCION QUE TRAE TODOS LOS MOTIVOS POR ID DE PEDIDO
	private ArrayList<Motivo> traerMotivosPorIdPedido(int idPedido) {
		SqlRowSet motivoQ;
		motivoQ = jdbcTemplate.queryForRowSet("SELECT * FROM motivos WHERE ID_Pedido = ?;", idPedido);
		ArrayList<Motivo> motivos = new ArrayList<Motivo>();
		while (motivoQ.next()) {
			int id = motivoQ.getInt("ID");
			String motivo = motivoQ.getString("Motivo");
			String fecha = motivoQ.getString("Fecha");
			int id_Pedido = motivoQ.getInt("ID_Pedido");
			Motivo mot = new Motivo(id, motivo, fecha, id_Pedido);
			motivos.add(mot);
		}
		return motivos;
	}

	// FUNCION QUE TRAE A LOS ITEMS DE UN PEDIDO (COMPLETO) 26 ATRIBUTOS
	private ArrayList<Item> traerItemsCompleto(int idPedido) {
		SqlRowSet rowItemsCompleto;
		rowItemsCompleto = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE IDpedido = ?;", idPedido);
		ArrayList<Item> listaItemsCompleto = new ArrayList<Item>();
		while (rowItemsCompleto.next()) {
			int id = rowItemsCompleto.getInt("ID");
			int idpedido = rowItemsCompleto.getInt("IDpedido");
			int item = rowItemsCompleto.getInt("Item");
			String posicion = rowItemsCompleto.getString("Posicion");
			String acero = rowItemsCompleto.getString("Acero");
			String material = rowItemsCompleto.getString("Material");
			String diametro = rowItemsCompleto.getString("Diametro");
			String cantidad = rowItemsCompleto.getString("Cantidad");
			String formato = rowItemsCompleto.getString("Formato");
			String dibujo = rowItemsCompleto.getString("Dibujo");
			String a = rowItemsCompleto.getString("A");
			String b = rowItemsCompleto.getString("B");
			String c = rowItemsCompleto.getString("C");
			String d = rowItemsCompleto.getString("D");
			String e = rowItemsCompleto.getString("E");
			String f = rowItemsCompleto.getString("F");
			String g = rowItemsCompleto.getString("G");
			String h = rowItemsCompleto.getString("H");
			String h1 = rowItemsCompleto.getString("H1");
			String h2 = rowItemsCompleto.getString("H2");
			String lparcial = rowItemsCompleto.getString("LParcial");
			String ltotal = rowItemsCompleto.getString("LTotal");
			String lcortar = rowItemsCompleto.getString("LCortar");
			String peso = rowItemsCompleto.getString("Peso");
			String observaciones = rowItemsCompleto.getString("Observaciones");
			String estructura = rowItemsCompleto.getString("Estructura");
			String codigo = rowItemsCompleto.getString("Codigo");

			Item ixp = new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, formato, dibujo, a,
					b, c, d, e, f, g, h, h1, h2, lparcial, ltotal, lcortar, peso, observaciones, codigo, estructura);
			listaItemsCompleto.add(ixp);

		}
		return listaItemsCompleto;
	}

	// FUNCION QUE TRAE A LOS ITEMS DE UN PEDIDO
	private ArrayList<Item> traerItemsXPed(int idPedido) {
		SqlRowSet rowItemsXPed;
		rowItemsXPed = jdbcTemplate.queryForRowSet("SELECT IDpedido, Item , Posicion FROM items WHERE IDpedido = ?;",
				idPedido);
		ArrayList<Item> listaItemsXPed = new ArrayList<Item>();
		while (rowItemsXPed.next()) {
			int idpedido = rowItemsXPed.getInt("IDpedido");
			int item = rowItemsXPed.getInt("Item");
			String posicion = rowItemsXPed.getString("Posicion");
			Item ixp = new Item(idpedido, item, posicion);
			listaItemsXPed.add(ixp);
		}
		return listaItemsXPed;
	}

	// FUNCION QUE TRAE A LOS ITEMS DE UN PEDIDO
	private ArrayList<Item> traerItemsXPedd(int idPedido) {
		SqlRowSet rowItemsXPed;
		rowItemsXPed = jdbcTemplate.queryForRowSet(
				"SELECT IDpedido, Item , Posicion, Estructura FROM items WHERE IDpedido = ?;", idPedido);
		ArrayList<Item> listaItemsXPed = new ArrayList<Item>();
		while (rowItemsXPed.next()) {
			int idpedido = rowItemsXPed.getInt("IDpedido");
			int item = rowItemsXPed.getInt("Item");
			String posicion = rowItemsXPed.getString("Posicion");
			String estructura = rowItemsXPed.getString("Estructura");
			Item ixp = new Item(idpedido, item, posicion, estructura);
			listaItemsXPed.add(ixp);
		}
		return listaItemsXPed;
	}

	// FUNCION QUE TRAE A LAS OBRAS (CLIENOB)
	private ArrayList<CliEnOb> traerobrasceo() {
		SqlRowSet traeobr;
		traeobr = jdbcTemplate.queryForRowSet("SELECT * FROM clientesenobras");
		ArrayList<CliEnOb> lisObr = new ArrayList<CliEnOb>();
		while (traeobr.next()) {
			int id = traeobr.getInt("ID");
			String nomFan = traeobr.getString("NombreFantasia");
			String nomObr = traeobr.getString("NombreDeObra");
			String contacto = traeobr.getString("Contacto");
			String telefono = traeobr.getString("Telefono");
			String direccion = traeobr.getString("Direccion");
			String condPago = traeobr.getString("CondPago");
			CliEnOb ceo = new CliEnOb(id, nomFan, nomObr, contacto, telefono, direccion, condPago);
			lisObr.add(ceo);
		}
		return lisObr;
	}

	// FUNCION QUE TRAE EL STOCK
	private ArrayList<Stock> traerStock() {
		SqlRowSet traeStock;
		traeStock = jdbcTemplate.queryForRowSet("SELECT * FROM stock");
		ArrayList<Stock> listaStock = new ArrayList<Stock>();
		while (traeStock.next()) {
			int id = traeStock.getInt("ID");
			String familia = traeStock.getString("Familia");
			String codMat = traeStock.getString("CodMat");
			String descripcion = traeStock.getString("Descripcion");
			String tipoMaterial = traeStock.getString("TipoMaterial");
			String kgTeorico = traeStock.getString("KGTeorico");
			String kgProd = traeStock.getString("KGProd");
			String kgDisponible = traeStock.getString("KGDisponible");
			Stock s = new Stock(id, familia, codMat, descripcion, tipoMaterial, kgTeorico, kgProd, kgDisponible);
			listaStock.add(s);
		}
		return listaStock;
	}

	// FUNCION QUE TRAE A LAS OBRAS (CLIENOB) POR NOMBRE DE FANTASIA
	private ArrayList<CliEnOb> traerobrasceoPorNomFan(String nomFan) {
		SqlRowSet traeobr;
		traeobr = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientesenobras WHERE NombreFantasia = ?;",
				nomFan);
		ArrayList<CliEnOb> lisObr = new ArrayList<CliEnOb>();
		while (traeobr.next()) {
			int id = traeobr.getInt("ID");
			String nomFantasia = traeobr.getString("NombreFantasia");
			CliEnOb ceo = new CliEnOb(id, nomFantasia);
			lisObr.add(ceo);
		}
		return lisObr;
	}

	// FUNCION QUE TRAE A LAS OBRAS (CLIENOB) POR NOMBRE DE OBRA
	private ArrayList<CliEnOb> traerobrasceoPorNomOb(String nomOb) {
		SqlRowSet traeobr;
		traeobr = jdbcTemplate.queryForRowSet("SELECT ID, NombreDeObra FROM clientesenobras WHERE NombreDeObra = ?;",
				nomOb);
		ArrayList<CliEnOb> lisObr = new ArrayList<CliEnOb>();
		while (traeobr.next()) {
			int id = traeobr.getInt("ID");
			String nomObra = traeobr.getString("NombreDeObra");
			CliEnOb ceo = new CliEnOb(id, nomObra);
			lisObr.add(ceo);
		}
		return lisObr;
	}

	// FUNCION QUE TRAE A LA DIRECCION DE UNA OBRA POR NOMBRE DE OBRA
	private String traerDireccionDeObraPorNomOb(String nomOb) {
		SqlRowSet traeobr;
		traeobr = jdbcTemplate.queryForRowSet("SELECT Direccion FROM clientesenobras WHERE NombreDeObra = ?;", nomOb);
		traeobr.next();
		return traeobr.getString("Direccion");
	}

	// FUNCION QUE TRAE A LAS OBRAS (CLIENOB) POR ID
	private ArrayList<CliEnOb> traerobrasceoPorID(int id) {
		SqlRowSet traeobr;
		traeobr = jdbcTemplate.queryForRowSet("SELECT * FROM clientesenobras WHERE ID = ?;", id);
		ArrayList<CliEnOb> lisObr = new ArrayList<CliEnOb>();
		while (traeobr.next()) {
			int id1 = traeobr.getInt("ID");
			String nomOb = traeobr.getString("NombreDeObra");
			String nomFan = traeobr.getString("NombreFantasia");
			CliEnOb ceo = new CliEnOb(id1, nomFan, nomOb);
			lisObr.add(ceo);
		}
		return lisObr;
	}

	// FUNCION QUE TRAE A TODOS LOS ITEMS, BUSCA POR IDPEDIDO
	public ArrayList<Item> traerItems(int idPedido) {
		SqlRowSet itemsBD;
		itemsBD = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE IDpedido = ?;", idPedido);
		ArrayList<Item> listaItems = new ArrayList<Item>();
		while (itemsBD.next()) {
			long id = itemsBD.getInt("id");
			long idpedido = itemsBD.getInt("idpedido");
			long item = itemsBD.getInt("item");
			String posicion = itemsBD.getString("posicion");
			String acero = itemsBD.getString("acero");
			String material = itemsBD.getString("material");
			String diametro = itemsBD.getString("diametro");
			String cantidad = itemsBD.getString("cantidad");
			String formato = itemsBD.getString("formato");
			String dibujo = itemsBD.getString("dibujo");
			String a = itemsBD.getString("a");
			String b = itemsBD.getString("b");
			String c = itemsBD.getString("c");
			String d = itemsBD.getString("d");
			String e = itemsBD.getString("e");
			String f = itemsBD.getString("f");
			String g = itemsBD.getString("g");
			String h = itemsBD.getString("h");
			String h1 = itemsBD.getString("h1");
			String h2 = itemsBD.getString("h2");
			String lParcial = itemsBD.getString("lParcial");
			String lTotal = itemsBD.getString("lTotal");
			String lCortar = itemsBD.getString("lCortar");
			String peso = itemsBD.getString("peso");
			String observaciones = itemsBD.getString("observaciones");
			String codigo = itemsBD.getString("Codigo");
			String estructura = itemsBD.getString("estructura");
			Item i = new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, formato, dibujo, a, b,
					c, d, e, f, g, h, h1, h2, lParcial, lTotal, lCortar, peso, observaciones, codigo, estructura);
			listaItems.add(i);
		}
		return listaItems;
	}

	// FUNCION QUE TRAE A TODOS LOS ITEMS, BUSCA POR IDPEDIDO
	private ArrayList<Item> traerItemsImprimir(int idPedido) {
		SqlRowSet itemsBD;
		itemsBD = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE IDpedido = ?;", idPedido);
		ArrayList<Item> listaItems = new ArrayList<Item>();
		while (itemsBD.next()) {
			long id = itemsBD.getInt("id");
			long idpedido = itemsBD.getInt("idpedido");
			long item = itemsBD.getInt("item");
			String posicion = itemsBD.getString("posicion");
			String acero = itemsBD.getString("acero");
			String material = itemsBD.getString("material");
			String diametro = itemsBD.getString("diametro");
			String cantidad = itemsBD.getString("cantidad");
			String formato = itemsBD.getString("formato");
			String dibujo = itemsBD.getString("dibujo");
			String a = itemsBD.getString("a");
			String b = itemsBD.getString("b");
			String c = itemsBD.getString("c");
			String d = itemsBD.getString("d");
			String e = itemsBD.getString("e");
			String f = itemsBD.getString("f");
			String g = itemsBD.getString("g");
			String h = itemsBD.getString("h");
			String h1 = itemsBD.getString("h1");
			String h2 = itemsBD.getString("h2");
			String lParcial = itemsBD.getString("lParcial");
			String lTotal = itemsBD.getString("lTotal");
			String lCortar = itemsBD.getString("lCortar");
			String peso = itemsBD.getString("peso");
			String observaciones = itemsBD.getString("observaciones");
			String codigo = itemsBD.getString("Codigo");
			String estructura = itemsBD.getString("estructura");
			String nuevaEstructura = "";
			if (estructura.length() > 20) {
				for (int i = 0; i < 19; i++) {
					nuevaEstructura += estructura.charAt(i);
				}
			} else {
				nuevaEstructura = estructura;
			}
			Item i = new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, formato, dibujo, a, b,
					c, d, e, f, g, h, h1, h2, lParcial, lTotal, lCortar, peso, observaciones, codigo, nuevaEstructura);
			listaItems.add(i);
		}
		return listaItems;
	}

	// FUNCION QUE TRAE LA TABLA PARA LOS CALCULOS
	private ArrayList<TablaCalculos> traerDatos() {
		SqlRowSet calculos;
		calculos = jdbcTemplate.queryForRowSet("SELECT * FROM paracalculos");
		ArrayList<TablaCalculos> traerTabla = new ArrayList<TablaCalculos>();
		while (calculos.next()) {
			double diametro = calculos.getDouble("Diametro");
			double pxm = calculos.getDouble("PxM");
			double descxd = calculos.getDouble("DescxD");
			TablaCalculos tc = new TablaCalculos(diametro, pxm, descxd);
			traerTabla.add(tc);
		}
		return traerTabla;
	}

	
	// FUNCION QUE TRAE A LOS PEDIDOS
	private ArrayList<Pedido> traerPedidos() {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos where Estado != 'DESPACHADO '");
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String otros = pedidosBD.getString("otros");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			String elementos = pedidosBD.getString("Elementos");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, otros, estado, pedido, oc, elementos);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}
	// FUNCION QUE TRAE A LOS PEDIDOS DESPACHADOS
	private ArrayList<Pedido> traerPedidosDespachados() {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos where Estado = 'DESPACHADO '");
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String otros = pedidosBD.getString("otros");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			String elementos = pedidosBD.getString("Elementos");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, otros, estado, pedido, oc, elementos);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}

	// FUNCION QUE TRAE A LOS PEDIDOS DESDE Y HASTA
	private ArrayList<Pedido> traerPedidosDesdeHasta(String desde, String hasta) {
		String desdeFecha = "";
		String hastaFecha = "";
		String dia = desde.charAt(0) + "" + desde.charAt(1) + "";
		String mes = desde.charAt(2) + "" + desde.charAt(3) + "";
		String anio = desde.charAt(4) + "" + desde.charAt(5) + "" + desde.charAt(6) + "" + desde.charAt(7);
		String dia1 = hasta.charAt(0) + "" + hasta.charAt(1) + "";
		String mes1 = hasta.charAt(2) + "" + hasta.charAt(3) + "";
		String anio1 = desde.charAt(4) + "" + desde.charAt(5) + "" + desde.charAt(6) + "" + desde.charAt(7);
		desdeFecha = anio + mes + dia;
		hastaFecha = anio1 + mes1 + dia1;
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos");
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();

		while (pedidosBD.next()) {
			String fecha = pedidosBD.getString("entrega");
			// SI TIENE ASTERISCOS
			if (fecha.length() > 10) {
				fecha = fecha.substring(0, 10);
			}
			String fechaN = "";
			for (int x = 0; x < fecha.length(); x++) {
				if (fecha.charAt(x) != '/') {
					fechaN += fecha.charAt(x);
				}
			}
			int d = Integer.parseInt(desdeFecha);
			int h = Integer.parseInt(hastaFecha);
			int f = Integer.parseInt(fechaN);
			if (d <= f && h >= f) {
				long id = pedidosBD.getInt("id");
				String entrega = pedidosBD.getString("entrega");
				String cliente = pedidosBD.getString("cliente");
				String obra = pedidosBD.getString("obra");
				String codigo = pedidosBD.getString("codigo");
				String descripcion = pedidosBD.getString("descripcion");
				String tipo = pedidosBD.getString("tipo");
				String totalKg = pedidosBD.getString("totalKg");
				String cuatrocomados = pedidosBD.getString("cuatrocomados");
				String seis = pedidosBD.getString("seis");
				String ocho = pedidosBD.getString("ocho");
				String diez = pedidosBD.getString("diez");
				String doce = pedidosBD.getString("doce");
				String dieciseis = pedidosBD.getString("dieciseis");
				String veinte = pedidosBD.getString("veinte");
				String veinticinco = pedidosBD.getString("veinticinco");
				String treintaydos = pedidosBD.getString("treintaydos");
				String estado = pedidosBD.getString("estado");
				String pedido = pedidosBD.getString("Pedido");
				String oc = pedidosBD.getString("OC");
				Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados,
						seis, ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, estado, pedido, oc);
				listaPedidos.add(a);
				Fecha b = new Fecha(invertirCadena(entrega));
				fechas.add(b);
			}

		}
		return listaPedidos;
	}

	// FUNCION QUE TRAE A LOS PEDIDOS POR CLIENTE Y OBRA
	private ArrayList<Pedido> traerPedidosPorCliente(String nombreCliente, String nombreObra) {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Cliente =? AND Obra =?", nombreCliente,
				nombreObra);
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String otros = pedidosBD.getString("Otros");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			String elementos = pedidosBD.getString("Elementos");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, otros, estado, pedido, oc, elementos);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}

	// FUNCION QUE TRAE A LOS PEDIDOS POR CLIENTE
	private ArrayList<Pedido> traerPedidosPorCliente(String nombreCliente) {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Cliente =? ", nombreCliente);
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, estado, pedido, oc);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}

	// PEDIDOS POR OBRA Y CLIENTE
	@SuppressWarnings("unused")
	private ArrayList<Pedido> traerPedidosPorClienteYporObra(String nombreCliente, String obraCliente) {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Cliente =? AND Obra=? ", nombreCliente,
				obraCliente);
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, estado, pedido, oc);
			listaPedidos.add(a);
		}
		return listaPedidos;
	}

	// FUNCION QUE TRAE A LOS PEDIDOS, BUSCA POR idPedido
	private ArrayList<Pedido> traerPedidosIP(int idPedido) {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID = ?;", idPedido);
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String otros = pedidosBD.getString("Otros");
			String estado = pedidosBD.getString("estado");
			String pedido = pedidosBD.getString("Pedido");
			String oc = pedidosBD.getString("OC");
			String elementos = pedidosBD.getString("Elementos");
			Pedido a = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados, seis,
					ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, otros, estado, pedido, oc, elementos);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}

	// FUNCION QUE TRAE A LOS PEDIDOS, BUSCA POR idPedido
	private ArrayList<PedidoPSS> traerPedidosPSSIP(int idPedido) {
		SqlRowSet pedidosBD;
		pedidosBD = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID = ?;", idPedido);
		ArrayList<PedidoPSS> listaPedidos = new ArrayList<PedidoPSS>();
		ArrayList<Fecha> fechas = new ArrayList<Fecha>();
		while (pedidosBD.next()) {
			long id = pedidosBD.getInt("id");
			String entrega = pedidosBD.getString("entrega");
			String cliente = pedidosBD.getString("cliente");
			String obra = pedidosBD.getString("obra");
			String codigo = pedidosBD.getString("codigo");
			String descripcion = pedidosBD.getString("descripcion");
			String tipo = pedidosBD.getString("tipo");
			String totalKg = pedidosBD.getString("totalKg");
			String cuatrocomados = pedidosBD.getString("cuatrocomados");
			String seis = pedidosBD.getString("seis");
			String ocho = pedidosBD.getString("ocho");
			String diez = pedidosBD.getString("diez");
			String doce = pedidosBD.getString("doce");
			String dieciseis = pedidosBD.getString("dieciseis");
			String veinte = pedidosBD.getString("veinte");
			String veinticinco = pedidosBD.getString("veinticinco");
			String treintaydos = pedidosBD.getString("treintaydos");
			String estado = pedidosBD.getString("estado");
			String nombreDePlano = "";
			String numeroDeSector = "";
			String numeroDeSubSector = "";
			String pedido = pedidosBD.getString("Pedido");
			PedidoPSS a = new PedidoPSS(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKg, cuatrocomados,
					seis, ocho, diez, doce, dieciseis, veinte, veinticinco, treintaydos, estado, nombreDePlano,
					numeroDeSector, numeroDeSubSector, pedido);
			listaPedidos.add(a);
			Fecha b = new Fecha(invertirCadena(entrega));
			fechas.add(b);
		}
		return listaPedidos;
	}

	// FUNCION QUE TRAE A LOS CLIENTES
	private ArrayList<Cliente> traerClientes() {
		SqlRowSet rowclientes;
		rowclientes = jdbcTemplate.queryForRowSet("SELECT * FROM clientes");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (rowclientes.next()) {
			int id = rowclientes.getInt("ID");
			String codigo = rowclientes.getString("Codigo");
			String nombreFantasia = rowclientes.getString("NombreFantasia");
			String telefono = rowclientes.getString("Telefono");
			String razonSocial = rowclientes.getString("RazonSocial");
			String direccionFiscal = rowclientes.getString("DireccionFiscal");
			String nroCuil = rowclientes.getString("NroCuil");
			Cliente cli = new Cliente(id, codigo, nroCuil, nombreFantasia, razonSocial, direccionFiscal, telefono);
			listaClientes.add(cli);
		}
		return listaClientes;
	}

	// FUNCION QUE TRAE A LOS USUARIOS DE ANDROID
	private ArrayList<UsuarioAndroid> traerUsuariosAndroid() {
		SqlRowSet usuariosAndroidBD;
		usuariosAndroidBD = jdbcTemplate.queryForRowSet("SELECT * FROM usuariosandroid");
		ArrayList<UsuarioAndroid> listaDeUsuariosAndroid = new ArrayList<UsuarioAndroid>();
		while (usuariosAndroidBD.next()) {
			int id = usuariosAndroidBD.getInt("ID");
			String codigo = usuariosAndroidBD.getString("codigo");
			String nombre = usuariosAndroidBD.getString("nombre");
			String apellido = usuariosAndroidBD.getString("apellido");
			String contraseña = usuariosAndroidBD.getString("contraseña");
			String fechaDeNacimiento = usuariosAndroidBD.getString("fechaDeNacimiento");
			UsuarioAndroid ua = new UsuarioAndroid(id, codigo, nombre, apellido, contraseña, fechaDeNacimiento);
			listaDeUsuariosAndroid.add(ua);
		}
		return listaDeUsuariosAndroid;
	}

	// FUNCION QUE TRAE A LOS USUARIOS WEB
	private ArrayList<Usuario> traerUsuariosWeb() {
		SqlRowSet usuariosWebBD;
		usuariosWebBD = jdbcTemplate.queryForRowSet("SELECT * FROM usuarios");
		ArrayList<Usuario> listaDeUsuariosWeb = new ArrayList<Usuario>();
		while (usuariosWebBD.next()) {
			int id = usuariosWebBD.getInt("ID");
			String nombre = usuariosWebBD.getString("Nombre");
			String apellido = usuariosWebBD.getString("Apellido");
			String confirmacion = usuariosWebBD.getString("Confirmacion");
			String correo = usuariosWebBD.getString("Correo");
			String contraseña = usuariosWebBD.getString("Contrasenia");
			Usuario uw = new Usuario(id, nombre, apellido, confirmacion, correo, contraseña);
			listaDeUsuariosWeb.add(uw);
		}
		return listaDeUsuariosWeb;
	}

	// FUNCION QUE TRAE A LOS REMITOS
	private ArrayList<Remito> traerRemitos() {
		SqlRowSet rowRemitos;
		rowRemitos = jdbcTemplate.queryForRowSet("SELECT * FROM remitos");
		ArrayList<Remito> listaRemitos = new ArrayList<Remito>();
		while (rowRemitos.next()) {
			int id = rowRemitos.getInt("ID");
			String numeroDeRemito = rowRemitos.getString("NumeroDeRemito");
			String fecha = rowRemitos.getString("Fecha");
			String estado = rowRemitos.getString("Estado");
			Remito rem = new Remito(id, numeroDeRemito, fecha, estado);
			listaRemitos.add(rem);
		}
		return listaRemitos;
	}

	// FUNCION QUE TRAE A LOS CODIGOS DE MP
	private ArrayList<CodigoMP> traerCodigosMP() {
		SqlRowSet rowCodigoMP;
		rowCodigoMP = jdbcTemplate.queryForRowSet("SELECT * FROM codigomp");
		ArrayList<CodigoMP> listaCodigosMP = new ArrayList<CodigoMP>();
		while (rowCodigoMP.next()) {
			int id = rowCodigoMP.getInt("ID");
			String codSap = rowCodigoMP.getString("CodSAP");
			String familia = rowCodigoMP.getString("Familia");
			String descripcion = rowCodigoMP.getString("Descripcion");
			String tipoMaterial = rowCodigoMP.getString("TipoMaterial");
			CodigoMP codMP = new CodigoMP(id, codSap, familia, descripcion, tipoMaterial);
			listaCodigosMP.add(codMP);
		}
		return listaCodigosMP;
	}

	// FUNCION QUE TRAE A LOS INGRESOS DE MP
	private ArrayList<IngresoMP> traerIngresosMP() {
		SqlRowSet rowIngresoMP;
		rowIngresoMP = jdbcTemplate.queryForRowSet("SELECT * FROM ingresomp");
		ArrayList<IngresoMP> listaIngresosMP = new ArrayList<IngresoMP>();
		while (rowIngresoMP.next()) {
			long id = rowIngresoMP.getInt("ID");
			Date fecha = rowIngresoMP.getDate("Fecha");
			String referencia = rowIngresoMP.getString("Referencia");
			String material = rowIngresoMP.getString("Material");
			String descripcion = rowIngresoMP.getString("Descripcion");
			String cantidad = rowIngresoMP.getString("Cantidad");
			String umb = rowIngresoMP.getString("UMB");
			String lote = rowIngresoMP.getString("Lote");
			String destinatario = rowIngresoMP.getString("Destinatario");
			String colada = rowIngresoMP.getString("Colada");
			String pesoPorBalanza = rowIngresoMP.getString("PesoPorBalanza");
			IngresoMP ingMP = new IngresoMP(id, fecha, referencia, material, descripcion, cantidad, umb, lote,
					destinatario, colada, pesoPorBalanza);
			listaIngresosMP.add(ingMP);
		}
		return listaIngresosMP;
	}

	// FUNCION QUE TRAE A LOS INGRESOS DE REMITOS
	private ArrayList<IngresoRemitos> traerIngresosDeRemitos() {
		SqlRowSet traeInRem;
		traeInRem = jdbcTemplate.queryForRowSet("SELECT * FROM ingresoremitos");
		ArrayList<IngresoRemitos> lisIngRem = new ArrayList<IngresoRemitos>();
		while (traeInRem.next()) {
			int id = traeInRem.getInt("ID");
			String fecha = traeInRem.getString("Fecha");
			String nroRemito = traeInRem.getString("NroRemito");
			String referencia = traeInRem.getString("Referencia");
			String estado = traeInRem.getString("Estado");
			IngresoRemitos ingR = new IngresoRemitos(id, fecha, nroRemito, referencia, estado);
			lisIngRem.add(ingR);
		}
		return lisIngRem;
	}

	private boolean tieneNroRefRepetido(ArrayList<IngresoRemitos> lisIngRem, String referencia) {
		boolean tieneNroRefRep = false;
		for (IngresoRemitos ir : lisIngRem) {
			String ref = ir.getReferencia();
			if (ref.equals(referencia)) {
				tieneNroRefRep = true;
			}
		}
		return tieneNroRefRep;
	}

	// FUNCION QUE TRAE A LOS REMITOS ACERBRAG
	private ArrayList<Remito> traerRemitosAcerBrag() {
		SqlRowSet rowRemitos;
		rowRemitos = jdbcTemplate.queryForRowSet("SELECT * FROM remitosacerbrag");
		ArrayList<Remito> listaRemitos = new ArrayList<Remito>();
		while (rowRemitos.next()) {
			int id = rowRemitos.getInt("ID");
			String numeroDeRemito = rowRemitos.getString("NumeroDeRemito");
			String fecha = rowRemitos.getString("Fecha");
			String estado = rowRemitos.getString("Estado");
			Remito rem = new Remito(id, numeroDeRemito, fecha, estado);
			listaRemitos.add(rem);
		}
		return listaRemitos;
	}

	// FUNCION QUE TRAE A LOS CLIENTES POR NOMBREDEFANTASIA
	private ArrayList<Cliente> traerClientesPorId(int idCliente) {
		SqlRowSet rowclientes;
		rowclientes = jdbcTemplate.queryForRowSet("SELECT * FROM clientes WHERE ID = ?", idCliente);
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (rowclientes.next()) {
			int id = rowclientes.getInt("ID");
			String codigo = rowclientes.getString("Codigo");
			String nombreFantasia = rowclientes.getString("NombreFantasia");
			String telefono = rowclientes.getString("Telefono");
			String razonSocial = rowclientes.getString("RazonSocial");
			String direccionFiscal = rowclientes.getString("DireccionFiscal");
			String nroCuil = rowclientes.getString("NroCuil");
			Cliente cli = new Cliente(id, codigo, nroCuil, nombreFantasia, razonSocial, direccionFiscal, telefono);
			listaClientes.add(cli);
		}
		return listaClientes;
	}

	// FUNCION QUE TRAE A LAS OBRAS
	private ArrayList<Obra> traerObras() {
		SqlRowSet rowobras;
		rowobras = jdbcTemplate.queryForRowSet("SELECT * FROM obra");
		ArrayList<Obra> listaObras = new ArrayList<Obra>();
		while (rowobras.next()) {
			int id = rowobras.getInt("ID");
			String codigo = rowobras.getString("Codigo");
			String nombreDeObra = rowobras.getString("NombreDeObra");
			Obra obra = new Obra(id, codigo, nombreDeObra);
			listaObras.add(obra);
		}
		return listaObras;
	}

	// FUNCION QUE TRAE A LAS OBRAS. BUSCA POR NOMBRE DE LA OBRA
	private ArrayList<Obra> traerObrasPorNomOb(String nomOb) {
		SqlRowSet rowobras;
		rowobras = jdbcTemplate.queryForRowSet("SELECT ID, Codigo, NombreDeObra FROM obra WHERE NombreDeObra = ?",
				nomOb);
		ArrayList<Obra> listaObras = new ArrayList<Obra>();
		while (rowobras.next()) {
			int id = rowobras.getInt("ID");
			String codigo = rowobras.getString("Codigo");
			String nombreDeObra = rowobras.getString("NombreDeObra");
			Obra obra = new Obra(id, codigo, nombreDeObra);
			listaObras.add(obra);
		}
		return listaObras;
	}

	// HOME
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model template) {
		String variableDeSesion = (String) request.getSession().getAttribute("miVariable");
		if (variableDeSesion == null) {
			return "redirect:" + "/ingresar";
		}	
		return "home";
	}
	
	// CLIENTE
	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public String cliente(Model template) {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "cliente";

		}
	}

	// OFICINA TECNICA
	@RequestMapping(value = "/oficinatecnica", method = RequestMethod.GET)
	public String oficinaTecnica(Model template) {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "oficinaTecnica";

		}
	}
	
	// REMITOS
	@RequestMapping(value = "/remitos", method = RequestMethod.GET)
	public String remitos(Model template) {
		if(validarAjuste()) {
			return "home";
		}
		else {
			return "remitos";

		}
	}

	// USUARIOS ANDROID
	@RequestMapping(value = "/usuariosAndroid", method = RequestMethod.GET)
	public String usuariosAndroid(Model template) {

		return "usuariosAndroid";
	}

	// USUARIOS WEB
	@RequestMapping(value = "/usuariosWeb", method = RequestMethod.GET)
	public String usuariosWeb(Model template) {

		return "usuariosWeb";
	}

	// REDIRECIONAR PARA IMPRIMIR EL REMITOACERBRAG DESDE LISTA DE REMITOS
	@RequestMapping(value = "/mostrarRemitosAcerBrag/{id}", method = RequestMethod.GET)
	public String remitosAcerBrag(@PathVariable(value = "id") int idRemito, Model template) {
		int cont = 0;
		// REMITOS
		SqlRowSet rowRemitos;
		rowRemitos = jdbcTemplate.queryForRowSet("SELECT * FROM remitosacerbrag WHERE ID = ?", idRemito);
		rowRemitos.next();
		// REMITOS PEDIDOS
		String obserRem = rowRemitos.getString("Observaciones");
		String remitimosAUd = rowRemitos.getString("RemitimosAUd");
		String codigoDePedido1 = "";
		String codigoDePedido2 = "";
		boolean pedido2 = false;
		String codigoDePedido3 = "";
		boolean pedido3 = false;
		SqlRowSet rowRemitosPedidos;
		ArrayList<RemitoPedido> listaPedFormRem1 = new ArrayList<RemitoPedido>();
		ArrayList<RemitoPedido> listaPedFormRem2 = new ArrayList<RemitoPedido>();
		ArrayList<RemitoPedido> listaPedFormRem3 = new ArrayList<RemitoPedido>();
		rowRemitosPedidos = jdbcTemplate.queryForRowSet("SELECT * FROM remitospedidosacerbrag WHERE NumeroDeRemito = ?",
				rowRemitos.getString("NumeroDeRemito"));
		rowRemitosPedidos.next();
		codigoDePedido1 = rowRemitosPedidos.getString("CodigoDePedido");
		// BUSCO LA DESCRIPCION DEL PEDIDO POR MEDIO DEL CODIGO
		SqlRowSet pedidoCodigoUno;
		pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", codigoDePedido1);
		pedidoCodigoUno.next();
		String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
		String descripcionCodigoDos = "";
		String descripcionCodigoTres = "";
		// BUSCO DATOS DEL CLIENTE POR CODIGO DE CLIENTE
		String codigoCliente = codigoDePedido1.charAt(0) + "" + codigoDePedido1.charAt(1) + ""
				+ codigoDePedido1.charAt(2);
		SqlRowSet rowCliente;
		rowCliente = jdbcTemplate.queryForRowSet("SELECT * FROM clientes WHERE Codigo = ? ", codigoCliente);
		rowCliente.next();
		String cliente = rowCliente.getString("NombreFantasia");
		String cuil = rowCliente.getString("NroCuil");
		// BUSCO LA DIRECCION DE LA OBRA POR EL NOMBRE DE FANTASIA DEL CLIENTE
		SqlRowSet rowObra;
		rowObra = jdbcTemplate.queryForRowSet("SELECT * FROM clientesenobras WHERE NombreFantasia = ? ", cliente);
		rowObra.next();
		String domicilio = rowObra.getString("Direccion");
		rowRemitosPedidos = jdbcTemplate.queryForRowSet("SELECT * FROM remitospedidosacerbrag WHERE NumeroDeRemito = ?",
				rowRemitos.getString("NumeroDeRemito"));
		int totalGlobal = 0;
		while (rowRemitosPedidos.next()) {
			long id = rowRemitosPedidos.getLong("ID");
			String numeroDeRemito = rowRemitosPedidos.getString("NumeroDeRemito");
			String codigoDePedido = rowRemitosPedidos.getString("CodigoDePedido");
			String codigo = rowRemitosPedidos.getString("Codigo");
			String descripcion = rowRemitosPedidos.getString("Descripcion");
			String um = rowRemitosPedidos.getString("Um");
			String total = rowRemitosPedidos.getString("Total");
			totalGlobal += Integer.parseInt(rowRemitosPedidos.getString("Total"));
			RemitoPedido remitoPedido = new RemitoPedido(id, numeroDeRemito, codigoDePedido, codigo, descripcion, um,
					total);
			if (codigoDePedido1.equals(codigoDePedido)) {
				listaPedFormRem1.add(remitoPedido);
			} else if (!codigoDePedido1.equals(codigoDePedido) && cont == 0) {
				codigoDePedido2 = remitoPedido.getCodigoDePedido();
				cont = 1;
			}
			if (codigoDePedido2.equals(codigoDePedido)) {
				listaPedFormRem2.add(remitoPedido);
				pedido2 = true;
			} else if (!codigoDePedido2.equals(codigoDePedido) && cont == 1) {
				codigoDePedido3 = remitoPedido.getCodigoDePedido();
			}
			if (codigoDePedido3.equals(codigoDePedido)) {
				listaPedFormRem3.add(remitoPedido);
				pedido3 = true;
			}
		}
		if (!codigoDePedido2.equals("")) {
			SqlRowSet pedidoCodigoDos;
			pedidoCodigoDos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", codigoDePedido2);
			pedidoCodigoDos.next();
			descripcionCodigoDos = pedidoCodigoDos.getString("Descripcion");
		}
		if (!codigoDePedido3.equals("")) {
			SqlRowSet pedidoCodigoTres;
			pedidoCodigoTres = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", codigoDePedido3);
			pedidoCodigoTres.next();
			descripcionCodigoTres = pedidoCodigoTres.getString("Descripcion");
		}

		template.addAttribute("obserRem", obserRem);
		template.addAttribute("remitimosAUd", remitimosAUd);
		template.addAttribute("fechaRemito", rowRemitos.getString("Fecha"));
		template.addAttribute("cliente", cliente);
		template.addAttribute("domicilio", domicilio);
		template.addAttribute("cuil", cuil);
		template.addAttribute("totalGlobal", totalGlobal);
		template.addAttribute("CodigoDePedido1", codigoDePedido1);
		template.addAttribute("CodigoDePedido2", codigoDePedido2);
		template.addAttribute("CodigoDePedido3", codigoDePedido3);
		template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
		template.addAttribute("descripcionCodigoDos", descripcionCodigoDos);
		template.addAttribute("descripcionCodigoTres", descripcionCodigoTres);
		template.addAttribute("listaPedFormRem1", listaPedFormRem1);
		template.addAttribute("listaPedFormRem2", listaPedFormRem2);
		template.addAttribute("listaPedFormRem3", listaPedFormRem3);
		template.addAttribute("pedido2", pedido2);
		template.addAttribute("pedido3", pedido3);
		return "imprimirRemitoAcerBrag";
	}

	// REDIRECIONAR PARA IMPRIMIR EL REMITO DESDE LISTA DE REMITOS
	@RequestMapping(value = "/mostrarRemitos/{id}", method = RequestMethod.GET)
	public String remitos(@PathVariable(value = "id") int idRemito, Model template) {
		int cont = 0;

		// REMITOS
		SqlRowSet rowRemitos;
		rowRemitos = jdbcTemplate.queryForRowSet("SELECT * FROM remitos WHERE ID = ?", idRemito);
		rowRemitos.next();

		// REMITOS PEDIDOS
		String obserRem = rowRemitos.getString("Observaciones");
		String remitimosAUd = rowRemitos.getString("RemitimosAUd");
		String codigoDePedido1 = "";
		String codigoDePedido2 = "";
		boolean pedido2 = false;
		String codigoDePedido3 = "";
		boolean pedido3 = false;
		SqlRowSet rowRemitosPedidos;
		ArrayList<RemitoPedido> listaPedFormRem1 = new ArrayList<RemitoPedido>();
		ArrayList<RemitoPedido> listaPedFormRem2 = new ArrayList<RemitoPedido>();
		ArrayList<RemitoPedido> listaPedFormRem3 = new ArrayList<RemitoPedido>();
		rowRemitosPedidos = jdbcTemplate.queryForRowSet("SELECT * FROM remitospedidos WHERE NumeroDeRemito = ?",
				rowRemitos.getString("NumeroDeRemito"));
		rowRemitosPedidos.next();
		codigoDePedido1 = rowRemitosPedidos.getString("CodigoDePedido");

		// BUSCO LA DESCRIPCION DEL PEDIDO POR MEDIO DEL CODIGO
		SqlRowSet pedidoCodigoUno;
		pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", codigoDePedido1);
		pedidoCodigoUno.next();
		String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
		String descripcionCodigoDos = "";
		String descripcionCodigoTres = "";

		// BUSCO DATOS DEL CLIENTE POR CODIGO DE CLIENTE
		String codigoCliente = codigoDePedido1.charAt(0) + "" + codigoDePedido1.charAt(1) + ""
				+ codigoDePedido1.charAt(2);
		SqlRowSet rowCliente;
		rowCliente = jdbcTemplate.queryForRowSet("SELECT * FROM clientes WHERE Codigo = ? ", codigoCliente);
		rowCliente.next();
		String cliente = rowCliente.getString("NombreFantasia");
		String cuil = rowCliente.getString("NroCuil");

		String codigoObra = codigoDePedido1.substring(3, 5);
		System.out.println("Codigo Obra: " + codigoObra);
		SqlRowSet sqlRowObra;
		sqlRowObra = jdbcTemplate.queryForRowSet("SELECT * FROM obra WHERE Codigo = ?", codigoObra);
		sqlRowObra.next();
		String nombreObra = sqlRowObra.getString("NombreDeObra");

		// BUSCO LA DIRECCION DE LA OBRA POR EL NOMBRE DE LA OBRA
		SqlRowSet rowObra;
		rowObra = jdbcTemplate.queryForRowSet("SELECT * FROM clientesenobras WHERE NombreDeObra = ? ", nombreObra);
		rowObra.next();
		String domicilio = rowObra.getString("Direccion");

		rowRemitosPedidos = jdbcTemplate.queryForRowSet("SELECT * FROM remitospedidos WHERE NumeroDeRemito = ?",
				rowRemitos.getString("NumeroDeRemito"));
		int totalGlobal = 0;
		while (rowRemitosPedidos.next()) {
			long id = rowRemitosPedidos.getLong("ID");
			String numeroDeRemito = rowRemitosPedidos.getString("NumeroDeRemito");
			String codigoDePedido = rowRemitosPedidos.getString("CodigoDePedido");
			String codigo = rowRemitosPedidos.getString("Codigo");
			String descripcion = rowRemitosPedidos.getString("Descripcion");
			String um = rowRemitosPedidos.getString("Um");
			String total = rowRemitosPedidos.getString("Total");
			totalGlobal += Integer.parseInt(rowRemitosPedidos.getString("Total"));
			RemitoPedido remitoPedido = new RemitoPedido(id, numeroDeRemito, codigoDePedido, codigo, descripcion, um,
					total);
			if (codigoDePedido1.equals(codigoDePedido)) {
				listaPedFormRem1.add(remitoPedido);
			} else if (!codigoDePedido1.equals(codigoDePedido) && cont == 0) {
				codigoDePedido2 = remitoPedido.getCodigoDePedido();
				cont = 1;
			}
			if (codigoDePedido2.equals(codigoDePedido)) {
				listaPedFormRem2.add(remitoPedido);
				pedido2 = true;
			} else if (!codigoDePedido2.equals(codigoDePedido) && cont == 1) {
				codigoDePedido3 = remitoPedido.getCodigoDePedido();
			}
			if (codigoDePedido3.equals(codigoDePedido)) {
				listaPedFormRem3.add(remitoPedido);
				pedido3 = true;
			}
		}
		if (!codigoDePedido2.equals("")) {
			SqlRowSet pedidoCodigoDos;
			pedidoCodigoDos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", codigoDePedido2);
			pedidoCodigoDos.next();
			descripcionCodigoDos = pedidoCodigoDos.getString("Descripcion");
		}
		if (!codigoDePedido3.equals("")) {
			SqlRowSet pedidoCodigoTres;
			pedidoCodigoTres = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", codigoDePedido3);
			pedidoCodigoTres.next();
			descripcionCodigoTres = pedidoCodigoTres.getString("Descripcion");
		}

		template.addAttribute("obserRem", obserRem);
		template.addAttribute("remitimosAUd", remitimosAUd);
		template.addAttribute("fechaRemito", rowRemitos.getString("Fecha"));
		template.addAttribute("cliente", cliente);
		template.addAttribute("domicilio", domicilio);
		template.addAttribute("cuil", cuil);
		template.addAttribute("totalGlobal", totalGlobal);
		template.addAttribute("CodigoDePedido1", codigoDePedido1);
		template.addAttribute("CodigoDePedido2", codigoDePedido2);
		template.addAttribute("CodigoDePedido3", codigoDePedido3);
		template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
		template.addAttribute("descripcionCodigoDos", descripcionCodigoDos);
		template.addAttribute("descripcionCodigoTres", descripcionCodigoTres);
		template.addAttribute("listaPedFormRem1", listaPedFormRem1);
		template.addAttribute("listaPedFormRem2", listaPedFormRem2);
		template.addAttribute("listaPedFormRem3", listaPedFormRem3);
		template.addAttribute("pedido2", pedido2);
		template.addAttribute("pedido3", pedido3);
		return "imprimirRemito";
	}

	// LISTA DE REMITOS
	@RequestMapping(value = "/remitos/listaRemitos", method = RequestMethod.GET)
	public String listaDeRemitos(Model template) {

		// TRAE A LOS REMITOS
		ArrayList<Remito> listaRemitos = traerRemitos();
		template.addAttribute("listaDeRemitos", listaRemitos);

		// SI NO HAY CLIENTES REGISTRADOS
		if (listaRemitos.size() == 0) {
			template.addAttribute("hayRemitos", false);
			return "resultados";
		}
		// SI HAY REMITOS REGISTRADOS
		else {
			return "listaDeRemitos";
		}
	}

	// LISTA DE REMITOS ACERBRAG
	@RequestMapping(value = "/remitos/listaRemitosAcerBrag", method = RequestMethod.GET)
	public String listaDeRemitosAcerBrag(Model template) {

		// TRAE A LOS REMITOS
		ArrayList<Remito> listaRemitos = traerRemitosAcerBrag();
		template.addAttribute("listaDeRemitos", listaRemitos);

		// SI NO HAY CLIENTES REGISTRADOS
		if (listaRemitos.size() == 0) {
			template.addAttribute("hayRemitos", false);
			return "resultados";
		}
		// SI HAY REMITOS REGISTRADOS
		else {
			return "listaDeRemitosAcerBrag";
		}
	}

	// IMPRIMIR REMITO - GET
	@RequestMapping(value = "/remitos/imprimirRemito", method = RequestMethod.GET)
	public String imprimirRemito(Model template) {

		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES (Sin repetidos o con
		// obras)
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT DISTINCT NombreFantasia FROM clientesenobras");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}

		if (listaClientes.size() == 0) {
			template.addAttribute("hayClientes", false);
			template.addAttribute("hayObras", false);
			return "resultados";
		}

		ArrayList<CliEnOb> obras = traerobrasceo();
		if (obras.size() == 0) {
			template.addAttribute("hayObras", false);
			return "resultados";
		}

		// NRO DE REMITO - Trae los ultimos 3 digitos del numero
		// TODO DA ERROR SI NO HAY NINGUN REMITO CARGADO!
		SqlRowSet nroRemito;
		nroRemito = jdbcTemplate.queryForRowSet("SELECT NumeroDeRemito FROM remitos ORDER BY NumeroDeRemito ASC");
		ArrayList<Integer> listaRemitos = new ArrayList<Integer>();
		while (nroRemito.next()) {
			String nRemito = nroRemito.getString("NumeroDeRemito");
			int nRem = Integer.parseInt(nRemito.substring(5, 13));
			listaRemitos.add(nRem);
		}

		/*
		 * for (int i=0;i<listaRemitos.size();i++) {
		 * System.out.println(listaRemitos.get(i)); }
		 */

		// [ 0001-00000403 ] --> Formato remito

		int nro = 0;

		// Aca pregunta si listaRemitos es correlativo o no
		boolean esCorrelativo = true;
		for (int i = 1; i < listaRemitos.size(); i++) {
			int a = listaRemitos.get(i - 1) + 1;
			int b = listaRemitos.get(i);
			if (a != b) {
				esCorrelativo = false;
			}
		}

		// Si no es correlativo
		if (esCorrelativo == false) {
			int ultNroBase = listaRemitos.get(listaRemitos.size() - 1);
			int i = 600;
			int j = 0;
			while (i < ultNroBase) {
				if (listaRemitos.get(j) != i) {
					nro = i;
					break;
				}
				i++;
				j++;
			}
		}
		// Si es correlativo
		else {
			nro = listaRemitos.get(listaRemitos.size() - 1) + 1;
		}

		int numreeero = nro;
		String numero = "0001-00000" + numreeero;

		template.addAttribute("nroRemito", numero);

		template.addAttribute("lisClientes", listaClientes);
		return "nuevoRemito";
	}

	// IMPRIMIR REMITO - POST
	@RequestMapping(value = "/remitos/imprimirRemito", method = RequestMethod.POST)
	public String imprimirRemitoPost(Model template, @RequestParam(value = "cantidad") String cantidad,
			@RequestParam(value = "nroRemito") String nroRemito,
			@RequestParam(value = "fechaRemito") String fechaRemito, @RequestParam(value = "cliente") String cliente,
			@RequestParam(value = "obra") String obra, @RequestParam(value = "pedUno") String pedUno,
			@RequestParam(value = "pedDos") String pedDos, @RequestParam(value = "pedTres") String pedTres,
			@RequestParam(value = "obserRem") String obserRem,
			@RequestParam(value = "remitimosAUd") String remitimosAUd) {

		// VERIFICAR QUE NO SE REPITAN LOS NUMEROS DE REMITOS
		// BUSCAMOS TODOS LOS NUMEROS DE REMITOS CARGADOS
		SqlRowSet numerosDeRemitos;
		numerosDeRemitos = jdbcTemplate.queryForRowSet("SELECT NumeroDeRemito FROM remitos");
		while (numerosDeRemitos.next()) {
			if (numerosDeRemitos.getString("NumeroDeRemito").equals(nroRemito)) {
				template.addAttribute("numeroDeRemitoSinRepetir", false);
				return "resultados";
			}
		}

		// Trae a todos los clientes
		ArrayList<Cliente> listaClientes = traerClientes();
		String cuil = null;
		for (Cliente cli : listaClientes) {
			String nomFan = cli.getNombreFantasia();
			if (cliente.equals(nomFan)) {
				cuil = cli.getNroCuil();
			}
		}

		// Trae la direccion de la obra
		String domicilio = traerDireccionDeObraPorNomOb(obra);

		// Trae a los pedidos
		ArrayList<Pedido> listaPedidos = traerPedidosPorCliente(cliente, obra);
		ArrayList<Pedido> listaPe1 = null;
		ArrayList<Pedido> listaPe2 = null;
		ArrayList<Pedido> listaPe3 = null;
		double totalGlobal = 0;

		// Si es un pedido
		if (cantidad.equals("1")) {
			listaPe1 = new ArrayList<Pedido>();
			for (Pedido p1 : listaPedidos) {
				String codigo = p1.getCodigo();
				if (codigo.equals(pedUno)) {
					listaPe1.add(p1);
					totalGlobal = Double.parseDouble(p1.getTotalKg());
				}
			}

			// Si son dos pedidos
		} else if (cantidad.equals("2")) {
			listaPe1 = new ArrayList<Pedido>();
			listaPe2 = new ArrayList<Pedido>();
			for (Pedido p1 : listaPedidos) {
				String codigo = p1.getCodigo();
				if (codigo.equals(pedUno)) {
					listaPe1.add(p1);
					totalGlobal = Double.parseDouble(p1.getTotalKg());
				}
			}
			for (Pedido p2 : listaPedidos) {
				String codigo = p2.getCodigo();
				if (codigo.equals(pedDos)) {
					listaPe2.add(p2);
					totalGlobal += Double.parseDouble(p2.getTotalKg());
				}
			}

			// Si son tres pedidos
		} else if (cantidad.equals("3")) {
			listaPe1 = new ArrayList<Pedido>();
			listaPe2 = new ArrayList<Pedido>();
			listaPe3 = new ArrayList<Pedido>();
			for (Pedido p1 : listaPedidos) {
				String codigo = p1.getCodigo();
				if (codigo.equals(pedUno)) {
					listaPe1.add(p1);
					totalGlobal = Double.parseDouble(p1.getTotalKg());
				}
			}
			for (Pedido p2 : listaPedidos) {
				String codigo = p2.getCodigo();
				if (codigo.equals(pedDos)) {
					listaPe2.add(p2);
					totalGlobal += Double.parseDouble(p2.getTotalKg());
				}
			}
			for (Pedido p3 : listaPedidos) {
				String codigo = p3.getCodigo();
				if (codigo.equals(pedTres)) {
					listaPe3.add(p3);
					totalGlobal += Double.parseDouble(p3.getTotalKg());
				}
			}
		}

		// INGRESAR EMCABEZADO DEL REMITO A LA BASE DE DATOS
		this.jdbcTemplate.update("INSERT INTO remitos (NumeroDeRemito, Fecha, Estado) VALUES(?, ?, ?);", nroRemito,
				fechaRemito, "Activo");

		// INGRESAR OBSERVACIONES Y REMITIMOS A USTED EN LA BASE DE DATOS
		if (obserRem.length() != 0 || remitimosAUd.length() != 0) {
			this.jdbcTemplate.update("UPDATE remitos SET Observaciones=?, RemitimosAUd=? WHERE NumeroDeRemito=?;",
					obserRem, remitimosAUd, nroRemito);
		}

		if (cantidad.equals("1")) {
			// UN PEDIDO
			// TRAER PEDIDO PARA USAR LA DESCRIPCION
			SqlRowSet pedidoCodigoUno;
			pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedUno);
			pedidoCodigoUno.next();
			String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
			// METE UN ARRAYLIST EL CONTENIDO DE UN PEDIDO
			ArrayList<pedFormatoRemito> listaPedFormRem1 = armarListaFormatoRemito(listaPe1);
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem1.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidos (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedUno, listaPedFormRem1.get(posicion).getCodigo(),
						listaPedFormRem1.get(posicion).getDescripcion(), listaPedFormRem1.get(posicion).getUm(),
						listaPedFormRem1.get(posicion).getTotal());
			}
			template.addAttribute("obserRem", obserRem);
			template.addAttribute("remitimosAUd", remitimosAUd);
			template.addAttribute("CodigoDePedido1", pedUno);
			template.addAttribute("listaPedFormRem1", listaPedFormRem1);
			template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
			template.addAttribute("CodigoDePedido2", "");
			template.addAttribute("CodigoDePedido3", "");
		} else if (cantidad.equals("2")) {
			// DOS PEDIDOS
			// TRAER PEDIDO PARA USAR LA DESCRIPCION
			SqlRowSet pedidoCodigoUno;
			pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedUno);
			pedidoCodigoUno.next();
			String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
			SqlRowSet pedidoCodigoDos;
			pedidoCodigoDos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedDos);
			pedidoCodigoDos.next();
			String descripcionCodigoDos = pedidoCodigoDos.getString("Descripcion");
			// METE EN DOS ARRAYLIST DIFERENTES EL CONTENIDO DE CADA PEDIDO
			ArrayList<pedFormatoRemito> listaPedFormRem1 = armarListaFormatoRemito(listaPe1);
			ArrayList<pedFormatoRemito> listaPedFormRem2 = armarListaFormatoRemito(listaPe2);
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem1.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidos (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedUno, listaPedFormRem1.get(posicion).getCodigo(),
						listaPedFormRem1.get(posicion).getDescripcion(), listaPedFormRem1.get(posicion).getUm(),
						listaPedFormRem1.get(posicion).getTotal());
			}
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem2.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidos (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedDos, listaPedFormRem2.get(posicion).getCodigo(),
						listaPedFormRem2.get(posicion).getDescripcion(), listaPedFormRem2.get(posicion).getUm(),
						listaPedFormRem2.get(posicion).getTotal());
			}
			template.addAttribute("obserRem", obserRem);
			template.addAttribute("remitimosAUd", remitimosAUd);
			template.addAttribute("CodigoDePedido1", pedUno);
			template.addAttribute("listaPedFormRem1", listaPedFormRem1);
			template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
			template.addAttribute("pedido2", true);
			template.addAttribute("CodigoDePedido2", pedDos);
			template.addAttribute("listaPedFormRem2", listaPedFormRem2);
			template.addAttribute("descripcionCodigoDos", descripcionCodigoDos);
		} else if (cantidad.equals("3")) {
			// TRES PEDIDOS
			// TRAER PEDIDO PARA USAR LA DESCRIPCION
			SqlRowSet pedidoCodigoUno;
			pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedUno);
			pedidoCodigoUno.next();
			String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
			SqlRowSet pedidoCodigoDos;
			pedidoCodigoDos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedDos);
			pedidoCodigoDos.next();
			String descripcionCodigoDos = pedidoCodigoDos.getString("Descripcion");
			SqlRowSet pedidoCodigoTres;
			pedidoCodigoTres = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedTres);
			pedidoCodigoTres.next();
			String descripcionCodigoTres = pedidoCodigoTres.getString("Descripcion");
			// METE EN TRES ARRAYLIST DIFERENTES EL CONTENIDO DE CADA PEDIDO
			ArrayList<pedFormatoRemito> listaPedFormRem1 = armarListaFormatoRemito(listaPe1);
			ArrayList<pedFormatoRemito> listaPedFormRem2 = armarListaFormatoRemito(listaPe2);
			ArrayList<pedFormatoRemito> listaPedFormRem3 = armarListaFormatoRemito(listaPe3);
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem1.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidos (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedUno, listaPedFormRem1.get(posicion).getCodigo(),
						listaPedFormRem1.get(posicion).getDescripcion(), listaPedFormRem1.get(posicion).getUm(),
						listaPedFormRem1.get(posicion).getTotal());
			}
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem2.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidos (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedDos, listaPedFormRem2.get(posicion).getCodigo(),
						listaPedFormRem2.get(posicion).getDescripcion(), listaPedFormRem2.get(posicion).getUm(),
						listaPedFormRem2.get(posicion).getTotal());
			}
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem3.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidos (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedTres, listaPedFormRem3.get(posicion).getCodigo(),
						listaPedFormRem3.get(posicion).getDescripcion(), listaPedFormRem3.get(posicion).getUm(),
						listaPedFormRem3.get(posicion).getTotal());
			}
			template.addAttribute("obserRem", obserRem);
			template.addAttribute("remitimosAUd", remitimosAUd);
			template.addAttribute("CodigoDePedido1", pedUno);
			template.addAttribute("listaPedFormRem1", listaPedFormRem1);
			template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
			template.addAttribute("pedido2", true);
			template.addAttribute("CodigoDePedido2", pedDos);
			template.addAttribute("listaPedFormRem2", listaPedFormRem2);
			template.addAttribute("descripcionCodigoDos", descripcionCodigoDos);
			template.addAttribute("pedido3", true);
			template.addAttribute("CodigoDePedido3", pedTres);
			template.addAttribute("listaPedFormRem3", listaPedFormRem3);
			template.addAttribute("descripcionCodigoTres", descripcionCodigoTres);
		}

		// Envia el TOTAL al template
		template.addAttribute("totalGlobal", totalGlobal);

		// Envio de datos al template (van siempre)
		template.addAttribute("fechaRemito", fechaRemito);
		template.addAttribute("cliente", cliente);
		template.addAttribute("domicilio", domicilio);
		template.addAttribute("cuil", cuil);
		return "imprimirRemito";
	}

	// IMPRIMIR REMITO ACERBRAG - GET
	@RequestMapping(value = "/remitos/imprimirRemitoAcerBrag", method = RequestMethod.GET)
	public String imprimirRemitoAcerBrag(Model template) {

		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES (Sin repetidos o con
		// obras)
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT DISTINCT NombreFantasia FROM clientesenobras");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}

		if (listaClientes.size() == 0) {
			template.addAttribute("hayClientes", false);
			template.addAttribute("hayObras", false);
			return "resultados";
		}

		ArrayList<CliEnOb> obras = traerobrasceo();
		if (obras.size() == 0) {
			template.addAttribute("hayObras", false);
			return "resultados";
		}

		// NRO DE REMITO - Trae los ultimos 3 digitos del numero
		// DA ERROR SI NO HAY NINGUN REMITO CARGADO!
		SqlRowSet nroRemito;
		nroRemito = jdbcTemplate
				.queryForRowSet("SELECT NumeroDeRemito FROM remitosacerbrag ORDER BY NumeroDeRemito ASC");
		ArrayList<Integer> listaRemitos = new ArrayList<Integer>();
		while (nroRemito.next()) {
			String nRemito = nroRemito.getString("NumeroDeRemito");
			int nRem = Integer.parseInt(nRemito.substring(5, 13));
			listaRemitos.add(nRem);
		}

		// [ 0001-00000001 ] --> Formato remito

		int nro = 0;

		// Aca pregunta si listaRemitos es correlativo o no
		boolean esCorrelativo = true;
		for (int i = 1; i < listaRemitos.size(); i++) {
			int a = listaRemitos.get(i - 1) + 1;
			int b = listaRemitos.get(i);
			if (a != b) {
				esCorrelativo = false;
			}
		}

		// Si no es correlativo
		if (esCorrelativo == false) {
			int ultNroBase = listaRemitos.get(listaRemitos.size() - 1);
			int i = 1;
			int j = 0;
			while (i < ultNroBase) {
				if (listaRemitos.get(j) != i) {
					nro = i;
					break;
				}
				i++;
				j++;
			}
		}
		// Si es correlativo
		else {
			nro = listaRemitos.get(listaRemitos.size() - 1) + 1;
		}
		// DEFERNTE AL DE REMITOS, NUEVA VALIDACION PORQUE EMPIEZA DEL 1
		int numreeero = nro;
		String numero = "";
		if (nro >= 1 && nro <= 9) {
			numero = "0001-0000000" + numreeero;
		} else if (nro >= 10 && nro <= 99) {
			numero = "0001-000000" + numreeero;
		} else if (nro >= 100 && nro <= 999) {
			numero = "0001-00000" + numreeero;
		} else if (nro >= 1000 && nro <= 9999) {
			numero = "0001-0000" + numreeero;
		} else if (nro >= 10000 && nro <= 99999) {
			numero = "0001-000" + numreeero;
		}

		template.addAttribute("nroRemito", numero);

		template.addAttribute("lisClientes", listaClientes);
		return "nuevoRemitoAcerBrag";
	}

	// IMPRIMIR REMITO ACERBRAG - POST
	@RequestMapping(value = "/remitos/imprimirRemitoAcerBrag", method = RequestMethod.POST)
	public String imprimirRemitoPostAcerBrag(Model template, @RequestParam(value = "cantidad") String cantidad,
			@RequestParam(value = "nroRemito") String nroRemito,
			@RequestParam(value = "fechaRemito") String fechaRemito, @RequestParam(value = "cliente") String cliente,
			@RequestParam(value = "obra") String obra, @RequestParam(value = "pedUno") String pedUno,
			@RequestParam(value = "pedDos") String pedDos, @RequestParam(value = "pedTres") String pedTres,
			@RequestParam(value = "obserRem") String obserRem,
			@RequestParam(value = "remitimosAUd") String remitimosAUd) {

		// VERIFICAR QUE NO SE REPITAN LOS NUMEROS DE REMITOS
		// BUSCAMOS TODOS LOS NUMEROS DE REMITOS CARGADOS
		SqlRowSet numerosDeRemitos;
		numerosDeRemitos = jdbcTemplate.queryForRowSet("SELECT NumeroDeRemito FROM remitosacerbrag");
		while (numerosDeRemitos.next()) {
			if (numerosDeRemitos.getString("NumeroDeRemito").equals(nroRemito)) {
				template.addAttribute("numeroDeRemitoSinRepetir", false);
				return "resultados";
			}
		}

		// Trae a todos los clientes
		ArrayList<Cliente> listaClientes = traerClientes();
		String cuil = null;
		for (Cliente cli : listaClientes) {
			String nomFan = cli.getNombreFantasia();
			if (cliente.equals(nomFan)) {
				cuil = cli.getNroCuil();
			}
		}

		// Trae la direccion de la obra
		String domicilio = traerDireccionDeObraPorNomOb(obra);

		// Trae a los pedidos
		ArrayList<Pedido> listaPedidos = traerPedidosPorCliente(cliente, obra);
		ArrayList<Pedido> listaPe1 = null;
		ArrayList<Pedido> listaPe2 = null;
		ArrayList<Pedido> listaPe3 = null;
		int totalGlobal = 0;

		// Si es un pedido
		if (cantidad.equals("1")) {
			listaPe1 = new ArrayList<Pedido>();
			for (Pedido p1 : listaPedidos) {
				String codigo = p1.getCodigo();
				if (codigo.equals(pedUno)) {
					listaPe1.add(p1);
					totalGlobal = Integer.parseInt(p1.getTotalKg());
				}
			}

			// Si son dos pedidos
		} else if (cantidad.equals("2")) {
			listaPe1 = new ArrayList<Pedido>();
			listaPe2 = new ArrayList<Pedido>();
			for (Pedido p1 : listaPedidos) {
				String codigo = p1.getCodigo();
				if (codigo.equals(pedUno)) {
					listaPe1.add(p1);
					totalGlobal = Integer.parseInt(p1.getTotalKg());
				}
			}
			for (Pedido p2 : listaPedidos) {
				String codigo = p2.getCodigo();
				if (codigo.equals(pedDos)) {
					listaPe2.add(p2);
					totalGlobal += Integer.parseInt(p2.getTotalKg());
				}
			}

			// Si son tres pedidos
		} else if (cantidad.equals("3")) {
			listaPe1 = new ArrayList<Pedido>();
			listaPe2 = new ArrayList<Pedido>();
			listaPe3 = new ArrayList<Pedido>();
			for (Pedido p1 : listaPedidos) {
				String codigo = p1.getCodigo();
				if (codigo.equals(pedUno)) {
					listaPe1.add(p1);
					totalGlobal = Integer.parseInt(p1.getTotalKg());
				}
			}
			for (Pedido p2 : listaPedidos) {
				String codigo = p2.getCodigo();
				if (codigo.equals(pedDos)) {
					listaPe2.add(p2);
					totalGlobal += Integer.parseInt(p2.getTotalKg());
				}
			}
			for (Pedido p3 : listaPedidos) {
				String codigo = p3.getCodigo();
				if (codigo.equals(pedTres)) {
					listaPe3.add(p3);
					totalGlobal += Integer.parseInt(p3.getTotalKg());
				}
			}
		}

		// INGRESAR EMCABEZADO DEL REMITO A LA BASE DE DATOS
		this.jdbcTemplate.update("INSERT INTO remitosacerbrag (NumeroDeRemito, Fecha, Estado) VALUES(?, ?, ?);",
				nroRemito, fechaRemito, "Activo");
		// INGRESAR OBSERVACIONES Y REMITIMOS A USTED EN LA BASE DE DATOS
		if (obserRem.length() != 0 && remitimosAUd.length() != 0) {
			this.jdbcTemplate.update(
					"UPDATE remitosacerbrag SET Observaciones=?, RemitimosAUd=? WHERE NumeroDeRemito=?;", obserRem,
					remitimosAUd, nroRemito);
		}

		if (cantidad.equals("1")) {
			// UN PEDIDO
			// TRAER PEDIDO PARA USAR LA DESCRIPCION
			SqlRowSet pedidoCodigoUno;
			pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedUno);
			pedidoCodigoUno.next();
			String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
			// METE UN ARRAYLIST EL CONTENIDO DE UN PEDIDO
			ArrayList<pedFormatoRemito> listaPedFormRem1 = armarListaFormatoRemito(listaPe1);
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem1.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidosacerbrag (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedUno, listaPedFormRem1.get(posicion).getCodigo(),
						listaPedFormRem1.get(posicion).getDescripcion(), listaPedFormRem1.get(posicion).getUm(),
						listaPedFormRem1.get(posicion).getTotal());
			}
			template.addAttribute("obserRem", obserRem);
			template.addAttribute("remitimosAUd", remitimosAUd);
			template.addAttribute("CodigoDePedido1", pedUno);
			template.addAttribute("listaPedFormRem1", listaPedFormRem1);
			template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
			template.addAttribute("CodigoDePedido2", "");
			template.addAttribute("CodigoDePedido3", "");
		} else if (cantidad.equals("2")) {
			// DOS PEDIDOS
			// TRAER PEDIDO PARA USAR LA DESCRIPCION
			SqlRowSet pedidoCodigoUno;
			pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedUno);
			pedidoCodigoUno.next();
			String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
			SqlRowSet pedidoCodigoDos;
			pedidoCodigoDos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedDos);
			pedidoCodigoDos.next();
			String descripcionCodigoDos = pedidoCodigoUno.getString("Descripcion");
			// METE EN DOS ARRAYLIST DIFERENTES EL CONTENIDO DE CADA PEDIDO
			ArrayList<pedFormatoRemito> listaPedFormRem1 = armarListaFormatoRemito(listaPe1);
			ArrayList<pedFormatoRemito> listaPedFormRem2 = armarListaFormatoRemito(listaPe2);
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem1.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidosacerbrag (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedUno, listaPedFormRem1.get(posicion).getCodigo(),
						listaPedFormRem1.get(posicion).getDescripcion(), listaPedFormRem1.get(posicion).getUm(),
						listaPedFormRem1.get(posicion).getTotal());
			}
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem2.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidosacerbrag (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedDos, listaPedFormRem2.get(posicion).getCodigo(),
						listaPedFormRem2.get(posicion).getDescripcion(), listaPedFormRem2.get(posicion).getUm(),
						listaPedFormRem2.get(posicion).getTotal());
			}
			template.addAttribute("obserRem", obserRem);
			template.addAttribute("remitimosAUd", remitimosAUd);
			template.addAttribute("CodigoDePedido1", pedUno);
			template.addAttribute("listaPedFormRem1", listaPedFormRem1);
			template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
			template.addAttribute("pedido2", true);
			template.addAttribute("CodigoDePedido2", pedDos);
			template.addAttribute("listaPedFormRem2", listaPedFormRem2);
			template.addAttribute("descripcionCodigoDos", descripcionCodigoDos);
		} else if (cantidad.equals("3")) {
			// TRES PEDIDOS
			// TRAER PEDIDO PARA USAR LA DESCRIPCION
			SqlRowSet pedidoCodigoUno;
			pedidoCodigoUno = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedUno);
			pedidoCodigoUno.next();
			String descripcionCodigoUno = pedidoCodigoUno.getString("Descripcion");
			SqlRowSet pedidoCodigoDos;
			pedidoCodigoDos = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedDos);
			pedidoCodigoDos.next();
			String descripcionCodigoDos = pedidoCodigoDos.getString("Descripcion");
			SqlRowSet pedidoCodigoTres;
			pedidoCodigoTres = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE Codigo = ?", pedTres);
			pedidoCodigoTres.next();
			String descripcionCodigoTres = pedidoCodigoTres.getString("Descripcion");
			// METE EN TRES ARRAYLIST DIFERENTES EL CONTENIDO DE CADA PEDIDO
			ArrayList<pedFormatoRemito> listaPedFormRem1 = armarListaFormatoRemito(listaPe1);
			ArrayList<pedFormatoRemito> listaPedFormRem2 = armarListaFormatoRemito(listaPe2);
			ArrayList<pedFormatoRemito> listaPedFormRem3 = armarListaFormatoRemito(listaPe3);
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem1.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidosacerbrag (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedUno, listaPedFormRem1.get(posicion).getCodigo(),
						listaPedFormRem1.get(posicion).getDescripcion(), listaPedFormRem1.get(posicion).getUm(),
						listaPedFormRem1.get(posicion).getTotal());
			}
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem2.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidosacerbrag (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedDos, listaPedFormRem2.get(posicion).getCodigo(),
						listaPedFormRem2.get(posicion).getDescripcion(), listaPedFormRem2.get(posicion).getUm(),
						listaPedFormRem2.get(posicion).getTotal());
			}
			// INGRESAR CONTENIDO REMITO A LA BASE DE DATOS
			for (int posicion = 0; posicion < listaPedFormRem3.size(); posicion++) {
				this.jdbcTemplate.update(
						"INSERT INTO remitospedidosacerbrag (NumeroDeRemito , CodigoDePedido, Codigo, Descripcion, Um, Total) VALUES(?, ?, ?, ?, ?, ?);",
						nroRemito, pedTres, listaPedFormRem3.get(posicion).getCodigo(),
						listaPedFormRem3.get(posicion).getDescripcion(), listaPedFormRem3.get(posicion).getUm(),
						listaPedFormRem3.get(posicion).getTotal());
			}
			template.addAttribute("obserRem", obserRem);
			template.addAttribute("remitimosAUd", remitimosAUd);
			template.addAttribute("CodigoDePedido1", pedUno);
			template.addAttribute("listaPedFormRem1", listaPedFormRem1);
			template.addAttribute("descripcionCodigoUno", descripcionCodigoUno);
			template.addAttribute("pedido2", true);
			template.addAttribute("CodigoDePedido2", pedDos);
			template.addAttribute("listaPedFormRem2", listaPedFormRem2);
			template.addAttribute("descripcionCodigoDos", descripcionCodigoDos);
			template.addAttribute("pedido3", true);
			template.addAttribute("CodigoDePedido3", pedTres);
			template.addAttribute("listaPedFormRem3", listaPedFormRem3);
			template.addAttribute("descripcionCodigoTres", descripcionCodigoTres);
		}

		// Envia el TOTAL al template
		template.addAttribute("totalGlobal", totalGlobal);

		// Envio de datos al template (van siempre)
		template.addAttribute("fechaRemito", fechaRemito);
		template.addAttribute("cliente", cliente);
		template.addAttribute("domicilio", domicilio);
		template.addAttribute("cuil", cuil);
		return "imprimirRemitoAcerBrag";
	}

	// LISTA DE CLIENTES
	@RequestMapping(value = "/cliente/listadeclientes", method = RequestMethod.GET)
	public String listaDeClientes(Model template) {

		// TRAE A LOS CLIENTES
		ArrayList<Cliente> listaClientes = traerClientes();
		template.addAttribute("listaClientes", listaClientes);

		// SI NO HAY CLIENTES REGISTRADOS
		if (listaClientes.size() == 0) {
			template.addAttribute("hayClientes", false);
			return "resultados";
		}
		// SI HAY CLIENTES REGISTRADOS
		else {
			return "listaDeClientes";
		}
	}

	// LISTA DE OBRAS - GET
	@RequestMapping(value = "/obra/listadeobras", method = RequestMethod.GET)
	public String listaDeObrasGet(Model template) {

		// TRAE LOS NOMBRES DE FANTASIA DE LOS CLIENTES
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientes");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}
		template.addAttribute("lisClientes", listaClientes);

		// TRAE LOS NOMBRES DE LAS OBRAS
		SqlRowSet obras;
		obras = jdbcTemplate.queryForRowSet("SELECT NombreDeObra, ID FROM obra");
		ArrayList<Obra> listaObrass = new ArrayList<Obra>();
		while (obras.next()) {
			String nomObra = obras.getString("NombreDeObra");
			int id = obras.getInt("ID");
			Obra ob = new Obra(id, nomObra);
			listaObrass.add(ob);
		}
		template.addAttribute("lisOb", listaObrass);

		// TRAE A LAS OBRAS
		ArrayList<Obra> listaObras = traerObras();

		// TRAE A LAS OBRAS (CEO)
		ArrayList<CliEnOb> listaObrasCeo = traerobrasceo();
		template.addAttribute("listaObras", listaObrasCeo);

		// SI NO HAY CLIENTES REGISTRADOS
		if (listaObras.size() == 0) {
			template.addAttribute("hayObras", false);
			return "resultados";
		}
		// SI HAY CLIENTES REGISTRADOS
		else {
			return "listaDeObras";
		}
	}

	// LISTA DE OBRAS - POST
	@RequestMapping(value = "/obra/listadeobras", method = RequestMethod.POST)
	public String listaDeObrasPost(Model template, @RequestParam(value = "nombreFantasia") String nombreFantasia,
			@RequestParam(value = "nombreDeObra") String nombreDeObra,
			@RequestParam(value = "contacto") String contacto, @RequestParam(value = "telefono") String telefono,
			@RequestParam(value = "direccion") String direccion, @RequestParam(value = "condpago") String condpago) {

		SqlRowSet clienob;
		clienob = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientesenobras WHERE NombreDeObra = ?;",
				nombreDeObra);
		ArrayList<CliEnOb> lisCliEnOb = new ArrayList<CliEnOb>();
		while (clienob.next()) {
			String nombreFan = clienob.getString("NombreFantasia");
			CliEnOb ceo = new CliEnOb(nombreFan);
			lisCliEnOb.add(ceo);
		}
		if (lisCliEnOb.size() == 1) {
			if (lisCliEnOb.get(0).getNombreFantasia().equals(nombreFantasia)) {
				template.addAttribute("yaIngresado", true);
				return "resultados";
			} else {
				this.jdbcTemplate.update(
						"INSERT INTO clientesenobras (NombreFantasia, NombreDeObra, Contacto, Telefono, Direccion, CondPago) VALUES (?,?,?,?,?,?);",
						nombreFantasia, nombreDeObra, contacto, telefono, direccion, condpago);
			}
		} else {
			for (CliEnOb ceo : lisCliEnOb) {
				String nombreFan = ceo.getNombreFantasia();
				if (nombreFan.equals(nombreFantasia)) {
					template.addAttribute("yaIngresado", true);
					return "resultados";
				}
			}
			this.jdbcTemplate.update(
					"INSERT INTO clientesenobras (NombreFantasia, NombreDeObra, Contacto, Telefono, Direccion, CondPago) VALUES (?,?,?,?,?,?);",
					nombreFantasia, nombreDeObra, contacto, telefono, direccion, condpago);
		}

		return "redirect:" + "/obra/listadeobras";
	}

	// MODIFICAR CLIENTE - GET
	@RequestMapping("/cliente/modificar/{id}")
	public String modificarClienteGet(@PathVariable(value = "id") int idCliente, Model template) {

		// TRAE A LOS CLIENTES
		SqlRowSet rowclientes;
		rowclientes = jdbcTemplate.queryForRowSet("SELECT * FROM clientes WHERE ID = ?;", idCliente);
		rowclientes.next();
		// int id = rowclientes.getInt("ID");
		String codigo = rowclientes.getString("Codigo");
		String nombreFantasia = rowclientes.getString("NombreFantasia");
		String telefono = rowclientes.getString("Telefono");
		String razonSocial = rowclientes.getString("RazonSocial");
		String direccionFiscal = rowclientes.getString("DireccionFiscal");
		String nroCuil = rowclientes.getString("NroCuil");
		nroCuil = nroCuil.replace("-", "");
		template.addAttribute("nombreFantasia", nombreFantasia);
		template.addAttribute("codigo", codigo);
		template.addAttribute("nombreFantasia", nombreFantasia);
		template.addAttribute("telefono", telefono);
		template.addAttribute("razonSocial", razonSocial);
		template.addAttribute("direccionFiscal", direccionFiscal);
		template.addAttribute("nroCuil", nroCuil);
		return "modificarCliente";
	}

	// MODIFICAR CLIENTE - POST
	@RequestMapping(value = "/cliente/modificar/{id}", method = RequestMethod.POST)
	public String modificarClientePost(Model template, @PathVariable(value = "id") int idCliente,
			@RequestParam(value = "nroCuil") String nroCuil,
			@RequestParam(value = "nombreFantasia") String nombreFantasia,
			@RequestParam(value = "razonSocial") String razonSocial,
			@RequestParam(value = "direccionFiscal") String direccionFiscal,
			@RequestParam(value = "telefono") String telefono, @RequestParam(value = "codigo") String codigo) {

		// TRAE A LOS CLIENTES
		ArrayList<Cliente> listaClientes = traerClientes();
		template.addAttribute("listaClientes", listaClientes);

		// CUIL SIN GUIONES
		if (nroCuil.length() == 11) {
			String primera = nroCuil.charAt(0) + "" + nroCuil.charAt(1) + "-";
			String segunda = nroCuil.charAt(2) + "" + nroCuil.charAt(3) + "" + nroCuil.charAt(4) + ""
					+ nroCuil.charAt(5) + "" + nroCuil.charAt(6) + "" + nroCuil.charAt(7) + "" + nroCuil.charAt(8) + ""
					+ nroCuil.charAt(9) + "-";
			char tercera = nroCuil.charAt(10);
			nroCuil = primera + segunda + tercera;
		} else {
			template.addAttribute("cuilOK", false);
			return "resultados";
		}

		// MODIFICA AL CLIENTE (POR LO MENOS SE DEBE LLENAR EL CAMPO DE NOMBRE
		// DE FANTASIA, NRO CUIL Y CODIGO)
		int a, b, c;
		a = nombreFantasia.length();
		b = nroCuil.length();
		c = codigo.length();
		// SI SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		if (a != 0 && b != 0 && c != 0) {
			// SI TECLEA SOLO 3 DIGITOS EN EL CAMPO CODIGO
			if (codigo.length() == 3) {
				this.jdbcTemplate.update(
						"UPDATE clientes SET NroCuil=?, NombreFantasia=?, RazonSocial=?, DireccionFiscal=?, Telefono=?, Codigo=? WHERE ID=?;",
						nroCuil, nombreFantasia, razonSocial, direccionFiscal, telefono, codigo, idCliente);
				return "redirect:" + "/cliente/listadeclientes";
			}
			// SI TECLEA MAS O MENOS DE 3 DIGITOS EN EL CAMPO CODIGO
			else {
				template.addAttribute("tresDigitos", false);
				return "resultados";
			}
		}
		// SI NO SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// MODIFICAR OBRA - GET
	@RequestMapping("/obra/modificar/{id}")
	public String modificarObraGet(@PathVariable(value = "id") int idObra, Model template) {

		// TRAE A LAS OBRAS (clientes en obras)
		SqlRowSet rowobras;
		rowobras = jdbcTemplate.queryForRowSet("SELECT * FROM clientesenobras WHERE ID = ?;", idObra);
		rowobras.next();
		int id = rowobras.getInt("ID");
		String nombreDeObra = rowobras.getString("NombreDeObra");
		String contacto = rowobras.getString("Contacto");
		String telefono = rowobras.getString("Telefono");
		String direccion = rowobras.getString("Direccion");
		String condPago = rowobras.getString("CondPago");

		template.addAttribute("id", id);
		template.addAttribute("nombreDeObra", nombreDeObra);
		template.addAttribute("contacto", contacto);
		template.addAttribute("telefono", telefono);
		template.addAttribute("direccion", direccion);
		template.addAttribute("condPago", condPago);

		// TRAE EL CODIGO DE LA OBRA Y EL ID
		ArrayList<Obra> listaObras = traerObrasPorNomOb(nombreDeObra);
		String codigo = listaObras.get(0).getCodigo();
		String ido = Integer.toString(listaObras.get(0).getId());
		template.addAttribute("codigo", codigo);
		template.addAttribute("ido", ido);

		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientes");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}
		template.addAttribute("listaClientes", listaClientes);

		SqlRowSet clientesdelaobra;
		clientesdelaobra = jdbcTemplate.queryForRowSet("SELECT * FROM clientesenobras WHERE NombreDeObra = ?;",
				nombreDeObra);
		String[] cli = new String[50];
		int i = 0;
		while (clientesdelaobra.next()) {
			String cliente = clientesdelaobra.getString("NombreFantasia");
			cli[i] = cliente;
			i++;
		}
		int cont = 0;
		for (int i1 = 0; i1 < cli.length; i1++) {
			if (cli[i1] != null) {
				cont++;
			}
		}
		String[] clien = new String[cont];
		for (int i1 = 0; i1 < clien.length; i1++) {
			if (cli[i1] != "") {
				clien[i1] = cli[i1];
			}
		}
		template.addAttribute("clientes", clien);

		return "modificarObra";
	}

	// MODIFICAR OBRA - POST
	@RequestMapping(value = "/obra/modificar/{id}", method = RequestMethod.POST)
	public String modificarObraPost(Model template, @PathVariable(value = "id") int idObra,
			@RequestParam(value = "codigo") String codigo, @RequestParam(value = "nombreDeObra") String nombreDeObra,
			// @RequestParam(value = "nombreFantasia") String nombreFantasia,
			@RequestParam(value = "contacto") String contacto, @RequestParam(value = "telefono") String telefono,
			@RequestParam(value = "direccion") String direccion, @RequestParam(value = "condPago") String condPago,
			@RequestParam(value = "ido") String ido) {

		// TRAE A LAS OBRAS
		ArrayList<Obra> listaObras = traerObras();
		template.addAttribute("listaObras", listaObras);

		// MODIFICA A LA OBRA (POR LO MENOS SE DEBE LLENAR EL CAMPO DE NOMBRE DE
		// FANTASIA, NRO CUIL Y CODIGO)
		int a, b, d;
		a = codigo.length();
		b = nombreDeObra.length();
		// c = nombreFantasia.length();
		d = contacto.length();

		// SI SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		if (a != 0 && b != 0 && d != 0) {
			// SI TECLEA SOLO 2 DIGITOS EN EL CAMPO CODIGO
			if (codigo.length() == 2) {
				this.jdbcTemplate.update(
						"UPDATE clientesenobras SET Contacto=?, Telefono=?, Direccion=?, CondPago=? WHERE ID=?;",
						contacto, telefono, direccion, condPago, idObra);
				this.jdbcTemplate.update("UPDATE obra SET Codigo=?, NombreDeObra=? WHERE ID = ?;", codigo, nombreDeObra,
						ido);
				return "redirect:" + "/obra/listadeobras";
			}
			// SI TECLEA MAS O MENOS DE 2 DIGITOS EN EL CAMPO CODIGO
			else {
				template.addAttribute("dosDigitos", false);
				return "resultados";
			}
		}
		// SI NO SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// ELIMINAR CLIENTE
	@RequestMapping("/cliente/eliminar/{id}")
	public String eliminarCliente(@PathVariable(value = "id") int idCliente, Model template) {
		ArrayList<Cliente> cliente = traerClientesPorId(idCliente);
		ArrayList<CliEnOb> obras = traerobrasceoPorNomFan(cliente.get(0).getNombreFantasia());
		ArrayList<Pedido> pedidos = traerPedidosPorCliente(cliente.get(0).getNombreFantasia());
		if (pedidos.size() == 0 && obras.size() == 0) {
			jdbcTemplate.update("DELETE FROM clientes WHERE ID = ?;", idCliente);
			return "redirect:" + "/cliente/listadeclientes";
		} else if (pedidos.size() != 0) {
			template.addAttribute("noTieneObras", false);
			template.addAttribute("noTienePedidos", false);
			return "resultados";
		} else {
			template.addAttribute("noTieneObras", false);
			return "resultados";
		}

	}

	// ANULAR REMITO
	@RequestMapping("/remito/anular/{id}")
	public String anularRemito(@PathVariable(value = "id") int idItem, Model template) {
		// ACTUALIZAMOS EL ESTADO DEL REMITO
		jdbcTemplate.update("UPDATE remitos SET Estado = ? WHERE ID = ?;", "Anulado", idItem);
		// ELIMINAR LOS ITEMS CARGADOS AL REMITO
		// BUSCAMOS EL NUMERO DEL REMITO
		SqlRowSet remitos;
		remitos = jdbcTemplate.queryForRowSet("SELECT NumeroDeRemito FROM remitos WHERE ID = ? ", idItem);
		remitos.next();
		String numeroDeRemito = remitos.getString("NumeroDeRemito");
		// ELIMINAMOS ITEMS BAJO EL NUMERO DE REMITO EN REMITOSPEDIDOS
		jdbcTemplate.update("DELETE FROM remitospedidos WHERE NumeroDeRemito = ?;", numeroDeRemito);
		return "redirect:" + "/remitos/listaRemitos";
	}
	// FIN DE ANULAR REMITO

	// ANULAR REMITO ACERBRAG
	@RequestMapping("/remitoAcerBrag/anular/{id}")
	public String anularRemitoAcerBrag(@PathVariable(value = "id") int idItem, Model template) {
		// ACTUALIZAMOS EL ESTADO DEL REMITO
		jdbcTemplate.update("UPDATE remitosacerbrag SET Estado = ? WHERE ID = ?;", "Anulado", idItem);
		// ELIMINAR LOS ITEMS CARGADOS AL REMITO
		// BUSCAMOS EL NUMERO DEL REMITO
		SqlRowSet remitos;
		remitos = jdbcTemplate.queryForRowSet("SELECT NumeroDeRemito FROM remitosacerbrag WHERE ID = ? ", idItem);
		remitos.next();
		String numeroDeRemito = remitos.getString("NumeroDeRemito");
		// ELIMINAMOS ITEMS BAJO EL NUMERO DE REMITO EN REMITOSPEDIDOS
		jdbcTemplate.update("DELETE FROM remitospedidosacerbrag WHERE NumeroDeRemito = ?;", numeroDeRemito);
		return "redirect:" + "/remitos/listaRemitosAcerBrag";
	}
	// FIN DE ANULAR REMITO ACERBRAG

	// ACTIVAR REMITO
	@RequestMapping("/remito/activar/{id}")
	public String activarRemito(@PathVariable(value = "id") int idItem, Model template) {
		jdbcTemplate.update("UPDATE remitos SET Estado = ? WHERE ID = ?;", "Activo", idItem);
		return "redirect:" + "/remitos/listaRemitos";
	}
	// FIN DE ACTIVAR REMITO

	// ACTIVAR REMITO ACERBRAG
	@RequestMapping("/remitoAcerBrag/activar/{id}")
	public String activarRemitoAcerBrag(@PathVariable(value = "id") int idItem, Model template) {
		jdbcTemplate.update("UPDATE remitosacerbrag SET Estado = ? WHERE ID = ?;", "Activo", idItem);
		return "redirect:" + "/remitos/listaRemitosAcerBrag";
	}
	// FIN DE ACTIVAR REMITO ACERBRAG

	// MODIFICAR REMITO
	// GET
	@RequestMapping(value = "remito/modificar/{id}", method = RequestMethod.GET)
	public String modificarRemito(@PathVariable(value = "id") int idItem, Model template) {
		SqlRowSet remitosBD;
		remitosBD = jdbcTemplate.queryForRowSet("SELECT * FROM remitos WHERE ID = ?;", idItem);
		remitosBD.next();
		String numeroDeRemito = remitosBD.getString("NumeroDeRemito");
		String fecha = remitosBD.getString("Fecha");
		template.addAttribute("numeroDeRemito", numeroDeRemito);
		template.addAttribute("fecha", fecha);
		return "modificarRemito";
	}

	// POST
	@RequestMapping(value = "/remito/modificar/{id}", method = RequestMethod.POST)
	public String modifcarRemito(@PathVariable(value = "id") int idItem,
			@RequestParam(value = "numeroDeRemito") String numeroDeRemito, @RequestParam(value = "fecha") String fecha,
			Model template) {
		if (numeroDeRemito.length() != 0 && fecha.length() != 0) {
			this.jdbcTemplate.update("UPDATE remitos SET NumeroDeRemito = ?, Fecha = ? WHERE ID = ?;", numeroDeRemito,
					fecha, idItem);
			return "redirect:" + "/remitos/listaRemitos";
		} else {
			template.addAttribute("numeroDeRemito", numeroDeRemito);
			template.addAttribute("fecha", fecha);
			return "modificarRemito";
		}
	}
	// FIN DE MODIFICAR REMITO

	// MODIFICAR REMITO ACERBRAG
	// GET
	@RequestMapping(value = "remitoAcerBrag/modificar/{id}", method = RequestMethod.GET)
	public String modificarRemitoAcerBrag(@PathVariable(value = "id") int idItem, Model template) {
		SqlRowSet remitosBD;
		remitosBD = jdbcTemplate.queryForRowSet("SELECT * FROM remitosacerbrag WHERE ID = ?;", idItem);
		remitosBD.next();
		String numeroDeRemito = remitosBD.getString("NumeroDeRemito");
		String fecha = remitosBD.getString("Fecha");
		template.addAttribute("numeroDeRemito", numeroDeRemito);
		template.addAttribute("fecha", fecha);
		return "modificarRemitoAcerBrag";
	}

	// POST
	@RequestMapping(value = "/remitoAcerBrag/modificar/{id}", method = RequestMethod.POST)
	public String modifcarRemitoAcerBrag(@PathVariable(value = "id") int idItem,
			@RequestParam(value = "numeroDeRemito") String numeroDeRemito, @RequestParam(value = "fecha") String fecha,
			Model template) {
		if (numeroDeRemito.length() != 0 && fecha.length() != 0) {
			this.jdbcTemplate.update("UPDATE remitosAcerBrag SET NumeroDeRemito = ?, Fecha = ? WHERE ID = ?;",
					numeroDeRemito, fecha, idItem);
			return "redirect:" + "/remitos/listaRemitosAcerBrag";
		} else {
			template.addAttribute("numeroDeRemito", numeroDeRemito);
			template.addAttribute("fecha", fecha);
			return "modificarRemitoAcerBrag";
		}
	}
	// FIN DE MODIFICAR REMITO ACERBRAG

	// ELIMINAR CLIENTE DE OBRA
	/*
	 * @RequestMapping("/cliente/eliminar/{cliente}/{idObra}") public String
	 * eliminarClienteDeObra(@PathVariable(value = "cliente") String idCliente,
	 * 
	 * @PathVariable(value = "idObra") int idObra, Model template) {
	 * ArrayList<CliEnOb> obras = traerobrasceoPorNomFan(idCliente);
	 * ArrayList<Pedido> pedidos = traerPedidosPorCliente(idCliente);
	 * ArrayList<Obra> obra = traerObrasPorId(idObra); if (pedidos.size() == 0 &&
	 * obras.size() == 0) { jdbcTemplate.update(
	 * "DELETE FROM clientesenobras WHERE NombreFantasia = ? AND NombreDeObra=?;" ,
	 * idCliente, obra.get(0).getNombreDeObra()); return "redirect:" +
	 * "/obra/listadeobras"; } else if (pedidos.size() != 0) {
	 * template.addAttribute("noTieneObras", false);
	 * template.addAttribute("noTienePedidos", false); return "resultados"; } else {
	 * template.addAttribute("noTieneObras", false); return "resultados"; } }
	 */

	// ELIMINAR OBRA
	@RequestMapping("/obra/eliminar/{id}")
	public String eliminarObra(@PathVariable(value = "id") int idObra, Model template) {

		ArrayList<CliEnOb> listaObras = traerobrasceoPorID(idObra);
		String nombreDeObra = listaObras.get(0).getNombreDeObra();
		String nombreFantasia = listaObras.get(0).getNombreFantasia();

		ArrayList<Obra> lista = traerObrasPorNomOb(nombreDeObra);
		int idObra1 = lista.get(0).getId();

		ArrayList<CliEnOb> lista1 = traerobrasceoPorNomOb(nombreDeObra);

		ArrayList<Pedido> pedidos = traerPedidosPorCliente(nombreFantasia, nombreDeObra);
		// SI LA OBRA NO TIENE PEDIDOS
		if (pedidos.size() == 0) {
			// SI EN LA OBRA TRABAJA UN SOLO CLIENTE
			if (lista1.size() == 1) {
				jdbcTemplate.update("DELETE FROM clientesenobras WHERE ID = ?;", idObra);
				jdbcTemplate.update("DELETE FROM obra WHERE ID = ?;", idObra1);
				return "redirect:" + "/obra/listadeobras";
			}
			// SI EN LA OBRA TRABAJAN VARIOS CLIENTES
			else {
				jdbcTemplate.update("DELETE FROM clientesenobras WHERE ID = ?;", idObra);
				return "redirect:" + "/obra/listadeobras";
			}
		}
		// SI LA OBRA TIENE PEDIDOS
		else {
			template.addAttribute("noTienePedidosObra", false);
			return "resultados";
		}
	}

	// NUEVO CLIENTE - GET
	@RequestMapping(value = "/cliente/nuevocliente", method = RequestMethod.GET)
	public String nuevoCliente(Model template) {

		return "nuevoCliente";
	}

	// NUEVO CLIENTE - POST
	@RequestMapping(value = "/cliente/nuevocliente", method = RequestMethod.POST)
	public String NuevoProductoGet(Model template, @RequestParam(value = "nro_cuil") String nro_cuil,
			@RequestParam(value = "nombreDeFantasia") String nombreDeFantasia,
			@RequestParam(value = "razonSocial") String razonSocial,
			@RequestParam(value = "dirFiscal") String dirFiscal, @RequestParam(value = "telefono") String telefono,
			@RequestParam(value = "codigo") String codigo) {

		// CUIL
		if (nro_cuil.length() == 11) {
			String primera = nro_cuil.charAt(0) + "" + nro_cuil.charAt(1) + "-";
			String segunda = nro_cuil.charAt(2) + "" + nro_cuil.charAt(3) + "" + nro_cuil.charAt(4) + ""
					+ nro_cuil.charAt(5) + "" + nro_cuil.charAt(6) + "" + nro_cuil.charAt(7) + "" + nro_cuil.charAt(8)
					+ "" + nro_cuil.charAt(9) + "-";
			char tercera = nro_cuil.charAt(10);
			nro_cuil = primera + segunda + tercera;
		} else {
			template.addAttribute("cuilOK", false);
			return "resultados";
		}

		// PASAR A MAYUSCULA EL NOMBRE DEL CLIENTE
		nombreDeFantasia = nombreDeFantasia.toUpperCase();
		// TRAE A LOS CLIENTES
		ArrayList<Cliente> listaClientes = traerClientes();
		template.addAttribute("listaClientes", listaClientes);

		// EVALUA SI EL CODIGO YA EXISTE
		boolean codigoRepetido = false;
		for (Cliente c : listaClientes) {
			if (c.getCodigo().equals(codigo)) {
				codigoRepetido = true;
			}
		}

		// EVALUA SI EL NOMBRE DE FANTASIA YA EXISTE
		boolean nomFanRep = false;
		for (Cliente c : listaClientes) {
			if (c.getNombreFantasia().equals(nombreDeFantasia)) {
				nomFanRep = true;
			}
		}

		// CREA UN CLIENTE (POR LO MENOS SE DEBE LLENAR EL CAMPO DE NOMBRE DE
		// FANTASIA, NRO CUIL Y CODIGO)
		int a, b, c, d, e;
		a = nombreDeFantasia.length();
		b = nro_cuil.length();
		c = codigo.length();
		d = razonSocial.length();
		e = dirFiscal.length();
		// SI SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		if (a != 0 && b != 0 && c != 0 && d != 0 && e != 0) {
			// SI NO HAY CODIGO REPETIDO
			if (codigoRepetido == false) {
				// SI NO HAY NOMBRE DE FANTASIA REPETIDO
				if (nomFanRep == false) {
					// SI TECLEA SOLO 3 DIGITOS EN EL CAMPO CODIGO
					if (codigo.length() == 3) {
						this.jdbcTemplate.update(
								"INSERT INTO clientes (NroCuil, NombreFantasia, RazonSocial, DireccionFiscal, Telefono, Codigo) VALUES(?, ?, ?, ?, ?, ?);",
								nro_cuil, nombreDeFantasia, razonSocial, dirFiscal, telefono, codigo);
						return "home";
					}
					// SI TECLEA MAS O MENOS DE 3 DIGITOS EN EL CAMPO CODIGO
					else {
						template.addAttribute("tresDigitos", false);
						return "resultados";
					}
				} else {
					template.addAttribute("nomFanRep", true);
					return "resultados";
				}
			}
			// SI HAY CODIGO REPETIDO
			else {
				template.addAttribute("codigoRepetido", true);
				return "resultados";
			}
		}
		// SI NO SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}

	}

	// NUEVA OBRA - GET
	@RequestMapping(value = "/obra/nuevaobra", method = RequestMethod.GET)
	public String NuevaObraGet(Model template) {

		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientes");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}
		template.addAttribute("lisClientes", listaClientes);

		// SI NO HAY CLIENTES
		if (listaClientes.size() == 0) {
			return "rstdoObra";
		}
		// SI HAY CLIENTES
		else {
			return "nuevaObra";
		}
	}

	// NUEVA OBRA - POST
	@RequestMapping(value = "/obra/nuevaobra", method = RequestMethod.POST)
	public String NuevaObraPost(Model template, @RequestParam(value = "codigo") String codigo,
			@RequestParam(value = "nombreFantasia") String nombreFantasia,
			@RequestParam(value = "contacto") String contacto,
			@RequestParam(value = "telefonoContacto") String telefonoContacto,
			@RequestParam(value = "direccion") String direccion, @RequestParam(value = "condPago") String condPago,
			@RequestParam(value = "nombreDeObra") String nombreDeObra) {

		// PASAR A MAYUSCULA EL NOMBRE DEL CLIENTE
		nombreDeObra = nombreDeObra.toUpperCase();

		// TRAE A LAS OBRAS
		ArrayList<Obra> listaObras = traerObras();
		template.addAttribute("listaObras", listaObras);

		// EVALUA SI EL CODIGO YA EXISTE
		boolean codigoRepetido = false;
		for (Obra o : listaObras) {
			if (o.getCodigo().equals(codigo)) {
				codigoRepetido = true;
			}
		}

		// TRAE A LAS OBRAS (CEO)
		ArrayList<CliEnOb> listaObrasCeo = traerobrasceo();

		// EVALUA SI EL NOMBRE DE LA OBRA EXISTE
		boolean nomRep = false;
		for (CliEnOb ceo : listaObrasCeo) {
			if (ceo.getNombreDeObra().equals(nombreDeObra)) {
				nomRep = true;
			}
		}

		// SI LOS CAMPOS NO ESTAN VACIOS, SE GUARDA EL PRODUCTO
		int a, b, c, d, e, f;
		a = codigo.length();
		b = nombreFantasia.length();
		c = contacto.length();
		d = nombreDeObra.length();
		e = telefonoContacto.length();
		f = direccion.length();
		// SI SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		if (a != 0 && b != 0 && c != 0 && d != 0 && e != 0 && f != 0) {
			// SI NO HAY CODIGO REPETIDO
			if (codigoRepetido == false) {
				if (nomRep == false) {
					// SI TECLEA SOLO 2 DIGITOS EN EL CAMPO CODIGO
					if (codigo.length() == 2) {
						this.jdbcTemplate.update("INSERT INTO obra (Codigo, NombreDeObra) VALUES(?, ?);", codigo,
								nombreDeObra);
						this.jdbcTemplate.update(
								"INSERT INTO clientesenobras (NombreFantasia, NombreDeObra, Contacto, Telefono, Direccion, CondPago) VALUES (?,?,?,?,?,?);",
								nombreFantasia, nombreDeObra, contacto, telefonoContacto, direccion, condPago);
						// template.addAttribute("cargaProducto", true);
						return "home";
					}
					// SI TECLEA MAS O MENOS DE 2 DIGITOS EN EL CAMPO CODIGO
					else {
						template.addAttribute("dosDigitos", false);
						return "resultados";
					}
				} else {
					template.addAttribute("nomRep", true);
					return "resultados";
				}
			}
			// SI HAY CODIGO REPETIDO
			else {
				template.addAttribute("codigoRepetido", true);
				return "resultados";
			}
		}
		// SI NO SE COMPLETAN LOS CAMPOS OBLIGATORIOS
		else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}

	// GONZALO

	// PRODUCTOS
	// PALETA CON NUEVO ITEM Y NUEVO FORMATO
	@RequestMapping(value = "/productos", method = RequestMethod.GET)
	public String productos(Model template) {
		return "productos";
	}

	// NUEVO ITEM
	// GET
	@RequestMapping(value = "/productos/nuevoItem/{idPedido}", method = RequestMethod.GET)
	public String nuevoItem(@PathVariable(value = "idPedido") String idPedido, Model template) {
		SqlRowSet itemsBD;
		itemsBD = jdbcTemplate.queryForRowSet(
				"SELECT * FROM items WHERE IDpedido = ? AND Item = (SELECT MAX(Item) FROM items WHERE IDpedido = ?);",
				idPedido, idPedido);

		if (itemsBD.next() == false) {
			template.addAttribute("estructura", "");
		} else {
			template.addAttribute("estructura", itemsBD.getString("Estructura"));
		}
		template.addAttribute("idPedido", idPedido);
		// TRAER TIPO DE ACERO DEL ULTIMO ITEM CARGADO
		SqlRowSet items;
		items = jdbcTemplate.queryForRowSet(
				"SELECT Item, Acero FROM items WHERE idPedido = ? ORDER BY item DESC limit 1;", idPedido);
		if (items.next() == false) {
			template.addAttribute("acero", "");
		} else {
			template.addAttribute("acero", items.getString("Acero"));
		}

		// TRAER TIPO DE MATERIAL DEL ULTIMO ITEM CARGADO
		SqlRowSet itemmm;
		itemmm = jdbcTemplate.queryForRowSet(
				"SELECT Item, Material FROM items WHERE idPedido = ? ORDER BY item DESC limit 1;", idPedido);
		if (itemmm.next() == false) {
			template.addAttribute("material", "");
		} else {
			template.addAttribute("material", itemmm.getString("Material"));
		}

		// TRAER ULTIMA OBSERVACION CARGADA y TRAER ULTIMA POSICION CARGADA +1
		SqlRowSet item;
		item = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE idPedido = ? ORDER BY item Desc limit 1;",
				idPedido);
		if (item.next() == false) {
			template.addAttribute("posicion", "");
			template.addAttribute("observaciones", "");
		} else {
			String numeroLetra = "";
			String numeroSolo = "";
			String posicion = "";
			String posi = item.getString("Posicion");
			boolean tieneletra = false;
			for (int i = 0; i < posi.length(); i++) {
				if (posi.charAt(i) == 'a' || posi.charAt(i) == 'b' || posi.charAt(i) == 'c' || posi.charAt(i) == 'd'
						|| posi.charAt(i) == 'e' || posi.charAt(i) == 'f' || posi.charAt(i) == 'g'
						|| posi.charAt(i) == 'h' || posi.charAt(i) == 'i' || posi.charAt(i) == 'j'
						|| posi.charAt(i) == 'k' || posi.charAt(i) == 'l' || posi.charAt(i) == 'm'
						|| posi.charAt(i) == 'n' || posi.charAt(i) == 'o' || posi.charAt(i) == 'p'
						|| posi.charAt(i) == 'q' || posi.charAt(i) == 'r' || posi.charAt(i) == 's'
						|| posi.charAt(i) == 't' || posi.charAt(i) == 'u' || posi.charAt(i) == 'v'
						|| posi.charAt(i) == 'w' || posi.charAt(i) == 'x' || posi.charAt(i) == 'y'
						|| posi.charAt(i) == 'z' || posi.charAt(i) == 'A' || posi.charAt(i) == 'B'
						|| posi.charAt(i) == 'C' || posi.charAt(i) == 'D' || posi.charAt(i) == 'E'
						|| posi.charAt(i) == 'F' || posi.charAt(i) == 'G' || posi.charAt(i) == 'H'
						|| posi.charAt(i) == 'I' || posi.charAt(i) == 'J' || posi.charAt(i) == 'K'
						|| posi.charAt(i) == 'L' || posi.charAt(i) == 'M' || posi.charAt(i) == 'N'
						|| posi.charAt(i) == 'O' || posi.charAt(i) == 'P' || posi.charAt(i) == 'Q'
						|| posi.charAt(i) == 'R' || posi.charAt(i) == 'S' || posi.charAt(i) == 'T'
						|| posi.charAt(i) == 'U' || posi.charAt(i) == 'V' || posi.charAt(i) == 'W'
						|| posi.charAt(i) == 'X' || posi.charAt(i) == 'Y' || posi.charAt(i) == 'Z'
						|| posi.charAt(i) == '-' || posi.charAt(i) == '/' || posi.charAt(i) == 'ñ'
						|| posi.charAt(i) == 'Ñ') {
					tieneletra = true;
				}
			}
			if (tieneletra) {
				numeroLetra = item.getString("Posicion");
				String numeros = "";
				String letras = "";
				for (int x = 0; x < numeroLetra.length(); x++) {
					if (numeroLetra.charAt(x) == '0' || numeroLetra.charAt(x) == '1' || numeroLetra.charAt(x) == '2'
							|| numeroLetra.charAt(x) == '3' || numeroLetra.charAt(x) == '4'
							|| numeroLetra.charAt(x) == '5' || numeroLetra.charAt(x) == '6'
							|| numeroLetra.charAt(x) == '7' || numeroLetra.charAt(x) == '8'
							|| numeroLetra.charAt(x) == '9') {
						numeros += numeroLetra.charAt(x) + "";
					} else {
						letras += numeroLetra.charAt(x) + "";
					}
				}
				int pos = Integer.parseInt(numeros);
				pos++;
				String poss = String.valueOf(pos);
				posicion = poss + letras;
			} else {
				numeroSolo = item.getString("posicion");
				int pos = Integer.parseInt(numeroSolo);
				pos++;
				posicion = pos + "";
			}
			template.addAttribute("posicion", posicion);
			template.addAttribute("observaciones", item.getString("Observaciones"));
		}

		// FORMATOS PARA MOSTRARLOS
		SqlRowSet formatosBD;
		formatosBD = jdbcTemplate.queryForRowSet("SELECT * FROM formatos;");
		ArrayList<Formato> formatos = new ArrayList<Formato>();
		while (formatosBD.next()) {
			long id = formatosBD.getInt("id");
			long posicion = formatosBD.getInt("posicion");
			String formato = formatosBD.getString("formato");
			long lados = formatosBD.getInt("lados");
			long cant_doblados = formatosBD.getInt("cant_doblados");
			Formato a = new Formato(id, posicion, formato,lados,cant_doblados);
			formatos.add(a);
		}
		template.addAttribute("formatos", formatos);
		return "nuevoItem";
	}

	// POST
	@RequestMapping(value = "/productos/nuevoItem/{idPedido}", method = RequestMethod.POST)
	public String nuevoItem(@PathVariable(value = "idPedido") String idPedido,
			@RequestParam(value = "posicion") String posicion, @RequestParam(value = "acero") String acero,
			@RequestParam(value = "diametro") String diametro, @RequestParam(value = "cantidad") String cantidad,
			@RequestParam(value = "formato") String formato, @RequestParam(value = "dibujo") String dibujo,
			@RequestParam(value = "a") String a, @RequestParam(value = "b") String b,
			@RequestParam(value = "c") String c, @RequestParam(value = "d") String d,
			@RequestParam(value = "e") String e, @RequestParam(value = "f") String f,
			@RequestParam(value = "g") String g, @RequestParam(value = "h") String h,
			@RequestParam(value = "h1") String h1, @RequestParam(value = "h2") String h2,
			@RequestParam(value = "observaciones") String observaciones,
			@RequestParam(value = "estructura") String estructura, Model template) {

		// QUE NO SE REPITA EL NUMERO DE POSICION CON LA MISMA ESTRUCTURA
		ArrayList<Item> lista = traerItemsXPedd(Integer.parseInt(idPedido));
		ArrayList<String> listaDePosiciones = new ArrayList<String>();
		for (int x = 0; x < lista.size(); x++) {
			if (estructura.equals(lista.get(x).getEstructura())) {
				listaDePosiciones.add(lista.get(x).getPosicion());
			}
		}
		// SABER SI SON TODOS NUMEROS LA POSICION
		// boolean todosNumeros = true;
		// for (int a1 = 0; a1 < posicion.length(); a1++) {
		// todosNumeros = todosNumeros && (posicion.charAt(a1) == '1' ||
		// posicion.charAt(a1) == '2'
		// || posicion.charAt(a1) == '3' || posicion.charAt(a1) == '4' ||
		// posicion.charAt(a1) == '5'
		// || posicion.charAt(a1) == '6' || posicion.charAt(a1) == '7' ||
		// posicion.charAt(a1) == '8'
		// || posicion.charAt(a1) == '9' || posicion.charAt(a1) == '0');
		// }
		// if (todosNumeros) {
		if (listaDePosiciones.contains(posicion)) {
			template.addAttribute("posicionEstructura", false);
			return "resultados";
		}
		// }
		// ArrayList<Item> lista = traerItemsXPedd(Integer.parseInt(idPedido));
		// ArrayList<Integer> listaDePosiciones = new ArrayList<Integer>();
		// for (int x = 0; x < lista.size(); x++) {
		// if (estructura.equals(lista.get(x).getEstructura())) {
		// listaDePosiciones.add(Integer.parseInt(lista.get(x).getPosicion()));
		// }
		// }
		// // SABER SI SON TODOS NUMEROS LA POSICION
		// boolean todosNumeros = true;
		// for (int a1 = 0; a1 < posicion.length(); a1++) {
		// todosNumeros = todosNumeros && (posicion.charAt(a1) == '1' ||
		// posicion.charAt(a1) == '2'
		// || posicion.charAt(a1) == '3' || posicion.charAt(a1) == '4' ||
		// posicion.charAt(a1) == '5'
		// || posicion.charAt(a1) == '6' || posicion.charAt(a1) == '7' ||
		// posicion.charAt(a1) == '8'
		// || posicion.charAt(a1) == '9' || posicion.charAt(a1) == '0');
		// }
		// if (todosNumeros) {
		// if (listaDePosiciones.contains(Integer.parseInt(posicion))) {
		// template.addAttribute("posicionEstructura", false);
		// return "resultados";
		// }
		// }
		// TRAE A LOS ITEMS DE UN PEDIDO
		ArrayList<Item> listaItemsXPed = traerItemsXPed(Integer.parseInt(idPedido));

		int ultIt;
		if (listaItemsXPed.size() == 0) {
			ultIt = 0;
		} else {
			int cantPed = listaItemsXPed.size();
			ultIt = (int) listaItemsXPed.get(cantPed - 1).getItem();
		}

		String item = Integer.toString(ultIt + 1);

		if (item.length() == 1) {
			item = "00" + item;
		}
		if (item.length() == 2) {
			item = "0" + item;
		}

		// OBTENGO LARGO PARCIAL
		// LParcial = a + b+ c + d + e + f + g

		ArrayList<String> listaMedidas = new ArrayList<String>();
		ArrayList<Double> listaMedidasD = new ArrayList<Double>();
		listaMedidas.add(a);
		listaMedidas.add(b);
		listaMedidas.add(c);
		listaMedidas.add(d);
		listaMedidas.add(e);
		listaMedidas.add(f);
		listaMedidas.add(g);

		// PASA DE STRING A DOUBLE PARA HACER LA SUMATORIA
		for (String i : listaMedidas) {
			int cantDig = i.length();
			if (cantDig != 0) {
				double med = Double.parseDouble(i);
				listaMedidasD.add(med);
			} else {
				double medCero = 0;
				listaMedidasD.add(medCero);
			}
		}

		// HACE LA SUMATORIA
		double lParciald = 0;
		for (Double d1 : listaMedidasD) {
			lParciald += d1;
		}

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lParcialEntero = redondearDouble(lParciald);
		String lParcial = Integer.toString(lParcialEntero);

		// OBTENGO LARGO TOTAL
		// LTotal = cantidad * LParcial
		double cantd = Double.parseDouble(cantidad);
		double lTotald = cantd * lParciald;

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lTotalEntero = redondearDouble(lTotald);
		String lTotal = Integer.toString(lTotalEntero);

		// OBTENGO LARGO CORTAR
		// LCortar = LParcial - (Desc x Diam * CANTIDAD DE LADOS)
		// Desc x Diam lo saco de la tabla segun su diametro
		//CANTIDAD DE LADOS, SE DEBE SACAR A TRAVÉS DEL FORMATO...
		// Cantidad de medidas las saco poniendo un contador en los input.Lenght
		// != 0

		// TRAE A LA TABLA PARA LOS CALCULOS
		ArrayList<TablaCalculos> tablaCalculos = traerDatos();

		double descxd = 0;
		if (diametro.equals("2,5")) {
			diametro = "2.5";
		}
		if (diametro.equals("4,2")) {
			diametro = "4.2";
		}
		if (diametro.equals("2,71")) {
			diametro = "2.71";
		}
		if (diametro.equals("7,4")) {
			diametro = "7.4";
		}
		if (diametro.equals("9,02")) {
			diametro = "9.02";
		}
		if (diametro.equals("10,71")) {
			diametro = "10.71";
		}
		double diametrod = Double.parseDouble(diametro.replace(",", "."));

		double pxm = 0;
		// RECORRO LA TABLA PARA SACAR Desc x Diam
		for (TablaCalculos tc : tablaCalculos) {
			double diam = tc.getDiametro();
			if (diam == diametrod) {
				descxd = tc.getDescxd();
				pxm = tc.getPxm();
			}
		}

		ArrayList<String> listaControl = new ArrayList<String>();
		listaControl.add(a);
		listaControl.add(b);
		listaControl.add(c);
		listaControl.add(d);
		listaControl.add(e);
		listaControl.add(f);
		listaControl.add(g);

		// RECORRE LA LISTA DE LOS INPUT (DE A a G) Y SI NO ESTAN VACIOS,
		// AUMENTA EN 1 EL CONTADOR DE MEDIDAS
		@SuppressWarnings("unused")
		int contMed = 0;
		for (int i = 0; i < listaControl.size(); i++) {
			int cantDigitos = listaControl.get(i).length();
			if (cantDigitos != 0) {
				contMed++;
			}
		}

		
		// TODO
		//PUNTO D DE LA MINUTA DE L13 de enero de 2018

		//Aca buca la cantidad de lados de acuerdo al formato elegido en la pantalla de nuevo item...
		//Para esto se creó el método getCantidadDeLados() 
		int cant_doblados = getCant_Doblados(formato);
		double LCortard = lParciald - (descxd * (cant_doblados));
		
		System.out.println(LCortard + " = " + lParciald + " - " + "(" + descxd + " * (" + cant_doblados + "))");
		
		//Prints en pantallas para validar que los datos se obtenían correctamente
		System.out.println("cantidad de doblados " + cant_doblados);
		System.out.println("Largo a cortar " + LCortard );

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lCortarEntero = redondearDouble(LCortard);
		String lCortar = Integer.toString(lCortarEntero);
		
		// OBTENGO PESO
		//PUNTO C DE LA MINUTA DEL 13 de enero de 2018

		// peso = LParcial * P x M
		double pesod = lTotald * pxm / 100;
		//System.out.println("peso = " + "ltotald (" + lTotald + ") * pxm(" + pxm + ") / 100");
		//TODO
		// Ya no se debe redondear el peso.
		pesod = Math.round(pesod* 10d) / 10d;
		String peso = Double.toString(pesod);
		
		// GENERAR CODIGO AL ITEM
		SqlRowSet pedido;
		pedido = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID = ?;", idPedido);
		pedido.next();
		String codigoPedido = pedido.getString("Codigo");
		String codigoItem = codigoPedido + item;

		if (diametro.equals("1") || diametro.equals("2") || diametro.equals("2,5") || diametro.equals("4,2")
				|| diametro.equals("6") || diametro.equals("8") || diametro.equals("10") || diametro.equals("12")
				|| diametro.equals("16") || diametro.equals("20") || diametro.equals("25") || diametro.equals("32")
				|| diametro.equals("2,71") || diametro.equals("7,4") || diametro.equals("9,02")
				|| diametro.equals("10,71") || diametro.equals("2.71") || diametro.equals("7.4") 
				|| diametro.equals("9.02") || diametro.equals("10.71")) {
			if (posicion.length() != 0 && diametro.length() != 0 && cantidad.length() != 0 && formato.length() != 0
					&& dibujo.length() != 0 && a.length() != 0 && estructura.length() != 0) {
				this.jdbcTemplate.update(
						"INSERT INTO items(Item, IDpedido, Codigo, Posicion, Acero, Diametro, Cantidad, Formato, Dibujo, A, B, C, D, E, F, G, H, H1, H2, LParcial, LTotal, LCortar, Peso, Observaciones, Estructura) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
						item, idPedido, codigoItem, posicion, acero, diametro, cantidad, formato, dibujo, a, b, c, d, e,
						f, g, h, h1, h2, lParcial, lTotal, lCortar, peso, observaciones, estructura);
				this.jdbcTemplate.update("UPDATE pedidos SET Estado=? WHERE ID=?;", "ETIQUETADO", idPedido);
				// TRAE LOS ITEMS DEL PEDIDO, HACE LA SUMA DE LOS KG POR
				// DIAMETRO Y ACTUALIZA LOS VALORES DENTRO DEL PEDIDO
				ArrayList<Item> itemsPedido = traerItems(Integer.parseInt(idPedido));

				double otros = 0;
				for (Item i : itemsPedido) {
					String aceroItem = i.getAcero();
					double pesoAngulo = Double.parseDouble(i.getPeso());
					if (aceroItem.equals("ANGULOS") || aceroItem.equals("CLAVOS") || aceroItem.equals("ALAMBRES")) {
						otros += pesoAngulo;
					}
				}

				double contCuatro = 0;
				double contSeis = 0;
				double contOcho = 0;
				double contDiez = 0;
				double contDoce = 0;
				double contDeciseis = 0;
				double contVeinte = 0;
				double contVeinticinco = 0;	
				double contTreintaydos = 0;
				for (Item i : itemsPedido) {
					String diam = i.getDiametro();
					String peso1 = i.getPeso();

					if (diam.equals("4.2") || diam.equals("4,2")) {
						contCuatro += Double.parseDouble(peso1);
					} else if (diam.equals("6")) {
						contSeis += Double.parseDouble(peso1);
					} else if (diam.equals("8")) {
						contOcho += Double.parseDouble(peso1);
					} else if (diam.equals("10")) {
						contDiez += Double.parseDouble(peso1);
					} else if (diam.equals("12")) {
						contDoce += Double.parseDouble(peso1);
					} else if (diam.equals("16")) {
						contDeciseis += Double.parseDouble(peso1);
					} else if (diam.equals("20")) {
						contVeinte += Double.parseDouble(peso1);
					} else if (diam.equals("25")) {
						contVeinticinco += Double.parseDouble(peso1);
					} else if (diam.equals("32")) {
						contTreintaydos += Double.parseDouble(peso1);
					}
				}

				contCuatro = setearUnDecimal(contCuatro);
				contSeis = setearUnDecimal(contSeis);
				contOcho = setearUnDecimal(contOcho);
				contDiez = setearUnDecimal(contDiez);
				contDoce = setearUnDecimal(contDoce);
				contDeciseis = setearUnDecimal(contDeciseis);
				contVeinte = setearUnDecimal(contVeinte);
				contVeinticinco = setearUnDecimal(contVeinticinco);
				contTreintaydos = setearUnDecimal(contTreintaydos);
				otros = setearUnDecimal(otros);
				System.out.println(contSeis);

				this.jdbcTemplate.update("UPDATE pedidos SET Cuatrocomados=? WHERE ID=?;", contCuatro, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Seis=? WHERE ID=?;", contSeis, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Ocho=? WHERE ID=?;", contOcho, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Diez=? WHERE ID=?;", contDiez, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Doce=? WHERE ID=?;", contDoce, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Dieciseis=? WHERE ID=?;", contDeciseis, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Veinte=? WHERE ID=?;", contVeinte, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Veinticinco=? WHERE ID=?;", contVeinticinco, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Treintaydos=? WHERE ID=?;", contTreintaydos, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Otros=? WHERE ID=?;", otros, idPedido);

				double totalkilos = contCuatro + contSeis + contOcho + contDiez + contDoce + contDeciseis + contVeinte
						+ contVeinticinco + contTreintaydos + otros;
				this.jdbcTemplate.update("UPDATE pedidos SET TotalKg=? WHERE ID=?;", totalkilos, idPedido);

				return "redirect:" + "/productos/nuevoItem/" + idPedido;
			} else {
				template.addAttribute("idPedido", idPedido);
				template.addAttribute("camposObligatorios", false);
				return "resultados";
			}
		} else {
			template.addAttribute("diametroCorrespondiente", false);
			return "resultados";
		}
	}
	// FIN DE NUEVO ITEM

	// MOSTRAR ITEMS
	// GET
	@RequestMapping(value = "productos/items/{idPedido}", method = RequestMethod.GET)
	public String items(@PathVariable(value = "idPedido") String idPedido, Model template) {

		// TRAE A LOS ITEMS
		ArrayList<Item> listaItems = traerItems(Integer.parseInt(idPedido));
		template.addAttribute("items", listaItems);

		return "mostrarItems";
	}
	// FIN DE MOSTRAR ITEMS

	// NUEVO FORMATO
	// GET
	@RequestMapping(value = "/productos/nuevoFormato", method = RequestMethod.GET)
	public String nuevoFormato(Model template) {
		return "nuevoFormato";
	}

	// POS
	@RequestMapping(value = "/productos/nuevoFormato", method = RequestMethod.POST)
	public String nuevoFormato(@RequestParam(value = "posicion") String posicion,
			@RequestParam(value = "formato") String formato, @RequestParam(value = "lados") int lados,@RequestParam (value = "cant_doblados") long cant_doblados, Model template) {
		
		
		if (lados > 8 || lados < 1) {
			template.addAttribute("lados", false);
			return "resultados";
		}
		if (posicion.length() != 0 && formato.length() != 0 && lados != 0  && cant_doblados !=0) {
			this.jdbcTemplate.update("INSERT INTO formatos(Posicion, Formato, Lados, Cant_doblados) VALUES (?,?,?,?);", posicion,
					formato, lados,cant_doblados);
			return "redirect:" + "/productos/nuevoFormato";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}

	}

	// FIN DE NUEVO FORMATO

	// MOSTRAR FORMATOS
	// GET
	@RequestMapping(value = "/productos/formatos", method = RequestMethod.GET)
	public String formatos(Model template) {
		SqlRowSet formatosBD;
		formatosBD = jdbcTemplate.queryForRowSet("SELECT * FROM formatos;");
		ArrayList<Formato> formatos = new ArrayList<Formato>();
		while (formatosBD.next()) {
			long id = formatosBD.getInt("id");
			long posicion = formatosBD.getInt("posicion");
			String formato = formatosBD.getString("formato");
			long lados = formatosBD.getInt("lados");
			long cant_doblados = formatosBD.getInt("cant_doblados");			

			Formato a = new Formato(id, posicion, formato,lados,cant_doblados);
			formatos.add(a);
		}
		template.addAttribute("formatos", formatos);
		return "mostrarFormatos";
	}
	// FIN DE MOSTRAR FORMATOS

	// PROGRAMACION
	// GET
	@RequestMapping(value = "/programacion", method = RequestMethod.GET)
	public String programacion(Model template) {
		return "programacion";
	}
	// FIN DE PROGRAMACION

	// NUEVO PEDIDO PROGRAMACION
	// GET
	@RequestMapping(value = "/programacion/pedido", method = RequestMethod.GET)
	public String pedido(Model template) {
		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES (Sin repetidos o con
		// obras)
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT DISTINCT NombreFantasia FROM clientesenobras");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}

		if (listaClientes.size() == 0) {
			template.addAttribute("hayClientes", false);
			template.addAttribute("hayObras", false);
			return "resultados";
		}

		ArrayList<CliEnOb> obras = traerobrasceo();
		if (obras.size() == 0) {
			template.addAttribute("hayObras", false);
			return "resultados";
		}
		template.addAttribute("lisClientes", listaClientes);
		return "nuevoPedido";
	}

	// POST
	@RequestMapping(value = "/programacion/pedido", method = RequestMethod.POST)
	public String pedido(@RequestParam(value = "entrega") String entrega,
			@RequestParam(value = "cliente") String cliente, @RequestParam(value = "obra") String obra,
			@RequestParam(value = "descripcion") String descripcion, @RequestParam(value = "tipo") String tipo,
			@RequestParam(value = "cuatrocomados") String cuatrocomados, @RequestParam(value = "seis") String seis,
			@RequestParam(value = "ocho") String ocho, @RequestParam(value = "diez") String diez,
			@RequestParam(value = "doce") String doce, @RequestParam(value = "dieciseis") String dieciseis,
			@RequestParam(value = "veinte") String veinte, @RequestParam(value = "veinticinco") String veinticinco,
			@RequestParam(value = "treintaydos") String treintaydos, @RequestParam(value = "otros") String otros,
			@RequestParam(value = "oc") String oc, @RequestParam(value = "elementos") String elementos, Model template) {

		System.out.println("ENTRO ACAAAAAAAAAAAAAAAAAAA");
		
		for (int i = 0; i < entrega.length(); i++) {
			if (entrega.charAt(i) == 'a' || entrega.charAt(i) == 'b' || entrega.charAt(i) == 'c'
					|| entrega.charAt(i) == 'd' || entrega.charAt(i) == 'e' || entrega.charAt(i) == 'f'
					|| entrega.charAt(i) == 'g' || entrega.charAt(i) == 'h' || entrega.charAt(i) == 'i'
					|| entrega.charAt(i) == 'j' || entrega.charAt(i) == 'k' || entrega.charAt(i) == 'l'
					|| entrega.charAt(i) == 'm' || entrega.charAt(i) == 'n' || entrega.charAt(i) == 'o'
					|| entrega.charAt(i) == 'p' || entrega.charAt(i) == 'q' || entrega.charAt(i) == 'r'
					|| entrega.charAt(i) == 's' || entrega.charAt(i) == 't' || entrega.charAt(i) == 'u'
					|| entrega.charAt(i) == 'v' || entrega.charAt(i) == 'w' || entrega.charAt(i) == 'x'
					|| entrega.charAt(i) == 'y' || entrega.charAt(i) == 'z' || entrega.charAt(i) == 'A'
					|| entrega.charAt(i) == 'B' || entrega.charAt(i) == 'C' || entrega.charAt(i) == 'D'
					|| entrega.charAt(i) == 'E' || entrega.charAt(i) == 'F' || entrega.charAt(i) == 'G'
					|| entrega.charAt(i) == 'H' || entrega.charAt(i) == 'I' || entrega.charAt(i) == 'J'
					|| entrega.charAt(i) == 'K' || entrega.charAt(i) == 'L' || entrega.charAt(i) == 'M'
					|| entrega.charAt(i) == 'N' || entrega.charAt(i) == 'O' || entrega.charAt(i) == 'P'
					|| entrega.charAt(i) == 'Q' || entrega.charAt(i) == 'R' || entrega.charAt(i) == 'S'
					|| entrega.charAt(i) == 'T' || entrega.charAt(i) == 'U' || entrega.charAt(i) == 'V'
					|| entrega.charAt(i) == 'W' || entrega.charAt(i) == 'X' || entrega.charAt(i) == 'Y'
					|| entrega.charAt(i) == 'Z' || entrega.charAt(i) == '-' || entrega.charAt(i) == '/'
					|| entrega.charAt(i) == 'ñ' || entrega.charAt(i) == 'Ñ') {
				template.addAttribute("fechaValida", false);
				return "resultados";
			}
		}

		// INVERTIR FECHA
		if (entrega.length() == 8) {
			// DDMMAAAA
			entrega = invertirCadena(entrega);
		}
		if (entrega.length() == 6) {
			// DDMMAA
			entrega = invertirCadenaMas(entrega);
		}
		if (entrega.length() != 10) {
			entrega = "";
		}
		// TRAE PEDIDOS POR OBRA Y POR CLIENTE
		ArrayList<Pedido> listaPedidosPOYPC = traerPedidosPOYPC(obra, cliente);

		int ultPed;
		if (listaPedidosPOYPC.size() == 0) {
			ultPed = 0;
		} else {
			int cantPed = listaPedidosPOYPC.size();
			// String prueba = listaPedidosPOYPC.get(cantPed - 1).getPedido();
			ultPed = Integer.parseInt(listaPedidosPOYPC.get(cantPed - 1).getPedido());
		}

		// SETEA NUMERO DE PEDIDO
		String pedido = Integer.toString(ultPed + 1);

		if (pedido.length() == 1) {
			pedido = "00" + pedido;
		}
		if (pedido.length() == 2) {
			pedido = "0" + pedido;
		}

		// TRAE A LOS CLIENTES
		ArrayList<Cliente> listaClientes = traerClientes();
		template.addAttribute("listaClientes", listaClientes);

		// TRAE A LAS OBRAS
		ArrayList<Obra> listaObras = traerObras();
		template.addAttribute("listaObras", listaObras);

		int a, b, c, e, f;
		a = entrega.length();
		b = cliente.length();
		c = obra.length();
		e = descripcion.length();
		f = tipo.length();

		// TOTAL KG
		ArrayList<String> listaDiam = new ArrayList<String>();
		listaDiam.add(cuatrocomados);
		listaDiam.add(seis);
		listaDiam.add(ocho);
		listaDiam.add(diez);
		listaDiam.add(doce);
		listaDiam.add(dieciseis);
		listaDiam.add(veinte);
		listaDiam.add(veinticinco);
		listaDiam.add(treintaydos);
		listaDiam.add(otros);

		// CALCULAR TOTAL KG
		double totalkgI = 0;
		for (int i = 0; i < listaDiam.size(); i++) {
			if (listaDiam.get(i).length() != 0) {
				double num = Double.parseDouble(listaDiam.get(i));
				totalkgI += num;
			}
		}
		String totalkg = Double.toString(totalkgI);

		// ARMAR CODIGO
		String codCli = "algo";
		String codObr = "algo";
		for (int i = 0; i < listaClientes.size(); i++) {
			if (listaClientes.get(i).getNombreFantasia().equals(cliente)) {
				codCli = listaClientes.get(i).getCodigo();
				//System.out.println(codCli + " lalala");
			}
		}
		
		//System.out.println(obra);
		//System.out.println(listaObras.get(listaObras.size()-1).getNombreDeObra());
		
		for (int i = 0; i < listaObras.size(); i++) {
			//System.out.println(listaObras.get(listaObras.size()-1).getNombreDeObra());
			if (listaObras.get(i).getNombreDeObra().equals(obra)) {
				codObr = listaObras.get(i).getCodigo();
				//System.out.println(codObr);
			}
		}
		String codigo = codCli + codObr + pedido;
		//System.out.println(codigo);

		String estado = "PENDIENTE";
		if (a == 10 && b != 0 && c != 0 && e != 0 && f != 0) {
			this.jdbcTemplate.update(
					"INSERT INTO pedidos(Entrega, Cliente, Obra, Codigo, Descripcion, Tipo, TotalKg, Cuatrocomados , Seis, Ocho, Diez, Doce, Dieciseis, Veinte, Veinticinco, Treintaydos, Otros, Estado, Pedido, OC, Elementos) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
					entrega, cliente, obra, codigo, descripcion, tipo, totalkg, cuatrocomados, seis, ocho, diez, doce,
					dieciseis, veinte, veinticinco, treintaydos, otros, estado, pedido, oc, elementos);
			return "oficinaTecnica";
		} else {
			if (b == 0 || c == 0 || e == 0 || f == 0) {
				template.addAttribute("camposObligatorios", false);
			}
			if (entrega.length() == 0) {
				template.addAttribute("fechaValida", false);
			}
			return "resultados";
		}

	}
	// FIN DE NUEVO PEDIDO PROGRAMACION

	// DUPLICAR PEDIDO
	@RequestMapping("/duplicarPedido/{ID}")
	public String duplicarPedido(@PathVariable(value = "ID") int ID, Model template) {
		SqlRowSet pedido;
		SqlRowSet cliente;
		SqlRowSet obra;
		pedido = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID = ?;", ID);
		pedido.next();
		cliente = jdbcTemplate.queryForRowSet("SELECT * FROM clientes WHERE NombreFantasia = ?", pedido.getString("Cliente"));
		cliente.next();
		obra = jdbcTemplate.queryForRowSet("SELECT * FROM obra WHERE NombreDeObra = ?", pedido.getString("Obra"));
		obra.next();
		// TRAE PEDIDOS POR OBRA Y POR CLIENTE
		ArrayList<Pedido> listaPedidosPOYPC = traerPedidosPOYPC(pedido.getString("Obra"), pedido.getString("Cliente"));
		
		int ultPed;
		if (listaPedidosPOYPC.size() == 0) {
			ultPed = 0;
		} else {
			int cantPed = listaPedidosPOYPC.size();
			ultPed = Integer.parseInt(listaPedidosPOYPC.get(cantPed - 1).getPedido());
		}
		// SETEA NUMERO DE PEDIDO
		String ped = Integer.toString(ultPed + 1);

		if (ped.length() == 1) {
			ped = "00" + ped;
		}
		if (ped.length() == 2) {
			ped = "0" + ped;
		}

		// SACARLE LOS ASTERISCOS A LA FECHA DE ENTREGA PORQUE ESTA NUEVA NO HA
		// SIDO CAMBIADA
		String entrega = pedido.getString("Entrega").replace("*", "");

		// ARMA EL NUEVO CODIGO
		String codigo = cliente.getString("Codigo") + "" + obra.getString("Codigo") + "" + ped;

		this.jdbcTemplate.update(
				"INSERT INTO pedidos(Entrega, Cliente, Obra, Codigo, Descripcion, Tipo, TotalKg, Cuatrocomados , Seis, Ocho,"
						+ "Diez, Doce, Dieciseis, Veinte, Veinticinco, Treintaydos, Otros, Estado, Pedido, OC, Elementos) VALUES "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
				entrega, pedido.getString("Cliente"), pedido.getString("Obra"), codigo, pedido.getString("Descripcion"),
				pedido.getString("Tipo"), pedido.getString("TotalKg"), pedido.getString("Cuatrocomados"),
				pedido.getString("seis"), pedido.getString("Ocho"), pedido.getString("Diez"), pedido.getString("Doce"),
				pedido.getString("Dieciseis"), pedido.getString("Veinte"), pedido.getString("Veinticinco"),
				pedido.getString("Treintaydos"), pedido.getString("Otros"), pedido.getString("Estado"), ped,
				pedido.getString("OC"), pedido.getString("Elementos"));
		SqlRowSet pedidoRecienDuplicado;
		pedidoRecienDuplicado = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos ORDER BY ID DESC LIMIT 1;");
		pedidoRecienDuplicado.next();
		SqlRowSet items;
		items = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE IDpedido = ?;", ID);
		while (items.next()) {
			this.jdbcTemplate.update(
					"INSERT INTO items( IDpedido, Codigo, Item, Posicion, Acero, Material, Diametro, Cantidad, Formato, Dibujo, A, B, C, D, E, F, G, H, H1, H2, LParcial, LTotal, LCortar, Peso, Observaciones, Estructura ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
					pedidoRecienDuplicado.getInt("ID"), items.getString("Codigo"), items.getInt("Item"),
					items.getString("Posicion"), items.getString("Acero"), items.getString("Material"),
					items.getString("Diametro"), items.getString("Cantidad"), items.getString("Formato"),
					items.getString("Dibujo"), items.getString("A"), items.getString("B"), items.getString("C"),
					items.getString("D"), items.getString("E"), items.getString("F"), items.getString("G"),
					items.getString("H"), items.getString("H1"), items.getString("H2"), items.getString("LParcial"),
					items.getString("LTotal"), items.getString("LCortar"), items.getString("Peso"),
					items.getString("Observaciones"), items.getString("Estructura"));
		}
		return "redirect:" + "/etiquetado/pedidos";
	}

	// FIN DE DUPLICAR PEDIDO

	// MOSTRAR PEDIDOS (PROGRAMACION)
	// GET
	@RequestMapping(value = "/programacion/pedidos", method = RequestMethod.GET)
	public String pedidos(Model template) {
		// TRAE A LOS PEDIDOS
		ArrayList<Pedido> listaPedidos = traerPedidos();
		if (listaPedidos.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		template.addAttribute("pedidos", listaPedidos);
		return "mostrarPedidos";
	}

	// POST
	@RequestMapping(value = "/programacion/pedidos", method = RequestMethod.POST)
	public String pedidosProgramacionPOST(@RequestParam(value = "desde") String desde,
			@RequestParam(value = "hasta") String hasta, Model template) {
		if (hasta.length() == 0 || desde.length() == 0) {
			ArrayList<Pedido> listaPedidos = traerPedidos();
			template.addAttribute("pedidos", listaPedidos);
			return "mostrarPedidos";
		}
		// TRAE A LOS PEDIDOS
		ArrayList<Pedido> listaPedidos = traerPedidosDesdeHasta(desde, hasta);
		if (listaPedidos.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		template.addAttribute("pedidos", listaPedidos);
		return "mostrarPedidos";
	}
	// FIN DE MOSTRAR PEDIDOS (PROGRAMACION)

	

	// MOSTRAR PEDIDOS DESPACHADO(ETIQUETADO)
	// GET
	@RequestMapping(value = "/etiquetado/pedidosDespachados", method = RequestMethod.GET)
	public String pedidosEtiquetadoDespachado(Model template) {
		// TRAE A LOS PEDIDOS
		ArrayList<Pedido> listaPedidos = traerPedidosDespachados();
		if (listaPedidos.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		template.addAttribute("pedidos", listaPedidos);
		return "mostrarPedidosDespachados";
	}
	
	
	// MOSTRAR PEDIDOS (ETIQUETADO)
	// GET
	@RequestMapping(value = "/etiquetado/pedidos", method = RequestMethod.GET)
	public String pedidosEtiquetado(Model template) {
		// TRAE A LOS PEDIDOS
		ArrayList<Pedido> listaPedidos = traerPedidos();
		if (listaPedidos.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		template.addAttribute("pedidos", listaPedidos);
		return "mostrarPedidosE";
	}

	// POST
	@RequestMapping(value = "/etiquetado/pedidos", method = RequestMethod.POST)
	public String pedidosEtiquetadoPOST(@RequestParam(value = "desde") String desde,
			@RequestParam(value = "hasta") String hasta, Model template) {
		if (hasta.length() == 0 || desde.length() == 0) {
			ArrayList<Pedido> listaPedidos = traerPedidos();
			template.addAttribute("pedidos", listaPedidos);
			return "mostrarPedidosE";
		}
		// TRAE A LOS PEDIDOS
		ArrayList<Pedido> listaPedidos = traerPedidosDesdeHasta(desde, hasta);
		if (listaPedidos.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		template.addAttribute("pedidos", listaPedidos);
		return "mostrarPedidosE";
	}
	// FIN DE MOSTRAR PEDIDOS (ETIQUETADO)

	// ELIMINAR ITEM
	@RequestMapping("/productos/items/eliminar/{idPedido}/{idItem}")
	public String eliminarItem(@PathVariable(value = "idPedido") int idPedido,
			@PathVariable(value = "idItem") int idItem, Model template) {

		// TRAE A TODOS LOS ITEMS DEL PEDIDO PASADO COMO PARAMETRO
		ArrayList<Item> lItem = traerItems(idPedido);

		double pesoI = 0;
		String diametroI = "";
		String acero = "";

		for (Item i : lItem) {
			int item = (int) i.getItem();
			if (item == idItem) {
				pesoI = Double.parseDouble(i.getPeso());
				diametroI = i.getDiametro();
				acero = i.getAcero();
			}
		}

		// TRAE EL PEDIDO QUE CONTIENE EL ITEM A ELIMINAR
		ArrayList<Pedido> ped = traerPedidosIP(idPedido);

		String cuatrocomados = ped.get(0).getCuatrocomados();
		String seis = ped.get(0).getSeis();
		String ocho = ped.get(0).getOcho();
		String diez = ped.get(0).getDiez();
		String doce = ped.get(0).getDoce();
		String dieciseis = ped.get(0).getDieciseis();
		String veinte = ped.get(0).getVeinte();
		String veinticinco = ped.get(0).getVeinticinco();
		String treintaydos = ped.get(0).getTreintaydos();
		String otros = ped.get(0).getOtros();

		double cuatrocomadosI = Double.parseDouble(cuatrocomados);
		double seisI = Double.parseDouble(seis);
		double ochoI = Double.parseDouble(ocho);
		double diezI = Double.parseDouble(diez);
		double doceI = Double.parseDouble(doce);
		double dieciseisI = Double.parseDouble(dieciseis);
		double veinteI = Double.parseDouble(veinte);
		double veinticincoI = Double.parseDouble(veinticinco);
		double treintaydosI = Double.parseDouble(treintaydos);
		double otrosI = Double.parseDouble(otros);
		cuatrocomadosI = setearUnDecimal(cuatrocomadosI);
		seisI = setearUnDecimal(seisI);
		ochoI = setearUnDecimal(ochoI);
		diezI = setearUnDecimal(diezI);
		doceI = setearUnDecimal(doceI);
		dieciseisI = setearUnDecimal(dieciseisI);
		veinteI = setearUnDecimal(veinteI);
		veinticincoI = setearUnDecimal(veinticincoI);
		treintaydosI = setearUnDecimal(treintaydosI);
		otrosI = setearUnDecimal(otrosI);
		System.out.println(seisI);

		if (acero.equals("ANGULOS") || acero.equals("CLAVOS") || acero.equals("ALAMBRES")) {
			otrosI -= pesoI;
			String otrosII = Double.toString(otrosI);
			otros = otrosII;
		}

		// RECALCULA LOS DATOS DEL PEDIDO DEPENDIENDO DEL DIAMETRO
		if (diametroI.equals("4,2")) {
			cuatrocomadosI -= pesoI;
			String cuatrocomadosII = Double.toString(cuatrocomadosI);
			cuatrocomados = cuatrocomadosII;
		}
		if (diametroI.equals("6")) {
			seisI -= pesoI;
			String seisII = Double.toString(seisI);
			seis = seisII;
		}
		if (diametroI.equals("8")) {
			ochoI -= pesoI;
			String ochoII = Double.toString(ochoI);
			ocho = ochoII;
		}
		if (diametroI.equals("10")) {
			diezI -= pesoI;
			String diezII = Double.toString(diezI);
			diez = diezII;
		}
		if (diametroI.equals("12")) {
			doceI -= pesoI;
			String doceII = Double.toString(doceI);
			doce = doceII;
		}
		if (diametroI.equals("16")) {
			dieciseisI -= pesoI;
			String dieciseisII = Double.toString(dieciseisI);
			dieciseis = dieciseisII;
		}
		if (diametroI.equals("20")) {
			veinteI -= pesoI;
			String veinteII = Double.toString(veinteI);
			veinte = veinteII;
		}
		if (diametroI.equals("25")) {
			veinticincoI -= pesoI;
			String veinticincoII = Double.toString(veinticincoI);
			veinticinco = veinticincoII;
		}
		if (diametroI.equals("32")) {
			treintaydosI -= pesoI;
			String treintaydosII = Double.toString(treintaydosI);
			treintaydos = treintaydosII;
		}
		// ACTUALIZO LOS DATOS DE LAS COLUMNAS

		this.jdbcTemplate.update("UPDATE pedidos SET Cuatrocomados=? WHERE ID=?;", cuatrocomados, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Seis=? WHERE ID=?;", seis, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Ocho=? WHERE ID=?;", ocho, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Diez=? WHERE ID=?;", diez, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Doce=? WHERE ID=?;", doce, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Dieciseis=? WHERE ID=?;", dieciseis, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Veinte=? WHERE ID=?;", veinte, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Veinticinco=? WHERE ID=?;", veinticinco, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Treintaydos=? WHERE ID=?;", treintaydos, idPedido);
		this.jdbcTemplate.update("UPDATE pedidos SET Otros=? WHERE ID=?;", otros, idPedido);

		double a = Double.parseDouble(cuatrocomados);
		double b = Double.parseDouble(seis);
		double c = Double.parseDouble(ocho);
		double d = Double.parseDouble(diez);
		double e = Double.parseDouble(doce);
		double f = Double.parseDouble(dieciseis);
		double g = Double.parseDouble(veinte);
		double h = Double.parseDouble(veinticinco);
		double ii = Double.parseDouble(treintaydos);
		double jj = Double.parseDouble(otros);

		// HAGO EL CALCULO DEL TOTAL DE KG
		double totalkilos = a + b + c + d + e + f + g + h + ii + jj;

		// ACTUALIZO EL TOTAL DE KG
		this.jdbcTemplate.update("UPDATE pedidos SET TotalKg=? WHERE ID=?;", totalkilos, idPedido);

		// ELIMINA AL ITEM
		jdbcTemplate.update("DELETE FROM items WHERE IDpedido = ? AND Item = ?;", idPedido, idItem);

		// // TRAIGO A TODOS LOS ITEMS DE UN PEDIDO PARA IR ENUMERARLOS DE NUEVO
		// SI
		// // SE ELIMINA UN ITEM
		// // VER QUE PASA CUANDO ES 1A 2A 3A
		//
		// SqlRowSet item;
		// item = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE
		// idPedido = ? ORDER BY item Desc limit 1;",
		// idPedido);
		// String numeroLetra = new String();
		// String posicion = "";
		// item.next();
		// String posi = item.getString("Posicion");
		// boolean tieneletra = false;
		// for (int i = 0; i < posi.length(); i++) {
		// if (posi.charAt(i) == 'a' || posi.charAt(i) == 'b' || posi.charAt(i)
		// == 'c' || posi.charAt(i) == 'd'
		// || posi.charAt(i) == 'e' || posi.charAt(i) == 'f' || posi.charAt(i)
		// == 'g' || posi.charAt(i) == 'h'
		// || posi.charAt(i) == 'i' || posi.charAt(i) == 'j' || posi.charAt(i)
		// == 'k' || posi.charAt(i) == 'l'
		// || posi.charAt(i) == 'm' || posi.charAt(i) == 'n' || posi.charAt(i)
		// == 'o' || posi.charAt(i) == 'p'
		// || posi.charAt(i) == 'q' || posi.charAt(i) == 'r' || posi.charAt(i)
		// == 's' || posi.charAt(i) == 't'
		// || posi.charAt(i) == 'u' || posi.charAt(i) == 'v' || posi.charAt(i)
		// == 'w' || posi.charAt(i) == 'x'
		// || posi.charAt(i) == 'y' || posi.charAt(i) == 'z' || posi.charAt(i)
		// == 'A' || posi.charAt(i) == 'B'
		// || posi.charAt(i) == 'C' || posi.charAt(i) == 'D' || posi.charAt(i)
		// == 'E' || posi.charAt(i) == 'F'
		// || posi.charAt(i) == 'G' || posi.charAt(i) == 'H' || posi.charAt(i)
		// == 'I' || posi.charAt(i) == 'J'
		// || posi.charAt(i) == 'K' || posi.charAt(i) == 'L' || posi.charAt(i)
		// == 'M' || posi.charAt(i) == 'N'
		// || posi.charAt(i) == 'O' || posi.charAt(i) == 'P' || posi.charAt(i)
		// == 'Q' || posi.charAt(i) == 'R'
		// || posi.charAt(i) == 'S' || posi.charAt(i) == 'T' || posi.charAt(i)
		// == 'U' || posi.charAt(i) == 'V'
		// || posi.charAt(i) == 'W' || posi.charAt(i) == 'X' || posi.charAt(i)
		// == 'Y' || posi.charAt(i) == 'Z'
		// || posi.charAt(i) == '-' || posi.charAt(i) == '/' || posi.charAt(i)
		// == 'ñ'
		// || posi.charAt(i) == 'Ñ') {
		// tieneletra = true;
		// }
		//
		// if (tieneletra) {
		// // ArrayList<Item> listaItems = traerItemsXPed(idPedido);
		// // for (Item i1 : listaItems) {
		// // long idItemL = i1.getItem();
		// // numeroLetra = i1.getPosicion();
		// // String numeros = "";
		// // String letras = "";
		// // for (int x = 0; x < numeroLetra.length(); x++) {
		// // if (numeroLetra.charAt(x) == '0' || numeroLetra.charAt(x) ==
		// // '1' || numeroLetra.charAt(x) == '2'
		// // || numeroLetra.charAt(x) == '3' || numeroLetra.charAt(x) ==
		// // '4'
		// // || numeroLetra.charAt(x) == '5' || numeroLetra.charAt(x) ==
		// // '6'
		// // || numeroLetra.charAt(x) == '7' || numeroLetra.charAt(x) ==
		// // '8'
		// // || numeroLetra.charAt(x) == '9') {
		// // numeros += numeroLetra.charAt(x) + "";
		// // } else {
		// // letras += numeroLetra.charAt(x) + "";
		// // }
		// // }
		// // int pos = Integer.parseInt(numeros);
		// // pos++;
		// // String poss = String.valueOf(pos);
		// // posicion = poss + letras;
		// // this.jdbcTemplate.update("UPDATE items SET Posicion=? WHERE
		// // Item=?;", posicion, idItemL);
		// // }
		// } else {
		// ArrayList<Item> listaItems = traerItemsXPed(idPedido);
		// int psc = 000;
		// for (Item i1 : listaItems) {
		// long idItemL = i1.getItem();
		// psc++;
		// this.jdbcTemplate.update("UPDATE items SET Posicion=? WHERE Item=?;",
		// psc, idItemL);
		// }
		// }
		// }

		return "redirect:" + "/productos/items/" + idPedido;

	}

	// FIN DE ELIMINAR ITEM
	// MODIFICAR ITEM
	// GET
	@RequestMapping(value = "productos/items/modificar/{idPedido}/{idItem}", method = RequestMethod.GET)
	public String item(@PathVariable(value = "idPedido") int idPedido, @PathVariable(value = "idItem") int idItem,
			Model template) {
		SqlRowSet itemBD;
		itemBD = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE IDpedido = ? AND Item = ?;", idPedido, idItem);
		itemBD.next();
		String posicion = itemBD.getString("posicion");
		String acero = itemBD.getString("acero");
		String diametro = itemBD.getString("diametro");
		String cantidad = itemBD.getString("cantidad");
		String formato = itemBD.getString("formato");
		String dibujo = itemBD.getString("dibujo");
		String a = itemBD.getString("a");
		String b = itemBD.getString("b");
		String c = itemBD.getString("c");
		String d = itemBD.getString("d");
		String e = itemBD.getString("e");
		String f = itemBD.getString("f");
		String g = itemBD.getString("g");
		String h = itemBD.getString("h");
		String h1 = itemBD.getString("h1");
		String h2 = itemBD.getString("h2");
		String observaciones = itemBD.getString("observaciones");
		String estructura = itemBD.getString("estructura");
		template.addAttribute("posicion", posicion);
		template.addAttribute("acero", acero);
		template.addAttribute("diametro", diametro);
		template.addAttribute("cantidad", cantidad);
		template.addAttribute("formato", formato);
		template.addAttribute("dibujo", dibujo);
		template.addAttribute("a", a);
		template.addAttribute("b", b);
		template.addAttribute("c", c);
		template.addAttribute("d", d);
		template.addAttribute("e", e);
		template.addAttribute("f", f);
		template.addAttribute("g", g);
		template.addAttribute("h", h);
		template.addAttribute("h1", h1);
		template.addAttribute("h2", h2);
		template.addAttribute("observaciones", observaciones);
		template.addAttribute("estructura", estructura);
		return "modificarItem";
	}

	// POST
	@RequestMapping(value = "productos/items/modificar/{idPedido}/{idItem}", method = RequestMethod.POST)
	public String modifcarItem(@PathVariable(value = "idPedido") int idPedido,
			@PathVariable(value = "idItem") int idItem, @RequestParam(value = "posicion") String posicion,
			@RequestParam(value = "acero") String acero, @RequestParam(value = "diametro") String diametro,
			@RequestParam(value = "cantidad") String cantidad, @RequestParam(value = "formato") String formato,
			@RequestParam(value = "dibujo") String dibujo, @RequestParam(value = "a") String a,
			@RequestParam(value = "b") String b, @RequestParam(value = "c") String c,
			@RequestParam(value = "d") String d, @RequestParam(value = "e") String e,
			@RequestParam(value = "f") String f, @RequestParam(value = "g") String g,
			@RequestParam(value = "h") String h, @RequestParam(value = "h1") String h1,
			@RequestParam(value = "h2") String h2, @RequestParam(value = "observaciones") String observaciones,
			@RequestParam(value = "estructura") String estructura, Model template) {

		/*
		 * NO DEBE MODIFICAR EL NRO DE ITEM.
		 * 
		 * // TRAE A LOS ITEMS DE UN PEDIDO ArrayList<Item> listaItemsXPed =
		 * traerItemsXPed(idPedido);
		 * 
		 * int ultIt; if (listaItemsXPed.size()==0) { ultIt = 0; } else { int cantPed =
		 * listaItemsXPed.size(); ultIt = (int) listaItemsXPed.get(cantPed -
		 * 1).getItem(); }
		 * 
		 * String item = Integer.toString(ultIt + 1);
		 * 
		 * if (item.length() == 1) { item = "00" + item; } if (item.length() == 2) {
		 * item = "0" + item; }
		 */

		// OBTENGO LARGO PARCIAL
		// LParcial = a + b+ c + d + e + f + g

		ArrayList<String> listaMedidas = new ArrayList<String>();
		ArrayList<Double> listaMedidasD = new ArrayList<Double>();
		listaMedidas.add(a);
		listaMedidas.add(b);
		listaMedidas.add(c);
		listaMedidas.add(d);
		listaMedidas.add(e);
		listaMedidas.add(f);
		listaMedidas.add(g);

		// PASA DE STRING A DOUBLE PARA HACER LA SUMATORIA
		for (String i : listaMedidas) {
			int cantDig = i.length();
			if (cantDig != 0) {
				double med = Double.parseDouble(i);
				listaMedidasD.add(med);
			} else {
				double medCero = 0;
				listaMedidasD.add(medCero);
			}
		}

		// HACE LA SUMATORIA
		double lParciald = 0;
		for (Double d1 : listaMedidasD) {
			lParciald += d1;
		}

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lParcialEntero = redondearDouble(lParciald);
		String lParcial = Integer.toString(lParcialEntero);

		// OBTENGO LARGO TOTAL
		// LTotal = cantidad * LParcial
		double cantd = Double.parseDouble(cantidad);
		double lTotald = cantd * lParciald;

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lTotalEntero = redondearDouble(lTotald);
		String lTotal = Integer.toString(lTotalEntero);

		// OBTENGO LARGO CORTAR
		// LCortar = LParcial - (Desc x Diam * (Cantidad de medidas - 1))
		// Desc x Diam lo saco de la tabla segun su diametro
		// Cantidad de medidas las saco poniendo un contador en los input.Lenght
		// != 0

		// TRAE A LA TABLA PARA LOS CALCULOS
		ArrayList<TablaCalculos> tablaCalculos = traerDatos();

		double descxd = 0;
		if (diametro.equals("2,5")) {
			diametro = "2.5";
		}
		if (diametro.equals("4,2")) {
			diametro = "4.2";
		}
		if (diametro.equals("2,71")) {
			diametro = "2.71";
		}
		if (diametro.equals("7,4")) {
			diametro = "7.4";
		}
		if (diametro.equals("9,02")) {
			diametro = "9.02";
		}
		if (diametro.equals("10,71")) {
			diametro = "10.71";
		}

		double diametrod = Double.parseDouble(diametro.replace(",", "."));
		double pxm = 0;

		
		// RECORRO LA TABLA PARA SACAR Desc x Diam
		for (TablaCalculos tc : tablaCalculos) {
			double diam = tc.getDiametro();
			if (diam == diametrod) {
				descxd = tc.getDescxd();
				pxm = tc.getPxm();
			}
		}

		ArrayList<String> listaControl = new ArrayList<String>();
		listaControl.add(a);
		listaControl.add(b);
		listaControl.add(c);
		listaControl.add(d);
		listaControl.add(e);
		listaControl.add(f);
		listaControl.add(g);

		// RECORRE LA LISTA DE LOS INPUT (DE A a G) Y SI NO ESTAN VACIOS,
		// AUMENTA EN 1 EL CONTADOR DE MEDIDAS
		int contMed = 0;
		for (int i = 0; i < listaControl.size(); i++) {
			int cantDigitos = listaControl.get(i).length();
			if (cantDigitos != 0) {
				contMed++;
			}
		}

		double LCortard = lParciald - (descxd * (contMed - 1));
		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lCortarEntero = redondearDouble(LCortard);
		String lCortar = Integer.toString(lCortarEntero);

		// peso = LParcial * P x M
		double pesod = lTotald * pxm / 100;
		System.out.println("peso = " + "ltotald (" + lTotald + ") * pxm(" + pxm + ") / 100");

		// Ya no se debe redondear el peso.
		pesod = Math.round(pesod* 10d) / 10d;
		String peso = Double.toString(pesod);

		if (diametro.equals("1") || diametro.equals("2") || diametro.equals("2,5") || diametro.equals("4,2")
				|| diametro.equals("6") || diametro.equals("8") || diametro.equals("10") || diametro.equals("12")
				|| diametro.equals("16") || diametro.equals("20") || diametro.equals("25") || diametro.equals("32")
				|| diametro.equals("2,71") || diametro.equals("7,40") || diametro.equals("9,02")
				|| diametro.equals("10,71") || diametro.equals("2.71") || diametro.equals("7.40") || diametro.equals("9.02")
				|| diametro.equals("10.71")) {
			if (posicion.length() != 0 && diametro.length() != 0 && cantidad.length() != 0 && formato.length() != 0
					&& dibujo.length() != 0 && a.length() != 0 && estructura.length() != 0) {
				this.jdbcTemplate.update(
						"UPDATE items SET Posicion=?, Acero=?, Diametro=?, Cantidad=?, Formato=?, Dibujo=?, A=?, B=?, C=?, D=?, E=?, F=?, G=?, H=?, H1=?, H2=?, LParcial=?, LTotal=?, LCortar=?, Peso=?, Observaciones=?, Estructura =?  WHERE IDpedido = ? AND Item = ?;",
						posicion, acero, diametro, cantidad, formato, dibujo, a, b, c, d, e, f, g, h, h1, h2, lParcial,
						lTotal, lCortar, peso, observaciones, estructura, idPedido, idItem);
				// TRAE LOS ITEMS DEL PEDIDO, HACE LA SUMA DE LOS KG POR
				// DIAMETRO Y ACTUALIZA LOS VALORES DENTRO DEL PEDIDO
				ArrayList<Item> itemsPedido = traerItems(idPedido);

				double contCuatro = 0;
				double contSeis = 0;
				double contOcho = 0;
				double contDiez = 0;
				double contDoce = 0;
				double contDeciseis = 0;
				double contVeinte = 0;
				double contVeinticinco = 0;
				double contTreintaydos = 0;
				double contOtros = 0;
				for (Item i : itemsPedido) {
					String diam = i.getDiametro();
					String peso1 = (i.getPeso()).replace(",", ".");
					
					if (diam.equals("4.2")) {
						contCuatro += Double.parseDouble(peso1);
					} else if (diam.equals("6")) {
						contSeis += Double.parseDouble(peso1);
					} else if (diam.equals("8")) {
						contOcho += Double.parseDouble(peso1);
					} else if (diam.equals("10")) {
						contDiez += Double.parseDouble(peso1);
					} else if (diam.equals("12")) {
						contDoce += Double.parseDouble(peso1);
					} else if (diam.equals("16")) {
						contDeciseis += Double.parseDouble(peso1);
					} else if (diam.equals("20")) {
						contVeinte += Double.parseDouble(peso1);
					} else if (diam.equals("25")) {
						contVeinticinco += Double.parseDouble(peso1);
					} else if (diam.equals("32")) {
						contTreintaydos += Double.parseDouble(peso1);
					} else if (diam.equals("2,71") || diam.equals("2.71")) {
						contOtros += Double.parseDouble(peso1);
					} else if (diam.equals("7,40") || diam.equals("7.40")) {
						contOtros += Double.parseDouble(peso1);
					} else if (diam.equals("9,02") || diam.equals("9.02")) {
						contOtros += Double.parseDouble(peso1);
					} else if (diam.equals("10,71") || diam.equals("10.71")) {
						contOtros += Double.parseDouble(peso1);
					} else if (diam.equals("1")) {
						contOtros += Double.parseDouble(peso1);
					} else if (diam.equals("2")) {
						contOtros += Double.parseDouble(peso1);
					} else if (diam.equals("2,5")) {
						contOtros += Double.parseDouble(peso1);
					}

				}
				contCuatro = setearUnDecimal(contCuatro);
				contSeis = setearUnDecimal(contSeis);
				contOcho = setearUnDecimal(contOcho);
				contDiez = setearUnDecimal(contDiez);
				contDoce = setearUnDecimal(contDoce);
				contDeciseis = setearUnDecimal(contDeciseis);
				contVeinte = setearUnDecimal(contVeinte);
				contVeinticinco = setearUnDecimal(contVeinticinco);
				contTreintaydos = setearUnDecimal(contTreintaydos);
				contOtros = setearUnDecimal(contOtros);
				this.jdbcTemplate.update("UPDATE pedidos SET Cuatrocomados=? WHERE ID=?;", contCuatro, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Seis=? WHERE ID=?;", contSeis, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Ocho=? WHERE ID=?;", contOcho, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Diez=? WHERE ID=?;", contDiez, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Doce=? WHERE ID=?;", contDoce, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Dieciseis=? WHERE ID=?;", contDeciseis, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Veinte=? WHERE ID=?;", contVeinte, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Veinticinco=? WHERE ID=?;", contVeinticinco, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Treintaydos=? WHERE ID=?;", contTreintaydos, idPedido);
				this.jdbcTemplate.update("UPDATE pedidos SET Otros=? WHERE ID=?;", contOtros, idPedido);

				double totalkilos = contCuatro + contSeis + contOcho + contDiez + contDoce + contDeciseis + contVeinte
						+ contVeinticinco + contTreintaydos + contOtros;
				totalkilos = setearUnDecimal(totalkilos);
				this.jdbcTemplate.update("UPDATE pedidos SET TotalKg=? WHERE ID=?;", totalkilos, idPedido);
				return "redirect:" + "/productos/items/" + idPedido;
			} else {
				template.addAttribute("posicion", posicion);
				template.addAttribute("acero", acero);
				template.addAttribute("diametro", diametro);
				template.addAttribute("cantidad", cantidad);
				template.addAttribute("formato", formato);
				template.addAttribute("dibujo", dibujo);
				template.addAttribute("a", a);
				template.addAttribute("b", b);
				template.addAttribute("c", c);
				template.addAttribute("d", d);
				template.addAttribute("e", e);
				template.addAttribute("f", f);
				template.addAttribute("g", g);
				template.addAttribute("h", h);
				template.addAttribute("h1", h1);
				template.addAttribute("h2", h2);
				template.addAttribute("observaciones", observaciones);
				template.addAttribute("estructura", estructura);
				return "modificarItem";
			}
		} else {
			template.addAttribute("diametroCorrespondiente", false);
			return "resultados";
		}
	}
	// FIN DE MODIFICAR ITEM

	// ELIMINAR FORMATO
	@RequestMapping("/productos/formatos/eliminar/{id}")
	public String eliminarFormato(@PathVariable(value = "id") int idItem, Model template) {
		jdbcTemplate.update("DELETE FROM formatos WHERE ID = ?;", idItem);
		return "redirect:" + "/productos/formatos";
	}
	// FIN DE ELEIMINAR FORMATO

	// MODIFICAR FORMATO
	// GET
	@RequestMapping(value = "productos/formatos/modificar/{id}", method = RequestMethod.GET)
	public String formato(@PathVariable(value = "id") int idItem, Model template) {
		SqlRowSet formatosBD;
		formatosBD = jdbcTemplate.queryForRowSet("SELECT * FROM formatos WHERE ID = ?;", idItem);
		formatosBD.next();
		long posicion = formatosBD.getInt("posicion");
		String formato = formatosBD.getString("formato");
		String lados = formatosBD.getString("lados");
		String cant_doblados = formatosBD.getString("cant_doblados");
		template.addAttribute("posicion", posicion);
		template.addAttribute("formato", formato);
		template.addAttribute("lados", lados);
		template.addAttribute("cant_doblados", cant_doblados);

		return "modificarFormato";
	}

	// POST
	@RequestMapping(value = "/productos/formatos/modificar/{id}", method = RequestMethod.POST)
	public String modifcarFormato(@PathVariable(value = "id") int idItem,
			@RequestParam(value = "posicion") String posicion, @RequestParam(value = "formato") String formato,
			@RequestParam(value = "lados") String lados,
			@RequestParam (value = "cant_doblados") String cant_doblados, Model template) {
		
		try {
			if (Integer.parseInt(lados) > 8 || Integer.parseInt(lados) < 1) {
				template.addAttribute("lados", false);
				return "resultados";
			}}
			catch(Exception e) {
				template.addAttribute("camposObligatorios", false);
				return "resultados";

			}
			
			
		try{
			Integer.parseInt(cant_doblados);
		}
		catch(Exception e) {
			template.addAttribute("cant_doblados", false);
			return "resultados";
		
		}

		if (formato.length() != 0 && posicion.length() != 0 && lados.length() != 0 && cant_doblados.length() != 0) {
			this.jdbcTemplate.update("UPDATE formatos SET Posicion=?, Formato=?, Lados = ?, Cant_doblados = ? WHERE ID = ?;", posicion,
					formato, lados,cant_doblados, idItem);
			return "redirect:" + "/productos/formatos";
		} else {
			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
		
	}
	// FIN DE MODIFICAR FORMATO

	// ELIMINAR PEDIDO PROGRAMACION
	@RequestMapping("/programacion/pedidos/eliminar/{id}")
	public String eliminarPedido(@PathVariable(value = "id") int idPedido, Model template) {
		jdbcTemplate.update("DELETE FROM pedidos WHERE id = ?;", idPedido);

		// NOSE SI HAY QUE HACER ESTO, PORQUE AL CAMBIAR EL NRO DE PEDIDO
		// TAMBIEN CAMBIA EL CODIGO DE BARRAS

		/*
		 * // TRAE A LOS PEDIDOS, BUSCA POR ID // HAGO ESTO PARA SACAR EL DATO DE LA
		 * OBRA Y PODER USAR LA FUNCION "traerPedidosPorObra(String obra)"
		 * ArrayList<Pedido> listaPedidoIP = traerPedidosIP(idPedido);
		 * 
		 * // OBTENGO EL VALOR DE LA OBRA String obra = listaPedidoIP.get(0).getObra();
		 * 
		 * // TRAE A LOS PEDIDOS POR OBRA ArrayList<Pedido> listaPedidoPorObra =
		 * traerPedidosPorObra(obra);
		 * 
		 * int pedido = 000; for(@SuppressWarnings("unused") Pedido p :
		 * listaPedidoPorObra) { pedido++; this.jdbcTemplate.update(
		 * "UPDATE pedidos SET Pedido=? WHERE Obra=?;", pedido, obra); }
		 */
		return "redirect:" + "/programacion/pedidos";
	}
	// FIN DE ELIMINAR PEDIDO

	// ELIMINAR PEDIDO ETIQUETADO
	@RequestMapping("/etiquetado/pedidos/eliminar/{id}")
	public String eliminarPedidoE(@PathVariable(value = "id") int idPedido, Model template) {
		jdbcTemplate.update("DELETE FROM pedidos WHERE id = ?;", idPedido);

		// NOSE SI HAY QUE HACER ESTO, PORQUE AL CAMBIAR EL NRO DE PEDIDO
		// TAMBIEN CAMBIA EL CODIGO DE BARRAS

		/*
		 * // TRAE A LOS PEDIDOS, BUSCA POR ID // HAGO ESTO PARA SACAR EL DATO DE LA
		 * OBRA Y PODER USAR LA FUNCION "traerPedidosPorObra(String obra)"
		 * ArrayList<Pedido> listaPedidoIP = traerPedidosIP(idPedido);
		 * 
		 * // OBTENGO EL VALOR DE LA OBRA String obra = listaPedidoIP.get(0).getObra();
		 * 
		 * // TRAE A LOS PEDIDOS POR OBRA ArrayList<Pedido> listaPedidoPorObra =
		 * traerPedidosPorObra(obra);
		 * 
		 * int pedido = 000; for(@SuppressWarnings("unused") Pedido p :
		 * listaPedidoPorObra) { pedido++; this.jdbcTemplate.update(
		 * "UPDATE pedidos SET Pedido=? WHERE Obra=?;", pedido, obra); }
		 */

		return "redirect:" + "/etiquetado/pedidos";
	}
	// FIN DE ELIMINAR PEDIDO

	// MODIFICAR PEDIDO ETIQUETADO
	@RequestMapping(value = "etiquetado/pedidos/modificar/{id}", method = RequestMethod.GET)
	public String modificarPedido(@PathVariable(value = "id") int idPedido, Model template) {
		// TRAE EL PEDIDO
		ArrayList<Pedido> pedido = traerPedidosIP(idPedido);
		String entrega = pedido.get(0).getEntrega();
		String entregaN = "";
		for (int x = 0; x < entrega.length(); x++) {
			if (entrega.charAt(x) != '/') {
				entregaN += entrega.charAt(x) + "";
			}
		}
		if (entregaN.length() == 8) {
			entregaN = invertirModificar(entregaN);
		}
		if (entregaN.length() == 11) {
			entregaN = invertirConAsteriscos(entregaN);
		}
		template.addAttribute("entrega", entregaN);
		template.addAttribute("descripcion", pedido.get(0).getDescripcion());
		template.addAttribute("cliente", pedido.get(0).getCliente());
		template.addAttribute("obra", pedido.get(0).getObra());
		template.addAttribute("tipo", pedido.get(0).getTipo());
		template.addAttribute("cuatrocomados", pedido.get(0).getCuatrocomados());
		template.addAttribute("seis", pedido.get(0).getSeis());
		template.addAttribute("ocho", pedido.get(0).getOcho());
		template.addAttribute("diez", pedido.get(0).getDiez());
		template.addAttribute("doce", pedido.get(0).getDoce());
		template.addAttribute("dieciseis", pedido.get(0).getDieciseis());
		template.addAttribute("veinte", pedido.get(0).getVeinte());
		template.addAttribute("veinticinco", pedido.get(0).getVeinticinco());
		template.addAttribute("treintaydos", pedido.get(0).getTreintaydos());
		template.addAttribute("oc", pedido.get(0).getOc());
		template.addAttribute("elementos", pedido.get(0).getElementos());

		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientes");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}
		// SE FIJA SI ESTA ETIQUETADO O NO PARA MOSTRAR LOS DIAMETROS
		SqlRowSet pedidoo;
		pedidoo = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID =?", idPedido);
		pedidoo.next();
		if (pedidoo.getString("Estado").equals("ETIQUETADO")) {
			template.addAttribute("ETIQUETADO", false);
		} else {
			template.addAttribute("ETIQUETADO", true);
		}
		template.addAttribute("lisClientes", listaClientes);
		return "modificarPedidoE";
	}

	// POST
	@RequestMapping(value = "/etiquetado/pedidos/modificar/{id}", method = RequestMethod.POST)
	public String modifcarPedido(@PathVariable(value = "id") int idPedido,
			@RequestParam(value = "entrega") String entrega, @RequestParam(value = "cliente") String cliente,
			@RequestParam(value = "obra") String obra, @RequestParam(value = "descripcion") String descripcion,
			@RequestParam(value = "tipo") String tipo, @RequestParam(value = "cuatrocomados") String cuatrocomados,
			@RequestParam(value = "seis") String seis, @RequestParam(value = "ocho") String ocho,
			@RequestParam(value = "diez") String diez, @RequestParam(value = "doce") String doce,
			@RequestParam(value = "dieciseis") String dieciseis, @RequestParam(value = "veinte") String veinte,
			@RequestParam(value = "veinticinco") String veinticinco,
			@RequestParam(value = "treintaydos") String treintaydos, @RequestParam(value = "oc") String oc,
			@RequestParam(value = "elementos") String elementos, Model template) {

		/*
		 * NO SE DEBE MODIFICAR EL NRO DE PEDIDO. Y POR CONSIGUIENTE, EL CODIGO TAMPOCO
		 * 
		 * // TRAE PEDIDOS POR OBRA Y POR CLIENTE ArrayList<Pedido> listaPedidosPOYPC =
		 * traerPedidosPOYPC(obra, cliente);
		 * 
		 * int ultPed; if (listaPedidosPOYPC.size()==0) { ultPed = 0; } else { int
		 * cantPed = listaPedidosPOYPC.size(); ultPed =
		 * Integer.parseInt(listaPedidosPOYPC.get(cantPed - 1).getPedido()); }
		 * 
		 * // SETEA NUMERO DE PEDIDO String pedido = Integer.toString(ultPed + 1);
		 * 
		 * if (pedido.length() == 1) { pedido = "00" + pedido; } if (pedido.length() ==
		 * 2) { pedido = "0" + pedido; }
		 */
		for (int i = 0; i < entrega.length(); i++) {
			if (entrega.charAt(i) == 'a' || entrega.charAt(i) == 'b' || entrega.charAt(i) == 'c'
					|| entrega.charAt(i) == 'd' || entrega.charAt(i) == 'e' || entrega.charAt(i) == 'f'
					|| entrega.charAt(i) == 'g' || entrega.charAt(i) == 'h' || entrega.charAt(i) == 'i'
					|| entrega.charAt(i) == 'j' || entrega.charAt(i) == 'k' || entrega.charAt(i) == 'l'
					|| entrega.charAt(i) == 'm' || entrega.charAt(i) == 'n' || entrega.charAt(i) == 'o'
					|| entrega.charAt(i) == 'p' || entrega.charAt(i) == 'q' || entrega.charAt(i) == 'r'
					|| entrega.charAt(i) == 's' || entrega.charAt(i) == 't' || entrega.charAt(i) == 'u'
					|| entrega.charAt(i) == 'v' || entrega.charAt(i) == 'w' || entrega.charAt(i) == 'x'
					|| entrega.charAt(i) == 'y' || entrega.charAt(i) == 'z' || entrega.charAt(i) == 'A'
					|| entrega.charAt(i) == 'B' || entrega.charAt(i) == 'C' || entrega.charAt(i) == 'D'
					|| entrega.charAt(i) == 'E' || entrega.charAt(i) == 'F' || entrega.charAt(i) == 'G'
					|| entrega.charAt(i) == 'H' || entrega.charAt(i) == 'I' || entrega.charAt(i) == 'J'
					|| entrega.charAt(i) == 'K' || entrega.charAt(i) == 'L' || entrega.charAt(i) == 'M'
					|| entrega.charAt(i) == 'N' || entrega.charAt(i) == 'O' || entrega.charAt(i) == 'P'
					|| entrega.charAt(i) == 'Q' || entrega.charAt(i) == 'R' || entrega.charAt(i) == 'S'
					|| entrega.charAt(i) == 'T' || entrega.charAt(i) == 'U' || entrega.charAt(i) == 'V'
					|| entrega.charAt(i) == 'W' || entrega.charAt(i) == 'X' || entrega.charAt(i) == 'Y'
					|| entrega.charAt(i) == 'Z' || entrega.charAt(i) == '-' || entrega.charAt(i) == '/'
					|| entrega.charAt(i) == 'ñ' || entrega.charAt(i) == 'Ñ') {
				template.addAttribute("fechaValida", false);
				return "resultados";
			}
		}

		// TRAE A LOS CLIENTES
		ArrayList<Cliente> listaClientes = traerClientes();
		template.addAttribute("listaClientes", listaClientes);

		// TRAE A LAS OBRAS
		ArrayList<Obra> listaObras = traerObras();
		template.addAttribute("listaObras", listaObras);

		// TOTAL KG
		ArrayList<String> listaDiam = new ArrayList<String>();
		listaDiam.add(cuatrocomados);
		listaDiam.add(seis);
		listaDiam.add(ocho);
		listaDiam.add(diez);
		listaDiam.add(doce);
		listaDiam.add(dieciseis);
		listaDiam.add(veinte);
		listaDiam.add(veinticinco);
		listaDiam.add(treintaydos);

		// CALCULAR TOTAL KG
		double totalkgI = 0;
		for (int i = 0; i < listaDiam.size(); i++) {
			if (listaDiam.get(i).length() != 0) {
				double num = Double.parseDouble(listaDiam.get(i));
				totalkgI += num;
			}
		}
		String totalkg = Double.toString(totalkgI);

		// ARMAR CODIGO
		/*
		 * String codCli = "algo"; String codObr = "algo"; for (int i = 0; i <
		 * listaClientes.size(); i++) { if
		 * (listaClientes.get(i).getNombreFantasia().equals(cliente)) { codCli =
		 * listaClientes.get(i).getCodigo(); } } for (int i = 0; i < listaObras.size();
		 * i++) { if (listaObras.get(i).getNombreDeObra().equals(obra)) { codObr =
		 * listaObras.get(i).getCodigo(); } } String codigo = codCli + codObr + pedido;
		 */

		int b, c, e, f;
		// a = entrega.length();
		b = cliente.length();
		c = obra.length();
		e = descripcion.length();
		f = tipo.length();

		// VER SI SE CAMBIO LA FECHA
		int x = 0;
		ArrayList<Pedido> ped = traerPedidosIP(idPedido);
		String fechaa = ped.get(0).getEntrega();
		String entregaN = "";
		for (int f1 = 0; f1 < fechaa.length(); f1++) {
			if (fechaa.charAt(f1) != '/') {
				entregaN += fechaa.charAt(f1) + "";
			}
		}
		if (entregaN.length() == 8) {
			entregaN = invertirModificar(entregaN);
		}
		boolean tieneAsteriscos = false;
		if (entregaN.length() == 11) {
			entregaN = invertirConAsteriscos(entregaN);
			tieneAsteriscos = true;

		}
		if (entrega.length() == 6) {
			entrega = agregar20(entrega);
		}
		if (tieneAsteriscos && entrega.length() == 8) {
			entrega = entrega + "***";
		}
		if (!entrega.equals(entregaN)) {
			x = 1;
		}
		// INVERTIR FECHA
		if (entrega.length() == 8) {
			// DDMMAAAA
			entrega = invertirCadena(entrega);
		}
		if (entrega.length() == 6) {
			// DDMMAA
			entrega = invertirCadenaMas(entrega);
		}
		if (entrega.length() == 11) {
			entrega = invertirCadenaMasAsteriscos(entrega);
		}
		if (entrega.length() != 10 && entrega.length() != 13) {
			entrega = "";
		}
		if (entrega.length() != 0 && b != 0 && c != 0 && e != 0 && f != 0) {
			// SAQUE CODIGO Y PEDIDO DE LA QUERY
			this.jdbcTemplate.update(
					"UPDATE pedidos SET Entrega=?, Cliente=?, Obra=?, Descripcion=?, Tipo=?, Totalkg=?, Cuatrocomados=?, Seis=?, Ocho=?, Diez=?, Doce=?, Dieciseis=?, Veinte=?, Veinticinco=?, Treintaydos=?, OC=?, Elementos=? WHERE ID = ?;",
					entrega, cliente, obra, descripcion, tipo, totalkg, cuatrocomados, seis, ocho, diez, doce,
					dieciseis, veinte, veinticinco, treintaydos, oc, elementos, idPedido);
			if (x == 1) {
				return "redirect:" + "/motivo/" + idPedido;
			} else {
				return "redirect:" + "/etiquetado/pedidos";
			}
		} else {
			if (entrega.length() == 0) {
				template.addAttribute("fechaValida", false);
				return "resultados";
			}
			template.addAttribute("campoVacio", true);
			return "resultados";
		}
	}

	// FIN DE MODIFICAR PEDIDO ETIQUETADO
	// MOTIVO DE EDITAR LA FECHA DE ENTREGA
	// GET
	@RequestMapping(value = "/motivo/{idPedido}", method = RequestMethod.GET)
	public String motivoGET(@PathVariable(value = "idPedido") int idPedido, Model template) {
		return "motivo";
	}

	// POST
	@RequestMapping(value = "/motivo/{idPedido}", method = RequestMethod.POST)
	public String modificarObraPost(@PathVariable(value = "idPedido") int idPedido, Model template,
			@RequestParam(value = "motivo") String motivo) {
		Date fecha = new Date();
		if (motivo.length() != 0) {
			ArrayList<Pedido> pedido = traerPedidosIP(idPedido);
			if (pedido.get(0).getEntrega().length() == 10) {
				String entrega = new String();
				entrega = pedido.get(0).getEntrega();
				entrega += "***";
				this.jdbcTemplate.update("UPDATE pedidos SET Entrega=? WHERE ID = ?;", entrega, idPedido);
			}
			this.jdbcTemplate.update("INSERT INTO motivos(Motivo, Fecha, ID_Pedido) VALUES (?,?,?);", motivo, fecha,
					idPedido);
			return "redirect:" + "/etiquetado/pedidos";
		} else {
			template.addAttribute("hayMotivo", false);
			return "resultados";
		}

	}
	// FIN DE MOTIVO DE EDITAR LA FECHA DE ENTREGA

	// MOTIVO DE EDITAR LA FECHA DE ENTREGA
	// GET
	@RequestMapping(value = "/motivos/{idPedido}", method = RequestMethod.GET)
	public String motivoGETb(@PathVariable(value = "idPedido") int idPedido, Model template) {
		return "motivo";
	}

	// POST
	@RequestMapping(value = "/motivos/{idPedido}", method = RequestMethod.POST)
	public String modificarMotivoPostb(@PathVariable(value = "idPedido") int idPedido, Model template,
			@RequestParam(value = "motivo") String motivo) {
		Date fecha = new Date();
		if (motivo.length() != 0) {
			ArrayList<Pedido> pedido = traerPedidosIP(idPedido);
			if (pedido.get(0).getEntrega().length() == 10) {
				String entrega = new String();
				entrega = pedido.get(0).getEntrega();
				entrega += "***";
				this.jdbcTemplate.update("UPDATE pedidos SET Entrega=? WHERE ID = ?;", entrega, idPedido);
			}
			this.jdbcTemplate.update("INSERT INTO motivos(Motivo, Fecha, ID_Pedido) VALUES (?,?,?);", motivo, fecha,
					idPedido);
			return "redirect:" + "/programacion/pedidos";
		} else {
			template.addAttribute("hayMotivo", false);
			return "resultados";
		}
	}
	// FIN DE MOTIVO DE EDITAR LA FECHA DE ENTREGA

	// MOSTRAR MOTIVO
	// GET
	@RequestMapping(value = "mostrarMotivo/{idPedido}", method = RequestMethod.GET)
	public String motivos(@PathVariable(value = "idPedido") String idPedido, Model template) {

		// TRAE A LOS ITEMS
		Motivo motivo = traerMotivoPorIdPedido(Integer.parseInt(idPedido));
		if (motivo.getId() == 0) {
			return "redirect:" + "/etiquetado/pedidos";
		}
		ArrayList<Motivo> motivos = traerMotivosPorIdPedido(Integer.parseInt(idPedido));
		template.addAttribute("motivos", motivos);
		return "mostrarMotivo";
	}
	// FIN DE MOSTRAR MOTIVOS

	// MOSTRAR MOTIVO
	// GET
	@RequestMapping(value = "mostrarMotivos/{idPedido}", method = RequestMethod.GET)
	public String motivosb(@PathVariable(value = "idPedido") String idPedido, Model template) {

		// TRAE A LOS ITEMS
		Motivo motivo = traerMotivoPorIdPedido(Integer.parseInt(idPedido));
		if (motivo.getId() == 0) {
			return "redirect:" + "/programacion/pedidos";
		}
		template.addAttribute("id", motivo.getId());
		template.addAttribute("motivo", motivo.getMotivo());
		template.addAttribute("fecha", motivo.getFecha());

		return "mostrarMotivosb";
	}
	// FIN DE MOSTRAR MOTIVOS

	// ELIMINAR MOTIVO
	@RequestMapping("/motivo/eliminar/{id}")
	public String eliminarMotivo(@PathVariable(value = "id") int idMotivo, Model template) {
		jdbcTemplate.update("DELETE FROM motivos WHERE ID = ?;", idMotivo);
		return "redirect:" + "/etiquetado/pedidos";
	}
	// FIN DE ELIMINAR MOTIVO

	// ELIMINAR MOTIVO
	@RequestMapping("/motivos/eliminar/{id}")
	public String eliminarMotivob(@PathVariable(value = "id") int idMotivo, Model template) {
		jdbcTemplate.update("DELETE FROM motivos WHERE ID = ?;", idMotivo);
		return "redirect:" + "/programacion/pedidos";
	}
	// FIN DE ELIMINAR MOTIVO

	// MODIFICAR PEDIDO PROGRAMACION
	@RequestMapping(value = "programacion/pedidos/modificar/{id}", method = RequestMethod.GET)
	public String modificarPedidoProgramacion(@PathVariable(value = "id") int idPedido, Model template) {
		// TRAE EL PEDIDO
		ArrayList<Pedido> pedido = traerPedidosIP(idPedido);
		String entrega = pedido.get(0).getEntrega();
		String entregaN = "";
		for (int x = 0; x < entrega.length(); x++) {
			if (entrega.charAt(x) != '/') {
				entregaN += entrega.charAt(x) + "";
			}
		}
		if (entregaN.length() == 8) {
			entregaN = invertirModificar(entregaN);
		}
		if (entregaN.length() == 11) {
			entregaN = invertirConAsteriscos(entregaN);
		}
		template.addAttribute("entrega", entregaN);
		template.addAttribute("descripcion", pedido.get(0).getDescripcion());
		template.addAttribute("cliente", pedido.get(0).getCliente());
		template.addAttribute("obra", pedido.get(0).getObra());
		template.addAttribute("tipo", pedido.get(0).getTipo());
		template.addAttribute("cuatrocomados", pedido.get(0).getCuatrocomados());
		template.addAttribute("seis", pedido.get(0).getSeis());
		template.addAttribute("ocho", pedido.get(0).getOcho());
		template.addAttribute("diez", pedido.get(0).getDiez());
		template.addAttribute("doce", pedido.get(0).getDoce());
		template.addAttribute("dieciseis", pedido.get(0).getDieciseis());
		template.addAttribute("veinte", pedido.get(0).getVeinte());
		template.addAttribute("veinticinco", pedido.get(0).getVeinticinco());
		template.addAttribute("treintaydos", pedido.get(0).getTreintaydos());
		template.addAttribute("oc", pedido.get(0).getOc());
		template.addAttribute("elementos", pedido.get(0).getElementos());

		// TRAE EL NOMBRE DE FANTASIA DE LOS CLIENTES
		SqlRowSet clientes;
		clientes = jdbcTemplate.queryForRowSet("SELECT NombreFantasia FROM clientes");
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		while (clientes.next()) {
			String nombreFantasia = clientes.getString("NombreFantasia");
			Cliente cli = new Cliente(nombreFantasia);
			listaClientes.add(cli);
		}
		template.addAttribute("lisClientes", listaClientes);

		// SE FIJA SI ESTA ETIQUETADO O NO PARA MOSTRAR LOS DIAMETROS
		SqlRowSet pedidoo;
		pedidoo = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID =?", idPedido);
		pedidoo.next();
		if (pedidoo.getString("Estado").equals("ETIQUETADO")) {
			template.addAttribute("ETIQUETADO", false);
		} else {
			template.addAttribute("ETIQUETADO", true);
		}

		return "modificarPedido";
	}

	// POST
	@RequestMapping(value = "/programacion/pedidos/modificar/{id}", method = RequestMethod.POST)
	public String modifcarPedidoProgramacion(@PathVariable(value = "id") int idPedido,
			@RequestParam(value = "entrega") String entrega, @RequestParam(value = "cliente") String cliente,
			@RequestParam(value = "obra") String obra, @RequestParam(value = "descripcion") String descripcion,
			@RequestParam(value = "tipo") String tipo, @RequestParam(value = "cuatrocomados") String cuatrocomados,
			@RequestParam(value = "seis") String seis, @RequestParam(value = "ocho") String ocho,
			@RequestParam(value = "diez") String diez, @RequestParam(value = "doce") String doce,
			@RequestParam(value = "dieciseis") String dieciseis, @RequestParam(value = "veinte") String veinte,
			@RequestParam(value = "veinticinco") String veinticinco,
			@RequestParam(value = "treintaydos") String treintaydos, @RequestParam(value = "oc") String oc,
			@RequestParam(value = "elementos") String elementos, Model template) {

		/*
		 * NO SE DEBE MODIFICAR EL NRO DE PEDIDO. Y POR CONSIGUIENTE, EL CODIGO TAMPOCO
		 * 
		 * // TRAE PEDIDOS POR OBRA Y POR CLIENTE ArrayList<Pedido> listaPedidosPOYPC =
		 * traerPedidosPOYPC(obra, cliente);
		 * 
		 * int ultPed; if (listaPedidosPOYPC.size()==0) { ultPed = 0; } else { int
		 * cantPed = listaPedidosPOYPC.size(); ultPed =
		 * Integer.parseInt(listaPedidosPOYPC.get(cantPed - 1).getPedido()); }
		 * 
		 * // SETEA NUMERO DE PEDIDO String pedido = Integer.toString(ultPed + 1);
		 * 
		 * if (pedido.length() == 1) { pedido = "00" + pedido; } if (pedido.length() ==
		 * 2) { pedido = "0" + pedido; }
		 */

		for (int i = 0; i < entrega.length(); i++) {
			if (entrega.charAt(i) == 'a' || entrega.charAt(i) == 'b' || entrega.charAt(i) == 'c'
					|| entrega.charAt(i) == 'd' || entrega.charAt(i) == 'e' || entrega.charAt(i) == 'f'
					|| entrega.charAt(i) == 'g' || entrega.charAt(i) == 'h' || entrega.charAt(i) == 'i'
					|| entrega.charAt(i) == 'j' || entrega.charAt(i) == 'k' || entrega.charAt(i) == 'l'
					|| entrega.charAt(i) == 'm' || entrega.charAt(i) == 'n' || entrega.charAt(i) == 'o'
					|| entrega.charAt(i) == 'p' || entrega.charAt(i) == 'q' || entrega.charAt(i) == 'r'
					|| entrega.charAt(i) == 's' || entrega.charAt(i) == 't' || entrega.charAt(i) == 'u'
					|| entrega.charAt(i) == 'v' || entrega.charAt(i) == 'w' || entrega.charAt(i) == 'x'
					|| entrega.charAt(i) == 'y' || entrega.charAt(i) == 'z' || entrega.charAt(i) == 'A'
					|| entrega.charAt(i) == 'B' || entrega.charAt(i) == 'C' || entrega.charAt(i) == 'D'
					|| entrega.charAt(i) == 'E' || entrega.charAt(i) == 'F' || entrega.charAt(i) == 'G'
					|| entrega.charAt(i) == 'H' || entrega.charAt(i) == 'I' || entrega.charAt(i) == 'J'
					|| entrega.charAt(i) == 'K' || entrega.charAt(i) == 'L' || entrega.charAt(i) == 'M'
					|| entrega.charAt(i) == 'N' || entrega.charAt(i) == 'O' || entrega.charAt(i) == 'P'
					|| entrega.charAt(i) == 'Q' || entrega.charAt(i) == 'R' || entrega.charAt(i) == 'S'
					|| entrega.charAt(i) == 'T' || entrega.charAt(i) == 'U' || entrega.charAt(i) == 'V'
					|| entrega.charAt(i) == 'W' || entrega.charAt(i) == 'X' || entrega.charAt(i) == 'Y'
					|| entrega.charAt(i) == 'Z' || entrega.charAt(i) == '-' || entrega.charAt(i) == '/'
					|| entrega.charAt(i) == 'ñ' || entrega.charAt(i) == 'Ñ') {
				template.addAttribute("fechaValida", false);
				return "resultados";
			}
		}

		// TRAE A LOS CLIENTES
		ArrayList<Cliente> listaClientes = traerClientes();
		template.addAttribute("listaClientes", listaClientes);

		// TRAE A LAS OBRAS
		ArrayList<Obra> listaObras = traerObras();
		template.addAttribute("listaObras", listaObras);

		// TOTAL KG
		ArrayList<String> listaDiam = new ArrayList<String>();
		listaDiam.add(cuatrocomados);
		listaDiam.add(seis);
		listaDiam.add(ocho);
		listaDiam.add(diez);
		listaDiam.add(doce);
		listaDiam.add(dieciseis);
		listaDiam.add(veinte);
		listaDiam.add(veinticinco);
		listaDiam.add(treintaydos);

		// CALCULAR TOTAL KG
		double totalkgI = 0;
		for (int i = 0; i < listaDiam.size(); i++) {
			if (listaDiam.get(i).length() != 0) {
				double num = Double.parseDouble(listaDiam.get(i));
				totalkgI += num;
			}
		}
		String totalkg = Double.toString(totalkgI);

		// ARMAR CODIGO
		/*
		 * String codCli = "algo"; String codObr = "algo"; for (int i = 0; i <
		 * listaClientes.size(); i++) { if
		 * (listaClientes.get(i).getNombreFantasia().equals(cliente)) { codCli =
		 * listaClientes.get(i).getCodigo(); } } for (int i = 0; i < listaObras.size();
		 * i++) { if (listaObras.get(i).getNombreDeObra().equals(obra)) { codObr =
		 * listaObras.get(i).getCodigo(); } }
		 */
		// String codigo = codCli + codObr + pedido;

		int b, c, e, f;
		// a = entrega.length();
		b = cliente.length();
		c = obra.length();
		e = descripcion.length();
		f = tipo.length();

		// VER SI SE CAMBIO LA FECHA
		int x = 0;
		ArrayList<Pedido> ped = traerPedidosIP(idPedido);
		String fechaa = ped.get(0).getEntrega();
		String entregaN = "";
		for (int f1 = 0; f1 < fechaa.length(); f1++) {
			if (fechaa.charAt(f1) != '/') {
				entregaN += fechaa.charAt(f1) + "";
			}
		}
		if (entregaN.length() == 8) {
			entregaN = invertirModificar(entregaN);
		}
		boolean tieneAsteriscos = false;
		if (entregaN.length() == 11) {
			entregaN = invertirConAsteriscos(entregaN);
			tieneAsteriscos = true;

		}
		if (entrega.length() == 6) {
			entrega = agregar20(entrega);
		}
		if (tieneAsteriscos && entrega.length() == 8) {
			entrega = entrega + "***";
		}
		if (!entrega.equals(entregaN)) {
			x = 1;
		}
		// INVERTIR FECHA
		if (entrega.length() == 8) {
			// DDMMAAAA
			entrega = invertirCadena(entrega);
		}
		if (entrega.length() == 6) {
			// DDMMAA
			entrega = invertirCadenaMas(entrega);
		}
		if (entrega.length() == 11) {
			entrega = invertirCadenaMasAsteriscos(entrega);
		}
		if (entrega.length() != 10 && entrega.length() != 13) {
			entrega = "";
		}
		if (entrega.length() != 0 && b != 0 && c != 0 && e != 0 && f != 0) {
			// SAQUE CODIGO Y PEDIDO DE LA QUERY
			this.jdbcTemplate.update(
					"UPDATE pedidos SET Entrega=?, Cliente=?, Obra=?, Descripcion=?, Tipo=?, Totalkg=?, Cuatrocomados=?, Seis=?, Ocho=?, Diez=?, Doce=?, Dieciseis=?, Veinte=?, Veinticinco=?, Treintaydos=?, OC=?, Elementos=? WHERE ID = ?;",
					entrega, cliente, obra, descripcion, tipo, totalkg, cuatrocomados, seis, ocho, diez, doce,
					dieciseis, veinte, veinticinco, treintaydos, oc, elementos, idPedido);
			if (x == 1) {
				return "redirect:" + "/motivos/" + idPedido;
			} else {
				return "redirect:" + "/etiquetado/pedidos";
			}
		} else {
			if (entrega.length() == 0) {
				template.addAttribute("fechaValida", false);
				return "resultados";
			}
			template.addAttribute("campoVacio", true);
			return "resultados";
		}
	}
	// FIN DE MODIFICAR PEDIDO PROGRAMACION

	// // PERMISOS
	// @RequestMapping(value = "/otorgarPermisos", method = RequestMethod.GET)
	// public String permisosGet(Model template) {
	//
	// // TRAE EL NOMBRE Y EL APELLIDO DE LOS USUARIOS
	// SqlRowSet usuarios;
	// usuarios = jdbcTemplate.queryForRowSet("SELECT Nombre, Apellido FROM
	// usuarios");
	// ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
	// while (usuarios.next()) {
	// String nombre = usuarios.getString("Nombre");
	// String apellido = usuarios.getString("Apellido");
	// Usuario usu = new Usuario(nombre, apellido);
	// listaUsuarios.add(usu);
	// }
	// template.addAttribute("lisUsuarios", listaUsuarios);
	//
	// return "darPermisosGet";
	// }
	//
	// // POST
	// @RequestMapping(value = "/otorgarPermisos", method = RequestMethod.POST)
	// public String permisosPost(@RequestParam(value = "usuario") String
	// usuario,
	// @RequestParam(value = "tipoUsuario") String tipoUsuario, Model template)
	// {
	// // LA VARIABLE "usuario" VIENE CON EL NOMBRE, " " Y EL APELLIDO. EJ:
	// // "ABEL GUILLEN".
	// // SE PUDRE LA MOMIA CUANDO SE INGRESA UN NOMBRE, OTRO NOMBRE Y EL
	// // APELLIDO
	// // POR EL ESPACIO ENTRE NOMBRE Y NOMBRE
	//
	// String nombre = "";
	// String apellido = "";
	// String encontroEspacio = "false";
	//
	// // Hago el for para separar de la variable usuario, el nombre y el
	// // apellido
	// for (int n = 0; n < usuario.length(); n++) {
	// char c = usuario.charAt(n);
	// if (c != ' ' && encontroEspacio == "false") {
	// nombre += c;
	// } else if (c == ' ') {
	// encontroEspacio = "true";
	// } else if (encontroEspacio == "true") {
	// apellido += c;
	// }
	// }
	//
	// // TRAE EL NOMBRE, APELLIDO Y CORREO DE LOS USUARIOS
	// SqlRowSet usuarios;
	// usuarios = jdbcTemplate.queryForRowSet(
	// "SELECT Nombre, Apellido, Correo FROM usuarios WHERE Nombre = ? and
	// Apellido = ?;", nombre, apellido);
	// ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
	// while (usuarios.next()) {
	// String nom = usuarios.getString("Nombre");
	// String ape = usuarios.getString("Apellido");
	// String correo = usuarios.getString("Correo");
	// Usuario usu = new Usuario(nom, ape, correo);
	// listaUsuarios.add(usu);
	// }
	//
	// String correo = listaUsuarios.get(0).getCorreo();
	//
	// // VERIFICA SI EL PERMISO QUE SE QUIERE OTORGAR YA EXISTE
	// // TRAE TODOS LOS DATOS DE LA TABLA TIPOUSUARIO
	// SqlRowSet tipoUsu;
	// tipoUsu = jdbcTemplate.queryForRowSet("SELECT * FROM tiposusuarios WHERE
	// CORREO_Usuario = ?;", correo);
	// ArrayList<TipoUsuario> listaTipoUsu = new ArrayList<TipoUsuario>();
	// while (tipoUsu.next()) {
	// double id = tipoUsu.getDouble("ID");
	// String tipo = tipoUsu.getString("Tipo");
	// String email = tipoUsu.getString("CORREO_Usuario");
	// TipoUsuario tUsu = new TipoUsuario(id, tipo, email);
	// listaTipoUsu.add(tUsu);
	// }
	//
	// boolean existeTUsu = false;
	// for (TipoUsuario tU : listaTipoUsu) {
	// String tipoU = tU.getTipo();
	// if (tipoU.equals(tipoUsuario)) {
	// existeTUsu = true;
	// }
	// }
	//
	// if (existeTUsu == false) {
	// this.jdbcTemplate.update("INSERT INTO tiposusuarios(Tipo, CORREO_Usuario)
	// VALUES (?,?);", tipoUsuario,
	// correo);
	// template.addAttribute("permiRepe", false);
	// return "resultados";
	// } else {
	// template.addAttribute("permiRepe", true);
	// return "resultados";
	// }
	// }

	// IMPRIMIR ITEMS
	@RequestMapping(value = "productos/items/imprimir/{idPedido}", method = RequestMethod.GET)
	public String imprimirItems(@PathVariable(value = "idPedido") String idPedido, Model template) {

		// TRAE A LOS PEDIDOS
		ArrayList<PedidoPSS> listaPedidos = traerPedidosPSSIP(Integer.parseInt(idPedido));

		// HACE QUE EL NRO DE PEDIDO SEA UN INT Y MUESTRE EL VALOR REAL (SI ES 1
		// QUE MUESTRE 1 Y NO 001)
		int pedido = Integer.parseInt(listaPedidos.get(0).getPedido());
		String pedidoS = Integer.toString(pedido);
		listaPedidos.get(0).setPedido(pedidoS);
		template.addAttribute("pedidos", listaPedidos);

		// TRAE A LOS ITEMS POR PEDIDO
		ArrayList<Item> items = traerItemsImprimir(Integer.parseInt(idPedido));

		// LES PONE UN "-" A LOS INPUTS VACIOS [a-h] Y UN "0" A H1 Y H2
		for (Item item : items) {
			int a = item.getA().length();
			int b = item.getB().length();
			int c = item.getC().length();
			int d = item.getD().length();
			int e = item.getE().length();
			int f = item.getF().length();
			int g = item.getG().length();
			int h = item.getH().length();
			int h1 = item.getH1().length();
			int h2 = item.getH2().length();

			if (h1 == 0) {
				String h1S = "0";
				item.setH1(h1S);
			}
			if (h2 == 0) {
				String h2S = "0";
				item.setH2(h2S);
			}
			if (a == 0) {
				String aS = "-";
				item.setA(aS);
			}
			if (b == 0) {
				String bS = "-";
				item.setB(bS);
			}
			if (c == 0) {
				String cS = "-";
				item.setC(cS);
			}
			if (d == 0) {
				String dS = "-";
				item.setD(dS);
			}
			if (e == 0) {
				String eS = "-";
				item.setE(eS);
			}
			if (f == 0) {
				String fS = "-";
				item.setF(fS);
			}
			if (g == 0) {
				String gS = "-";
				item.setG(gS);
			}
			if (h == 0) {
				String hS = "-";
				item.setH(hS);
			}
		}

		template.addAttribute("items", items);

		return "imprimirItems";
	}

	// IMPRIMIR PLANILLAS
	@RequestMapping(value = "imprimir/planilla/{idPedido}", method = RequestMethod.GET)
	public String imprimirPlanilla(@PathVariable(value = "idPedido") String idPedido, Model template) {
		// TRAE A LOS ITEMS
		ArrayList<Item> listaItems = traerItems(Integer.parseInt(idPedido));
		template.addAttribute("items", listaItems);
		// TRAE AL PEDIDO
		ArrayList<Pedido> pedido = traerPedidosIP(Integer.parseInt(idPedido));
		template.addAttribute("pedido", pedido);

		return "imprimirPlanilla";
	}

	// IMPRIMIR INSPECCION DE CARGA
	@RequestMapping(value = "imprimir/inspecciondecarga/{idPedido}", method = RequestMethod.GET)
	public String imprimirInspeccionDeCarga(@PathVariable(value = "idPedido") String idPedido, Model template) {
		ArrayList<Pedido> ped = traerPedidosIP(Integer.parseInt(idPedido));
		int[] cant = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, };
		template.addAttribute("pedido", ped);
		template.addAttribute("cantidad", cant);
		return "imprimirInspeccionDeCarga";
	}

	// IMPRIMIR RESUMEN DE PESO
	@RequestMapping(value = "imprimir/resumendepeso/{idPedido}", method = RequestMethod.GET)
	public String imprimirResumenDePeso(@PathVariable(value = "idPedido") String idPedido, Model template) {

		// TRAE EL PEDIDO
		ArrayList<Pedido> ped = traerPedidosIP(Integer.parseInt(idPedido));
		template.addAttribute("pedido", ped);

		// String pesoTotal = ped.get(0).getTotalKg();

		// TRAE A LOS ITEMS DEL PEDIDO
		ArrayList<Item> listaItem = traerItems(Integer.parseInt(idPedido));

		int contSeis = 0;
		int contOcho = 0;
		int contDiez = 0;
		int contDoce = 0;
		int contDieciseis = 0;
		int contVeinte = 0;
		int contVeinticinco = 0;
		int contTreintaydos = 0;

		for (Item i : listaItem) {
			String diam = i.getDiametro();
			int lTotal = Integer.parseInt(i.getlTotal());
			if (diam.equals("6"))
				contSeis += lTotal;
			if (diam.equals("8"))
				contOcho += lTotal;
			if (diam.equals("10"))
				contDiez += lTotal;
			if (diam.equals("12"))
				contDoce += lTotal;
			if (diam.equals("16"))
				contDieciseis += lTotal;
			if (diam.equals("20"))
				contVeinte += lTotal;
			if (diam.equals("25"))
				contVeinticinco += lTotal;
			if (diam.equals("32"))
				contTreintaydos += lTotal;
		}

		int largoAngulos = 0;
		for (Item i : listaItem) {
			String acero = i.getAcero();
			int lTotal = Integer.parseInt(i.getlTotal());
			if (acero.equals("ANGULOS") || acero.equals("CLAVOS") || acero.equals("ALAMBRES")) {
				largoAngulos += lTotal;
			}
		}

		double pesoAngulos = 0;
		for (Item i : listaItem) {
			String acero = i.getAcero();
			double peso = Double.parseDouble((i.getPeso()).replace(",", "."));
			if (acero.equals("ANGULOS") || acero.equals("CLAVOS") || acero.equals("ALAMBRES")) {
				pesoAngulos += peso;
			}
		}

		int largoTotal = contSeis + contOcho + contDiez + contDoce + contDieciseis + contVeinte + contVeinticinco
				+ contTreintaydos + largoAngulos;
		
		System.out.println("este pedidoooo" + ped.get(0).getOcho());
		template.addAttribute("largoAngulos", largoAngulos);
		template.addAttribute("pesoAngulos", pesoAngulos);
		template.addAttribute("contSeis", contSeis);
		template.addAttribute("contOcho", contOcho);
		template.addAttribute("contDiez", contDiez);
		template.addAttribute("contDoce", contDoce);
		template.addAttribute("contDieciseis", contDieciseis);
		template.addAttribute("contVeinte", contVeinte);
		template.addAttribute("contVeinticinco", contVeinticinco);
		template.addAttribute("contTreintaydos", contTreintaydos);
		template.addAttribute("largoTotal", largoTotal);

		return "imprimirResumenDePeso";
	}
		
	
	// DAME IMAGEN ; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "formato/{id}", method = RequestMethod.GET)
	public String imagenesDeFormatos(@PathVariable(value = "id") int idItem, Model template) {
		SqlRowSet formatosBD;
		formatosBD = jdbcTemplate.queryForRowSet("SELECT * FROM formatos WHERE Posicion = ?;", idItem);
		formatosBD.next();
		String formato = formatosBD.getString("Formato");
		template.addAttribute("formato", formato);
		return "formato";
	}
	// FIN DAME IMAGEN

	// DAME CANTIDAD DE LADOS ; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "lados/{id}", method = RequestMethod.GET)
	@ResponseBody
	//TODO ACÁ TRAE LADOS EN STRING, PERO ES LONG. 
	public Long cantidadDeLados(@PathVariable(value = "id") int id, Model template) {
		SqlRowSet formatosBD;
		formatosBD = jdbcTemplate.queryForRowSet("SELECT * FROM formatos WHERE POSICION = ?;", id);
		formatosBD.next();
		Long lados = formatosBD.getLong("Lados");
		return lados;
	}
	//PUNTO D DE LA MINUTA DEL 13 de enero de 2018
	//CREO UN NUEVO METODO QUE OBTIENE LA CANTIDAD DE LADOS, SE UTILIZA EN 
	//TODO
	public int getCant_Doblados(String posicion) {
		SqlRowSet formatosBD;
		formatosBD = jdbcTemplate.queryForRowSet("SELECT * FROM formatos WHERE POSICION = ?;", posicion);
		formatosBD.next();
		int cant_doblados = formatosBD.getInt("Cant_doblados");
				
		return cant_doblados;
	}

	// CODIGO REPETIDOS ; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "codigoRepetidoUsuarioAndroid/{codigo}", method = RequestMethod.GET)
	@ResponseBody
	public boolean codigoRepetidoUsuarioAndroid(@PathVariable(value = "codigo") String codigo, Model template) {
		SqlRowSet usuarioAndroidBD;
		usuarioAndroidBD = jdbcTemplate.queryForRowSet("SELECT * FROM usuariosandroid");
		boolean estaRepetido = false;
		while (usuarioAndroidBD.next()) {
			if (usuarioAndroidBD.getString("codigo").equals(codigo)) {
				estaRepetido = true;
			}
		}
		return estaRepetido;
	}

	// METODO
	// INVERTIR FECHA PARA MOSTRAR EN FORMATO INGLES
	public String invertirCadena(String cadena) {
		String fecha = new String();
		String dia = "";
		String mes = "";
		String anio = "";
		for (int i = 0; i < cadena.length(); i++) {
			if (i == 0 || i == 1) {
				dia += cadena.charAt(i);
			} else if (i == 2 || i == 3) {
				mes += cadena.charAt(i);
			} else if (i == 4 || i == 5 || i == 6 || i == 7) {
				anio += cadena.charAt(i);
			}
		}
		mes = "/" + mes + "/";
		fecha = anio + mes + dia;
		return fecha;
	}

	public String invertirCadenaMas(String cadena) {
		String fecha = new String();
		String dia = "";
		String mes = "";
		String anio = "";
		for (int i = 0; i < cadena.length(); i++) {
			if (i == 0 || i == 1) {
				dia += cadena.charAt(i);
			} else if (i == 2 || i == 3) {
				mes += cadena.charAt(i);
			} else if (i == 4 || i == 5) {
				anio += cadena.charAt(i);
			}
		}
		anio = "20" + anio;
		mes = "/" + mes + "/";
		fecha = anio + mes + dia;
		return fecha;
	}

	public String invertirCadenaMasAsteriscos(String cadena) {
		String fecha = new String();
		String dia = "";
		String mes = "";
		String anio = "";
		for (int i = 0; i < cadena.length(); i++) {
			if (i == 0 || i == 1 || i == 8 || i == 9 || i == 10) {
				dia += cadena.charAt(i);
			} else if (i == 2 || i == 3) {
				mes += cadena.charAt(i);
			} else if (i == 4 || i == 5 || i == 6 || i == 7) {
				anio += cadena.charAt(i);
			}
		}
		mes = "/" + mes + "/";
		fecha = anio + mes + dia;
		return fecha;
	}

	public boolean isThisDateValid(String dateToValidate, String dateFromat) {

		if (dateToValidate == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}

	public String agregar20(String cadena) {
		String fecha = new String();
		String dia = "";
		String mes = "";
		String anio = "";
		for (int i = 0; i < cadena.length(); i++) {
			if (i == 0 || i == 1) {
				dia += cadena.charAt(i);
			} else if (i == 2 || i == 3) {
				mes += cadena.charAt(i);
			} else if (i == 4 || i == 5) {
				anio += cadena.charAt(i);
			}
		}
		anio = "20" + anio;
		fecha = dia + mes + anio;
		return fecha;
	}

	public String invertirModificar(String cadena) {
		String fecha = new String();
		String dia = "";
		String mes = "";
		String anio = "";
		for (int i = 0; i < cadena.length(); i++) {
			if (i == 0 || i == 1 || i == 2 || i == 3) {
				anio += cadena.charAt(i);
			} else if (i == 4 || i == 5) {
				mes += cadena.charAt(i);
			} else if (i == 6 || i == 7) {
				dia += cadena.charAt(i);
			}
		}
		fecha = dia + mes + anio;
		return fecha;
	}

	public String invertirConAsteriscos(String cadena) {
		String fecha = new String();
		String dia = "";
		String mes = "";
		String anio = "";
		for (int i = 0; i < cadena.length(); i++) {
			if (i == 0 || i == 1 || i == 2 || i == 3) {
				anio += cadena.charAt(i);
			} else if (i == 4 || i == 5) {
				mes += cadena.charAt(i);
			} else if (i == 6 || i == 7) {
				dia += cadena.charAt(i);
			} else if (i == 8 || i == 9 || i == 10) {
				anio += cadena.charAt(i);
			}
		}
		fecha = dia + mes + anio;
		return fecha;
	}

	// Arma los datos a enviar al remito de lo que es hierro (no incluye angulos
	// y demas)
	public ArrayList<pedFormatoRemito> armarListaFormatoRemito(ArrayList<Pedido> listaPe1) {
		ArrayList<pedFormatoRemito> listaPedFormRem = new ArrayList<pedFormatoRemito>();

		for (Pedido p : listaPe1) {
			String codigoRemito = "";
			String descripcionRemito = "";
			String um = "KG";
			String total = "";

			String tipo = p.getTipo();
			String Seis = p.getSeis();
			String Ocho = p.getOcho();
			String Diez = p.getDiez();
			String Doce = p.getDoce();
			String Dieciseis = p.getDieciseis();
			String Veinte = p.getVeinte();
			String Veinticinco = p.getVeinticinco();
			String Treintaydos = p.getTreintaydos();
			String otros = p.getOtros();

			long id = p.getId();

			// ARMA LOS CODIGOS PARA ANGULOS
			if (otros != null) {
				if (!otros.equals("0")) {
					ArrayList<Item> listaItems = traerItemsCompleto((int) id);

					int cont1 = 0;
					int cont2 = 0;
					int cont3 = 0;
					int cont4 = 0;
					int cont5 = 0;
					int cont6 = 0;
					int cont7 = 0;

					for (Item i : listaItems) {
						String aceroItem = i.getAcero();
						String diametroItem = i.getDiametro();
						String peso = i.getPeso();

						// ANGULOS
						if (aceroItem.equals("ANGULOS")) {
							codigoRemito = "A";
							descripcionRemito = "Angulos Ø ";
							if (diametroItem.equals("2,71")) {
								codigoRemito += "271";
								descripcionRemito += "2,71";
								total = peso;
								if (cont1 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont1 += 1;
								} else {
									for (pedFormatoRemito p1 : listaPedFormRem) {
										String codRemito = p1.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p1.getTotal());
										if (codRemito.equals("A271")) {
											totalI += pesoI;
											p1.setTotal(Integer.toString(totalI));
										}
									}
								}
							} else if (diametroItem.equals("7,4")) {
								codigoRemito += "740";
								descripcionRemito += "7,40";
								total = peso;
								if (cont2 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont2 += 1;
								} else {
									for (pedFormatoRemito p2 : listaPedFormRem) {
										String codRemito = p2.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p2.getTotal());
										if (codRemito.equals("A740")) {
											totalI += pesoI;
											p2.setTotal(Integer.toString(totalI));
										}
									}
								}
							} else if (diametroItem.equals("9,02")) {
								codigoRemito += "902";
								descripcionRemito += "9,02";
								total = peso;
								if (cont3 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont3 += 1;
								} else {
									for (pedFormatoRemito p3 : listaPedFormRem) {
										String codRemito = p3.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p3.getTotal());
										if (codRemito.equals("A902")) {
											totalI += pesoI;
											p3.setTotal(Integer.toString(totalI));
										}
									}
								}
							} else if (diametroItem.equals("10,71")) {
								codigoRemito += "1071";
								descripcionRemito += "10,71";
								total = peso;
								if (cont4 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont4 += 1;
								} else {
									for (pedFormatoRemito p4 : listaPedFormRem) {
										String codRemito = p4.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p4.getTotal());
										if (codRemito.equals("A1071")) {
											totalI += pesoI;
											p4.setTotal(Integer.toString(totalI));
										}
									}
								}
							}
						}

						// CLAVOS
						else if (aceroItem.equals("CLAVOS")) {
							codigoRemito = "C";
							descripcionRemito = "Clavos Ø ";
							if (diametroItem.equals("2,5")) {
								codigoRemito += "25";
								descripcionRemito += "2,5";
								total = peso;
								if (cont5 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont5 += 1;
								} else {
									for (pedFormatoRemito p1 : listaPedFormRem) {
										String codRemito = p1.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p1.getTotal());
										if (codRemito.equals("C25")) {
											totalI += pesoI;
											p1.setTotal(Integer.toString(totalI));
										}
									}
								}
							} else if (diametroItem.equals("2")) {
								codigoRemito += "20";
								descripcionRemito += "2,0";
								total = peso;
								if (cont6 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont6 += 1;
								} else {
									for (pedFormatoRemito p2 : listaPedFormRem) {
										String codRemito = p2.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p2.getTotal());
										if (codRemito.equals("C20")) {
											totalI += pesoI;
											p2.setTotal(Integer.toString(totalI));
										}
									}
								}
							}
						}
						// ALAMBRE
						else if (aceroItem.equals("ALAMBRES")) {
							codigoRemito = "AL";
							descripcionRemito = "Alambre Ø ";
							if (diametroItem.equals("1")) {
								codigoRemito += "10";
								descripcionRemito += "1";
								total = peso;
								if (cont7 == 0) {
									pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um,
											total);
									listaPedFormRem.add(pedRem);
									cont7 += 1;
								} else {
									for (pedFormatoRemito p1 : listaPedFormRem) {
										String codRemito = p1.getCodigo();
										int pesoI = Integer.parseInt(peso);
										int totalI = Integer.parseInt(p1.getTotal());
										if (codRemito.equals("A10")) {
											totalI += pesoI;
											p1.setTotal(Integer.toString(totalI));
										}
									}
								}
							}
						}
					}
				}
			}

			if (tipo.equals("Barras")) {
				codigoRemito = "BAR";
				descripcionRemito = "Barras Ø ";
			} else if (tipo.equals("CyD")) {
				codigoRemito = "CYD";
				descripcionRemito = "Cortado y Doblado Ø ";
			} else if (tipo.equals("Armado")) {
				codigoRemito = "ARM";
				descripcionRemito = "Armado Ø ";
			} else if (tipo.equals("Pilote")) {
				codigoRemito = "PIL";
				descripcionRemito = "Pilote Ø ";
			}

			if (!Seis.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "06";
				descripcionRemito += "6 mm";
				total = Seis;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Ocho.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "08";
				descripcionRemito += "8 mm";
				total = Ocho;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Diez.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "10";
				descripcionRemito += "10 mm";
				total = Diez;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Doce.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "12";
				descripcionRemito += "12 mm";
				total = Doce;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Dieciseis.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "16";
				descripcionRemito += "16 mm";
				total = Dieciseis;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Veinte.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "20";
				descripcionRemito += "20 mm";
				total = Veinte;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Veinticinco.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "25";
				descripcionRemito += "25 mm";
				total = Veinticinco;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
			if (!Treintaydos.equals("0")) {
				String descrRemAntes = descripcionRemito;
				String abrev = codigoRemito;
				codigoRemito += "32";
				descripcionRemito += "32 mm";
				total = Treintaydos;
				pedFormatoRemito pedRem = new pedFormatoRemito(codigoRemito, descripcionRemito, um, total);
				listaPedFormRem.add(pedRem);
				codigoRemito = abrev;
				descripcionRemito = descrRemAntes;
			}
		}

		return listaPedFormRem;
	}

	// FUNCION QUE CALCULA EL LARGO TOTAL ; SE ACCEDE SOLO POR AJAX
	@RequestMapping(value = "item/lTotal/{cantidad}/{a}/{b}/{c}/{d}/{e}/{f}/{g}", method = RequestMethod.GET)
	@ResponseBody
	public String calculaLargoTotal(@PathVariable(value = "cantidad") String cantidad,
			@PathVariable(value = "a") String a, @PathVariable(value = "b") String b,
			@PathVariable(value = "c") String c, @PathVariable(value = "d") String d,
			@PathVariable(value = "e") String e, @PathVariable(value = "f") String f,
			@PathVariable(value = "g") String g, Model template) {
		// OBTENGO LARGO PARCIAL
		// LParcial = a + b+ c + d + e + f + g

		ArrayList<String> listaMedidas = new ArrayList<String>();
		ArrayList<Double> listaMedidasD = new ArrayList<Double>();
		listaMedidas.add(a);
		listaMedidas.add(b);
		listaMedidas.add(c);
		listaMedidas.add(d);
		listaMedidas.add(e);
		listaMedidas.add(f);
		listaMedidas.add(g);

		// PASA DE STRING A DOUBLE PARA HACER LA SUMATORIA
		for (String i : listaMedidas) {
			int cantDig = i.length();
			if (cantDig != 0) {
				double med = Double.parseDouble(i);
				listaMedidasD.add(med);
			} else {
				double medCero = 0;
				listaMedidasD.add(medCero);
			}
		}

		// HACE LA SUMATORIA
		double lParciald = 0;
		for (Double d1 : listaMedidasD) {
			lParciald += d1;
		}

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		/*
		 * int lParcialEntero = redondearDouble(lParciald); String lParcial =
		 * Integer.toString(lParcialEntero);
		 */

		// OBTENGO LARGO TOTAL
		// LTotal = cantidad * LParcial
		// double cantd = Double.parseDouble(cantidad);
		double lTotald = lParciald;

		// MEDIANTE LA FUNCION redondearDouble REDONDEO EL NRO PARA ARRIBA
		int lTotalEntero = redondearDouble(lTotald);
		String lTotal = Integer.toString(lTotalEntero);

		return lTotal;
	}
	
	
	//Método para ver items en hoja A4 
	// GET
	@RequestMapping(value = "/productos/items/verItems/{idPedido}", method = RequestMethod.GET)
	public String verItemsPedidosEtiquetado(@PathVariable(value = "idPedido") String idPedido,Model template) {
		
		// TRAE A LOS PEDIDOS
		ArrayList<Item> listaItems = traerItemsImprimir(Integer.parseInt(idPedido));
		ArrayList<Pedido> pedidos = traerPedidosIP(Integer.parseInt(idPedido));
		Pedido pedido = pedidos.get(0);
		if (listaItems.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		for(Item i : listaItems) {
			i.setCodigo(i.getCodigo().substring(i.getCodigo().length() - 3));
		}
		template.addAttribute("pedido",pedido);
		template.addAttribute("listaItems", listaItems);
		return "imprimirCantidadItems";
	}
	// POST
	@RequestMapping(value = "/productos/items/verItems/{id}", method = RequestMethod.POST)
	public String verItemsPedidosEtiquetadoPost(@PathVariable(value = "idPedido") String idPedido,@RequestParam(value = "desde") String desde,
			@RequestParam(value = "hasta") String hasta, Model template) {
		if (hasta.length() == 0 || desde.length() == 0) {
			ArrayList<Item> listaItems = traerItemsImprimir(Integer.parseInt(idPedido));
			template.addAttribute("listaItems", listaItems);
			return "mostrarPedidosE";
		}
		ArrayList<Pedido> pedidos = traerPedidosIP(Integer.parseInt(idPedido));
		Pedido pedido = pedidos.get(0);
		// TRAE A LOS PEDIDOS
		ArrayList<Pedido> listaPedidos = traerPedidosDesdeHasta(desde, hasta);
		if (listaPedidos.size() == 0) {
			template.addAttribute("hayPedidos", false);
			return "resultados";
		}
		template.addAttribute("pedido",pedido);
		template.addAttribute("pedidos", listaPedidos);
		return "imprimirCantidadItems";
	}
	// FIN DE MOSTRAR PEDIDOS (ETIQUETADO)
	
	
	//INVENTARIO
	@RequestMapping(value = "/inventario", method = RequestMethod.GET)
	public String inventario(Model template) {

		return "inventario";
	}
	
	//FUNCION QUE TRAE INGRESOS MP_TEMP 
	private ArrayList<IngresoMP_temp> traerIngresosmp_temp() {
		SqlRowSet traeStock;
		traeStock = jdbcTemplate.queryForRowSet("SELECT * FROM ingresomp_temp");
		ArrayList<IngresoMP_temp> listaIngresos = new ArrayList<IngresoMP_temp>();
		while (traeStock.next()) {
			int id = traeStock.getInt("ID");
			String fecha = traeStock.getString("Fecha");
			String material = traeStock.getString("Material");
			String descripcion = traeStock.getString("Descripcion");
			String kgInicial = traeStock.getString("KGInicial");
			String umb = traeStock.getString("UMB");
			String lote = traeStock.getString("Lote"); 
			String destinatario = traeStock.getString("Destinatario"); 
			String colada = traeStock.getString("Colada");
			String pesoPorBalanza = traeStock.getString("PesoPorBalanza");
			String kgEnPlanta = traeStock.getString("KGEnPlanta"); 
			IngresoMP_temp mp = new IngresoMP_temp(id,fecha,material,descripcion,kgInicial,umb,lote,destinatario,colada,pesoPorBalanza,kgEnPlanta);
			listaIngresos .add(mp);
		}
		return listaIngresos ;
	}
	
	//MODIFICAR INVENTARIO 
	@RequestMapping(value = "/modificarInventario", method = RequestMethod.GET)
	public String modificarInventario(Model template) {

		ArrayList<IngresoMP_temp> listaIngresos = traerIngresosmp_temp();
		template.addAttribute("listaIngresos", listaIngresos);
		return "modificarInventario";
	}
	
	//MODIFICAR INGRESO MP_TEMP
	
	@RequestMapping("/modificarInventario/editar/{id}")
	public String modificarIngreso(@PathVariable(value = "id") int idPedido, Model template){
		
		// TRAE LOS CODIGOS
		SqlRowSet rowCodigosMP;
		rowCodigosMP = jdbcTemplate.queryForRowSet("SELECT * FROM ingresomp_temp WHERE ID = ?;", idPedido);
		rowCodigosMP.next();
		
		String material = rowCodigosMP.getString("Material");
		String descripcion = rowCodigosMP.getString("Descripcion");
		String kgInicial = rowCodigosMP.getString("KGInicial");
		String umb = rowCodigosMP.getString("UMB");
		String lote = rowCodigosMP.getString("Lote");
		String destinatario = rowCodigosMP.getString("Destinatario");
		String colada = rowCodigosMP.getString("Colada");
		String pesoPorBalanza = rowCodigosMP.getString("PesoPorBalanza");
		String kgEnPlanta = rowCodigosMP.getString("KGEnPlanta");
		
		template.addAttribute("material", material);
		template.addAttribute("descripcion", descripcion);
		template.addAttribute("kgInicial", kgInicial);
		template.addAttribute("umb", umb);
		template.addAttribute("lote", lote);
		template.addAttribute("destinatario", destinatario);
		template.addAttribute("colada", colada);
		template.addAttribute("pesoPorBalanza", pesoPorBalanza);
		template.addAttribute("kgEnPlanta", kgEnPlanta);

		
		return "ModificarPedidoMP_temp";
	}
	
	
	//MODIFICAR INGRESO MP_TEMP 
	//POST
	
	// POST
	@RequestMapping(value = "/modificarInventario/editar/{id}", method = RequestMethod.POST)
	public String editarIngreso(@PathVariable(value = "id") int id,
			@RequestParam (value = "kgEnPlanta") String kgEnPlanta,Model template) {
		try {
			Integer.parseInt(kgEnPlanta);
		}catch(Exception e) {
			template.addAttribute("kgEnPlanta", false);
			return "resultados";
		}
		if (kgEnPlanta.length() != 0) {
			this.jdbcTemplate.update("UPDATE ingresomp_temp SET KGEnPlanta = ? WHERE ID = ?;",kgEnPlanta,id);
			return "redirect:" + "/modificarInventario";
		} else {

			template.addAttribute("camposObligatorios", false);
			return "resultados";
		}
	}
	
	
	//ELIMINAR INGRESO MP_TEMP
	
	@RequestMapping("/modificarInventario/eliminar/{id}")
	public String eliminarIngreso(@PathVariable(value = "id") int idPedido, Model template) {
		jdbcTemplate.update("DELETE FROM ingresomp_temp WHERE id = ?;", idPedido);

		return "redirect:" + "/modificarInventario";

	}
	
	
	
	//AJUSTAR INVENTARIO
	@RequestMapping(value = "/ajustarInventario", method = RequestMethod.GET)
	public String ajustarInventario(Model template) throws SQLException {
			ArrayList<IngresoMP_temp> listaIngresosMP_temp= new ArrayList<>();
			listaIngresosMP_temp = traerIngresosmp_temp();
			for(IngresoMP_temp ingresos : listaIngresosMP_temp ) {
				String material = ingresos.getMaterial().replace(" ","");
				@SuppressWarnings("unused")
				String descripcion = ingresos.getDescripcion();
				String kgInicial = ingresos.getKgInicial().replace(" ","");
				@SuppressWarnings("unused")
				String umb = ingresos.getUmb();
				String lote = ingresos.getLote().replace(" ","");
				@SuppressWarnings("unused")
				String destinatario = ingresos.getDestinatario();
				@SuppressWarnings("unused")
				String colada = ingresos.getColada();
				@SuppressWarnings("unused")
				String pesoPorBalanza = ingresos.getPesoPorBalanza();
				String kgEnPlanta = ingresos.getKgEnPlanta().replace(" ","");
				ajustarIngresoMP(kgEnPlanta,lote,material,kgInicial);
				backupearIngresoMP(ingresos);
				finalizarAjuste();
				limpiarIngresosMP_temp();

			}
			actualizarKgs();
		return "home";
	}
	
	//MERMA 
	@RequestMapping(value = "/merma", method = RequestMethod.GET)
	public String merma(Model template) {

		return "merma";
	}
	
	//Funcion que hace backap de los ingresos modificados.
	
	private void backupearIngresoMP(IngresoMP_temp ingresoTemp) throws java.sql.SQLException{
		ArrayList<IngresoMP> ingresosMP = traerIngresosMP();
		@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
		ArrayList<IngresoMP> ingresosMPABackUp = new ArrayList();
		String loteTemp = ingresoTemp.getLote().replace(" ","");
		String materialTemp = ingresoTemp.getMaterial().replace(" ","");
		String kgInicialTemp = ingresoTemp.getKgInicial().replace(" ","");
		
		for(IngresoMP ingreso: ingresosMP) {
			if(ingreso.getLote().equals(loteTemp) && ingreso.getMaterial().equals(materialTemp) && ingreso.getCantidad().equals(kgInicialTemp)) {
				System.out.println("SIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
				try {
					this.jdbcTemplate.update("INSERT INTO INGRESOMP_BKP VALUES (?,?,?,?,?,?,?,?,?,?,?,default,?,?,?)",ingreso.getId(),ingreso.getFecha(),ingreso.getReferencia(),ingreso.getMaterial(),ingreso.getDescripcion(),ingreso.getCantidad(),ingreso.getUmb(),ingreso.getLote(),ingreso.getDestinatario(),ingreso.getColada(),ingreso.getColada(),"","","");
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
			}
				else {
				System.out.println(ingreso.getLote() + " = " + loteTemp);
				System.out.println(ingreso.getMaterial() + " = " + materialTemp);
				System.out.println(ingreso.getCantidad() + " = " + kgInicialTemp);
			}
		}
		
		
	}
	
	private boolean validarAjuste() {
		SqlRowSet ajuste;
		ajuste = jdbcTemplate.queryForRowSet("SELECT * FROM ajuste_inventario;");
		ajuste.next();
		boolean validacion = ajuste.getBoolean("ajuste");
		return validacion;
	}
	
	private void finalizarAjuste() {
		this.jdbcTemplate.update("UPDATE AJUSTE_INVENTARIO SET AJUSTE = 0");
	}
	private void ajustarIngresoMP(String kgEnPlanta,String lote,String material,String kgInicial) {
		this.jdbcTemplate.update("update ingresomp set KGDisponible = ? where lote=? and material =? and cantidad=?;",kgEnPlanta,lote,material,kgInicial);
	}

	private void limpiarIngresosMP_temp() {
		this.jdbcTemplate.update("TRUNCATE TABLE INGRESOMP_TEMP");

	}
	
	private void setearKgDisponibleStock(String material) {
		SqlRowSet ajuste;
		ajuste = jdbcTemplate.queryForRowSet("SELECT SUM(kgdisponible) as SUMA FROM ingresomp WHERE material=?;",material);
		ajuste.next();
		Double suma = ajuste.getDouble("SUMA");
		this.jdbcTemplate.update("UPDATE STOCK SET kgteorico = ? where codMat = ? ;" ,suma,material);
		System.out.println(ajuste.getDouble("SUMA"));
	}
	
	private void setearKgTeoricoStock(String material) {
		SqlRowSet ajuste;
		ajuste = jdbcTemplate.queryForRowSet("SELECT SUM(kgteorico) as SUMA FROM ingresomp WHERE material=?;",material);
		ajuste.next();
		Double suma = ajuste.getDouble("SUMA");
		this.jdbcTemplate.update("UPDATE STOCK SET kgteorico = ? where codMat = ? ;" ,suma,material);
		System.out.println(ajuste.getDouble("SUMA"));
		
	}
	
	private void setearKgProducidoStock(String material) {
		SqlRowSet ajuste;
		ajuste = jdbcTemplate.queryForRowSet("SELECT SUM(kgprod) as SUMA FROM ingresomp WHERE material=?;",material);
		ajuste.next();
		Double suma = ajuste.getDouble("SUMA");
		this.jdbcTemplate.update("UPDATE STOCK SET kgprod = ? where codMat = ? ;" ,suma,material);
		System.out.println(ajuste.getDouble("SUMA"));
	}
	
	private void actualizarKgs() {
		ArrayList<Stock> stock = traerStock();
		for(Stock s : stock) {
			String material = s.getCodMat();
			setearKgDisponibleStock(material);
			setearKgTeoricoStock(material);
			setearKgProducidoStock(material);
		}

	}
	
	private double setearUnDecimal(double numero) {

		double response = Math.round(numero* 10d) / 10d;
		return response;
	}
	

}