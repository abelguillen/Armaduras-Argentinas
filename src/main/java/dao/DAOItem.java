package dao;

import java.util.ArrayList;

import model.Item;

public interface DAOItem {
	public void alta(Item item);
	public void baja(long id);
	public void modificar(Item item);
	public ArrayList<Item> consultar();
	public Item consultar(long id);
}
