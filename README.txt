Analisador
==========

Trabalho 01 - Linguagens Formais e Computabilidade - Victor Ferreira Pereira

Autor(s): Victor Ferreira Pereira
version: 1.0.5
created: 15 dez 2013
last-modified: 12 jan 2014

===========

1. Introdução
    
     Trata-se de um programa que recebe como entrada uma Expressão Regular na pri-
meira linha e palavras nas linhas seguintes. A entrada termina com uma linha em 
branco. Perceba que haverá exatamente uma palavra por linha na entrada. A entrada 
deve ser lida da entrada padrão.

2. Executar o Programa

    Para executar o programa, basta executar o arquivo analisador.jar.

3. Instrucoes Analisador.jar

   Segue o passo a passo para geracao do automato e validacao das palavras no Ana-
lisador.jar.

   Passo 1: Gerar o Automato
	
	Para se gerar o Automato, deve inserir a Expressão Regular(ER) no campo de texto,
   e clicar no botão gerar Automato.
   
   IMPORTANTE: As ERs devem seguir o padrão especificado.
	       Operacoes suportadas: concatenacao(.), uniao(+) e Fecho-kleene ou estrela(*).

   Passo 2: Inserir Palavras para validacao
	
	As palavras devem ser inseridas na Area de Texto. UMA PALAVRA POR LINHA! As palavras
   não devem ser separadas pos vírgula(,) ou qualquer outro caracter especal.

   Passo 3: validacao

	Para validar as palavras inseridas no passo anterior, basta clicar no botão validar.
   será apresentado como resultado, na area de texto, o seguinte padrao de saida:

   result: palavra

   onde, result é o resultado da validacao e palavra é a palavra avaliada. Exemplo:
   Para a ER = 1.0

   0: 1001
   1: 10
   0: 0
   0: 1
   .
   .
   .


   Para gerar outro Automato para um ER diferente, deve-se apenas clicar no botão "limpar" e 
   realizar os passos acima citados.