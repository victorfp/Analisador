package ufs.br.funcional;

/*
 * Class-name: Estado
 * version: 1.0.0
 * date: 21/12/2013
 * last-modifed: 23/12/2013
 * Autor: Victor Ferreira Pereira
 * Description: Classe que representa os Estados do Automato
 * 
 * */

public class Estado {

	private int id;
	private String label;
	
	/* Construtores */
	public Estado() {
		// TODO Auto-generated constructor stub
	}
	
	public Estado(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.label = String.valueOf(id);
	}
	
	public Estado(String label) {
		// TODO Auto-generated constructor stub
		this.label = label;
	}
	
	public Estado(int id,String label) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.label = label;
	}
	
	/* Metodos GETTERS e SETTERS */
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setId(int id) {
		this.id = id;
		this.label = String.valueOf(id);
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}
