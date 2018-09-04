package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import model.Maquina;

public class DAOMaquinaImpl implements DAOMaquina{

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DAOMaquinaImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void alta(Maquina maquina) {
		this.jdbcTemplate.update(
				"INSERT INTO maquina(clasificacion, marca, modelo, tipo_mp, diametroMin,"
				+ "diametroMax, merma) VALUES (?,?,?,?,?,?,?)", 
				maquina.getClasificacion(), maquina.getMarca(), maquina.getModelo(), maquina.getTipoMP(),
				maquina.getDiametroMin(), maquina.getDiametroMax(), maquina.getMerma());
	}

	@Override
	public void baja(long id) {
		jdbcTemplate.update("DELETE FROM maquina WHERE id = ?;", id);
	}

	@Override
	public void modificar(Maquina maquina) {
		long id = maquina.getId();
		String clasificacion = maquina.getClasificacion();
		String marca = maquina.getMarca();
		String modelo = maquina.getModelo();
		String tipoMP = maquina.getTipoMP();
		String diametroMin = maquina.getDiametroMin();
		String diametroMax = maquina.getDiametroMax();
		String merma = maquina.getMerma();
		this.jdbcTemplate.update(
				"UPDATE maquina SET clasificacion=?, marca=?, modelo=?, tipo_mp=? , diametroMin=? , diametroMax=? , "
				+ "merma=? WHERE id=?;", clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma, id);
	}

	@Override
	public ArrayList<Maquina> consultar() {		
		SqlRowSet maquinas = jdbcTemplate.queryForRowSet("SELECT * FROM maquina;");
		ArrayList<Maquina> listaDeMaquinas = new ArrayList<Maquina>();
		while (maquinas.next()) {
			long id = maquinas.getInt("id");
			String clasificacion = maquinas.getString("clasificacion");
			String marca = maquinas.getString("marca");
			String modelo = maquinas.getString("modelo");
			String tipoMP = maquinas.getString("tipo_mp");
			String diametroMin = maquinas.getString("diametroMin");
			String diametroMax = maquinas.getString("diametroMax");
			String merma = maquinas.getString("merma");
			Maquina m = new Maquina(id, clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma);
			listaDeMaquinas.add(m);
		}
		return listaDeMaquinas;
	}
	
	@Override
	public Maquina consultar(long id) {
		SqlRowSet maquina = jdbcTemplate.queryForRowSet("SELECT * FROM maquina WHERE id = ?;", id);;
		maquina.next();
		String clasificacion = maquina.getString("clasificacion");
		String marca = maquina.getString("marca");
		String modelo = maquina.getString("modelo");
		String tipoMP = maquina.getString("tipo_mp");
		String diametroMin = maquina.getString("diametroMin");
		String diametroMax = maquina.getString("diametroMax");
		String merma = maquina.getString("merma");
		return new Maquina(id, clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma);
	}

	@Override
	public List<Maquina> consultarEstribadoras() {
		SqlRowSet maquinas = jdbcTemplate.queryForRowSet("select * from maquina where clasificacion = 'Estribadora';");
		ArrayList<Maquina> listaDeMaquinas = new ArrayList<Maquina>();
		while (maquinas.next()) {
			long id = maquinas.getInt("id");
			String clasificacion = maquinas.getString("clasificacion");
			String marca = maquinas.getString("marca");
			String modelo = maquinas.getString("modelo");
			String tipoMP = maquinas.getString("tipo_mp");
			String diametroMin = maquinas.getString("diametroMin");
			String diametroMax = maquinas.getString("diametroMax");
			String merma = maquinas.getString("merma");
			Maquina m = new Maquina(id, clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma);
			listaDeMaquinas.add(m);
		}
		return listaDeMaquinas;
		
	}

	@Override
	public List<Maquina> consultarDobladoras() {
		SqlRowSet maquinas = jdbcTemplate.queryForRowSet("select * from maquina where clasificacion = 'Dobladora';");
		ArrayList<Maquina> listaDeMaquinas = new ArrayList<Maquina>();
		while (maquinas.next()) {
			long id = maquinas.getInt("id");
			String clasificacion = maquinas.getString("clasificacion");
			String marca = maquinas.getString("marca");
			String modelo = maquinas.getString("modelo");
			String tipoMP = maquinas.getString("tipo_mp");
			String diametroMin = maquinas.getString("diametroMin");
			String diametroMax = maquinas.getString("diametroMax");
			String merma = maquinas.getString("merma");
			Maquina m = new Maquina(id, clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma);
			listaDeMaquinas.add(m);
		}
		return listaDeMaquinas;
	}

	@Override
	public List<Maquina> consultarCortadoras() {
		SqlRowSet maquinas = jdbcTemplate.queryForRowSet("select * from maquina where clasificacion = 'Cortadora';");
		ArrayList<Maquina> listaDeMaquinas = new ArrayList<Maquina>();
		while (maquinas.next()) {
			long id = maquinas.getInt("id");
			String clasificacion = maquinas.getString("clasificacion");
			String marca = maquinas.getString("marca");
			String modelo = maquinas.getString("modelo");
			String tipoMP = maquinas.getString("tipo_mp");
			String diametroMin = maquinas.getString("diametroMin");
			String diametroMax = maquinas.getString("diametroMax");
			String merma = maquinas.getString("merma");
			Maquina m = new Maquina(id, clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma);
			listaDeMaquinas.add(m);
		}
		return listaDeMaquinas;
	}
	
	
}
