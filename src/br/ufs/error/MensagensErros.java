package br.ufs.error;

/*
 * class MensagensErros
 * autor: Victor Ferreira Pereira
 * 
 * descricao: constantes com as mensagens que serao exibidas caso algum erro ocorra
 * durante a execucao.
 * 
 * */

public class MensagensErros {

	//caso a gramatica esteja mal formada
	public static final String MSG_MALFORMADA = "Gramatica Mal Formada";
	//caso a gramatica possui espaço em branco
	public static final String MSG_GRAMATICAESPACOS = "Gramatica possui espacos em brancos";
	//caso a gramatica passada seja ambigua
	public static final String MSG_GRAMATICAAMBIGUA = "Gramatica Ambigua";
	//caso nao tenha sido gerado o automato para a gramatica
	public static final String MSG_NULLGRAMATICA = "Gramatica Não Gerada";
}
