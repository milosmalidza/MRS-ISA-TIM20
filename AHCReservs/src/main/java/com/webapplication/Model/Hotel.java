package com.webapplication.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel extends Company {
	
	private Map<Integer, List<Room>> rooms; //kljuc sprat hotela, vrednost lista soba na tom spratu
	private Map<String, Double> prices; //kljuc usluga, vrednost cena te usluge
	
	public Hotel() {
		
		super();
		
		this.rooms = new HashMap<Integer, List<Room>>();
		this.prices = new HashMap<String, Double>();
		
	}
	
	public Hotel(String name) {
		super(name);
	}
	
	public Hotel(int id, String name, String description, String address, double rating) {
		
		super(id, name, description, address, rating);
		
		this.rooms = new HashMap<Integer, List<Room>>();
		this.prices = new HashMap<String, Double>();
	}

	public Map<Integer, List<Room>> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Integer, List<Room>> rooms) {
		this.rooms = rooms;
	}

	public Map<String, Double> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, Double> prices) {
		this.prices = prices;
	}
	
	

}
