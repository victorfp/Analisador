Analisador
==========

Trabalho 01 - Linguagens Formais e Computabilidade - Victor Ferreira Pereira

Autor(s): Victor Ferreira Pereira
version: 1.2.4
created: 15 dez 2013
last-modified: 12 jan 2014

===========

1. Introdução
    
     Trata-se de um programa que recebe como entrada uma Expressão Regular na pri-
meira linha e palavras nas linhas seguintes. A entrada termina com uma linha em 
branco. Perceba que haverá exatamente uma palavra por linha na entrada. A entrada 
deve ser lida da entrada padrão.

2. Executar o Programa

    Para executar o programa, basta apenas modificar as variaveis "entrada" e "saida",    
na Classe Main, para o diretorio onde os arquivo estao localizados.

//diretorio do arquivo de entrada
String entrada = path_name;

//diretorio em sera gerado a saida
String saida = path_name;

    2.1 Formato da Entrada
        
        Como foi especificado, a primeira linha do arquivo será a Expressao Regular, as
    demais serão as palavras que serão validadas.
    Exemplo
    File.input:
    
    1. (0.1)*
    2. 111010
    3. 010101
    4. E
    5. 00100110
    .
    .
    .
    
    2.2 Formato da Saida
    
        na saida será escrita se a palavra foi aceita ou nao. Se foi aceita, entao será 
    escrito "1", caso contrario escreve-se "0".
    Exemplo:
    File.output
    1. 0
    2. 1
    3. 1
    4. 0
    .
    .
    .
    
