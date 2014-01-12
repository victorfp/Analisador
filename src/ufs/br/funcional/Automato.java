package ufs.br.funcional;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Class-name: Automato
 * version: 2.1.3
 * date: 21/12/2013
 * last-modifed: 11/01/2014
 * Autor: Victor Ferreira Pereira
 * Description: Classe que representa o Automato, porem nao possui
 * 				o alfabeto(sigma), pois foi especficado que o o alfabeto
 * 				seria somente 0 e 1.
 * 				Fica estabelicido tambem que o elemento da posicao 0
 * 				do conjunto de Estados(Q) é o estado inicial.
 * 
 * */

public class Automato {
	
	//Conjunto de Estados Q
	private ArrayList<Estado> q = new ArrayList<>();
	//Conjunto de Estados Finais F
	private ArrayList<Estado> f = new ArrayList<>();
	//Funcao de Transicao
	private ArrayList<Transicao> transicoes = new ArrayList<>();
	//Albafeto
	private ArrayList<Character> alpha = new ArrayList<>();
	
	public Automato() {
		// TODO Auto-generated constructor stub
		alpha.add('0');
		alpha.add('1');
	}
	
	/* GETTERS */
	public ArrayList<Estado> getQ() {
		return q;
	}
	
	public ArrayList<Estado> getF() {
		return f;
	}
	
	public ArrayList<Transicao> getTransicoes() {
		return transicoes;
	}
	
	public ArrayList<Character> getAlpha() {
		return alpha;
	}
	
	public Estado getEstado(Estado e){
		Estado t = new Estado();
		for (int i = 0; i < q.size(); i++) {
			if (q.get(i).getId() == e.getId() && q.get(i).getLabel().equals(e.getLabel())){
				t = q.get(i);
				break;
			}
		}
		return t;
	}
	
	/* ADD Estados */
	public void addEstado(Estado e){
		q.add(e);
	}
	
	public void addEstadoFinal(int index){
		f.add(q.get(index));
	}
	
	public void addEstadoFinal(Estado e){
		f.add(e);
	}
	
	/* ADD Transicoes */
	public void addTransicao(Estado origem, Estado destino, Character simbolo) {
		// TODO Auto-generated method stub
		transicoes.add(new Transicao(q.get(q.indexOf(origem)), q.get(q.indexOf(destino)), simbolo));
	}
	
	public void addTransicao(Transicao t) {
		// TODO Auto-generated method stub
		transicoes.add(t);
	}
	
	/* 
	 * metodo: FindAll 
	 * objetivo: busca todas as possibilidades de transicoes 
	 * 			 de um estado.
	 * Paraemtros: - Um Estado "e": retorna todas as transicoes em
	 * 				 que "e" é origem
	 *             - Um Estado "e" e um simbolo: retorna todas as 
	 *               transicoes em que "e" é origem com o simbolo.
	 *             - Um simbolo: retorna todas as transcoes com este 
	 *               simbolo.
	 * */
	public ArrayList<Transicao> findAll(Estado e){
		ArrayList<Transicao> r = new ArrayList<>();
		Iterator<Transicao> it = transicoes.iterator();
		while(it.hasNext()){
			Transicao t = it.next();
			if (t.getOrigem().equals(e)){
				r.add(t);
			}
		}
		return r;
	}
	
	public ArrayList<Transicao> findAll(Estado e, Character simbolo){
		ArrayList<Transicao> r = new ArrayList<>();
		Iterator<Transicao> it = transicoes.iterator();
		while(it.hasNext()){
			Transicao t = it.next();
			if (t.getOrigem().equals(e) && t.getSimbolo().equals(simbolo)){
				r.add(t);
			}
		}
		return r;
	}
	
	public ArrayList<Transicao> findAll(Character simbolo){
		ArrayList<Transicao> r = new ArrayList<>();
		Iterator<Transicao> it = transicoes.iterator();
		while(it.hasNext()){
			Transicao t = it.next();
			if (t.getSimbolo().equals(simbolo)){
				r.add(t);
			}
		}
		return r;
	}

	/* 
	 * metodo: ALCANCABILIDADE 
	 * objetivo: calcular a alcancabilidade de um Estado "e" com
	 * 			 transicoes vazias(Epsilon).
	 * parametros: - Um Estado: retorna o conjunto de estados 
	 *   			 alcancaveis por transicao vazia.          
	 * 
	 * */
	
	public ArrayList<Estado> alcancabilidade(Estado e){
		ArrayList<Estado> alcancaveis = new ArrayList<>();
		ArrayList<Transicao> fila = findAll(e,Operacao.EPSILON);
		
		while(!fila.isEmpty()){
			if (!alcancaveis.contains(fila.get(0).getDestino())){
				alcancaveis.add(fila.get(0).getDestino());
				fila.addAll(findAll(fila.get(0).getDestino(),Operacao.EPSILON));
			}
			fila.remove(0);
		}
		return alcancaveis;
	}
	
