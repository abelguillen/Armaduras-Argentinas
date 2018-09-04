package model;

public class TablaCalculos {
	double diametro;
	double pxm;
	double descxd;
	
	public TablaCalculos(double diametro, double pxm, double descxd) {
		this.diametro = diametro;
		this.pxm = pxm;
		this.descxd = descxd;
	}

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getPxm() {
		return pxm;
	}

	public void setPxm(double pxm) {
		this.pxm = pxm;
	}

	public double getDescxd() {
		return descxd;
	}

	public void setDescxd(double descxd) {
		this.descxd = descxd;
	}
	
}
