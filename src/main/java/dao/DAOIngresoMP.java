package dao;

import model.IngresoMP;

public interface DAOIngresoMP {

	public IngresoMP getIngreso(String loteObtenido, String materialObtenido, String pesoObtenido);
	public boolean esRollo(String loteObtenido, String materialObtenido, String pesoObtenido);
	public boolean esBarra(String loteObtenido, String materialObtenido, String pesoObtenido);
	public boolean updateIngreso(String loteObtenido, String materialObtenido, String pesoObtenido,String nuevoPeso);
	
}
