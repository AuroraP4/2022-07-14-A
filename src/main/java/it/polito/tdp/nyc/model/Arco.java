package it.polito.tdp.nyc.model;

public class Arco implements Comparable<Arco>{

	String vertice1;
	String vertice2;
	Double peso;
	
	public Arco(String vertice1, String vertice2, Double peso) {
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.peso = peso;
	}

	public String getVertice1() {
		return vertice1;
	}

	public String getVertice2() {
		return vertice2;
	}

	public Double getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return "Arco [vertice1=" + vertice1 + ", vertice2=" + vertice2 + ", peso=" + peso + "]";
	}

	public int compareTo(Arco other) {
		return -this.peso.compareTo(other.peso); //descrescente
	}
	
}