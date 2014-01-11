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
	 * metodo: Extrair
	 * objetivo: extrai os estados com os IDs das string passada como
	 * 			 paramentro.
	 * 
	 * */
	
	public ArrayList<Estado> extrair(Automato a,String arv){
		ArrayList<Estado> t = new ArrayList<>();
		for (int i = 0; i < arv.length(); i++) {
			t.add(a.getQ().get(Integer.parseInt(""+arv.charAt(i))));
		}
		return t;
	}
	
	/*
	 * metodo: Criar Arvore
	 * objeetivo: criar a arvore de execucao de um AFN
	 * 
	 * */
	
	public ArrayList<String> criarArvore(Automato a){
		ArrayList<String> arvore = new ArrayList<>();
		ArrayList<Estado> fecho = new ArrayList<>();
		
		//Adiociona o inicial, fechamento do estado inicial de a
		arvore.add(montar(a.fechamento(a.getQ().get(0))));
		int i = 0;
		int pai = -1;
		while(i < arvore.size()){
			if  ((pai < 0)||(arvore.get(i) != "\0" && !arvore.get(i).equals(arvore.get(pai)))){
				//realiza o movimento com '0'
				fecho = a.movimento(extrair(a, arvore.get(i)), '0');
				if (fecho.isEmpty()){
					arvore.add("\0");
				}else{
					//se possui movimento, monta a string e adiciona na arvore.
					arvore.add(montar(a.fechamento(fecho)));
				}
				//realiza o movimento com '1'
				fecho = a.movimento(extrair(a, arvore.get(i)), '1');
				if (fecho.isEmpty()){
					arvore.add("\0");
				}else{
					//se possui movimento, monta a string e adiciona na arvore.
					arvore.add(montar(a.fechamento(fecho)));
				}
				pai++;
			}
			i++;
		}
		return arvore;
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
		Automato r = new Automato();
		//criar a arvore de execucao da AFN
		ArrayList<String> arvore  = criarArvore(a);
		
		for (int i = 0; i < arvore.size()/2; i++) {
			if (!arvore.get(i).equals("\0")){
				Estado e = new Estado(arvore.get(i));
				if (!r.existe(e)){
					//se o estado nao existe cria-se o estado
					r.addEstado(e);
				}else
				{
					//senao recuperar o estado
					e = r.getEstado(e);
				}
				//recupera os movimentos '0' e '1'
				String zero = arvore.get((i*2)+1);
				String um = arvore.get((i*2)+2);
				//se possuir movimentos com '0'
				if (!zero.equals("\0")){
					Estado x = new Estado(zero);
					if (!r.existe(x)){
						r.addEstado(x);
					}else{
						x = r.getEstado(x);
					}
					//adiciona a transicao '0'
					r.addTransicao(e,x,'0');
				}
				//se possuir movimentos com '0'
				if (!um.equals("\0")){
					Estado x = new Estado(um);
					if (!r.existe(x)){
						r.addEstado(x);
					}else{
						x = r.getEstado(x);
					}
					//adiciona a transicao '1'
					r.addTransicao(e,x,'1');
				}
			}
		}
		//atribui os estados finais de r
		atribuirFinais(r, a.getF());
		
		//atualiza os indices
		Operacao.updateIndex(r);
		return r;
	}

	/* 
	 * metodo: Equivalencia AFN para AFD
	 * objetivo: Criar o Automato AFD equivalente 
	 * 
	 * */
	
	public Automato equalAFN2AFD(Automato a){
		Automato r = new Automato();
		ArrayList<ArrayList<Estado>> fila = new ArrayList<>();
		ArrayList<Estado> mov = new ArrayList<>(), atual = new ArrayList<>();
		r.addEstado(a.getQ().get(0));
		int i = 0;
		mov.add(a.getQ().get(0));
		fila.add(mov);
		while(i < fila.size()){
			atual = fila.get(i);
			Estado e0 = new Estado(montar(atual));
			if (!r.existe(e0)){
				r.addEstado(e0);
			}else{
				e0 = r.getEstado(e0);
			}
			mov = a.movimento(fila.get(i), '0');
			
			Estado e = new Estado(montar(mov));
			if (!r.existe(e)){
				r.addEstado(e);
			}else{
				
				e = r.getEstado(e);
			}
			if (mov.isEmpty()){
				if (!r.existe(e0,e,'0')){
					r.addTransicao(e0, e0, '0');
				}
			}else{
				if (!r.existe(e0,e,'0')){
					fila.add(mov);
					r.addTransicao(e0, e, '0');
				}
			}
			
			mov = a.movimento(fila.get(i), '1');
			
			e = new Estado(montar(mov));
			if (!r.existe(e)){
				r.addEstado(e);
			}else{
				e = r.getEstado(e);
			}
			if (mov.isEmpty()){
				if (!r.existe(e0,e,'1')){
					r.addTransicao(e0, e0, '1');
				}
			}else{
				if (!r.existe(e0,e,'1')){
					fila.add(mov);
					r.addTransicao(e0, e, '1');
				}
			}
			i++;
		}
		atribuirFinais(r, a.getF());
		return r;
	}
}
