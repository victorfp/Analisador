package ufs.br.grafico;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ufs.br.funcional.Main;

public class DrawAutomato extends JPanel{

	private ArrayList<JLabel> estado = new ArrayList<>();
	
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
		for (int i = 0; i < estado.size(); i++) {
			add(estado.get(i));
		}
		
		for (int i = 0; i < estado.size(); i++) {
			getComponent(i).move(getComponent(i).getX()*i*2, getComponent(i).getY());
		}
	}
	
	public void estados(){
		for (int i = 0; i < Main.automato.getQ().size(); i++) {
			estado.add(new JLabel("q"+Main.automato.getQ().get(i).getLabel()));
		}
	}
	
}
