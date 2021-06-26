package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
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
	private Double max;
	private List<Match> soluzione;
	private Match destinazione;
	
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
	
	public List<Match> getVertici(){
		List<Match> vertici=new ArrayList<>(graph.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	
	public List<Match> avviaRicorsione(Match m1, Match m2){
		this.max=0.0;
		this.soluzione=null;
		this.destinazione=m2;
		List<Match> parziale=new ArrayList<>();
		parziale.add(m1);
		this.doRicorsione(parziale, 1);
		return soluzione;
		
	}
	
	private void doRicorsione(List<Match> parziale, int livello) {
		
		Match ultimo=parziale.get(parziale.size()-1);
		if(ultimo.equals(this.destinazione)) {
			//ho trovato un percorso
			//devo verificare che sia di peso massimo
			double pesoTot=calcolaPeso(parziale);
			if(pesoTot>this.max) {
				this.max=pesoTot;
				this.soluzione=new ArrayList<>(parziale);
				return;
			}
			return;
		}
		
		List<Match> vicini=Graphs.neighborListOf(graph, ultimo);
		int team1=ultimo.getTeamHomeID();
		int team2=ultimo.getTeamAwayID();
		for(Match m: vicini) {
			int t1=m.getTeamHomeID();
			int t2=m.getTeamAwayID();
			if(!parziale.contains(m) && !(team1==t1 && team2==t2) && ! (team1==t2 && team2==t1) ) {
				parziale.add(m);
				this.doRicorsione(parziale, livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	private double calcolaPeso(List<Match> lista) {
		double tot=0.0;
		for(int i=0; i<lista.size()-1; i++) {
			Match m1=lista.get(i);
			Match m2=lista.get(i+1);
			double peso=graph.getEdgeWeight(graph.getEdge(m1, m2));
			tot+=peso;
		}
		return tot;
	}
	public Double getMax() {
		return this.max;
	}
}
