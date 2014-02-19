package br.ufs.funcional.automato;

import java.util.ArrayList;

/*
 * class Pilha
 * autor: Victor Ferreira Pereira
 * 
 * descricao: pilha para a execucao do automato
 * 
 * */

public class Pilha{

	ArrayList<Object> pilha = new ArrayList<Object>();
	
	public Pilha() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * push
	 * <br>metodo que empilha um objeto na pilha
	 * 
	 * @param none
	 * @return none
	 * 
	 * */
	public void push(Object o){
		pilha.add(0,o);
	}
	
	/**
	 * top
	 * <br>metodo que exibe o elemento do topo da pilha, mas nao desempilha este.
	 * 
	 * @param none
	 * @return Object
	 * 
	 * */
	public Object top(){
		return pilha.get(0);
	} 
	
	/**
	 * pop
	 * <br>metodo que desempilha o elemento do topo da pilha.
	 * 
	 * @param none
	 * @return Object
	 * 
	 * */
	public Object pop(){
		Object o = pilha.get(0);
		pilha.remove(0);
		return o;
	}
	
	/**
	 * size
	 * <br>tamanho da pilha.
	 * 
	 * @param none
	 * @return int
	 * 
	 * */
	public int size(){
		return pilha.size();
	}
	
	/**
	 * isEmpty
	 * <br>metodo que veriica se a pilha esta vazia.
	 * 
	 * @param none
	 * @return boolean
	 * 
	 * */
	public boolean isEmpty(){
		return pilha.isEmpty();
	}
	
	/**
	 * getPilha
	 * <br>metodo pega a referencia para a pilha.
	 * 
	 * @param none
	 * @return List
	 * 
	 * */
	public ArrayList<Object> getPilha() {
		return pilha;
	}
}
