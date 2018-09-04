package dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import model.Item;

public class DAOItemImpl implements DAOItem{

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DAOItemImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	


	@Override
	public void baja(long id) {
		jdbcTemplate.update("DELETE FROM maquina WHERE id = ?;", id);
	}


	@Override
	public void alta(Item item) {
		this.jdbcTemplate.update(
				"INSERT INTO item(clasificacion, marca, modelo, tipo_mp, diametro_minimo,"
				+ "diametro_maximo, merma) VALUES (?,?,?,?,?,?,?)");		
	}

	@Override
	public void modificar(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Item> consultar() {

		SqlRowSet itemsBD= jdbcTemplate.queryForRowSet("SELECT * FROM items;");
		ArrayList<Item> listaDeItems= new ArrayList<Item>();
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
			listaDeItems.add(i);
			System.out.println(i.getItem());
		}
		return listaDeItems;
	}

	@Override
	public Item consultar(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Item getItem(int idItem) {
		SqlRowSet itemsBD;
		itemsBD = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE ID = ? limit 1;", idItem);
		Item i = null;
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
			 i = new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, formato, dibujo, a, b,
					c, d, e, f, g, h, h1, h2, lParcial, lTotal, lCortar, peso, observaciones, codigo, estructura);
		}
		return i;
	}
	
	

	public Item getItem4Cod(String codigoItem) {
		SqlRowSet itemsBD;
		itemsBD = jdbcTemplate.queryForRowSet("SELECT * FROM items WHERE CODIGO = ? limit 1;", codigoItem);
		Item i = null;
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
			 i = new Item(id, idpedido, item, posicion, acero, material, diametro, cantidad, formato, dibujo, a, b,
					c, d, e, f, g, h, h1, h2, lParcial, lTotal, lCortar, peso, observaciones, codigo, estructura);
		}
		return i;
	}
	
	
}
