package dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import model.IngresoMP;

public class DAOIngresoMPImpl implements DAOIngresoMP{
private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DAOIngresoMPImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public boolean updateIngreso(String loteObtenido, String materialObtenido, String pesoObtenido, String nuevoPeso) {
		Double nuevoPesoNumerico = Double.parseDouble(nuevoPeso);
		String kdProd = nuevoPeso;
		String kgDisp ;
		IngresoMP ingreso = getIngreso(loteObtenido,materialObtenido,pesoObtenido);
		if(ingreso.getKgDisponible() == null) {
			kgDisp = String.valueOf(Double.parseDouble(ingreso.getCantidad()) - nuevoPesoNumerico);
		}
		else {
			kgDisp = String.valueOf(Double.parseDouble(ingreso.getKgDisponible()) - nuevoPesoNumerico);
		}
		this.jdbcTemplate.update("update ingresomp set KGProd = '" + kdProd +"', KGDisponible = '" + kgDisp+"' where lote ='" + loteObtenido + "' AND material = '" + materialObtenido + "' And cantidad ='" + pesoObtenido + "';");
		return true;
	}
	@Override
	public IngresoMP getIngreso(String loteObtenido, String materialObtenido, String pesoObtenido) {

		SqlRowSet rowIngresoMP;
		rowIngresoMP = jdbcTemplate.queryForRowSet("SELECT * FROM ingresomp where lote = '" + loteObtenido + "' AND material = '"
                + materialObtenido + "' AND cantidad = '" + pesoObtenido + "' limit 1;");
		IngresoMP ingMP = null;
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
			String kgProd = rowIngresoMP.getString("KGProd");
			String kgDisponible = rowIngresoMP.getString("KGDisponible");
			String kgTeorico= rowIngresoMP.getString("KGTeorico");
			ingMP = new IngresoMP(id, fecha, referencia, material, descripcion, cantidad, umb, lote,
					destinatario, colada, pesoPorBalanza);
		}
		return ingMP;
	}

	@Override
	public boolean esRollo(String loteObtenido, String materialObtenido, String pesoObtenido) {

		SqlRowSet rowIngresoMP;
		rowIngresoMP = jdbcTemplate.queryForRowSet("SELECT (1) FROM ingresomp where lote = '" + loteObtenido + "' AND material = '"
                + materialObtenido + "' AND cantidad = '" + pesoObtenido + "' AND Descripcion like '%ALAMBRON%' limit 1;");
		boolean esRollo = false;
		if(rowIngresoMP.next()) {
			esRollo = true;
		}
		return esRollo;
	}
	@Override
	public boolean esBarra(String loteObtenido, String materialObtenido, String pesoObtenido) {

		SqlRowSet rowIngresoMP;
		rowIngresoMP = jdbcTemplate.queryForRowSet("SELECT (1) FROM ingresomp where lote = '" + loteObtenido + "' AND material = '"
                + materialObtenido + "' AND cantidad = '" + pesoObtenido + "' AND Descripcion like '%BARRA%' limit 1;");
		boolean esBarra = false;
		if(rowIngresoMP.next()) {
			esBarra = true;
		}
		return esBarra;
	}

}
