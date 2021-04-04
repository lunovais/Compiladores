package analisadorsintatico;

import java.util.LinkedList;

public class TabelaSintatica {

    public LinkedList<Combinacao> geraTabela() {

        
        LinkedList<Combinacao> listaCombinacoes = new LinkedList<>();

        Combinacao combinacaoInicio = new Combinacao("inicio");
        combinacaoInicio.adicionaProducao("PROGRAMA", 0);

        Combinacao combinacaoFim = new Combinacao("fim");
        combinacaoFim.adicionaProducao("LISTACOMANDOS", 2);

        Combinacao combinacaoDeclaraInt = new Combinacao("declaraint");
        combinacaoDeclaraInt.adicionaProducao("LISTACOMANDOS", 1);
        combinacaoDeclaraInt.adicionaProducao("COMANDO", 6);
        combinacaoDeclaraInt.adicionaProducao("DECLARACAO", 37);

        Combinacao combinacaoEscreva = new Combinacao("escreva");
        combinacaoEscreva.adicionaProducao("LISTACOMANDOS", 1);
        combinacaoEscreva.adicionaProducao("COMANDO", 3);
        combinacaoEscreva.adicionaProducao("ESCREVA", 26);

        Combinacao combinacaoLeia = new Combinacao("leia");
        combinacaoLeia.adicionaProducao("LISTACOMANDOS", 1);
        combinacaoLeia.adicionaProducao("COMANDO", 4);
        combinacaoLeia.adicionaProducao("LEITURA", 5);
        combinacaoLeia.adicionaProducao("LEIA", 25);

        Combinacao combinacaoSe = new Combinacao("se");
        combinacaoSe.adicionaProducao("LISTACOMANDOS", 1);
        combinacaoSe.adicionaProducao("COMANDO", 13);
        combinacaoSe.adicionaProducao("SE", 27);

        Combinacao combinacaoSenao = new Combinacao("senao");
        combinacaoSenao.adicionaProducao("LISTACOMANDOS", 2);
        combinacaoSenao.adicionaProducao("SENAO", 14);
        
        Combinacao combinacaoFimSe = new Combinacao("fimse");
        combinacaoFimSe.adicionaProducao("LISTACOMANDOS", 2);
        combinacaoFimSe.adicionaProducao("FIMSE", 15);

        Combinacao combinacaoFaca = new Combinacao("faca");
        combinacaoFaca.adicionaProducao("LISTACOMANDOS", 1);
        combinacaoFaca.adicionaProducao("COMANDO", 16);
        combinacaoFaca.adicionaProducao("FACA", 28);

        Combinacao combinacaoAte = new Combinacao("ate");
        combinacaoAte.adicionaProducao("ATE", 29);

        Combinacao combinacaoMaior = new Combinacao("maior");
        combinacaoMaior.adicionaProducao("OPERADORLOG", 38);
        combinacaoMaior.adicionaProducao("CONTEUDOEXPRESSAOLOG", 18);

        Combinacao combinacaoMenor = new Combinacao("menor");
        combinacaoMenor.adicionaProducao("OPERADORLOG", 39);
        combinacaoMenor.adicionaProducao("CONTEUDOEXPRESSAOLOG", 18);

        Combinacao combinacaoMaiorIgual = new Combinacao("maiorigual");
        combinacaoMaiorIgual.adicionaProducao("OPERADORLOG", 40);
        combinacaoMaiorIgual.adicionaProducao("CONTEUDOEXPRESSAOLOG", 18);

        Combinacao combinacaoMenorIgual = new Combinacao("menorigual");
        combinacaoMenorIgual.adicionaProducao("OPERADORLOG", 41);
        combinacaoMenorIgual.adicionaProducao("CONTEUDOEXPRESSAOLOG", 18);

        Combinacao combinacaoIgual = new Combinacao("igual");
        combinacaoIgual.adicionaProducao("OPERADORLOG", 42);
        combinacaoIgual.adicionaProducao("CONTEUDOEXPRESSAOLOG", 18);

        Combinacao combinacaoDiferente = new Combinacao("diferente");
        combinacaoDiferente.adicionaProducao("OPERADORLOG", 43);
        combinacaoDiferente.adicionaProducao("CONTEUDOEXPRESSAOLOG", 18);

        Combinacao combinacaoAtribuicao = new Combinacao("atribuicao");
        combinacaoAtribuicao.adicionaProducao("ATRIBUICAO", 12);
        combinacaoAtribuicao.adicionaProducao("CONTEUDODECLARACAO", 8);

        Combinacao combinacaoFimLinha = new Combinacao("fimlinha");
        combinacaoFimLinha.adicionaProducao("CONTEUDODECLARACAO", 11);
        combinacaoFimLinha.adicionaProducao("CONTEUDOEXPRESSAOMAT", 22);
        combinacaoFimLinha.adicionaProducao("CONTEUDOEXPRESSAOLOG", 19);

        Combinacao combinacaoFechaP = new Combinacao("fechaparenteses");
        combinacaoFechaP.adicionaProducao("LISTACOMANDOS", 2);
        combinacaoFechaP.adicionaProducao("CONTEUDOEXPRESSAOLOG", 19);

        Combinacao combinacaoSoma = new Combinacao("soma");
        combinacaoSoma.adicionaProducao("OPERADORMAT", 44);
        combinacaoSoma.adicionaProducao("CONTEUDOEXPRESSAOMAT", 21);

        Combinacao combinacaoSub = new Combinacao("subtracao");
        combinacaoSub.adicionaProducao("OPERADORMAT", 45);
        combinacaoSub.adicionaProducao("CONTEUDOEXPRESSAOMAT", 21);

        Combinacao combinacaoMulti = new Combinacao("multiplicacao");
        combinacaoMulti.adicionaProducao("OPERADORMAT", 46);
        combinacaoMulti.adicionaProducao("CONTEUDOEXPRESSAOMAT", 21);

        Combinacao combinacaoDivisao = new Combinacao("divisao");
        combinacaoDivisao.adicionaProducao("OPERADORMAT", 47);
        combinacaoDivisao.adicionaProducao("CONTEUDOEXPRESSAOMAT", 21);

        Combinacao combinacaoIncremento = new Combinacao("incremento");
        combinacaoIncremento.adicionaProducao("ITERACAO", 23);
        combinacaoIncremento.adicionaProducao("CONTEUDODECLARACAO", 10);

        Combinacao combinacaoDecremento = new Combinacao("decremento");
        combinacaoDecremento.adicionaProducao("ITERACAO", 24);
        combinacaoDecremento.adicionaProducao("CONTEUDODECLARACAO", 10);

        Combinacao combinacaoNumInteiro = new Combinacao("numerointeiro");
        combinacaoNumInteiro.adicionaProducao("CONTEUDO", 34);
        combinacaoNumInteiro.adicionaProducao("NUMERO", 31);
        combinacaoNumInteiro.adicionaProducao("EXPRESSAOMAT", 20);

        Combinacao combinacaoNumInteiroNeg = new Combinacao("numerointeironegativo");
        combinacaoNumInteiroNeg.adicionaProducao("CONTEUDO", 34);
        combinacaoNumInteiroNeg.adicionaProducao("NUMERO", 32);
        combinacaoNumInteiroNeg.adicionaProducao("EXPRESSAOMAT", 20);

        Combinacao combinacaoString = new Combinacao("string");
        combinacaoString.adicionaProducao("CONTEUDO", 36);
        combinacaoString.adicionaProducao("STRING", 30);
        combinacaoString.adicionaProducao("EXPRESSAOMAT", 20);

        Combinacao combinacaoVariavel = new Combinacao("variavel");
        combinacaoVariavel.adicionaProducao("LISTACOMANDOS", 1);
        combinacaoVariavel.adicionaProducao("COMANDO", 7);
        combinacaoVariavel.adicionaProducao("CONTEUDO", 35);
        combinacaoVariavel.adicionaProducao("VARIAVEL", 33);
        combinacaoVariavel.adicionaProducao("CONTEUDODECLARACAO", 9);
        combinacaoVariavel.adicionaProducao("EXPRESSAOMAT", 20);
        combinacaoVariavel.adicionaProducao("EXPRESSAOLOG", 17);




        listaCombinacoes.add(combinacaoInicio);
        listaCombinacoes.add(combinacaoFim);
        listaCombinacoes.add(combinacaoDeclaraInt);
        listaCombinacoes.add(combinacaoEscreva);
        listaCombinacoes.add(combinacaoLeia);
        listaCombinacoes.add(combinacaoSe);
        listaCombinacoes.add(combinacaoSenao);
        listaCombinacoes.add(combinacaoFimSe);
        listaCombinacoes.add(combinacaoFaca);
        listaCombinacoes.add(combinacaoAte);
        listaCombinacoes.add(combinacaoMaior);
        listaCombinacoes.add(combinacaoMenor);
        listaCombinacoes.add(combinacaoMaiorIgual);
        listaCombinacoes.add(combinacaoMenorIgual);
        listaCombinacoes.add(combinacaoIgual);
        listaCombinacoes.add(combinacaoDiferente);
        listaCombinacoes.add(combinacaoAtribuicao);
        listaCombinacoes.add(combinacaoFimLinha);
        listaCombinacoes.add(combinacaoFechaP);
        listaCombinacoes.add(combinacaoSoma);
        listaCombinacoes.add(combinacaoSub);
        listaCombinacoes.add(combinacaoMulti);
        listaCombinacoes.add(combinacaoDivisao);
        listaCombinacoes.add(combinacaoIncremento);
        listaCombinacoes.add(combinacaoDecremento);
        listaCombinacoes.add(combinacaoNumInteiro);
        listaCombinacoes.add(combinacaoNumInteiroNeg);
        listaCombinacoes.add(combinacaoString);
        listaCombinacoes.add(combinacaoVariavel);


        return listaCombinacoes;
    }


}