import java.util.Vector;

public class ExcluirArquivo {

    Vector<Arquivo> excluir(Vector<Arquivo> arquivos, Arquivo arqExcluir) {
        arquivos.remove(arqExcluir);
        Conexao con = new Conexao();
        con.excluirArquivo(arqExcluir.getId());

        return arquivos;
    }
}
