package model;

public class Usuario {
	long id;
	String nombre;
	String apellido;
	String confirmacion;
	String correo;
	String contrasenia;
	public Usuario(long id, String nombre, String apellido, String confirmacion, String correo, String contrasenia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.confirmacion = confirmacion;
		this.correo = correo;
		this.contrasenia = contrasenia;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getConfirmacion() {
		return confirmacion;
	}
	public void setConfirmacion(String confirmacion) {
		this.confirmacion = confirmacion;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
}
