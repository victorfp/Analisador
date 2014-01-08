package ufs.br.funcional;

public class Transicao {

	private Estado origem;
	private Estado destino;
	private Character simbolo;
	
	/* Construtores */
	public Transicao() {
		// TODO Auto-generated constructor stub
	}
	
	public Transicao(Estado origem, Estado destino, Character simbolo) {
		// TODO Auto-generated constructor stub
		this.origem = origem;
		this.destino = destino;
		this.simbolo = simbolo;
	}
	
	/* Metodos GETTERS e SETTERS */
	public Estado getOrigem() {
		return origem;
	}
	
	public Estado getDestino() {
		return destino;
	}
	
	public Character getSimbolo() {
		return simbolo;
	}
	
	public void setOrigem(Estado origem) {
		this.origem = origem;
	}
	
	public void setDestino(Estado destino) {
		this.destino = destino;
	}
	
	public void setSimbolo(Character simbolo) {
		this.simbolo = simbolo;
	}
}
