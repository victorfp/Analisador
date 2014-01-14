package ufs.br.grafico;

import java.awt.BorderLayout;
import java.awt.Color;


import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Principal extends JFrame{

	private Opcoes op = new Opcoes();
	public static DrawAutomato da = new DrawAutomato();
	public Principal() {
		// TODO Auto-generated constructor stub
		JPanel p = new JPanel();
		BorderLayout bl = new BorderLayout();
		p.setLayout(bl);
		p.add(BorderLayout.WEST,op);
		p.add(BorderLayout.EAST,da);
		this.add(p);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		show();
	}
	
}
