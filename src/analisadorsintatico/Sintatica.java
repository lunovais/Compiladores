package analisadorsintatico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import analisadorlexico.Token;

public class Sintatica {

    private LinkedList<Token> pTokens;
    private LinkedList<String> pNaoTerminais;
    private LinkedList<Combinacao> listaCombinacoes;

    public Sintatica() {
        this.pTokens = new LinkedList<>();
        this.pNaoTerminais = new LinkedList<>();
        this.listaCombinacoes = new LinkedList<>();
    }

    public LinkedList<Token> getPTokens() {
        return pTokens;
    }


    public void setPTokens(LinkedList<Token> pTokens) {
        this.pTokens = pTokens;
    }

    public void adicionaTokens(Token s) {

        this.pTokens.add(0, s);
    }


    public LinkedList<String> getPNaoTerminais() {
        return pNaoTerminais;
    }


    public void setPNaoTerminais(LinkedList<String> pNaoTerminais) {
        this.pNaoTerminais = pNaoTerminais;
    }

    public void adicionaNaoTerminais(String s) {
        this.pNaoTerminais.add(0, s);
    }


    public LinkedList<Combinacao> getListaCombinacoes() {
        return listaCombinacoes;
    }


    public void setListaCombinacoes(LinkedList<Combinacao> listaCombinacoes) {
        this.listaCombinacoes = listaCombinacoes;
    }

    //verifica se o token da tabela é igual ao da pilha
    public boolean verificaTokenListaCombinacoes(String nomeToken){
        boolean haToken = false;

        for(Combinacao c : listaCombinacoes){
            if (c.getTokenGerador().equals(nomeToken)){
                haToken = true;
            }
        }
        return haToken;
    }
    
    //verifica se é igual e adiciona na lista
    public Map<String, Integer> getListaProducoes(String nomeToken){

        Map<String, Integer> listaProducoes = new HashMap<>();

        for(Combinacao c : listaCombinacoes){
            if (c.getTokenGerador().equals(nomeToken)){
                listaProducoes = c.getListaProducoes();
            }
        }

        return listaProducoes;
    }

    //se for igual ele tira os dois; usa o metodo verificatoken; depois ele pega listadeproducoes;
    //
    public void analiseSintatica(ArrayList<Token> tokens, boolean opcao) {

       TabelaSintatica t = new TabelaSintatica();
       setListaCombinacoes(t.geraTabela());

        boolean finalizado = false;
       System.out.println("\n======================== Analise Sintática =======================");

        String mensagem = "\n=================== LOG da Análise Sintática ==================";

        Token $ = new Token("$", "CIFRAO", 0, 0);
        $.setNome("$");
        adicionaTokens($);

        Collections.reverse(tokens);
        for (Token token : tokens) {
            adicionaTokens(token);
        }

        adicionaNaoTerminais("$");
        adicionaNaoTerminais("PROGRAMA");

        while (!finalizado) {

            if (getPTokens().isEmpty() && getPNaoTerminais().isEmpty()) {
                finalizado = true;
            } else {
                mensagem += "\n\nTOPO DA FILA DE TOKENS: " + getPTokens().peek().getNome() + " - " + getPTokens().peek().getConteudo() + "\n";
                mensagem += "TOPO DA FILA DE NAO TERMINAIS: " + getPNaoTerminais().peek();

                if (getPTokens().peek().getNome().equals(getPNaoTerminais().peek())) {

                    getPTokens().pop();
                    getPNaoTerminais().pop();

                } else if (verificaTokenListaCombinacoes(getPTokens().peek().getNome())) {

                    Map<String, Integer> listaProducoes = getListaProducoes(getPTokens().peek().getNome());
                    if (listaProducoes.containsKey(getPNaoTerminais().peek())) {

                        ArrayList<String> listaNaoTerminais = leListaProducoes(listaProducoes.get(getPNaoTerminais().peek()));
                        getPNaoTerminais().pop();

                        if (!listaNaoTerminais.get(0).equals("i")) {

                            for (String s : listaNaoTerminais) {
                                adicionaNaoTerminais(s);
                            }

                        }

                    } else {

                        System.out.println(mensagemErro());
                        finalizado = true;
                    }
                } else {

                    System.out.println(mensagemErro());
                    finalizado = true;
                }

            }
        }

        if (opcao) {

            System.out.println(mensagem);

        }
            System.out.println("\n==================== Fim da Analise Sintática ===================");

    }

    public ArrayList<String> leListaProducoes(int numeroLinha) {

        String producoesCSV = "listaProducoes.csv";
        String linha = "";
        String separador = ",";

        ArrayList<String> listaProducoes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(producoesCSV))) {
            int linhaCont = 0;
            while ((linha = br.readLine()) != null) {
                if (linhaCont == numeroLinha) {
                    String[] naoTerminais = linha.split(separador);
                    for (String s : naoTerminais) {
                        listaProducoes.add(s);
                    }
                }

                linhaCont++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.reverse(listaProducoes);
        return listaProducoes;
    }

    public String mensagemErro() {
        String mensagem = "\nERRO SINTATICO"
                + "\nToken " + this.getPTokens().peek().getNome() + " inesperado"
                + "\nLinha:" + this.getPTokens().peek().getLinha()
                + "\nColuna:" + this.getPTokens().peek().getColuna();

        return mensagem;
    }
}
