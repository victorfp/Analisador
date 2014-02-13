package br.ufs.funcional;


import br.ufs.funcional.automato.Automato;
import br.ufs.funcional.gramatica.Gramatica;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Gramatica g = new Gramatica("A->TB,B->+TB,B->E,T->FC,C->*FC,C->E,F->(A),F->i");
		//Tabela t = new Tabela(g);
		Automato a = new Automato(g);
		System.out.println(a.executa("i+i*i"));
		//t.construirTabela();
		
	}

}
