package model;

public class Obra {
	int id;
	String codigo;
	String nombreDeObra;
	
	public Obra(int id, String nombreDeObra) {
		super();
		this.id = id;
		this.nombreDeObra = nombreDeObra;
	}

	public Obra(int id, String codigo, String nombreDeObra) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombreDeObra = nombreDeObra;
	}

	public Obra(String nombreDeObra) {
		this.nombreDeObra = nombreDeObra;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreDeObra() {
		return nombreDeObra;
	}

	public void setNombreDeObra(String nombreDeObra) {
		this.nombreDeObra = nombreDeObra;
	}	
}
