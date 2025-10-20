
import java.util.Vector;

public class BuscaBinaria {

    int buscarIndexArquivo(Vector<arquivo> arquivos, arquivo arq) {
        int inicio = 0;
        int fim = arquivos.size() - 1;
        int meio;

        while (inicio <= fim) {

            meio = (inicio + fim) / 2;

            if (arq.getId() == arquivos.get(meio).getId()) {
                return arquivos.get(meio).getId();
            }

            if (arq.getId() < arquivos.get(meio).getId()) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }

        }
        return -1;
    }
    
}
