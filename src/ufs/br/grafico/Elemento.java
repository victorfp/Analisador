package ufs.br.grafico;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Elemento extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private boolean _final;
	private String elemento;
	
	public Elemento(int x, int y, boolean _final, String s) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this._final = _final;
		elemento = s;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawOval(x, y, 50, 50);
		if (_final){
			g.drawOval(x+3, y+3, 44, 44);
		}
		g.drawString(elemento, x+20, y+27);
	}
}
