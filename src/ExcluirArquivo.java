import java.util.Vector;

public class ExcluirArquivo {

    Vector<arquivo> excluir(Vector<arquivo> arquivos, arquivo arqExcluir) {
        arquivos.remove(arqExcluir);
        conexao con = new conexao();
        con.excluirArquivo(arqExcluir.getId());

        return arquivos;
    }
}
