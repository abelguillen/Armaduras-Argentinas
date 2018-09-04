package model;

public class IngresoRemitos {
	private long ID;
	private String fecha;
	private String nroRemito;
	private String referencia;
	private String estado;
	
	public IngresoRemitos(long iD, String fecha, String nroRemito, String referencia, String estado) {
		super();
		ID = iD;
		this.fecha = fecha;
		this.nroRemito = nroRemito;
		this.referencia = referencia;
		this.estado = estado;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNroRemito() {
		return nroRemito;
	}

	public void setNroRemito(String nroRemito) {
		this.nroRemito = nroRemito;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}