	/* 
	 * metodo: UNIAO 
	 * objetivo: Unir dois conjutos de Estados disjuntos.
	 * parametros: - Dois Conjuntos de Estado: retorna o conjunto
	 * 				 de Estados referente a uniao deles.
	 * 
	 * */
	
	public ArrayList<Estado> uniao(ArrayList<Estado> a, ArrayList<Estado> b){
		ArrayList<Estado> iguais = new ArrayList<>();
		ArrayList<Estado> r = new ArrayList<>();
		r.addAll(a);
		r.addAll(b);
		iguais.addAll(a);
		iguais.retainAll(b);
		r.removeAll(iguais);
		r.addAll(iguais);
		return r;
	}
	
	/* 
	 * metodo: FECHAMENTO - e 
	 * objetivo: calcular o fechamento transitivo vazio de um 
	 *           Estado "e".
	 * parametros: - Um Estado: retorna o conjunto de estados 
	 *   			 referente ao fechamento-e de "e".
	 *   		   - Um conjunto de Estados: retorna o conjunto
	 *   			 de estados referente a uniao do fechamento
	 *   			 de todos os estados do conjunto passado como
	 *    	     	 parametro.          
	 * 
	 * */
	
	public ArrayList<Estado> fechamento(Estado e){
		ArrayList<Estado> r = new ArrayList<>();
		r.add(e);
		r.addAll(alcancabilidade(e));
		return r;
	}
	
	public ArrayList<Estado> fechamento(ArrayList<Estado> e){
		ArrayList<Estado> r = new ArrayList<>();
		Iterator<Estado> it = e.iterator();
		while(it.hasNext()){
			Estado es = it.next();
			r.add(es);
			r = uniao(r,alcancabilidade(es));
		}
		return r;
	}

	/* 
	 * metodo: MOVIMENTO 
	 * objetivo: realizar o movimento de um conjunto de Estado com
	 * 			 um simbolo.
	 * parametros: - Um Conjunto Estado e um simbolo: retorna o conjunto
	 *  			 de estados referente a uniao de estados cujo o
	 *  			 conjunto se direciona com o simbolo passado como
	 *               paramentro.
	 * 
	 * */
	
	public ArrayList<Estado> movimento(ArrayList<Estado> estado, Character simbolo){
		ArrayList<Estado> novos = new ArrayList<>();
		Iterator<Estado> it = estado.iterator();
		while(it.hasNext()){
			Estado e = it.next();
			ArrayList<Transicao> trans = findAll(e, simbolo);
			Iterator<Transicao> it2 = trans.iterator();
			while(it2.hasNext()){
				Transicao t = it2.next();
				if (!novos.contains(t.getDestino())){
					novos.add(t.getDestino());
				}
			}
		}
		return novos;
	}
	
	/* 
	 * metodo: Existe 
	 * objetivo: Verificar se um Estado existe em Q
	 * parametros: - Um Estado: retorna verdadeiro se o Estado existir
	 *  			 senao retorna falso.
	 * 
	 * */
	
	public boolean existe(Estado e){
		boolean flag = false;
		Iterator<Estado> it = q.iterator();
		while(it.hasNext()){
			Estado a = it.next();
			if (a.getLabel().equals(e.getLabel())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/* 
	 * metodo: Existe Transicao 
	 * objetivo: Verificar se uma Transicao existe em Transicoes
	 * parametros: - Um Estado: retorna verdadeiro se o Estado existir
	 *  			 senao retorna falso.
	 * 
	 * */
	
	public boolean existe(Estado o, Estado d, Character s){
		boolean flag = false;
		Iterator<Transicao> it = transicoes.iterator();
		while(it.hasNext()){
			Transicao a = it.next();
			if (a.getOrigem().equals(o) && a.getDestino().equals(d) && a.getSimbolo().equals(s)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean isFinal(Estado e){
		if (f.contains(e)){
			return true;
		}
		else{
			return false;
			}
	}
	
	public boolean validar(String palavra){
		Estado atual = q.get(0);
		ArrayList<Transicao> pos = new ArrayList<>();
		boolean flag = false;
		for (int i = 0; i < palavra.length(); i++) {
			if(palavra.charAt(i) != 'E'){
				pos = findAll(atual,palavra.charAt(i));
				if (!pos.isEmpty()){
					atual = pos.get(0).getDestino();
				}else{
					flag = false;
					break;
				}
			}
			if (isFinal(atual)){
				flag = true;
			}
			else{
				flag = false;
			}
		}
		
		return flag;
	}
}
