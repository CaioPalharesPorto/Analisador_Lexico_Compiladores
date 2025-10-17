# Analisador Léxico — Trabalho de Compiladores

Este projeto implementa um Analisador Léxico em Java, desenvolvido como parte da disciplina de Compiladores.  
O objetivo é reconhecer tokens válidos de uma linguagem fictícia semelhante a C, identificando palavras reservadas, identificadores, números inteiros e reais, strings, caracteres, operadores e pontuações.

---

## Estrutura do Projeto


---

## Funcionamento

1. O analisador lê o arquivo-fonte (ex: `Teste2.txt`).
2. O Lexer percorre o arquivo caractere por caractere.
3. Cada palavra, número ou símbolo é transformado em um Token.
4. Ao final, são exibidos:
   - A sequência de tokens reconhecidos;
   - A Tabela de Símbolos, contendo:
     - Palavras reservadas
     - Identificadores instalados

---

## Exemplo de Execução

### Código de entrada (`Teste7.txt`)
```java
app medidas

  real a, b, media, proporcao, negativo;

{
  print("Digite dois numeros reais: ");
  scan(a); scan(b);

  media = (a + b) / 2.0;
  proporcao = a / (b + 0.1);
  negativo = -3.75;
}

TOKENS:
app medidas real a , b , media , proporcao , negativo ; { print ( "Digite dois numeros reais: " ) ; scan ( a ) ; scan ( b ) ; media = ( a + b ) / 2.0 ; proporcao = a / ( b + 0.1 ) ; negativo = -3.75 ; }

TABELA DE SÍMBOLOS
Palavras-reservadas:
  app
  real
  print
  scan

Identificadores:
  medidas
  a
  b
  media
  proporcao
  negativo
