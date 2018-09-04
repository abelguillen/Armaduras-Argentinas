package controller.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.DAOIngresoMP;
import dao.DAOIngresoMPImpl;
import dao.DAOMaquinaImpl;
import model.IngresoMP;
import model.Maquina;

@RestController
@RequestMapping("/ingresomp")

public class WebServiceIngresoMp {

	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private DAOIngresoMP ingresoMPImpl;
	
	@Autowired
	public WebServiceIngresoMp(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		ingresoMPImpl = new DAOIngresoMPImpl(jdbcTemplate);
	}
	
	@RequestMapping(value = "/buscar/{codigoDeBarras}", method = RequestMethod.POST)
	public IngresoMP buscarIngresoMP(@PathVariable(value = "codigoDeBarras") String codigoDeBarras) {
		String lote = codigoDeBarras.substring(0,10);
		String material = codigoDeBarras.substring(10,20);
		String peso = codigoDeBarras.substring(20);
		System.out.println(lote + material + peso);
		return ingresoMPImpl.getIngreso(lote,material,peso);
	}
	@RequestMapping(value = "/esBarra/{codigoDeBarras}", method = RequestMethod.GET)
	public boolean esBarra(@PathVariable(value = "codigoDeBarras") String codigoDeBarras) {
		String lote = codigoDeBarras.substring(0,10);
		String material = codigoDeBarras.substring(10,20);
		String peso = codigoDeBarras.substring(20);
		System.out.println(lote + material + peso);
		return ingresoMPImpl.esBarra(lote,material,peso);
	}
	
	@RequestMapping(value = "/esRollo/{codigoDeBarras}", method = RequestMethod.GET)
	public boolean esRollo(@PathVariable(value = "codigoDeBarras") String codigoDeBarras) {
		String lote = codigoDeBarras.substring(0,10);
		String material = codigoDeBarras.substring(10,20);
		String peso = codigoDeBarras.substring(20);
		System.out.println(lote + material + peso);
		return ingresoMPImpl.esRollo(lote,material,peso);
	}
	
	@RequestMapping(value = "updatekg/{codigoDeBarras}", method = RequestMethod.GET)
	public boolean update(@PathVariable(value = "codigoDeBarras") String codigoDeBarras,@PathVariable(value = "codigoDeBarras") String codigo) {
		String lote = codigoDeBarras.substring(0,10);
		String material = codigoDeBarras.substring(10,20);
		String peso = codigoDeBarras.substring(20);
		String pesoNuevo = codigoDeBarras.substring(20);
		System.out.println(lote + material + peso);
		return ingresoMPImpl.updateIngreso(lote,material,peso,pesoNuevo);
	}
	

}

