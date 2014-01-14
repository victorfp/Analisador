package ufs.br.funcional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ufs.br.grafico.Principal;

public class Main {

	public static Automato automato = new Automato();
	
	public static void main(String[] args) {
		String entrada = "C:\\Users\\Victor\\Desktop\\entrada.input";
		String saida = "C:\\Users\\Victor\\Desktop\\saida.output";
		ArrayList<String> leitura = new ArrayList<>();
		ArrayList<String> escrita = new ArrayList<>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(entrada));
			String buffer;
			while(br.ready()){
				buffer = br.readLine();
				if (buffer.length() == 0){
					break;
				}
				else{
					leitura.add(buffer);
				}
			}
			br.close();
		}catch(IOException e){
			System.out.println("Arquivo nao encontrado");
		}
		Construcao c = new Construcao();
		ER er = new ER(leitura.get(0));
		Automato r = c.construir(er);
		r = c.equalAF2AFN(r);
		for (int i = 1; i < leitura.size(); i++) {
			if(r.validar(leitura.get(i))){
				escrita.add(leitura.get(i)+" 1");
			}else{
				escrita.add(leitura.get(i)+" 0");
			}
		}
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(saida));
			for (int i = 0; i < escrita.size(); i++) {
				System.out.println(escrita.get(i));
				bw.write(escrita.get(i)+"\n");
			}
			bw.close();
		}catch(FileNotFoundException e){
			System.out.println("Arquivo não encontrado!!");
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("Erro na escrita");
		}
		
		Principal p = new Principal();
		
	}
	
}
