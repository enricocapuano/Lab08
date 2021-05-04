package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;


public class Model {
	
	Graph<Airport, DefaultWeightedEdge> grafo;
	Map<Integer, Airport> idMap;
	ExtFlightDelaysDAO dao;
	List<Flight> voli;
	
	public Model() {
		idMap = new HashMap<Integer, Airport>();
		dao = new ExtFlightDelaysDAO();
		voli = dao.loadAllFlights();
	}
	
	
	public void creaGrafo(int distanza) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);	
		dao.loadAllAirports(idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		for(Airport a1 : this.grafo.vertexSet()) {
			
			Airport a2 = null;	
			Map<Airport, Integer> mappa = dao.getVoli(a1.getId());
			int media = 0;
			for(Airport a : mappa.keySet()) {
				media += mappa.get(a);
				a2 = a;
			}
			
			media = media/2;
			if(media >= distanza) {
				Graphs.addEdge(this.grafo, idMap.get(a1.getId()), idMap.get(a2.getId()), media);
			}

				
			
		}
		System.out.println("GRAFO CREATO!");
		System.out.println("# vertici: "+this.grafo.vertexSet().size());
		System.out.println("# archi: "+this.grafo.edgeSet().size());
	}
	
	
	
	
}
