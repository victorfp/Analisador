package ufs.br.funcional;

import java.util.Iterator;

/*
 * Class-name: Operacao
 * version: 2.0.1
 * date: 21/12/2013
 * last-modifed: 03/01/2014
 * Autor: Victor Ferreira Pereira
 * Description: Classe que representa o Automato, porem nao possui
 * 				o alfabeto(sigma), pois foi especficado que o o alfabeto
 * 				seria somente 0 e 1.
 * 				Fica estabelicido tambem que o elemento da posicao 0
 * 				do conjunto de Estados(Q) é o estado inicial.
 * 
 * */

public class Operacao {
	
	public static final Character EPSILON = 'E';
	public static final Character VAZIO = 'V';
	
	
	/*
	 * metodo: Update Index
	 * Objetivo: Atualizar os Indices de um automato.
	 * Parametros: - Automato a: sem retorno.
	 * 
	 * */
	
	public static void updateIndex(Automato a){
		Iterator<Estado> it = a.getQ().iterator();
		int id = 0;
		while(it.hasNext()){
			Estado e = it.next();
			e.setId(id++);
		}
	}
	
	 /*
	 * metodo: Uniao
	 * Objetivo: Realizar a Uniao de Dois Automatos Finitos NAO 
	 * 	         Deterministicos (AFN).
	 * Parametros: - Automatos a,b: retorna um Automato referente
	 * 				 a uniao desses dois automatos.
	 * 
	 * */
	
	public static Automato uniao(Automato a, Automato b){
		Automato r = new Automato();
		//Adiciona um novo estado Inicial.
		r.addEstado(new Estado(0));
		
		//Adiciona todos os Estados de a e de b;
		r.getQ().addAll(a.getQ());
		r.getQ().addAll(b.getQ());
		
		//Cria uma transicao Epsilon do novo estado inicial para
		//os iniciais de a e de b.
		r.addTransicao(r.getQ().get(0), a.getQ().get(0), EPSILON);
		r.addTransicao(r.getQ().get(0), b.getQ().get(0), EPSILON);
		
		//Adiciona todas as transicoes de a e b.
		r.getTransicoes().addAll(a.getTransicoes());
		r.getTransicoes().addAll(b.getTransicoes());
		
		//Adiciona um novo Estado Final
		int ultimo = r.getQ().size();
		r.addEstado(new Estado(ultimo));
		r.addEstadoFinal(ultimo);
		
		//Cria Transicoes dos estados finai de a para o novo estado
		//final.
		Iterator<Estado> it = a.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
		}
		
		//Cria Transicoes dos estados finai de b para o novo estado
		//final.
		it = b.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
		}
		
		//atualiza os indices
		updateIndex(r);
		return r;
	}

	/*
	 * metodo: Concatenacao
	 * Objetivo: Realizar a Concatenacao de Dois Automatos Finitos NAO 
	 * 	         Deterministicos (AFN).
	 * Parametros: - Automatos a,b: retorna um Automato referente
	 * 				 a concatencao desses dois automatos.
	 * 
	 * */

	public static Automato concatencao(Automato a, Automato b){
		Automato r = new Automato();
		//Adiciona um novo estado Inicial.
		r.addEstado(new Estado(0));
		
		//Adiciona todos os Estados de a e de b;
		r.getQ().addAll(a.getQ());
		r.getQ().addAll(b.getQ());
		
		//Cria uma transicao Epsilon do novo estado inicial para
		//o inicial de a.
		r.addTransicao(r.getQ().get(0), a.getQ().get(0), EPSILON);
		
		//Cria Transicoes dos estados finais de a para o estado inicial
		//de b.
		Iterator<Estado> it = a.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, b.getQ().get(0), EPSILON);
		}
		
		//Adiciona todas as Transicoes de a e b
		r.getTransicoes().addAll(a.getTransicoes());
		r.getTransicoes().addAll(b.getTransicoes());
		
		//Cria um novo estado final
		int ultimo = r.getQ().size();
		r.addEstado(new Estado(ultimo));
		r.addEstadoFinal(ultimo);
		
		//Adiciona Transicoes vazia dos finais de b para o novo estado final
		it = b.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			r.addTransicao(e, r.getF().get(0), EPSILON);
		}
		
		//atualiza indices
		updateIndex(r);
		return r;
	}

	/*
	 * metodo: Estrela ou Fecho de Kleene
	 * Objetivo: Realizar o Fecho de Kleene de um Automato Finito NAO 
	 * 	         Deterministico (AFN).
	 * Parametros: - Automato a: retorna um Automato referente
	 * 				 ao Fecho de Kleene desses dois automatos.
	 * 
	 * */
	
	public static Automato estrela(Automato a){
		Automato r = new Automato();
		
		//Cria um novo Estado inicial
		r.addEstado(new Estado(0));
		
		//Adiciona os estados de a
		r.getQ().addAll(a.getQ());
		
		//Adiciona as transicoes de a
		r.getTransicoes().addAll(a.getTransicoes());
		
		//Cria um novo estado final
		int ultimo = r.getQ().size();
		r.addEstado(new Estado(ultimo));
		r.addEstadoFinal(ultimo);
		
		//Adiciona um transicao vazia do novo inicial para o inicial de a
		r.addTransicao(r.getQ().get(0), a.getQ().get(0), EPSILON);
		
		//Adiciona um transicao vazia do novo inicial para o novo final
		//pois o inicial tambem é final.
		r.addTransicao(r.getQ().get(0), r.getF().get(0), EPSILON);
		
		Iterator<Estado> it = a.getF().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			//Adiciona um transicao vazia do final de a para o novo final
			r.addTransicao(e, r.getF().get(0), EPSILON);
			//Adiciona um transicao vazia do final de a para o inicial de a
			r.addTransicao(e, a.getQ().get(0), EPSILON);
		}
		//atualiza os indices
		updateIndex(r);
		return r;
	}

}
