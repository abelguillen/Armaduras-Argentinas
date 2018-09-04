package model;

public class Stock {
	private long ID;
	private String Familia, CodMat, Descripcion, TipoMaterial, KGTeorico, KGProd, KGDisponible;
	
	public Stock(long iD, String familia, String codMat, String descripcion, String tipoMaterial,
			String kGTeorico, String kGProd, String kGDisponible) {
		super();
		ID = iD;
		Familia = familia;
		CodMat = codMat;
		Descripcion = descripcion;
		TipoMaterial = tipoMaterial;
		KGTeorico = kGTeorico;
		KGProd = kGProd;
		KGDisponible = kGDisponible;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getFamilia() {
		return Familia;
	}

	public void setFamilia(String familia) {
		Familia = familia;
	}

	public String getCodMat() {
		return CodMat;
	}

	public void setCodMat(String codMat) {
		CodMat = codMat;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getTipoMaterial() {
		return TipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		TipoMaterial = tipoMaterial;
	}

	public String getKGTeorico() {
		return KGTeorico;
	}

	public void setKGTeorico(String kGTeorico) {
		KGTeorico = kGTeorico;
	}

	public String getKGProd() {
		return KGProd;
	}

	public void setKGProd(String kGProd) {
		KGProd = kGProd;
	}

	public String getKGDisponible() {
		return KGDisponible;
	}

	public void setKGDisponible(String kGDisponible) {
		KGDisponible = kGDisponible;
	}
}
