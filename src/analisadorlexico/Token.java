package analisadorlexico;

public class Token {

    private String nome;
    private String rotulo;
    private String conteudo;
    private int linha;
    private int coluna;
    
    public Token() {
        this.nome= null;
    }
    
    public Token(String nome, String rotulo, String conteudo, int linha, int coluna) {
    this.nome = nome;
    this.rotulo = rotulo;
    this.conteudo = conteudo;
    this.linha = linha;
    this.coluna = coluna;
    }

    public Token(String nome, String conteudo, int linha, int coluna) {
        this.nome = nome;
        this.conteudo = conteudo;
        this.linha = linha;
        this.coluna = coluna;
    }    

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
}
