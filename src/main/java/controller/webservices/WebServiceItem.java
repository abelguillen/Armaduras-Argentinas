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

import dao.DAOItemImpl;
import dao.DAOMaquinaImpl;
import model.Item;
import model.Maquina;

@RestController
@RequestMapping("/items")
public class WebServiceItem {

	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private DAOItemImpl itemImpl;

	@Autowired
	public WebServiceItem(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		itemImpl = new DAOItemImpl(jdbcTemplate);
	}

	@RequestMapping(value = "/json/{item}")
	public @ResponseBody List<Item> retornarCodigo(@PathVariable(value = "item") String item) {
		ArrayList<Item> items = new ArrayList<Item>();
		if ("conejo".equals(item)) {
			items.add(new Item(0L, 1L));
			items.add(new Item(1L, 4L));
			return items;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/consultarTodos")
	public @ResponseBody List<Item> traerTodos() {
		return itemImpl.consultar();
	}

	@RequestMapping(value = "/{item}")
	public @ResponseBody Item getItem(@PathVariable(value = "item") int item) {
		return itemImpl.getItem(item);
	}

	@RequestMapping(value = "/codItem/{codItem}")
	public @ResponseBody Item getItem4Cod(@PathVariable(value = "codItem") String codItem) {
		return itemImpl.getItem4Cod(codItem);
	}

}