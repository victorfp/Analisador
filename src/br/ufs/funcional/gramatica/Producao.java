package br.ufs.funcional.gramatica;

import java.util.ArrayList;


public class Producao {

	private String producao;
	
	public Producao() {
		// TODO Auto-generated constructor stub
	}
	
	public Producao(String p) {
		// TODO Auto-generated constructor stub
		producao = p;
	}
	
	public Character getVariavel(){
		return producao.charAt(0);
	}
	
	public String getBehavior(){
		if (producao.contains("->"))
			return producao.split("->")[1];
		else
			return "";
	}
	
	public boolean isEpsilon(){
		return producao.endsWith("->E");
	}
	
	public static boolean isTerminal(Character c){
		if (Character.isLowerCase(c) || !Character.isLetter(c))
			return true;
		else
			return false;
	}
	
	public ArrayList<Character> getTerminais(){
		ArrayList<Character> r = new ArrayList<>();
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
