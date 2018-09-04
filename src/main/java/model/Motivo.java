package model;

public class Motivo {
	long id;
	String motivo;
	String fecha;
	long idPedido;
	public Motivo(long id, String motivo, String fecha, long idPedido) {
		super();
		this.id = id;
		this.motivo = motivo;
		this.fecha = fecha;
		this.idPedido = idPedido;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}
}
