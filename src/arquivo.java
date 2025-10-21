import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Arquivo {
    private int id;
    private String nome;
    private Timestamp data_criacao;
    private int tamanho;
    private byte[] arquivo;
    
    public Arquivo(int id, String nome, Timestamp data_criacao, int tamanho, byte[] arquivo) {
        this.id = id;
        this.nome = nome;
        this.data_criacao = data_criacao;
        this.tamanho = tamanho;
        this.arquivo = arquivo;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public Timestamp getDataCriacao() {
        return data_criacao;
    }
    
    
    public long getDataCriacaoLong() {
        return data_criacao.getTime();
    }
    
    public String getDataCriacaoStringFormatado() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formatado = data_criacao.toLocalDateTime().format(formatter);
        return formatado;
    }
    
    public int getTamanho() {
        return tamanho;
    }
    
    public byte[] getArquivo() {
        return arquivo;
    }
    
    public void setId(int novoId) {
        id = novoId;
    }
    
    public void setNome(String novoNome) {
        nome = novoNome;
    }
    
    public void setDataCriacaoLong(long novoDataCriacao) {
        data_criacao = Timestamp.from(Instant.ofEpochMilli(novoDataCriacao));
    }
    
    public void setArquivo(byte[] novoArquivo) {
        arquivo = novoArquivo;
    }
    
    public void setTamanho(int novoTamanho) {
        tamanho = novoTamanho;
    }
}
