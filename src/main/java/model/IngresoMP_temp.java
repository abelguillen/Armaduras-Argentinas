package model;

import java.sql.Date;

public class IngresoMP_temp {

    private int id;
    private String fecha;
    private String material;
    private String descripcion;
    private String kgInicial;
    private String umb;
    private String lote;
    private String destinatario;
    private String colada;
    private String pesoPorBalanza;
    private String kgEnPlanta;

    public IngresoMP_temp(int id, String fecha, String material, String descripcion,String kgInicial, String umb, String lote, String destinatario, String colada, String pesoPorBalanza,String kgEnPlanta) {
        this.id = id;
        this.fecha = fecha;
        this.material = material;
        this.descripcion = descripcion;
        this.kgInicial = kgInicial;
        this.umb = umb;
        this.lote = lote;
        this.destinatario = destinatario;
        this.colada = colada;
        this.pesoPorBalanza = pesoPorBalanza;
        this.kgEnPlanta = kgEnPlanta;
    }
    public IngresoMP_temp(String material, String descripcion,String kgInicial, String umb, String lote, String destinatario, String colada, String pesoPorBalanza,String kgEnPlanta) {
        this.material = material;
        this.descripcion = descripcion;
        this.kgInicial = kgInicial;
        this.umb = umb;
        this.lote = lote;
        this.destinatario = destinatario;
        this.colada = colada;
        this.pesoPorBalanza = pesoPorBalanza;
        this.kgEnPlanta = kgEnPlanta;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getMaterial() {
        return material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getKgInicial() {
        return kgInicial;
    }

    public String getUmb() {
        return umb;
    }

    public String getLote() {
        return lote;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getColada() {
        return colada;
    }

    public String getPesoPorBalanza() {
        return pesoPorBalanza;
    }

    public String getKgEnPlanta() {
        return kgEnPlanta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setKgInicial(String kgInicial) {
        this.kgInicial = kgInicial;
    }

    public void setUmb(String ubm) {
        this.umb= umb;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setColada(String colada) {
        this.colada = colada;
    }

    public void setPesoPorBalanza(String pesoPorBalanza) {
        this.pesoPorBalanza = pesoPorBalanza;
    }

    public void setKgEnPlanta(String kgEnPlanta) {
        this.kgEnPlanta = kgEnPlanta;
    }
}

