package br.ufs.funcional.automato;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import br.ufs.funcional.gramatica.Gramatica;
import br.ufs.funcional.gramatica.Producao;

public class Tabela {

	private ArrayList<ArrayList<String>> tabela = new ArrayList<>();
	private ArrayList<ArrayList<Character>> primeiros = new ArrayList<>();
	private ArrayList<ArrayList<Character>> seguintes = new ArrayList<>();
	private Gramatica gramatica;
	
	public Tabela() {
		// TODO Auto-generated constructor stub
		gramatica = new Gramatica();
	}
	
	public Tabela(Gramatica g) {
		// TODO Auto-generated constructor stub
		this.gramatica = g;
	}
	
	public ArrayList<ArrayList<String>> getTabela() {
		return tabela;
	}
	
	public ArrayList<Character> uniao(ArrayList<Character> a, ArrayList<Character> b){
		ArrayList<Character> r = new ArrayList<>();
		ArrayList<Character> iguais = new ArrayList<>();
		iguais.addAll(a);
		iguais.retainAll(b);
		r.addAll(a);
		r.addAll(b);
		r.removeAll(iguais);
		r.addAll(iguais);
		return r;
	}
	
	public boolean pertence(String behavior,int k, int i){
		for (int j = k; j < i-1; j++) {
			ArrayList<Character> temp = primeiro(behavior.charAt(j));
			if(temp.contains('E'))
				return false;
		}
		return true;
	}
	
	public boolean todosNaoTerminais(String behavior){
		for (int j = 0; j < behavior.length(); j++) {
			if(Producao.isTerminal(behavior.charAt(j)))
				return false;
		}
		return true;
	}
	
	public ArrayList<Character> primeiro(Character c){
		ArrayList<Character> primeiro = new ArrayList<>();
		ArrayList<Producao> prod = new ArrayList<>();
		prod.addAll(gramatica.getProducoes(c));
		boolean flag = false;
		if (Producao.isTerminal(c)){
			primeiro.add(c);
		}else{
			
			for (int i = 0; i < prod.size(); i++) {
				
				String producao = prod.get(i).getBehavior();
				flag = true;
				for (int j = 0; j < producao.length(); j++) {
					ArrayList<Character> p =  primeiro(producao.charAt(j));
					primeiro = uniao(primeiro, p);
					primeiro.remove(new Character('E'));
					if (!p.contains('E')){
						flag = false;
						break;
					}
				}
				if (flag){
					primeiro.add('E');
				}
			}
		}
		return primeiro;
	}
	
	public ArrayList<Character> primeiro(String s){
		ArrayList<Character> primeiro = new ArrayList<>();
		ArrayList<Producao> prod = new ArrayList<>();
		ArrayList<Character> p = primeiro(s.charAt(0));
		
		boolean flag = false;
		primeiro.addAll(p);
		primeiro.remove(new Character('E'));
		if (p.contains('E')){
			flag = true;
		}
		int n = s.length();
		for (int i = 1; i < n; i++) {
			if (!flag) break;
			for (int j = 0; j < i-1; j++) {
				if (!primeiro(s.charAt(j)).contains('E')){
					flag = false;
				}
			}
			if (flag){
				primeiro = uniao(primeiro,primeiro(s.charAt(i)));
				primeiro.remove(new Character('E'));
			}
		}
		if (flag){
			primeiro.add('E');
		}
		
		return primeiro;
	}
	
