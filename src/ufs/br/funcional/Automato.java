package ufs.br.funcional;

import java.util.ArrayList;
import java.util.Iterator;

public class Automato {
	
	private ArrayList<Estado> q = new ArrayList<>();
	private ArrayList<Estado> f = new ArrayList<>();
	private ArrayList<Transicao> transicoes = new ArrayList<>();
	
	public Automato() {
		// TODO Auto-generated constructor stub
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
	
	/* FindAll */
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

	
	/* FECHAMENTO - e */
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
	
	public ArrayList<Estado> alcancabilidade(Estado e, Character simbolo){
		ArrayList<Estado> alcancaveis = new ArrayList<>();
		ArrayList<Transicao> fila = findAll(e,simbolo);
		
		while(!fila.isEmpty()){
			if (!alcancaveis.contains(fila.get(0).getDestino())){
				alcancaveis.add(fila.get(0).getDestino());
				fila.addAll(findAll(fila.get(0).getDestino(),simbolo));
			}
			fila.remove(0);
		}
		return alcancaveis;
	}
	
	public ArrayList<Estado> fechamento(Estado e){
		ArrayList<Estado> r = new ArrayList<>();
		r.add(e);
		r.addAll(alcancabilidade(e));
		return r;
	}
	
	public ArrayList<Estado> uniao(ArrayList<Estado> a, ArrayList<Estado> b){
		ArrayList<Estado> r = new ArrayList<>();
		Iterator<Estado> it = b.iterator();
		r.addAll(a);
		while(it.hasNext()){
			Estado e = it.next();
			if (!b.contains(e)){
				r.add(e);
			}
		}
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
	
	public ArrayList<Estado> fechamento(Estado e, Character simbolo){
		ArrayList<Estado> r = new ArrayList<>();
		r.addAll(alcancabilidade(e, simbolo));
		r.addAll(alcancabilidade(e));
		return r;
	}

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
	
}
