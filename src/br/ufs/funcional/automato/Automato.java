package br.ufs.funcional.automato;

import br.ufs.funcional.gramatica.Gramatica;
import br.ufs.funcional.gramatica.Producao;

/*
 * class Automato
 * autor: Victor Ferreira Pereira
 * 
 * descricao: Classe que representa o automato pilha para uma GLC
 * 
 * */

public class Automato {
	
	//atributos
	private Pilha pilha = new Pilha();
	private Gramatica gramatica = new Gramatica();
	private Tabela tabela;		
	
	//construtores
	public Automato() {
		// TODO Auto-generated constructor stub
	}
	
	public Automato(Gramatica g) {
		// TODO Auto-generated constructor stub
		gramatica = g;
		tabela = new Tabela(gramatica);
		tabela.construirTabela();
	}
	
	//getters
	public Tabela getTabela() {
		return tabela;
	}
	
	public Pilha getPilha() {
		return pilha;
	}
	
	/**
	 * start<br>
	 * inicia a execucao do automato empilhando no inicio o $ e a variavel inicial da GLC
	 * 
	 * @param none
	 * @return none
	 * */
	public void start(){
		pilha.push('$');
		pilha.push(gramatica.variavelInicial());
	}
	
	/**
	 * executa<br>
	 * execucao o automato para uma palavra, retornando se a palavra é valida ou nao.
	 * 
	 * @param String
	 * @return boolean
	 * */
	public boolean executa(String palavra){
		//inicia o automato empilhando $ e a variavel inicial
		start();
		//concatenca o $ ao fim da palavra para indicar seu termino
		String s = palavra.concat("$");
		//posicao na palavra
		int pos = 0;
		//enquanto o topo da pilha nao for $, pq se for a pilha estara vazia
		while(!pilha.top().equals('$')){
			// char 'x' indica o elemento do topo da pilha e 'a' o char da posicao 'pos' da palavra
			Character x = (Character) pilha.top();
			Character a = s.charAt(pos);
			//caso o topo da pilha seja terminal ou $
			if (Producao.isTerminal(x) || x.equals('$')){
				//realiza os casamentos da pilha com entrada e desempilha e anda na entrada
				if (x.equals(a)){
					pilha.pop();
					pos++;
				}else{
					//error
					return false;
				}
			}else{
				//indices de 'x' e 'a' na tabela M[N,T] 
				int indexNT = gramatica.getNaoTerminais().indexOf(x)+1;
				int indexT = tabela.indexT(a);
				//se existe producao para a combinacao 'x' e 'a'
				if (tabela.getTabela().get(indexNT).get(indexT) != null){
					//desempilha 'x'
					pilha.pop();
					String producao = tabela.getTabela().get(indexNT).get(indexT).split("->")[1];
					//empilha a producao que se encontra na tabela.
					if (!producao.equals("E")){
						for (int i = producao.length()-1; i >= 0; i--) {
							pilha.push(producao.charAt(i));
						}
					}
				}else{
					//error
					return false;
				}
			}
		}
		
		//se ao terminar a execucao do laco a cima e, 'x'(topo da pilha) e 'a'(entrada) forem $ a palavra é aceita pelo automato 
		if (pilha.top().equals('$') && s.charAt(pos)=='$')
			return true;
		else{
			return false;
		}
	}

}
