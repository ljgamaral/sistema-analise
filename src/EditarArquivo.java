import java.util.Vector;

public class EditarArquivo {
    Vector<arquivo> editar(Vector<arquivo> arquivos, arquivo arqAntigo, arquivo arqNovo) {
        InsertionSort ordenar = new InsertionSort();
        Vector<arquivo> arqs = ordenar.ordenar(arquivos, "ID");
        BuscaBinaria buscaBinaria = new BuscaBinaria();
        int index = buscaBinaria.buscarIndexArquivo(arqs, arqAntigo);
        arquivos.set(index, arqNovo);
        conexao con = new conexao();
        con.editarArquivo(index, arqNovo.getNome(), arqNovo.getTamanho(), arqNovo.getArquivo());
        
        return arquivos;
    }
}
