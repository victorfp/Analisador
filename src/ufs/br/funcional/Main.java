package ufs.br.funcional;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		
		Automato a = new Automato();
		a.addEstado(new Estado(0));
		a.addEstado(new Estado(1));
		a.addEstado(new Estado(2));

		a.addEstadoFinal(a.getQ().get(2));
		
		a.addTransicao(a.getQ().get(0), a.getQ().get(0), '0');
		a.addTransicao(a.getQ().get(0), a.getQ().get(1), Operacao.EPSILON);
		a.addTransicao(a.getQ().get(1), a.getQ().get(1), '1');
		a.addTransicao(a.getQ().get(1), a.getQ().get(2), Operacao.EPSILON);
		a.addTransicao(a.getQ().get(2), a.getQ().get(2), '0');
		Construcao c = new Construcao();
		Automato r = c.equalAF2AFN(a);
		Iterator<Estado> it = r.getQ().iterator();
		while(it.hasNext()){
			Estado t = it.next();
			System.out.println("o: "+t.getLabel());
		}
		
	}
	
}
