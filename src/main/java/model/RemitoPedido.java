package model;

public class RemitoPedido {
	private long id;
	private String numeroDeRemito;
	private String codigoDePedido;
	private String codigo;
	private String descripcion;
	private String um;
	private String total;
	
	public RemitoPedido(long id, String numeroDeRemito, String codigoDePedido, String codigo, String descripcion,
			String um, String total) {
		super();
		this.id = id;
		this.numeroDeRemito = numeroDeRemito;
		this.codigoDePedido = codigoDePedido;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.um = um;
		this.total = total;
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
	public String getCodigoDePedido() {
		return codigoDePedido;
	}
	public void setCodigoDePedido(String codigoDePedido) {
		this.codigoDePedido = codigoDePedido;
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
