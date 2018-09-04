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

import dao.DAODeclaracionImpl;
import dao.DAOItemImpl;
import model.Declaracion;
import model.Item;

@RestController
@RequestMapping("/declaracion")
public class WebServiceDeclaracion {
	
	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private DAODeclaracionImpl declaracionImpl;

	@Autowired
	public WebServiceDeclaracion(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		declaracionImpl = new DAODeclaracionImpl(jdbcTemplate);
	}

	@RequestMapping(value = "/{codItem}", method = RequestMethod.POST)
	public @ResponseBody boolean existe(@PathVariable(value = "codItem") String codItem) {	
		return declaracionImpl.existeItem(codItem);
	}
	
	@RequestMapping(value = "/crear/{declaracion}" ,  method = RequestMethod.POST)
	public @ResponseBody void crear(@PathVariable(value = "declaracion") Declaracion declaracion) {
		declaracionImpl.insertDeclaracion(declaracion);
	}
}
