package dao;

import model.Declaracion;

public interface DAODeclaracion {
	public boolean existeItem(String codItem);
	public void insertDeclaracion(Declaracion d);
	
}
