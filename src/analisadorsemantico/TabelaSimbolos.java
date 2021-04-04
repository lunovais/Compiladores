package analisadorsemantico;

import analisadorlexico.Token;
import java.util.ArrayList;
import java.util.List;


public class TabelaSimbolos {
    
    private List<Token> tabelaSimbolos;

    public TabelaSimbolos() {
        tabelaSimbolos = new ArrayList<>();
    }
    
    public int insereNome(String nome, String conteudo){
        if (JaDeclarada(nome)){
            throw new RuntimeException("Erro Semântico: Variavel"+ nome +"ja"
                    + "declarada");
        }
        Token tk = new Token();
        tk.getNome();
        tk.getConteudo();
        tabelaSimbolos.add(tk);
        return tabelaSimbolos.size()-1;
    }
    
    public String determinaTipo(String nome){
        for(Token tk : tabelaSimbolos)
            if(tk.getNome().equals(nome)){
                return tk.getConteudo();
            }
        return null;
    }

    private boolean JaDeclarada(String nome) {
        for(Token tk : tabelaSimbolos)
            if(tk.getNome().equals(nome)){
                return true;
            }
        return false;
    }
    
    

}