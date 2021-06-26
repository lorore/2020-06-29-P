package it.polito.tdp.PremierLeague.model;

import java.time.Month;

public class TestModel {

	public static void main(String[] args) {
		Model m=new Model();
	System.out.println(m.creaGrafo(Month.MAY, 10));
	System.out.println(m.getMigliore());
	}

}
