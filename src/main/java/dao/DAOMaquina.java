package dao;

import java.util.ArrayList;
import java.util.List;

import model.Maquina;

public interface DAOMaquina {
	public void alta(Maquina maquina);
	public void baja(long id);
	public void modificar(Maquina maquina);
	public ArrayList<Maquina> consultar();
	public Maquina consultar(long id);
	public List<Maquina> consultarEstribadoras();
	public List<Maquina> consultarDobladoras();
	public List<Maquina> consultarCortadoras();
}
