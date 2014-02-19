package br.ufs.funcional.automato;

import java.util.ArrayList;

public class Pilha{

	ArrayList<Object> pilha = new ArrayList<Object>();
	
	public Pilha() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void push(Object o){
		pilha.add(0,o);
	}
	
	public Object top(){
		return pilha.get(0);
	} 
	
	public Object pop(){
		Object o = pilha.get(0);
		pilha.remove(0);
		return o;
	}
	
	public int size(){
		return pilha.size();
	}
	
	public boolean isEmpty(){
		return pilha.isEmpty();
	}
	
	public ArrayList<Object> getPilha() {
		return pilha;
	}
}
