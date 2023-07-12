package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<NTA, DefaultWeightedEdge> grafo;
	private List<NTA> NTA;
	
	public Model() {
		dao = new NYCDao();
	}
	
	public void creaGrafo(String borough) {
		this.NTA = dao.getNTA(borough);
		//System.out.println(this.NTA);
		grafo = new SimpleWeightedGraph<NTA, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, NTA);  //aggiunta vertici
		
		for(NTA n: NTA) {
			for(NTA n2: NTA) {
				if(!n.equals(n2)) {
					Set<String> unione = new HashSet<String>(n.getSSID()); //inizializzo con il primo
					unione.addAll(n2.getSSID()); //con addAll inserisce solo quelli non ancora presenti
					Graphs.addEdge(grafo, n, n2, unione.size()); //archi con peso calibrato 
				}
			} }
		System.out.println("N vertici: " + this.grafo.vertexSet().size()
				+ "\nN archi: " + this.grafo.edgeSet().size());  	}
	
	
	public List<String> getBorghi() {
		List<String> borghi = dao.getAllBorought();
		return borghi;  }
	
	public List<Arco> analisiArchi() {
		double media = 0.0;
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			media = media + grafo.getEdgeWeight(e);  }
		media = (media/grafo.edgeSet().size());
		
		List<Arco> result = new ArrayList<>();
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>media) 
				result.add(new Arco (grafo.getEdgeSource(e).getNTACode(),
									 grafo.getEdgeTarget(e).getNTACode(), 
									 grafo.getEdgeWeight(e)));
	 }
		Collections.sort(result);
		return result;  }
	
}
