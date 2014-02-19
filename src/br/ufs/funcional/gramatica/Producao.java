package br.ufs.funcional.gramatica;

import java.util.ArrayList;

/**
 * <b>Classe Producao</b>
 * @author Victor Ferreira
 * @version 1.0
 * 
 * */
public class Producao {

	private String producao;
	
	public Producao() {
		// TODO Auto-generated constructor stub
	}
	
	public Producao(String p) {
		// TODO Auto-generated constructor stub
		producao = p;
	}

	/**
	 * 	<b>getVariavel</b>
	 * 	<br>
	 * 		Pega o simbola NAO-TERMINAL(Variavel) a direita.
	 * 
	 * @param none
	 * @return Character
	 * 
	 * */
	public Character getVariavel(){
		return producao.charAt(0);
	}
	
	/**
	 * 	<b>getBehavior</b>
	 * 	<br>retorna a cadeia de caracter a esquerda da producao.
	 * 		
	 * @param none
	 * @return String
	 * 
	 * */
	public String getBehavior(){
		if (producao.contains("->"))
			return producao.split("->")[1];
		else
			return "";
	}
	
	/**
	 * 	<b>isTerminal</b>
	 * 	<br>verifica se um caracter é um simbolo TERMINAL.
	 * 		
	 * @param Character c
	 * @return Boolean
	 * 
	 * */
	
	public static boolean isTerminal(Character c){
		if (Character.isLowerCase(c) || !Character.isLetter(c))
			return true;
		else
			return false;
	}
	
	/**
	 * 	<b>getTerminais</b>
	 * 	<br>retorna todos os terminais da producao.
	 * 		
	 * @param none
	 * @return List
	 * 
	 * */
	
	public ArrayList<Character> getTerminais(){
		ArrayList<Character> r = new ArrayList<Character>();
		String term = producao.split("->")[1];
		for (int i = 0; i < term.length(); i++) {
			if (!r.contains(term.charAt(i))){
				if (Producao.isTerminal(term.charAt(i))){
					r.add(term.charAt(i));
				}
			}
		}
		return r;
	}
	
	public String getProducao() {
		return producao;
	}
	
	public void setProducao(String producao) {
		this.producao = producao;
	}
}
