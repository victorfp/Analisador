package ufs.br.grafico;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ufs.br.funcional.Main;

public class DrawAutomato extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> estado = new ArrayList<>();
	private ArrayList<Elemento> elementos = new ArrayList<>();
	
	public DrawAutomato() {
		// TODO Auto-generated constructor stub
		setSize(500, 600);
		this.setVisible(true);
	}
	
	public ArrayList<JLabel> getEstado() {
		return estado;
	}
	
	public void draw(){
		estados();
		GridLayout gl = new GridLayout(10,5);
		gl.setHgap(2);
		gl.setVgap(2);
		setLayout(gl);
		for (int i = 0; i < estado.size(); i++) {		
			Elemento e = new Elemento(estado.get(i).getX()+20, estado.get(i).getY(), Main.automato.isFinal(Main.automato.getQ().get(i)), estado.get(i).getText());
			elementos.add(e);
			add(e);
		}
	}
	
	public void estados(){
		for (int i = 0; i < Main.automato.getQ().size(); i++) {
			estado.add(new JLabel("q"+Main.automato.getQ().get(i).getLabel()));
		}
	}
	
}