	public ArrayList<Character> seguinte2(Character c){
		ArrayList<Character> seguinte = new ArrayList<>();
		ArrayList<Producao> prod = new ArrayList<>();
		prod.addAll(gramatica.getProducoes(c));
		
		if (gramatica.variavelInicial().equals(c)){
			seguinte.add('$');
		}
		
		for (int i = 0; i < prod.size(); i++) {
			String producao = prod.get(i).getBehavior();
			for (int j = 0; j < producao.length(); j++) {
				if (!Producao.isTerminal(producao.charAt(j))){
					String w = producao.substring(j+1, producao.length());
					ArrayList<Character> p = new ArrayList<>();
					if (w.length() <= 1){
						if (w.length() != 0){
							p = primeiro(new Character(w.charAt(0)));
						}
					}else{
						p = primeiro(w);
					}
					
					seguinte = uniao(seguinte,p);
					seguinte.remove(new Character('E'));
					
					if (p.contains('E')){
						seguinte = uniao(seguinte, seguinte(prod.get(i).getVariavel()));
					}
				}
			}
		}
		
		return seguinte;
	}

	
	public ArrayList<Character> seguinte(Character c){
		ArrayList<Character> seguinte = new ArrayList<>();
		if(gramatica.variavelInicial().equals(c)){
			seguinte.add('$');
		}
		ArrayList<Producao> prod = gramatica.getNaoProduz(c);
		int index = gramatica.getNaoTerminais().indexOf(c);
		for (int i = 0; i < prod.size(); i++) {
			String producao = prod.get(i).getBehavior();
			ArrayList<Character> p = new ArrayList<>();
			if (producao.contains(c.toString())){
				if(producao.endsWith(c.toString())){
					seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
				}else{
					p.addAll(primeiro(producao.charAt(producao.indexOf(c)+1)));
					if(!Producao.isTerminal(producao.charAt(producao.indexOf(c)+1))){
						if (gramatica.existe(new Producao(producao.charAt(producao.indexOf(c)+1)+"->E"))){
							p.add('E');
						}
					}
					seguinte = uniao(seguinte,p);
					seguinte.remove(new Character('E'));
					if (p.contains('E')){
						seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
					}
				}
			}	
		}
		
		return seguinte;
	}
	
	public void iniciarTabela(ArrayList<ArrayList<String>> tabela){
		//instaciano a tabela
		for (int i = 0; i <= gramatica.getNaoTerminais().size(); i++) {
			ArrayList<String> linha = new ArrayList<>();
			for (int j = 0; j <= gramatica.getTerminais().size()+1; j++) {
				linha.add(null);
			}
		tabela.add(linha);
		}
	}
	
	public void criarTabela(ArrayList<ArrayList<String>> tabela){
		
		for (int i = 1; i <= gramatica.getNaoTerminais().size(); i++) {
			tabela.get(i).set(0, gramatica.getNaoTerminais().get(i-1).toString());
		}
		for (int i = 1; i <= gramatica.getTerminais().size(); i++) {
			tabela.get(0).set(i, gramatica.getTerminais().get(i-1).toString());
		}
		tabela.get(0).set(gramatica.getTerminais().size()+1, "$");
	}
	
	public void imprimir(ArrayList<ArrayList<String>> tabela){
		for (int i = 0; i <= gramatica.getNaoTerminais().size(); i++) {
			System.out.println(tabela.get(i).toString());
		}
	}
	
	public ArrayList<Character> variaveisE(){
		ArrayList<Character> r = new ArrayList<>();
		for (int i = 0; i < gramatica.getNaoTerminais().size(); i++) {
			if (gramatica.existe(new Producao(gramatica.getNaoTerminais().get(i)+"->E"))){
				r.add(gramatica.getNaoTerminais().get(i));
			}
		}
		return r;
	}
	
	public void computaPrimeiros(){
		for (int i = 0; i < gramatica.getNaoTerminais().size(); i++) {
			primeiros.add(primeiro(gramatica.getNaoTerminais().get(i)));
		}
		
		ArrayList<Character> eps = variaveisE();
		for (int i = 0; i < eps.size(); i++) {
			primeiros.get(gramatica.getNaoTerminais().indexOf(eps.get(i))).add('E');
		}
		
		System.out.println("primeiros: "+primeiros);
	}
	
	public void computaSeguintes(){
		for (int i = 0; i < gramatica.getNaoTerminais().size(); i++) {
			seguintes.add(seguinte(gramatica.getNaoTerminais().get(i)));
		}
		System.out.println("seguintes: "+seguintes);
	}
	
	public int indexT(Character c){
		return gramatica.getTerminais().indexOf(c)+1;
	}
	
	public int indexNT(Character c){
		return gramatica.getNaoTerminais().indexOf(c);
	}
	
	
	public void construirTabela(){
		iniciarTabela(tabela);
		criarTabela(tabela);
		computaPrimeiros();
		computaSeguintes();
		ArrayList<Producao> prod = new ArrayList<>();
		prod.addAll(gramatica.getProducoes());
		for (int i = 0; i < prod.size(); i++) {
			Character var = prod.get(i).getVariavel();
			int index = gramatica.getNaoTerminais().indexOf(var);
			ArrayList<Character> primeiro = primeiros.get(index);
			for (int j = 0; j < primeiro.size(); j++) {
				if (!primeiro.equals('E')){
					int indexT = indexT(primeiro.get(j));
					tabela.get(index+1).set(indexT, prod.get(i).getProducao());
				}
					
			}
		}
		imprimir(tabela);
		
	}
}
