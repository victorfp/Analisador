package br.ufs.grafico;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class DrawTabelas extends JPanel{

	private JPanel panel = new JPanel();
	public DrawTabelas() {
		// TODO Auto-generated constructor stub
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		add(panel);
		show();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
}
