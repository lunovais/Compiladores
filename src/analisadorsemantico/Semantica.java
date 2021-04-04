package analisadorsemantico;

import analisadorlexico.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Semantica {

    private boolean argumento;
    private String mensagem;
    private boolean haErros;

    public Semantica(ArrayList<Token> listaTokens, boolean argumento) {
        mensagem = "";
        Collections.reverse(listaTokens);
        System.out.println("\n======================= Analise Semântica =======================\n");
        verificaVariaveisIguais(listaTokens);
        verificaVariavelDeclarada(listaTokens);
        verificaDivisaoPorZero(listaTokens);
        verificaTipo(listaTokens);

        this.setArgumento(argumento);
        this.mensagem();

    }

    public ArrayList<Token> retornaListaVariaveisDeclaradas(ArrayList<Token> listaTokens) {

        ArrayList<Token> variaveisDeclaradas;

        variaveisDeclaradas = new ArrayList<>();

        for (Token t : listaTokens) {
            if (t.getNome().equalsIgnoreCase("declaraint")) {
                int index = listaTokens.indexOf(t);
                variaveisDeclaradas.add(listaTokens.get(index + 1));

            }
        }       
       
        return variaveisDeclaradas;
      

    }

    public void verificaTipo (ArrayList<Token> listaTokens) {
        
        List<Token> listaVariaveis = 
                this.retornaListaVariaveisDeclaradas(listaTokens);
        
        for (Token t : listaTokens) {
            if (isOperadorMatematico(t)) {
                int index = listaTokens.indexOf(t);
                Token anterior = listaTokens.get(index-1);
                Token posterior = listaTokens.get(index+1);

                
                boolean declaradoAnterior = false;
                boolean declaradoPosterior = false;
                
                for(Token token : listaVariaveis) {
                     if (anterior.getConteudo().equals(token.getConteudo())) {
                         declaradoAnterior = true;
                     }
                     
                     if (posterior.getConteudo().equals(token.getConteudo())){
                         declaradoPosterior = true;
                         
                     }
                }
                
                if (!declaradoAnterior || !declaradoPosterior) {
                    this.setMensagem("Erro Semântico: Operação Incorreta "
                            + "na Linha: " + t.getLinha());
                }
                        
            
            }
        }
        
    }
    
    private boolean isOperadorMatematico (Token t){
        if (t.getNome().equals("soma") || t.getNome().equals("subtracao") ||
               t.getNome().equals("divisao") || t.getNome().equals("multiplicacao")){
            
            return true; 
        } else {
            return false;
        }
    }
    
    
    
    public void verificaDivisaoPorZero(ArrayList<Token> listaTokens) {

        for (Token t : listaTokens) {
            if (t.getNome().equals("divisao")) {
                int indexGeral = listaTokens.indexOf(t);

                if (listaTokens.get(indexGeral + 1).getConteudo().equals("0")) {
                    this.setMensagem("Erro semântico: Divisão por 0 na linha " + t.getLinha());

                }

                if (listaTokens.get(indexGeral + 1).getNome().equals("variavel")) {

                    ArrayList<Token> variaveisDeclaradas = this.retornaListaVariaveisDeclaradas(listaTokens);
                    String conteudo = "";

                    for (Token vD : variaveisDeclaradas) {
                        if (vD.getConteudo().equals(listaTokens.get(indexGeral + 1).getConteudo())) {
                            boolean iniciada = false;

                            for (Token t2 : listaTokens) {
                                if (t2.getNome().equals("variavel") && t2.getConteudo().equals(listaTokens.get(indexGeral + 1).getConteudo())) {

                                    int index2 = listaTokens.indexOf(t2);

                                    if (listaTokens.get(index2 + 1).getNome().equals("atribuicao")) {

                                        conteudo = listaTokens.get(index2 + 2).getConteudo();

                                    }
                                }
                            }

                        }   
                    }

                    if (conteudo.equals("0")) {
                        this.setMensagem("Erro semantico: Variável " + listaTokens.get(indexGeral + 1).getConteudo() + " na linha "
                                + listaTokens.get(indexGeral + 1).getLinha() + " possui valor igual a 0.");
                    }

                }

            }

        }

    }

    public void verificaVariavelDeclarada(ArrayList<Token> listaTokens) {

        ArrayList<Token> variaveisDeclaradas = this.retornaListaVariaveisDeclaradas(listaTokens);
        boolean declarada;
        for (Token t : listaTokens) {
            declarada = false;
            int index = listaTokens.indexOf(t);
            if (t.getNome().equals("variavel") && !listaTokens.get(index - 1).getNome().equals("declaraint")) {
                for (Token vD : variaveisDeclaradas) {
                    if (vD.getConteudo().equals(t.getConteudo())) {
                        declarada = true;
                    }
                }
                if (!declarada) {
                    this.setMensagem("Erro semântico: Variável " + t.getConteudo()
                            + " na linha " + t.getLinha() + " não foi declarada.");
                }
            }

        }

    }

    public void verificaVariaveisIguais(ArrayList<Token> listaTokens) {

        ArrayList<Token> variaveisDeclaradas = this.retornaListaVariaveisDeclaradas(listaTokens);

        ArrayList<Token> variaveisRepetidas;
        variaveisRepetidas = new ArrayList<>();
        for (int x = 0; x < variaveisDeclaradas.size(); x++) {
            for (int j = x + 1; j < variaveisDeclaradas.size(); j++) {
                if (variaveisDeclaradas.get(x).getConteudo().equals(variaveisDeclaradas.get(j).getConteudo())) {
                    if (!variaveisRepetidas.isEmpty()) {
                        Token tAux = new Token();
                        for (Token t : variaveisRepetidas) {
                            if (!variaveisDeclaradas.get(j).getConteudo().equals(t.getConteudo())) {
                                tAux = variaveisDeclaradas.get(j);
                                 variaveisRepetidas.add(tAux);
                            }
                        }

                       

                    } else {
                        variaveisRepetidas.add(variaveisDeclaradas.get(x));
                    }

                }
            }
        }
        if (!variaveisRepetidas.isEmpty()) {
            for (Token t : variaveisRepetidas) {

                this.setMensagem("Erro semântico : Variável duplicada: " + t.getConteudo() + " - Primeira Declaração: linha " + t.getLinha());
            }
        }

    }

    public void mensagem() {

        System.out.println("Verificação de declaração prévia de variáveis");      
        System.out.println("Verificação da declaração duplicada de variáveis");
        System.out.println("Verificação de divisão por zero");
        System.out.println("Verificação de tipos em operações lógicas e matemática com variáveis\n");

        if (!this.isHaErros()) {
            System.out.println("NÃO FORAM ENCONTRADOS ERROS SEMÂNTICOS\n");
        } else {
            if (this.getArgumento()) {
                System.out.println(this.getMensagem() + "\n");
            } else {
                System.out.println("ERROS SEMÂNTICOS ENCONTRADOS\n");
            }
        }
            System.out.println("==================== Fim da Analise Semântica ====================\n");
    }

    /**
     * @return the argumento
     */
    public boolean getArgumento() {
        return argumento;
    }

    /**
     * @param argumento the argumento to set
     */
    public void setArgumento(boolean argumento) {
        this.argumento = argumento;
    }

    /**
     * @return the mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(String mensagem) {
        this.setHaErros(true);
        if (this.mensagem == "") {
            this.mensagem = "======================= Especificação do Erro ====================\n\n" + mensagem;
        } else {
            this.mensagem += "\n" + mensagem;
        }
    }

    /**
     * @return the haErros
     */
    public boolean isHaErros() {
        return haErros;
    }

    /**
     * @param haErros the haErros to set
     */
    public void setHaErros(boolean haErros) {
        this.haErros = haErros;
    }

}