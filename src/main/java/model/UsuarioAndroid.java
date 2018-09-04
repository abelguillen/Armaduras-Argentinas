package model;


public class UsuarioAndroid {
	
	private long id;
	private String codigo;
	private String nombre;
	private String apellido;
	private String contrasenia;
	private String fechaDeNacimiento;
	
	public UsuarioAndroid(long id, String codigo, String nombre, String apellido, String contrasenia,
			String fechaDeNacimiento) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasenia = contrasenia;
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContraseña(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public void setFechaDeNacimiento(String fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}
	
}
