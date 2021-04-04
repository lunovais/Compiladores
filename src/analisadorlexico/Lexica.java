package analisadorlexico;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexica {

    ArrayList<String[]> palavras;
    ArrayList<Token> listaTokens;
    ArrayList<TokenErro> listaTokensErros;
    boolean statusString = false;
    private boolean haErros;

    public Lexica() {
        palavras = new ArrayList<>();
        listaTokens = new ArrayList<>();
        listaTokensErros = new ArrayList<>();
        this.haErros = false;
    }

    public void analiseLexica(ArrayList<String> linhas, boolean argumento) {

        System.out.println("\n========================= Analise Léxica =========================");

        for (String s : linhas) {

            if (!s.contains("\"")) {
                palavras.add(s.split(" "));
            } else {
                String[] linhaComAspas = s.split(" ");
                String variavel = "";
                int posicaoAInserir = 0;
                for (int x = 0; x < linhaComAspas.length; x++) {

                    if (linhaComAspas[x].startsWith("\"") && !linhaComAspas[x].endsWith("\"")) {
                        statusString = true;
                        posicaoAInserir = x;
                        variavel += linhaComAspas[x];

                        linhaComAspas = removeDoVetor(linhaComAspas[x], linhaComAspas);
                        while (statusString) {
                            if (!linhaComAspas[x].endsWith("\"")) {
                                variavel += " " + linhaComAspas[x];
                                linhaComAspas = removeDoVetor(linhaComAspas[x], linhaComAspas);

                            } else {
                                variavel += " " + linhaComAspas[x];
                                linhaComAspas = removeDoVetor(linhaComAspas[x], linhaComAspas);
                                statusString = false;

                            }
                        }

                    } else if (linhaComAspas[x].startsWith("\"") && linhaComAspas[x].endsWith("\"")) {
                        linhaComAspas = s.split(" ");

                    }

                }

                ArrayList<String> listaTemporaria = new ArrayList<>(Arrays.asList(linhaComAspas));
                listaTemporaria.add(posicaoAInserir, variavel);
                linhaComAspas = listaTemporaria.toArray(new String[0]);
                palavras.add(linhaComAspas);

            }
        }

        int coluna;

        for (int x = 0; x < palavras.size(); x++) {

            for (int y = 0; y < palavras.get(x).length; y++) {
                coluna = 0;
                coluna = coluna + palavras.get(x)[y].length() - 1;
                if (!palavras.get(x)[y].trim().equals("")) {

                    verificaTokens(palavras.get(x)[y], x + 1, y);

                }
            }

        }

        if (argumento && !listaTokens.isEmpty()) {
            System.out.println("\n==================== Lista de Tokens ===================\n");
            for (Token token : listaTokens) {
                System.out.println("Lexema: " + token.getConteudo() + " |  Token: " + token.getNome() + " | Linha: " + token.getLinha() + " | Coluna:  " + token.getColuna());
            }

            System.out.println("\nQuantidade de Tokens encontrados: " + listaTokens.size());
        }

        if (argumento && !listaTokensErros.isEmpty()) {
            setHaErros(true);
            System.out.println("\n==================== Lista de Erros Lexicos ====================");
            for (TokenErro tokenErro : listaTokensErros) {
                System.out.println("\nERRO LÉXICO");
                System.out.println("Erro: " + tokenErro.getNome() + "| Linha: " + tokenErro.getLinha() + "| Coluna: " + tokenErro.getColuna());

            }
        }
        System.out.println("\n====================== Fim da Analise Léxica =====================");

    }

    public String[] removeDoVetor(String s, String[] vetor) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(vetor));
        list.remove(s);
        vetor = list.toArray(new String[0]);
        return vetor;

    }

    public void verificaTokens(String s, int linha, int coluna) {

        if (s.matches("^[a-zA-Z]+$")) {
            if (s.equals("inicio")) {
                listaTokens.add(new Token("inicio", "Inicio de programa", s, linha, coluna));
            } else if (s.equals("fim")) {
                listaTokens.add(new Token("fim", "Fim de programa", s, linha, coluna));
            } else if (s.equals("int")) {
                listaTokens.add(new Token("declaraint", "Declara valor inteiro", s, linha, coluna));
            } else if (s.equals("escreva")) {
                listaTokens.add(new Token("escreva", "Instrução screva", s, linha, coluna));
            } else if (s.equals("leia")) {
                listaTokens.add(new Token("leia", "Instrução Leia", s, linha, coluna));
            } else if (s.equals("se")) {
                listaTokens.add(new Token("se", "Instrução se", s, linha, coluna));
            } else if (s.equals("senao")) {
                listaTokens.add(new Token("senao", "Instrução Se Não", s, linha, coluna));
            } else if (s.equals("fimse")) {
                listaTokens.add(new Token("fimse", "Instrução Fim Se", s, linha, coluna));
            } else if (s.equals("faca")) {
                listaTokens.add(new Token("faca", "Instrução Faça", s, linha, coluna));
            } else if (s.equals("ate")) {
                listaTokens.add(new Token("ate", "Instrução Até", s, linha, coluna));
            } else {
                listaTokens.add(new Token("variavel", "Nome da Variavel", s, linha, coluna));
            }

        } else if (s.matches("[ > | < | = | ; | ( | ) | + | * | /]")) {
            if (s.equals(">")) {
                listaTokens.add(new Token("maior", "Simbolo Maior", s, linha, coluna));
            } else if (s.equals("<")) {
                listaTokens.add(new Token("menor", "Simbolo Menor", s, linha, coluna));
            } else if (s.equals("=")) {
                listaTokens.add(new Token("igual", "Simbolo Igual", s, linha, coluna));
            } else if (s.equals(";")) {
                listaTokens.add(new Token("fimlinha", "Simbolo de Final da linha", s, linha, coluna));
            } else if (s.equals("(")) {
                listaTokens.add(new Token("abreparenteses", "Simbolo de Abre Parenteses", s, linha, coluna));
            } else if (s.equals(")")) {
                listaTokens.add(new Token("fechaparenteses", "Simbolo de Fecha Parenteses", s, linha, coluna));
            } else if (s.equals("+")) {
                listaTokens.add(new Token("soma", "Simbolo de Soma", s, linha, coluna));
            } else if (s.equals("*")) {
                listaTokens.add(new Token("multiplicacao", "Simbolo de Multiplicacao", s, linha, coluna));
            } else if (s.equals("/")) {
                listaTokens.add(new Token("divisao", "Simbolo de Divisão", s, linha, coluna));
            } 

        } else if (s.matches("[0-9]+")) {
            listaTokens.add(new Token("numerointeiro", "Número Inteiro", s, linha, coluna));
        } else if (s.matches("-[0-9]+")) {
            listaTokens.add(new Token("numerointeironegativo", "Número Inteiro Negativo", s, linha, coluna));
        } else if (s.matches("\"(.+)\"")) {
            listaTokens.add(new Token("string", "Mensagem string", s, linha, coluna));
        } else if (s.matches("[++]{2}")) {
            listaTokens.add(new Token("incremento", "Simbolo de Incremento", s, linha, coluna));
        } else if (s.matches("[--]{2}")) {
            listaTokens.add(new Token("decremento", "Simbolo de Decremento", s, linha, coluna));
        } else if (s.matches(">=")) {
            listaTokens.add(new Token("maiorigual", "Simbolo Maior Igual", s, linha, coluna));
        } else if (s.matches("<=")) {
            listaTokens.add(new Token("menorigual", "Simbolo Menor Igual", s, linha, coluna));
        } else if (s.matches("=/")) {
            listaTokens.add(new Token("diferente", "Simbolo Diferente", s, linha, coluna));
        } else if (s.matches("->")) {
            listaTokens.add(new Token("atribuicao", "Simbolo de Atribuição", s, linha, coluna));
        } else if (s.matches("[-]{1}")) {
            listaTokens.add(new Token("subtracao", "Simbolo de Subtracao", s, linha, coluna));
        } else {
            listaTokensErros.add(new TokenErro(s, linha, coluna));
        }

    }

    public ArrayList<Token> getListaTokens() {
        return listaTokens;
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
