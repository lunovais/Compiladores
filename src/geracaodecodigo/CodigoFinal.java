package geracaodecodigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CodigoFinal {

    private String secaoBss;
    private String secaoData;
    private String secaoText;
    private String blocoJmpsIfs = "";
    private boolean haCondicional;
    private boolean argumento;
    private int numeroCondicional;

    public CodigoFinal(boolean argumento) {
        this.secaoData = "section .data\n";
        this.secaoBss = "section .bss\n";
        this.secaoText = "section .text\n\n"
                + "global _start\n\n"
                + "_start:\n\n";
        this.setHaCondicional(false);
        this.setArgumento(argumento);

    }

    public void interpretaCodigo(BufferedReader arquivo) throws FileNotFoundException {

        ArrayList<String> linhas = new ArrayList<>();
        int numero = 0;

        try {
            String linha = arquivo.readLine();

            while (linha != null) {
                linhas.add(linha);
                linha = arquivo.readLine();

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        for (String s : linhas) {
            String[] palavras = s.split(" ");

            ArrayList<String> listaPalavrasLinha = new ArrayList<>();

            for (String p : palavras) {
                listaPalavrasLinha.add(p);

            }

            for (String palavra : listaPalavrasLinha) {
               
                int index = listaPalavrasLinha.indexOf(palavra);

                if (palavra.equalsIgnoreCase("escreva")) {
                    String codigo = "";
                

                    if (listaPalavrasLinha.get(index + 1).contains("\"")) {

                        String mensagem = "";
                        int tamanho = 0;

                        for (int x = index + 1; x < listaPalavrasLinha.size(); x++) {

                            String p = listaPalavrasLinha.get(x);
                            if (p.contains("\"")) {
                                p = p.replace("\"", "");
                            }

                            mensagem += p + " ";
                            tamanho = mensagem.length();
                        }

                        String nomeVar = "var" + String.valueOf(numero);
                        setSecaoData(nomeVar + "  db '" + mensagem + "',0XA, 0XD\n");
                        setSecaoData("len_" + nomeVar + " equ $- " + nomeVar + "\n");
                        numero++;

                        codigo += "mov rcx, " + nomeVar + "\n";
                        codigo += "mov rdx, len_" + nomeVar + "\n";
                        codigo += "mov rbx, 1\n";
                        codigo += "mov rax, 4\n";
                        codigo += "int 0x80\n";

                    } else {

                        codigo += "mov rax, 4\n";
                        codigo += "mov rbx, 1\n";
                        codigo += "mov rcx, " + listaPalavrasLinha.get(index + 1) + "\n";
                        codigo += "mov rdx, 2\n";
                        codigo += "int 0x80\n";
                    }
                    
                    if (index != 0) {
                         codigo = "_" + listaPalavrasLinha.get(0)
                                + listaPalavrasLinha.get(1) + ":\n" + codigo;

                        codigo += "jmp _continuacao"+listaPalavrasLinha.get(1)+"\n";
                        setNumeroCondicional(Integer.parseInt(listaPalavrasLinha.get(1)));
                        setBlocoJmpsIfs(codigo);
                    }else{
                       setSecaoText(codigo);
                   }

                } else if (palavra.equalsIgnoreCase("leia") && listaPalavrasLinha.indexOf(palavra) == 0) {

                    String codigoText = "mov rax, 3\n";
                    codigoText += "mov rbx, 0\n";
                    codigoText += "mov rcx, " + listaPalavrasLinha.get(index + 1) + "\n";
                    codigoText += "mov rdx, 4\n";
                    codigoText += "int 0x80\n";
                    setSecaoText(codigoText);

                } else if (palavra.equalsIgnoreCase("var")) {

                    String codigo = listaPalavrasLinha.get(index + 1) + " resb" + " 4\n";
                    setSecaoBss(codigo);

                } else if (palavra.equalsIgnoreCase("soma")) {
                    String varA = "";
                    String varB = "";

                    varA = verificaVariavel(listaPalavrasLinha.get(index + 3));
                    varB = verificaVariavel(listaPalavrasLinha.get(index + 5));

                    String codigoText = "mov eax, " + varA + "\n";
                    codigoText += "sub eax, '0'\n";
                    codigoText += "mov ebx, " + varB + "\n";
                    codigoText += "sub ebx, '0'\n";
                    codigoText += "add eax, ebx\n";
                    codigoText += "add eax, '0'\n";
                    codigoText += "mov [" + listaPalavrasLinha.get(index + 1) + "], eax\n";

                    setSecaoText(codigoText);

                } else if (palavra.equalsIgnoreCase("subtracao")) {
                    String varA = "";
                    String varB = "";

                    varA = verificaVariavel(listaPalavrasLinha.get(index + 3));
                    varB = verificaVariavel(listaPalavrasLinha.get(index + 5));

                    String codigoText = "mov eax, " + varA + "\n";
                    codigoText += "sub eax, '0'\n";
                    codigoText += "mov ebx, " + varB + "\n";
                    codigoText += "sub ebx, '0'\n";
                    codigoText += "sub eax, ebx\n";
                    codigoText += "add eax, '0'\n";
                    codigoText += "mov [" + listaPalavrasLinha.get(index + 1) + "], eax\n";

                    setSecaoText(codigoText);

                } else if (palavra.equalsIgnoreCase("mult")) {
                    String varA = "";
                    String varB = "";

                    varA = verificaVariavel(listaPalavrasLinha.get(index + 3));
                    varB = verificaVariavel(listaPalavrasLinha.get(index + 5));

                    String codigoText = "mov al, " + varA + "\n";
                    codigoText += "sub al, '0'\n";
                    codigoText += "mov bl, " + varB + "\n";
                    codigoText += "sub bl, '0'\n";
                    codigoText += "mul bl\n";
                    codigoText += "add al, '0'\n";
                    codigoText += "mov [" + listaPalavrasLinha.get(index + 1) + "], al\n";

                    setSecaoText(codigoText);

                } else if (palavra.equalsIgnoreCase("divisao")) {
                    String varA = "";
                    String varB = "";

                    varA = verificaVariavel(listaPalavrasLinha.get(index + 3));
                    varB = verificaVariavel(listaPalavrasLinha.get(index + 5));

                    String codigoText = "mov al, " + varA + "\n";
                    codigoText += "sub al, '0'\n";
                    codigoText += "mov bl, " + varB + "\n";
                    codigoText += "sub bl, '0'\n";
                    codigoText += "div bl\n";
                    codigoText += "add al, '0'\n";
                    codigoText += "mov [" + listaPalavrasLinha.get(index + 1) + "], al\n";

                    setSecaoText(codigoText);

                } else if (listaPalavrasLinha.get(index).equalsIgnoreCase("=")) {
                    String codigo = "";
                    
                    String var = "";
                    var = verificaVariavel(listaPalavrasLinha.get(index + 1));
                    codigo += "mov eax, " + var + "\n";
                    codigo += "mov [" + listaPalavrasLinha.get(index - 1) + "], eax\n";
                    
                                    
                   if (index - 1 != 0) {
                         codigo = "_" + listaPalavrasLinha.get(0)
                                + listaPalavrasLinha.get(1) + ":\n" + codigo;

                        codigo += "jmp _continuacao"+listaPalavrasLinha.get(1)+"\n";
                        setNumeroCondicional(Integer.parseInt(listaPalavrasLinha.get(1)));
                        
                        setBlocoJmpsIfs(codigo);
                    } else{
                       setSecaoText(codigo);
                   }

                   
                } else if (listaPalavrasLinha.get(index).equals("se")) {
                    setHaCondicional(true);

                    String operacao = "";
                    String operacaoInversa = "";

                    if (listaPalavrasLinha.get(index + 3).equals("maior")) {
                        operacao = "jg";
                        operacaoInversa = "jl";

                    } else if (listaPalavrasLinha.get(index + 3).equals("menor")) {
                        operacao = "jl";
                        operacaoInversa = "jg";

                    } else if (listaPalavrasLinha.get(index + 3).equals("igual")) {
                        operacao = "je";
                        operacaoInversa = "jne";

                    }
                    

                    String varA = "", varB = "";

                    varA = verificaVariavel(listaPalavrasLinha.get(index + 2));
                    varB = verificaVariavel(listaPalavrasLinha.get(index + 4));
                    
                    int numeroSe = Integer.parseInt(listaPalavrasLinha.get(index+1));
                    String codigo ="";
                    
                    if(numeroSe>0){
                          codigo += "_continuacao"+(numeroSe-1)+":\n";                      
                    }
                    
                    codigo += "mov eax, " + varA + "\n";
                    codigo += "mov ebx, " + varB + "\n";
                    codigo += "cmp eax, ebx\n";
                    String positivo = " _true" + listaPalavrasLinha.get(index + 1) + "\n";
                    String negativo = " _false" + listaPalavrasLinha.get(index + 1) + "\n";
                    codigo += operacao + positivo;
                    codigo += "cmp eax, ebx\n";
                    codigo += operacaoInversa + negativo;
                    
                    setSecaoText(codigo);

                } 

            }

        }

        this.geraCodigo();

    }

    public String verificaVariavel(String variavel) {
        String varA = "";
        if (variavel.matches("[0-9]+")) {
            varA = "'" + variavel + "'";
        } else {
            varA = "[" + variavel + "]";
        }
        return varA;
    }

    public void geraCodigo() throws FileNotFoundException {

        String codigoFinal = "";
        codigoFinal += getSecaoBss() + "\n";
        codigoFinal += getSecaoData() + "\n";
        codigoFinal += getSecaoText() + "\n";
        codigoFinal += getBlocoJmpsIfs() + "\n";
        if(isHaCondicional()){
            codigoFinal+=" _continuacao"+getNumeroCondicional()+":\n";
        }
        codigoFinal += "mov rax, 1\n"
                + "int 80h\n";

        PrintWriter arquivoInt = new PrintWriter("final.asm");
        arquivoInt.println(codigoFinal);
        arquivoInt.close();

        System.out.println("\n========================== Código Final ==========================\n");
        System.out.println(codigoFinal);
        System.out.println("=================================================================\n");
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
     * @return the secaoBss
     */
    public String getSecaoBss() {
        return secaoBss;
    }

    /**
     * @param secaoBss the secaoBss to set
     */
    public void setSecaoBss(String secaoBss) {
        this.secaoBss += secaoBss;
    }

    /**
     * @return the secaoData
     */
    public String getSecaoData() {
        return secaoData;
    }

    /**
     * @param secaoData the secaoData to set
     */
    public void setSecaoData(String secaoData) {
        this.secaoData += secaoData;
    }

    /**
     * @return the secaoText
     */
    public String getSecaoText() {
        return secaoText;
    }

    /**
     * @param secaoText the secaoText to set
     */
    public void setSecaoText(String secaoText) {
        this.secaoText += secaoText;
    }

    /**
     * @return the haCondicional
     */
    public boolean isHaCondicional() {
        return haCondicional;
    }

    /**
     * @param haCondicional the haCondicional to set
     */
    public void setHaCondicional(boolean haCondicional) {
        this.haCondicional = haCondicional;
    }

    /**
     * @return the blocoJmpsIfs
     */
    public String getBlocoJmpsIfs() {
        return blocoJmpsIfs;
    }

    /**
     * @param blocoJmpsIfs the blocoJmpsIfs to set
     */
    public void setBlocoJmpsIfs(String blocoJmpsIfs) {
        this.blocoJmpsIfs += blocoJmpsIfs;
    }

    /**
     * @return the numeroCondicional
     */
    public int getNumeroCondicional() {
        return numeroCondicional;
    }

    /**
     * @param numeroCondicional the numeroCondicional to set
     */
    public void setNumeroCondicional(int numeroCondicional) {
        this.numeroCondicional = numeroCondicional;
    }

}
