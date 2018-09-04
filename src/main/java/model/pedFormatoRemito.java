package model;

public class pedFormatoRemito {
	String codigo;
	String descripcion;
	String um;
	String total;
	
	public pedFormatoRemito(String codigo, String descripcion, String um, String total) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.um = um;
		this.total = total;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}