package ufs.br.grafico;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Principal extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Opcoes op = new Opcoes();
	public static DrawAutomato da = new DrawAutomato();
	private JScrollPane scroll = new JScrollPane(da);
	
	@SuppressWarnings("deprecation")
	public Principal() {
		// TODO Auto-generated constructor stub
		JPanel p = new JPanel();
		da.setAutoscrolls(true);
		scroll.setPreferredSize(da.getPreferredSize());
		BorderLayout bl = new BorderLayout();
		p.setLayout(bl);
		p.add(BorderLayout.WEST,op);
		p.add(scroll);
		this.add(p);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		show();
	}
	
}
