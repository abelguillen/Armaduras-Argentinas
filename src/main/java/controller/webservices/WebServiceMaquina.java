package controller.webservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.DAOMaquinaImpl;
import model.Item;
import model.Maquina;

@RestController
public class WebServiceMaquina {

	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private DAOMaquinaImpl maquinaImpl;
	
	@Autowired
	public WebServiceMaquina(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		maquinaImpl = new DAOMaquinaImpl(jdbcTemplate);
	}
	
	@RequestMapping(value = "/maquina/buscar/{id}", method = RequestMethod.POST)
	public Maquina buscarMaquinaPorID(@PathVariable(value = "id") long id) {
		return maquinaImpl.consultar(id);
	}
	
	@RequestMapping(value = "/maquina/estribadoras")
	public @ResponseBody List<Maquina> getEstribadoras() {	 
		return maquinaImpl.consultarEstribadoras();
	}
	@RequestMapping(value = "/maquina/cortadoras")
	public @ResponseBody List<Maquina> getCortadoras() {	
		return maquinaImpl.consultarCortadoras();
	}
	
	@RequestMapping(value = "/maquina/dobladoras")
	public @ResponseBody List<Maquina> getDobladoras() {	
		return maquinaImpl.consultarDobladoras();
	}
	
	
	
	
}