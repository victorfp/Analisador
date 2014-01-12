package ufs.br.funcional;

import java.util.ArrayList;

public class ER {

	private String er;
	private ArrayList<String> elementos = new ArrayList<>();
	private ArrayList<Character> operadores = new ArrayList<>();
	public ER(String er){
		this.er = er;
	}
	
	public String getEr() {
		return er;
	}
	
	public ArrayList<String> getElementos() {
		return elementos;
	}
	
	public ArrayList<Character> getOperadores() {
		return operadores;
	}
	
	public void setEr(String er) {
		this.er = er;
	}
	
	public void separar(){
		if (er.length() == 1){
			elementos.add(er);
		}else{
			for (int i = 0; i < er.length(); i++) {
				if ((er.charAt(i) == '0') || (er.charAt(i) == '1')){
					if (i < er.length()-1){
						if (er.charAt(i+1) == '*'){
							elementos.add(""+er.charAt(i)+er.charAt(i+1));
							i++;
						}else{
							elementos.add(""+er.charAt(i));
						}
					}else{
						elementos.add(""+er.charAt(i));
					}
				}else{
					if (er.charAt(i) == '('){
						String data = "(";
						i++;
						while(er.charAt(i) != ')'){
							data += er.charAt(i);
							i++;
						}
						data+=")";
						if (i < er.length()-1){
							if (er.charAt(i+1) == '*'){
								++i;
								data += er.charAt(i);
							}
						}
						elementos.add(data);
					}else{
						operadores.add(er.charAt(i));
					}
				}
			}
		}
	}
	
}
