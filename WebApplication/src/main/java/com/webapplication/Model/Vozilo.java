package com.webapplication.Model;

public class Vozilo {
	private String id;
	private String naziv;
	private int brojMesta;
	private int brojVrata;
	private TipVozila tipVozila;
	
	
	public Vozilo() {}
	
	public Vozilo(String id, String naziv, int brojMesta, int brojVrata, TipVozila tipVozila) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.brojMesta = brojMesta;
		this.brojVrata = brojVrata;
		this.tipVozila = tipVozila;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getBrojMesta() {
		return brojMesta;
	}
	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}
	public int getBrojVrata() {
		return brojVrata;
	}
	public void setBrojVrata(int brojVrata) {
		this.brojVrata = brojVrata;
	}
	public TipVozila getTipVozila() {
		return tipVozila;
	}
	public void setTipVozila(TipVozila tipVozila) {
		this.tipVozila = tipVozila;
	}
	
	
	
}
