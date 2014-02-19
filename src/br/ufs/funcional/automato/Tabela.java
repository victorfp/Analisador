package br.ufs.funcional.automato;

import java.util.ArrayList;
import br.ufs.funcional.gramatica.Gramatica;
import br.ufs.funcional.gramatica.Producao;

/*
 * class Tabela
 * autor: Victor Ferreira Pereira
 * 
 * descricao: tabela M[N,T] que relaciona um Nao-Terminal e um Terminal 
 * 			  à uma producao P.
 * 
 * */

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
	
	//getters
	public ArrayList<ArrayList<String>> getTabela() {
		return tabela;
	}
	
	public ArrayList<ArrayList<Character>> getPrimeiros() {
		return primeiros;
	}
	
	public ArrayList<ArrayList<Character>> getSeguintes() {
		return seguintes;
	}
	
	/*
	 * uniao
	 * 
	 * realiza a uniao entre duas listas.
	 * 
	 * */
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
	
	/*
	 * primeiro
	 * 
	 * calcula o conjunto dos elementos primeiros para um caracter
	 * 
	 * */
	
	public ArrayList<Character> primeiro(Character c){
		ArrayList<Character> primeiro = new ArrayList<Character>();
		ArrayList<Producao> prod = new ArrayList<Producao>();
		//pega todas as producao que 'c' produz, ou seja, sao do tipo 'c'->...
		prod.addAll(gramatica.getProducoes(c));
		boolean flag = false;
		if (Producao.isTerminal(c)){
			//caso seja terminal primero(c) = {c}
			primeiro.add(c);
		}else{
			//para cada producao que 'c' produz
			for (int i = 0; i < prod.size(); i++) {
				//pega-se o lado direito da producao
				String producao = prod.get(i).getBehavior();
				//seta a flag para true
				flag = true;
				//para todos os caracteres do lado direito 
				for (int j = 0; j < producao.length(); j++) {
					//calcula o conjunto primeiro para ele
					ArrayList<Character> p =  primeiro(producao.charAt(j));
					//primeiro(c) = primeiro U primeiro(cj) - {E}
					primeiro = uniao(primeiro, p);
					primeiro.remove(new Character('E'));
					if (!p.contains('E')){
						//se o primeiro de cj nao contem E interrompe a execucao
						//pois, existe um 'x' que nao deriva para 'E'(epsilon)
						flag = false;
						break;
					}
				}
				//se todos os caracteres do lado direito derivam para epsilon, entao é adicionado
				//'E' ao conjunto primeiro de c.
				if (flag){
					primeiro.add('E');
				}
			}
		}
		return primeiro;
	}
	
	/*
	 * primeiro
	 * 
	 * calcula o conjunto dos elementos primeiros para uma cadeia de caracter
	 * 
	 * */
	public ArrayList<Character> primeiro(String s){
		ArrayList<Character> primeiro = new ArrayList<Character>();
		//calcula-se o conjunto primeiro para o primeiro caracter da cadeia
		ArrayList<Character> p = primeiro(s.charAt(0));
		//esta flag indica se pode continuar ou nao o calculo do conjunto primeiro
		boolean flag = false;
		primeiro.addAll(p);
		primeiro.remove(new Character('E'));
		//se o conjunto primeiro para o primeiro caracter contem 'E' seta a flag para true
		if (p.contains('E')){
			flag = true;
		}
		
		int n = s.length();
		for (int i = 1; i < n; i++) {
			//se nao pode continuar a execucao
			if (!flag) break;
			for (int j = 0; j < i-1; j++) {
				//verifica se todos os primeiros do char 0 ate o char i-1 contem 'E'  
				if (!primeiro(s.charAt(j)).contains('E')){
					flag = false;
				}
			}
			//se sim faz a primeiro = primeiro U primeiro(ci)-{E}
			if (flag){
				primeiro = uniao(primeiro,primeiro(s.charAt(i)));
				primeiro.remove(new Character('E'));
			}
		}
		//se todos possuem derivacao para E entao adiciona E ao conjunto primeiro
		if (flag){
			primeiro.add('E');
		}
		
		return primeiro;
	}
	
	/*
	 * contarChar
	 * 
	 * Dada uma string s e um char c, retorna a quantidade de vezes que esse char 
	 * aparece em s.
	 * 
	 * */
	
	public int contarChar(String s, Character c){
		int total = 0;
		for (int i = 0; i < s.length(); i++) {
			if(c.equals(s.charAt(i))){
				total++;
			}
		}
		return total;
	}
	
	/*
	 * seguinte
	 * 
	 * calcula o conjunto dos elementos seguintes para um character c.
	 * 
	 * */
	
	public ArrayList<Character> seguinte(Character c){
		ArrayList<Character> seguinte = new ArrayList<Character>();
		//se c é variavel inicial adiciona $ ao conjunto seguinte.
		if(gramatica.variavelInicial().equals(c)){
			seguinte.add('$');
		}
		//pega todas as producoes em que c esta contido do lado direito
		ArrayList<Producao> prod = gramatica.getContido(c);
		
		//para cada producao em que c esta contido do lado direito
		for (int i = 0; i < prod.size(); i++) {
			String producao = prod.get(i).getBehavior();
			ArrayList<Character> p = new ArrayList<Character>();
			/*caso 1:
			 * 		se o caracter 'c' aparecer mais de 1 vez no lado direito, ou
			 * 		seja, para c='S'(exemplo) a producao é do tipo: X->aSbS
			 */
			if(contarChar(producao, c)>=2){
				//verifica se termina com 'c'
				if(producao.endsWith(c.toString())){
					if (!prod.get(i).getVariavel().equals(c))
						//seguinte = seguinte(c) U seguinte(Variavel da producao)
						seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
				}
				//calcula-se o conjuto primeiro para o o caracter seguinte ao 'c' 
				p.addAll(primeiro(producao.charAt(producao.indexOf(c)+1)));
				//se o caracter 'x' seguinte nao é terminal
				if(!Producao.isTerminal(producao.charAt(producao.indexOf(c)+1))){
					//se 'x' deriva para 'E' adiciona E para o conjunto dos primeiros
					if (gramatica.existe(new Producao(producao.charAt(producao.indexOf(c)+1)+"->E"))){
						p.add('E');
					}
				}
				//seguinte = seguinte U primeiros - {E}
				seguinte = uniao(seguinte,p);
				seguinte.remove(new Character('E'));
				//E está contido nos primeiros
				if (p.contains('E')){
					if (!prod.get(i).getVariavel().equals(c))
						//seguinte = seguinte(c) U seguinte(Variavel da producao)
						seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
				}
			}else{
				if(producao.contains(c.toString())){
					/*caso 2:
					 * 		o caracter 'c' aparece apenas no final da producao, ou
					 * 		seja, para c='S'(exemplo) a producao é do tipo: X->aS
					 */
					if(producao.endsWith(c.toString())){
						if (!prod.get(i).getVariavel().equals(c)){
							//seguinte = seguinte(c) U seguinte(Variavel da producao)
							seguinte = uniao(seguinte,seguinte(prod.get(i).getVariavel()));
						}
					}else{
						/*caso 3:
						 * 		o caracter 'c' nao finaliza a producao, ou seja,
						 * 		para c='S'(exemplo) a producao é do tipo: X->aSb
						 */
						
						//calcula-se o conjuto primeiro para o o caracter seguinte ao 'c'
						p.addAll(primeiro(producao.charAt(producao.indexOf(c)+1)));
						//adiciona E ao conjunto primeiro se derivar para 'E'
						if(!Producao.isTerminal(producao.charAt(producao.indexOf(c)+1))){
							if (gramatica.existe(new Producao(producao.charAt(producao.indexOf(c)+1)+"->E"))){
								p.add('E');
							}
						}
						//seguinte = seguinte U primeiros - {E}
						seguinte = uniao(seguinte,p);
						seguinte.remove(new Character('E'));
						//E está contido nos primeiros
						if (p.contains('E')){
							if (!prod.get(i).getVariavel().equals(c))
								//seguinte = seguinte(c) U seguinte(Variavel da producao)
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
