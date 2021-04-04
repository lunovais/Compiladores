package geracaodecodigo;

import analisadorlexico.Token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CodigoIntermediario {

    private ArrayList<String> listaVariaveis;
    private boolean modoSe;
    
    

    public CodigoIntermediario() {

        System.out.println("======================== Geração de Código ========================");
        modoSe = false;
        this.listaVariaveis = new ArrayList<>();

    }

    public void geraCodigoIntermediario(ArrayList<Token> listaTokens) throws FileNotFoundException {

        String codigoTexto = "";
        int numLaco = 0;

        ArrayList<Token> listaOperandos = new ArrayList<>();
   

        for (Token t : listaTokens) {

            int index = listaTokens.indexOf(t);

            if (t.getNome().equalsIgnoreCase("declaraint") && !isModoSe()) {
                
                if(listaTokens.get(index+2).getNome().equals("fimlinha")){
                    this.getListaVariaveis().add("var "+listaTokens.get(index + 1).getConteudo());
                }

                

            } else if (t.getNome().equalsIgnoreCase("atribuicao")  && !isModoSe()) {

                int indiceSinal = index;
                index++;

                while (!listaTokens.get(index).getNome().equals("fimlinha")) {

                    listaOperandos.add(listaTokens.get(index));
                    index++;

                }

                if (listaOperandos.size() == 1) {

                    codigoTexto += listaTokens.get(indiceSinal - 1).getConteudo() + " = " + listaOperandos.get(0).getConteudo() + "\n";

                } else if (listaOperandos.size() == 3) {

                    String operacao = "";

                    if (listaOperandos.get(1).getConteudo().equalsIgnoreCase("+")) {
                        operacao = "soma ";
                    } else if (listaOperandos.get(1).getConteudo().equalsIgnoreCase("-")) {
                        operacao = "subtracao ";
                    } else if (listaOperandos.get(1).getConteudo().equalsIgnoreCase("/")) {
                        operacao = "divisao ";
                    } else if (listaOperandos.get(1).getConteudo().equalsIgnoreCase("*")) {
                        operacao = "mult ";
                    }

                    codigoTexto += operacao + listaTokens.get(indiceSinal - 1).getConteudo() + " ="
                            + " " + listaOperandos.get(0).getConteudo() + ""
                            + " " + listaOperandos.get(1).getConteudo() + ""
                            + " " + listaOperandos.get(2).getConteudo() + "\n";
                }

                listaOperandos.clear();

            } else if (t.getNome().equalsIgnoreCase("escreva") && !isModoSe()) {

                codigoTexto += t.getNome() + " " + listaTokens.get(index + 2).getConteudo() + "\n";

            } else if (t.getNome().equalsIgnoreCase("leia") && !isModoSe()) {

                codigoTexto += t.getNome() + " " + listaTokens.get(index + 2).getConteudo() + "\n";

            } else if (t.getNome().equalsIgnoreCase("se")){
                
                setModoSe(true);
                
                String operador = listaTokens.get(index+3).getConteudo();
                if (operador.equals(">")) {
                    operador = "maior ";
                }else if(operador.equals("<")){
                    operador = "menor ";
                }else if(operador.equals("=")){
                    operador = "igual ";
                }else if(operador.equals("=/")){
                    operador = "diferente ";
                }else if(operador.equals(">=")){
                    operador = "maior igual ";
                }else if(operador.equals("<=")){
                    operador = "menor igual ";
                }
                
                
                codigoTexto += t.getNome()+" "+numLaco+" "+listaTokens.get(index+2).getConteudo()+
                        " "+operador+listaTokens.get(index+4).getConteudo()+"\n";
                
                String verdadeiro = "true "+numLaco+" ", falso = "false "+numLaco+" ";
                
                int x = index+6;
                
                while(!listaTokens.get(x).getNome().equals("senao")){
                    verdadeiro += listaTokens.get(x).getConteudo() + " ";
                    x++;
                }
                
                int indexSenao = x + 1;
               
                while(!listaTokens.get(indexSenao).getNome().equalsIgnoreCase("fimse")){
                    falso += listaTokens.get(indexSenao).getConteudo()+" ";
                    indexSenao++;
                }
                
                numLaco++;
                
                verdadeiro = verdadeiro.replace("( ", "");
                verdadeiro = verdadeiro.replace(" )", "");
                verdadeiro = verdadeiro.replace(" ; ", "");
                verdadeiro = verdadeiro.replace("->", "=");
                
                falso = falso.replace("( ", "");
                falso = falso.replace(" )", "");
                falso = falso.replace(" ; ", "");
                falso = falso.replace("->", "=");
                
                codigoTexto += verdadeiro+"\n";
                codigoTexto += falso+"\n";
                
            } else if(t.getNome().equals("fimse")){
                setModoSe(false);
                
            } else if (t.getNome().equals("faca")){
                
            }

        }

        String codigoInt = "";
        for (String var : this.getListaVariaveis()) {
            codigoInt += var + "\n";
        }

        codigoInt += codigoTexto;

        System.out.println("\n====================== Código Intermediário =======================\n");
        System.out.println(codigoInt);

        try {
            escreveArquivoInt(codigoInt);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        FileReader arquivo = new FileReader("intermediario.txt");
        BufferedReader lerarq = new BufferedReader(arquivo);

        CodigoFinal fim = new CodigoFinal(true);
        fim.interpretaCodigo(lerarq);

    }

    public ArrayList<String> getListaVariaveis() {
        return listaVariaveis;
    }

    public void setListaVariaveis(ArrayList<String> listaVariaveis) {
        this.listaVariaveis = listaVariaveis;
    }
    
    

    private void escreveArquivoInt(String codigo) throws FileNotFoundException {

        PrintWriter arquivoInt = new PrintWriter("intermediario.txt");
        arquivoInt.println(codigo);
        arquivoInt.close();

    }

    /**
     * @return the modoSe
     */
    public boolean isModoSe() {
        return modoSe;
    }

    /**
     * @param modoSe the modoSe to set
     */
    public void setModoSe(boolean modoSe) {
        this.modoSe = modoSe;
    }



}
