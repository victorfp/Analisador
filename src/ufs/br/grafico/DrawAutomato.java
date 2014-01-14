package ufs.br.grafico;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ufs.br.funcional.Main;

public class DrawAutomato extends JPanel{

	private ArrayList<JLabel> estado = new ArrayList<>();
	private JLabel inicial = new JLabel();
	
	
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
		
	}
	
	public void estados(){
		for (int i = 0; i < Main.automato.getQ().size(); i++) {
			estado.add(new JLabel("q"+Main.automato.getQ().get(i).getLabel()));
		}
	}
	
}
