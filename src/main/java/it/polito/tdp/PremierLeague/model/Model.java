package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private Graph<Match, DefaultWeightedEdge> graph;
	private Map<Integer, Match> idMap;
	private PremierLeagueDAO dao;
	
	public Model() {
		dao=new PremierLeagueDAO();
	}
	
	public String creaGrafo(Month m, double min) {
		
		graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap=new HashMap<>();
		dao.getVertici(idMap, m);
		Graphs.addAllVertices(graph, idMap.values());
	
		String result=" num vertici: "+graph.vertexSet().size();
		
		List<Adiacenza> archi=dao.getArchi(idMap, m, min);
		for(Adiacenza a: archi) {
			Match m1=a.getM1();
			Match m2=a.getM2();
			if(graph.getEdge(m1, m2)==null && a.getPeso()>0) {
				Graphs.addEdge(graph, m1, m2, a.getPeso());
			}
			
		}
		result+=" num Archi: "+graph.edgeSet().size();
		return result;
	}
	
	
	public List<Adiacenza> getMigliore(){
		List<Adiacenza> result=new ArrayList<>();
		double max=0.0;
		for(DefaultWeightedEdge e: graph.edgeSet()) {
			Double peso=graph.getEdgeWeight(e);
			if(peso==max) {
				result.add(new Adiacenza(graph.getEdgeSource(e), graph.getEdgeTarget(e), graph.getEdgeWeight(e) ));

			}
			if(peso>max) {
				result.clear();
				max=peso;
				result.add(new Adiacenza(graph.getEdgeSource(e), graph.getEdgeTarget(e), graph.getEdgeWeight(e) ));
			}
			 
		}
		return result;

	}
}
