package model;

public class Maquina {
	private long id;
	private String clasificacion, marca, modelo, tipoMP, diametroMin, diametroMax, merma;
	
	public Maquina(String clasificacion, String marca, String modelo, String tipoMP,
			String diametroMin, String diametroMax, String merma) {
		this.clasificacion = clasificacion;
		this.marca = marca;
		this.modelo = modelo;
		this.tipoMP = tipoMP;
		this.diametroMin = diametroMin;
		this.diametroMax = diametroMax;
		this.merma = merma;
	}
	
	public Maquina(long id, String clasificacion, String marca, String modelo, String tipoMP,
			String diametroMin, String diametroMax, String merma) {
		this.id = id;
		this.clasificacion = clasificacion;
		this.marca = marca;
		this.modelo = modelo;
		this.tipoMP = tipoMP;
		this.diametroMin = diametroMin;
		this.diametroMax = diametroMax;
		this.merma = merma;
	}
	
	public boolean validar(){
		if(
			!clasificacion.isEmpty() &&
			!marca.isEmpty() &&
			!modelo.isEmpty() &&
			!tipoMP.isEmpty() &&
			!diametroMin.isEmpty() &&
			!diametroMax.isEmpty() &&
			Integer.parseInt(diametroMax) > Integer.parseInt(diametroMin) &&
			!merma.isEmpty()
		) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return "Maquina [id=" + id + ", clasificacion=" + clasificacion + ", marca=" + marca + ", modelo=" + modelo
				+ ", tipoMP=" + tipoMP + ", diametroMin=" + diametroMin + ", diametroMax=" + diametroMax + ", merma="
				+ merma + "]";
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipoMP() {
		return tipoMP;
	}

	public void setTipoMP(String tipoMP) {
		this.tipoMP = tipoMP;
	}

	public String getDiametroMin() {
		return diametroMin;
	}

	public void setDiametroMin(String diametroMin) {
		this.diametroMin = diametroMin;
	}

	public String getDiametroMax() {
		return diametroMax;
	}

	public void setDiametroMax(String diametroMax) {
		this.diametroMax = diametroMax;
	}

	public String getMerma() {
		return merma;
	}

	public void setMerma(String merma) {
		this.merma = merma;
	}
}
