package br.ufs.grafico;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Principal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Opcoes op = new Opcoes();
	public static DrawTabelas da = new DrawTabelas();
	private JScrollPane scroll = new JScrollPane(da);
	
	@SuppressWarnings("deprecation")
	public Principal() {
		// TODO Auto-generated constructor stub
		setTitle("Analisador Sintatico LL(1)");
		JPanel p = new JPanel();
		BorderLayout bl = new BorderLayout();
		da.setAutoscrolls(true);
		scroll.setPreferredSize(da.getPreferredSize());
		p.setLayout(bl);
		p.add(BorderLayout.WEST,op);
		p.add(scroll);
		
		this.add(p);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		show();
	}
	
}
