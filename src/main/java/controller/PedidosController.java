package controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.DAOPedidoImpl;
import model.Item;
import model.Pedido;

@Controller
@RequestMapping("/pedidos")
public class PedidosController {
	@SuppressWarnings("unused")
	private final JdbcTemplate jdbcTemplate;
	private final DAOPedidoImpl DAO_PEDIDO;
	IndexController indexController;

	@Autowired
	public PedidosController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.DAO_PEDIDO = new DAOPedidoImpl(jdbcTemplate);
		indexController  = new IndexController(jdbcTemplate);
	}

	@RequestMapping(value = "/imprimir/caratula/{idPedido}", method = RequestMethod.GET)
	public String maquina(@PathVariable(value = "idPedido") long idPedido, Model template) {
		Pedido pedido = DAO_PEDIDO.buscarPorID(idPedido);
		if (Integer.parseInt(pedido.getElementos()) >= 1) {
			template.addAttribute("pedido", pedido);
			return "imprimirCaratula";
		} else {
			// TRAE A LOS ITEMS
			int nuevo = (int)idPedido;
			ArrayList<Item> listaItems = indexController.traerItems(nuevo);
			template.addAttribute("items", listaItems);
			template.addAttribute("tieneElementos", true);
			return "mostrarItems";
		}
	}
}
