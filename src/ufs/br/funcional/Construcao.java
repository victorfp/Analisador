package ufs.br.funcional;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Class-name: Construcao
 * version: 1.1.2
 * date: 28/12/2013
 * last-modifed: 11/01/2014
 * Autor: Victor Ferreira Pereira
 * Description: Classe que realiza a Construcao e equivalencia entre
 * 				Automatos.
 * 
 * */

public class Construcao {

	public Construcao() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * metodo: Simples
	 * objetivo: Construir um automato simples. (Casos Bases)
	 * 
	 * */
	
	public Automato simples(Character c){
		Automato r = new Automato();
		
		switch (c) {
		//Palavra Vazia(Epsilon)
		case 'E' : r.addEstado(new Estado(0));
				  r.addEstadoFinal(r.getQ().get(0));
				  break;
		//Linguagem Vazia(Vazio)
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

	/*
	 * metodo: AplicaOperacao
	 * objeetivo: realizar a operacao de acordo com "op"
	 * 
	 * */
	
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

	/*
	 * metodo: Montar
	 * objetivo: montar uma String com os IDs dos Estados 
	 * 
	 * */
	
	public String montar(ArrayList<Estado> e){
		String data = "";
		Iterator<Estado> it = e.iterator();
		while(it.hasNext()){
			Estado a = it.next();
			data += a.getId();
		}
		return data;
	}
	
	/* 
	 * metodo: Atribur Finais
	 * objeetivo: define como final os estado de a que possuem algum ID
	 * 			  dos finais. 
	 * 
	 * */
	
	public void atribuirFinais(Automato a, ArrayList<Estado> finais){
		for (int i = 0; i < a.getQ().size(); i++) {
			Iterator<Estado> it = finais.iterator();
			while(it.hasNext()){
				Estado e = it.next();
				if (a.getQ().get(i).getLabel().contains(e.getLabel())){
					a.getF().add(a.getQ().get(i));
				}
			}
		}
	}
	
	/* 
	 * metodo: Equivalencia AFN-e(com transicao Epsilon) para AFN
	 * objeetivo: Criar o Automato equivalente sem transicoes Epsilon 
	 * 
	 * */
	
	public Automato equalAF2AFN(Automato a){
		//a construcao da equivalencia de A será feita, utilizando a 
		//tecnica de busca em largura.
		
		Automato r = new Automato();
		//fechamento transitivo vazio (Epsilon)
		ArrayList<Estado> fecho = new ArrayList<>();
		//conjunto de estados resultante do movimento com simbolo '0'
		ArrayList<Estado> zero = new ArrayList<>();
		//conjunto de estados resultante do movimento com simbolo '1'
		ArrayList<Estado> um = new ArrayList<>();
		//fila com os filhos(zero e um) do fechamento transtivo
		ArrayList<ArrayList<Estado>> fila = new ArrayList<>();
		//calcula o fechamento do estado inicial
		fecho = a.fechamento(a.getQ().get(0));
		fila.add(fecho);
		while(!fila.isEmpty()){
			fecho = fila.get(0);
			Estado inicial = new Estado(montar(fecho));
			//se os estado nao existir cria-se esse novo estado.
			if (!r.existe(inicial))
				r.addEstado(inicial);
			else
				inicial = r.getEstado(inicial);
			//calcula os movimentos com 0 e 1
			zero = a.movimento(fecho, '0');
			um = a.movimento(fecho, '1');
			if (!zero.isEmpty()){
				//se possui movimento com 0 calcula o fechamento
				fecho = a.fechamento(zero);
				Estado e = new Estado(montar(fecho));
				if (!r.existe(e))
					//se o estado nao existe cria o estado
					r.addEstado(e);
				else
					e = r.getEstado(e);
				if (!r.existe(inicial, e, '0')){
					//se a trasicao nao existe cria a transicao
					//e adiciona o fechamento na fila
					fila.add(fecho);
					r.addTransicao(inicial, e, '0');
				}
			}
			if (!um.isEmpty()){
				fecho = a.fechamento(um);
				
				Estado e = new Estado(montar(fecho));
				if (!r.existe(e))
					//se o estado nao existe cria o estado
					r.addEstado(e);
				else
					e = r.getEstado(e);
				if (!r.existe(inicial, e, '1')){
					//se a trasicao nao existe cria a transicao
					//e adiciona o fechamento na fila
					fila.add(fecho);
					r.addTransicao(inicial, e, '1');
				}
			}
			fila.remove(0);
		}
		atribuirFinais(r, a.getF());
		Operacao.updateIndex(r);
		return r;
	}

	/* 
	 * metodo: Equivalencia AFN para AFD
	 * objetivo: Criar o Automato AFD equivalente 
	 * 
	 * */
	
	public Automato equalAFN2AFD(Automato a){
		Automato r = a;
		Estado morto = new Estado("morto");
		r.addEstado(morto);
		r.addTransicao(morto,morto,'0');
		r.addTransicao(morto,morto,'1');
		Iterator<Estado> it = r.getQ().iterator();
		while(it.hasNext()){
			Estado e = it.next();
			if (r.findAll(e, '0').isEmpty()){
				r.addTransicao(e, morto, '0');
			}
			if (r.findAll(e, '1').isEmpty()){
				r.addTransicao(e, morto, '1');
			}
		}
		Operacao.updateIndex(r);
		return r;
	}

	public boolean isOperacao(Character c){
		if (c.equals('.') || c.equals('+') || c.equals('*'))
			return true;
		else
			return false;
	}
	
	public Automato construir(String er){
		Automato a = new Automato(),b = new Automato();
		Character op = '\0';
		for (int i = 0; i < er.length(); i++) {
			if (a.getAlpha().contains(er.charAt(i)) && op == '\0'){
				a = aplicaOperacao(null, null, er.charAt(i));
			}
			else{
				if (op != '\0'){
					b = aplicaOperacao(null, null, er.charAt(i));
					a = aplicaOperacao(a, b, op);
					op = '\0';
				}else
					if (isOperacao(er.charAt(i)) && er.charAt(i) != '*'){
						op = er.charAt(i);
					}else{
						a = aplicaOperacao(a, null, er.charAt(i));
					}
			}
		}
		return a;
	}
	
	public Automato construir(ER er){
		Automato r = new Automato();
		ArrayList<Automato> automatos = new ArrayList<>();
		er.separar();
		
		for (int i = 0; i < er.getElementos().size(); i++) {
			if (er.getElementos().get(i).startsWith("(")){
				ArrayList<Automato> temp = new ArrayList<>();
				ER expressao;
				if (er.getElementos().get(i).endsWith("*")){
					expressao = new ER(er.getElementos().get(i).substring(1, er.getElementos().get(i).length()-2));
				}else{
					expressao = new ER(er.getElementos().get(i).substring(1, er.getElementos().get(i).length()-1));
				}
				
				expressao.separar();
				for (int j = 0; j < expressao.getElementos().size(); j++) {
					temp.add(construir(expressao.getElementos().get(j)));
				}
				Automato c = temp.get(0);
				for (int j = 1; j < temp.size(); j++) {
					Automato b = temp.get(j);
					c = aplicaOperacao(c, b, expressao.getOperadores().get(j-1));
				}
				if (er.getElementos().get(i).endsWith("*")){
					automatos.add(Operacao.estrela(c));
				}else{
				automatos.add(c);
				}
			}else{
				automatos.add(construir(er.getElementos().get(i)));
			}
		}
		Automato a = automatos.get(0);
		for (int i = 1; i < automatos.size(); i++) {
			if (i < er.getOperadores().size()){
				if (!er.getOperadores().get(i-1).equals(er.getOperadores().get(i))){
					Automato b = automatos.get(i);
					Automato c = automatos.get(i+1);
					b = aplicaOperacao(b, c, er.getOperadores().get(i));
					a = aplicaOperacao(a, b, er.getOperadores().get(i-1));
					i++;
				}else{
					Automato b = automatos.get(i);
					a = aplicaOperacao(a, b, er.getOperadores().get(i-1));
				}
			}else{
				Automato b = automatos.get(i);
				a = aplicaOperacao(a, b, er.getOperadores().get(i-1));
			}
			
		}
		
		r = equalAF2AFN(a);
		r = equalAFN2AFD(r);
		return r;
	}
}
