package model;

public class CliEnOb {
	long id;
	String nombreFantasia;
	String nombreDeObra;
	String contacto;
	String telefono;
	String direccion;
	String condPago;
	
	public CliEnOb(long id, String nombreFantasia, String nombreDeObra, String contacto, String telefono,
			String direccion, String condPago) {
		super();
		this.id = id;
		this.nombreFantasia = nombreFantasia;
		this.nombreDeObra = nombreDeObra;
		this.contacto = contacto;
		this.telefono = telefono;
		this.direccion = direccion;
		this.condPago = condPago;
	}

	public CliEnOb(long id, String nombreFantasia) {
		super();
		this.id = id;
		this.nombreFantasia = nombreFantasia;
	}

	public CliEnOb(long id, String nombreFantasia, String nombreDeObra) {
		super();
		this.id = id;
		this.nombreFantasia = nombreFantasia;
		this.nombreDeObra = nombreDeObra;
	}

	public CliEnOb(String nombreFantasia) {
		super();
		this.nombreFantasia = nombreFantasia;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCondPago() {
		return condPago;
	}

	public void setCondPago(String condPago) {
		this.condPago = condPago;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreFantasia() {
		return nombreFantasia;
	}

	public void setNombreFantasia(String nombreFantasia) {
		this.nombreFantasia = nombreFantasia;
	}

	public String getNombreDeObra() {
		return nombreDeObra;
	}

	public void setNombreDeObra(String nombreDeObra) {
		this.nombreDeObra = nombreDeObra;
	}
		
}
