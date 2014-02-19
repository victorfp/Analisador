package br.ufs.error;


/*
 * class GramaticaMalFormadaException
 * autor: Victor Ferreira Pereira
 * 
 * descricao: verifica se a gramatica passada esta conforme especificado.
 * 
 * */

public class GramaticaMalFormadaException{

	public GramaticaMalFormadaException(){	
	}
	
	/*
	 * metodo hasSeta
	 * 
	 * verifica se uma palavra s passada possui seta do tipo ->
	 * 
	 * */
	public static boolean hasSeta(String s){
		if (s.contains("->"))
			return true;
		return false;
	}
	
	/*
	 * Metodo isMalFormada
	 * 
	 * verifica se a gramtica esta mal formada se sim return true senao,
	 * a gramatica esta correta, entao returna false.
	 * 
	 * */
	public static boolean isMalFormada(String t){
		//separa a gramatica t pelas ','
		for (int i = 0; i < t.split(",").length; i++) {
			String s = t.split(",")[i];
			//para cada producao verifica se tem seta
			//se nao tiver seta esta mal formada 
			if (!hasSeta(s)){
				return true;
			}else{
				//se o lado direito nao possui somente 1 elemento esta incorreta
				if (s.split("->")[0].length() != 1){
					return true;
				}else{
					//se este character não é Terminal a gramatica esta mal formada  
					if(!Character.isLetter(s.split("->")[0].charAt(0)) || !Character.isUpperCase(s.split("->")[0].charAt(0))){
						return true;
					}else{
						//caso o algum lado da producao estiver vazio esta errada a gramatica
						if(s.split("->").length <= 1){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
