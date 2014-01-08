package ufs.br.funcional;

import java.util.Iterator;

public class Operacao {
	
	public static final Character EPSILON = 'E';
	public static final Character VAZIO = 'V';
	
	public static void updateIndex(Automato a){
		Iterator<Estado> it = a.getQ().iterator();
		int id = 0;
		while(it.hasNext()){
			Estado e = it.next();
			e.setId(id++);
		}
	}
	
	/* UNIAO */
	public static Automato uniao(Automato a, Automato b){
		Automato r = new Automato();
		r.addEstado(new Estado(0));
		r.getQ().addAll(a.getQ());
		r.getQ().addAll(b.getQ());
		r.addTransicao(r.getQ().get(0), a.getQ().get(0), EPSILON);
		r.addTransicao(r.getQ().get(0), b.getQ().get(0), EPSILON);
		r.getTransicoes().addAll(a.getTransicoes());
		r.getTransicoes().addAll(b.getTransicoes());
		int ultimo = r.getQ().size();
		r.addEstado(new Estado(ultimo));
		r.addEstadoFinal(ultimo);
		Iterator<Estado> it = a.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
		}
		it = b.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
		}
		updateIndex(r);
		return r;
	}

	/* CONCATENACAO */
	public static Automato concatencao(Automato a, Automato b){
		Automato r = new Automato();
		r.addEstado(new Estado(0));
		r.getQ().addAll(a.getQ());
		r.getQ().addAll(b.getQ());
		r.addTransicao(r.getQ().get(0), a.getQ().get(0), EPSILON);
		Iterator<Estado> it = a.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, b.getQ().get(0), EPSILON);
		}
		r.getTransicoes().addAll(a.getTransicoes());
		r.getTransicoes().addAll(b.getTransicoes());
		 
		int ultimo = r.getQ().size();
		r.addEstado(new Estado(ultimo));
		r.addEstadoFinal(ultimo);
		
		it = b.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
		}
		updateIndex(r);
		return r;
	}

	/* ESTRELA */
	public static Automato estrela(Automato a){
		Automato r = new Automato();
		r.addEstado(new Estado(0));
		r.getQ().addAll(a.getQ());
		r.getTransicoes().addAll(a.getTransicoes());
		int ultimo = r.getQ().size();
		r.addEstado(new Estado(ultimo));
		r.addEstadoFinal(ultimo);
		
		r.addTransicao(r.getQ().get(0), a.getQ().get(0), EPSILON);
		r.addTransicao(r.getQ().get(0), r.getF().get(0), EPSILON);
		
		Iterator<Estado> it = a.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
			r.addTransicao(e, a.getQ().get(0), EPSILON);
		}
		
		updateIndex(r);
		return r;
	}

}
