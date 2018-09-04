package model;

public class Cliente {
	int id;
	String nroCuil;
	String nombreFantasia;
	String razonSocial;
	String direccionFiscal;
	String telefono;
	String codigo;
	
	// CONSTRUCTOR GENERAL
	public Cliente(int id, String codigo, String nroCuil, String nombreFantasia, String razonSocial, String direccionFiscal, String telefono) {
		this.id = id;
		this.nroCuil = nroCuil;
		this.nombreFantasia = nombreFantasia;
		this.razonSocial = razonSocial;
		this.direccionFiscal = direccionFiscal;
		this.telefono = telefono;
		this.codigo = codigo;
	}
	
	// CONSTRUCTOR CON NOMBRE DE FANTASIA
	public Cliente(String nombreFantasia) {
		this.nombreFantasia = nombreFantasia;
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

	public String getNroCuil() {
		return nroCuil;
	}

	public void setNroCuil(String nroCuil) {
		this.nroCuil = nroCuil;
	}

	public String getNombreFantasia() {
		return nombreFantasia;
	}

	public void setNombreFantasia(String nombreFantasia) {
		this.nombreFantasia = nombreFantasia;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccionFiscal() {
		return direccionFiscal;
	}

	public void setDireccionFiscal(String direccionFiscal) {
		this.direccionFiscal = direccionFiscal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
