package br.ufs.funcional.gramatica;

import java.util.ArrayList;

/*
 * class Gramatica
 * autor: Victor Ferreira Pereira
 * 
 * descricao: representacao de uma GLC(Gramatica Livre de Contexto).
 * 
 * */
public class Gramatica {

	//atributos
	private ArrayList<Character> nao_terminais = new ArrayList<Character>();
	private ArrayList<Character> terminais = new ArrayList<Character>();
	private ArrayList<Producao> producoes = new ArrayList<Producao>();
	
	public Gramatica() {
		// TODO Auto-generated constructor stub
	}
	
	public Gramatica(String grammar){
		for (int i = 0; i < grammar.split(",").length; i++) {
			producoes.add(new Producao(grammar.split(",")[i]));
		}
		coletaNaoTerminais();
		coletaTerminais();
	}
	
	public void coletaNaoTerminais(){
		for (int i = 0; i < producoes.size(); i++) {
			if (!nao_terminais.contains(producoes.get(i).getVariavel())){
				nao_terminais.add(producoes.get(i).getVariavel());
			}
		}
	}
	
	public void coletaTerminais(){
		ArrayList<Character> iguais = new ArrayList<Character>();
		for (int i = 0; i < producoes.size(); i++) {
			iguais.addAll(terminais);
			iguais.retainAll(producoes.get(i).getTerminais());
			terminais.addAll(producoes.get(i).getTerminais());
			terminais.removeAll(iguais);
			terminais.addAll(iguais);
			iguais.clear();
		}
	}
	
	public Character variavelInicial(){
		return nao_terminais.get(0);
	}
	
	public ArrayList<Producao> getProducoes() {
		return producoes;
	}
	
	public ArrayList<Producao> getProducoes(Character c) {
		ArrayList<Producao> produz = new ArrayList<Producao>();
		for (int i = 0; i < producoes.size(); i++) {
			if (producoes.get(i).getVariavel().equals(c)){
				produz.add(producoes.get(i));
			}
		}
		return produz;
	}
	
	public ArrayList<Producao> getContido(Character c) {
		ArrayList<Producao> produz = new ArrayList<Producao>();
		for (int i = 0; i < producoes.size(); i++) {
			if (producoes.get(i).getBehavior().contains(c.toString())){
				produz.add(producoes.get(i));
			}
		}
		return produz;
	}
	
	public ArrayList<Producao> getNaoProduz(Character c) {
		ArrayList<Producao> produz = new ArrayList<Producao>();
		for (int i = 0; i < producoes.size(); i++) {
			if (!producoes.get(i).getVariavel().equals(c)){
				produz.add(producoes.get(i));
			}
		}
		return produz;
	}
	
	public ArrayList<Character> getNaoTerminais() {
		return nao_terminais;
	}
	
	public ArrayList<Character> getTerminais() {
		return terminais;
	}
	
	public void setProducoes(ArrayList<Producao> producoes) {
		this.producoes = producoes;
	}
	
	public void setNaoTerminais(ArrayList<Character> nao_terminais) {
		this.nao_terminais = nao_terminais;
	}
	
	public void setTerminais(ArrayList<Character> terminais) {
		this.terminais = terminais;
	}
	
	public boolean existe(Producao p){
		for (int i = 0; i < producoes.size(); i++) {
			if (producoes.get(i).getProducao().equals(p.getProducao())){
				return true;
			}
		}
		return false;
	}
}
