

package analisadorlexico;


public class TokenErro {
    
    private String nome;
    private int linha;
    private int coluna;

    public TokenErro(String nome, int linha, int coluna) {
        this.nome = nome;
        this.linha = linha;
        this.coluna = coluna;
    }

    /*
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the linha
     */
    public int getLinha() {
        return linha;
    }

    /**
     * @param linha the linha to set
     */
    public void setLinha(int linha) {
        this.linha = linha;
    }

    /**
     * @return the coluna
     */
    public int getColuna() {
        return coluna;
    }

    /**
     * @param coluna the coluna to set
     */
    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    

}
