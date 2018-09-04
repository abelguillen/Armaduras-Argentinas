package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import model.Declaracion;

public class DAODeclaracionImpl implements DAODeclaracion{

	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public DAODeclaracionImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	@Override
	public boolean existeItem(String codItem) {
		SqlRowSet declaracionDB;
		declaracionDB = jdbcTemplate.queryForRowSet("SELECT (1) FROM DECLARACION WHERE Item = ? limit 1;", codItem);
		boolean existe = false;
		if(declaracionDB.next()) {
			existe = true;
		}
		
		return existe;
	}

	@Override
	public void insertDeclaracion(Declaracion d) {
		// TODO Auto-generated method stub
		
	}
	
}
