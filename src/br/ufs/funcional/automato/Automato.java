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
	
	public Tabela getTabela() {
		return tabela;
	}
	
	public Pilha getPilha() {
		return pilha;
	}
	
	public Automato(Gramatica g) {
		// TODO Auto-generated constructor stub
		gramatica = g;
		tabela = new Tabela(gramatica);
		tabela.construirTabela();
	}
	
	public void start(){
		pilha.push('$');
		pilha.push(gramatica.variavelInicial());
	}
	

	public boolean executa(String palavra){
		start();
		String s = palavra.concat("$");
		int pos = 0;
		while(!pilha.top().equals('$')){
			Character x = (Character) pilha.top();
			Character a = s.charAt(pos);
			if (Producao.isTerminal(x) || x.equals('$')){
				if (x.equals(a)){
					pilha.pop();
					pos++;
				}else{
					return false;
				}
			}else{
				int indexNT = gramatica.getNaoTerminais().indexOf(x)+1;
				int indexT = tabela.indexT(a);
				
				if (tabela.getTabela().get(indexNT).get(indexT) != null){
					pilha.pop();
					String producao = tabela.getTabela().get(indexNT).get(indexT).split("->")[1];
					if (!producao.equals("E")){
						for (int i = producao.length()-1; i >= 0; i--) {
							pilha.push(producao.charAt(i));
						}
					}
				}else{
					return false;
				}
			}
		}
		if (pilha.top().equals('$') && s.charAt(pos)=='$')
			return true;
		else{
			return false;
		}
	}

}
