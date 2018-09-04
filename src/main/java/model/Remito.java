package model;

public class Remito {
	
	private long id;
	private String numeroDeRemito;
	private String fecha;
	private String estado;
	
	public Remito(long id, String numeroDeRemito, String fecha, String estado) {
		super();
		this.id = id;
		this.numeroDeRemito = numeroDeRemito;
		this.fecha = fecha;
		this.estado = estado;
	}
	
	public Remito(String numeroDeRemito) {
		super();
		this.numeroDeRemito = numeroDeRemito;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNumeroDeRemito() {
		return numeroDeRemito;
	}
	public void setNumeroDeRemito(String numeroDeRemito) {
		this.numeroDeRemito = numeroDeRemito;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
