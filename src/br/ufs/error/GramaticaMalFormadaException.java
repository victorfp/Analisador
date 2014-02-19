package br.ufs.error;

public class GramaticaMalFormadaException{

	public GramaticaMalFormadaException(){	
	}
	
	public static boolean hasSeta(String s){
		if (s.contains("->"))
			return true;
		return false;
	}
	
	public static boolean isMalFormada(String t){
		for (int i = 0; i < t.split(",").length; i++) {
			String s = t.split(",")[i];
			if (!hasSeta(s)){
				return true;
			}else{
				if (s.split("->")[0].length() != 1){
					return true;
				}else{
					if(!Character.isLetter(s.split("->")[0].charAt(0)) || !Character.isUpperCase(s.split("->")[0].charAt(0))){
						return true;
					}
				}
			}
		}
		return false;
	}
}
