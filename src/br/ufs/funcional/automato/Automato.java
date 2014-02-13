package br.ufs.funcional.automato;

import br.ufs.funcional.gramatica.Gramatica;
import br.ufs.funcional.gramatica.Producao;

public class Automato {
	
	private Pilha pilha = new Pilha();
	private Gramatica gramatica = new Gramatica();
	private Tabela tabela;		
	public Automato() {
		// TODO Auto-generated constructor stub
	}
	
	public Pilha getPilha() {
		return pilha;
	}
	
	public Automato(Gramatica g) {
		// TODO Auto-generated constructor stub
		gramatica = g;
		
	}
	
	public void start(){
		pilha.push('$');
		pilha.push(gramatica.variavelInicial());
	}
	
	public boolean executa(String palavra){
		start();
		tabela = new Tabela(gramatica);
		tabela.construirTabela();
		return true;
	}

}
