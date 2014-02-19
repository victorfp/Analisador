package br.ufs.funcional.automato;

import java.util.ArrayList;
import br.ufs.funcional.gramatica.Gramatica;
import br.ufs.funcional.gramatica.Producao;

public class Tabela {

	private ArrayList<ArrayList<String>> tabela = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<Character>> primeiros = new ArrayList<ArrayList<Character>>();
	private ArrayList<ArrayList<Character>> seguintes = new ArrayList<ArrayList<Character>>();
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
	
	public ArrayList<ArrayList<Character>> getPrimeiros() {
		return primeiros;
	}
	
	public ArrayList<ArrayList<Character>> getSeguintes() {
		return seguintes;
	}
	
	public ArrayList<Character> uniao(ArrayList<Character> a, ArrayList<Character> b){
		ArrayList<Character> r = new ArrayList<Character>();
		ArrayList<Character> iguais = new ArrayList<Character>();
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
		ArrayList<Character> primeiro = new ArrayList<Character>();
		ArrayList<Producao> prod = new ArrayList<Producao>();
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
		ArrayList<Character> primeiro = new ArrayList<Character>();
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
	
	public int contarChar(String s, Character c){
		int total = 0;
		for (int i = 0; i < s.length(); i++) {
			if(c.equals(s.charAt(i))){
				total++;
			}
		}
		return total;
	}
	
	public ArrayList<Character> seguinte(Character c){
		ArrayList<Character> seguinte = new ArrayList<Character>();
		if(gramatica.variavelInicial().equals(c)){
			seguinte.add('$');
		}
		ArrayList<Producao> prod = gramatica.getContido(c);
		for (int i = 0; i < prod.size(); i++) {
			String producao = prod.get(i).getBehavior();
			ArrayList<Character> p = new ArrayList<Character>();
			if(contarChar(producao, c)>=2){
				if(producao.endsWith(c.toString())){
					if (!prod.get(i).getVariavel().equals(c))
						seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
				}
				p.addAll(primeiro(producao.charAt(producao.indexOf(c)+1)));
				
				if(!Producao.isTerminal(producao.charAt(producao.indexOf(c)+1))){
					
					if (gramatica.existe(new Producao(producao.charAt(producao.indexOf(c)+1)+"->E"))){
						p.add('E');
					}
				}
				seguinte = uniao(seguinte,p);
				seguinte.remove(new Character('E'));
				if (p.contains('E')){
					if (!prod.get(i).getVariavel().equals(c))
						seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
				}
			}else{
				if(producao.contains(c.toString())){
					if(producao.endsWith(c.toString())){
						if (!prod.get(i).getVariavel().equals(c)){
							seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
						}
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
							if (!prod.get(i).getVariavel().equals(c))
								seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
						}
					}
				}
			}
		}
		
		return seguinte;
	}
	
	public void iniciarTabela(ArrayList<ArrayList<String>> tabela){
		//instaciano a tabela
		for (int i = 0; i <= gramatica.getNaoTerminais().size(); i++) {
			ArrayList<String> linha = new ArrayList<String>();
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
		ArrayList<Character> r = new ArrayList<Character>();
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
	}
	
	public void computaSeguintes(){
		for (int i = 0; i < gramatica.getNaoTerminais().size(); i++) {
			seguintes.add(seguinte(gramatica.getNaoTerminais().get(i)));
		}
	}
	
	public int indexT(Character c){
		if (!c.equals('$'))
			return gramatica.getTerminais().indexOf(c)+1;
		else
			return gramatica.getTerminais().size()+1;
	}
	
	public int indexNT(Character c){
		return gramatica.getNaoTerminais().indexOf(c);
	}
	
	
	public void construirTabela(){
		iniciarTabela(tabela);
		criarTabela(tabela);
		computaPrimeiros();
		computaSeguintes();
		ArrayList<Producao> prod = new ArrayList<Producao>();
		prod.addAll(gramatica.getProducoes());
		for (int i = 0; i < prod.size();i++) {
			Character var = prod.get(i).getVariavel();
			int index = gramatica.getNaoTerminais().indexOf(var);
			ArrayList<Character> primeiro = primeiro(prod.get(i).getBehavior());
			for (int j = 0; j < primeiro.size(); j++) {
				if (!primeiro.get(j).equals('E')){
					if (tabela.get(index+1).get(indexT(primeiro.get(j))) == null){
						tabela.get(index+1).set(indexT(primeiro.get(j)), prod.get(i).getProducao());
					}
				}
			}
			if (prod.get(i).getProducao().equals(var+"->E")){
				ArrayList<Character> seguinte = seguintes.get(index);
				for (int j = 0; j < seguinte.size(); j++) {
					tabela.get(index+1).set(indexT(seguinte.get(j)), prod.get(i).getProducao());
				}
			}else{
				if (primeiro.contains('E')){
					ArrayList<Character> seguinte = seguintes.get(index);
					for (int j = 0; j < seguinte.size(); j++) {
						tabela.get(index+1).set(indexT(seguinte.get(j)), prod.get(i).getProducao());
					}
				}
			}
		}
	}
}
