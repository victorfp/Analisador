package br.ufs.grafico;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.ufs.error.GramaticaMalFormadaException;
import br.ufs.error.MensagensErros;
import br.ufs.funcional.Main;
import br.ufs.funcional.automato.Automato;
import br.ufs.funcional.gramatica.Gramatica;

public class Opcoes extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel grammar = new JLabel("Gramática: ",JLabel.LEFT);
	private JTextField grammar_edit = new JTextField();
	private JButton btn_gerar = new JButton("Gerar");
	private JButton btn_limpar = new JButton("Limpar");
	private JLabel palavras = new JLabel("Palavras");
	private JTextArea ta = new JTextArea();
	private JButton btn_validar = new JButton("Validar");
	private JScrollPane scroll = new JScrollPane(ta);
	
	
	public Opcoes(){
		// TODO Auto-generated constructor stub
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS); 
		this.setLayout(layout);
		grammar_edit.setColumns(20);
		ta.setAutoscrolls(true);
		ta.setRows(30);
		
		JPanel p = new JPanel();
		p.add(btn_gerar);
		p.add(btn_limpar);
		p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
		scroll.setPreferredSize(ta.getPreferredSize());
		
		this.add(grammar);
		this.add(grammar_edit);
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
				if (grammar_edit.getText().length() == 0){
					JOptionPane.showMessageDialog(null, "Insira uma Gramatica Livre de Contexto.");
				}else{
					String texto = grammar_edit.getText();
					if (!texto.contains(" ")){
						if (!GramaticaMalFormadaException.isMalFormada(texto)){
							try{
							Gramatica g = new Gramatica(texto);
							Main.automato = new Automato(g);
							gerarTabelas();
							}catch(StackOverflowError error){
								JOptionPane.showMessageDialog(null, MensagensErros.MSG_GRAMATICAAMBIGUA,"ERROR", JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(null, MensagensErros.MSG_MALFORMADA,"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, MensagensErros.MSG_GRAMATICAESPACOS,"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btn_limpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.automato = null;
				Principal.da.getPanel().removeAll();
				
				Principal.da.updateUI();
				ta.setText("");
				grammar_edit.setText("");
			}
		});
		
		btn_validar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (Main.automato != null){
					try{
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
							if (Main.automato.executa(palavras.get(i))){
								texto += "1: "+palavras.get(i)+"\n";
							}else{
								texto+= "0: "+palavras.get(i)+"\n";
							}
						}
						ta.setText(texto);
					}
					catch(StackOverflowError error){
						JOptionPane.showMessageDialog(null, MensagensErros.MSG_GRAMATICAAMBIGUA,"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, MensagensErros.MSG_NULLGRAMATICA,"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	
	public String[] coletaNomesPrimeiro(int linha){
		String[] nomes = new String[linha-1];
		for (int i = 0; i < linha-1; i++) {
			if (Main.automato.getTabela().getTabela().get(i+1).get(0)!= null)
				nomes[i] = Main.automato.getTabela().getTabela().get(i+1).get(0).toString();
			else{
				nomes[i] = " ";
			}
		}
		return nomes;
	}
	
	public String[] coletaNomes(int coluna){
		String[] nomes = new String[coluna];
		for (int i = 0; i < coluna; i++) {
			if (Main.automato.getTabela().getTabela().get(0).get(i)!= null)
				nomes[i] = Main.automato.getTabela().getTabela().get(0).get(i).toString();
			else{
				nomes[i] = " ";
			}
		}
		return nomes;
	}
	
	public Object[][] coletaDadosPrimero(int linha, int coluna){
		Object[][] dados = new Object[coluna][linha-1];
		for (int i = 0; i < linha-1; i++) {
			for (int j = 0; j < Main.automato.getTabela().getPrimeiros().get(i).size(); j++) {
				if (Main.automato.getTabela().getPrimeiros().get(i).get(j)!= null)
					dados[j][i] =  Main.automato.getTabela().getPrimeiros().get(i).get(j);
				else{
					dados[j][i] = " ";
				}
			}
		}
		return dados;
	}
	
	public Object[][] coletaDadosSeguinte(int linha, int coluna){
		Object[][] dados = new Object[coluna][linha-1];
		for (int i = 0; i < linha-1; i++) {
			for (int j = 0; j < Main.automato.getTabela().getSeguintes().get(i).size(); j++) {
				if (Main.automato.getTabela().getSeguintes().get(i).get(j)!= null)
					dados[j][i] =  Main.automato.getTabela().getSeguintes().get(i).get(j);
				else{
					dados[j][i] = " ";
				}
			}
		}
		return dados;
	}
	
	public Object[][] coletaDados(int linha, int coluna){
		Object[][] dados = new Object[linha-1][coluna];
		for (int i = 1; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				if (Main.automato.getTabela().getTabela().get(i).get(j)!= null)
					dados[i-1][j] =  Main.automato.getTabela().getTabela().get(i).get(j);
				else{
					dados[i-1][j] = " ";
				}
			}
		}
		return dados;
	}
	
	public void gerarTabelas(){
		int linha = Main.automato.getTabela().getTabela().size();
		int coluna = Main.automato.getTabela().getTabela().get(0).size();
		String[] nomes = coletaNomes(coluna);
		String[] nomesConjuntos = coletaNomesPrimeiro(linha);
		
		Object[][] dados = coletaDados(linha, coluna);
		Object[][] dadosP = coletaDadosPrimero(linha, coluna);
		Object[][] dadosS = coletaDadosSeguinte(linha, coluna);
		
		JLabel lblPrimeiro = new JLabel("Primeiro");
		JLabel lblSeguinte = new JLabel("Sequencia");
		JLabel lblTabela = new JLabel("Tabela");
		
		JTable t1 = new JTable(dados,nomes);
		JTable t2 = new JTable(dadosP,nomesConjuntos);
		JTable t3 = new JTable(dadosS,nomesConjuntos);
		JScrollPane tabela = new JScrollPane(t1);
		JScrollPane primeiro = new JScrollPane(t2);
		JScrollPane seguinte = new JScrollPane(t3);
		primeiro.setPreferredSize(new Dimension(400, 100));
		seguinte.setPreferredSize(new Dimension(400, 100));
		tabela.setPreferredSize(new Dimension(400, 200));
		Principal.da.getPanel().add(lblTabela);
		Principal.da.getPanel().add(tabela);
		Principal.da.getPanel().add(lblPrimeiro);
		Principal.da.getPanel().add(primeiro);
		Principal.da.getPanel().add(lblSeguinte);
		Principal.da.getPanel().add(seguinte);
		Principal.da.updateUI();
	}
	
}
