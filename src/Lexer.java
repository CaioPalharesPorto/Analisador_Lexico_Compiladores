import java.io.*; 
import java.util.*;

public class Lexer {
    public static int line = 1;
    private final Reader in;
    private int ch = ' ';
    private final Map<String,Word> symtab = new HashMap<>();

    public Lexer(Reader r){
    this.in = r;
    // reserve palavras-chave
    reserve(new Word("app",   Tag.APP));
    reserve(new Word("int",   Tag.INT));
    reserve(new Word("real",  Tag.REAL));
    reserve(new Word("string",Tag.STRING));
    reserve(new Word("char",  Tag.CHAR));
    reserve(new Word("if",    Tag.IF));
    reserve(new Word("else",  Tag.ELSE));
    reserve(new Word("while", Tag.WHILE));
    reserve(new Word("do",    Tag.DO));
    reserve(new Word("scan",  Tag.SCAN));
    reserve(new Word("print", Tag.PRINT));
    }
    private void reserve(Word w){ symtab.put(w.lexeme, w); }

    private void readch() throws IOException { ch = in.read(); }
    private boolean readch(int c) throws IOException { readch(); if (ch != c) return false; ch=' '; return true; }

    public Token scan() throws IOException {
    // ignora espaços e conta linhas
    for(;; readch()){
    if (ch==' '||ch=='\t'||ch=='\r'||ch=='\f') continue;
    else if (ch=='\n') { 
        line++; 
    }
    else break;
    }

    // comentários //... até \n
    if (ch=='/'){

    
        if (readch('/')) { 
            while (ch!='\n' && ch!=-1) readch(); 
            return scan(); 
        }
    
        else return new Token(Tag.DIV);
    }

    // operadores compostos
    switch (ch){
    case '&': 
        if (readch('&')) 
            return new Word("&&", Tag.ANDAND); 
        else 
            return new Token('&');
    case '|': 
        if (readch('|')) 
            return new Word("||", Tag.OROR);   
        else 
            return new Token('|');
    case '=': 
        if (readch('=')) 
            return new Word("==", Tag.EQ);     
        else 
            return new Token(Tag.ASSIGN);
    case '!': 
        if (readch('=')) 
            return new Word("!=", Tag.NE);     
        else { int t=Tag.NOT; ch=' '; 
            return new Token(t); }
    case '<': 
        if (readch('=')) 
            return new Word("<=", Tag.LE);     
        else { int t=Tag.LT; ch=' '; 
            return new Token(t); 
        }
    case '>': 
        if (readch('=')) 
            return new Word(">=", Tag.GE);     
        else { int t=Tag.GT; ch=' '; 
            return new Token(t); 
        }
    }

    // pontuação de 1 char
    if ("+-*(),{};".indexOf(ch) >= 0){ 
        int t=ch; 
        ch=' '; 
        return new Token(t); 
    }

    // string literal: " ... "
    if (ch=='"'){

        StringBuilder sb = new StringBuilder(); readch();
    
        while (ch!='"' && ch!='\n' && ch!=-1){ 
            sb.append((char)ch); 
            readch(); 
        }
    
        if (ch=='"'){ 
            ch=' '; 
            return new Word(sb.toString(), Tag.LIT); 
        }
    
        throw new IOException("Linha "+line+": string não fechada");
    }

    // char: 'x'
    if (ch=='\''){

    
        readch();
    
        int c = ch;
    
        readch();
    
        if (ch=='\''){ 
            ch=' '; 
            return new Word(Character.toString((char)c), Tag.CHAR_CONST); 
        }
    
        throw new IOException("Linha "+line+": char inválido");
    }

    // número: inteiro ou float (com opcional '-')
    if (Character.isDigit(ch) || ch=='-'){
        StringBuilder sb = new StringBuilder();
        if (ch=='-'){ 
            sb.append((char)ch); 
            readch();

            if (!Character.isDigit(ch)) throw new IOException("Linha "+line+": '-' inesperado");

            }

        // parte inteira
        while (Character.isDigit(ch)){ 
            sb.append((char)ch); 
            readch(); 
        }
    
        // ponto e parte fracionária?
       // Para números decimais
        if (ch == '.') {
            sb.append('.'); 
            readch();

                if (!Character.isDigit(ch)) throw new IOException("Linha "+line+": float sem dígitos após '.'");

                while (Character.isDigit(ch)) {
                    sb.append((char)ch); 
                    readch();
                }
                
                // Verificação para conversão
                try {
                    String valueString = sb.toString();
                    if(!valueString.isEmpty()) {
                        float temp = Float.parseFloat(valueString);
                        return new FNum(temp);  
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Linha "+line+": valor float inválido");
                }
            } else {
                // Para inteiros
                String valueString = sb.toString();
                try {
                    if (!valueString.isEmpty()) {
                        int temp = Integer.parseInt(valueString);
                        return new Num(temp);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Linha "+line+": valor inteiro inválido");
                }
            }
    }

    // identificador ou palavra-chave
    if (Character.isLetter(ch) || ch=='_'){
    
        StringBuilder sb = new StringBuilder();
    
        do { 
            sb.append((char)ch); 
            readch(); 
        } while (Character.isLetterOrDigit(ch) || ch=='_');
    
        String s = sb.toString();
    
        Word w = symtab.get(s);
    
        if (w != null) return w;
    
        w = new Word(s, Tag.ID);
    
        symtab.put(s, w);
    
        return w;
    }

    // EOF?
    if (ch == -1) return null;

    // qualquer outro caractere: token unitário
    int t = ch; ch=' ';
    return new Token(t);
    }

    // para a Etapa 1: expose a symtab pra imprimir no final
    public Map<String,Word> symbols(){ return symtab; }
}