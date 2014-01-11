package ufs.br.funcional;

public class ER {

	private String er;
	
	public ER(String er){
		this.er = er;
	}
	
	public String getEr() {
		return er;
	}
	
	public void setEr(String er) {
		this.er = er;
	}
	
	public String atualizar(){
		String e = "";
		
		for (int i = 0; i < er.length(); i++) {
			if((er.charAt(i) == '0') || (er.charAt(i) == '1')){
				e += er.charAt(i);
			}else{
				e = er.charAt(i) + e;
			}
		}
		return e;
	}
	
}
