package ufs.br.funcional;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		
		Automato a = new Automato();
		Automato b = new Automato();
		
		a.addEstado(new Estado(0));
		a.addEstado(new Estado(1));
		a.addEstadoFinal(a.getQ().get(1));
		
		b.addEstado(new Estado(0));
		b.addEstado(new Estado(1));
		b.addEstadoFinal(b.getQ().get(1));
		
		a.addTransicao(a.getQ().get(0), a.getQ().get(1), '0');
		b.addTransicao(b.getQ().get(0), b.getQ().get(1), '1');
		Construcao c = new Construcao();
		Automato r = c.construir("1.0*");
		//r = c.equalAFN2AFD(r);
		Iterator<Transicao> it = r.getTransicoes().iterator();
		
		while(it.hasNext()){
			Transicao t = it.next();
			System.out.println("o: "+t.getOrigem().getLabel()+" d: "+t.getDestino().getLabel()+" s: "+t.getSimbolo());
		}
		
		Iterator<Estado> it2 = r.getF().iterator();
		while(it2.hasNext()){
			Estado t = it2.next();
			System.out.println("q: "+t.getLabel());
		}	
		System.out.println("ER: "+r.validar("10"));
	}
	
}
