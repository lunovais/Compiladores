package producao;

import analisadorlexico.Lexica;
import analisadorlexico.Token;
import analisadorsemantico.Semantica;
import analisadorsintatico.Sintatica;
import geracaodecodigo.CodigoFinal;
import geracaodecodigo.CodigoIntermediario;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Teste {

    public static void main(String[] args) throws IOException {

        String caminho = "";
        boolean argumento = false;
        ArrayList<String> listaArgumentos = new ArrayList<>();

        if (args.length == 0) {
            System.out.println("Argumento vazio");
        } else {
            caminho = args[0];

        }

        if (args.length > 1) {
            for (int x = 1; x < args.length; x++) {
                if (args[x].equalsIgnoreCase("-lt") || args[x].equalsIgnoreCase("-ls") 
                        || args[x].equalsIgnoreCase("-lse") || args[x].equalsIgnoreCase("-lgc")
                        || args[x].equalsIgnoreCase("-tudo")) {
                    listaArgumentos.add(args[x]);
                } else {
                    System.out.println("Argumento invalido");
                }
            }
        }

        abreArquivo(caminho, listaArgumentos);

    }

    public static void abreArquivo(String caminho, ArrayList<String> listaArgumentos) throws IOException {

        try {
            String nome;

            BufferedReader lerarq = new BufferedReader(new FileReader(caminho));

            ArrayList<String> linhas = new ArrayList<>();
            ArrayList<Token> tokens = new ArrayList<>();

            try {
                String linha = lerarq.readLine();

                while (linha != null) {
                    linhas.add(linha);
                    linha = lerarq.readLine();

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            boolean argumentoLT = false;
            boolean argumentoLS = false;
            boolean argumentoLSE = false;
            boolean argumentoLGC = false;
            

            for (String a : listaArgumentos) {
                if (a.equalsIgnoreCase("-lt")) {
                    argumentoLT = true;
                } else if(a.equalsIgnoreCase("-ls")){
                    argumentoLS = true;
                } else if (a.equalsIgnoreCase("-lse")){
                    argumentoLSE = true;
                } else if (a.equalsIgnoreCase("-lgc")){                    
                    argumentoLGC = true;
                } else if(a.equalsIgnoreCase("-tudo")){
                    argumentoLS = true;
                    argumentoLT = true;
                    argumentoLSE = true;
                    argumentoLGC = true;
                }
            }

            Lexica le = new Lexica();
            le.analiseLexica(linhas, argumentoLT);

            if (!le.isHaErros()) {
                Sintatica si = new Sintatica();
                si.analiseSintatica(le.getListaTokens(), argumentoLS);
            }
            
            if (argumentoLSE) {
                Semantica se = new Semantica(le.getListaTokens(), true);    
            }
            
            if(argumentoLGC){
                CodigoIntermediario intermediario = new CodigoIntermediario();
                intermediario.geraCodigoIntermediario(le.getListaTokens());
            }        
            

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

}
