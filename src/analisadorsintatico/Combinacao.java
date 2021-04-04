package analisadorsintatico;

import java.util.HashMap;
import java.util.Map;

public class Combinacao {

    private Map<String,Integer> listaProducoes;
    private String tokenGerador;


    public Combinacao(String tokenGerador) {
        this.tokenGerador = tokenGerador;
        listaProducoes = new HashMap<>();

    }

    public void adicionaProducao(String naoTerminal, Integer producao){

        this.listaProducoes.put(naoTerminal,producao);

    }

    public Map<String, Integer> getListaProducoes() {
        return listaProducoes;
    }

    public void setListaProducoes(Map<String, Integer> listaProducoes) {
        this.listaProducoes = listaProducoes;
    }

    public String getTokenGerador() {
        return tokenGerador;
    }

    public void setTokenGerador(String tokenGerador) {
        this.tokenGerador = tokenGerador;
    }
}


