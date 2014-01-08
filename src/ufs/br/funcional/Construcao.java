package ufs.br.funcional;

import java.util.ArrayList;
import java.util.Iterator;

public class Construcao {

	public Construcao() {
		// TODO Auto-generated constructor stub
	}
	
	public Automato simples(Character c){
		Automato r = new Automato();
		switch (c) {
		case 'E': r.addEstado(new Estado(0));
				  r.addEstadoFinal(r.getQ().get(0));
				  break;
		case 'V': r.addEstado(new Estado(0));
		  		  break;
		
		default: r.addEstado(new Estado(0));
		  		 r.addEstado(new Estado(1));
		  		 r.addEstadoFinal(r.getQ().get(1));
		  		 r.addTransicao(r.getQ().get(0), r.getQ().get(1), c);
		  		 break;
		}
		
		return r;
	}

	public Automato aplicaOperacao(Automato a, Automato b, Character op){
		Automato r = new Automato();
		switch (op) {
		case '*': r = Operacao.estrela(a);
				  break;

		case '.': r = Operacao.concatencao(a, b);
		  		  break;
		case '+': r = Operacao.uniao(a, b);
		  break;
		default: r = simples(op);
			break;
		}
		return r;
	}

	public String montar(ArrayList<Estado> e){
		String data = "";
		Iterator<Estado> it = e.iterator();
		while(it.hasNext()){
			Estado a = it.next();
			data += a.getId();
		}
		return data;
	}
	
	public ArrayList<Estado> extrair(Automato a,String arv){
		ArrayList<Estado> t = new ArrayList<>();
		for (int i = 0; i < arv.length(); i++) {
			t.add(a.getQ().get(Integer.parseInt(""+arv.charAt(i))));
		}
		return t;
	}
	
	public Automato equalAF2AFN(Automato a){
		Automato r = new Automato();
		ArrayList<Estado> fecho = a.fechamento(a.getQ().get(0));
		ArrayList<String> arvore = new ArrayList<>(); 
		ArrayList<Estado> zero = new ArrayList<>();
		ArrayList<Estado> um = new ArrayList<>();
		arvore.add(montar(fecho));
		int i = 0;
		while(i < arvore.size()){
			Estado e = new Estado(arvore.get(i));
			fecho = extrair(a, arvore.get(i));
			if (!r.existe(e)){
				r.addEstado(e);
				zero = a.movimento(fecho, '0');
				um = a.movimento(fecho, '1');
				if(!zero.isEmpty()){
					fecho = a.fechamento(zero);
					arvore.add(montar(fecho));
				}
				if(!um.isEmpty()){
					fecho = a.fechamento(um);
					arvore.add(montar(fecho));
				}
			}
			++i;
		}
		
		return r;
	}
	
}
