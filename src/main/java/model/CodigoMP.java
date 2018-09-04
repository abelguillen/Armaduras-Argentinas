package model;

public class CodigoMP {
	private int id;
	private String codSap, familia, descripcion, tipoMaterial;

	public CodigoMP(int id, String codSap, String familia, String descripcion, String tipoMaterial) {
		this.id = id;
		this.codSap = codSap;
		this.familia = familia;
		this.descripcion = descripcion;
		this.tipoMaterial = tipoMaterial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodSap() {
		return codSap;
	}

	public void setCodSap(String codSap) {
		this.codSap = codSap;
	}
	
	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}
}
