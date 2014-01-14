package ufs.br.grafico;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ufs.br.funcional.Construcao;
import ufs.br.funcional.ER;
import ufs.br.funcional.Main;

public class Opcoes extends JPanel{
	
	private JLabel er = new JLabel("Expressão Regular",JLabel.LEFT);
	private JTextField er_edit = new JTextField();
	private JButton btn_gerar = new JButton("Gerar");
	private JButton btn_limpar = new JButton("Limpar");
	private JLabel palavras = new JLabel("Palavras");
	private JTextArea ta = new JTextArea();
	private JButton btn_validar = new JButton("Validar");
	private JTable tabela = new JTable(1,2);
	private JScrollPane scroll = new JScrollPane(ta);
	
	
	public Opcoes() {
		// TODO Auto-generated constructor stub
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS); 
		this.setLayout(layout);
		er_edit.setColumns(20);
		ta.setAutoscrolls(true);
		ta.setRows(30);
		
		JPanel p = new JPanel();
		p.add(btn_gerar);
		p.add(btn_limpar);
		p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
		scroll.setPreferredSize(ta.getPreferredSize());
		
		this.add(er);
		this.add(er_edit);
		this.add(p);
		this.add(palavras);
		this.add(scroll);
		this.add(btn_validar);
		this.setSize(300,600);
		
		//eventos
		btn_gerar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String texto = er_edit.getText() ;
				Construcao c = new Construcao();
				Main.automato = c.construir(new ER(texto));
				Principal.da.draw();
				Principal.da.updateUI();
			}
		});
		
		btn_limpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.automato = null;
				ta.setText("");
				er_edit.setText("");
				Principal.da.removeAll();
				Principal.da.getEstado().clear();
				Principal.da.updateUI();
				
			}
		});
		
		btn_validar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> palavras = new ArrayList<>();
				
				String texto = ta.getText() ;  
				StringTokenizer st = new StringTokenizer(texto,"\n") ;  
				
				while (st.hasMoreTokens())   
				{  
				     String line = st.nextToken();  
				     palavras.add(line);  
				}
				texto = "";
				for (int i = 0; i < palavras.size(); i++) {
					if (Main.automato.validar(palavras.get(i))){
						texto += "1: "+palavras.get(i)+"\n";
					}else{
						texto+= "0: "+palavras.get(i)+"\n";
					}
				}
				ta.setText(texto);
			}
		});
	}
	
}
