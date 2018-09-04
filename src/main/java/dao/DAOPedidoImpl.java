package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import model.Pedido;

public class DAOPedidoImpl implements DAOPedido{

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DAOPedidoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Pedido buscarPorID(long idPedido) {
		SqlRowSet pedidosDB;
		pedidosDB = jdbcTemplate.queryForRowSet("SELECT * FROM pedidos WHERE ID = ? limit 1;",
				idPedido);
		Pedido p = null;
		while (pedidosDB.next()) {
			long id = pedidosDB.getInt("ID");
			String entrega = pedidosDB.getString("Entrega");
			String cliente = pedidosDB.getString("Cliente");
			String obra = pedidosDB.getString("Obra");
			String codigo = pedidosDB.getString("Codigo");
			String descripcion = pedidosDB.getString("Descripcion");
			String tipo = pedidosDB.getString("Tipo");
			String totalKG = pedidosDB.getString("TotalKG");
			String cuatrocomados = pedidosDB.getString("Cuatrocomados");
			String seis = pedidosDB.getString("Seis");
			String ocho = pedidosDB.getString("Ocho");
			String diez = pedidosDB.getString("Diez");
			String doce = pedidosDB.getString("Doce");
			String dieciseis = pedidosDB.getString("Dieciseis");
			String veinte = pedidosDB.getString("Veinte");
			String veinticinco = pedidosDB.getString("Veinticinco");
			String treintaydos = pedidosDB.getString("Treintaydos");
			String otros = pedidosDB.getString("Otros");
			String estado = pedidosDB.getString("Estado");
			String pedido = pedidosDB.getString("Pedido");
			String oc = pedidosDB.getString("OC");
			String elementos = pedidosDB.getString("Elementos");

			p = new Pedido(id, entrega, codigo, cliente, obra, descripcion, tipo, totalKG,
					cuatrocomados, seis, ocho, diez, doce, dieciseis, veinte, veinticinco,
					treintaydos, otros, estado, oc, pedido, elementos);
			System.out.println(p.toString());
		}
		return p;
	}
}
